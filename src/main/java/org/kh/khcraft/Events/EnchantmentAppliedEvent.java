package org.kh.khcraft.Events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class EnchantmentAppliedEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    private String playerName;

    public EnchantmentAppliedEvent(String playerName) {
        this.playerName = playerName;
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
}
