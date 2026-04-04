package com.superhero.mod.util;

import net.minecraft.item.ItemStack;

public class ChargeHelper {
    
    // Şarjı hep %100 göster
    public static float getCharge(ItemStack stack) {
        return 100.0f; 
    }

    public static void consumeCharge(ItemStack stack, float amount) {
    }

    // Silah "Çalışabilir miyim?" diye sorduğunda hep "Evet (true)" de
    public static boolean canWork(ItemStack stack) {
        return true;
    }

    // Silah "Şarjı azaltayım mı?" dediğinde hiçbir şey yapma (Şarj bitmesin)
    public static void drain(ItemStack stack, float amount) {
    }
}
