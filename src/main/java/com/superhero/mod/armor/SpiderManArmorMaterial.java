package com.superhero.mod.armor;

import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

import java.util.List;

public class SpiderManArmorMaterial implements ArmorMaterial {

    public static final SpiderManArmorMaterial INSTANCE = new SpiderManArmorMaterial();

    @Override
    public int getDurability(ArmorItem.Type type) {
        return switch (type) {
            case HELMET -> 220;
            case CHESTPLATE -> 320;
            case LEGGINGS -> 300;
            case BOOTS -> 260;
            default -> 200;
        };
    }

    @Override
    public int getProtection(ArmorItem.Type type) {
        return switch (type) {
            case HELMET -> 3;
            case CHESTPLATE -> 6;
            case LEGGINGS -> 5;
            case BOOTS -> 2;
            default -> 2;
        };
    }

    @Override
    public int getEnchantability() { return 25; }

    @Override
    public RegistryEntry<SoundEvent> getEquipSound() {
        return SoundEvents.ITEM_ARMOR_EQUIP_LEATHER;
    }

    @Override
    public Ingredient getRepairIngredient() { return Ingredient.EMPTY; }

    @Override
    public float getToughness() { return 0.5f; }

    @Override
    public float getKnockbackResistance() { return 0.0f; }

    @Override
    public List<Layer> getLayers() {
        return List.of(new Layer(net.minecraft.util.Identifier.of("superheromod", "spiderman")));
    }
}
