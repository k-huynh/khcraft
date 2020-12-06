package org.kh.khcraft.Skills.ExpHandlers;

import org.kh.khcraft.Khcraft;

public class DiggingExp extends SkillExp {
    public DiggingExp(Khcraft instance) {
        super(instance);
    }

    // child class implementation of xp curve
    // digging: XP = 3*level^2  + 100*level => level = -50/3 + 1/3 * sqrt(3*x+2500)
    @Override
    public double getCurrentSkillLevel(double currentExp) {
        Double exp = Math.floor(-50.0/3 + 1.0/3 * Math.sqrt(3*currentExp + 2500));
        if (exp < 0) {
            return 0.0;
        }
        return exp;
    }

    @Override
    public double getExpRequired(double currentLevel) {
        return 3*Math.pow(currentLevel, 2) + 100*currentLevel;
    }
}
