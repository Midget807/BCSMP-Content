package com.bcsmp.bcsmp_content.main.domain_expansion.command;

import com.bcsmp.bcsmp_content.main.domain_expansion.worldgen.dimension.DEModDimensions;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec2f;
import net.minecraft.world.World;
import net.minecraft.world.border.WorldBorder;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;

import static net.minecraft.server.command.CommandManager.*;
import static com.mojang.brigadier.arguments.IntegerArgumentType.*;
import static com.mojang.brigadier.arguments.DoubleArgumentType.*;
import static net.minecraft.command.argument.Vec2ArgumentType.*;

public class DomainBorderCommand {
    private static final SimpleCommandExceptionType CENTER_FAILED_EXCEPTION = new SimpleCommandExceptionType(
            Text.translatable("commands.worldborder.center.failed")
    );
    private static final SimpleCommandExceptionType SET_FAILED_NO_CHANGE_EXCEPTION = new SimpleCommandExceptionType(
            Text.translatable("commands.worldborder.set.failed.nochange")
    );
    private static final SimpleCommandExceptionType SET_FAILED_SMALL_EXCEPTION = new SimpleCommandExceptionType(
            Text.translatable("commands.worldborder.set.failed.small")
    );
    private static final SimpleCommandExceptionType SET_FAILED_BIG_EXCEPTION = new SimpleCommandExceptionType(
            Text.translatable("commands.worldborder.set.failed.big", 5.999997E7F)
    );
    private static final SimpleCommandExceptionType SET_FAILED_FAR_EXCEPTION = new SimpleCommandExceptionType(
            Text.translatable("commands.worldborder.set.failed.far", 2.9999984E7)
    );
    private static final SimpleCommandExceptionType WARNING_TIME_FAILED_EXCEPTION = new SimpleCommandExceptionType(
            Text.translatable("commands.worldborder.warning.time.failed")
    );
    private static final SimpleCommandExceptionType WARNING_DISTANCE_FAILED_EXCEPTION = new SimpleCommandExceptionType(
            Text.translatable("commands.worldborder.warning.distance.failed")
    );
    private static final SimpleCommandExceptionType DAMAGE_BUFFER_FAILED_EXCEPTION = new SimpleCommandExceptionType(
            Text.translatable("commands.worldborder.damage.buffer.failed")
    );
    private static final SimpleCommandExceptionType DAMAGE_AMOUNT_FAILED_EXCEPTION = new SimpleCommandExceptionType(
            Text.translatable("commands.worldborder.damage.amount.failed")
    );
    public static LiteralArgumentBuilder<ServerCommandSource> register(CommandDispatcher<ServerCommandSource> dispatcher) {
        return literal("border")
                .then(literal("add")
                        .then(literal("all")
                                .then(argument("radius", doubleArg(-5.999997E7F, 5.999997E7F))
                                        .executes(context -> {
                                            for (ServerWorld serverWorld : context.getSource().getServer().getWorlds()) {
                                                if (serverWorld.getRegistryKey() == DEModDimensions.DOMAIN_1_LEVEL_KEY
                                                        || serverWorld.getRegistryKey() == DEModDimensions.DOMAIN_2_LEVEL_KEY
                                                        || serverWorld.getRegistryKey() == DEModDimensions.DOMAIN_3_LEVEL_KEY
                                                        || serverWorld.getRegistryKey() == DEModDimensions.DOMAIN_4_LEVEL_KEY
                                                ) {
                                                    return executeSet(context.getSource(), serverWorld, serverWorld.getWorldBorder().getSize() + getDouble(context, "radius"), 0L);
                                                }
                                            }
                                            return 1;
                                        })
                                        .then(argument("time", integer(0))
                                                .executes(context -> {
                                                    for (ServerWorld serverWorld : context.getSource().getServer().getWorlds()) {
                                                        if (serverWorld.getRegistryKey() == DEModDimensions.DOMAIN_1_LEVEL_KEY
                                                                || serverWorld.getRegistryKey() == DEModDimensions.DOMAIN_2_LEVEL_KEY
                                                                || serverWorld.getRegistryKey() == DEModDimensions.DOMAIN_3_LEVEL_KEY
                                                                || serverWorld.getRegistryKey() == DEModDimensions.DOMAIN_4_LEVEL_KEY
                                                        ) {
                                                            return executeSet(
                                                                    context.getSource(),
                                                                    serverWorld,
                                                                    serverWorld.getWorldBorder().getSize() + getDouble(context, "radius"),
                                                                    serverWorld.getWorldBorder().getSizeLerpTime() + getInteger(context, "time") * 1000L
                                                            );
                                                        }
                                                    }
                                                    return 1;
                                                })
                                        )
                                )
                        )
                        .then(literal("dark_plains")
                                .then(argument("radius", doubleArg(-5.999997E7F, 5.999997E7F))
                                        .executes(context -> {
                                            for (ServerWorld serverWorld : context.getSource().getServer().getWorlds()) {
                                                if (serverWorld.getRegistryKey() == DEModDimensions.DOMAIN_1_LEVEL_KEY) {
                                                    return executeSet(context.getSource(), serverWorld, serverWorld.getWorldBorder().getSize() + getDouble(context, "radius"), 0L);
                                                }
                                            }
                                            return 1;
                                        })
                                        .then(argument("time", integer(0))
                                                .executes(context -> {
                                                    for (ServerWorld serverWorld : context.getSource().getServer().getWorlds()) {
                                                        if (serverWorld.getRegistryKey() == DEModDimensions.DOMAIN_1_LEVEL_KEY) {
                                                            return executeSet(
                                                                    context.getSource(),
                                                                    serverWorld,
                                                                    serverWorld.getWorldBorder().getSize() + getDouble(context, "radius"),
                                                                    serverWorld.getWorldBorder().getSizeLerpTime() + getInteger(context, "time") * 1000L
                                                            );
                                                        }
                                                    }
                                                    return 1;
                                                })
                                        )
                                )
                        )
                        .then(literal("barrens")
                                .then(argument("radius", doubleArg(-5.999997E7F, 5.999997E7F))
                                        .executes(context -> {
                                            for (ServerWorld serverWorld : context.getSource().getServer().getWorlds()) {
                                                if (serverWorld.getRegistryKey() == DEModDimensions.DOMAIN_2_LEVEL_KEY) {
                                                    return executeSet(context.getSource(), serverWorld, serverWorld.getWorldBorder().getSize() + getDouble(context, "radius"), 0L);
                                                }
                                            }
                                            return 1;
                                        })
                                        .then(argument("time", integer(0))
                                                .executes(context -> {
                                                    for (ServerWorld serverWorld : context.getSource().getServer().getWorlds()) {
                                                        if (serverWorld.getRegistryKey() == DEModDimensions.DOMAIN_2_LEVEL_KEY) {
                                                            return executeSet(
                                                                    context.getSource(),
                                                                    serverWorld,
                                                                    serverWorld.getWorldBorder().getSize() + getDouble(context, "radius"),
                                                                    serverWorld.getWorldBorder().getSizeLerpTime() + getInteger(context, "time") * 1000L
                                                            );
                                                        }
                                                    }
                                                    return 1;
                                                })
                                        )
                                )
                        )
                        .then(literal("tundra")
                                .then(argument("radius", doubleArg(-5.999997E7F, 5.999997E7F))
                                        .executes(context -> {
                                            for (ServerWorld serverWorld : context.getSource().getServer().getWorlds()) {
                                                if (serverWorld.getRegistryKey() == DEModDimensions.DOMAIN_3_LEVEL_KEY) {
                                                    return executeSet(context.getSource(), serverWorld, serverWorld.getWorldBorder().getSize() + getDouble(context, "radius"), 0L);
                                                }
                                            }
                                            return 1;
                                        })
                                        .then(argument("time", integer(0))
                                                .executes(context -> {
                                                    for (ServerWorld serverWorld : context.getSource().getServer().getWorlds()) {
                                                        if (serverWorld.getRegistryKey() == DEModDimensions.DOMAIN_4_LEVEL_KEY) {
                                                            return executeSet(
                                                                    context.getSource(),
                                                                    serverWorld,
                                                                    serverWorld.getWorldBorder().getSize() + getDouble(context, "radius"),
                                                                    serverWorld.getWorldBorder().getSizeLerpTime() + getInteger(context, "time") * 1000L
                                                            );
                                                        }
                                                    }
                                                    return 1;
                                                })
                                        )
                                )
                        )
                        .then(literal("crystal_cave")
                                .then(argument("radius", doubleArg(-5.999997E7F, 5.999997E7F))
                                        .executes(context -> {
                                            for (ServerWorld serverWorld : context.getSource().getServer().getWorlds()) {
                                                if (serverWorld.getRegistryKey() == DEModDimensions.DOMAIN_4_LEVEL_KEY) {
                                                    return executeSet(context.getSource(), serverWorld, serverWorld.getWorldBorder().getSize() + getDouble(context, "radius"), 0L);
                                                }
                                            }
                                            return 1;
                                        })
                                        .then(argument("time", integer(0))
                                                .executes(context -> {
                                                    for (ServerWorld serverWorld : context.getSource().getServer().getWorlds()) {
                                                        if (serverWorld.getRegistryKey() == DEModDimensions.DOMAIN_4_LEVEL_KEY) {
                                                            return executeSet(
                                                                    context.getSource(),
                                                                    serverWorld,
                                                                    serverWorld.getWorldBorder().getSize() + getDouble(context, "radius"),
                                                                    serverWorld.getWorldBorder().getSizeLerpTime() + getInteger(context, "time") * 1000L
                                                            );
                                                        }
                                                    }
                                                    return 1;
                                                })
                                        )
                                )
                        )

                )
                .then(literal("set")
                        .then(literal("all")
                                .then(argument("radius", doubleArg(-5.999997E7F, 5.999997E7F))
                                        .executes(context -> {
                                            for (ServerWorld serverWorld : context.getSource().getServer().getWorlds()) {
                                                if (serverWorld.getRegistryKey() == DEModDimensions.DOMAIN_1_LEVEL_KEY
                                                        || serverWorld.getRegistryKey() == DEModDimensions.DOMAIN_2_LEVEL_KEY
                                                        || serverWorld.getRegistryKey() == DEModDimensions.DOMAIN_3_LEVEL_KEY
                                                        || serverWorld.getRegistryKey() == DEModDimensions.DOMAIN_4_LEVEL_KEY
                                                ) {
                                                    return executeSet(context.getSource(), serverWorld, getDouble(context, "radius") * 2, 0L);
                                                }
                                            }
                                            return 1;
                                        })
                                        .then(argument("time", integer(0))
                                                .executes(context -> {
                                                    for (ServerWorld serverWorld : context.getSource().getServer().getWorlds()) {
                                                        if (serverWorld.getRegistryKey() == DEModDimensions.DOMAIN_1_LEVEL_KEY
                                                                || serverWorld.getRegistryKey() == DEModDimensions.DOMAIN_2_LEVEL_KEY
                                                                || serverWorld.getRegistryKey() == DEModDimensions.DOMAIN_3_LEVEL_KEY
                                                                || serverWorld.getRegistryKey() == DEModDimensions.DOMAIN_4_LEVEL_KEY
                                                        ) {
                                                            return executeSet(
                                                                    context.getSource(),
                                                                    serverWorld,
                                                                    getDouble(context, "radius") * 2,
                                                                    serverWorld.getWorldBorder().getSizeLerpTime() + getInteger(context, "time") * 1000L
                                                            );
                                                        }
                                                    }
                                                    return 1;
                                                })
                                        )
                                )
                        )
                        .then(literal("dark_plains")
                                .then(argument("radius", doubleArg(-5.999997E7F, 5.999997E7F))
                                        .executes(context -> {
                                            for (ServerWorld serverWorld : context.getSource().getServer().getWorlds()) {
                                                if (serverWorld.getRegistryKey() == DEModDimensions.DOMAIN_1_LEVEL_KEY) {
                                                    return executeSet(context.getSource(), serverWorld, getDouble(context, "radius") * 2, 0L);
                                                }
                                            }
                                            return 1;
                                        })
                                        .then(argument("time", integer(0))
                                                .executes(context -> {
                                                    for (ServerWorld serverWorld : context.getSource().getServer().getWorlds()) {
                                                        if (serverWorld.getRegistryKey() == DEModDimensions.DOMAIN_1_LEVEL_KEY) {
                                                            return executeSet(
                                                                    context.getSource(),
                                                                    serverWorld,
                                                                    getDouble(context, "radius") * 2,
                                                                    serverWorld.getWorldBorder().getSizeLerpTime() + getInteger(context, "time") * 1000L
                                                            );
                                                        }
                                                    }
                                                    return 1;
                                                })
                                        )
                                )
                        )
                        .then(literal("barrens")
                                .then(argument("radius", doubleArg(-5.999997E7F, 5.999997E7F))
                                        .executes(context -> {
                                            for (ServerWorld serverWorld : context.getSource().getServer().getWorlds()) {
                                                if (serverWorld.getRegistryKey() == DEModDimensions.DOMAIN_2_LEVEL_KEY) {
                                                    return executeSet(context.getSource(), serverWorld, getDouble(context, "radius") * 2, 0L);
                                                }
                                            }
                                            return 1;
                                        })
                                        .then(argument("time", integer(0))
                                                .executes(context -> {
                                                    for (ServerWorld serverWorld : context.getSource().getServer().getWorlds()) {
                                                        if (serverWorld.getRegistryKey() == DEModDimensions.DOMAIN_2_LEVEL_KEY) {
                                                            return executeSet(
                                                                    context.getSource(),
                                                                    serverWorld,
                                                                    getDouble(context, "radius") * 2,
                                                                    serverWorld.getWorldBorder().getSizeLerpTime() + getInteger(context, "time") * 1000L
                                                            );
                                                        }
                                                    }
                                                    return 1;
                                                })
                                        )
                                )
                        )
                        .then(literal("tundra")
                                .then(argument("radius", doubleArg(-5.999997E7F, 5.999997E7F))
                                        .executes(context -> {
                                            for (ServerWorld serverWorld : context.getSource().getServer().getWorlds()) {
                                                if (serverWorld.getRegistryKey() == DEModDimensions.DOMAIN_3_LEVEL_KEY) {
                                                    return executeSet(context.getSource(), serverWorld, getDouble(context, "radius") * 2, 0L);
                                                }
                                            }
                                            return 1;
                                        })
                                        .then(argument("time", integer(0))
                                                .executes(context -> {
                                                    for (ServerWorld serverWorld : context.getSource().getServer().getWorlds()) {
                                                        if (serverWorld.getRegistryKey() == DEModDimensions.DOMAIN_4_LEVEL_KEY) {
                                                            return executeSet(
                                                                    context.getSource(),
                                                                    serverWorld,
                                                                    getDouble(context, "radius") * 2,
                                                                    serverWorld.getWorldBorder().getSizeLerpTime() + getInteger(context, "time") * 1000L
                                                            );
                                                        }
                                                    }
                                                    return 1;
                                                })
                                        )
                                )
                        )
                        .then(literal("crystal_cave")
                                .then(argument("radius", doubleArg(-5.999997E7F, 5.999997E7F))
                                        .executes(context -> {
                                            for (ServerWorld serverWorld : context.getSource().getServer().getWorlds()) {
                                                if (serverWorld.getRegistryKey() == DEModDimensions.DOMAIN_4_LEVEL_KEY) {
                                                    return executeSet(context.getSource(), serverWorld, getDouble(context, "radius") * 2, 0L);
                                                }
                                            }
                                            return 1;
                                        })
                                        .then(argument("time", integer(0))
                                                .executes(context -> {
                                                    for (ServerWorld serverWorld : context.getSource().getServer().getWorlds()) {
                                                        if (serverWorld.getRegistryKey() == DEModDimensions.DOMAIN_4_LEVEL_KEY) {
                                                            return executeSet(
                                                                    context.getSource(),
                                                                    serverWorld,
                                                                    getDouble(context, "radius") * 2,
                                                                    serverWorld.getWorldBorder().getSizeLerpTime() + getInteger(context, "time") * 1000L
                                                            );
                                                        }
                                                    }
                                                    return 1;
                                                })
                                        )
                                )
                        )

                )
                .then(literal("center")
                        .then(literal("all")
                                .then(argument("pos", vec2())
                                        .executes(context -> {
                                            executeCenter(context.getSource(), getVec2(context, "pos"), DEModDimensions.DOMAIN_1_LEVEL_KEY);
                                            executeCenter(context.getSource(), getVec2(context, "pos"), DEModDimensions.DOMAIN_2_LEVEL_KEY);
                                            executeCenter(context.getSource(), getVec2(context, "pos"), DEModDimensions.DOMAIN_3_LEVEL_KEY);
                                            executeCenter(context.getSource(), getVec2(context, "pos"), DEModDimensions.DOMAIN_4_LEVEL_KEY);
                                            return 1;
                                        })
                                )
                        )
                        .then(literal("dark_plains")
                                .then(argument("pos", vec2())
                                        .executes(context -> {
                                            executeCenter(context.getSource(), getVec2(context, "pos"), DEModDimensions.DOMAIN_1_LEVEL_KEY);
                                            return 1;
                                        })
                                )
                        )
                        .then(literal("barrens")
                                .then(argument("pos", vec2())
                                        .executes(context -> {
                                            executeCenter(context.getSource(), getVec2(context, "pos"), DEModDimensions.DOMAIN_2_LEVEL_KEY);
                                            return 1;
                                        })
                                )
                        )
                        .then(literal("tundra")
                                .then(argument("pos", vec2())
                                        .executes(context -> {
                                            executeCenter(context.getSource(), getVec2(context, "pos"), DEModDimensions.DOMAIN_3_LEVEL_KEY);
                                            return 1;
                                        })
                                )
                        )
                        .then(literal("crystal_cave")
                                .then(argument("pos", vec2())
                                        .executes(context -> {
                                            executeCenter(context.getSource(), getVec2(context, "pos"), DEModDimensions.DOMAIN_4_LEVEL_KEY);
                                            return 1;
                                        })
                                )
                        )
                );
    }

