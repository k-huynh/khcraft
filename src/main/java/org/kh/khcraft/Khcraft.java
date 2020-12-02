package org.kh.khcraft;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.kh.khcraft.Listeners.HoverListener;
import org.kh.khcraft.Listeners.SkillExpListener;
import org.kh.khcraft.Listeners.SkillsListener;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public final class Khcraft extends JavaPlugin {
    FileConfiguration config = getConfig();

    static Connection connection;
    public Statement stmt;
    private String hostname;
    private String port;
    private String username;
    private String password;
    private String database;

    @Override
    public void onEnable() {
        // Plugin startup logic
        // generate config if needed
        ConfigGenerator configGenerator = new ConfigGenerator(this);
        configGenerator.generateConfig();

        // set up db connection
        DatabaseHandler databaseHandler = new DatabaseHandler(this);
        databaseHandler.databaseConnect();

        // run db setup
        databaseHandler.databaseSetup();

        // register listeners
        getServer().getPluginManager().registerEvents(new HoverListener(), this);
        getServer().getPluginManager().registerEvents(new SkillsListener(this), this);
        getServer().getPluginManager().registerEvents(new SkillExpListener(this), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

}
