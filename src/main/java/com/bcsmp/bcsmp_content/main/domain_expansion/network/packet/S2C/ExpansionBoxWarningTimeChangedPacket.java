package com.bcsmp.bcsmp_content.main.domain_expansion.network.packet.S2C;

import com.bcsmp.bcsmp_content.main.domain_expansion.network.DEModMessages;
import com.bcsmp.bcsmp_content.main.domain_expansion.network.packet.DEClientPlayerListener;
import com.bcsmp.bcsmp_content.main.domain_expansion.world.area.ExpansionBox;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.PacketType;

public class ExpansionBoxWarningTimeChangedPacket implements Packet<ClientPlayPacketListener> {
    public static final PacketCodec<PacketByteBuf, ExpansionBoxWarningTimeChangedPacket> CODEC = Packet.createCodec(
            ExpansionBoxWarningTimeChangedPacket::write, ExpansionBoxWarningTimeChangedPacket::new
    );
    private final int warningBlocks;

    public ExpansionBoxWarningTimeChangedPacket(ExpansionBox expansionBox) {
        this.warningBlocks = expansionBox.getWarningBlocks();
    }

    private ExpansionBoxWarningTimeChangedPacket(PacketByteBuf buf) {
        this.warningBlocks = buf.readVarInt();
    }

    private void write(PacketByteBuf buf) {
        buf.writeVarInt(warningBlocks);
    }

    @Override
    public PacketType<ExpansionBoxWarningTimeChangedPacket> getPacketId() {
        return DEModMessages.SET_EXPANSION_BOX_WARNING_DELAY;
    }

    public void apply(ClientPlayPacketListener listener) {
        ((DEClientPlayerListener)listener).onExpansionBoxWarningTimeChanged(this);
    }

    public int getWarningBlocks() {
        return warningBlocks;
    }
}
