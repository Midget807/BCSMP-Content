package com.bcsmp.main.domain_expansion.network.packet.S2C;

import com.bcsmp.main.domain_expansion.block.custom.entity.DomainPillarBlockEntity;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;

public class ItemStackSyncPacket {
    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {
        int size = buf.readInt();
        DefaultedList<ItemStack> list = DefaultedList.ofSize(size, ItemStack.EMPTY);
        for (int i = 0; i < size; i++) {
            list.set(i, buf.readItemStack());
        }
        BlockPos blockPos = buf.readBlockPos();
        if (client.world.getBlockEntity(blockPos) instanceof DomainPillarBlockEntity domainPillarBlockEntity) {
            domainPillarBlockEntity.setInventory(list);
        }
    }
}
