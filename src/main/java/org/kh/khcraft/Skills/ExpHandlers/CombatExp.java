package org.kh.khcraft.Skills.ExpHandlers;

import org.kh.khcraft.Khcraft;

public class CombatExp extends SkillExp {
    public CombatExp(Khcraft instance) {
        super(instance);
    }

    // child class implementation of xp curve
    // combat: XP = level^2  + 100*level => level = -50 + sqrt(x+2500)
    @Override
    public double getCurrentSkillLevel(double currentExp) {
        return Math.floor(-50 + Math.sqrt(currentExp + 2500));
    }

    @Override
    public double getExpRequired(double currentLevel) {
        return Math.pow(currentLevel, 2) + 100*currentLevel;
    }
}
