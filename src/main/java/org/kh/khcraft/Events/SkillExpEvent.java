package org.kh.khcraft.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class SkillExpEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    private String skillName;
    private final String playerName;
    private Player player;
    private double exp;

    public SkillExpEvent(String skillName, String playerName,double exp) {
        this.skillName = skillName;
        this.playerName = playerName;
        this.exp = exp;
    }

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public String getSkillName() {
        return this.skillName;
    }

    public String getPlayerName() {
        return this.playerName;
    }

    public double getExpChange() {
        return this.exp;
    }


}
