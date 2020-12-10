package org.kh.khcraft.Listeners;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.PlayerInventory;
import org.kh.khcraft.Khcraft;

import java.io.IOException;
import java.util.List;

public class HoverListener implements Listener {
    FileConfiguration config;
    Khcraft plugin;

    public HoverListener(Khcraft instance) {
        plugin = instance;
        config = plugin.getConfig();
    }

    @EventHandler
    public void onPlayerSwapHandItemsEvent(PlayerSwapHandItemsEvent event) throws IOException {
        ItemStack offItem = event.getOffHandItem();
        if (offItem != null) {
            if (checkOffHand(offItem) && elytraEquipped(event.getPlayer())) {
//                System.out.printf("Offhand %s; true\n", offItem.getType());
                setHover(event.getPlayer(), true);
            } else {
//                System.out.printf("Offhand %s; false\n", offItem.getType());
                setHover(event.getPlayer(), false);
            }
        }
    }

    @EventHandler
    public void onPlayerInventoryCloseEvent(InventoryCloseEvent event) throws IOException {
        // check what's in offhand
        HumanEntity humanPlayer = event.getPlayer();
        PlayerInventory playerInventory = humanPlayer.getInventory();

        if (humanPlayer instanceof Player){
            Player player = (Player)humanPlayer;

            ItemStack offItem = playerInventory.getItemInOffHand();

            if (offItem != null) {
                if (checkOffHand(offItem) && elytraEquipped(player)) {
//                    System.out.printf("Invent close; offhand %s; true\n", offItem.getType());
                    setHover(player, true);
                } else {
//                    System.out.printf("invent close; offhand: %s; false\n", offItem.getType());
                    setHover(player, false);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) throws IOException {
        Player player = event.getPlayer();
        PlayerInventory playerInventory = player.getInventory();

        ItemStack offItem = playerInventory.getItemInOffHand();

        if (offItem != null) {
            if (checkOffHand(offItem) && elytraEquipped(player)) {
//                System.out.printf("player join; offhand %s; true\n", offItem.getType());
                setHover(player, true);
            } else {
//                System.out.printf("player join; offhand: %s; false\n", offItem.getType());
                setHover(player, false);
            }
        }
    }

    // need to check if they are still 'eligible' for hovering if they change their armour
    @EventHandler
    public void onArmourChangeEvent(PlayerArmorChangeEvent event) {
        Player player = event.getPlayer();
        PlayerInventory playerInventory = player.getInventory();

        ItemStack offItem = playerInventory.getItemInOffHand();

        if (offItem != null) {
            if (checkOffHand(offItem) && elytraEquipped(player)) {
                setHover(player, true);
            } else {
                setHover(player, false);
            }
        }
    }

    // returns true if the offhand contains the hover item
    public boolean checkOffHand(ItemStack offItem) {
        String hoverBase = config.getString("items.HOVER_ITEM.vanillaBase");
        int cmd = config.getInt("items.HOVER_ITEM.cmd");
        if (offItem.getType().toString().equalsIgnoreCase(hoverBase)){
            // check custom model data tag
            if (offItem.getItemMeta().hasCustomModelData()) {
                if (offItem.getItemMeta().getCustomModelData() == cmd) {
                    return true;
                }
            }
        }
        return false;
    }

    // also need to check that elytra is equipped
    public boolean elytraEquipped(Player player) {
        ItemStack chestplateItem = player.getInventory().getChestplate();

        if (chestplateItem != null) {
            if (chestplateItem.getType().equals(Material.ELYTRA)) {
                return true;
            }
        }

        return false;
    }

    public void setHover(Player player, boolean flight) {
        player.setAllowFlight(flight);
    }
}
