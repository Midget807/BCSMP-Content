package com.bcsmp.bcsmp_content.main.domain_expansion.network;

import com.bcsmp.bcsmp_content.BCSMPContentMain;
import com.bcsmp.bcsmp_content.main.domain_expansion.network.packet.C2S.TeleportToDomainPacket;
import com.bcsmp.bcsmp_content.main.domain_expansion.network.packet.C2S.TeleportToOverworldPacket;
import com.bcsmp.bcsmp_content.main.domain_expansion.network.packet.S2C.ItemStackSyncPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;

public class DEModMessages {
    public static final Identifier ITEM_SYNC = BCSMPContentMain.domainExpansionId("item_sync");

    public static void registerS2CPackets() {
        ClientPlayNetworking.registerGlobalReceiver(ITEM_SYNC, ItemStackSyncPacket::receive);
    }

    public static final Identifier TELEPORT_DOMAIN = BCSMPContentMain.domainExpansionId("teleport_domain");
    public static final Identifier TELEPORT_OVERWORLD = BCSMPContentMain.domainExpansionId("teleport_overworld");
    public static void registerC2SPackets() {
        ServerPlayNetworking.registerGlobalReceiver(TELEPORT_DOMAIN, TeleportToDomainPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(TELEPORT_OVERWORLD, TeleportToOverworldPacket::receive);
    }
}
