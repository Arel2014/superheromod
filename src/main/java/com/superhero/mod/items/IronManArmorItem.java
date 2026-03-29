package com.superhero.mod.items;

import com.superhero.mod.SuperHeroMod;
import com.superhero.mod.util.ChargeHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import java.util.List;

public class IronManArmorItem extends ArmorItem {
    public IronManArmorItem(RegistryEntry<ArmorMaterial> material, Type type, Settings settings) {
        super(material, type, settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (!world.isClient && entity instanceof PlayerEntity player) {
            ItemStack chest = player.getEquippedStack(EquipmentSlot.CHEST);
            
            // %5 ŞARJ KONTROLÜ
            if (!ChargeHelper.canWork(chest)) {
                disableSystems(player);
                return;
            }

            // --- IB: BOTLAR (Uçuş ve Düşme Hasarı) ---
            if (player.getEquippedStack(EquipmentSlot.FEET).getItem() instanceof IronManArmorItem) {
                player.getAbilities().allowFlying = true;
                player.fallDistance = 0; // Düşme hasarını engelle
                // Tek başınaysa yavaş uçuş (Sadece botlar varsa)
                if (!isFullSet(player)) {
                    player.getAbilities().setFlySpeed(0.02f); 
                }
            }

            // --- IP: PANTOLON (Otomatik Saldırı) ---
            if (player.getEquippedStack(EquipmentSlot.LEGS).getItem() instanceof IronManArmorItem && SuperHeroMod.autoAttackEnabled) {
                double range = isFullSet(player) ? 8.0 : 3.0; // Full set 8 blok, sadece IP 3 blok
                handleAutoAttack(player, world, range);
            }

            // --- FULL SET KONTROLÜ (Hız ve Diğer Ayarlar) ---
            if (isFullSet(player)) {
                player.getAbilities().setFlySpeed(0.05f * SuperHeroMod.flightSpeed);
            }
            
            player.sendAbilitiesUpdate();
        }
    }

    private void handleAutoAttack(PlayerEntity player, World world, double range) {
        List<Entity> targets = world.getOtherEntities(player, player.getBoundingBox().expand(range), e -> e instanceof LivingEntity);
        for (Entity target : targets) {
            if (target instanceof LivingEntity living && player.age % 20 == 0) { // Saniyede 1 vuruş
                living.damage(world.getDamageSources().magic(), 4.0f);
            }
        }
    }

    private boolean isFullSet(PlayerEntity player) {
        return player.getEquippedStack(EquipmentSlot.HEAD).getItem() instanceof IronManArmorItem &&
               player.getEquippedStack(EquipmentSlot.CHEST).getItem() instanceof IronManArmorItem &&
               player.getEquippedStack(EquipmentSlot.LEGS).getItem() instanceof IronManArmorItem &&
               player.getEquippedStack(EquipmentSlot.FEET).getItem() instanceof IronManArmorItem;
    }

    private void disableSystems(PlayerEntity player) {
        if (!player.isCreative()) {
            player.getAbilities().allowFlying = false;
            player.getAbilities().flying = false;
            player.sendAbilitiesUpdate();
            player.sendMessage(Text.literal("§c⚡ Kritik Enerji! Zırh Sistemleri Devre Dışı."), true);
        }
    }
}
