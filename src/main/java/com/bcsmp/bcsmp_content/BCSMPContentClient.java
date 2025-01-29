package com.bcsmp.bcsmp_content;

import com.bcsmp.bcsmp_content.main.domain_expansion.network.DEModMessages;
import com.bcsmp.bcsmp_content.main.domain_expansion.screen.DEModScreenHandlers;
import com.bcsmp.bcsmp_content.main.domain_expansion.screen.custom.client.DomainPillarScreen;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class BCSMPContentClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        HandledScreens.register(DEModScreenHandlers.DOMAIN_PILLAR_SCREEN_HANDLER, DomainPillarScreen::new);
        DEModMessages.registerS2CPackets();
    }
}
