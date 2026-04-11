package com.superhero.mod.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class JarvisHudOverlay implements HudRenderCallback {
    private static final Identifier JARVIS_HUD = Identifier.of("superheromod", "textures/gui/jarvis_hud.png");

    @Override
    public void onHudRender(DrawContext drawContext, RenderTickCounter tickCounter) {
        MinecraftClient client = MinecraftClient.getInstance();
        
        if (client != null && client.player != null && !client.options.hudHidden) {
            ItemStack helmet = client.player.getEquippedStack(EquipmentSlot.HEAD);
            
            // Kaskın adında "ironman" geçiyorsa ekranı çiz
            if (!helmet.isEmpty() && helmet.getItem().toString().contains("ironman")) {
                int width = client.getWindow().getScaledWidth();
                int height = client.getWindow().getScaledHeight();

                // 🌟 CAM EFEKTİNİ AÇIYORUZ 🌟
                RenderSystem.enableBlend();
                RenderSystem.defaultBlendFunc();

                RenderSystem.setShader(GameRenderer::getPositionTexProgram);
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                RenderSystem.setShaderTexture(0, JARVIS_HUD);
                
                // Resmi tüm ekrana çiz
                drawContext.drawTexture(JARVIS_HUD, 0, 0, 0, 0, width, height, width, height);

                // 🌟 CAM EFEKTİNİ KAPATIYORUZ (Diğer yazılar bozulmasın diye) 🌟
                RenderSystem.disableBlend();
            }
        }
    }
}
