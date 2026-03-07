package com.superhero.mod.registry;

import com.superhero.mod.SuperHeroMod;
import com.superhero.mod.blocks.ArmorChargingStationBlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {

    public static final BlockEntityType<ArmorChargingStationBlockEntity> CHARGING_STATION =
            BlockEntityType.Builder.create(ArmorChargingStationBlockEntity::new,
                    ModBlocks.ARMOR_CHARGING_STATION).build();

    public static void registerBlockEntities() {
        Registry.register(Registries.BLOCK_ENTITY_TYPE,
                Identifier.of(SuperHeroMod.MOD_ID, "charging_station"),
                CHARGING_STATION);
    }
}
