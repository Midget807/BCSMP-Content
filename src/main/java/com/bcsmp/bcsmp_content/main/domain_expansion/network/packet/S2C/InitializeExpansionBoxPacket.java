package com.bcsmp.bcsmp_content.main.domain_expansion.network.packet.S2C;

import com.bcsmp.bcsmp_content.main.domain_expansion.network.DEModMessages;
import com.bcsmp.bcsmp_content.main.domain_expansion.network.packet.DEClientPlayerListener;
import com.bcsmp.bcsmp_content.main.domain_expansion.world.area.ExpansionBox;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.PacketType;

public class InitializeExpansionBoxPacket implements Packet<ClientPlayPacketListener> {
    public static final PacketCodec<PacketByteBuf, InitializeExpansionBoxPacket> CODEC = Packet.createCodec(
            InitializeExpansionBoxPacket::write, InitializeExpansionBoxPacket::new
    );
    private final double centerX;
    private final double centerY;
    private final double centerZ;
    private final double size;
    private final double sizeLerpTarget;
    private final long sizeLerpTime;
    private final int maxRadius;
    private final int warningBlocks;
    private final int warningTime;

    public InitializeExpansionBoxPacket(ExpansionBox expansionBox, double centerX, double centerY, double centerZ, double size, double sizeLerpTarget, long sizeLerpTime, int maxRadius, int warningBlocks, int warningTime) {
        this.centerX = expansionBox.getCenterX();
        this.centerY = expansionBox.getCenterY();
        this.centerZ = expansionBox.getCenterZ();
        this.size = expansionBox.getSize();
        this.sizeLerpTarget = expansionBox.getSizeLerpTarget();
        this.sizeLerpTime = expansionBox.getSizeLerpTime();
        this.maxRadius = expansionBox.getMaxRadius();
        this.warningBlocks = expansionBox.getWarningBlocks();
        this.warningTime = expansionBox.getWarningTime();
    }

    private InitializeExpansionBoxPacket(PacketByteBuf buf) {
        this.centerX = buf.readDouble();
        this.centerY = buf.readDouble();
        this.centerZ = buf.readDouble();
        this.size = buf.readDouble();
        this.sizeLerpTarget = buf.readDouble();
        this.sizeLerpTime = buf.readVarLong();
        this.maxRadius = buf.readVarInt();
        this.warningBlocks = buf.readVarInt();
        this.warningTime = buf.readVarInt();
    }

    private void write(PacketByteBuf buf) {
        buf.writeDouble(this.centerX);
        buf.writeDouble(this.centerY);
        buf.writeDouble(this.centerZ);
        buf.writeDouble(this.size);
        buf.writeDouble(this.sizeLerpTarget);
        buf.writeVarLong(this.sizeLerpTime);
        buf.writeVarInt(this.maxRadius);
        buf.writeVarInt(this.warningBlocks);
        buf.writeVarInt(this.warningTime);
    }

    @Override
    public PacketType<InitializeExpansionBoxPacket> getPacketId() {
        return DEModMessages.INITIALIZE_EXPANSION_BORDER;
    }

    public void apply(ClientPlayPacketListener listener) {
        ((DEClientPlayerListener)listener).onExpansionBorderInitialize(this);
    }

    public double getSizeLerpTarget() {
        return this.sizeLerpTarget;
    }
}
