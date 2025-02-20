package com.bcsmp.bcsmp_content.main.domain_expansion.effect.custom;

import com.bcsmp.bcsmp_content.main.domain_expansion.network.DEModMessages;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DomainTpEffect extends StatusEffect {
    public DomainTpEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    /*@Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        if (entity instanceof ServerPlayerEntity serverPlayer) {
            PacketByteBuf buf = PacketByteBufs.create();
            buf.writeUuid(serverPlayer.getUuid());
            ClientPlayNetworking.send(DEModMessages.TELEPORT_OVERWORLD, buf);
        }
        super.onApplied(entity, attributes, amplifier);
    }*/
}
