package com.superhero.mod.util;

import com.superhero.mod.armor.IronManArmorItem;
import com.superhero.mod.registry.ModItems;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class ArmorHelper {

    public static boolean isWearingFullSpiderMan(PlayerEntity player) {
        return player.getEquippedStack(EquipmentSlot.HEAD).getItem() == ModItems.SPIDERMAN_HELMET &&
               player.getEquippedStack(EquipmentSlot.CHEST).getItem() == ModItems.SPIDERMAN_CHESTPLATE &&
               player.getEquippedStack(EquipmentSlot.LEGS).getItem() == ModItems.SPIDERMAN_LEGGINGS &&
               player.getEquippedStack(EquipmentSlot.FEET).getItem() == ModItems.SPIDERMAN_BOOTS;
    }

    public static boolean isWearingFullIronMan(PlayerEntity player) {
        return player.getEquippedStack(EquipmentSlot.HEAD).getItem() instanceof IronManArmorItem &&
               player.getEquippedStack(EquipmentSlot.CHEST).getItem() instanceof IronManArmorItem &&
               player.getEquippedStack(EquipmentSlot.LEGS).getItem() instanceof IronManArmorItem &&
               player.getEquippedStack(EquipmentSlot.FEET).getItem() instanceof IronManArmorItem;
    }

    public static boolean isWearingElytra(PlayerEntity player) {
        return player.getEquippedStack(EquipmentSlot.CHEST).getItem() == Items.ELYTRA;
    }

    public static ItemStack getIronManChestplate(PlayerEntity player) {
        ItemStack chest = player.getEquippedStack(EquipmentSlot.CHEST);
        if (chest.getItem() instanceof IronManArmorItem) return chest;
        return ItemStack.EMPTY;
    }
}
