package com.bcsmp.bcsmp_content.main.domain_expansion.network.packet.S2C;

import com.bcsmp.bcsmp_content.main.domain_expansion.network.DEModMessages;
import com.bcsmp.bcsmp_content.main.domain_expansion.network.packet.DEClientPlayerListener;
import com.bcsmp.bcsmp_content.main.domain_expansion.world.area.ExpansionBox;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.PacketType;

public class ExpansionBoxSizeChangedPacket implements Packet<ClientPlayPacketListener> {
    public static final PacketCodec<PacketByteBuf, ExpansionBoxSizeChangedPacket> CODEC = Packet.createCodec(
            ExpansionBoxSizeChangedPacket::write, ExpansionBoxSizeChangedPacket::new
    );
    private final double sizeLerpTarget;

    public ExpansionBoxSizeChangedPacket(ExpansionBox expansionBox) {
        this.sizeLerpTarget = expansionBox.getSizeLerpTarget();
    }

    private ExpansionBoxSizeChangedPacket(PacketByteBuf buf) {
        this.sizeLerpTarget = buf.readDouble();
    }

    private void write(PacketByteBuf buf) {
        buf.writeDouble(this.sizeLerpTarget);
    }

    @Override
    public PacketType<ExpansionBoxSizeChangedPacket> getPacketId() {
        return DEModMessages.SET_EXPANSION_BOX_SIZE;
    }

    public void apply(ClientPlayPacketListener listener) {
        ((DEClientPlayerListener)listener).onExpansionBoxSizeChanged(this);
    }

    public double getSizeLerpTarget() {
        return this.sizeLerpTarget;
    }
}
