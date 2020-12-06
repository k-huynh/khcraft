package org.kh.khcraft.Skills.ExpHandlers;

import org.kh.khcraft.Khcraft;

public class ChoppingExp extends SkillExp {
    public ChoppingExp(Khcraft instance) {
        super(instance);
    }

    // child class implementation of xp curve
    // chopping: XP = 2*level^2  + 50*level => level = -25/2 + 1/2 sqrt(2x + 625)
    @Override
    public double getCurrentSkillLevel(double currentExp) {
        Double exp = Math.floor(0.5*(-25 + Math.sqrt(2*currentExp + 625)));
        if (exp < 0) {
            return 0.0;
        }
        return exp;
    }

    @Override
    public double getExpRequired(double currentLevel) {
        return 2*Math.pow(currentLevel, 2) + 50*currentLevel;
    }
}
