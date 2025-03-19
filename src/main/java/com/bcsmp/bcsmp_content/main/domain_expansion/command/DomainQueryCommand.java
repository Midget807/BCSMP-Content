package com.bcsmp.bcsmp_content.main.domain_expansion.command;

import com.bcsmp.bcsmp_content.main.domain_expansion.util.DomainAvailabilityState;
import com.bcsmp.bcsmp_content.main.domain_expansion.world.gen.dimension.DEModDimensions;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.world.World;

import static net.minecraft.server.command.CommandManager.*;

public class DomainQueryCommand {
    public static LiteralArgumentBuilder<ServerCommandSource> register(CommandDispatcher<ServerCommandSource> dispatcher) {
        return literal("query")
                .then(literal("dark_plains")
                        .executes(context -> getAvailability(context.getSource(), DEModDimensions.DOMAIN_1_LEVEL_KEY))
                )
                .then(literal("barrens")
                        .executes(context -> getAvailability(context.getSource(), DEModDimensions.DOMAIN_2_LEVEL_KEY))
                )
                .then(literal("tundra")
                        .executes(context -> getAvailability(context.getSource(), DEModDimensions.DOMAIN_3_LEVEL_KEY))
                )
                .then(literal("crystal_caves")
                        .executes(context -> getAvailability(context.getSource(), DEModDimensions.DOMAIN_4_LEVEL_KEY))
                );
    }
    public static int getAvailability(ServerCommandSource source, RegistryKey<World> domainKey) {
        DomainAvailabilityState state = DomainAvailabilityState.getServerState(source.getServer());
        if (state.queryAvailability(domainKey) != null) {
            for (ServerWorld ignored : source.getServer().getWorlds()) {
                if (state.queryAvailability(domainKey)) {
                    if (domainKey == DEModDimensions.DOMAIN_1_LEVEL_KEY) {
                        source.sendFeedback(() -> Text.literal("Domain: Dark Plains is available"), true);
                        break;
                    } else if (domainKey == DEModDimensions.DOMAIN_2_LEVEL_KEY) {
                        source.sendFeedback(() -> Text.literal("Domain: Barrens is available"), true);
                        break;
                    } else if (domainKey == DEModDimensions.DOMAIN_3_LEVEL_KEY) {
                        source.sendFeedback(() -> Text.literal("Domain: Tundra is available"), true);
                        break;
                    } else if (domainKey == DEModDimensions.DOMAIN_4_LEVEL_KEY) {
                        source.sendFeedback(() -> Text.literal("Domain: Crystal Cave is available"), true);
                        break;
                    }
                } else {
                    if (domainKey == DEModDimensions.DOMAIN_1_LEVEL_KEY) {
                        source.sendFeedback(() -> Text.literal("Domain: Dark Plains is not available"), true);
                        break;
                    } else if (domainKey == DEModDimensions.DOMAIN_2_LEVEL_KEY) {
                        source.sendFeedback(() -> Text.literal("Domain: Barrens is not available"), true);
                        break;
                    } else if (domainKey == DEModDimensions.DOMAIN_3_LEVEL_KEY) {
                        source.sendFeedback(() -> Text.literal("Domain: Tundra is not available"), true);
                        break;
                    } else if (domainKey == DEModDimensions.DOMAIN_4_LEVEL_KEY) {
                        source.sendFeedback(() -> Text.literal("Domain: Crystal Cave is not available"), true);
                        break;
                    }
                }
            }
        }
        return 1;
    }
}
