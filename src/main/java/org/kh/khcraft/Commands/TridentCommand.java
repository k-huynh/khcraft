package org.kh.khcraft.Commands;

import org.bukkit.command.TabExecutor;
import org.kh.khcraft.Khcraft;

import java.util.ArrayList;
import java.util.List;

public class TridentCommand extends SkillCommands implements TabExecutor {
    public TridentCommand(Khcraft instance) {
        plugin = instance;
        config = plugin.getConfig();
    }

    @Override
    public List<String> getIncompatibleEnchantments(String toolName, String enchantmentName) {
        // given the tool, skill, and enchantment name, check the config if there's a list of incompatible enchantments
        // that includes this one

        List<String> incompatibleEnchants1 = config.getStringList(String.format("skills.%s.incompatible.%s.1", "trident", toolName));
        List<String> incompatibleEnchants2 = config.getStringList(String.format("skills.%s.incompatible.%s.2", "trident", toolName));

        if (incompatibleEnchants1.contains(enchantmentName)) {
            incompatibleEnchants1.remove(enchantmentName);

            return incompatibleEnchants1;
        }
        else if (incompatibleEnchants2.contains(enchantmentName)) {
            incompatibleEnchants2.remove(enchantmentName);

            return incompatibleEnchants2;
        }

        return new ArrayList<String>();
    }
}
