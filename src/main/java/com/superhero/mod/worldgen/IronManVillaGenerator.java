package com.superhero.mod.worldgen;

import com.superhero.mod.registry.ModItems;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.entity.EquipmentSlot;

import java.util.Random;

public class IronManVillaGenerator {

    public static void generate(StructureWorldAccess world, BlockPos origin, Random random) {
        int w = 15, h = 8, d = 15;

        // Floor
        for (int x = 0; x < w; x++)
            for (int z = 0; z < d; z++)
                world.setBlockState(origin.add(x, 0, z), Blocks.QUARTZ_BLOCK.getDefaultState(), 3);

        // Interior floor
        for (int x = 1; x < w-1; x++)
            for (int z = 1; z < d-1; z++)
                world.setBlockState(origin.add(x, 0, z), Blocks.POLISHED_BLACKSTONE.getDefaultState(), 3);

        // Walls
        for (int y = 1; y <= h; y++) {
            for (int x = 0; x < w; x++) {
                world.setBlockState(origin.add(x, y, 0), Blocks.QUARTZ_BRICKS.getDefaultState(), 3);
                world.setBlockState(origin.add(x, y, d-1), Blocks.QUARTZ_BRICKS.getDefaultState(), 3);
            }
            for (int z = 1; z < d-1; z++) {
                world.setBlockState(origin.add(0, y, z), Blocks.QUARTZ_BRICKS.getDefaultState(), 3);
                world.setBlockState(origin.add(w-1, y, z), Blocks.QUARTZ_BRICKS.getDefaultState(), 3);
            }
        }

        // Ceiling
        for (int x = 0; x < w; x++)
            for (int z = 0; z < d; z++)
                world.setBlockState(origin.add(x, h+1, z), Blocks.QUARTZ_BLOCK.getDefaultState(), 3);

        // Door opening
        for (int dy = 1; dy <= 2; dy++)
            for (int dx = 7; dx <= 8; dx++)
                world.setBlockState(origin.add(dx, dy, 0), Blocks.AIR.getDefaultState(), 3);

        // Glowstone lights
        world.setBlockState(origin.add(4, h, 4), Blocks.GLOWSTONE.getDefaultState(), 3);
        world.setBlockState(origin.add(10, h, 4), Blocks.GLOWSTONE.getDefaultState(), 3);
        world.setBlockState(origin.add(4, h, 10), Blocks.GLOWSTONE.getDefaultState(), 3);
        world.setBlockState(origin.add(10, h, 10), Blocks.GLOWSTONE.getDefaultState(), 3);

        // Pedestals
        world.setBlockState(origin.add(5, 0, 11), Blocks.IRON_BLOCK.getDefaultState(), 3);
        world.setBlockState(origin.add(9, 0, 11), Blocks.RED_CONCRETE.getDefaultState(), 3);

        // Armor stands
        spawnArmorStand(world, origin.add(5, 1, 11), true);
        spawnArmorStand(world, origin.add(9, 1, 11), false);

        // Spider-Man secret chest (30% chance)
        if (random.nextFloat() < 0.30f) {
            BlockPos chestPos = origin.add(11, 1, 11);
            world.setBlockState(chestPos, Blocks.CHEST.getDefaultState(), 3);
            if (world.getBlockEntity(chestPos) instanceof ChestBlockEntity chest) {
                chest.setStack(0, new ItemStack(ModItems.WEB_SHOOTER));
                chest.setStack(1, new ItemStack(ModItems.SPIDERMAN_HELMET));
                chest.setStack(2, new ItemStack(ModItems.SPIDERMAN_CHESTPLATE));
                chest.setStack(3, new ItemStack(ModItems.SPIDERMAN_LEGGINGS));
                chest.setStack(4, new ItemStack(ModItems.SPIDERMAN_BOOTS));
            }
        }
    }

    private static void spawnArmorStand(StructureWorldAccess world, BlockPos pos, boolean ironMan) {
        world.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
        ArmorStandEntity stand = new ArmorStandEntity(EntityType.ARMOR_STAND, world.toServerWorld());
        stand.setPosition(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);

        if (ironMan) {
            stand.equipStack(EquipmentSlot.HEAD, new ItemStack(ModItems.IRONMAN_HELMET));
            stand.equipStack(EquipmentSlot.CHEST, new ItemStack(ModItems.IRONMAN_CHESTPLATE));
            stand.equipStack(EquipmentSlot.LEGS, new ItemStack(ModItems.IRONMAN_LEGGINGS));
            stand.equipStack(EquipmentSlot.FEET, new ItemStack(ModItems.IRONMAN_BOOTS));
        } else {
            stand.equipStack(EquipmentSlot.HEAD, new ItemStack(ModItems.SPIDERMAN_HELMET));
            stand.equipStack(EquipmentSlot.CHEST, new ItemStack(ModItems.SPIDERMAN_CHESTPLATE));
            stand.equipStack(EquipmentSlot.LEGS, new ItemStack(ModItems.SPIDERMAN_LEGGINGS));
            stand.equipStack(EquipmentSlot.FEET, new ItemStack(ModItems.SPIDERMAN_BOOTS));
        }

        world.toServerWorld().spawnEntity(stand);
    }
}
