package org.kh.khcraft;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.Arrays;
import java.util.List;

public class ConfigGenerator {
    Khcraft plugin;
    FileConfiguration config;

    public ConfigGenerator(Khcraft instance) {
        plugin = instance;
        config = plugin.getConfig();
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
                "TRIDENT",
                "GENERAL");
        config.addDefault("skills.list", skillsList);

        /*
         * block exp (mining))
         */

//        // special
//        config.addDefault("skills.mining.blocks.MAGMA_BLOCK", 0.5);
//        config.addDefault("skills.mining.blocks.OBSIDIAN", 5);
//        config.addDefault("skills.mining.blocks.CRYING_OBSIDIAN", 5);
//        config.addDefault("skills.mining.blocks.SPAWNER", 25);
//        // ores
//        config.addDefault("skills.mining.blocks.COAL_ORE", 10);
//        config.addDefault("skills.mining.blocks.DIAMOND_ORE", 40);
//        config.addDefault("skills.mining.blocks.EMERALD_ORE", 20);
//        config.addDefault("skills.mining.blocks.LAPIS_ORE", 10);
//        config.addDefault("skills.mining.blocks.REDSTONE_ORE", 5);
//        config.addDefault("skills.mining.blocks.NETHER_QUARTZ_ORE", 5);
//        config.addDefault("skills.mining.blocks.NETHER_GOLD_ORE", 5);
//        // non special ores
//        config.addDefault("skills.mining.blocks.ANCIENT_DEBRIS", 1);
//        config.addDefault("skills.mining.blocks.IRON_ORE", 1);
//        config.addDefault("skills.mining.blocks.GOLD_ORE", 1);
//        // default blocks
//        config.addDefault("skills.mining.blocks.COBBLESTONE", 1);
//        config.addDefault("skills.mining.blocks.SANDSTONE", 0.5);
//        config.addDefault("skills.mining.blocks.MOSSY_COBBLESTONE", 1);
//        config.addDefault("skills.mining.blocks.ANDESITE", 1);
//        config.addDefault("skills.mining.blocks.DIORITE", 1);
//        config.addDefault("skills.mining.blocks.GRANITE", 1);
//        config.addDefault("skills.mining.blocks.STONE", 1);
//        config.addDefault("skills.mining.blocks.TERRACOTTA", 1);
//        // ocean monument blocks
//        config.addDefault("skills.mining.blocks.DARK_PRISMARINE", 1);
//        config.addDefault("skills.mining.blocks.PRISMARINE", 1);
//        config.addDefault("skills.mining.blocks.PRISMARINE_BRICKS", 1);
//        // nether blocks
//        config.addDefault("skills.mining.blocks.NETHERRACK", 0.5);
//        config.addDefault("skills.mining.blocks.WARPED_NYLIUM", 0.5);
//        config.addDefault("skills.mining.blocks.CRIMSON_NYLIUM", 0.5);
//        config.addDefault("skills.mining.blocks.NETHER_BRICK", 1);
//        config.addDefault("skills.mining.blocks.BLACKSTONE", 1);
//        config.addDefault("skills.mining.blocks.GLOWSTONE", 1);
//        // end blocks
//        config.addDefault("skills.mining.blocks.END_STONE", 1);
//        config.addDefault("skills.mining.blocks.END_STONE_BRICKS", 1);
//        config.addDefault("skills.mining.blocks.PURPUR_BLOCK", 1);
//        config.addDefault("skills.mining.blocks.PURPUR_PILLAR", 1);
//        // stronghold blocks
//        config.addDefault("skills.mining.blocks.STONE_BRICKS", 1);
//        config.addDefault("skills.mining.blocks.MOSSY_STONE_BRICKS", 1);
//        config.addDefault("skills.mining.blocks.CRACKED_STONE_BRICKS", 1);
//        // bastion blocks
//        config.addDefault("skills.mining.blocks.POLISHED_BLACKSTONE_BRICKS", 1);
//        config.addDefault("skills.mining.blocks.CHISELED_POLISHED_BLACKSTONE", 1);
//        config.addDefault("skills.mining.blocks.POLISHED_BLACKSTONE_BRICKS", 1);
//        config.addDefault("skills.mining.blocks.GILDED_BLACKSTONE", 1);
//        config.addDefault("skills.mining.blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS", 1);
//        config.addDefault("skills.mining.blocks.POLISHED_BASALT", 1);
//        config.addDefault("skills.mining.blocks.SMOOTH_QUARTZ", 1);
//        config.addDefault("skills.mining.blocks.QUARTZ_BLOCK", 1);
        // from cheah

        config.addDefault("skills.mining.blocks.ICE", 1);
        config.addDefault("skills.mining.blocks.PACKED_ICE", 1);
        config.addDefault("skills.mining.blocks.BLUE_ICE", 1);
        config.addDefault("skills.mining.blocks.FROSTED_ICE", 1);

        config.addDefault("skills.mining.blocks.ANVIL", 1);
        config.addDefault("skills.mining.blocks.BELL", 1);
        config.addDefault("skills.mining.blocks.REDSTONE_BLOCK", 1);
        config.addDefault("skills.mining.blocks.BREWING_STAND", 1);
        config.addDefault("skills.mining.blocks.CAULDRON", 1);
        config.addDefault("skills.mining.blocks.CHAIN", 1);
        config.addDefault("skills.mining.blocks.HOPPER", 1);
        config.addDefault("skills.mining.blocks.IRON_BARS", 1);
        config.addDefault("skills.mining.blocks.IRON_DOOR", 1);
        config.addDefault("skills.mining.blocks.IRON_TRAPDOOR", 1);
        config.addDefault("skills.mining.blocks.LANTERN", 1);
        config.addDefault("skills.mining.blocks.SOUL_LANTERN", 1);

