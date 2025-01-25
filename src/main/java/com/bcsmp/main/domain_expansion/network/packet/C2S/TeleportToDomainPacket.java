package com.bcsmp.main.domain_expansion.network.packet.C2S;

import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

public class TeleportToDomainPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity serverPlayer, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {
        BlockPos casterPos = buf.readBlockPos();
        serverPlayer.sendMessage(Text.literal("yoooo"));
    }
}
