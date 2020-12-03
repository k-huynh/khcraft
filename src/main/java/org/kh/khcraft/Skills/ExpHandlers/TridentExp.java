package org.kh.khcraft.Skills.ExpHandlers;

import org.kh.khcraft.Khcraft;

public class TridentExp extends SkillExp {
    public TridentExp(Khcraft instance) {
        super(instance);
    }

    // child class implementation of xp curve
    // trident: XP = 0.2*level^2  + 25*level => level = -125/2 + sqrt(5)/2 * sqrt(4x+3125)
    @Override
    public double getCurrentSkillLevel(double currentExp) {
        return Math.floor(-125.0/2 + Math.sqrt(5)/2 * Math.sqrt(4*currentExp + 3125));
    }

    @Override
    public double getExpRequired(double currentLevel) {
        return 0.2*Math.pow(currentLevel, 2) + 25*currentLevel;
    }
}
