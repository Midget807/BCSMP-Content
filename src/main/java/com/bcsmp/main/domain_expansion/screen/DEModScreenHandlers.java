package com.bcsmp.main.domain_expansion.screen;

public class DEModScreenHandlers {
    public static final ScreenHandlerType<DomainWindowScreenHandler> DOMAIN_WINDOW_SCREEN_HANDLER =
        Registry.register(Registries.SCREEN_HANDLER, BCSMPS2ContentMain.domainExpansionId("domain_window_screen_handler"),
            new ScreenHandlerType<>(DomainWindowScreenHandler::new, FeatureFlags.VANILLA_FEATURES));

    public static void registerDomainExpansionScreenHandlers() {
        BCSMPS2ContentMain.LOGGER.info("Domain Expansion -> Registering Mod Screen Handlers");
    }
}