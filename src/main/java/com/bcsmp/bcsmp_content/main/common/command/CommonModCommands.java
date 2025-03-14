package com.bcsmp.bcsmp_content.main.common.command;

import com.bcsmp.bcsmp_content.BCSMPContentMain;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

import static net.minecraft.server.command.CommandManager.*;

public class CommonModCommands {
    public static void registerCommands() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(literal("bcsmp")
                    .requires(source -> source.hasPermissionLevel(2))
                    .then(EnableSubModCommand.register(dispatcher))
            );
        });
    }

    public static void registerCommonModCommands() {
        BCSMPContentMain.LOGGER.info("Main -> Registering Mod Commands");
        registerCommands();
    }
}
