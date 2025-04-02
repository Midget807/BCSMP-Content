package com.bcsmp.bcsmp_content.main.domain_expansion.network.packet.S2C;

import com.bcsmp.bcsmp_content.main.domain_expansion.network.DEModMessages;
import com.bcsmp.bcsmp_content.main.domain_expansion.network.packet.DEClientPlayerListener;
import com.bcsmp.bcsmp_content.main.domain_expansion.world.area.ExpansionBox;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.PacketType;

public class ExpansionBoxInterpolateSizePacket implements Packet<ClientPlayPacketListener> {
    public static final PacketCodec<PacketByteBuf, ExpansionBoxInterpolateSizePacket> CODEC = Packet.createCodec(
            ExpansionBoxInterpolateSizePacket::write, ExpansionBoxInterpolateSizePacket::new
    );
    private final double size;
    private final double sizeLerpTarget;
    private final long sizeLerpTime;

    public ExpansionBoxInterpolateSizePacket(ExpansionBox expansionBox) {
        this.size = expansionBox.getSize();
        this.sizeLerpTarget = expansionBox.getSizeLerpTarget();
        this.sizeLerpTime = expansionBox.getSizeLerpTime();
    }

    private ExpansionBoxInterpolateSizePacket(PacketByteBuf buf) {
        this.size = buf.readDouble();
        this.sizeLerpTarget = buf.readDouble();
        this.sizeLerpTime = buf.readVarLong();
    }

    private void write(PacketByteBuf buf) {
        buf.writeDouble(this.size);
        buf.writeDouble(this.sizeLerpTarget);
        buf.writeVarLong(this.sizeLerpTime);
    }

    @Override
    public PacketType<ExpansionBoxInterpolateSizePacket> getPacketId() {
        return DEModMessages.SET_EXPANSION_BOX_LERP_SIZE;
    }

    public void apply(ClientPlayPacketListener listener) {
        ((DEClientPlayerListener)listener).onExpansionBoxInterpolateSize(this);
    }

    public double getSize() {
        return size;
    }

    public double getSizeLerpTarget() {
        return this.sizeLerpTarget;
    }

    public long getSizeLerpTime() {
        return sizeLerpTime;
    }
}
