package com.bcsmp.bcsmp_content.main.domain_expansion.network;

import com.bcsmp.bcsmp_content.BCSMPContentMain;
import com.bcsmp.bcsmp_content.main.domain_expansion.network.packet.S2C.*;
import net.minecraft.network.*;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.listener.ServerPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.PacketType;
import net.minecraft.network.state.PlayStateFactories;
import net.minecraft.util.Identifier;

public class DEModMessages {
    public static final Identifier ITEM_SYNC = BCSMPContentMain.domainExpansionId("item_sync");
    public static final Identifier DOMAIN_BORDER_INIT = BCSMPContentMain.domainExpansionId("domain_border_init");

    public static void registerS2CPackets() {

    }

    public static void registerC2SPackets() {
    }

    public static final PacketType<InitializeExpansionBoxPacket> INITIALIZE_EXPANSION_BORDER = registerS2C("initialize_expansion_box");
    public static final PacketType<ExpansionBoxCenterChangedPacket> SET_EXPANSION_BOX_CENTER = registerS2C("set_expansion_box_center");
    public static final PacketType<ExpansionBoxSizeChangedPacket> SET_EXPANSION_BOX_SIZE = registerS2C("set_expansion_box_size");
    public static final PacketType<ExpansionBoxInterpolateSizePacket> SET_EXPANSION_BOX_LERP_SIZE = registerS2C("set_expansion_box_lerp_size");
    public static final PacketType<ExpansionBoxWarningTimeChangedPacket> SET_EXPANSION_BOX_WARNING_DELAY = registerS2C("set_expansion_box_warming_delay");
    public static final PacketType<ExpansionBoxWarningBlocksChangedPacket> SET_EXPANSION_BOX_WARNING_DISTANCE = registerS2C("set_expansion_box_warning_distance");

    public static <T extends Packet<ClientPlayPacketListener>>PacketType<T> registerS2C(String name) {
        return new PacketType<>(NetworkSide.CLIENTBOUND, BCSMPContentMain.domainExpansionId(name));
    }
    public static <T extends Packet<ServerPlayPacketListener>>PacketType<T> registerC2S(String name) {
        return new PacketType<>(NetworkSide.SERVERBOUND, BCSMPContentMain.domainExpansionId(name));
    }

    public static final NetworkState.Factory<ClientPlayPacketListener, RegistryByteBuf> S2C = NetworkStateBuilder.s2c(
            NetworkPhase.PLAY,
            builder -> builder
                    .add(SET_EXPANSION_BOX_CENTER, ExpansionBoxCenterChangedPacket.CODEC)
                    .add(SET_EXPANSION_BOX_SIZE, ExpansionBoxSizeChangedPacket.CODEC)
                    .add(SET_EXPANSION_BOX_LERP_SIZE, ExpansionBoxInterpolateSizePacket.CODEC)
    );
}
