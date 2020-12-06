package org.kh.khcraft.Skills.ExpHandlers;

import org.kh.khcraft.Khcraft;

public class ArcheryExp extends SkillExp {
    public ArcheryExp(Khcraft instance) {
        super(instance);
    }

    // child class implementation of xp curve
    // archery: XP = level^2  + 100*level => level = -50 + sqrt(x+2500)
    @Override
    public double getCurrentSkillLevel(double currentExp) {
        Double exp = Math.floor(-50 + Math.sqrt(currentExp + 2500));
        if (exp < 0) {
            return 0.0;
        }
        return exp;
    }

    @Override
    public double getExpRequired(double currentLevel) {
        return Math.pow(currentLevel, 2) + 100*currentLevel;
    }
}
