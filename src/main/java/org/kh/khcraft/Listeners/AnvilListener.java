package org.kh.khcraft.Listeners;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.kh.khcraft.Khcraft;

public class AnvilListener implements Listener {
    Khcraft plugin;
    FileConfiguration config;

    public AnvilListener(Khcraft instance) {
        plugin = instance;
        config = plugin.getConfig();
    }

    @EventHandler
    public void onPrepareAnvilEvent (PrepareAnvilEvent event) {
        AnvilInventory anvilInventory = event.getInventory();
        int currentRepairCost = anvilInventory.getRepairCost();
        int maxCost = config.getInt("skills.repairs.maxCost");
        System.out.printf("current cost: %d; max cost: %d\n", currentRepairCost, maxCost);
        if (currentRepairCost >= maxCost) {
            // set the max allowed repair cost to integer.max since it doesn't seem to actually replace the current
            // repair cost stored in the item, just presents an alternative cost in the gui
            anvilInventory.setMaximumRepairCost(Integer.MAX_VALUE);
            System.out.printf("new cost: %d\n", maxCost);
            anvilInventory.setRepairCost(maxCost);
        }
    }
}
