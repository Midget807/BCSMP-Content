package com.bcsmp.bcsmp_content.main.domain_expansion.item.custom;

import com.bcsmp.bcsmp_content.main.domain_expansion.worldgen.dimension.DEModDimensions;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.network.packet.s2c.play.DifficultyS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityStatusEffectS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerAbilitiesS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerRespawnS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;
import net.minecraft.world.WorldProperties;
import net.minecraft.world.biome.source.BiomeAccess;

import java.util.List;

public class DomainCompressorItem extends Item {
    public static final String CASTER_POS_KEY = "CasterOriginPos";
    public DomainCompressorItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack handStack = user.getStackInHand(hand);
        if (user.getWorld() instanceof ServerWorld domain) {
            if (user.isSneaking() && (domain.getDimensionKey() == DEModDimensions.GLOBAL_DOMAIN_DAY_TYPE || domain.getDimensionKey() == DEModDimensions.GLOBAL_DOMAIN_NIGHT_TYPE) && handStack.getNbt() != null) {
                NbtList nbtList = handStack.getNbt().getList(CASTER_POS_KEY, NbtElement.INT_TYPE);
                BlockPos casterOriginPos = new BlockPos(nbtList.getInt(0), nbtList.getInt(1), nbtList.getInt(2));
                List<? extends PlayerEntity> list = world.getPlayers();
                DefaultedList<PlayerEntity> playersInDomain = DefaultedList.of();
                playersInDomain.addAll(list);
                for (PlayerEntity player : playersInDomain) {
                    ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
                    /*teleportPlayersBack(
                            (ServerWorld) user.getWorld(),
                            user.getServer().getOverworld(),
                            serverPlayer,
                            ((ServerPlayerEntity) user).networkHandler,
                            user.getServer(),
                            casterOriginPos
                    );*/
                    serverPlayer.teleport(serverPlayer.getServer().getOverworld(), casterOriginPos.getX(), casterOriginPos.getY(), casterOriginPos.getZ(), 0.0f, 0.0f);
                }
            }
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
}
