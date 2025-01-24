package com.bcsmp.main.domain_expansion.screen;

import com.bcsmp.BCSMPS2ContentMain;
import com.bcsmp.main.domain_expansion.screen.custom.DomainPillarScreenHandler;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;

public class DEModScreenHandlers {
    public static final ScreenHandlerType<DomainPillarScreenHandler> DOMAIN_PILLAR_SCREEN_HANDLER = registerScreenHandler("domain_pillar", DomainPillarScreenHandler::create);
    private static <T extends ScreenHandler> ScreenHandlerType<T> registerScreenHandler(String name, ScreenHandlerType.Factory<T> factory) {
        return Registry.register(Registries.SCREEN_HANDLER, BCSMPS2ContentMain.domainExpansionId(name), new ScreenHandlerType<>(factory, FeatureFlags.VANILLA_FEATURES));
    }

    public static void registerDomainExpansionScreenHandlers() {
        BCSMPS2ContentMain.LOGGER.info("Domain Expansion -> Registering Mod Screen Handlers");
    }
}