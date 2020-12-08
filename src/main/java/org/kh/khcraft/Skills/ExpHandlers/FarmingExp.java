package org.kh.khcraft.Skills.ExpHandlers;

import org.kh.khcraft.Khcraft;

public class FarmingExp extends SkillExp {
    public FarmingExp(Khcraft instance) {
        super(instance);
    }

    // child class implementation of xp curve
    // farming: XP = level^2  + 25*level => level = -12.5 + 0.5 * sqrt(4*x + 625)
    @Override
    public double getCurrentSkillLevel(double currentExp) {
        Double exp = Math.floor(-12.5 + 0.5 * Math.sqrt(4*currentExp + 625));
        if (exp < 0) {
            return 0.0;
        }
        return exp;
    }

    @Override
    public double getExpRequired(double currentLevel) {
        return Math.pow(currentLevel, 2) + 25*currentLevel;
    }
}
