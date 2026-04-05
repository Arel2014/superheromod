package com.superhero.mod.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.superhero.mod.items.IronManArmorItem;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class JarvisHudOverlay implements HudRenderCallback {
    // 1.21.1 için doğru resim yolu
    private static final Identifier JARVIS_HUD = Identifier.of("superheromod", "textures/gui/jarvis_hud.png");

    @Override
    public void onHudRender(DrawContext drawContext, float tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();
        
        if (client != null && client.player != null && !client.options.hudHidden) {
            ItemStack helmet = client.player.getEquippedStack(EquipmentSlot.HEAD);
            
            // Eğer kafasında senin yaptığın Iron Man kaskı varsa
            if (helmet.getItem() instanceof IronManArmorItem) {
                int width = client.getWindow().getScaledWidth();
                int height = client.getWindow().getScaledHeight();

                RenderSystem.setShader(GameRenderer::getPositionTexProgram);
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                RenderSystem.setShaderTexture(0, JARVIS_HUD);
                
                // Resmi tüm ekrana yayar
                drawContext.drawTexture(JARVIS_HUD, 0, 0, 0, 0, width, height, width, height);
            }
        }
    }
}
