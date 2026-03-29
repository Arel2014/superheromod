package com.superhero.mod.registry;

import com.superhero.mod.SuperHeroMod;
import com.superhero.mod.items.WebShooterItem;
import com.superhero.mod.items.IronManArmorItem;
import com.superhero.mod.items.IronManRepulsorItem;
import com.superhero.mod.armor.ModArmorMaterials;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
    // Örümcek Adam Eşyaları
    public static final Item WEB_SHOOTER = new WebShooterItem(new Item.Settings().maxCount(1));
    
    // Ironman Silahı (Senin istediğin Sniper/Taramalı modlu ışın)
    public static final Item IRONMAN_REPULSOR = new IronManRepulsorItem(new Item.Settings().maxCount(1));
    
    // --- IRONMAN ZIRH PARÇALARI ---
    // IB: Botlar (Uçuş) | IP: Pantolon (Menü/Saldırı) | IG: Göğüslük (Güç) | IK: Kask (HUD)
    public static final Item IRONMAN_HELMET = new IronManArmorItem(ModArmorMaterials.IRONMAN, ArmorItem.Type.HELMET, new Item.Settings().maxCount(1));
    public static final Item IRONMAN_CHESTPLATE = new IronManArmorItem(ModArmorMaterials.IRONMAN, ArmorItem.Type.CHESTPLATE, new Item.Settings().maxCount(1));
    public static final Item IRONMAN_LEGGINGS = new IronManArmorItem(ModArmorMaterials.IRONMAN, ArmorItem.Type.LEGGINGS, new Item.Settings().maxCount(1));
    public static final Item IRONMAN_BOOTS = new IronManArmorItem(ModArmorMaterials.IRONMAN, ArmorItem.Type.BOOTS, new Item.Settings().maxCount(1));

    public static void registerItems() {
        Registry.register(Registries.ITEM, Identifier.of(SuperHeroMod.MOD_ID, "web_shooter"), WEB_SHOOTER);
        Registry.register(Registries.ITEM, Identifier.of(SuperHeroMod.MOD_ID, "ironman_repulsor"), IRONMAN_REPULSOR);
        Registry.register(Registries.ITEM, Identifier.of(SuperHeroMod.MOD_ID, "ironman_helmet"), IRONMAN_HELMET);
        Registry.register(Registries.ITEM, Identifier.of(SuperHeroMod.MOD_ID, "ironman_chestplate"), IRONMAN_CHESTPLATE);
        Registry.register(Registries.ITEM, Identifier.of(SuperHeroMod.MOD_ID, "ironman_leggings"), IRONMAN_LEGGINGS);
        Registry.register(Registries.ITEM, Identifier.of(SuperHeroMod.MOD_ID, "ironman_boots"), IRONMAN_BOOTS);
    }
}
