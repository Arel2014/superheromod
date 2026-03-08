package com.superhero.mod.armor;

import com.superhero.mod.util.ChargeHelper;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;

import java.util.List;

public class IronManArmorItem extends ArmorItem {

    public IronManArmorItem(RegistryEntry<ArmorMaterial> material, Type type, Settings settings) {
        super(material, type, settings);
    }

    @Override
    public boolean isItemBarVisible(ItemStack stack) {
        return getType() == Type.CHESTPLATE;
    }

    @Override
    public int getItemBarStep(ItemStack stack) {
        float charge = ChargeHelper.getCharge(stack);
        return Math.round(13.0f * (charge / ChargeHelper.MAX_CHARGE));
    }

    @Override
    public int getItemBarColor(ItemStack stack) {
        float charge = ChargeHelper.getCharge(stack);
        if (charge < 20f) return 0xFF3300;
        if (charge < 50f) return 0xFFAA00;
        return 0x00AAFF;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        if (getType() == Type.CHESTPLATE) {
            float charge = ChargeHelper.getCharge(stack);
            String color = charge < 20 ? "§c" : charge < 50 ? "§6" : "§b";
            tooltip.add(Text.literal("§7⚡ Charge: " + color + String.format("%.1f", charge) + "%"));
            if (charge < ChargeHelper.MIN_CHARGE) {
                tooltip.add(Text.literal("§c⚠ Suit disabled! Charge at station."));
            }
        }
        tooltip.add(Text.literal("§7Full set: Flight, Repulsors, No fall damage"));
    }
}
