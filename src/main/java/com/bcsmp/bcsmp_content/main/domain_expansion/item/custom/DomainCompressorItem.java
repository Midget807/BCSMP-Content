package com.bcsmp.bcsmp_content.main.domain_expansion.item.custom;

import com.bcsmp.bcsmp_content.main.domain_expansion.effect.DEModEffects;
import com.bcsmp.bcsmp_content.main.domain_expansion.item.DEModItems;
import com.bcsmp.bcsmp_content.main.domain_expansion.worldgen.dimension.DEModDimensions;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.command.argument.PosArgument;
import net.minecraft.command.argument.Vec3ArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.network.packet.s2c.play.*;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;
import net.minecraft.world.WorldProperties;
import net.minecraft.world.biome.source.BiomeAccess;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public class DomainCompressorItem extends Item {
    public static final String CASTER_POS_KEY = "CasterOriginPos";
    private boolean shouldTick = false;

    public DomainCompressorItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack handStack = user.getStackInHand(hand);
        if (world.getRegistryKey() == DEModDimensions.DOMAIN_1_LEVEL_KEY ||
                world.getRegistryKey() == DEModDimensions.DOMAIN_2_LEVEL_KEY||
                world.getRegistryKey() == DEModDimensions.DOMAIN_3_LEVEL_KEY||
                world.getRegistryKey() == DEModDimensions.DOMAIN_4_LEVEL_KEY
        ) {
            if (user.isSneaking() && handStack.getNbt() != null) {
                if (!(handStack.getNbt().contains("X") && handStack.getNbt().contains("Y") && handStack.getNbt().contains("Z"))) {
                    return TypedActionResult.pass(handStack);
                } else {
                    int x = handStack.getNbt().getInt("X");
                    int y = handStack.getNbt().getInt("Y");
                    int z = handStack.getNbt().getInt("Z");
                    if (!world.isClient) {
                        MinecraftServer server = user.getServer();
                        if (user instanceof ServerPlayerEntity && server != null) {
                            ServerWorld overworld = server.getWorld(World.OVERWORLD);
                            if (overworld != null) {
                                ServerWorld serverWorld = ((ServerPlayerEntity) user).getServerWorld();
                                for (ServerPlayerEntity player : serverWorld.getPlayers()) {
                                    player.addStatusEffect(new StatusEffectInstance(DEModEffects.DOMAIN_TP_EFFECT, 10 * 20, 0));
                                    this.shouldTick = true;
                                }
                                for (ServerPlayerEntity player : serverWorld.getPlayers()) {
                                    if (this.shouldTick && player != null) {
                                        ServerTickEvents.START_SERVER_TICK.register(server1 -> {
                                            if (player.getStatusEffect(DEModEffects.DOMAIN_TP_EFFECT) != null && player.getStatusEffect(DEModEffects.DOMAIN_TP_EFFECT).getDuration() > 0) {
                                                if (player.getStatusEffect(DEModEffects.DOMAIN_TP_EFFECT).getDuration() == 1) {
                                                    player.teleport(overworld, x, y, z, player.getBodyYaw(), player.prevPitch);
                                                }
                                            } else {
                                                this.shouldTick = false;
                                            }
                                        });
                                        user.setStackInHand(hand, ItemStack.EMPTY);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else {
            user.sendMessage(Text.literal("Must be in a domain to use").formatted(Formatting.RED), true);
        }
        return TypedActionResult.consume(handStack);
    }

    public static void teleportPlayersBack(
            ServerWorld domain,
            ServerWorld destination,
            ServerPlayerEntity serverPlayer,
            ServerPlayNetworkHandler handler,
            MinecraftServer server,
            BlockPos casterOriginPos

    ) {
        RegistryKey<World> originWorld  = domain.getRegistryKey();
        RegistryKey<World> destinationWorld  = destination.getRegistryKey();
        WorldProperties worldProperties = destination.getLevelProperties();
        handler.sendPacket(new PlayerRespawnS2CPacket(
                destination.getDimensionKey(),
                destinationWorld,
                BiomeAccess.hashSeed(destination.getSeed()),
                serverPlayer.interactionManager.getGameMode(),
                serverPlayer.interactionManager.getPreviousGameMode(),
                false,
                true,
                (byte) 3,
                serverPlayer.getLastDeathPos(),
                0
        ));
        handler.sendPacket(new DifficultyS2CPacket(
                worldProperties.getDifficulty(),
                worldProperties.isDifficultyLocked()
        ));
        PlayerManager playerManager = server.getPlayerManager();
        playerManager.sendCommandTree(serverPlayer);
        domain.removePlayer(serverPlayer, Entity.RemovalReason.CHANGED_DIMENSION);
        TeleportTarget teleportTarget = new TeleportTarget(new Vec3d(casterOriginPos.getX(), casterOriginPos.getY(), casterOriginPos.getZ()), new Vec3d(0, 0, 0), 0.0f, 0.0f);

        if (teleportTarget != null) {
            domain.getProfiler().push("placing");
            serverPlayer.setServerWorld(destination);
            serverPlayer.networkHandler
                    .requestTeleport(teleportTarget.position.x, teleportTarget.position.y, teleportTarget.position.z, teleportTarget.yaw, teleportTarget.pitch);
            serverPlayer.networkHandler.syncWithPlayerPosition();
            destination.onPlayerChangeDimension(serverPlayer);
            domain.getProfiler().pop();

            Criteria.CHANGED_DIMENSION.trigger(serverPlayer, originWorld, destinationWorld);
            serverPlayer.networkHandler.sendPacket(new PlayerAbilitiesS2CPacket(serverPlayer.getAbilities()));
            playerManager.sendWorldInfo(serverPlayer, destination);
            playerManager.sendPlayerStatus(serverPlayer);

            for (StatusEffectInstance statusEffectInstance : serverPlayer.getStatusEffects()) {
                serverPlayer.networkHandler.sendPacket(new EntityStatusEffectS2CPacket(serverPlayer.getId(), statusEffectInstance));
            }
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (stack.getNbt() != null) {
            if (stack.getNbt().contains("X") && stack.getNbt().contains("Y") && stack.getNbt().contains("Z")) {
                int x = stack.getNbt().getInt("X");
                int y = stack.getNbt().getInt("Y");
                int z = stack.getNbt().getInt("Z");
                tooltip.add(Text.literal("Return position: " + x + " " + y + " " + z));
            } else {
                tooltip.add(Text.literal("Return position not available").formatted(Formatting.DARK_GRAY));
            }
        } else {
            tooltip.add(Text.literal("Return position not available").formatted(Formatting.DARK_GRAY));
        }
        super.appendTooltip(stack, world, tooltip, context);
    }
}
