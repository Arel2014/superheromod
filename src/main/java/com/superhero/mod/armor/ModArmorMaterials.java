package com.superhero.mod.armor;

import com.superhero.mod.SuperHeroMod;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import java.util.List;
import java.util.Map;

public class ModArmorMaterials {
    // Örümcek Adam: Büyülenebilirlik 0!
    public static final RegistryEntry<ArmorMaterial> SPIDERMAN = register("spiderman", Map.of(
            ArmorItem.Type.HELMET, 3,
            ArmorItem.Type.CHESTPLATE, 8,
            ArmorItem.Type.LEGGINGS, 6,
            ArmorItem.Type.BOOTS, 3
    ), 0, 0.0F, 0.0F);

    // Demir Adam: Büyülenebilirlik 0! (Teknoloji büyüden üstündür)
    public static final RegistryEntry<ArmorMaterial> IRONMAN = register("ironman", Map.of(
            ArmorItem.Type.HELMET, 5,
            ArmorItem.Type.CHESTPLATE, 10,
            ArmorItem.Type.LEGGINGS, 8,
            ArmorItem.Type.BOOTS, 5
    ), 0, 3.0F, 0.2F);

    private static RegistryEntry<ArmorMaterial> register(String id, Map<ArmorItem.Type, Integer> defense, int enchantability, float toughness, float knockbackResistance) {
        List<ArmorMaterial.Layer> layers = List.of(new ArmorMaterial.Layer(Identifier.of(SuperHeroMod.MOD_ID, id)));
        ArmorMaterial material = new ArmorMaterial(defense, enchantability, SoundEvents.ITEM_ARMOR_EQUIP_IRON, () -> Ingredient.EMPTY, layers, toughness, knockbackResistance);
        return Registry.registerReference(Registries.ARMOR_MATERIAL, Identifier.of(SuperHeroMod.MOD_ID, id), material);
    }
}
