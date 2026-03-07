package com.superhero.mod;

import com.superhero.mod.registry.ModItems;
import com.superhero.mod.registry.ModBlocks;
import com.superhero.mod.registry.ModBlockEntities;
import com.superhero.mod.registry.ModRecipes;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SuperHeroMod implements ModInitializer {
    public static final String MOD_ID = "superheromod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        ModItems.registerItems();
        ModBlocks.registerBlocks();
        ModBlockEntities.registerBlockEntities();
        ModRecipes.registerRecipes();
        IronManEvents.register();
        LOGGER.info("SuperHero Mod loaded!");
    }
}
