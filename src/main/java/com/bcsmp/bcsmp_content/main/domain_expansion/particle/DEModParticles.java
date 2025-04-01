package com.bcsmp.bcsmp_content.main.domain_expansion.particle;

import com.bcsmp.bcsmp_content.BCSMPContentMain;
import com.bcsmp.bcsmp_content.main.domain_expansion.particle.custom.PillarActiveParticleEffect;
import com.mojang.serialization.MapCodec;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.client.particle.SpriteBillboardParticle;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import java.util.function.Function;

public class DEModParticles {

    public static final ParticleType<PillarActiveParticleEffect> PILLAR_ACTIVE_PARTICLE = registerParticle(
            "pillar_active", true, type -> PillarActiveParticleEffect.CODEC, type -> PillarActiveParticleEffect.PACKET_CODEC
    );

    public static <T extends ParticleEffect> ParticleType<T> registerParticle(
            String name,
            boolean alwaysShow,
            Function<ParticleType<T>, MapCodec<T>> codecGetter,
            Function<ParticleType<T>, PacketCodec<? extends RegistryByteBuf, T>> packetCodecGetter
    ) {
        return Registry.register(Registries.PARTICLE_TYPE, BCSMPContentMain.domainExpansionId(name), new ParticleType<T>(alwaysShow) {
            @Override
            public MapCodec<T> getCodec() {
                return codecGetter.apply(this);
            }

            @Override
            public PacketCodec<? super RegistryByteBuf, T> getPacketCodec() {
                return (PacketCodec<? super RegistryByteBuf, T>) packetCodecGetter.apply(this);
            }
        });
    }

    public static void registerDomainExpansionParticles() {
        BCSMPContentMain.LOGGER.info("Domain Expansion -> Registering Mode Particles");
    }
}
