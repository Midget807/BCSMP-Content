package com.bcsmp.bcsmp_content.main.domain_expansion.network.packet.C2S;

import com.bcsmp.bcsmp_content.main.domain_expansion.item.custom.DomainExpansionItem;
import com.bcsmp.bcsmp_content.main.domain_expansion.worldgen.dimension.DEModDimensions;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.s2c.play.DifficultyS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityStatusEffectS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerAbilitiesS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerRespawnS2CPacket;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;
import net.minecraft.world.WorldProperties;
import net.minecraft.world.biome.source.BiomeAccess;

import java.util.UUID;

public class TeleportToDomainPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity serverPlayer, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {
        boolean isOwner = buf.readBoolean();
        BlockPos casterPos = buf.readBlockPos();
        float radius = buf.readFloat();
        UUID playerUuid = buf.readUuid();
        serverPlayer.sendMessage(Text.literal("" + radius)); //todo debug this shit
        serverPlayer.sendMessage(Text.literal("yoooo"));
        //serverPlayer.moveToWorld(server.getWorld(DEModDimensions.DOMAIN_1_LEVEL_KEY));
        CommandManager commandManager = server.getCommandManager();
        //commandManager.executeWithPrefix(server.getCommandSource(), "/execute in domain_expansion:domain_1 run tp @a 0 60 0");
        teleportToDomain(serverPlayer.getServerWorld(), server.getWorld(DEModDimensions.DOMAIN_1_LEVEL_KEY), server.getPlayerManager().getPlayer(playerUuid), handler, server, isOwner, radius);
    }
    public static void teleportToDomain(ServerWorld origin, ServerWorld destination, ServerPlayerEntity serverPlayer, ServerPlayNetworkHandler handler, MinecraftServer server, boolean isOwner, float radius) {
        RegistryKey<World> originWorld  = origin.getRegistryKey();
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
        origin.removePlayer(serverPlayer, Entity.RemovalReason.CHANGED_DIMENSION);
        TeleportTarget teleportTarget;
        if (isOwner) {
            teleportTarget = new TeleportTarget(new Vec3d(0.5 + ((radius * 3) / 4), 1, 0.5), new Vec3d(0, 0, 0), 0.0f, 0.0f);
        } else {
            teleportTarget = new TeleportTarget(new Vec3d(0.5 - ((radius * 3) / 4), 1, 0.5), new Vec3d(0, 0, 0), 0.0f, 0.0f);
        }
        if (teleportTarget != null) {
            origin.getProfiler().push("placing");
            serverPlayer.setServerWorld(destination);
            serverPlayer.networkHandler
                    .requestTeleport(teleportTarget.position.x, teleportTarget.position.y, teleportTarget.position.z, teleportTarget.yaw, teleportTarget.pitch);
            serverPlayer.networkHandler.syncWithPlayerPosition();
            destination.onPlayerChangeDimension(serverPlayer);
            origin.getProfiler().pop();
            //serverPlayer.worldChanged(origin);
            serverPlayer.networkHandler.sendPacket(new PlayerAbilitiesS2CPacket(serverPlayer.getAbilities()));
            playerManager.sendWorldInfo(serverPlayer, destination);
            playerManager.sendPlayerStatus(serverPlayer);

            for (StatusEffectInstance statusEffectInstance : serverPlayer.getStatusEffects()) {
                serverPlayer.networkHandler.sendPacket(new EntityStatusEffectS2CPacket(serverPlayer.getId(), statusEffectInstance));
            }

        }

    }
}
