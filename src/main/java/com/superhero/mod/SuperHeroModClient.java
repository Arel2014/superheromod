package com.superhero.mod;

import com.superhero.mod.client.JarvisHudOverlay;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

public class SuperheroModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // JARVIS ekranını (HUD) oyuna kaydediyoruz
        // Bu kod, oyuncu Iron Man kaskını taktığında ekranı çizer
        HudRenderCallback.EVENT.register(new JarvisHudOverlay());
        
        // Buraya gelecekte Spider-Man ağ fırlatma sesleri 
        // veya diğer görsel efektleri de ekleyebiliriz!
    }
}