        config.addDefault("skills.mining.blocks.LIGHT_WEIGHTED_PRESSURE_PLATE", 1);
        config.addDefault("skills.mining.blocks.HEAVY_WEIGHTED_PRESSURE_PLATE", 1);
        config.addDefault("skills.mining.blocks.STONE_PRESSURE_PLATE", 1);
        config.addDefault("skills.mining.blocks.POLISHED_BLACKSTONE_PRESSURE_PLATE", 1);

        config.addDefault("skills.mining.blocks.IRON_BLOCK", 1);
        config.addDefault("skills.mining.blocks.LAPIS_BLOCK", 1);
        config.addDefault("skills.mining.blocks.GOLD_BLOCK", 1);
        config.addDefault("skills.mining.blocks.EMERALD_BLOCK", 1);
        config.addDefault("skills.mining.blocks.DIAMOND_BLOCK", 1);
        config.addDefault("skills.mining.blocks.NETHERITE_BLOCK", 1);

        config.addDefault("skills.mining.blocks.PISTON", 1);
        config.addDefault("skills.mining.blocks.STICKY_PISTON", 1);

        config.addDefault("skills.mining.blocks.CONDUIT", 1);

        config.addDefault("skills.mining.blocks.SHULKER_BOX", 1);
        config.addDefault("skills.mining.blocks.WHITE_SHULKER_BOX", 1);
        config.addDefault("skills.mining.blocks.ORANGE_SHULKER_BOX", 1);
        config.addDefault("skills.mining.blocks.MAGENTA_SHULKER_BOX", 1);
        config.addDefault("skills.mining.blocks.LIGHT_BLUE_SHULKER_BOX", 1);
        config.addDefault("skills.mining.blocks.YELLOW_SHULKER_BOX", 1);
        config.addDefault("skills.mining.blocks.LIME_SHULKER_BOX", 1);
        config.addDefault("skills.mining.blocks.PINK_SHULKER_BOX", 1);
        config.addDefault("skills.mining.blocks.GRAY_SHULKER_BOX", 1);
        config.addDefault("skills.mining.blocks.LIGHT_GRAY_SHULKER_BOX", 1);
        config.addDefault("skills.mining.blocks.CYAN_SHULKER_BOX", 1);
        config.addDefault("skills.mining.blocks.PURPLE_SHULKER_BOX", 1);
        config.addDefault("skills.mining.blocks.BLUE_SHULKER_BOX", 1);
        config.addDefault("skills.mining.blocks.BROWN_SHULKER_BOX", 1);
        config.addDefault("skills.mining.blocks.GREEN_SHULKER_BOX", 1);
        config.addDefault("skills.mining.blocks.RED_SHULKER_BOX", 1);
        config.addDefault("skills.mining.blocks.BLACK_SHULKER_BOX", 1);

        config.addDefault("skills.mining.blocks.ACTIVATOR_RAIL", 1);
        config.addDefault("skills.mining.blocks.DETECTOR_RAIL", 1);
        config.addDefault("skills.mining.blocks.POWERED_RAIL", 1);
        config.addDefault("skills.mining.blocks.RAIL", 1);

