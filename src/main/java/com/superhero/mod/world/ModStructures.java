package com.superhero.mod.world;

import com.superhero.mod.SuperHeroMod;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.structure.Structure;

public class ModStructures {
    // Villanın dünyada nerede çıkabileceğini belirleyen anahtar
    public static final TagKey<Structure> IRONMAN_VILLA = TagKey.of(RegistryKeys.STRUCTURE, Identifier.of(SuperHeroMod.MOD_ID, "ironman_villa"));

    public static void register() {
        SuperHeroMod.LOGGER.info("Demir Adam Villası koordinatları taranıyor...");
    }
}
