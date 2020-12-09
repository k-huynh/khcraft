package org.kh.khcraft.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.kh.khcraft.Events.SkillExpEvent;
import org.kh.khcraft.Khcraft;
import org.kh.khcraft.Skills.ExpHandlers.*;

import java.sql.SQLException;

public class SkillExpListener implements Listener {
    Khcraft plugin;

    public SkillExpListener(Khcraft instance) {
        plugin = instance;
    }

    @EventHandler
    public void onSkillExpEvent(SkillExpEvent event) {
        String playerName = event.getPlayerName();
        String skillName = event.getSkillName();
        Player player = event.getPlayer();
        Double exp = event.getExpChange();

        // check exp for the skill to see if they've leveled up
        switch(skillName) {
            case("MINING"):
                MiningExp miningExp = new MiningExp(plugin);
                miningExp.handleSkillExp(playerName, skillName, exp, player);
                break;
            case("DIGGING"):
                DiggingExp diggingExp = new DiggingExp(plugin);
                diggingExp.handleSkillExp(playerName, skillName, exp, player);
                break;
            case("CHOPPING"):
                ChoppingExp choppingExp = new ChoppingExp(plugin);
                choppingExp.handleSkillExp(playerName, skillName, exp, player);
                break;
            case("FARMING"):
                FarmingExp farmingExp = new FarmingExp(plugin);
                farmingExp.handleSkillExp(playerName, skillName, exp, player);
                break;
            case("FISHING"):
                FishingExp fishingExp = new FishingExp(plugin);
                fishingExp.handleSkillExp(playerName, skillName, exp, player);
                break;
            case("ARCHERY"):
                ArcheryExp archeryExp = new ArcheryExp(plugin);
                archeryExp.handleSkillExp(playerName, skillName, exp, player);
                break;
            case("COMBAT"):
                CombatExp combatExp = new CombatExp(plugin);
                combatExp.handleSkillExp(playerName, skillName, exp, player);
                break;
            case("TRIDENT"):
                TridentExp tridentExp = new TridentExp(plugin);
                tridentExp.handleSkillExp(playerName, skillName, exp, player);
                break;
        }
    }


    // vanilla exp for general skills
    // atm it will only recognise that you're level 30 after you receive exp again after hitting 30
    @EventHandler
    public void onPlayerExpChangeEvent(PlayerExpChangeEvent event) {
        Player player = event.getPlayer();
        int level = player.getLevel();

        // change later if need to re-balance
        // if level will be 30, then automatically convert to 1 general skill point and reset level/exp to 0
        if (level >= 30) {
            player.sendMessage("You have been given 1 additional General skill point!");
            String playerName = player.getPlayer().getName();

            try {
                plugin.stmt.executeUpdate(String.format("UPDATE UserSkills SET AvailablePoints = AvailablePoints + 1 WHERE Username = '%s' AND SkillName = '%s';", playerName, "GENERAL"));
            } catch (SQLException e) {
                e.printStackTrace();
            }

            player.setExp(0);
            player.setLevel(0);
        }

    }
}
