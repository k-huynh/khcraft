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
                                                "COMBAT",
                                                "FISHING",
                                                "ARCHERY",
                                                "GENERAL");
        config.addDefault("skills.list", skillsList);

        /*
         * block exp (mining))
         */

        // special
        config.addDefault("skills.mining.blocks.MAGMA_BLOCK", 0.5);
        config.addDefault("skills.mining.blocks.OBSIDIAN", 5);
        config.addDefault("skills.mining.blocks.CRYING_OBSIDIAN", 5);
        config.addDefault("skills.mining.blocks.SPAWNER", 25);
        // ores
        config.addDefault("skills.mining.blocks.COAL_ORE", 10);
        config.addDefault("skills.mining.blocks.DIAMOND_ORE", 40);
        config.addDefault("skills.mining.blocks.EMERALD_ORE", 20);
        config.addDefault("skills.mining.blocks.LAPIS_ORE", 10);
        config.addDefault("skills.mining.blocks.REDSTONE_ORE", 5);
        config.addDefault("skills.mining.blocks.NETHER_QUARTZ_ORE", 5);
        config.addDefault("skills.mining.blocks.NETHER_GOLD_ORE", 5);
        // non special ores
        config.addDefault("skills.mining.blocks.ANCIENT_DEBRIS", 1);
        config.addDefault("skills.mining.blocks.IRON_ORE", 1);
        config.addDefault("skills.mining.blocks.GOLD_ORE", 1);
        // default blocks
        config.addDefault("skills.mining.blocks.COBBLESTONE", 1);
        config.addDefault("skills.mining.blocks.SANDSTONE", 0.5);
        config.addDefault("skills.mining.blocks.MOSSY_COBBLESTONE", 1);
        config.addDefault("skills.mining.blocks.ANDESITE", 1);
        config.addDefault("skills.mining.blocks.DIORITE", 1);
        config.addDefault("skills.mining.blocks.GRANITE", 1);
        config.addDefault("skills.mining.blocks.STONE", 1);
        config.addDefault("skills.mining.blocks.TERRACOTTA", 1);
        // ocean monument blocks
        config.addDefault("skills.mining.blocks.DARK_PRISMARINE", 1);
        config.addDefault("skills.mining.blocks.PRISMARINE", 1);
        config.addDefault("skills.mining.blocks.PRISMARINE_BRICKS", 1);
        config.addDefault("skills.mining.blocks.SEA_LANTERN", 1);
        // nether blocks
        config.addDefault("skills.mining.blocks.NETHERRACK", 0.5);
        config.addDefault("skills.mining.blocks.WARPED_NYLIUM", 0.5);
        config.addDefault("skills.mining.blocks.CRIMSON_NYLIUM", 0.5);
        config.addDefault("skills.mining.blocks.NETHER_BRICK", 1);
        config.addDefault("skills.mining.blocks.BLACKSTONE", 1);
        config.addDefault("skills.mining.blocks.GLOWSTONE", 1);
        // end blocks
        config.addDefault("skills.mining.blocks.END_STONE", 1);
        config.addDefault("skills.mining.blocks.END_STONE_BRICKS", 1);
        config.addDefault("skills.mining.blocks.PURPUR_BLOCK", 1);
        config.addDefault("skills.mining.blocks.PURPUR_PILLAR", 1);
        // stronghold blocks
        config.addDefault("skills.mining.blocks.STONE_BRICKS", 1);
        config.addDefault("skills.mining.blocks.MOSSY_STONE_BRICKS", 1);
        config.addDefault("skills.mining.blocks.CRACKED_STONE_BRICKS", 1);
        config.addDefault("skills.mining.blocks.INFESTED_STONE_BRICKS", 1);
        config.addDefault("skills.mining.blocks.INFESTED_MOSSY_STONE_BRICKS", 1);
        config.addDefault("skills.mining.blocks.INFESTED_CRACKED_STONE_BRICKS", 1);
        config.addDefault("skills.mining.blocks.INFESTED_STONE", 1);
        config.addDefault("skills.mining.blocks.INFESTED_COBBLESTONE", 1);
        // bastion blocks
        config.addDefault("skills.mining.blocks.POLISHED_BLACKSTONE_BRICKS", 1);
        config.addDefault("skills.mining.blocks.CHISELED_POLISHED_BLACKSTONE", 1);
        config.addDefault("skills.mining.blocks.POLISHED_BLACKSTONE_BRICKS", 1);
        config.addDefault("skills.mining.blocks.GILDED_BLACKSTONE", 1);
        config.addDefault("skills.mining.blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS", 1);
        config.addDefault("skills.mining.blocks.POLISHED_BASALT", 1);
        config.addDefault("skills.mining.blocks.SMOOTH_QUARTZ", 1);
        config.addDefault("skills.mining.blocks.QUARTZ_BLOCK", 1);

        /*
         * block exp (chopping)
         */
        // logs
        config.addDefault("skills.chopping.blocks.OAK_LOG", 1);
        config.addDefault("skills.chopping.blocks.SPRUCE_LOG", 1);
        config.addDefault("skills.chopping.blocks.BIRCH_LOG", 1);
        config.addDefault("skills.chopping.blocks.JUNGLE_LOG", 1);
        config.addDefault("skills.chopping.blocks.ACACIA_LOG", 1);
        config.addDefault("skills.chopping.blocks.DARK_OAK_LOG", 1);
        config.addDefault("skills.chopping.blocks.WARPED_STEM", 1);
        config.addDefault("skills.chopping.blocks.CRIMSON_STEM", 1);
        // stripped logs
        config.addDefault("skills.chopping.blocks.STRIPPED_OAK_LOG", 1);
        config.addDefault("skills.chopping.blocks.STRIPPED_SPRUCE_LOG", 1);
        config.addDefault("skills.chopping.blocks.STRIPPED_BIRCH_LOG", 1);
        config.addDefault("skills.chopping.blocks.STRIPPED_JUNGLE_LOG", 1);
        config.addDefault("skills.chopping.blocks.STRIPPED_ACACIA_LOG", 1);
        config.addDefault("skills.chopping.blocks.STRIPPED_DARK_OAK_LOG", 1);
        config.addDefault("skills.chopping.blocks.STRIPPED_WARPED_STEM", 1);
        config.addDefault("skills.chopping.blocks.STRIPPED_CRIMSON_STEM", 1);
        // wood
        config.addDefault("skills.chopping.blocks.OAK_WOOD", 1);
        config.addDefault("skills.chopping.blocks.SPRUCE_WOOD", 1);
        config.addDefault("skills.chopping.blocks.BIRCH_WOOD", 1);
        config.addDefault("skills.chopping.blocks.JUNGLE_WOOD", 1);
        config.addDefault("skills.chopping.blocks.ACACIA_WOOD", 1);
        config.addDefault("skills.chopping.blocks.DARK_OAK_WOOD", 1);
        config.addDefault("skills.chopping.blocks.WARPED_HYPHAE", 1);
        config.addDefault("skills.chopping.blocks.CRIMSON_HYPHAE", 1);
        // stripped wood
        config.addDefault("skills.chopping.blocks.STRIPPED_OAK_WOOD", 1);
        config.addDefault("skills.chopping.blocks.STRIPPED_SPRUCE_WOOD", 1);
        config.addDefault("skills.chopping.blocks.STRIPPED_BIRCH_WOOD", 1);
        config.addDefault("skills.chopping.blocks.STRIPPED_JUNGLE_WOOD", 1);
        config.addDefault("skills.chopping.blocks.STRIPPED_ACACIA_WOOD", 1);
        config.addDefault("skills.chopping.blocks.STRIPPED_DARK_OAK_WOOD", 1);
        config.addDefault("skills.chopping.blocks.STRIPPED_WARPED_HYPHAE", 1);
        config.addDefault("skills.chopping.blocks.STRIPPED_CRIMSON_HYPHAE", 1);
        // planks
        config.addDefault("skills.chopping.blocks.OAK_PLANKS", 1);
        config.addDefault("skills.chopping.blocks.SPRUCE_PLANKS", 1);
        config.addDefault("skills.chopping.blocks.BIRCH_PLANKS", 1);
        config.addDefault("skills.chopping.blocks.JUNGLE_PLANKS", 1);
        config.addDefault("skills.chopping.blocks.ACACIA_PLANKS", 1);
        config.addDefault("skills.chopping.blocks.DARK_OAK_PLANKS", 1);
        config.addDefault("skills.chopping.blocks.WARPED_PLANKS", 1);
        config.addDefault("skills.chopping.blocks.CRIMSON_PLANKS", 1);
        // other
        config.addDefault("skills.chopping.blocks.MELON_BLOCK", 1);
        config.addDefault("skills.chopping.blocks.PUMPKIN_BLOCK", 1);
        config.addDefault("skills.chopping.blocks.RED_MUSHROOM_BLOCK", 1);
        config.addDefault("skills.chopping.blocks.BROWN_MUSHROOM_BLOCK", 1);


        /*
         * block exp (digging)
         */
        // default
        config.addDefault("skills.digging.blocks.CLAY", 1);
        config.addDefault("skills.digging.blocks.FARMLAND", 1);
        config.addDefault("skills.digging.blocks.GRASS_BLOCK", 1);
        config.addDefault("skills.digging.blocks.GRASS_PATH", 1);
        config.addDefault("skills.digging.blocks.GRAVEL", 1);
        config.addDefault("skills.digging.blocks.MYCELIUM", 1);
        config.addDefault("skills.digging.blocks.PODZOL", 1);
        config.addDefault("skills.digging.blocks.COARSE_DIRT", 1);
        config.addDefault("skills.digging.blocks.DIRT", 1);
        config.addDefault("skills.digging.blocks.RED_SAND", 1);
        config.addDefault("skills.digging.blocks.SAND", 1);
        config.addDefault("skills.digging.blocks.SOUL_SAND", 1);
        config.addDefault("skills.digging.blocks.SOUL_SOIL", 1);
        config.addDefault("skills.digging.blocks.SNOW_BLOCK", 1);

        /*
         * block exp (farming)
         */
        config.addDefault("skills.farming.blocks.NETHER_WART_BLOCK", 1);
        config.addDefault("skills.farming.blocks.WARPED_WART_BLOCK", 1);
        config.addDefault("skills.farming.blocks.SHROOMLIGHT", 1);
        config.addDefault("skills.farming.blocks.HAY_BALE", 1);
        // leaves
        config.addDefault("skills.farming.blocks.OAK_LEAVES", 1);
        config.addDefault("skills.farming.blocks.SPRUCE_LEAVES", 1);
        config.addDefault("skills.farming.blocks.BIRCH_LEAVES", 1);
        config.addDefault("skills.farming.blocks.JUNGLE_LEAVES", 1);
        config.addDefault("skills.farming.blocks.ACACIA_LEAVES", 1);
        config.addDefault("skills.farming.blocks.DARK_OAK_LEAVES", 1);
        // crop products
        config.addDefault("skills.farming.blocks.MELON", 1);
        config.addDefault("skills.farming.blocks.PUMPKIN", 1);

        // crops
        List<String> cropsList = Arrays.asList("WHEAT",
                                              "CARROTS",
                                              "BEETROOTS",
                                              "POTATOES");
        config.addDefault("skills.farming.cropsList", cropsList);


        // tillable blocks
        List<String> tillableList = Arrays.asList("DIRT",
                                                  "GRASS_BLOCK",
                                                  "GRASS_PATH",
                                                  "COARSE_DIRT");
        config.addDefault("skills.farming.tillableList", tillableList);

        // silk touch exception blocks
        List<String> silkTouchExceptionList = Arrays.asList("COAL_ORE",
                                                            "DIAMOND_ORE",
                                                            "EMERALD_ORE",
                                                            "LAPIS_ORE",
                                                            "REDSTONE_ORE",
                                                            "NETHER_QUARTZ_ORE",
                                                            "NETHER_GOLD_ORE");
        config.addDefault("skills.mining.silkTouch", silkTouchExceptionList);


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
