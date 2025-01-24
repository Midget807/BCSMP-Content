package com.bcsmp;

import com.bcsmp.main.domain_expansion.network.DEModMessages;
import com.bcsmp.main.domain_expansion.screen.DEModScreenHandlers;
import com.bcsmp.main.domain_expansion.screen.custom.client.DomainPillarScreen;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class BCSMPS2ContentClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        HandledScreens.register(DEModScreenHandlers.DOMAIN_PILLAR_SCREEN_HANDLER, DomainPillarScreen::new);
        DEModMessages.registerS2CPackets();
    }
}
