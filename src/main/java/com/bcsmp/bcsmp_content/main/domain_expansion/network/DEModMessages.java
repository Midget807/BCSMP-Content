package com.bcsmp.bcsmp_content.main.domain_expansion.network;

import com.bcsmp.bcsmp_content.BCSMPContentMain;
import com.bcsmp.bcsmp_content.main.domain_expansion.network.packet.S2C.ItemStackSyncPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.util.Identifier;

public class DEModMessages {
    public static final Identifier ITEM_SYNC = BCSMPContentMain.domainExpansionId("item_sync");
    public static final Identifier DOMAIN_BORDER_INIT = BCSMPContentMain.domainExpansionId("domain_border_init");

    public static void registerS2CPackets() {
        ClientPlayNetworking.registerGlobalReceiver(ITEM_SYNC, ItemStackSyncPacket::receive);
        //ClientPlayNetworking.registerGlobalReceiver(DOMAIN_BORDER_INIT, DomainBorderInitializePacket::receive);
    }

    public static void registerC2SPackets() {
    }
}
