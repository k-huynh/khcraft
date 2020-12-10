package org.kh.khcraft;

import org.bukkit.configuration.file.FileConfiguration;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;

public class DatabaseHandler {
    Khcraft plugin;
    FileConfiguration config;

    private String hostname;
    private String port;
    private String username;
    private String password;
    private String database;

    public DatabaseHandler(Khcraft instance) {
        plugin = instance;
        config = plugin.getConfig();
    }


    public void databaseConnect() {
        this.hostname = config.getString("db.host");
        this.port = config.getString("db.port");
        this.username = config.getString("db.user");
        this.password = config.getString("db.pass");
        this.database = config.getString("db.name");

        try {
            openConnection();
            plugin.stmt = plugin.connection.createStatement();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void openConnection() throws SQLException, ClassNotFoundException {
        System.out.println("Attempting to open connection to database");
        if (plugin.connection != null && !plugin.connection.isClosed()){
            return;
        }
        Class.forName("com.mysql.jdbc.Driver");
        plugin.connection = DriverManager.getConnection("jdbc:mysql://" + this.hostname + ":"
                        + this.port + "/" + this.database,
                this.username, this.password);
    }

    public void databaseSetup() {
        System.out.println("Setting up database");
        try {
            // users table
            String userQuery = "CREATE TABLE IF NOT EXISTS Users("
                    + "Username VARCHAR(45) NOT NULL,"
                    + "KB DOUBLE DEFAULT 0,"
                    + "Daily INT DEFAULT 0,"
                    + "PRIMARY KEY (Username));";

            String userSkillsQuery = "CREATE TABLE IF NOT EXISTS UserSkills("
                    + "Username VARCHAR(45) NOT NULL,"
                    + "SkillName VARCHAR(45) NOT NULL,"
                    + "XP DOUBLE DEFAULT 0,"
                    + "UserSkillID INT AUTO_INCREMENT NOT NULL,"
                    + "AvailablePoints INT DEFAULT 0,"
                    + "FOREIGN KEY (Username) REFERENCES Users(Username),"
                    + "PRIMARY KEY (UserSkillID));";

            // userenchantments table
            String userEnchantsQuery = "CREATE TABLE IF NOT EXISTS UserEnchantments("
                    + "Username VARCHAR(45) NOT NULL,"
                    + "EnchantmentName VARCHAR(45) NOT NULL,"
                    + "EnchantmentLevel INT NOT NULL,"
                    + "SkillName VARCHAR(45) NOT NULL,"
                    + "Equipment VARCHAR(45) NOT NULL,"
                    + "Enabled INT DEFAULT 0 NOT NULL,"
                    + "UserEnchantmentID INT AUTO_INCREMENT NOT NULL,"
                    + "FOREIGN KEY (Username) REFERENCES Users(Username),"
                    + "PRIMARY KEY (UserEnchantmentID));";

            // transaction log table
            String KBLogQuery = "CREATE TABLE IF NOT EXISTS KBLog("
                    + "TransactionID INT AUTO_INCREMENT NOT NULL,"
                    + "FromUsername VARCHAR(45) NOT NULL,"
                    + "ToUsername VARCHAR(45) NOT NULL,"
                    + "KB INT NOT NULL,"
                    + "Timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
                    + "Message VARCHAR(100),"
                    + "FOREIGN KEY (FromUsername) REFERENCES Users(Username),"
                    + "FOREIGN KEY (ToUsername) REFERENCES Users(Username),"
                    + "PRIMARY KEY (TransactionID));";

            plugin.stmt.executeUpdate(userQuery);
            plugin.stmt.executeUpdate(userSkillsQuery);
            plugin.stmt.executeUpdate(userEnchantsQuery);
            plugin.stmt.executeUpdate(KBLogQuery);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//    public void populateEnchantments() {
//        try {
//            // read in from config
//
//            plugin.stmt.executeUpdate(populateEnchantmentString);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

}
