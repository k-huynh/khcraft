package org.kh.khcraft.Commands.Skills;

import org.bukkit.command.TabExecutor;
import org.kh.khcraft.Khcraft;

import java.util.ArrayList;
import java.util.List;

public class GeneralCommand extends SkillCommands implements TabExecutor {
    public GeneralCommand(Khcraft instance) {
        plugin = instance;
        config = plugin.getConfig();
    }

    @Override
    public List<String> getIncompatibleEnchantments(String toolName, String enchantmentName) {
        // given the tool, skill, and enchantment name, check the config if there's a list of incompatible enchantments
        // that includes this one

        if (toolName.equalsIgnoreCase("boots")) {
            List<String> incompatible = new ArrayList<String>();

            List<String> incompatibleEnchants1 = config.getStringList(String.format("skills.%s.incompatible.%s.1", "general", toolName));
            List<String> incompatibleEnchants2 = config.getStringList(String.format("skills.%s.incompatible.%s.2", "general", toolName));

            if (incompatibleEnchants1.contains(enchantmentName)) {
                incompatibleEnchants1.remove(enchantmentName);

                for (int i = 0; i < incompatibleEnchants1.size(); i++) {
                    incompatible.add(incompatibleEnchants1.get(i));
                }
            }

            if (incompatibleEnchants2.contains(enchantmentName)) {
                incompatibleEnchants2.remove(enchantmentName);

                for (int i = 0; i < incompatibleEnchants2.size(); i++) {
                    incompatible.add(incompatibleEnchants2.get(i));
                }
            }

            return incompatible;
        }
        else {
            List<String> incompatibleEnchants = config.getStringList(String.format("skills.%s.incompatible.%s", "general", toolName));

            if (incompatibleEnchants.contains(enchantmentName)) {
                incompatibleEnchants.remove(enchantmentName);

                return incompatibleEnchants;
            }
        }

        return new ArrayList<String>();
    }
}
