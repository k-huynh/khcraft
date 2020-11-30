package org.kh.khcraft;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.ItemStack;
import sun.jvm.hotspot.runtime.BasicLock;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class SkillsListener implements Listener {
    Khcraft plugin;
    Map<String, Object> blockExpMap;
    List<String> silkTouchExceptions;
    List<String> tillableList;

    public SkillsListener(Khcraft instance){
        plugin = instance;
        blockExpMap = plugin.getConfig().getConfigurationSection("skills.blocks").getValues(false);
        silkTouchExceptions = plugin.getConfig().getStringList("skills.silkTouch");
        tillableList = plugin.getConfig().getStringList("skills.tillableList");
    }

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        // get player name
        String playerName = player.getPlayerProfile().getName();

        // check if the player is already in the database; if not, then add them alongside default entries for skills
        try {
            ResultSet DBSearchRS = plugin.stmt.executeQuery(String.format("SELECT * FROM Users WHERE Username = '%s'", playerName));

            // if there are no more rows, then that means the username was not found
            if (!DBSearchRS.next()) {
                // add user to Users
                String addUserQuery = String.format("INSERT INTO Users (Username) VALUES ('%s')", playerName);
                plugin.stmt.executeUpdate(addUserQuery);

                // add userskills to UserSkills
                // get list of skills from config
                List<String> skillList = plugin.getConfig().getStringList("skills.list");

                for (int i = 0; i < skillList.size(); i++) {
                    String addUserSkillQuery = String.format("INSERT INTO UserSkills (Username, SkillName) VALUES ('%s', '%s')", playerName, skillList.get(i));
                    plugin.stmt.executeUpdate(addUserSkillQuery);
                }
            }

        }
        catch( SQLException e) {
            e.printStackTrace();
        }
    }

    @EventHandler
    public void onPlayerBlockBreakEvent(BlockBreakEvent event) {
        // get tool used to break the block (main hand item)
        Player player = event.getPlayer();
        PlayerInventory playerInventory = player.getInventory();
        ItemStack mainItem = playerInventory.getItemInMainHand();
        String toolName = mainItem.getType().toString();

        // get the block being broken
        Block block = event.getBlock();
        Material blockMaterial = block.getType();

        double exp = 1;

        // using pickaxe
        if (toolName.contains("_PICKAXE")) {
            // get xp based on block broken
            if (blockExpMap.containsKey(blockMaterial.toString())) {
                // check if using silk touch; if not, then use the special exp
                if (!mainItem.containsEnchantment(Enchantment.SILK_TOUCH)){
                    exp = Double.parseDouble(blockExpMap.get(blockMaterial.toString()).toString());
                }
                // if using silk touch, then use special exp if not an exception block
                else {
                    if (!silkTouchExceptions.contains(blockMaterial.toString())) {
                        exp = Double.parseDouble(blockExpMap.get(blockMaterial.toString()).toString());
                    }
                }
            }

            // give xp to mining skill
            // get player name
            String playerName = player.getPlayerProfile().getName();

            addToSkillExp(exp, "MINING", playerName);
        }

        // using axe
        else if (toolName.contains("_AXE")) {
            // get xp based on block broken
            exp = 1;

            // give xp to chopping skill
            // get player name
            String playerName = player.getPlayerProfile().getName();

            addToSkillExp(exp, "CHOPPING", playerName);

        }
        // using shovel
        else if (toolName.contains("_SHOVEL")) {
            // get xp based on block broken
            exp = 1;

            // give xp to digging skill
            // get player name
            String playerName = player.getPlayerProfile().getName();

            addToSkillExp(exp, "DIGGING", playerName);
        }
        // using hoe
        else if (toolName.contains("_HOE")) {
            // get xp based on block broken
            exp = 1;

            // give xp to farming skill
            // get player name
            String playerName = player.getPlayerProfile().getName();

            addToSkillExp(exp, "FARMING", playerName);
        }

    }

    // need to use this to check if hoes are being used to till ground, or axes are being used to strip wood
    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent event){

        // get tool used to interact with the block (main hand item)
        Player player = event.getPlayer();
        PlayerInventory playerInventory = player.getInventory();
        ItemStack mainItem = playerInventory.getItemInMainHand();
        String toolName = mainItem.getType().toString();

        // get the block being interacted with
        Block block = event.getClickedBlock();

        // check if using hoe
        if (toolName.contains("_HOE")){
            // check if it's tillable
            if (tillableList.contains(block.getType().toString())){
                double exp = 0.2;

                // give xp to farming skill
                // get player name
                String playerName = player.getPlayerProfile().getName();

                addToSkillExp(exp, "FARMING", playerName);

            }

        }

        // using axe
        else if (toolName.contains("_AXE")){
            // check if it's 'wood' or 'log' block and has not been stripped yet
            if ((block.getType().toString().contains("_LOG") || block.getType().toString().contains("_WOOD"))
                && !block.getType().toString().contains("STRIPPED")){
                double exp = 0.2;

                // give xp to chopping skill
                // get player name
                String playerName = player.getPlayerProfile().getName();

                addToSkillExp(exp, "CHOPPING", playerName);
            }
        }


    }

    public void addToSkillExp(double exp, String skillName, String playerName){
        // give xp to chopping skill
        try {
            // set SQL query
            String updateDBExpQuery = String.format("UPDATE UserSkills SET XP = XP + '%f' WHERE Username = '%s' AND SkillName = '%s'", exp, playerName, skillName);

            // execute SQL query
            plugin.stmt.execute(updateDBExpQuery);

            // TODO: set custom event for when you gain enough xp to level up
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
