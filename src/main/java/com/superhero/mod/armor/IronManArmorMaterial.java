package com.superhero.mod.armor;

import net.minecraft.item.ArmorMaterial;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.Map;

public class IronManArmorMaterial {

    public static final RegistryEntry<ArmorMaterial> INSTANCE = RegistryEntry.of(
        new ArmorMaterial(
            Map.of(
                net.minecraft.item.ArmorItem.Type.HELMET, 4,
                net.minecraft.item.ArmorItem.Type.CHESTPLATE, 9,
                net.minecraft.item.ArmorItem.Type.LEGGINGS, 7,
                net.minecraft.item.ArmorItem.Type.BOOTS, 4
            ),
            10,
            SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> net.minecraft.recipe.Ingredient.EMPTY,
            List.of(new ArmorMaterial.Layer(Identifier.of("superheromod", "iron_man"))),
            2.0f,
            0.1f
        )
    );
}
