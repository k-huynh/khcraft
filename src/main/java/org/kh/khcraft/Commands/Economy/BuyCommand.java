package org.kh.khcraft.Commands.Economy;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.kh.khcraft.Khcraft;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BuyCommand implements TabExecutor {
    Khcraft plugin;
    FileConfiguration config;

    List<String> itemList;
    List<String> itemNameList;

    public BuyCommand(Khcraft instance) {
        plugin = instance;
        config = plugin.getConfig();

        // get custom items from config
        itemList = config.getStringList("items.customItems");
        itemNameList = new ArrayList<String>();
        for (int i = 0; i < itemList.size(); i++) {
            itemNameList.add(config.getString(String.format("items.%s.name", itemList.get(i))));
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // should only work if the item name is specified correctly. Since the item names can be more than 1 word, and
        // we have no other arguments we care about, we can just take all args as the item name
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length > 0) {
                String itemName = "";
                for (int i = 0; i < args.length; i++) {
                    itemName = itemName + args[i] + " ";
                }

                // remove trailing space
                itemName = itemName.substring(0, itemName.length() - 1);

                // check if item name is legit
                System.out.printf("Trying to buy %s.\n", itemName);

                if (itemNameList.contains(itemName)) {
                    // get custom item (not display name). note that the index in itemList should match the index in itemNameList
                    String item = itemList.get(itemNameList.indexOf(itemName));

                    // get its price from config
                    double price = config.getDouble(String.format("items.%s.cost", item));

                    // check if player has sufficient funds
                    double balance = getBalance(sender.getName());

                    // they have enough money to buy it!
                    if (balance >= price) {
                        // create the item
                        Material baseMaterial = Material.getMaterial(config.getString(String.format("items.%s.vanillaBase", item)));
                        ItemStack customItem = new ItemStack(baseMaterial);

                        // set NBT info
                        ItemMeta meta = customItem.getItemMeta();
                        meta.setDisplayName(config.getString(String.format("items.%s.name", item)));
                        meta.setLore(config.getStringList(String.format("items.%s.description", item)));
                        meta.setCustomModelData(config.getInt(String.format("items.%s.cmd", item)));
                        customItem.setItemMeta(meta);

                        // try give player the item
                        HashMap<Integer, ItemStack> map = player.getInventory().addItem(customItem);

                        // success!
                        if (map.isEmpty()) {
                            // remove the money from their account
                            try {
                                plugin.stmt.executeUpdate(String.format("UPDATE Users SET KB = KB - %f WHERE Username = '%s';", price, player.getName()));
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }

                            sender.sendMessage(String.format("%s purchased for %.2f", itemName, price));
                            System.out.printf("%s purchased %s for %f\n", sender.getName(), itemName, price);
                            return true;

                        }
                        // no room :(
                        else {
                            sender.sendMessage("No space in inventory :(");
                            System.out.printf("%s tried to purchase %s for %f but had no space\n", sender.getName(), itemName, price);
                        }
                        return true;
                    }
                    else {
                        sender.sendMessage("Insufficient funds! :(");
                        System.out.printf("%s tried to purchase %s for %f but had insufficient funds\n", sender.getName(), itemName, price);
                        return true;
                    }
                }
                else {
                    sender.sendMessage("Item not found! :(");
                    return true;
                }
            }
            else {
                sender.sendMessage("Item name not specified!");
                return true;
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        // first argument should list out the buyable items; everything else should be empty
        if (args.length == 1) {


            return itemNameList;
        }

        return new ArrayList<String>();
    }

    public double getBalance(String playerName) {
        try {
            ResultSet balanceRS = plugin.stmt.executeQuery(String.format("SELECT KB FROM Users WHERE Username = '%s';", playerName));
            if (balanceRS.next()) {
                return balanceRS.getDouble(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0.0;
    }

    public void deductKB(String playerName, double amount) {
        try {
            plugin.stmt.executeUpdate(String.format("UPDATE Users SET KB = KB - %f WHERE Username = '%s'\n", amount, playerName));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
