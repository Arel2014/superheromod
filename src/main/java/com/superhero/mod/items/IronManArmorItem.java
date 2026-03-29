package com.superhero.mod.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.World;

public class IronManArmorItem extends ArmorItem {
    public IronManArmorItem(RegistryEntry<ArmorMaterial> material, Type type, Settings settings) {
        super(material, type, settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (!world.isClient() && entity instanceof PlayerEntity player) {
            if (hasFullSuitOfArmorOn(player)) {
                player.getAbilities().allowFlying = true;
                player.sendAbilitiesUpdate();
            } else if (!player.isCreative() && !player.isSpectator()) {
                player.getAbilities().allowFlying = false;
                player.getAbilities().flying = false;
                player.sendAbilitiesUpdate();
            }
        }
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    private boolean hasFullSuitOfArmorOn(PlayerEntity player) {
        ItemStack boots = player.getEquippedStack(EquipmentSlot.FEET);
        ItemStack leggings = player.getEquippedStack(EquipmentSlot.LEGS);
        ItemStack chestplate = player.getEquippedStack(EquipmentSlot.CHEST);
        ItemStack helmet = player.getEquippedStack(EquipmentSlot.HEAD);

        return !boots.isEmpty() && !leggings.isEmpty() && !chestplate.isEmpty() && !helmet.isEmpty() &&
               boots.getItem() instanceof IronManArmorItem &&
               leggings.getItem() instanceof IronManArmorItem &&
               chestplate.getItem() instanceof IronManArmorItem &&
               helmet.getItem() instanceof IronManArmorItem;
    }
}
