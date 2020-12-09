package org.kh.khcraft.Listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class EnchantmentTableListener implements Listener {
    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent event) {
        if (event.hasBlock()) {
            if (event.getClickedBlock().getType().equals(Material.ENCHANTING_TABLE)) {
                event.setCancelled(true);
            }
        }
    }
}
