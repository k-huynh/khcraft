package org.kh.khcraft;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Utilities {
    Khcraft plugin;

    public Utilities(Khcraft instance) {
        plugin = instance;
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

}
