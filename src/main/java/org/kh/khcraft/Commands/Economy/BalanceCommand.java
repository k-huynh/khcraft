package org.kh.khcraft.Commands.Economy;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.kh.khcraft.Khcraft;
import org.kh.khcraft.Utilities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BalanceCommand implements TabExecutor {
    Khcraft plugin;
    Utilities utilities;

    public BalanceCommand(Khcraft instance) {
        plugin = instance;
        utilities = new Utilities(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // if no arguments specified, return sender's balance
        String playerName = sender.getName();

        // if one argument specified, return player's balance
        if (args.length == 1) {
            playerName = args[0];
        }

        Double balance = 0.0;

        try {
            ResultSet balanceRS = plugin.stmt.executeQuery(String.format("SELECT KB FROM Users WHERE Username = '%s';", playerName));

            if (balanceRS.next()) {
                balance = balanceRS.getDouble(1);
            }
            else {
                // result was not found
                sender.sendMessage("Could not find your balance :(");
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // send message
        sender.sendMessage(String.format("%s's balance: %.2f KB", playerName, balance));
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        // optional 1st argument being a player; all other arguments should return an empty arraylist
        if (args.length == 1) {
            return utilities.getPlayerList();
        }

        return new ArrayList<String>();
    }
}
