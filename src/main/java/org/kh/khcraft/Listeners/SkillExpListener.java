package org.kh.khcraft.Listeners;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.kh.khcraft.Events.SkillExpEvent;
import org.kh.khcraft.Khcraft;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SkillExpListener implements Listener {
    Khcraft plugin;

    public SkillExpListener(Khcraft instance) {
        plugin = instance;
    }

    @EventHandler
    public void onSkillExpEvent(SkillExpEvent event) {
        String playerName = event.getPlayerName();
        String skillName = event.getSkillName();
        Player player = event.getPlayer();
        Double exp = event.getExpChange();

        // check exp for the skill to see if they've leveled up
        switch(skillName) {
            case("MINING"):
                handleMiningExp(playerName, skillName, player, exp);
        }
    }

    public void handleMiningExp(String playerName, String skillName, Player player, double exp) {
        double currentExp = getCurrentExp(playerName, skillName);
        System.out.printf("%s current mining exp: %f \n", playerName, currentExp);

        // TODO: replace this later with a proper way of doing things
        if (currentExp > 100.0) {
            // get pickaxe
            ItemStack mainItem = player.getInventory().getItemInMainHand();

            if (mainItem.getType().toString().contains("_PICKAXE")) {
                mainItem.addUnsafeEnchantment(Enchantment.DIG_SPEED, 6);
            }

        }

    }


    // vanilla exp for general skills
    @EventHandler
    public void onPlayerExpChangeEvent(PlayerExpChangeEvent event) {
        Player player = event.getPlayer();
        int level = player.getLevel();


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

    public int getPointsUsed(String playerName, String skillName) {
        try {
            ResultSet DBSearchRS = plugin.stmt.executeQuery(String.format("SELECT COUNT(*) FROM UserEnchantments INNER JOIN Enchantments ON UserEnchantments.EnchantmentName =  WHERE Username = '%s' AND SkillName = '%s';", playerName, skillName));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 1;

    }
}
