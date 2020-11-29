package org.kh.khcraft;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class Khcraft extends JavaPlugin {
    FileConfiguration config = getConfig();

    static Connection connection;
    private String hostname;
    private String port;
    private String username;
    private String password;
    private String database;

    @Override
    public void onEnable() {
        // Plugin startup logic
        // generate config if needed
        generateConfig();

        // set up db connection
        databaseConnect();

        // create listener for hoveritem checking
        getServer().getPluginManager().registerEvents(new HoverListener(), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void generateConfig() {
        config.addDefault("dbhost", "");
        config.addDefault("dbport", "");
        config.addDefault("dbname", "");
        config.addDefault("dbuser", "");
        config.addDefault("dbpass", "");
        config.options().copyDefaults(true);
        saveConfig();
    }

    public void databaseConnect() {
        this.hostname = config.getString("dbhost");
        this.port = config.getString("dbport");
        this.username = config.getString("dbuser");
        this.password = config.getString("dbpass");
        this.database = config.getString("dbname");

        try {
            openConnection();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void openConnection() throws SQLException, ClassNotFoundException {
        if (connection != null && !connection.isClosed()){
            return;
        }
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://" + this.hostname + ":"
                                                 + this.port + "/" + this.database,
                                                  this.username, this.password);
    }
}
