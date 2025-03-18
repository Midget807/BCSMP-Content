package com.bcsmp.bcsmp_content.main.domain_expansion.screen;

import com.bcsmp.bcsmp_content.BCSMPContentMain;
import com.bcsmp.bcsmp_content.main.domain_expansion.screen.custom.DomainPillarScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.math.BlockPos;

public class DEModScreenHandlers {
    public static final ScreenHandlerType<DomainPillarScreenHandler> DOMAIN_PILLAR_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, BCSMPContentMain.domainExpansionId("domain_pillar_screen"),
                    new ExtendedScreenHandlerType<>(DomainPillarScreenHandler::new, BlockPos.PACKET_CODEC));


    public static void registerDomainExpansionScreenHandlers() {
        BCSMPContentMain.LOGGER.info("Domain Expansion -> Registering Mod Screen Handlers");
    }
}