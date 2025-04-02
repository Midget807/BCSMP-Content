package com.bcsmp.bcsmp_content.main.domain_expansion.network.packet.S2C;

import com.bcsmp.bcsmp_content.main.domain_expansion.network.DEModMessages;
import com.bcsmp.bcsmp_content.main.domain_expansion.network.packet.DEClientPlayerListener;
import com.bcsmp.bcsmp_content.main.domain_expansion.world.area.ExpansionBox;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.PacketType;

public class ExpansionBoxWarningBlocksChangedPacket implements Packet<ClientPlayPacketListener> {
    public static final PacketCodec<PacketByteBuf, ExpansionBoxWarningBlocksChangedPacket> CODEC = Packet.createCodec(
            ExpansionBoxWarningBlocksChangedPacket::write, ExpansionBoxWarningBlocksChangedPacket::new
    );
    private final int warningTime;

    public ExpansionBoxWarningBlocksChangedPacket(ExpansionBox expansionBox) {
        this.warningTime = expansionBox.getWarningTime();
    }

    private ExpansionBoxWarningBlocksChangedPacket(PacketByteBuf buf) {
        this.warningTime = buf.readVarInt();
    }

    private void write(PacketByteBuf buf) {
        buf.writeVarInt(warningTime);
    }

    @Override
    public PacketType<ExpansionBoxWarningBlocksChangedPacket> getPacketId() {
        return DEModMessages.SET_EXPANSION_BOX_WARNING_DISTANCE;
    }

    public void apply(ClientPlayPacketListener listener) {
        ((DEClientPlayerListener)listener).onExpansionBoxWarningBlocksChanged(this);
    }

    public int getWarningTime() {
        return warningTime;
    }
}
