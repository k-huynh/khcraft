package org.kh.khcraft;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.kh.khcraft.Commands.*;
import org.kh.khcraft.Listeners.HoverListener;
import org.kh.khcraft.Listeners.SkillExpListener;
import org.kh.khcraft.Listeners.SkillsListener;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public final class Khcraft extends JavaPlugin {
    static Connection connection;
    public Statement stmt;

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

        // register commands
        this.getCommand("mining").setExecutor(new MiningCommand(this));
        this.getCommand("digging").setExecutor(new DiggingCommand(this));
        this.getCommand("chopping").setExecutor(new ChoppingCommand(this));
        this.getCommand("farming").setExecutor(new FarmingCommand(this));
        this.getCommand("trident").setExecutor(new TridentCommand(this));
        this.getCommand("combat").setExecutor(new CombatCommand(this));
        this.getCommand("archery").setExecutor(new ArcheryCommand(this));
        this.getCommand("fishing").setExecutor(new FishingCommand(this));
        this.getCommand("general").setExecutor(new GeneralCommand(this));


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        try {
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
