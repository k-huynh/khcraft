package org.kh.khcraft.Skills.ExpHandlers;

import org.bukkit.entity.Player;
import org.kh.khcraft.Khcraft;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class SkillExp {
    Khcraft plugin;

    public SkillExp(Khcraft instance) {
        plugin = instance;
    }

    public void handleSkillExp(String playerName, String skillName, double exp, Player player) {
        double currentExp = getCurrentExp(playerName, skillName);
        int currentLevel = (int) getCurrentSkillLevel(currentExp);

        // check if just leveled up by getting the minimum exp required for that level and comparing it against the
        // amount of exp just received
        double levelExpRequired = getExpRequired(currentLevel);
        double expDifference = currentExp - levelExpRequired;

//        System.out.printf("currentExp: %f, lvlExpRequired: %f, expDiff: %f, exp gained: %f \n", currentExp, levelExpRequired, expDifference, exp);

        // if the difference in exp is less than the amount we just got, then we leveled up!
        if (expDifference < exp) {
            if (currentLevel != 0) {
                System.out.printf("%s levelled up their %s skill to level %d!\n", playerName, skillName, currentLevel);
                player.sendMessage(String.format("%s levelled up to level %d!", skillName, currentLevel));
                // award a skill point if the level is divisible by 5
                if (currentLevel % 5 == 0) {
                    try {
                        plugin.stmt.executeUpdate(String.format("UPDATE UserSkills SET AvailablePoints = AvailablePoints + 1 WHERE Username = '%s' AND SkillName = '%s';", playerName, skillName));
                        player.sendMessage(String.format("You have been granted 1 additional skill point for %s!", skillName));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public double getCurrentExp(String playerName, String skillName) {
        double exp = 0.0;

        try {
            ResultSet DBSearchRS = plugin.stmt.executeQuery(String.format("SELECT XP FROM UserSkills WHERE Username = '%s' AND SkillName = '%s';", playerName, skillName));
            DBSearchRS.next();
            exp = DBSearchRS.getDouble(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return exp;
    }

    // TODO
    public int getPointsUsed(String playerName, String skillName) {
        try {
            ResultSet DBSearchRS = plugin.stmt.executeQuery(String.format("SELECT COUNT(*) FROM UserEnchantments INNER JOIN Enchantments ON UserEnchantments.EnchantmentName =  WHERE Username = '%s' AND SkillName = '%s';", playerName, skillName));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 1;

    }

    public abstract double getCurrentSkillLevel(double currentExp);
    public abstract double getExpRequired(double currentLevel);
}
