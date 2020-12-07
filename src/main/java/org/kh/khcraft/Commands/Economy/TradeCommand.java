package org.kh.khcraft.Commands.Economy;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.kh.khcraft.Khcraft;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TradeCommand implements TabExecutor {
    Khcraft plugin;

    public TradeCommand(Khcraft instance) {
        plugin = instance;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // arg 0 = @person
        // arg 1 = amount (double)
        // arg 2 = optional message

        // message supplied
        List<String> message = new ArrayList<String>();
        if (args.length > 2) {
            for (int i = 2; i < args.length; i++) {
                message.add(args[i]);
            }
        }

        // check if transaction is possible
        // get list of players
        List<String> players = getPlayerList();

        // need to supply at a minimum the name and amount
        if (args.length > 1) {
            // check if provided name is valid
            if (players.contains(args[0])) {
                // check if amount is valid
                double balance = getKBBalance(sender.getName());
                if (balance >= Double.parseDouble(args[1])) {
                    // perform transaction
                    sendKB(args[0], sender.getName(), Double.parseDouble(args[1]), getMessage(message));

                    sender.sendMessage(String.format("Success! %s transferred to %s.", args[1], args[0]));
                    return true;
                }
                else {
                    sender.sendMessage("Insufficient funds!");
                    return false;
                }
            }
            else {
                sender.sendMessage("Provided player name does not exist!");
                return false;
            }
        }
        else {
            sender.sendMessage("Please specify player name and amount to transfer!");
            return false;
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        // arg 0 = @person
        // arg 1 = amount (double)
        // arg 2 = optional message
        // i.e., only need tab completion for the first argument
        if (args.length == 1) {
            // return list of people (need not be online)
            return getPlayerList();
        }

        // should return an empty arraylist
        return new ArrayList<String>();
    }


    public List<String> getPlayerList() {
        // get list of playernames from database
        List<String> players = new ArrayList<String>();

        try {
            ResultSet userQueryRS = plugin.stmt.executeQuery("SELECT Username FROM Users;");

            while (userQueryRS.next()) {
                players.add(userQueryRS.getString(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return players;
    }

    public double getKBBalance(String playerName) {
        double balance = 0.0;
        try {
            ResultSet balanceQueryRS = plugin.stmt.executeQuery(String.format("SELECT KB FROM Users WHERE Username = '%s';", playerName));
            if (balanceQueryRS.next()) {
                balance = balanceQueryRS.getDouble(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return balance;
    }

    public String getMessage(List<String> message) {
        String msg = "";
        for (int i = 0; i < message.size(); i++) {
            msg = msg + message.get(i) + " ";
        }

        return msg;
    }

    public void sendKB(String toPlayerName, String fromPlayerName, double amount, String message) {
        try {
            // add KB to target player
            plugin.stmt.executeUpdate(String.format("UPDATE Users SET KB = KB + %f WHERE Username = '%s';", amount, toPlayerName));
            // remove KB from origin player
            plugin.stmt.executeUpdate(String.format("UPDATE Users SET KB = KB - %f WHERE Username = '%s';", amount, fromPlayerName));
            // update KBLog
            plugin.stmt.executeUpdate(String.format("INSERT INTO KBLog (FromUsername, ToUsername, KB, Message) VALUES ('%s', '%s', %f, '%s');", fromPlayerName, toPlayerName, amount, message));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
