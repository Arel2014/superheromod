package com.superhero.mod;

import com.superhero.mod.registry.ModItems;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SuperHeroMod implements ModInitializer {
    public static final String MOD_ID = "superheromod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    // Senin istediğin mod ayarları (Taramalı mı Sniper mı?)
    public static boolean isSniperMode = false; 
    public static boolean autoAttackEnabled = true;
    public static float flightSpeed = 1.0f;

    @Override
    public void onInitialize() {
        LOGGER.info("Süper Kahraman Modu Yükleniyor... Ironman Sistemleri Çevrimiçi!");
        
        // Eşyaları ve Zırhları Kaydet
        ModItems.registerItems();
    }
}
