package com.superhero.mod.registry;

import com.superhero.mod.SuperHeroMod;
import com.superhero.mod.blocks.ArmorChargingStationBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class ModBlocks {

    public static final Block ARMOR_CHARGING_STATION = new ArmorChargingStationBlock(
            AbstractBlock.Settings.create()
                .mapColor(MapColor.IRON_GRAY)
                .strength(5.0f, 6.0f)
                .sounds(BlockSoundGroup.METAL)
                .requiresTool()
                .nonOpaque()
    );

    public static void registerBlocks() {
        Registry.register(Registries.BLOCK,
                Identifier.of(SuperHeroMod.MOD_ID, "armor_charging_station"),
                ARMOR_CHARGING_STATION);
    }
}
