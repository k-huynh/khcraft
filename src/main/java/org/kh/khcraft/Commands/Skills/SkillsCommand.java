package org.kh.khcraft.Commands.Skills;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.kh.khcraft.Khcraft;
import org.kh.khcraft.Skills.ExpHandlers.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SkillsCommand implements TabExecutor {
    Khcraft plugin;
    FileConfiguration config;

    public SkillsCommand(Khcraft instance) {
        plugin = instance;
        config = plugin.getConfig();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            String playerName = player.getName();

            String sendStr = "";

            try {
                ResultSet userSkillRS = plugin.stmt.executeQuery(String.format("SELECT XP, AvailablePoints, SkillName FROM UserSkills WHERE Username = '%s';",
                        playerName));

                while (userSkillRS.next()) {
                    // get current xp and available points for this skill
                    double currentXP = userSkillRS.getDouble(1);
                    int availablePoints = userSkillRS.getInt(2);
                    String skillName = userSkillRS.getString(3);

                    SkillExp skillExp = null;

                    switch(skillName) {
                        case("MINING"):
                            skillExp = new MiningExp(plugin);
                            break;
                        case("DIGGING"):
                            skillExp = new DiggingExp(plugin);
                            break;
                        case("CHOPPING"):
                            skillExp = new ChoppingExp(plugin);
                            break;
                        case("FARMING"):
                            skillExp = new FarmingExp(plugin);
                            break;
                        case("FISHING"):
                            skillExp = new FishingExp(plugin);
                            break;
                        case("ARCHERY"):
                            skillExp = new ArcheryExp(plugin);
                            break;
                        case("COMBAT"):
                            skillExp = new CombatExp(plugin);
                            break;
                        case("TRIDENT"):
                            skillExp = new TridentExp(plugin);
                            break;
                        case("GENERAL"):
                            sendStr = sendStr + String.format("GENERAL | Available Points: %d\n", availablePoints);
                            continue;
                    }

                    // get current level
                    double currentLevel = skillExp.getCurrentSkillLevel(currentXP);

                    // get xp required for current level
                    double requiredXPCurrent = skillExp.getExpRequired(currentLevel);

                    // get xp required for next level
                    double requiredXP = skillExp.getExpRequired(currentLevel + 1);

                    // get xp required to next level
                    double requiredXPToNext = requiredXP - requiredXPCurrent;

                    sendStr = sendStr + String.format("%s | Level: %4d, XP: %.1f/%.1f, Available Points: %d\n", skillName, (int) currentLevel, currentXP - requiredXPCurrent, requiredXPToNext, availablePoints);

                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

            // sent to player
            player.sendMessage(sendStr);

            return true;
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        // no tab completion necessary so return an empty arraylist
        return new ArrayList<String>();
    }
}
