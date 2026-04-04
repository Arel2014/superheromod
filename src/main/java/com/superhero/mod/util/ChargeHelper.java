package com.superhero.mod.util;

import net.minecraft.item.ItemStack;

public class ChargeHelper {
    
    // Hata vermemesi için şarjı geçici olarak hep %100 dolu gösteriyoruz
    public static float getCharge(ItemStack stack) {
        return 100.0f; 
    }

    // 1.21.1 veri sistemi (Data Components) tam eklendiğinde burası dolacak
    public static void consumeCharge(ItemStack stack, float amount) {
        
    }
}
