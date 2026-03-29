package com.superhero.mod.items;

import com.superhero.mod.registry.ModItems;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class WebShooterItem extends Item {
    public WebShooterItem(Settings settings) { super(settings); }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        
        // --- KOŞUL: TAM SET VEYA ELYTRA KONTROLÜ ---
        boolean hasFullSpidey = 
            player.getEquippedStack(EquipmentSlot.HEAD).getItem() == ModItems.SPIDERMAN_HELMET &&
            player.getEquippedStack(EquipmentSlot.CHEST).getItem() == ModItems.SPIDERMAN_CHESTPLATE &&
            player.getEquippedStack(EquipmentSlot.LEGS).getItem() == ModItems.SPIDERMAN_LEGGINGS &&
            player.getEquippedStack(EquipmentSlot.FEET).getItem() == ModItems.SPIDERMAN_BOOTS;

        boolean hasElytra = player.getEquippedStack(EquipmentSlot.CHEST).getItem() == Items.ELYTRA;
        boolean isProtected = hasFullSpidey || hasElytra;

        HitResult hit = player.raycast(35.0, 0, false);

        if (hit.getType() == HitResult.Type.BLOCK) {
            Vec3d hitPos = hit.getPos();
            Vec3d playerPos = player.getPos();
            Vec3d boostDir = hitPos.subtract(playerPos).normalize();

            if (hand == Hand.OFF_HAND) { // SOL EL: HIZLI ÇEKİM (Elytra ile ultra hızlı)
                double speed = hasElytra ? 2.8 : 1.4;
                player.setVelocity(boostDir.multiply(speed).add(0, 0.2, 0));
                player.velocityModified = true;
                world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_FISHING_BOBBER_RETRIEVE, SoundCategory.PLAYERS, 1.0f, 1.2f);
            } else { // SAĞ EL: SALLANMA VE AĞ ATMA
                player.addVelocity(boostDir.x * 0.6, 0.4, boostDir.z * 0.6);
                player.velocityModified = true;
                
                BlockHitResult blockHit = (BlockHitResult) hit;
                net.minecraft.util.math.BlockPos pos = blockHit.getBlockPos().offset(blockHit.getSide());
                if (world.getBlockState(pos).isAir()) {
                    world.setBlockState(pos, Blocks.COBWEB.getDefaultState());
                }
            }

            // --- KOŞUL: KORUMA YOKSA HASAR AL ---
            if (!isProtected) {
                player.damage(world.getDamageSources().fall(), 3.0f);
            }
        }
        return TypedActionResult.success(stack, world.isClient());
    }
}
