package com.bcsmp.bcsmp_content.main.domain_expansion.network.packet.S2C;

import com.bcsmp.bcsmp_content.main.domain_expansion.network.packet.ClientPlayPacketListenerWithBorder;
import com.bcsmp.bcsmp_content.main.domain_expansion.worldgen.border.DomainBorder;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;

public class DomainBorderInterpolateSizePacket implements Packet<ClientPlayPacketListener> {
    private final double size;
    private final double sizeLerpTarget;
    private final long sizeLerpTime;
    public DomainBorderInterpolateSizePacket(PacketByteBuf buf) {
        this.size = buf.readDouble();
        this.sizeLerpTarget = buf.readDouble();
        this.sizeLerpTime = buf.readLong();
    }
    public DomainBorderInterpolateSizePacket(DomainBorder domainBorder) {
        this.size = domainBorder.getSize();
        this.sizeLerpTarget = domainBorder.getSizeLerpTarget();
        this.sizeLerpTime = domainBorder.getSizeLerpTime();
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeDouble(this.size);
        buf.writeDouble(this.sizeLerpTarget);
        buf.writeVarLong(this.sizeLerpTime);
    }

    @Override
    public void apply(ClientPlayPacketListener listener) {
        ((ClientPlayPacketListenerWithBorder)listener).onDomainBorderInterpolateSize(this);
    }

}
