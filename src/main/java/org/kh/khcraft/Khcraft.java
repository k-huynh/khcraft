package org.kh.khcraft;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.kh.khcraft.Commands.Economy.BalanceCommand;
import org.kh.khcraft.Commands.Economy.BuyCommand;
import org.kh.khcraft.Commands.Economy.ShopCommand;
import org.kh.khcraft.Commands.Economy.TradeCommand;
import org.kh.khcraft.Commands.Skills.*;
import org.kh.khcraft.Events.DailyRewardEvent;
import org.kh.khcraft.Listeners.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

public final class Khcraft extends JavaPlugin {
    static Connection connection;
    public Statement stmt;
    FileConfiguration config;

    @Override
    public void onEnable() {
        // Plugin startup logic
        // generate config if needed
        ConfigGenerator configGenerator = new ConfigGenerator(this);
        configGenerator.generateConfig();
        config = this.getConfig();

        // set up db connection
        DatabaseHandler databaseHandler = new DatabaseHandler(this);
        databaseHandler.databaseConnect();

        // run db setup
        databaseHandler.databaseSetup();

        // register listeners
        getServer().getPluginManager().registerEvents(new HoverListener(this), this);
        getServer().getPluginManager().registerEvents(new SkillsListener(this), this);
        getServer().getPluginManager().registerEvents(new SkillExpListener(this), this);
        getServer().getPluginManager().registerEvents(new EnchantmentListener(this), this);
        getServer().getPluginManager().registerEvents(new VillagerListener(), this);
        getServer().getPluginManager().registerEvents(new AnvilListener(this), this);
        getServer().getPluginManager().registerEvents(new EnchantmentTableListener(), this);
        getServer().getPluginManager().registerEvents(new KBListener(this), this);

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

        this.getCommand("skills").setExecutor(new SkillsCommand(this));

        this.getCommand("trade").setExecutor(new TradeCommand(this));
        this.getCommand("balance").setExecutor(new BalanceCommand(this));
        this.getCommand("shop").setExecutor(new ShopCommand(this));
        this.getCommand("buy").setExecutor(new BuyCommand(this));

        scheduleDailyReward();

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

    public void scheduleDailyReward() {
        // schedule daily reward
        // get current time (in milliseconds) (epoch time)
        System.out.printf("<========= SCHEDULING DAILY REWARD =========>\n");
        long now = Calendar.getInstance().getTimeInMillis();

        // get time of the day
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int minute = Calendar.getInstance().get(Calendar.MINUTE);
        int second = Calendar.getInstance().get(Calendar.SECOND);

        System.out.printf("Current time: %d; %d:%d:%d \n", now, hour, minute, second);

        // get desired time of day for event
        Calendar scheduledCal = Calendar.getInstance();
        scheduledCal.set(Calendar.HOUR_OF_DAY, config.getInt("economy.dailyRewardTime.hour"));
        scheduledCal.set(Calendar.MINUTE, config.getInt("economy.dailyRewardTime.minute"));
        scheduledCal.set(Calendar.SECOND, config.getInt("economy.dailyRewardTime.second"));
        scheduledCal.set(Calendar.MILLISECOND, config.getInt("economy.dailyRewardTime.millisecond"));

        System.out.printf("Reward time: %d:%d:%d\n",
                config.getInt("economy.dailyRewardTime.hour"),
                config.getInt("economy.dailyRewardTime.minute"),
                config.getInt("economy.dailyRewardTime.second"));

        // get offset between desired time and current time
        long offset = scheduledCal.getTimeInMillis() - now;
        System.out.printf("Offset: %d\n", offset);

        // if offset is negative, then we've already passed the time. Delay should be set to time until tomorrow's
        // time
        if (offset < 0) {
            scheduledCal.add(Calendar.DATE, 1);
            offset = scheduledCal.getTimeInMillis() - now;
            System.out.printf("Adjusted offset: %d\n", offset);
        }

        // get offset as a number of ticks
        long ticks = offset / 50L;
        System.out.printf("Offset in ticks: %d\n", ticks);

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                System.out.println("Daily reward time!");
                Bukkit.getPluginManager().callEvent(new DailyRewardEvent());
            }
        }, ticks, 1728000L);
    }

}
