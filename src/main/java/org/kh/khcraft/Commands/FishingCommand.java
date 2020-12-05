package org.kh.khcraft.Commands;

import org.bukkit.command.TabExecutor;
import org.kh.khcraft.Khcraft;

import java.util.ArrayList;
import java.util.List;

public class FishingCommand extends SkillCommands implements TabExecutor {
    public FishingCommand(Khcraft instance) {
        plugin = instance;
        config = plugin.getConfig();
    }

    @Override
    public List<String> getIncompatibleEnchantments(String toolName, String enchantmentName) {
        // given the tool, skill, and enchantment name, check the config if there's a list of incompatible enchantments
        // that includes this one

        List<String> incompatibleEnchants = config.getStringList(String.format("skills.%s.incompatible.%s", "fishing", toolName));

        if (incompatibleEnchants.contains(enchantmentName)) {
            incompatibleEnchants.remove(enchantmentName);

            return incompatibleEnchants;
        }

        return new ArrayList<String>();
    }
}
