package com.superhero.mod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

public class SuperHeroModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // Zırh Barı Renk Mantığı (Basitçe rengi metinle veya efektle simüle eder)
        HudRenderCallback.EVENT.register((drawContext, tickDelta) -> {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client.player != null) {
                ItemStack chest = client.player.getEquippedStack(EquipmentSlot.CHEST);
                
                if (chest.getItem().toString().contains("ironman")) {
                    // Demir Adam giyiliyken ekranın bir köşesinde şarjı göster
                    drawContext.drawText(client.textRenderer, Text.literal("§6HUD: §cIRON MAN MODE"), 10, 10, 0xFFFFFF, true);
                } else if (chest.getItem().toString().contains("spiderman")) {
                    drawContext.drawText(client.textRenderer, Text.literal("§cHUD: §9SPIDER-MAN MODE"), 10, 10, 0xFFFFFF, true);
                }
            }
        });
    }
}