        config.addDefault("skills.mining.blocks.ANDESITE", 1);
        config.addDefault("skills.mining.blocks.BASALT", 1);
        config.addDefault("skills.mining.blocks.BLACKSTONE", 1);
        config.addDefault("skills.mining.blocks.BLAST_FURNACE", 1);
        config.addDefault("skills.mining.blocks.COAL_BLOCK", 1);
        config.addDefault("skills.mining.blocks.BRICKS", 1);
        config.addDefault("skills.mining.blocks.COBBLESTONE", 1);
        config.addDefault("skills.mining.blocks.DARK_PRISMARINE", 1);
        config.addDefault("skills.mining.blocks.DIORITE", 1);
        config.addDefault("skills.mining.blocks.DISPENSER", 1);
        config.addDefault("skills.mining.blocks.DROPPER", 1);
        config.addDefault("skills.mining.blocks.ENCHANTING_TABLE", 1);
        config.addDefault("skills.mining.blocks.END_STONE", 1);
        config.addDefault("skills.mining.blocks.ENDER_CHEST", 1);
        config.addDefault("skills.mining.blocks.FURNACE", 1);
        config.addDefault("skills.mining.blocks.GRANITE", 1);
        config.addDefault("skills.mining.blocks.GRINDSTONE", 1);
        config.addDefault("skills.mining.blocks.LODESTONE", 1);
        config.addDefault("skills.mining.blocks.MOSSY_COBBLESTONE", 1);
        config.addDefault("skills.mining.blocks.NETHER_BRICKS", 1);
        config.addDefault("skills.mining.blocks.CRACKED_NETHER_BRICKS", 1);
        config.addDefault("skills.mining.blocks.CHISELED_NETHER_BRICKS", 1);
        config.addDefault("skills.mining.blocks.NETHER_BRICK_FENCE", 1);
        config.addDefault("skills.mining.blocks.RED_NETHER_BRICKS", 1);
        config.addDefault("skills.mining.blocks.OBSERVER", 1);
        config.addDefault("skills.mining.blocks.PRISMARINE", 1);
        config.addDefault("skills.mining.blocks.PRISMARINE_BRICKS", 1);
        config.addDefault("skills.mining.blocks.POLISHED_ANDESITE", 1);
        config.addDefault("skills.mining.blocks.POLISHED_BLACKSTONE", 1);
        config.addDefault("skills.mining.blocks.CHISELED_POLISHED_BLACKSTONE", 1);
        config.addDefault("skills.mining.blocks.POLISHED_BLACKSTONE_BRICKS", 1);
        config.addDefault("skills.mining.blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS", 1);
        config.addDefault("skills.mining.blocks.POLISHED_DIORITE", 1);
        config.addDefault("skills.mining.blocks.POLISHED_GRANITE", 1);
        config.addDefault("skills.mining.blocks.SMOKER", 1);
        config.addDefault("skills.mining.blocks.STONECUTTER", 1);
        config.addDefault("skills.mining.blocks.SMOOTH_STONE", 1);
        config.addDefault("skills.mining.blocks.STONE", 1);
        config.addDefault("skills.mining.blocks.STONE_BRICKS", 1);
        config.addDefault("skills.mining.blocks.CRACKED_STONE_BRICKS", 1);
        config.addDefault("skills.mining.blocks.MOSSY_STONE_BRICKS", 1);
        config.addDefault("skills.mining.blocks.CHISELED_STONE_BRICKS", 1);
        config.addDefault("skills.mining.blocks.STONE_BUTTON", 1);
        config.addDefault("skills.mining.blocks.POLISHED_BLACKSTONE_BUTTON", 1);

        config.addDefault("skills.mining.blocks.MAGMA_BLOCK", 0.5);
        config.addDefault("skills.mining.blocks.NETHERRACK", 0.5);
        config.addDefault("skills.mining.blocks.WARPED_NYLIUM", 0.5);
        config.addDefault("skills.mining.blocks.CRIMSON_NYLIUM", 0.5);
        config.addDefault("skills.mining.blocks.OBSIDIAN", 5);
        config.addDefault("skills.mining.blocks.CRYING_OBSIDIAN", 5);
        config.addDefault("skills.mining.blocks.SPAWNER", 25);

        config.addDefault("skills.mining.blocks.COAL_ORE", 10);
        config.addDefault("skills.mining.blocks.DIAMOND_ORE", 40);
        config.addDefault("skills.mining.blocks.EMERALD_ORE", 20);
        config.addDefault("skills.mining.blocks.LAPIS_ORE", 10);
        config.addDefault("skills.mining.blocks.REDSTONE_ORE", 5);
        config.addDefault("skills.mining.blocks.NETHER_QUARTZ_ORE", 5);
        config.addDefault("skills.mining.blocks.NETHER_GOLD_ORE", 5);

        config.addDefault("skills.mining.blocks.ANCIENT_DEBRIS", 1);
        config.addDefault("skills.mining.blocks.IRON_ORE", 1);
        config.addDefault("skills.mining.blocks.GOLD_ORE", 1);

        config.addDefault("skills.mining.blocks.QUARTZ_BLOCK", 1);
        config.addDefault("skills.mining.blocks.CHISELED_QUARTZ_BLOCK", 1);
        config.addDefault("skills.mining.blocks.QUARTZ_PILLAR", 1);
        config.addDefault("skills.mining.blocks.SMOOTH_QUARTZ", 1);
        config.addDefault("skills.mining.blocks.QUARTZ_BRICKS", 1);

        config.addDefault("skills.mining.blocks.COBBLESTONE_WALL", 1);
        config.addDefault("skills.mining.blocks.MOSSY_COBBLESTONE_WALL", 1);
        config.addDefault("skills.mining.blocks.STONE_BRICK_WALL", 1);
        config.addDefault("skills.mining.blocks.MOSSY_STONE_BRICK_WALL", 1);
        config.addDefault("skills.mining.blocks.ANDESITE_WALL", 1);
        config.addDefault("skills.mining.blocks.DIORITE_WALL", 1);
        config.addDefault("skills.mining.blocks.GRANITE_WALL", 1);
        config.addDefault("skills.mining.blocks.SANDSTONE_WALL", 1);
        config.addDefault("skills.mining.blocks.RED_SANDSTONE_WALL", 1);
        config.addDefault("skills.mining.blocks.BRICK_WALL", 1);
        config.addDefault("skills.mining.blocks.PRISMARINE_WALL", 1);
        config.addDefault("skills.mining.blocks.NETHER_BRICK_WALL", 1);
        config.addDefault("skills.mining.blocks.RED_NETHER_BRICK_WALL", 1);
        config.addDefault("skills.mining.blocks.END_STONE_BRICK_WALL", 1);
        config.addDefault("skills.mining.blocks.BLACKSTONE_WALL", 1);
        config.addDefault("skills.mining.blocks.POLISHED_BLACKSTONE_WALL", 1);
        config.addDefault("skills.mining.blocks.POLISHED_BLACKSTONE_BRICK_WALL", 1);

