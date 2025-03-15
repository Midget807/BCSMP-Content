package com.bcsmp.bcsmp_content.main.domain_expansion.command;

import com.bcsmp.bcsmp_content.BCSMPContentMain;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

import static net.minecraft.server.command.CommandManager.literal;

public class DEModCommands {
    public static void registerCommands() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(literal("domain")
                    .requires(source -> source.hasPermissionLevel(2))
                    .then(DomainSetCommand.register(dispatcher))
                    .then(DomainQueryCommand.register(dispatcher))
            );
        });
    }

    public static void registerDomainExpansionCommands() {
        BCSMPContentMain.LOGGER.info("Domain Expansion -> Registering Mod Domains");
        registerCommands();
    }
}
