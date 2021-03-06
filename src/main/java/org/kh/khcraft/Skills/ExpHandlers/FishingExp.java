package org.kh.khcraft.Skills.ExpHandlers;

import org.kh.khcraft.Khcraft;

public class FishingExp extends SkillExp {
    public FishingExp(Khcraft instance) {
        super(instance);
    }

    // child class implementation of xp curve
    // fishing: XP = 10*level => level = x/10
    @Override
    public double getCurrentSkillLevel(double currentExp) {
        Double exp = Math.floor(currentExp/10.0);
        if (exp < 0) {
            return 0.0;
        }
        return exp;
    }

    @Override
    public double getExpRequired(double currentLevel) {
        return 10*currentLevel;
    }
}
