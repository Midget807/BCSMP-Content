package com.bcsmp.bcsmp_content;

import com.bcsmp.bcsmp_content.main.domain_expansion.network.DEModMessages;
import com.bcsmp.bcsmp_content.main.domain_expansion.particle.DEModParticles;
import com.bcsmp.bcsmp_content.main.domain_expansion.particle.custom.PillarActiveParticle;
import com.bcsmp.bcsmp_content.main.domain_expansion.screen.DEModScreenHandlers;
import com.bcsmp.bcsmp_content.main.domain_expansion.screen.custom.client.DomainPillarScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class BCSMPContentClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        HandledScreens.register(DEModScreenHandlers.DOMAIN_PILLAR_SCREEN_HANDLER, DomainPillarScreen::new);
        DEModMessages.registerS2CPackets();

        ParticleFactoryRegistry.getInstance().register(DEModParticles.PILLAR_ACTIVE_PARTICLE, PillarActiveParticle.Factory::new);

    }
}
