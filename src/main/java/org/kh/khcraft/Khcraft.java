package org.kh.khcraft;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

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
        generateConfig();

        // set up db connection
        databaseConnect();

        // run db setup
        databaseSetup();

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
                    + "KB DOUBLE DEFAULT 0 NOT NULL,"
                    + "PRIMARY KEY (Username));";

            // skills table
            String skillsQuery = "CREATE TABLE IF NOT EXISTS Skills("
                    + "SkillID INT AUTO_INCREMENT NOT NULL,"
                    + "SkillName VARCHAR(45) NOT NULL,"
                    + "PRIMARY KEY (SkillID));";

            // enchantments table
            String enchantmentsQuery = "CREATE TABLE IF NOT EXISTS Enchantments("
                    + "EnchantmentName VARCHAR(45) NOT NULL,"
                    + "SkillID INT NOT NULL,"
                    + "FOREIGN KEY (SkillID) REFERENCES Skills(SkillID),"
                    + "PRIMARY KEY (EnchantmentName));";

            // userskills table
            String userSkillsQuery = "CREATE TABLE IF NOT EXISTS UserSkills("
                    + "Username VARCHAR(45) NOT NULL,"
                    + "SkillID INT NOT NULL,"
                    + "XP DOUBLE NOT NULL,"
                    + "UserSkillID INT AUTO_INCREMENT NOT NULL,"
                    + "FOREIGN KEY (SkillID) REFERENCES Skills(SkillID),"
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
            stmt.executeUpdate(skillsQuery);
            stmt.executeUpdate(enchantmentsQuery);
            stmt.executeUpdate(userSkillsQuery);
            stmt.executeUpdate(userEnchantsQuery);
            stmt.executeUpdate(KBLogQuery);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