        config.addDefault("skills.mining.blocks.WHITE_CONCRETE", 1);
        config.addDefault("skills.mining.blocks.ORANGE_CONCRETE", 1);
        config.addDefault("skills.mining.blocks.MAGENTA_CONCRETE", 1);
        config.addDefault("skills.mining.blocks.LIGHT_BLUE_CONCRETE", 1);
        config.addDefault("skills.mining.blocks.YELLOW_CONCRETE", 1);
        config.addDefault("skills.mining.blocks.LIME_CONCRETE", 1);
        config.addDefault("skills.mining.blocks.PINK_CONCRETE", 1);
        config.addDefault("skills.mining.blocks.GRAY_CONCRETE", 1);
        config.addDefault("skills.mining.blocks.LIGHT_GRAY_CONCRETE", 1);
        config.addDefault("skills.mining.blocks.CYAN_CONCRETE", 1);
        config.addDefault("skills.mining.blocks.PURPLE_CONCRETE", 1);
        config.addDefault("skills.mining.blocks.BLUE_CONCRETE", 1);
        config.addDefault("skills.mining.blocks.BROWN_CONCRETE", 1);
        config.addDefault("skills.mining.blocks.GREEN_CONCRETE", 1);
        config.addDefault("skills.mining.blocks.RED_CONCRETE", 1);
        config.addDefault("skills.mining.blocks.BLACK_CONCRETE", 1);

        config.addDefault("skills.mining.blocks.WHITE_GLAZED_TERRACOTTA", 1);
        config.addDefault("skills.mining.blocks.ORANGE_GLAZED_TERRACOTTA", 1);
        config.addDefault("skills.mining.blocks.MAGENTA_GLAZED_TERRACOTTA", 1);
        config.addDefault("skills.mining.blocks.LIGHT_BLUE_GLAZED_TERRACOTTA", 1);
        config.addDefault("skills.mining.blocks.YELLOW_GLAZED_TERRACOTTA", 1);
        config.addDefault("skills.mining.blocks.LIME_GLAZED_TERRACOTTA", 1);
        config.addDefault("skills.mining.blocks.PINK_GLAZED_TERRACOTTA", 1);
        config.addDefault("skills.mining.blocks.GRAY_GLAZED_TERRACOTTA", 1);
        config.addDefault("skills.mining.blocks.LIGHT_GRAY_GLAZED_TERRACOTTA", 1);
        config.addDefault("skills.mining.blocks.CYAN_GLAZED_TERRACOTTA", 1);
        config.addDefault("skills.mining.blocks.PURPLE_GLAZED_TERRACOTTA", 1);
        config.addDefault("skills.mining.blocks.BLUE_GLAZED_TERRACOTTA", 1);
        config.addDefault("skills.mining.blocks.BROWN_GLAZED_TERRACOTTA", 1);
        config.addDefault("skills.mining.blocks.GREEN_GLAZED_TERRACOTTA", 1);
        config.addDefault("skills.mining.blocks.RED_GLAZED_TERRACOTTA", 1);
        config.addDefault("skills.mining.blocks.BLACK_GLAZED_TERRACOTTA", 1);

        config.addDefault("skills.mining.blocks.SANDSTONE", 0.5);
        config.addDefault("skills.mining.blocks.CUT_SANDSTONE", 1);
        config.addDefault("skills.mining.blocks.CHISELED_SANDSTONE", 1);
        config.addDefault("skills.mining.blocks.SMOOTH_SANDSTONE", 1);
        config.addDefault("skills.mining.blocks.RED_SANDSTONE", 1);
        config.addDefault("skills.mining.blocks.CUT_RED_SANDSTONE", 1);
        config.addDefault("skills.mining.blocks.CHISELED_RED_SANDSTONE", 1);
        config.addDefault("skills.mining.blocks.SMOOTH_RED_SANDSTONE", 1);

        config.addDefault("skills.mining.blocks.STONE_SLAB", 1);
        config.addDefault("skills.mining.blocks.SMOOTH_STONE_SLAB", 1);
        config.addDefault("skills.mining.blocks.GRANITE_SLAB", 1);
        config.addDefault("skills.mining.blocks.POLISHED_GRANITE_SLAB", 1);
        config.addDefault("skills.mining.blocks.DIORITE_SLAB", 1);
        config.addDefault("skills.mining.blocks.POLISHED_DIORITE_SLAB", 1);
        config.addDefault("skills.mining.blocks.ANDESITE_SLAB", 1);
        config.addDefault("skills.mining.blocks.POLISHED_ANDESITE_SLAB", 1);
        config.addDefault("skills.mining.blocks.COBBLESTONE_SLAB", 1);
        config.addDefault("skills.mining.blocks.MOSSY_COBBLESTONE_SLAB", 1);
        config.addDefault("skills.mining.blocks.STONE_BRICK_SLAB", 1);
        config.addDefault("skills.mining.blocks.MOSSY_STONE_BRICK_SLAB", 1);
        config.addDefault("skills.mining.blocks.BRICK_SLAB", 1);
        config.addDefault("skills.mining.blocks.END_STONE_BRICK_SLAB", 1);
        config.addDefault("skills.mining.blocks.NETHER_BRICK_SLAB", 1);
        config.addDefault("skills.mining.blocks.RED_NETHER_BRICK_SLAB", 1);
        config.addDefault("skills.mining.blocks.SANDSTONE_SLAB", 1);
        config.addDefault("skills.mining.blocks.CUT_SANDSTONE_SLAB", 1);
        config.addDefault("skills.mining.blocks.SMOOTH_SANDSTONE_SLAB", 1);
        config.addDefault("skills.mining.blocks.RED_SANDSTONE_SLAB", 1);
        config.addDefault("skills.mining.blocks.CUT_RED_SANDSTONE_SLAB", 1);
        config.addDefault("skills.mining.blocks.SMOOTH_RED_SANDSTONE_SLAB", 1);
        config.addDefault("skills.mining.blocks.QUARTZ_SLAB", 1);
        config.addDefault("skills.mining.blocks.SMOOTH_QUARTZ_SLAB", 1);
        config.addDefault("skills.mining.blocks.PURPUR_SLAB", 1);
        config.addDefault("skills.mining.blocks.PRISMARINE_SLAB", 1);
        config.addDefault("skills.mining.blocks.PRISMARINE_BRICK_SLAB", 1);
        config.addDefault("skills.mining.blocks.DARK_PRISMARINE_SLAB", 1);
        config.addDefault("skills.mining.blocks.PETRIFIED_OAK_SLAB", 1);
        config.addDefault("skills.mining.blocks.BLACKSTONE_SLAB", 1);
        config.addDefault("skills.mining.blocks.POLISHED_BLACKSTONE_SLAB", 1);
        config.addDefault("skills.mining.blocks.POLISHED_BLACKSTONE_BRICK_SLAB", 1);

