package com.bcsmp.bcsmp_content.main.domain_expansion.particle.custom;

import com.bcsmp.bcsmp_content.main.domain_expansion.particle.DEModParticles;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;

public class PillarActiveParticleEffect implements ParticleEffect {
    public static final MapCodec<PillarActiveParticleEffect> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(Codec.INT.fieldOf("delay").forGetter(particleEffect -> particleEffect.delay)).apply(instance, PillarActiveParticleEffect::new)
    );
    public static final PacketCodec<RegistryByteBuf, PillarActiveParticleEffect> PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.VAR_INT, effect -> effect.delay, PillarActiveParticleEffect::new
    );
    private final int delay;

    public PillarActiveParticleEffect(int delay) {
        this.delay = delay;
    }

    @Override
    public ParticleType<?> getType() {
        return DEModParticles.PILLAR_ACTIVE_PARTICLE;
    }

    public int getDelay() {
        return this.delay;
    }
}
