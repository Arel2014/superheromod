package com.superhero.mod.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.World;

public class IronManArmorItem extends ArmorItem {
    public IronManArmorItem(RegistryEntry<ArmorMaterial> material, Type type, Settings settings) {
        super(material, type, settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (entity instanceof PlayerEntity player) {
            
            // 1. SUNUCU TARAF: Uçuş iznini kontrol et (Eski Kodun)
            if (!world.isClient()) {
                if (hasFullSuitOfArmorOn(player)) {
                    player.getAbilities().allowFlying = true;
                    player.sendAbilitiesUpdate();
                } else if (!player.isCreative() && !player.isSpectator()) {
                    player.getAbilities().allowFlying = false;
                    player.getAbilities().flying = false;
                    player.sendAbilitiesUpdate();
                }
            }
            
            // 2. İSTEMCİ TARAF: Uçuyorsa partikülleri fışkırt (Yeni Kodumuz)
            if (world.isClient() && player.getAbilities().flying && hasFullSuitOfArmorOn(player)) {
                // Ayaklardan çıkan ana itiş alevleri
                world.addParticle(ParticleTypes.FLAME, player.getX(), player.getY() + 0.1, player.getZ(), 0, -0.2, 0);
                world.addParticle(ParticleTypes.CLOUD, player.getX(), player.getY() + 0.1, player.getZ(), 0, -0.1, 0);
                
                // Ellerden çıkan mavi denge (ışın) partikülleri
                double yaw = player.getYaw() * (Math.PI / 180.0);
                double xOffset = Math.cos(yaw) * 0.5;
                double zOffset = Math.sin(yaw) * 0.5;
                
                // Sol ve Sağ el
                world.addParticle(ParticleTypes.SOUL_FIRE_FLAME, player.getX() - xOffset, player.getY() + 1.0, player.getZ() - zOffset, 0, -0.3, 0);
                world.addParticle(ParticleTypes.SOUL_FIRE_FLAME, player.getX() + xOffset, player.getY() + 1.0, player.getZ() + zOffset, 0, -0.3, 0);
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
