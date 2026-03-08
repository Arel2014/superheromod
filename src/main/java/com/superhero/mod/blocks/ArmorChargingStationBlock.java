package com.superhero.mod.blocks;

import com.superhero.mod.registry.ModBlockEntities;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ArmorChargingStationBlock extends BlockWithEntity {

    public ArmorChargingStationBlock(Settings settings) {
        super(settings);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ArmorChargingStationBlockEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return validateTicker(type, ModBlockEntities.CHARGING_STATION,
                (w, pos, s, be) -> ArmorChargingStationBlockEntity.tick(w, pos, s, be));
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos,
                                  PlayerEntity player, BlockHitResult hit) {
        if (!world.isClient()) {
            BlockEntity be = world.getBlockEntity(pos);
            if (be instanceof ArmorChargingStationBlockEntity station) {
                station.interact(player);
            }
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public net.minecraft.util.math.BlockPos getCodec() {
        return null;
    }

    @Override
    public com.mojang.serialization.MapCodec<? extends BlockWithEntity> getCodec() {
        return net.minecraft.block.Blocks.createCodec(ArmorChargingStationBlock::new);
    }
}
