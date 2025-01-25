package com.bcsmp.main.domain_expansion.network;

import com.bcsmp.BCSMPS2ContentMain;
import com.bcsmp.main.domain_expansion.network.packet.C2S.TeleportToDomainPacket;
import com.bcsmp.main.domain_expansion.network.packet.S2C.ItemStackSyncPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;

public class DEModMessages {
    public static final Identifier ITEM_SYNC = BCSMPS2ContentMain.domainExpansionId("item_sync");

    public static void registerS2CPackets() {
        ClientPlayNetworking.registerGlobalReceiver(ITEM_SYNC, ItemStackSyncPacket::receive);
    }

    public static final Identifier TELEPORT_DOMAIN = BCSMPS2ContentMain.domainExpansionId("teleport_domain");
    public static void registerC2SPackets() {
        ServerPlayNetworking.registerGlobalReceiver(TELEPORT_DOMAIN, TeleportToDomainPacket::receive);
    }
}