        config.addDefault("skills.mining.blocks.TERRACOTTA", 1);
        config.addDefault("skills.mining.blocks.WHITE_TERRACOTTA", 1);
        config.addDefault("skills.mining.blocks.ORANGE_TERRACOTTA", 1);
        config.addDefault("skills.mining.blocks.MAGENTA_TERRACOTTA", 1);
        config.addDefault("skills.mining.blocks.LIGHT_BLUE_TERRACOTTA", 1);
        config.addDefault("skills.mining.blocks.YELLOW_TERRACOTTA", 1);
        config.addDefault("skills.mining.blocks.LIME_TERRACOTTA", 1);
        config.addDefault("skills.mining.blocks.PINK_TERRACOTTA", 1);
        config.addDefault("skills.mining.blocks.GRAY_TERRACOTTA", 1);
        config.addDefault("skills.mining.blocks.LIGHT_GRAY_TERRACOTTA", 1);
        config.addDefault("skills.mining.blocks.CYAN_TERRACOTTA", 1);
        config.addDefault("skills.mining.blocks.PURPLE_TERRACOTTA", 1);
        config.addDefault("skills.mining.blocks.BLUE_TERRACOTTA", 1);
        config.addDefault("skills.mining.blocks.BROWN_TERRACOTTA", 1);
        config.addDefault("skills.mining.blocks.GREEN_TERRACOTTA", 1);
        config.addDefault("skills.mining.blocks.RED_TERRACOTTA", 1);
        config.addDefault("skills.mining.blocks.BLACK_TERRACOTTA", 1);

        config.addDefault("skills.mining.blocks.STONE_STAIRS", 1);
        config.addDefault("skills.mining.blocks.GRANITE_STAIRS", 1);
        config.addDefault("skills.mining.blocks.POLISHED_GRANITE_STAIRS", 1);
        config.addDefault("skills.mining.blocks.DIORITE_STAIRS", 1);
        config.addDefault("skills.mining.blocks.POLISHED_DIORITE_STAIRS", 1);
        config.addDefault("skills.mining.blocks.ANDESITE_STAIRS", 1);
        config.addDefault("skills.mining.blocks.POLISHED_ANDESITE_STAIRS", 1);
        config.addDefault("skills.mining.blocks.COBBLESTONE_STAIRS", 1);
        config.addDefault("skills.mining.blocks.MOSSY_COBBLESTONE_STAIRS", 1);
        config.addDefault("skills.mining.blocks.STONE_BRICK_STAIRS", 1);
        config.addDefault("skills.mining.blocks.MOSSY_STONE_BRICK_STAIRS", 1);
        config.addDefault("skills.mining.blocks.BRICK_STAIRS", 1);
        config.addDefault("skills.mining.blocks.END_STONE_BRICK_STAIRS", 1);
        config.addDefault("skills.mining.blocks.NETHER_BRICK_STAIRS", 1);
        config.addDefault("skills.mining.blocks.RED_NETHER_BRICK_STAIRS", 1);
        config.addDefault("skills.mining.blocks.SANDSTONE_STAIRS", 1);
        config.addDefault("skills.mining.blocks.SMOOTH_SANDSTONE_STAIRS", 1);
        config.addDefault("skills.mining.blocks.RED_SANDSTONE_STAIRS", 1);
        config.addDefault("skills.mining.blocks.SMOOTH_RED_SANDSTONE_STAIRS", 1);
        config.addDefault("skills.mining.blocks.QUARTZ_STAIRS", 1);
        config.addDefault("skills.mining.blocks.SMOOTH_QUARTZ_STAIRS", 1);
        config.addDefault("skills.mining.blocks.PURPUR_STAIRS", 1);
        config.addDefault("skills.mining.blocks.PRISMARINE_STAIRS", 1);
        config.addDefault("skills.mining.blocks.PRISMARINE_BRICK_STAIRS", 1);
        config.addDefault("skills.mining.blocks.DARK_PRISMARINE_STAIRS", 1);
        config.addDefault("skills.mining.blocks.BLACKSTONE_STAIRS", 1);
        config.addDefault("skills.mining.blocks.POLISHED_BLACKSTONE_STAIRS", 1);
        config.addDefault("skills.mining.blocks.POLISHED_BLACKSTONE_BRICK_STAIRS", 1);



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
        // crop products
        config.addDefault("skills.chopping.blocks.MELON", 1);
        config.addDefault("skills.chopping.blocks.PUMPKIN", 1);

