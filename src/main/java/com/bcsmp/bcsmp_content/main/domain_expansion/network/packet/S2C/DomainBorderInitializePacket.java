package com.bcsmp.bcsmp_content.main.domain_expansion.network.packet.S2C;

import com.bcsmp.bcsmp_content.main.domain_expansion.network.packet.ClientPlayPacketListenerWithBorder;
import com.bcsmp.bcsmp_content.main.domain_expansion.worldgen.border.DomainBorder;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.listener.PacketListener;
import net.minecraft.network.packet.Packet;

public class DomainBorderInitializePacket implements Packet<ClientPlayPacketListener> {
    private final double centerX;
    private final double centerZ;
    private final double size;
    private final double sizeLerpTarget;
    private final long sizeLerpTime;
    private final int maxRadius;
    private final int warningBlocks;
    private final int warningTime;
    public DomainBorderInitializePacket(PacketByteBuf buf) {
        this.centerX = buf.readDouble();
        this.centerZ = buf.readDouble();
        this.size = buf.readDouble();
        this.sizeLerpTarget = buf.readDouble();
        this.sizeLerpTime = buf.readLong();
        this.maxRadius = buf.readInt();
        this.warningBlocks = buf.readInt();
        this.warningTime = buf.readInt();
    }
    public DomainBorderInitializePacket(DomainBorder domainBorder) {
        this.centerX = domainBorder.getCenterX();
        this.centerZ = domainBorder.getCenterZ();
        this.size = domainBorder.getSize();
        this.sizeLerpTarget = domainBorder.getSizeLerpTarget();
        this.sizeLerpTime = domainBorder.getSizeLerpTime();
        this.maxRadius = domainBorder.getMaxRadius();
        this.warningBlocks = domainBorder.getWarningBlocks();
        this.warningTime = domainBorder.getWarningTime();
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeDouble(this.centerX);
        buf.writeDouble(this.centerZ);
        buf.writeDouble(this.size);
        buf.writeDouble(this.sizeLerpTarget);
        buf.writeVarLong(this.sizeLerpTime);
        buf.writeVarInt(this.maxRadius);
        buf.writeVarInt(this.warningBlocks);
        buf.writeVarInt(this.warningTime);
    }

    @Override
    public void apply(ClientPlayPacketListener listener) {
        ((ClientPlayPacketListenerWithBorder)listener).onDomainBorderInitialize(this);
    }

}
