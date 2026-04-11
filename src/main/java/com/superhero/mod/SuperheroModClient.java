package com.superhero.mod;

import com.superhero.mod.client.JarvisHudOverlay;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

public class SuperheroModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // JARVIS sistemini başlatıyoruz
        HudRenderCallback.EVENT.register(new JarvisHudOverlay());
    }
}
