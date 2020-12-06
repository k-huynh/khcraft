package org.kh.khcraft.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Trident;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.projectiles.ProjectileSource;
import org.kh.khcraft.Events.SkillExpEvent;
import org.kh.khcraft.Khcraft;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class SkillsListener implements Listener {
    Khcraft plugin;
    Map<String, Object> miningExpMap;
    Map<String, Object> choppingExpMap;
    Map<String, Object> diggingExpMap;
    Map<String, Object> farmingExpMap;
    List<String> silkTouchExceptions;
    List<String> tillableList;
    List<String> cropsList;

    public SkillsListener(Khcraft instance){
        plugin = instance;
        miningExpMap = plugin.getConfig().getConfigurationSection("skills.mining.blocks").getValues(false);
        choppingExpMap = plugin.getConfig().getConfigurationSection("skills.chopping.blocks").getValues(false);
        diggingExpMap = plugin.getConfig().getConfigurationSection("skills.digging.blocks").getValues(false);
        farmingExpMap = plugin.getConfig().getConfigurationSection("skills.farming.blocks").getValues(false);

        silkTouchExceptions = plugin.getConfig().getStringList("skills.mining.silkTouch");
        tillableList = plugin.getConfig().getStringList("skills.farming.tillableList");
        cropsList = plugin.getConfig().getStringList("skills.farming.cropsList");
    }

    // initialise rows in db for when a player first joins
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

    // used for when players break certain blocks with certain tools
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

        double exp = 0;

        // using pickaxe
        if (toolName.contains("_PICKAXE")) {
            // get xp based on block broken
            if (miningExpMap.containsKey(blockMaterial.toString())) {
                // check if using silk touch; if not, then use the special exp
                if (!mainItem.containsEnchantment(Enchantment.SILK_TOUCH)){
                    exp = Double.parseDouble(miningExpMap.get(blockMaterial.toString()).toString());
                }
                // if using silk touch, then use special exp if not an exception block
                else {
                    if (!silkTouchExceptions.contains(blockMaterial.toString())) {
                        exp = Double.parseDouble(miningExpMap.get(blockMaterial.toString()).toString());
                    }
                    else {
                        exp = 1.0;
                    }
                }
            }

            // give xp to mining skill
            // get player name
            String playerName = player.getPlayerProfile().getName();

            addToSkillExp(exp, "MINING", playerName);
            SkillExpEvent skillExpEvent = new SkillExpEvent("MINING", playerName, player, exp);
            Bukkit.getPluginManager().callEvent(skillExpEvent);
        }

        // using axe
        else if (toolName.contains("_AXE")) {
            // get xp based on block broken
            if (choppingExpMap.containsKey(blockMaterial.toString())) {
                exp = Double.parseDouble(choppingExpMap.get(blockMaterial.toString()).toString());
            }

            // give xp to chopping skill
            // get player name
            String playerName = player.getPlayerProfile().getName();

            addToSkillExp(exp, "CHOPPING", playerName);
            SkillExpEvent skillExpEvent = new SkillExpEvent("CHOPPING", playerName, player, exp);
            Bukkit.getPluginManager().callEvent(skillExpEvent);

        }
        // using shovel
        else if (toolName.contains("_SHOVEL")) {
            // get xp based on block broken
            if (diggingExpMap.containsKey(blockMaterial.toString())) {
                exp = Double.parseDouble(diggingExpMap.get(blockMaterial.toString()).toString());
            }

            // give xp to digging skill
            // get player name
            String playerName = player.getPlayerProfile().getName();

            addToSkillExp(exp, "DIGGING", playerName);
            SkillExpEvent skillExpEvent = new SkillExpEvent("DIGGING", playerName, player, exp);
            Bukkit.getPluginManager().callEvent(skillExpEvent);
        }
        // using hoe
        else if (toolName.contains("_HOE")) {
            // get xp based on block broken
            if (farmingExpMap.containsKey(blockMaterial.toString())) {
                exp = Double.parseDouble(farmingExpMap.get(blockMaterial.toString()).toString());
            }

            // if it was a crop then only give xp if age = max
            if (cropsList.contains(blockMaterial.toString())) {
                Ageable ageable = (Ageable) block.getBlockData();
                int currentAge = ageable.getAge();
                int maxAge= ageable.getMaximumAge();

                if (currentAge == maxAge) {
                    exp = 1.0;
                }

            }

            // give xp to farming skill
            // get player name
            String playerName = player.getPlayerProfile().getName();

            addToSkillExp(exp, "FARMING", playerName);
            SkillExpEvent skillExpEvent = new SkillExpEvent("FARMING", playerName, player, exp);
            Bukkit.getPluginManager().callEvent(skillExpEvent);
        }

    }

    // need to use this to check if hoes are being used to make dirt paths wit hshovels, or axes are being used to strip wood
    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent event){
        // check if it was a right click
        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && event.hasItem()) {
            // get tool used to interact with the block (main hand item)
            Player player = event.getPlayer();
            ItemStack usedItem = event.getItem();
            String usedItemName = usedItem.getType().toString();

            // get the block being interacted with
            Block block = event.getClickedBlock();

            // using axe
            if (usedItemName.contains("_AXE")) {
                // check if it's 'wood' or 'log' block and has not been stripped yet
                if ((block.getType().toString().contains("_LOG") || block.getType().toString().contains("_WOOD"))
                        && !block.getType().toString().contains("STRIPPED")){
                    double exp = 0.2;

                    // give xp to chopping skill
                    // get player name
                    String playerName = player.getPlayerProfile().getName();

                    addToSkillExp(exp, "CHOPPING", playerName);
                    SkillExpEvent skillExpEvent = new SkillExpEvent("CHOPPING", playerName, player, exp);
                    Bukkit.getPluginManager().callEvent(skillExpEvent);
                }
            }

            // using shovel
            else if (usedItemName.contains("_SHOVEL")) {
                // check if it's grass block (note that it becomes any form of dirt in 1.17)
                if (block.getType().equals(Material.GRASS_BLOCK)) {
                    double exp = 0.2;

                    // give xp to chopping skill
                    // get player name
                    String playerName = player.getPlayerProfile().getName();

                    addToSkillExp(exp, "DIGGING", playerName);
                    SkillExpEvent skillExpEvent = new SkillExpEvent("DIGGING", playerName, player, exp);
                    Bukkit.getPluginManager().callEvent(skillExpEvent);
                }
            }
        }
    }

    // used to check when a player uses a hoe to till the ground
    @EventHandler
    public void onBlockPlaceEvent(BlockPlaceEvent event) {
        // System.out.printf("Block placed: %s\n", event.getBlockPlaced().getType().toString());
        // just need to check if block placed is farmland
        if (event.getBlockPlaced().getType().equals(Material.FARMLAND)) {
            double exp = 0.2;

            // give xp to farming skill
            // get player name
            Player player = event.getPlayer();
            String playerName = player.getPlayerProfile().getName();

            addToSkillExp(exp, "FARMING", playerName);
            SkillExpEvent skillExpEvent = new SkillExpEvent("FARMING", playerName, player, exp);
            Bukkit.getPluginManager().callEvent(skillExpEvent);
        }
    }

    /*
     * COMBAT EVENTS
     */
    @EventHandler
    public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
        // check if the damage was caused by a player directly, or by a projectile thrown by a player
        Entity damager = event.getDamager();
        Player playerDamager = null;
        Double damageDealt = 0.0;
        String damageSkill = "COMBAT";

        if (damager instanceof Arrow) {
            // check if arrow was thrown by player
            Arrow arrow = (Arrow) damager;
            ProjectileSource shooter = arrow.getShooter();

            if (shooter instanceof Player) {
                playerDamager = (Player) shooter;

                // get damage dealt
                damageDealt = event.getFinalDamage();
                damageSkill = "ARCHERY";

            }
        }
        else if (damager instanceof Player) {
            // regular damage done by player melee attacks with any item
            playerDamager = (Player) damager;

            // get damage dealt
            damageDealt = event.getFinalDamage();

            // check if using trident melee by looking at item in player's main hand
            PlayerInventory inventory = playerDamager.getInventory();
            ItemStack mainItem = inventory.getItemInMainHand();

            if (mainItem.getType().equals(Material.TRIDENT)) {
                damageSkill = "TRIDENT";
            }
        }
        else if (damager instanceof Trident) {
            // check if trident was thrown by player
            Trident trident = (Trident) damager;
            ProjectileSource shooter = trident.getShooter();

            if (shooter instanceof Player) {
                 playerDamager = (Player) shooter;

                 // get damage dealt
                damageDealt = event.getFinalDamage();
                damageSkill = "TRIDENT";
            }
        }

        // add exp if indeed it was a player that caused the damage
        if (playerDamager != null) {
            String playerName = playerDamager.getPlayer().getName();
            addToSkillExp(damageDealt, damageSkill, playerName);
            SkillExpEvent skillExpEvent = new SkillExpEvent(damageSkill, playerName, playerDamager, damageDealt);
            Bukkit.getPluginManager().callEvent(skillExpEvent);
        }
    }

    /*
     * FISHING
     */

    @EventHandler
    public void onPlayerFishEvent(PlayerFishEvent event) {
        // check if catch was successful
        if (event.getState().equals(PlayerFishEvent.State.CAUGHT_FISH)) {
            double exp = 1.0;
            // get player
            Player player = event.getPlayer();
            String playerName = player.getPlayer().getName();

            addToSkillExp(exp, "FISHING", player.getPlayer().getName());
            SkillExpEvent skillExpEvent = new SkillExpEvent("FISHING", playerName, player, exp);
            Bukkit.getPluginManager().callEvent(skillExpEvent);
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
