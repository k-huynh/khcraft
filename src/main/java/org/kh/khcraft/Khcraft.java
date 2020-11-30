package org.kh.khcraft;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

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
        generateConfig();

        // set up db connection
        databaseConnect();

        // run db setup
        databaseSetup();

        // register listeners
        getServer().getPluginManager().registerEvents(new HoverListener(), this);
        getServer().getPluginManager().registerEvents(new SkillsListener(this), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void generateConfig() {
        // db config
        config.addDefault("db.host", "localhost");
        config.addDefault("db.port", "3306");
        config.addDefault("db.name", "test");
        config.addDefault("db.user", "khcraft");
        // need to update the config yourself to add the pword!
        config.addDefault("db.pass", "");

        // skills config
        List<String> skillsList = Arrays.asList("MINING",
                                                "CHOPPING",
                                                "DIGGING",
                                                "FARMING",
                                                "ATTACKING",
                                                "FISHING",
                                                "ARCHERY",
                                                "GENERAL");
        config.addDefault("skills.list", skillsList);

        // block exp when mined
        config.addDefault("skills.blocks.NETHERRACK", 0.5);
        config.addDefault("skills.blocks.WARPED_NYLIUM", 0.5);
        config.addDefault("skills.blocks.CRIMSON_NYLIUM", 0.5);
        // sandstone and its derivatives
        config.addDefault("skills.blocks.SANDSTONE", 0.5);
        config.addDefault("skills.blocks.MAGMA_BLOCK", 0.5);
        // obsidian and crying obsidian
        config.addDefault("skills.blocks.OBSIDIAN", 5);
        config.addDefault("skills.blocks.SPAWNER", 25);
        // ores
        config.addDefault("skills.blocks.COAL_ORE", 10);
        config.addDefault("skills.blocks.DIAMOND_ORE", 40);
        config.addDefault("skills.blocks.EMERALD_ORE", 20);
        config.addDefault("skills.blocks.LAPIS_ORE", 10);
        config.addDefault("skills.blocks.REDSTONE_ORE", 5);
        config.addDefault("skills.blocks.NETHER_QUARTZ_ORE", 5);
        config.addDefault("skills.blocks.NETHER_GOLD_ORE", 5);

        // tillable blocks
        List<String> tillableList = Arrays.asList("DIRT",
                                                  "GRASS_BLOCK",
                                                  "GRASS_PATH",
                                                  "COARSE_DIRT");
        config.addDefault("skills.tillableList", tillableList);

        // silk touch exception blocks
        List<String> silkTouchExceptionList = Arrays.asList("COAL_ORE",
                                                            "DIAMOND_ORE",
                                                            "EMERALD_ORE",
                                                            "LAPIS_ORE",
                                                            "REDSTONE_ORE",
                                                            "NETHER_QUARTZ_ORE",
                                                            "NETHER_GOLD_ORE");
        config.addDefault("skills.silkTouch", silkTouchExceptionList);


        config.options().copyDefaults(true);
        saveConfig();
    }

    public void databaseConnect() {
        this.hostname = config.getString("db.host");
        this.port = config.getString("db.port");
        this.username = config.getString("db.user");
        this.password = config.getString("db.pass");
        this.database = config.getString("db.name");

        try {
            openConnection();
            stmt = connection.createStatement();
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

    public void databaseSetup() {
        try {
            // users table
            String userQuery = "CREATE TABLE IF NOT EXISTS Users("
                    + "Username VARCHAR(45) NOT NULL,"
                    + "KB DOUBLE DEFAULT 0,"
                    + "PRIMARY KEY (Username));";

            // enchantments table
            String enchantmentsQuery = "CREATE TABLE IF NOT EXISTS Enchantments("
                    + "EnchantmentName VARCHAR(45) NOT NULL,"
                    + "SkillName VARCHAR(45) NOT NULL,"
                    + "PRIMARY KEY (EnchantmentName));";

            // userskills table
            String userSkillsQuery = "CREATE TABLE IF NOT EXISTS UserSkills("
                    + "Username VARCHAR(45) NOT NULL,"
                    + "SkillName VARCHAR(45) NOT NULL,"
                    + "XP DOUBLE DEFAULT 0,"
                    + "UserSkillID INT AUTO_INCREMENT NOT NULL,"
                    + "FOREIGN KEY (Username) REFERENCES Users(Username),"
                    + "PRIMARY KEY (UserSkillID));";

            // userenchantments table
            String userEnchantsQuery = "CREATE TABLE IF NOT EXISTS UserEnchantments("
                    + "Username VARCHAR(45) NOT NULL,"
                    + "EnchantmentName VARCHAR(45) NOT NULL,"
                    + "EnchantmentLevel INT NOT NULL,"
                    + "UserEnchantmentID INT AUTO_INCREMENT NOT NULL,"
                    + "FOREIGN KEY (Username) REFERENCES Users(Username),"
                    + "FOREIGN KEY (EnchantmentName) REFERENCES Enchantments(EnchantmentName),"
                    + "PRIMARY KEY (UserEnchantmentID));";

            // transaction log table
            String KBLogQuery = "CREATE TABLE IF NOT EXISTS KBLog("
                    + "TransactionID INT AUTO_INCREMENT NOT NULL,"
                    + "FromUsername VARCHAR(45) NOT NULL,"
                    + "ToUsername VARCHAR(45) NOT NULL,"
                    + "KB INT NOT NULL,"
                    + "Timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
                    + "FOREIGN KEY (FromUsername) REFERENCES Users(Username),"
                    + "FOREIGN KEY (ToUsername) REFERENCES Users(Username),"
                    + "PRIMARY KEY (TransactionID));";

            stmt.executeUpdate(userQuery);
            stmt.executeUpdate(enchantmentsQuery);
            stmt.executeUpdate(userSkillsQuery);
            stmt.executeUpdate(userEnchantsQuery);
            stmt.executeUpdate(KBLogQuery);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
