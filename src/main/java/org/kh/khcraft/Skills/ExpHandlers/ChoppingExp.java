package org.kh.khcraft.Skills.ExpHandlers;

import org.kh.khcraft.Khcraft;

public class ChoppingExp extends SkillExp {
    public ChoppingExp(Khcraft instance) {
        super(instance);
    }

    // child class implementation of xp curve
    // chopping: XP = 0.5*level^2  + 12*level => level = -12 + sqrt(2) * sqrt(x+72)
    @Override
    public double getCurrentSkillLevel(double currentExp) {
        return Math.floor(-12 + Math.sqrt(2) * Math.sqrt(currentExp + 72));
    }

    @Override
    public double getExpRequired(double currentLevel) {
        return 0.5*Math.pow(currentLevel, 2) + 12*currentLevel;
    }
}
