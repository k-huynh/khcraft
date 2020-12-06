package org.kh.khcraft.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class EnchantmentAppliedEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    private Player player;
    private String playerName;

    public EnchantmentAppliedEvent(String playerName, Player player) {
        this.playerName = playerName;
        this.player = player;
    }

    public HandlerList getHandlers() {
        return HANDLERS;
    }
    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public String getPlayerName() {
        return this.playerName;
    }

    public Player getPlayer() {
        return this.player;
    }
}
