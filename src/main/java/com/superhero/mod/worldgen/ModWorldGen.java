package com.superhero.mod.worldgen;

import com.superhero.mod.SuperHeroMod;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.Feature;

public class ModWorldGen {

    public static final Feature<IronManVillaFeature.Config> IRONMAN_VILLA_FEATURE =
            new IronManVillaFeature(IronManVillaFeature.Config.CODEC);

    public static final RegistryKey<net.minecraft.world.gen.feature.PlacedFeature> IRONMAN_VILLA_KEY =
            RegistryKey.of(RegistryKeys.PLACED_FEATURE,
                    Identifier.of(SuperHeroMod.MOD_ID, "ironman_villa"));

    public static void register() {
        Registry.register(Registries.FEATURE,
                Identifier.of(SuperHeroMod.MOD_ID, "ironman_villa"),
                IRONMAN_VILLA_FEATURE);

        BiomeModifications.addFeature(
                BiomeSelectors.foundInOverworld(),
                GenerationStep.Feature.SURFACE_STRUCTURES,
                IRONMAN_VILLA_KEY
        );
    }
}
