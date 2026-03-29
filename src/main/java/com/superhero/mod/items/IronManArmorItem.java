package com.superhero.mod.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

public class ChargeHelper {
    public static final float MAX_CHARGE = 100.0f;
    public static final float MIN_CHARGE = 5.0f; // Senin %5 kuralın

    public static float getCharge(ItemStack stack) {
        if (stack.isEmpty()) return 0;
        NbtCompound nbt = stack.getOrCreateNbt();
        if (!nbt.contains("ironman_charge")) {
            nbt.putFloat("ironman_charge", MAX_CHARGE);
        }
        return nbt.getFloat("ironman_charge");
    }

    public static boolean canWork(ItemStack stack) {
        return !stack.isEmpty() && getCharge(stack) >= MIN_CHARGE;
    }

    public static void drain(ItemStack stack, float amount) {
        float current = getCharge(stack);
        stack.getOrCreateNbt().putFloat("ironman_charge", Math.max(0, current - amount));
    }
}
