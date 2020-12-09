package org.kh.khcraft.Commands.Economy;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.kh.khcraft.Khcraft;
import org.kh.khcraft.Utilities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TradeCommand implements TabExecutor {
    Khcraft plugin;
    Utilities utilities;

    public TradeCommand(Khcraft instance) {
        plugin = instance;
        utilities = new Utilities(plugin);
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
        List<String> players = utilities.getPlayerList();

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

                    Player targetPlayer = plugin.getServer().getPlayer(args[0]);

                    // send a message to the player if they are online
                    if (targetPlayer != null) {
                        if (args.length > 2) {
                            // message supplied
                            targetPlayer.sendMessage(String.format("%s transferred you %s, saying \"%s\"", sender.getName(), args[1], getMessage(message)));
                            System.out.printf("%s transferred %s %s KB, saying \"%s\"\n", sender.getName(), args[0], args[1], getMessage(message));
                        }
                        else {
                            // message not supplied
                            targetPlayer.sendMessage(String.format("%s transferred you %s, no message attached", sender.getName(), args[1]));
                            System.out.printf("%s transferred %s %s KB, no message attached\n", sender.getName(), args[0], args[1]);
                        }
                    }

                    return true;
                }
                else {
                    sender.sendMessage("Insufficient funds!");
                    System.out.printf("%s tried to send %s KB to %s but had insufficient funds\n", sender.getName(), args[1], args[0]);
                    return true;
                }
            }
            else {
                sender.sendMessage("Provided player name does not exist!");
                return true;
            }
        }
        else {
            sender.sendMessage("Please specify player name and amount to transfer!");
            return true;
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
            return utilities.getPlayerList();
        }

        // should return an empty arraylist
        return new ArrayList<String>();
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