        // from cheah
        config.addDefault("skills.chopping.blocks.MUSHROOM_STEM", 1);
        config.addDefault("skills.chopping.blocks.BEE_NEST", 1);
        config.addDefault("skills.chopping.blocks.VINE", 1);
        config.addDefault("skills.chopping.blocks.JACK_O_LANTERN", 1);
        config.addDefault("skills.chopping.blocks.COCOA", 1);

        config.addDefault("skills.chopping.blocks.WHITE_BANNER", 1);
        config.addDefault("skills.chopping.blocks.ORANGE_BANNER", 1);
        config.addDefault("skills.chopping.blocks.MAGENTA_BANNER", 1);
        config.addDefault("skills.chopping.blocks.LIGHT_BLUE_BANNER", 1);
        config.addDefault("skills.chopping.blocks.YELLOW_BANNER", 1);
        config.addDefault("skills.chopping.blocks.LIME_BANNER", 1);
        config.addDefault("skills.chopping.blocks.PINK_BANNER", 1);
        config.addDefault("skills.chopping.blocks.GRAY_BANNER", 1);
        config.addDefault("skills.chopping.blocks.LIGHT_GRAY_BANNER", 1);
        config.addDefault("skills.chopping.blocks.CYAN_BANNER", 1);
        config.addDefault("skills.chopping.blocks.PURPLE_BANNER", 1);
        config.addDefault("skills.chopping.blocks.BLUE_BANNER", 1);
        config.addDefault("skills.chopping.blocks.BROWN_BANNER", 1);
        config.addDefault("skills.chopping.blocks.GREEN_BANNER", 1);
        config.addDefault("skills.chopping.blocks.RED_BANNER", 1);
        config.addDefault("skills.chopping.blocks.BLACK_BANNER", 1);
        config.addDefault("skills.chopping.blocks.WHITE_WALL_BANNER", 1);
        config.addDefault("skills.chopping.blocks.ORANGE_WALL_BANNER", 1);
        config.addDefault("skills.chopping.blocks.MAGENTA_WALL_BANNER", 1);
        config.addDefault("skills.chopping.blocks.LIGHT_BLUE_WALL_BANNER", 1);
        config.addDefault("skills.chopping.blocks.YELLOW_WALL_BANNER", 1);
        config.addDefault("skills.chopping.blocks.LIME_WALL_BANNER", 1);
        config.addDefault("skills.chopping.blocks.PINK_WALL_BANNER", 1);
        config.addDefault("skills.chopping.blocks.GRAY_WALL_BANNER", 1);
        config.addDefault("skills.chopping.blocks.LIGHT_GRAY_WALL_BANNER", 1);
        config.addDefault("skills.chopping.blocks.CYAN_WALL_BANNER", 1);
        config.addDefault("skills.chopping.blocks.PURPLE_WALL_BANNER", 1);
        config.addDefault("skills.chopping.blocks.BLUE_WALL_BANNER", 1);
        config.addDefault("skills.chopping.blocks.BROWN_WALL_BANNER", 1);
        config.addDefault("skills.chopping.blocks.GREEN_WALL_BANNER", 1);
        config.addDefault("skills.chopping.blocks.RED_WALL_BANNER", 1);
        config.addDefault("skills.chopping.blocks.BLACK_WALL_BANNER", 1);

        config.addDefault("skills.chopping.blocks.BARREL", 1);
        config.addDefault("skills.chopping.blocks.BEEHIVE", 1);
        config.addDefault("skills.chopping.blocks.BOOKSHELF", 1);
        config.addDefault("skills.chopping.blocks.CAMPFIRE", 1);
        config.addDefault("skills.chopping.blocks.CARTOGRAPHY_TABLE", 1);
        config.addDefault("skills.chopping.blocks.CHEST", 1);
        config.addDefault("skills.chopping.blocks.COMPOSTER", 1);
        config.addDefault("skills.chopping.blocks.CRAFTING_TABLE", 1);
        config.addDefault("skills.chopping.blocks.DAYLIGHT_DETECTOR", 1);
        config.addDefault("skills.chopping.blocks.FLETCHING_TABLE", 1);
        config.addDefault("skills.chopping.blocks.JUKEBOX", 1);
        config.addDefault("skills.chopping.blocks.LADDER", 1);
        config.addDefault("skills.chopping.blocks.LECTERN", 1);
        config.addDefault("skills.chopping.blocks.LOOM", 1);
        config.addDefault("skills.chopping.blocks.NOTE_BLOCK", 1);
        config.addDefault("skills.chopping.blocks.SMITHING_TABLE", 1);
        config.addDefault("skills.chopping.blocks.TRAPPED_CHEST", 1);

