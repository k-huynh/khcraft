package org.kh.khcraft.Listeners;

import org.bukkit.advancement.Advancement;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.kh.khcraft.Events.DailyRewardEvent;
import org.kh.khcraft.Khcraft;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;

public class KBListener implements Listener {
    Khcraft plugin;
    FileConfiguration config;

    public KBListener (Khcraft instance) {
        plugin = instance;
        config = plugin.getConfig();
    }

    // give players KB reward on advancements
    @EventHandler
    public void onPlayerAdvancementDoneEvent(PlayerAdvancementDoneEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();

        Advancement advancement = event.getAdvancement();
        String advancementName = advancement.getKey().toString();

        // make sure the advancement is not a recipe advancement lol
        if (!advancementName.contains("recipes")) {
            // check if the player has completed this advancement before
            try {
                ResultSet advancementRS = plugin.stmt.executeQuery(String.format("SELECT * FROM AdvancementHistory WHERE Username = '%s' AND AdvancementName = '%s';",
                        playerName, advancementName));

                // if it exists then they've gotten this achievement before
                if (!advancementRS.next()) {
                    // award player with KB
                    int reward = config.getInt("economy.achievementReward");

                    plugin.stmt.executeUpdate(String.format("UPDATE Users SET KB = KB + %d WHERE Username = '%s';", reward, playerName));
                    System.out.printf("%s has been rewarded %d KB for getting an advancement!\n", playerName, reward);
                    player.sendMessage(String.format("Congratulations! You have been awarded %d KB for completing an advancement.", reward));

                    // add to advancement history
                    plugin.stmt.executeUpdate(String.format("INSERT INTO AdvancementHistory (Username, AdvancementName) VALUES ('%s', '%s');",
                            playerName, advancementName));

                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // give players KB daily on login
    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();

        System.out.println("Checking for daily reward eligibility");

        // get last login date from database
        try {
            ResultSet dailyRS = plugin.stmt.executeQuery(String.format("SELECT Daily FROM Users WHERE Username = '%s';", playerName));

            if (dailyRS.next()) {
                int daily = dailyRS.getInt(1);

                // eligible!
                if (daily == 1) {
                    System.out.println("Eligible! Giving daily reward");
                    int dailyReward = config.getInt("economy.dailyReward");
                    plugin.stmt.executeUpdate(String.format("UPDATE Users SET Daily = 0, KB = KB + %d WHERE Username = '%s';", dailyReward, playerName));
                    player.sendMessage(String.format("You have received %d KB for today's daily bonus!", dailyReward));
                }
                else {
                    System.out.println("Ineligible. Not giving daily reward");
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @EventHandler
    public void onDailyRewardEvent(DailyRewardEvent event) {
        System.out.println("Daily reward event triggered! Setting DAILY to 1 for all users");

        // set everyone's 'daily' in users to 1
        try {
            plugin.stmt.executeUpdate("UPDATE Users SET Daily = 1;");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
