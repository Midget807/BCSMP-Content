package com.bcsmp.bcsmp_content.main.domain_expansion.particle.custom;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;

public class PillarActiveParticle extends SpriteBillboardParticle {
    private int delay;

    public PillarActiveParticle(ClientWorld clientWorld, double d, double e, double f, int delay) {
        super(clientWorld, d, e, f);
        this.delay = delay;
        this.maxAge = 60;
        this.gravityStrength = 0.0f;
        this.velocityX = 0.0;
        this.velocityY = 0.0;
        this.velocityZ = 0.0;
    }

    @Override
    public void buildGeometry(VertexConsumer vertexConsumer, Camera camera, float tickDelta) {
        if (this.delay <= 0) {
            this.alpha = 1.0F - MathHelper.clamp(MathHelper.sin((float) (this.age * Math.PI)), 0.0f, 1.0f);
            this.velocityY = 0.1 * (this.age/this.maxAge);
            this.method_60373(vertexConsumer, camera, new Quaternionf(), tickDelta);
        }
    }

    @Override
    public void tick() {
        if (this.delay > 0) {
            this.delay--;
        } else {
            super.tick();
        }
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    protected int getBrightness(float tint) {
        return 240;
    }
    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<PillarActiveParticleEffect> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public @Nullable Particle createParticle(PillarActiveParticleEffect parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            PillarActiveParticle pillarActiveParticle = new PillarActiveParticle(world, x, y, z, parameters.getDelay());
            pillarActiveParticle.setSprite(this.spriteProvider);
            pillarActiveParticle.setAlpha(1.0f);
            return pillarActiveParticle;
        }
    }
}
