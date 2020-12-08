package org.kh.khcraft.Commands.Economy;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.kh.khcraft.Khcraft;

import java.util.ArrayList;
import java.util.List;

public class ShopCommand implements TabExecutor {
    Khcraft plugin;
    FileConfiguration config;

    public ShopCommand(Khcraft instance) {
        plugin = instance;
        config = plugin.getConfig();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // ignore any other args
        // get list of items from config
        List<String> itemList = config.getStringList("items.customItems");

        String sendStr = ChatColor.BOLD + "SHOP\n" + ChatColor.RESET;

        // get names and costs of item from config
        for (int i = 0; i < itemList.size(); i++) {
            String itemName = config.getString(String.format("items.%s.name", itemList.get(i)));
            double itemCost = config.getDouble(String.format("items.%s.cost", itemList.get(i)));

            sendStr = sendStr + String.format("%s (%.2f KB)\n", itemName, itemCost);
        }

        sender.sendMessage(sendStr);

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        // should not show anything on tab complete so always return empty arraylist
        return new ArrayList<String>();
    }
}