        config.addDefault("skills.chopping.blocks.OAK_FENCE", 1);
        config.addDefault("skills.chopping.blocks.SPRUCE_FENCE", 1);
        config.addDefault("skills.chopping.blocks.BIRCH_FENCE", 1);
        config.addDefault("skills.chopping.blocks.JUNGLE_FENCE", 1);
        config.addDefault("skills.chopping.blocks.ACACIA_FENCE", 1);
        config.addDefault("skills.chopping.blocks.DARK_OAK_FENCE", 1);
        config.addDefault("skills.chopping.blocks.CRIMSON_FENCE", 1);
        config.addDefault("skills.chopping.blocks.WARPED_FENCE", 1);

        config.addDefault("skills.chopping.blocks.OAK_FENCE_GATE", 1);
        config.addDefault("skills.chopping.blocks.SPRUCE_FENCE_GATE", 1);
        config.addDefault("skills.chopping.blocks.BIRCH_FENCE_GATE", 1);
        config.addDefault("skills.chopping.blocks.JUNGLE_FENCE_GATE", 1);
        config.addDefault("skills.chopping.blocks.ACACIA_FENCE_GATE", 1);
        config.addDefault("skills.chopping.blocks.DARK_OAK_FENCE_GATE", 1);
        config.addDefault("skills.chopping.blocks.CRIMSON_FENCE_GATE", 1);
        config.addDefault("skills.chopping.blocks.WARPED_FENCE_GATE", 1);

        config.addDefault("skills.chopping.blocks.OAK_SIGN", 1);
        config.addDefault("skills.chopping.blocks.SPRUCE_SIGN", 1);
        config.addDefault("skills.chopping.blocks.BIRCH_SIGN", 1);
        config.addDefault("skills.chopping.blocks.JUNGLE_SIGN", 1);
        config.addDefault("skills.chopping.blocks.ACACIA_SIGN", 1);
        config.addDefault("skills.chopping.blocks.DARK_OAK_SIGN", 1);
        config.addDefault("skills.chopping.blocks.CRIMSON_SIGN", 1);
        config.addDefault("skills.chopping.blocks.WARPED_SIGN", 1);

        config.addDefault("skills.chopping.blocks.OAK_BUTTON", 1);
        config.addDefault("skills.chopping.blocks.SPRUCE_BUTTON", 1);
        config.addDefault("skills.chopping.blocks.BIRCH_BUTTON", 1);
        config.addDefault("skills.chopping.blocks.JUNGLE_BUTTON", 1);
        config.addDefault("skills.chopping.blocks.ACACIA_BUTTON", 1);
        config.addDefault("skills.chopping.blocks.DARK_OAK_BUTTON", 1);
        config.addDefault("skills.chopping.blocks.CRIMSON_BUTTON", 1);
        config.addDefault("skills.chopping.blocks.WARPED_BUTTON", 1);

        config.addDefault("skills.chopping.blocks.OAK_DOOR", 1);
        config.addDefault("skills.chopping.blocks.SPRUCE_DOOR", 1);
        config.addDefault("skills.chopping.blocks.BIRCH_DOOR", 1);
        config.addDefault("skills.chopping.blocks.JUNGLE_DOOR", 1);
        config.addDefault("skills.chopping.blocks.ACACIA_DOOR", 1);
        config.addDefault("skills.chopping.blocks.DARK_OAK_DOOR", 1);
        config.addDefault("skills.chopping.blocks.CRIMSON_DOOR", 1);
        config.addDefault("skills.chopping.blocks.WARPED_DOOR", 1);

        config.addDefault("skills.chopping.blocks.OAK_PRESSURE_PLATE", 1);
        config.addDefault("skills.chopping.blocks.SPRUCE_PRESSURE_PLATE", 1);
        config.addDefault("skills.chopping.blocks.BIRCH_PRESSURE_PLATE", 1);
        config.addDefault("skills.chopping.blocks.JUNGLE_PRESSURE_PLATE", 1);
        config.addDefault("skills.chopping.blocks.ACACIA_PRESSURE_PLATE", 1);
        config.addDefault("skills.chopping.blocks.DARK_OAK_PRESSURE_PLATE", 1);
        config.addDefault("skills.chopping.blocks.CRIMSON_PRESSURE_PLATE", 1);
        config.addDefault("skills.chopping.blocks.WARPED_PRESSURE_PLATE", 1);

        config.addDefault("skills.chopping.blocks.OAK_SLAB", 1);
        config.addDefault("skills.chopping.blocks.SPRUCE_SLAB", 1);
        config.addDefault("skills.chopping.blocks.BIRCH_SLAB", 1);
        config.addDefault("skills.chopping.blocks.JUNGLE_SLAB", 1);
        config.addDefault("skills.chopping.blocks.ACACIA_SLAB", 1);
        config.addDefault("skills.chopping.blocks.DARK_OAK_SLAB", 1);
        config.addDefault("skills.chopping.blocks.CRIMSON_SLAB", 1);
        config.addDefault("skills.chopping.blocks.WARPED_SLAB", 1);

        config.addDefault("skills.chopping.blocks.OAK_STAIRS", 1);
        config.addDefault("skills.chopping.blocks.SPRUCE_STAIRS", 1);
        config.addDefault("skills.chopping.blocks.BIRCH_STAIRS", 1);
        config.addDefault("skills.chopping.blocks.JUNGLE_STAIRS", 1);
        config.addDefault("skills.chopping.blocks.ACACIA_STAIRS", 1);
        config.addDefault("skills.chopping.blocks.DARK_OAK_STAIRS", 1);
        config.addDefault("skills.chopping.blocks.CRIMSON_STAIRS", 1);
        config.addDefault("skills.chopping.blocks.WARPED_STAIRS", 1);

