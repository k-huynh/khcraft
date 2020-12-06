package org.kh.khcraft.Skills.ExpHandlers;

import org.kh.khcraft.Khcraft;

public class DiggingExp extends SkillExp {
    public DiggingExp(Khcraft instance) {
        super(instance);
    }

    // child class implementation of xp curve
    // digging: XP = 4*level^2  + 100*level => level = -12.5 + 1/2 * sqrt(x+625)
    @Override
    public double getCurrentSkillLevel(double currentExp) {
        Double exp = Math.floor(-12.5 + 0.5 * Math.sqrt(currentExp + 625));
        if (exp < 0) {
            return 0.0;
        }
        return exp;
    }

    @Override
    public double getExpRequired(double currentLevel) {
        return 4*Math.pow(currentLevel, 2) + 100*currentLevel;
    }
}
