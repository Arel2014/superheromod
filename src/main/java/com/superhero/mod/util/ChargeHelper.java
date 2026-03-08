package com.superhero.mod.util;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

public class ChargeHelper {

    public static final float MAX_CHARGE = 100.0f;
    public static final float MIN_CHARGE = 5.0f;
    private static final String KEY = "ironman_charge";

    public static float getCharge(ItemStack stack) {
        if (stack.isEmpty()) return 0;
        NbtCompound nbt = stack.get(DataComponentTypes.CUSTOM_DATA) != null
                ? stack.get(DataComponentTypes.CUSTOM_DATA).copyNbt()
                : new NbtCompound();
        if (!nbt.contains(KEY)) {
            setCharge(stack, MAX_CHARGE);
            return MAX_CHARGE;
        }
        return nbt.getFloat(KEY);
    }

    public static void setCharge(ItemStack stack, float charge) {
        if (stack.isEmpty()) return;
        charge = Math.max(0, Math.min(MAX_CHARGE, charge));
        NbtCompound nbt = stack.get(DataComponentTypes.CUSTOM_DATA) != null
                ? stack.get(DataComponentTypes.CUSTOM_DATA).copyNbt()
                : new NbtCompound();
        nbt.putFloat(KEY, charge);
        stack.set(DataComponentTypes.CUSTOM_DATA,
                net.minecraft.component.type.NbtComponent.of(nbt));
    }

    public static void drain(ItemStack stack, float amount) {
        setCharge(stack, getCharge(stack) - amount);
    }

    public static void add(ItemStack stack, float amount) {
        setCharge(stack, getCharge(stack) + amount);
    }

    public static boolean hasEnough(ItemStack stack) {
        return getCharge(stack) >= MIN_CHARGE;
    }

    public static boolean isFull(ItemStack stack) {
        return getCharge(stack) >= MAX_CHARGE;
    }
}
