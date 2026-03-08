package com.superhero.mod.armor;

import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

import java.util.List;

public class IronManArmorMaterial implements ArmorMaterial {

    public static final RegistryEntry<ArmorMaterial> INSTANCE = ArmorMaterial.createEntry(
        new IronManArmorMaterialImpl()
    );

    public static class IronManArmorMaterialImpl implements ArmorMaterial {
        @Override
        public int getDurability(ArmorItem.Type type) {
            return switch (type) {
                case HELMET -> 400;
                case CHESTPLATE -> 600;
                case LEGGINGS -> 550;
                case BOOTS -> 450;
                default -> 400;
            };
        }
        @Override
        public int getProtection(ArmorItem.Type type) {
            return switch (type) {
                case HELMET -> 4;
                case CHESTPLATE -> 9;
                case LEGGINGS -> 7;
                case BOOTS -> 4;
                default -> 3;
            };
        }
        @Override public int getEnchantability() { return 10; }
        @Override public RegistryEntry<SoundEvent> getEquipSound() { return SoundEvents.ITEM_ARMOR_EQUIP_IRON; }
        @Override public Ingredient getRepairIngredient() { return Ingredient.EMPTY; }
        @Override public float getToughness() { return 2.0f; }
        @Override public float getKnockbackResistance() { return 0.1f; }
        @Override public List<Layer> getLayers() {
            return List.of(new Layer(Identifier.of("superheromod", "iron_man")));
        }
    }
}
