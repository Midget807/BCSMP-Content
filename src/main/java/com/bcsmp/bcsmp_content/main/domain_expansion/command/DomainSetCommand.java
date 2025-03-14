package com.bcsmp.bcsmp_content.main.domain_expansion.command;

import com.bcsmp.bcsmp_content.main.domain_expansion.util.DomainAvailabilityState;
import com.bcsmp.bcsmp_content.main.domain_expansion.worldgen.dimension.DEModDimensions;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.world.World;

import static net.minecraft.server.command.CommandManager.*;

public class DomainSetCommand {
    public static LiteralArgumentBuilder<ServerCommandSource> register(CommandDispatcher<ServerCommandSource> dispatcher) {
        return literal("set")
                .then(literal("available")
                        .then(literal("all")
                                .executes(context -> {
                                    executeSetDomainAvailability(context.getSource(), DEModDimensions.DOMAIN_1_LEVEL_KEY, true);
                                    executeSetDomainAvailability(context.getSource(), DEModDimensions.DOMAIN_2_LEVEL_KEY, true);
                                    executeSetDomainAvailability(context.getSource(), DEModDimensions.DOMAIN_3_LEVEL_KEY, true);
                                    executeSetDomainAvailability(context.getSource(), DEModDimensions.DOMAIN_4_LEVEL_KEY, true);
                                    context.getSource().sendFeedback(() -> Text.literal("All domains are now available"), true);
                                    return 1;
                                })
                        )
                        .then(literal("dark_plains")
                                .executes(context -> {
                                    executeSetDomainAvailability(context.getSource(), DEModDimensions.DOMAIN_1_LEVEL_KEY, true);
                                    return sendSetAvailableFeedback(context.getSource(), DEModDimensions.DOMAIN_1_LEVEL_KEY, true);
                                })
                        )
                        .then(literal("barrens")
                                .executes(context -> {
                                    executeSetDomainAvailability(context.getSource(), DEModDimensions.DOMAIN_2_LEVEL_KEY, true);
                                    return sendSetAvailableFeedback(context.getSource(), DEModDimensions.DOMAIN_2_LEVEL_KEY, true);
                                })
                        )
                        .then(literal("tundra")
                                .executes(context -> {
                                    executeSetDomainAvailability(context.getSource(), DEModDimensions.DOMAIN_3_LEVEL_KEY, true);
                                    return sendSetAvailableFeedback(context.getSource(), DEModDimensions.DOMAIN_3_LEVEL_KEY, true);
                                })
                        )
                        .then(literal("crystal_caves")
                                .executes(context -> {
                                    executeSetDomainAvailability(context.getSource(), DEModDimensions.DOMAIN_4_LEVEL_KEY, true);
                                    return sendSetAvailableFeedback(context.getSource(), DEModDimensions.DOMAIN_4_LEVEL_KEY, true);
                                })
                        )
                )
                .then(literal("unavailable")
                        .then(literal("all")
                                .executes(context -> {
                                    executeSetDomainAvailability(context.getSource(), DEModDimensions.DOMAIN_1_LEVEL_KEY, false);
                                    executeSetDomainAvailability(context.getSource(), DEModDimensions.DOMAIN_2_LEVEL_KEY, false);
                                    executeSetDomainAvailability(context.getSource(), DEModDimensions.DOMAIN_3_LEVEL_KEY, false);
                                    executeSetDomainAvailability(context.getSource(), DEModDimensions.DOMAIN_4_LEVEL_KEY, false);
                                    context.getSource().sendFeedback(() -> Text.literal("All domains are no longer available"), true);
                                    return 1;
                                })
                        )
                        .then(literal("dark_plains")
                                .executes(context -> {
                                    executeSetDomainAvailability(context.getSource(), DEModDimensions.DOMAIN_1_LEVEL_KEY, false);
                                    return sendSetAvailableFeedback(context.getSource(), DEModDimensions.DOMAIN_1_LEVEL_KEY, false);
                                })
                        )
                        .then(literal("barrens")
                                .executes(context -> {
                                    executeSetDomainAvailability(context.getSource(), DEModDimensions.DOMAIN_2_LEVEL_KEY, false);
                                    return sendSetAvailableFeedback(context.getSource(), DEModDimensions.DOMAIN_2_LEVEL_KEY, false);
                                })
                        )
                        .then(literal("tundra")
                                .executes(context -> {
                                    executeSetDomainAvailability(context.getSource(), DEModDimensions.DOMAIN_3_LEVEL_KEY, false);
                                    return sendSetAvailableFeedback(context.getSource(), DEModDimensions.DOMAIN_3_LEVEL_KEY, false);
                                })
                        )
                        .then(literal("crystal_caves")
                                .executes(context -> {
                                    executeSetDomainAvailability(context.getSource(), DEModDimensions.DOMAIN_4_LEVEL_KEY, false);
                                    return sendSetAvailableFeedback(context.getSource(), DEModDimensions.DOMAIN_4_LEVEL_KEY, false);
                                })
                        )
                );
    }

    public static int sendSetAvailableFeedback(ServerCommandSource source, RegistryKey<World> domainKey, boolean isAvailable) {
        if (domainKey == DEModDimensions.DOMAIN_1_LEVEL_KEY) {
            if (isAvailable) {
                source.sendFeedback(() -> Text.literal("Domain: Dark Plains is now available"), true);

            } else {
                source.sendFeedback(() -> Text.literal("Domain: Dark Plains is no longer available"), true);
            }
        } else if (domainKey == DEModDimensions.DOMAIN_2_LEVEL_KEY) {
            if (isAvailable) {
                source.sendFeedback(() -> Text.literal("Domain: Barrens is now available"), true);
            } else {
                source.sendFeedback(() -> Text.literal("Domain: Barrens is no longer available"), true);
            }
        } else if (domainKey == DEModDimensions.DOMAIN_3_LEVEL_KEY) {
            if (isAvailable) {
                source.sendFeedback(() -> Text.literal("Domain: Tundra is now available"), true);
            } else {
                source.sendFeedback(() -> Text.literal("Domain: Tundra is no longer available"), true);
            }
        } else if (domainKey == DEModDimensions.DOMAIN_4_LEVEL_KEY) {
            if (isAvailable) {
                source.sendFeedback(() -> Text.literal("Domain: Crystal Cave is now available"), true);
            } else {
                source.sendFeedback(() -> Text.literal("Domain: Crystal Cave is no longer available"), true);
            }
        }
        return 1;
    }

    public static void executeSetDomainAvailability(ServerCommandSource source, RegistryKey<World> domainKey, boolean shouldBeAvailable) {
        DomainAvailabilityState state = DomainAvailabilityState.getServerState(source.getServer());
        for (ServerWorld serverWorld : source.getServer().getWorlds()) {
            if (serverWorld.getRegistryKey() == domainKey) {
                if (shouldBeAvailable) {
                    state.setDomainAvailable(domainKey, source.getServer());
                } else {
                    state.setDomainUnavailable(domainKey, source.getServer());
                }
            }
        }
    }
}
