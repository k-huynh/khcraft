package org.kh.khcraft.Listeners;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.kh.khcraft.Events.EnchantmentAppliedEvent;
import org.kh.khcraft.Khcraft;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class EnchantmentListener implements Listener {
    Khcraft plugin;
    FileConfiguration config;

    public EnchantmentListener(Khcraft instance) {
        plugin = instance;
        config = plugin.getConfig();
    }

    // set enchantments when player joins
    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        setEnchantments(event.getPlayer());
    }

    // set enchantments when the selected enchantments have changed
    @EventHandler
    public void onEnchantmentAppliedEvent(EnchantmentAppliedEvent event) {
        setEnchantments(event.getPlayer());
    }

    // set enchantments when the player picks up items
    @EventHandler
    public void onPlayerPickupItemEvent(EntityPickupItemEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();

            ItemStack pickedUpItem = event.getItem().getItemStack();

            // check type of item
            List<String> toolList = getToolList(player.getName());

            for (int i = 0; i < toolList.size(); i++) {
                if (pickedUpItem.getType().toString().toLowerCase().contains(toolList.get(i))) {
                    enchantTool(player.getName(), toolList.get(i), pickedUpItem);
                }
            }
        }
    }

    // ensure enchantments are set when a player closes their inventory
    @EventHandler
    public void onInventoryCloseEvent(InventoryCloseEvent event) {
        if (event.getPlayer() instanceof Player){
            Player player = (Player) event.getPlayer();
            setEnchantments(player);
        }
    }

    public void removeAllEnchantmentsFromItem(ItemStack enchantedItem) {
        // remove already applied enchants
        Map<Enchantment, Integer> appliedEnchantments = enchantedItem.getEnchantments();

        for (Map.Entry<Enchantment, Integer> entry : appliedEnchantments.entrySet()) {
            enchantedItem.removeEnchantment(entry.getKey());
        }

    }

    public void setEnchantments(Player player) {
        PlayerInventory playerInventory = player.getInventory();
        ItemStack[] inventoryContents = playerInventory.getContents();
        List<String> toolList = getToolList(player.getName());

        System.out.printf("Trying to set enchantments on inventory. Inventorycontents length: %d, toollist size: %d\n", inventoryContents.length, toolList.size());

        // iterate through items in inventory; check if the item is a tool; if so, then remove all enchants from it and
        // then try enchant if it's part of toollist
        for (int i = 0; i < inventoryContents.length; i++) {
            if (inventoryContents[i] != null) {
                String toolName = inventoryContents[i].getType().toString().toLowerCase();
                String sanitisedToolName = toolName;
                // if there's a _ in the name, then get the last 'word'; else just use the whole word
                if (toolName.contains("_")) {
                    if (!(toolName.equals("flint_and_steel") || toolName.equals("carrot_on_a_stick") || toolName.equals("warped_fungus_on_a_stick"))) {
                        int underscoreIndex = toolName.lastIndexOf("_");
                        sanitisedToolName = toolName.substring(underscoreIndex + 1);
                    }
                }

//                System.out.printf("Considering %s (%s)\n", sanitisedToolName, toolName);

                // check if it's a tool
                if (getEnchantableToolsList().contains(sanitisedToolName)) {
//                    System.out.printf("%s is a tool! removing enchantments\n", sanitisedToolName);
                    // remove enchants
                    removeAllEnchantmentsFromItem(inventoryContents[i]);
                }

                // check if the tool is in toollist
                if (toolList.contains(sanitisedToolName)) {
//                    System.out.printf("%s is in toolList! adding enchantments\n", sanitisedToolName);
                    // try enchant
                    enchantTool(player.getName(), sanitisedToolName, inventoryContents[i]);
                }
            }
        }
    }

    // gets tools that the player has enchants for
    public List<String> getToolList(String playerName) {
        List<String> toolList = new ArrayList<String>();
        try {
            ResultSet enchantedToolsRS = plugin.stmt.executeQuery(String.format("SELECT DISTINCT Equipment FROM UserEnchantments WHERE Username = '%s'", playerName));

            // get list of tools that need enchanting enabled for this player from the database
            while (enchantedToolsRS.next()) {
                toolList.add(enchantedToolsRS.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return toolList;
    }

    // gets all possible enchantable tools
    public List<String> getEnchantableToolsList() {
        List<String> enchantableTools = Arrays.asList("pickaxe", "shovel", "axe", "hoe", "bow", "crossbow", "trident",
                "sword", "fishing_rod", "shield", "elytra", "flint_and_steel", "carrot_on_a_stick", "warped_fungus_on_a_stick",
                "helmet", "chestplate", "leggings", "boots");

        return enchantableTools;
    }

    public void enchantTool(String playerName, String toolName, ItemStack tool) {
//        System.out.printf("attempting to enchant %s\n", tool.getType().toString());
        try {
            // gets the enchantments from the database
            ResultSet enchantments = plugin.stmt.executeQuery(String.format("SELECT EnchantmentName, EnchantmentLevel FROM UserEnchantments WHERE Username = '%s' AND Equipment = '%s' AND Enabled = 1;", playerName, toolName));

            // remove already applied enchants
            removeAllEnchantmentsFromItem(tool);

            while (enchantments.next()) {
                // apply it to the tool
//                System.out.printf("enchantment: %s\n", enchantments.getString(1));

                // get enchantment
                tool.addUnsafeEnchantment(getEnchantmentFromString(enchantments.getString(1)), enchantments.getInt(2));
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Enchantment getEnchantmentFromString(String enchantmentName) {
        for (Enchantment value : Enchantment.values()) {
            // get vanilla minecraft name, then replace spaces with underscores and make all lower case
            String vanilla = config.getString(String.format("skills.enchantments.%s", enchantmentName));
            String sanitisedVanilla = vanilla.replaceAll(" ", "_").toLowerCase();

            if (value.equals((Enchantment) new EnchantmentWrapper(sanitisedVanilla))) {
                return value;
            }
        }
        return null;
    }
}
