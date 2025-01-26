package com.bcsmp.main.domain_expansion.network.packet.C2S;

import com.bcsmp.main.domain_expansion.world.dimension.DEModDimensions;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionTypes;

import javax.swing.text.html.parser.Entity;

public class TeleportToDomainPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity serverPlayer, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {
        BlockPos casterPos = buf.readBlockPos();
        serverPlayer.sendMessage(Text.literal("yoooo"));
        serverPlayer.moveToWorld(server.getWorld(DEModDimensions.DOMAIN_1_LEVEL_KEY));
        CommandManager commandManager = server.getCommandManager();
        commandManager.executeWithPrefix(server.getCommandSource(), "/execute in domain_expansion:domain_1 run tp @a 0 60 0");
    }
}
