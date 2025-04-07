package com.bcsmp.bcsmp_content.main.common.command;

import com.bcsmp.bcsmp_content.BCSMPContentMain;
import com.bcsmp.bcsmp_content.main.common.util.SubModState;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import static net.minecraft.server.command.CommandManager.*;

public class EnableSubModCommand {
    public static LiteralArgumentBuilder<ServerCommandSource> register(CommandDispatcher<ServerCommandSource> dispatcher) {
        return literal("mods")
                .then(literal("enable")
                        .then(literal("all")
                                .executes(context -> {
                                    executeEnableMod(context.getSource(), BCSMPContentMain.DE_MOD_ID, true);
                                    executeEnableMod(context.getSource(), BCSMPContentMain.CF_MOD_ID, true);
                                    executeEnableMod(context.getSource(), BCSMPContentMain.DR_MOD_ID, true);
                                    executeEnableMod(context.getSource(), BCSMPContentMain.AC_MOD_ID, true);
                                    return 1;
                                })
                        )
                        .then(literal("domain_expansion")
                                .executes(context -> {
                                    executeEnableMod(context.getSource(), BCSMPContentMain.DE_MOD_ID, true);
                                    return 1;
                                })
                        )
                        .then(literal("charter_fix")
                                .executes(context -> {
                                    executeEnableMod(context.getSource(), BCSMPContentMain.CF_MOD_ID, true);
                                    return 1;
                                })
                        )
                        .then(literal("domain_robes")
                                .executes(context -> {
                                    executeEnableMod(context.getSource(), BCSMPContentMain.DR_MOD_ID, true);
                                    return 1;
                                })
                        )
                        .then(literal("arcanus_clothes")
                                .executes(context -> {
                                    executeEnableMod(context.getSource(), BCSMPContentMain.AC_MOD_ID, true);
                                    return 1;
                                })
                        )
                )
                .then(literal("disable")
                        .then(literal("all")
                                .executes(context -> {
                                    executeEnableMod(context.getSource(), BCSMPContentMain.DE_MOD_ID, false);
                                    executeEnableMod(context.getSource(), BCSMPContentMain.CF_MOD_ID, false);
                                    executeEnableMod(context.getSource(), BCSMPContentMain.DR_MOD_ID, false);
                                    executeEnableMod(context.getSource(), BCSMPContentMain.AC_MOD_ID, false);
                                    return 1;
                                })
                        )
                        .then(literal("domain_expansion")
                                .executes(context -> {
                                    executeEnableMod(context.getSource(), BCSMPContentMain.DE_MOD_ID, false);
                                    return 1;
                                })
                        )
                        .then(literal("charter_fix")
                                .executes(context -> {
                                    executeEnableMod(context.getSource(), BCSMPContentMain.CF_MOD_ID, false);
                                    return 1;
                                })
                        )
                        .then(literal("domain_robes")
                                .executes(context -> {
                                    executeEnableMod(context.getSource(), BCSMPContentMain.DR_MOD_ID, false);
                                    return 1;
                                })
                        )
                        .then(literal("arcanus_clothes")
                                .executes(context -> {
                                    executeEnableMod(context.getSource(), BCSMPContentMain.AC_MOD_ID, false);
                                    return 1;
                                })
                        )
                )
                .then(literal("query")
                        .then(literal("all")
                                .executes(context -> {
                                    queryModEnabled(context.getSource(), BCSMPContentMain.DE_MOD_ID);
                                    queryModEnabled(context.getSource(), BCSMPContentMain.CF_MOD_ID);
                                    queryModEnabled(context.getSource(), BCSMPContentMain.DR_MOD_ID);
                                    queryModEnabled(context.getSource(), BCSMPContentMain.AC_MOD_ID);
                                    return 1;
                                })
                        )
                        .then(literal("domain_expansion")
                                .executes(context -> {
                                    queryModEnabled(context.getSource(), BCSMPContentMain.DE_MOD_ID);
                                    return 1;
                                })
                        )
                        .then(literal("charter_fix")
                                .executes(context -> {
                                    queryModEnabled(context.getSource(), BCSMPContentMain.CF_MOD_ID);
                                    return 1;
                                })
                        )
                        .then(literal("domain_robes")
                                .executes(context -> {
                                    queryModEnabled(context.getSource(), BCSMPContentMain.DR_MOD_ID);
                                    return 1;
                                })
                        )
                        .then(literal("arcanus_clothes")
                                .executes(context -> {
                                    queryModEnabled(context.getSource(), BCSMPContentMain.AC_MOD_ID);
                                    return 1;
                                })
                        )
                );
    }

    public static void queryModEnabled(ServerCommandSource source, String subModId) {
        SubModState state = SubModState.getServerState(source.getServer());
        if (subModId.equals(BCSMPContentMain.DE_MOD_ID)) {
            source.sendFeedback(() -> Text.literal("Domain Expansion is enabled: " + state.getDomainExpansionModEnabled()), true);
        } else if (subModId.equals(BCSMPContentMain.CF_MOD_ID)) {
            source.sendFeedback(() -> Text.literal("Charter Fix is enabled: " + state.getCharterFixModEnabled()), true);
        } else if (subModId.equals(BCSMPContentMain.DR_MOD_ID)) {
            source.sendFeedback(() -> Text.literal("Domain Robes is enabled: " + state.getDomainRobesModEnabled()), true);
        } else if (subModId.equals(BCSMPContentMain.AC_MOD_ID)) {
            source.sendFeedback(() -> Text.literal("Arcanus Clothes is enabled: " + state.getArcanusClothesModEnabled()), true);
        }
    }

    public static void executeEnableMod(ServerCommandSource source, String subModId, boolean enabled) {
        SubModState state = SubModState.getServerState(source.getServer());
        if (subModId.equals(BCSMPContentMain.DE_MOD_ID)) {
            if (enabled) {
                state.setDomainExpansionModEnabled(true);
            } else {
                state.setDomainExpansionModEnabled(false);
            }
            source.sendFeedback(() -> Text.literal("Domain Expansion is enabled: " + enabled), true);
        } else if (subModId.equals(BCSMPContentMain.CF_MOD_ID)) {
            if (enabled) {
                state.setCharterFixModEnabled(true);
            } else {
                state.setCharterFixModEnabled(false);
            }
            source.sendFeedback(() -> Text.literal("Charter Fix is enabled: " + enabled), true);
        } else if (subModId.equals(BCSMPContentMain.DR_MOD_ID)) {
            if (enabled) {
                state.setDomainRobesModEnabled(true);
            } else {
                state.setDomainRobesModEnabled(false);
            }
            source.sendFeedback(() -> Text.literal("Domain Robes is enabled: " + enabled), true);
        } else if (subModId.equals(BCSMPContentMain.AC_MOD_ID)) {
            if (enabled) {
                state.setArcanusClothesModEnabled(true);
            } else {
                state.setArcanusClothesModEnabled(false);
            }
            source.sendFeedback(() -> Text.literal("Arcanus Clothes is enabled: " + enabled), true);
        }
    }
}