    private static int executeSet(ServerCommandSource source, ServerWorld world, double distance, long time) throws CommandSyntaxException {
        WorldBorder worldBorder = world.getWorldBorder();
        double initSize = worldBorder.getSize();
        if (initSize == distance) {
            throw SET_FAILED_NO_CHANGE_EXCEPTION.create();
        } else if (distance < 1.0) {
            throw SET_FAILED_SMALL_EXCEPTION.create();
        } else if (distance > 5.999997E7F) {
            throw SET_FAILED_BIG_EXCEPTION.create();
        } else {
            if (time > 0L) {
                worldBorder.interpolateSize(initSize, distance, time);
                if (distance > initSize) {
                    source.sendFeedback(
                            () -> Text.translatable("commands.worldborder.set.grow", String.format(Locale.ROOT, "%.1f", distance), Long.toString(time / 1000L)), true
                    );
                } else {
                    source.sendFeedback(
                            () -> Text.translatable("commands.worldborder.set.shrink", String.format(Locale.ROOT, "%.1f", distance), Long.toString(time / 1000L)), true
                    );
                }
            } else {
                worldBorder.setSize(distance);
                source.sendFeedback(() -> Text.translatable("commands.worldborder.set.immediate", String.format(Locale.ROOT, "%.1f", distance)), true);
            }
            return (int) (distance - initSize);
        }
    }

    private static void executeCenter(ServerCommandSource source, Vec2f pos, RegistryKey<World> domainKey) {
        for (ServerWorld serverWorld : source.getServer().getWorlds()) {
            if (serverWorld.getRegistryKey() == domainKey) {
                WorldBorder worldBorder = serverWorld.getWorldBorder();
                worldBorder.setCenter(pos.x, pos.y);
            }
        }
    }
}
