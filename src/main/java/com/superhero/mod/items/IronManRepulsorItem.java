package com.superhero.mod.items;

import com.superhero.mod.SuperHeroMod;
import com.superhero.mod.util.ChargeHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.World.ExplosionSourceType;

public class IronManRepulsorItem extends Item {
    public IronManRepulsorItem(Settings settings) { super(settings); }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        ItemStack chest = player.getEquippedStack(EquipmentSlot.CHEST);
        
        // --- KOŞUL: FULL SET KONTROLÜ ---
        boolean isFullSet = isFullIronMan(player);
        
        // --- KOŞUL: ŞARJ KONTROLÜ ---
        if (!ChargeHelper.canWork(chest) && isFullSet) {
            return TypedActionResult.fail(stack);
        }

        if (!world.isClient) {
            ServerWorld serverWorld = (ServerWorld) world;
            Vec3d start = player.getEyePos();
            Vec3d dir = player.getRotationVec(1.0f);

            // --- AYAR: SNIPER VS TARAMALI ---
            float damage = SuperHeroMod.isSniperMode ? 15.0f : 4.0f;
            int cooldown = SuperHeroMod.isSniperMode ? 40 : 4; // Sniper yavaş, Taramalı çok hızlı
            
            // Atış Menzili ve Görsel
            for (int i = 1; i < 30; i++) {
                Vec3d point = start.add(dir.multiply(i));
                serverWorld.spawnParticles(ParticleTypes.END_ROD, point.x, point.y, point.z, 2, 0.1, 0.1, 0.1, 0.0);
                
                // Vuruş Kontrolü
                var targets = world.getOtherEntities(player, player.getBoundingBox().expand(30), e -> e instanceof LivingEntity);
                for (var target : targets) {
                    if (target.getBoundingBox().contains(point)) {
                        target.damage(world.getDamageSources().magic(), isFullSet ? damage : 2.0f); // Zırhsızsa çok az hasar
                        
                        // --- KOŞUL: FULL SETSE PATLAMA YAP ---
                        if (isFullSet && SuperHeroMod.isSniperMode) {
                            world.createExplosion(null, target.getX(), target.getY(), target.getZ(), 2.0f, false, ExplosionSourceType.NONE);
                        }
                        break;
                    }
                }
            }

            world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_FIREWORK_ROCKET_BLAST, SoundCategory.PLAYERS, 1.0f, 2.0f);
            player.getItemCooldownManager().set(this, cooldown);
            if (isFullSet) ChargeHelper.drain(chest, 0.2f); // Her atış şarj yer
        }

        return TypedActionResult.success(stack);
    }

    private boolean isFullIronMan(PlayerEntity player) {
        return player.getEquippedStack(EquipmentSlot.HEAD).getItem().toString().contains("ironman") &&
               player.getEquippedStack(EquipmentSlot.CHEST).getItem().toString().contains("ironman") &&
               player.getEquippedStack(EquipmentSlot.LEGS).getItem().toString().contains("ironman") &&
               player.getEquippedStack(EquipmentSlot.FEET).getItem().toString().contains("ironman");
    }
}
