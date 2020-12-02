package org.kh.khcraft.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.PlayerInventory;

import java.io.IOException;

public class HoverListener implements Listener {
    @EventHandler
    public void onPlayerSwapHandItemsEvent(PlayerSwapHandItemsEvent event) throws IOException {
        ItemStack offItem = event.getOffHandItem();
        if (checkOffHand(offItem)) {
            System.out.printf("Offhand empty\n");
            setHover(event.getPlayer(), false);
        } else {
            System.out.printf("Offhand: %s\n", offItem.getType());
            setHover(event.getPlayer(), true);
        }
    }

    @EventHandler
    public void onPlayerInventoryClickEvent(InventoryClickEvent event) throws IOException {
        // check what's in offhand
        HumanEntity humanPlayer = event.getWhoClicked();
        PlayerInventory playerInventory = humanPlayer.getInventory();

        if (humanPlayer instanceof Player){
            Player player = (Player)humanPlayer;
            ItemStack offItem = playerInventory.getItemInOffHand();

            if (checkOffHand((offItem))) {
                System.out.printf("Invent click; offhand empty\n");
                setHover(player, false);
            } else {
                System.out.printf("invent click; offhand: %s\n", offItem.getType());
                setHover(player, true);
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

            if (checkOffHand(offItem)) {
                System.out.printf("Invent close; offhand empty\n");
                setHover(player, false);
            } else {
                System.out.printf("invent close; offhand: %s\n", offItem.getType());
                setHover(player, true);
            }
        }
    }

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) throws IOException {
        Player player = event.getPlayer();
        PlayerInventory playerInventory = player.getInventory();

        ItemStack offItem = playerInventory.getItemInOffHand();

        if (checkOffHand(offItem)) {
            System.out.printf("player join; offhand empty\n");
            setHover(player, false);
        } else {
            System.out.printf("player join; offhand: %s\n", offItem.getType());
            setHover(player, true);
        }
    }

    public boolean checkOffHand(ItemStack offItem) {
        if (offItem.getType().equals(Material.AIR)){
            return true;
        }
        return false;
    }

    public void setHover(Player player, boolean flight) {
        player.setAllowFlight(flight);
    }
}
