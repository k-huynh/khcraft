package org.kh.khcraft.Skills.ExpHandlers;

import org.kh.khcraft.Khcraft;

public class MiningExp extends SkillExp {
    public MiningExp(Khcraft instance) {
        super(instance);
    }

    // child class implementation of xp curve
    // mining: XP = 20*level^2 + 500*level => level = -12.5 + (sqrt(x+3125))/(2*sqrt(5))
    @Override
    public double getCurrentSkillLevel(double currentExp) {
        return Math.floor(-12.5 + (Math.sqrt(currentExp + 3125))/(2 * Math.sqrt(5)));
    }

    @Override
    public double getExpRequired(double currentLevel) {
        return 20*Math.pow(currentLevel, 2) + 500*currentLevel;
    }
}
