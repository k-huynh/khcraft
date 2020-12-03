package org.kh.khcraft.Skills.ExpHandlers;

import org.kh.khcraft.Khcraft;

public class FarmingExp extends SkillExp {
    public FarmingExp(Khcraft instance) {
        super(instance);
    }

    // child class implementation of xp curve
    // farming: XP = 0.2*level^2  + 5*level => level = -12.5 + sqrt(5)/2 * sqrt(4*x + 125)
    @Override
    public double getCurrentSkillLevel(double currentExp) {
        return Math.floor(-12.5 + Math.sqrt(5)/2 * Math.sqrt(4*currentExp + 125));
    }

    @Override
    public double getExpRequired(double currentLevel) {
        return 0.2*Math.pow(currentLevel, 2) + 5*currentLevel;
    }
}
