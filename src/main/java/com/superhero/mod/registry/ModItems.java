package com.superhero.mod.registry;

import com.superhero.mod.SuperHeroMod;
import com.superhero.mod.armor.IronManArmorItem;
import com.superhero.mod.armor.IronManArmorMaterial;
import com.superhero.mod.armor.SpiderManArmorMaterial;
import com.superhero.mod.items.IronManRepulsorItem;
import com.superhero.mod.items.WebShooterItem;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {

    public static final Item WEB_SHOOTER = new WebShooterItem(
            new Item.Settings().maxCount(1).maxDamage(500));
    public static final Item IRON_MAN_REPULSOR = new IronManRepulsorItem(
            new Item.Settings().maxCount(1));

    public static final Item SPIDERMAN_HELMET = new ArmorItem(
            SpiderManArmorMaterial.INSTANCE, ArmorItem.Type.HELMET, new Item.Settings());
    public static final Item SPIDERMAN_CHESTPLATE = new ArmorItem(
            SpiderManArmorMaterial.INSTANCE, ArmorItem.Type.CHESTPLATE, new Item.Settings());
    public static final Item SPIDERMAN_LEGGINGS = new ArmorItem(
            SpiderManArmorMaterial.INSTANCE, ArmorItem.Type.LEGGINGS, new Item.Settings());
    public static final Item SPIDERMAN_BOOTS = new ArmorItem(
            SpiderManArmorMaterial.INSTANCE, ArmorItem.Type.BOOTS, new Item.Settings());

    public static final Item IRONMAN_HELMET = new IronManArmorItem(
            IronManArmorMaterial.INSTANCE, ArmorItem.Type.HELMET, new Item.Settings().maxCount(1));
    public static final Item IRONMAN_CHESTPLATE = new IronManArmorItem(
            IronManArmorMaterial.INSTANCE, ArmorItem.Type.CHESTPLATE, new Item.Settings().maxCount(1));
    public static final Item IRONMAN_LEGGINGS = new IronManArmorItem(
            IronManArmorMaterial.INSTANCE, ArmorItem.Type.LEGGINGS, new Item.Settings().maxCount(1));
    public static final Item IRONMAN_BOOTS = new IronManArmorItem(
            IronManArmorMaterial.INSTANCE, ArmorItem.Type.BOOTS, new Item.Settings().maxCount(1));

    public static final Item ARMOR_CHARGING_STATION = new BlockItem(
            ModBlocks.ARMOR_CHARGING_STATION, new Item.Settings());

    public static void registerItems() {
        Registry.register(Registries.ITEM, id("web_shooter"), WEB_SHOOTER);
        Registry.register(Registries.ITEM, id("iron_man_repulsor"), IRON_MAN_REPULSOR);
        Registry.register(Registries.ITEM, id("spiderman_helmet"), SPIDERMAN_HELMET);
        Registry.register(Registries.ITEM, id("spiderman_chestplate"), SPIDERMAN_CHESTPLATE);
        Registry.register(Registries.ITEM, id("spiderman_leggings"), SPIDERMAN_LEGGINGS);
        Registry.register(Registries.ITEM, id("spiderman_boots"), SPIDERMAN_BOOTS);
        Registry.register(Registries.ITEM, id("ironman_helmet"), IRONMAN_HELMET);
        Registry.register(Registries.ITEM, id("ironman_chestplate"), IRONMAN_CHESTPLATE);
        Registry.register(Registries.ITEM, id("ironman_leggings"), IRONMAN_LEGGINGS);
        Registry.register(Registries.ITEM, id("ironman_boots"), IRONMAN_BOOTS);
        Registry.register(Registries.ITEM, id("armor_charging_station"), ARMOR_CHARGING_STATION);
    }

    private static Identifier id(String path) {
        return Identifier.of(SuperHeroMod.MOD_ID, path);
    }
}