        config.addDefault("skills.chopping.blocks.OAK_TRAPDOOR", 1);
        config.addDefault("skills.chopping.blocks.SPRUCE_TRAPDOOR", 1);
        config.addDefault("skills.chopping.blocks.BIRCH_TRAPDOOR", 1);
        config.addDefault("skills.chopping.blocks.JUNGLE_TRAPDOOR", 1);
        config.addDefault("skills.chopping.blocks.ACACIA_TRAPDOOR", 1);
        config.addDefault("skills.chopping.blocks.DARK_OAK_TRAPDOOR", 1);
        config.addDefault("skills.chopping.blocks.CRIMSON_TRAPDOOR", 1);
        config.addDefault("skills.chopping.blocks.WARPED_TRAPDOOR", 1);

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

        config.addDefault("skills.digging.blocks.SNOW", 0.5);

        // FROM CHEAH
        config.addDefault("skills.digging.blocks.WHITE_CONCRETE_POWDER", 1);
        config.addDefault("skills.digging.blocks.ORANGE_CONCRETE_POWDER", 1);
        config.addDefault("skills.digging.blocks.MAGENTA_CONCRETE_POWDER", 1);
        config.addDefault("skills.digging.blocks.LIGHT_BLUE_CONCRETE_POWDER", 1);
        config.addDefault("skills.digging.blocks.YELLOW_CONCRETE_POWDER", 1);
        config.addDefault("skills.digging.blocks.LIME_CONCRETE_POWDER", 1);
        config.addDefault("skills.digging.blocks.PINK_CONCRETE_POWDER", 1);
        config.addDefault("skills.digging.blocks.GRAY_CONCRETE_POWDER", 1);
        config.addDefault("skills.digging.blocks.LIGHT_GRAY_CONCRETE_POWDER", 1);
        config.addDefault("skills.digging.blocks.CYAN_CONCRETE_POWDER", 1);
        config.addDefault("skills.digging.blocks.PURPLE_CONCRETE_POWDER", 1);
        config.addDefault("skills.digging.blocks.BLUE_CONCRETE_POWDER", 1);
        config.addDefault("skills.digging.blocks.BROWN_CONCRETE_POWDER", 1);
        config.addDefault("skills.digging.blocks.GREEN_CONCRETE_POWDER", 1);
        config.addDefault("skills.digging.blocks.RED_CONCRETE_POWDER", 1);
        config.addDefault("skills.digging.blocks.BLACK_CONCRETE_POWDER", 1);

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

        // from cheah
        config.addDefault("skills.farming.blocks.SPONGE", 1);
        config.addDefault("skills.farming.blocks.WET_SPONGE", 1);
        config.addDefault("skills.farming.blocks.TARGET", 1);

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

        // enchantments-skills dictionary
//        config.addDefault("skills.enchantments.ARROW_DAMAGE", "ARCHERY");
//        config.addDefault("skills.enchantments.ARROW_FIRE", "ARCHERY");
//        config.addDefault("skills.enchantments.ARROW_INFINITE", "ARCHERY");
//        config.addDefault("skills.enchantments.ARROW_KNOCKBACK", "ARCHERY");
//        config.addDefault("skills.enchantments.DURABILITY", "ARCHERY");
//
//        config.addDefault("skills.enchantments.CHANNELING", "TRIDENT");
//        config.addDefault("skills.enchantments.DURABILITY", "TRIDENT");
//        config.addDefault("skills.enchantments.IMPALING", "TRIDENT");
//
//        config.addDefault("skills.enchantments.DAMAGE_ALL", "COMBAT");
//        config.addDefault("skills.enchantments.DAMAGE_ARTHROPODS", "COMBAT");
//        config.addDefault("skills.enchantments.DAMAGE_UNDEAD", "COMBAT");
//        config.addDefault("skills.enchantments.DURABILITY", "COMBAT");
//        config.addDefault("skills.enchantments.FIRE_ASPECT", "COMBAT");
//        config.addDefault("skills.enchantments.KNOCKBACK", "COMBAT");
//
//        config.addDefault("skills.enchantments.DEPTH_STRIDER", "GENERAL");
//        config.addDefault("skills.enchantments.FROST_WALKER", "GENERAL");
//        config.addDefault("skills.enchantments.DURABILITY", "GENERAL");
//
//        config.addDefault("skills.enchantments.DIG_SPEED", "MINING");
//        config.addDefault("skills.enchantments.DURABILITY", "MINING");
//
//        config.addDefault("skills.enchantments.DIG_SPEED", "DIGGING");
//        config.addDefault("skills.enchantments.DURABILITY", "DIGGING");
//
//        config.addDefault("skills.enchantments.DIG_SPEED", "CHOPPING");
//        config.addDefault("skills.enchantments.DURABILITY", "CHOPPING");
//
//        config.addDefault("skills.enchantments.DIG_SPEED", "FARMING");
//        config.addDefault("skills.enchantments.DURABILITY", "FARMING");
//
//        config.addDefault("skills.enchantments.DURABILITY", "FISHING");



        config.options().copyDefaults(true);
        plugin.saveConfig();
    }
}
