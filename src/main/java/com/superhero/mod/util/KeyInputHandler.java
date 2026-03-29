package com.superhero.mod.util;

import com.superhero.mod.SuperHeroMod;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class KeyInputHandler {
    public static KeyBinding menuKey;

    public static void register() {
        menuKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.superheromod.menu", 
                InputUtil.Type.KEYSYM, 
                GLFW.GLFW_KEY_I, // Ironman Menüsü için "I" tuşu
                "category.superheromod"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (menuKey.wasPressed()) {
                if (client.player != null && client.player.getEquippedStack(EquipmentSlot.LEGS).getItem().toString().contains("ironman")) {
                    // Taramalı/Sniper arası geçiş yap (Senin isteğin!)
                    SuperHeroMod.isSniperMode = !SuperHeroMod.isSniperMode;
                    String mode = SuperHeroMod.isSniperMode ? "§cSNIPER (Yüksek Hasar)" : "§eTARAMALI (Hızlı Ateş)";
                    client.player.sendMessage(Text.literal("§6Ironman Silah Modu: " + mode), true);
                }
            }
        });
    }
}
