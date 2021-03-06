package org.kh.khcraft.Commands.Skills;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.kh.khcraft.Events.EnchantmentAppliedEvent;
import org.kh.khcraft.Khcraft;
import org.kh.khcraft.Skills.ExpHandlers.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public abstract class SkillCommands implements TabExecutor {
    Khcraft plugin;
    FileConfiguration config;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // check if sender is a player
        if (sender instanceof Player) {
            Player player = (Player) sender;

            // select or upgrade
            // need to check if enough arguments have been supplied
            if (args.length > 0) {
                if (args[0].equalsIgnoreCase("select")) {
                    // both tool and enchantment supplied
                    if (args.length > 2) {
                        String toolName = args[1];
                        String enchantmentName = args[2];

                        // if the enchantment level has been supplied as well; otherwise, default to 1
                        int enchantmentLevel = 1;
                        if (args.length == 4) {
                            enchantmentLevel = Integer.parseInt(args[3]);
                        }

                        // enable enchants in db
                        if (enableEnchants(player.getName(), command.getName(), toolName, enchantmentName, enchantmentLevel)) {
                            // call EnchantmentAppliedEvent so tools in inventory will be updated with selected enchantments
                            EnchantmentAppliedEvent enchantmentAppliedEvent = new EnchantmentAppliedEvent(player.getName(), player);
                            Bukkit.getPluginManager().callEvent(enchantmentAppliedEvent);

                            // tell the sender that the enchantment was applied
                            sender.sendMessage(String.format("%s applied to %s successfully!", enchantmentName, toolName));
                            System.out.printf("%s applied %s %d to %s\n", player.getName(), enchantmentName, enchantmentLevel, toolName);
                        }
                        else {
                            sender.sendMessage(String.format("Unable to enchant %s with %s :(\n", toolName, enchantmentName));
                            System.out.printf("%s was unable to enchant %s with %s %d\n", player.getName(), toolName, enchantmentName, enchantmentLevel);
                        }


                        return true;

                    }
                }
                else if (args[0].equalsIgnoreCase("upgrade")) {
                    // if no tool/enchantments specified, then we want to list all the available options
                    if (args.length == 1) {
                        // get list of tools associated with this skill
                        List<String> skillTools = getSkillTools(command.getName());

                        // for each tool, get list of upgradeable enchantments and send result
                        for (int i = 0; i < skillTools.size(); i++) {
                            List<String> toolUpgrades = getUpgradeableEnchantments(player.getName(), skillTools.get(i), command.getName());

                            String sendStr = String.format("%s: ", skillTools.get(i));
                            for (int j = 0; j < toolUpgrades.size() - 1; j++) {
                                sendStr = sendStr + toolUpgrades.get(j) + ", ";
                            }
                            sendStr = sendStr + toolUpgrades.get(toolUpgrades.size() - 1);
                            sender.sendMessage(sendStr);

                        }
                        return true;
                    }
                    // if a tool has been specified, then we want to list all the available options for that tool
                    else if (args.length == 2) {
                        // get list of upgradeable enchantments for this tool
                        List<String> toolUpgrades = getUpgradeableEnchantments(player.getName(), args[1], command.getName());

                        String sendStr = String.format("%s: ", args[1]);
                        for (int i = 0; i < toolUpgrades.size() - 1; i++) {
                            sendStr = sendStr + toolUpgrades.get(i) + ", ";
                        }
                        sendStr = sendStr + toolUpgrades.get(toolUpgrades.size() - 1);
                        sender.sendMessage(sendStr);
                        return true;
                    }
                    // if a tool and enchantment has been specified, then we want to make it available to the sender
                    else if (args.length == 3) {
                        // send message saying success if we were able to give them the enchantment
                        if (givePlayerEnchantment(player.getName(), command.getName(), args[1], args[2])) {
                            sender.sendMessage(String.format("%s for %s successfully learned!", args[2], args[1]));
                            System.out.printf("%s learned %s for %s\n", player.getName(), args[2], args[1]);
                            return true;
                        }
                        // send message saying they couldn't do it if we couldn't
                        else {
                            sender.sendMessage(String.format("Could not learn %s for %s :(", args[2], args[1]));
                            System.out.printf("%s was unable to learn %s for %s\n", player.getName(), args[2], args[1]);
                            return true;
                        }
                    }
                }
                else if (args[0].equalsIgnoreCase("disable")) {
                    // 'disable' and tool specified (and skill)
                    if (args.length == 2) {
                        disableAllEnchants(player.getName(), command.getName(), args[1]);

                        // call EnchantmentAppliedEvent so tools in inventory will be updated with selected enchantments
                        EnchantmentAppliedEvent enchantmentAppliedEvent = new EnchantmentAppliedEvent(sender.getName(), player);
                        Bukkit.getPluginManager().callEvent(enchantmentAppliedEvent);

                        // tell the sender that the enchantment was applied
                        sender.sendMessage(String.format("Enchantments for %s disabled successfully!", args[1]));
                        System.out.printf("%s disabled enchantments for %s\n", player.getName(), args[1]);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            // selecting between select or upgrade
            if (args.length == 1) {
                List<String> skillOptions = Arrays.asList("select", "upgrade", "disable");

                return skillOptions;
            }
            // select which tool to select enchantments/upgrades for
            else if (args.length == 2) {
                return getSkillTools(command.getName());
            }
            // if 'select', then get list of unlocked enchantments; if 'upgrade', then get list of unlockable enchantments
            else if (args.length == 3) {
                if (args[0].equalsIgnoreCase("select")) {
                    return getAvailableEnchantmentNames(player.getName(), args[1], command.getName());
                }
                else if (args[0].equalsIgnoreCase("upgrade")) {
                    return getUpgradeableEnchantments(player.getName(), args[1], command.getName());
                }
            }
            // if 'select' and an enchantment has been specified, then list the available levels
            else if (args.length == 4) {
                if (args[0].equalsIgnoreCase("select")) {
                    return getAvailableEnchantmentLevels(player.getName(), args[1], args[2]);
                }
            }
        }
        // if not one of the above options, then return an empty arraylist
        return new ArrayList<String>();
    }

    public boolean enableEnchants(String playerName, String skillName, String toolName, String enchantmentName, int enchantmentLevel) {
        // disable all entries (all levels) of this enchantment in userenchantments first, then enabled to 1
//        System.out.printf("trying to enable %s %d in db \n", enchantmentName, enchantmentLevel);
        // check if the player has upgraded this enchantment yet

        try {
            ResultSet enabledRS = plugin.stmt.executeQuery(String.format("SELECT * FROM UserEnchantments WHERE Username = '%s' AND SkillName = '%s' AND Equipment = '%s' AND EnchantmentName = '%s' AND EnchantmentLevel = '%s';",
                    playerName, skillName, toolName, enchantmentName, enchantmentLevel));

            if (enabledRS.next()) {
                disableEnchant(playerName, skillName, toolName, enchantmentName);
                plugin.stmt.executeUpdate(String.format("UPDATE UserEnchantments SET Enabled = 1 WHERE Username = '%s' AND SkillName = '%s' AND Equipment = '%s' AND EnchantmentName = '%s' AND EnchantmentLevel = '%d';",
                        playerName, skillName, toolName, enchantmentName, enchantmentLevel));
            }
            else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        // get any non-compatible enchantments from config
        List<String> incompatibleEnchantments = getIncompatibleEnchantments(toolName, enchantmentName);

        // find entries for any enabled, non-compatible enchantments and set enabled to 0
        for (int i = 0; i < incompatibleEnchantments.size(); i++) {
            // check if enabled; if so, then disable
            try {
                ResultSet incompatibleRS = plugin.stmt.executeQuery(String.format("SELECT Enabled FROM UserEnchantments WHERE Username = '%s' AND SkillName = '%s' AND Equipment = '%s' AND EnchantmentName = '%s';",
                        playerName, skillName, toolName, incompatibleEnchantments.get(i)));

                while (incompatibleRS.next()) {
                    if (incompatibleRS.getInt(1) == 1) {
//                        System.out.printf("%s is incompatible; disabling\n", incompatibleEnchantments.get(i));
                        disableEnchant(playerName, skillName, toolName, incompatibleEnchantments.get(i));
                        return true;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return true;

    }

    public void disableEnchant(String playerName, String skillName, String toolName, String enchantmentName) {
        // find entry in userenchantments and set enabled to 0
        try {
            plugin.stmt.executeUpdate(String.format("UPDATE UserEnchantments SET Enabled = 0 WHERE Username = '%s' AND SkillName = '%s' AND Equipment = '%s' AND EnchantmentName = '%s';",
                        playerName, skillName, toolName, enchantmentName));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void disableAllEnchants(String playerName, String skillName, String toolName) {
        // get selected enchants
        List<String> selectedEnchants = new ArrayList<String>();
        try {
            ResultSet selectedEnchantsRS = plugin.stmt.executeQuery(String.format("SELECT EnchantmentName FROM UserEnchantments WHERE Username = '%s' AND SkillName = '%s' AND Equipment = '%s' AND Enabled = 1;",
                    playerName, skillName, toolName));

            while (selectedEnchantsRS.next()) {
                selectedEnchants.add(selectedEnchantsRS.getString(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // remove each one
        for (int i = 0; i < selectedEnchants.size(); i++) {
            disableEnchant(playerName, skillName, toolName, selectedEnchants.get(i));
        }
    }

    public boolean givePlayerEnchantment(String playerName, String skillName, String toolName, String enchantmentName) {
        // ensure they meet the requirements for the enchantment (sufficient skill points and skill level)
        // get upgradeable enchantments
        List<String> upgradeableEnchantments = getPossibleUpgrades(playerName, toolName, skillName);
//
//        for (int i = 0; i < upgradeableEnchantments.size(); i++) {
//            System.out.println(upgradeableEnchantments.get(i));
//        }

        // check if this enchantment is on the list; if so, then add to userenchantments and deduct skill points, then return true
        if (upgradeableEnchantments.contains(enchantmentName)) {
            // get enchantment level (default 1; else the current max level + 1)
//            System.out.println("Enchantment name: " + enchantmentName);

            try {
                // get just the enchantment name
                int underscoreIndex = enchantmentName.lastIndexOf("_");
                String sanitisedEnchantmentName = enchantmentName.substring(0, underscoreIndex);

                ResultSet enchantmentRS = plugin.stmt.executeQuery(String.format("SELECT MAX(EnchantmentLevel) FROM UserEnchantments WHERE Username = '%s' AND SkillName = '%s' AND Equipment = '%s' AND EnchantmentName = '%s';",
                        playerName, skillName, toolName, sanitisedEnchantmentName));

                int enchantmentLevel = 1;

                if (enchantmentRS.next()) {
                    int maxLevel = enchantmentRS.getInt(1);
                    enchantmentLevel = maxLevel + 1;
                }

                plugin.stmt.executeUpdate(String.format("INSERT INTO UserEnchantments (Username, EnchantmentName, EnchantmentLevel, SkillName, Equipment) VALUES "
                        + "('%s', '%s', '%d', '%s', '%s');", playerName, sanitisedEnchantmentName, enchantmentLevel, skillName, toolName));

                // deduct skill points
                int reqPoints = getReqSkillPoints(skillName, toolName, sanitisedEnchantmentName);

                plugin.stmt.executeUpdate(String.format("UPDATE UserSkills SET AvailablePoints = AvailablePoints - %d WHERE Username = '%s' AND SkillName = '%s';",
                        reqPoints, playerName, skillName));


                return true;

            } catch (SQLException e) {
                e.printStackTrace();
            }


        }
        // else return false
        return false;
    }

    public List<String> getAvailableEnchantmentNames(String playerName, String toolName, String skillName) {
        // use getAvailableEnchantments and just take the first part of every string
        List<String> availableEnchantmentNames = new ArrayList<String>();
        List<String> availableEnchantments = getAvailableEnchantments(playerName, toolName, skillName);
        for (int i = 0; i < availableEnchantments.size(); i++) {
            String enchantStr = availableEnchantments.get(i);

            int underscoreIndex = enchantStr.lastIndexOf("_");
            availableEnchantmentNames.add(enchantStr.substring(0, underscoreIndex));
        }

        return availableEnchantmentNames;
    }

    public List<String> getAvailableEnchantments(String playerName, String toolName, String skillName) {
        // list max level of all available enchantments for this tool from database i.e. "Efficiency 5"
        List<String> availableEnchantments = new ArrayList<String>();
        try {
            ResultSet availableRS = plugin.stmt.executeQuery(String.format("SELECT EnchantmentName, MAX(EnchantmentLevel) as EnchantmentLevel FROM UserEnchantments WHERE Username = '%s' AND Equipment = '%s' AND SkillName = '%s' GROUP BY EnchantmentName;",
                    playerName, toolName, skillName));

            while (availableRS.next()){
                availableEnchantments.add(String.format("%s_%d", availableRS.getString(1), availableRS.getInt(2)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return availableEnchantments;
    }

    public int getCurrentMaxEnchantmentLevel(String playerName, String toolName, String skillName, String enchantmentName) {
        try {
            ResultSet enchantmentRS = plugin.stmt.executeQuery(String.format("SELECT MAX(EnchantmentLevel) FROM UserEnchantments WHERE Username = '%s' AND Equipment = '%s' AND SkillName = '%s' AND EnchantmentName = '%s';",
                    playerName, toolName, skillName, enchantmentName));

            if (enchantmentRS.next()) {
                return enchantmentRS.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public int getMaxEnchantmentLevel(String toolName, String skillName, String enchantmentName) {
        // consult config
        int maxLevel = config.getInt(String.format("skills.%s.max.%s.%s", skillName.toLowerCase(), toolName, enchantmentName));

        // if it wasn't found in the config, then it will return 0
        return maxLevel;
    }

    public List<String> getUpgradeableEnchantments(String playerName, String toolName, String skillName) {
        // check database for owned enchantments and check config for possible upgrades
        // return enchantment name, enchantment level, required level, and skill points required in a single string
        List<String> upgradeableEnchantments = new ArrayList<String>();

        // get max level of current enchantments using getAvailableEnchantments from super
        List<String> maxEnchantsOwned = getAvailableEnchantments(playerName, toolName, skillName);

        List<String> enchantsOwned = new ArrayList<String>();

        // get unique list of owned enchants by splitting maxenchantsowned
        for (int i = 0; i < maxEnchantsOwned.size(); i++) {
            String enchantStr = maxEnchantsOwned.get(i);

            int underscoreIndex = enchantStr.lastIndexOf("_");
            String enchantName = enchantStr.substring(0, underscoreIndex);

            enchantsOwned.add(enchantName);
        }

        // check config for any enchantments not selected + the next level for the current enchantments
        List<String> allEnchants = config.getStringList(String.format("skills.%s.enchantments.%s", skillName, toolName));

        // for any in common between the ones owned and all possible enchants, get the enchantment and level + 1
        for (int i = 0; i < allEnchants.size(); i++) {
            if (enchantsOwned.contains(allEnchants.get(i))) {
                // get enchantment name and level + 1
                int maxLevel = getCurrentMaxEnchantmentLevel(playerName, toolName, skillName, allEnchants.get(i));
                int maxAllowedLevel = getMaxEnchantmentLevel(toolName, skillName, allEnchants.get(i));

                // check if this level is possible and does not exceed the max (which is 0 if no max)
                if ((maxLevel + 1 <= maxAllowedLevel) || maxAllowedLevel == 0) {
                    upgradeableEnchantments.add(String.format("%s_%d", allEnchants.get(i), maxLevel + 1));
                }
            }
            // if it's not owned, then we add the enchantment name and level
            else {
                upgradeableEnchantments.add(String.format("%s_%d", allEnchants.get(i), 1));
            }
        }

        return upgradeableEnchantments;
    }


    public List<String> getPossibleUpgrades(String playerName, String toolName, String skillName) {
        // use getUpgradeableEnchantments to get the overall list including level and skill points required
        List<String> upgradeableEnchants = getUpgradeableEnchantments(playerName, toolName, skillName);
        List<String> possibleUpgrades = new ArrayList<String>();

        // parse each string to get the enchantment, enchantment level, required level, required skill points
        for (int i = 0; i < upgradeableEnchants.size(); i++) {
            // get required level and skill points from config
            String enchantStr = upgradeableEnchants.get(i);

            int underscoreIndex = enchantStr.lastIndexOf("_");
            String enchantmentName = enchantStr.substring(0, underscoreIndex);


            int minLevel = config.getInt(String.format("skills.%s.requirements.%s.%s.level", skillName, toolName, enchantmentName));
            int reqPoints = getReqSkillPoints(skillName, toolName, enchantmentName);

            int currentXP = 0;
            int availablePoints = 0;

            // get current skill level and available points
            try {
                ResultSet skillRS = plugin.stmt.executeQuery(String.format("SELECT XP, AvailablePoints FROM UserSkills WHERE Username = '%s' AND SkillName = '%s';", playerName, skillName));

                if (skillRS.next()) {
                    currentXP = skillRS.getInt(1);
                    availablePoints = skillRS.getInt(2);
                }
//                System.out.printf("xp: %d, availablepoints: %d\n", currentXP, availablePoints);

            } catch (SQLException e) {
                e.printStackTrace();
            }

            SkillExp skillExp = null;

            switch(skillName.toUpperCase()) {
                case ("MINING"):
                    skillExp = new MiningExp(plugin);
                    break;
                case ("DIGGING"):
                    skillExp = new DiggingExp(plugin);
                    break;
                case ("CHOPPING"):
                    skillExp = new ChoppingExp(plugin);
                    break;
                case ("FARMING"):
                    skillExp = new FarmingExp(plugin);
                    break;
                case ("FISHING"):
                    skillExp = new FishingExp(plugin);
                    break;
                case ("ARCHERY"):
                    skillExp = new ArcheryExp(plugin);
                    break;
                case ("COMBAT"):
                    skillExp = new CombatExp(plugin);
                    break;
                case ("TRIDENT"):
                    skillExp = new TridentExp(plugin);
                    break;
            }

            if (skillExp != null) {
                double currentLevel = skillExp.getCurrentSkillLevel(currentXP);

//                System.out.println("currentlevel: " + currentLevel);

                // if our skill level exceeds the minimum level, and our available skill points exceeds the required amount,
                // then list it as an option (enchantment + enchantment level)
                if ((currentLevel >= minLevel) && (availablePoints >= reqPoints)) {
                    possibleUpgrades.add(upgradeableEnchants.get(i));
                }
            }
            // otherwise, we're dealing with the 'GENERAL' skill
            else {
                if (availablePoints >= reqPoints) {
                    possibleUpgrades.add(upgradeableEnchants.get(i));
                }
            }

        }
        return possibleUpgrades;
    }

    public List<String> getAvailableEnchantmentLevels(String playerName, String toolName, String enchantmentName) {
        // list out all the levels up until the max available from database

        List<String> availableLevels = new ArrayList<String>();

        try {
            ResultSet maxEnchantmentLevelRS = plugin.stmt.executeQuery(String.format("SELECT MAX(EnchantmentLevel) FROM UserEnchantments WHERE Username = '%s' AND Equipment = '%s' AND EnchantmentName = '%s';",
                    playerName, toolName, enchantmentName));

            maxEnchantmentLevelRS.next();

            int maxLevel = maxEnchantmentLevelRS.getInt(1);

            for (int i = 1; i <= maxLevel; i++) {
                availableLevels.add(String.format("%d", i));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // TODO do i need to handle the case where the resultset is empty??

        return availableLevels;
    }

    public int getReqSkillPoints(String skillName, String toolName, String enchantmentName) {
        int reqPoints = config.getInt(String.format("skills.%s.requirements.%s.%s.points", skillName, toolName, enchantmentName));

        // if there isnt an entry for it as a requirement, then the default point requirement should be 1.
        // similarly, no min level means you can get it at any level
        if (reqPoints == 0) {
            reqPoints = 1;
        }

        return reqPoints;
    }

    public List<String> getSkillTools(String skillName) {
        return config.getStringList(String.format("skills.%s.equipment", skillName.toLowerCase()));
    }

    public String getVanillaName(String enchantmentName) {
        return config.getString(String.format("skills.enchantments.%s", enchantmentName));
    }

    public List<String> getVanillaNames(List<String> enchantmentList) {
        List<String> vanillaNames = new ArrayList<String>();
        for (int i = 0; i < enchantmentList.size(); i++) {
            vanillaNames.add(getVanillaName(enchantmentList.get(i)));
        }

        return vanillaNames;
    }

    public List<String> getVanillaNamesUpgradeable(List<String> dirtyEnchantmentList) {
        // when using getUpgradeableEnchantments, it returns it in the form enchantmentName_nextLevel; we want to take
        // take that list, and change it to vanillaEnchantmentName nextLevel

        List<String> vanillaNames = new ArrayList<String>();

        for (int i = 0; i < dirtyEnchantmentList.size(); i++) {
            // extract enchantment name and level
            int underscoreIndex = dirtyEnchantmentList.get(i).lastIndexOf("_");
            String enchantmentName = dirtyEnchantmentList.get(i).substring(0, underscoreIndex);
            String enchantmentLevel = dirtyEnchantmentList.get(i).substring(underscoreIndex + 1);

            // convert enchantmentName to vanilla name
            String vanillaName = getVanillaName(enchantmentName);

            // add to vanillaName
            vanillaNames.add(String.format("%s_%s", vanillaName, enchantmentLevel));

        }

        return vanillaNames;
    }

    public abstract List<String> getIncompatibleEnchantments(String toolName, String enchantmentName);
}
