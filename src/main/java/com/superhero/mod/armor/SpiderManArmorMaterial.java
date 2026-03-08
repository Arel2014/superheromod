package com.superhero.mod.armor;

import net.minecraft.item.ArmorMaterial;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.Map;

public class SpiderManArmorMaterial {

    public static final RegistryEntry<ArmorMaterial> INSTANCE = RegistryEntry.of(
        new ArmorMaterial(
            Map.of(
                net.minecraft.item.ArmorItem.Type.HELMET, 3,
                net.minecraft.item.ArmorItem.Type.CHESTPLATE, 6,
                net.minecraft.item.ArmorItem.Type.LEGGINGS, 5,
                net.minecraft.item.ArmorItem.Type.BOOTS, 2
            ),
            25,
            SoundEvents.ITEM_ARMOR_EQUIP_LEATHER,
            () -> net.minecraft.recipe.Ingredient.EMPTY,
            List.of(new ArmorMaterial.Layer(Identifier.of("superheromod", "spiderman"))),
            0.5f,
            0.0f
        )
    );
}
