package com.bcsmp.bcsmp_content.main.domain_expansion.network.packet.S2C;

import com.bcsmp.bcsmp_content.main.domain_expansion.network.DEModMessages;
import com.bcsmp.bcsmp_content.main.domain_expansion.network.packet.DEClientPlayerListener;
import com.bcsmp.bcsmp_content.main.domain_expansion.world.area.ExpansionBox;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.PacketType;
import net.minecraft.network.packet.PlayPackets;

public class ExpansionBoxCenterChangedPacket implements Packet<ClientPlayPacketListener> {
    public static final PacketCodec<PacketByteBuf, ExpansionBoxCenterChangedPacket> CODEC = Packet.createCodec(
            ExpansionBoxCenterChangedPacket::write, ExpansionBoxCenterChangedPacket::new
    );
    private final double centerX;
    private final double centerY;
    private final double centerZ;
    public ExpansionBoxCenterChangedPacket(ExpansionBox expansionBox) {
        this.centerX = expansionBox.getCenterX();
        this.centerY = expansionBox.getCenterY();
        this.centerZ = expansionBox.getCenterZ();
    }
    public ExpansionBoxCenterChangedPacket(PacketByteBuf buf) {
        this.centerX = buf.readDouble();
        this.centerY = buf.readDouble();
        this.centerZ = buf.readDouble();
    }
    public void write(PacketByteBuf buf) {
        buf.writeDouble(this.centerX);
        buf.writeDouble(this.centerY);
        buf.writeDouble(this.centerZ);
    }
    @Override
    public PacketType<? extends Packet<ClientPlayPacketListener>> getPacketId() {
        return DEModMessages.SET_EXPANSION_BOX_CENTER;
    }

    @Override
    public void apply(ClientPlayPacketListener listener) {
        ((DEClientPlayerListener)listener).onExpansionBoxCenterChanged(this);
    }

    public double getCenterX() {
        return centerX;
    }

    public double getCenterY() {
        return centerY;
    }

    public double getCenterZ() {
        return centerZ;
    }
}
