package com.bcsmp.bcsmp_content.mixin.domain_expansion.client;

import com.bcsmp.bcsmp_content.main.domain_expansion.worldgen.border.DomainBorder;
import com.bcsmp.bcsmp_content.main.domain_expansion.worldgen.border.WorldWithDomainBorder;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.profiler.Profiler;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public abstract class WorldRendererMixin {
    private static final Identifier FORCEFIELD = new Identifier("textures/misc/forcefield.png");
    @Shadow private @Nullable ClientWorld world;

    @Shadow @Final private MinecraftClient client;
    @Deprecated
    @Unique
    private void renderDomainBorder(Camera camera) {
        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        DomainBorder domainBorder = ((WorldWithDomainBorder) world).getDomainBorder();
        if (domainBorder != null) {
            double d = (double) (this.client.options.getClampedViewDistance() * 16);
            if (!(camera.getPos().x < domainBorder.getBoundEast() - d)
                    || !(camera.getPos().x > domainBorder.getBoundWest() + d)
                    || !(camera.getPos().z < domainBorder.getBoundSouth() - d)
                    || !(camera.getPos().z > domainBorder.getBoundNorth() + d)) {
                double e = 1.0 - domainBorder.getDistanceInsideBorder(camera.getPos().x, camera.getPos().z) / d;
                e = Math.pow(e, 4.0);
                e = MathHelper.clamp(e, 0.0, 1.0);
                double f = camera.getPos().x;
                double g = camera.getPos().z;
                double h = (double) this.client.gameRenderer.getFarPlaneDistance();
                RenderSystem.enableBlend();
                RenderSystem.enableDepthTest();
                RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE, GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ZERO);
                RenderSystem.setShaderTexture(0, FORCEFIELD);
                RenderSystem.depthMask(MinecraftClient.isFabulousGraphicsOrBetter());
                MatrixStack matrixStack = RenderSystem.getModelViewStack();
                matrixStack.push();
                RenderSystem.applyModelViewMatrix();
                int i = domainBorder.getStage().getColor();
                float j = (float) (i >> 16 & 0xFF) / 255.0F;
                float k = (float) (i >> 8 & 0xFF) / 255.0F;
                float l = (float) (i & 0xFF) / 255.0F;
                RenderSystem.setShaderColor(j, k, l, (float) e);
                RenderSystem.setShader(GameRenderer::getPositionTexProgram);
                RenderSystem.polygonOffset(-3.0F, -3.0F);
                RenderSystem.enablePolygonOffset();
                RenderSystem.disableCull();
                float m = (float) (Util.getMeasuringTimeMs() % 3000L) / 3000.0F;
                float n = (float) (-MathHelper.fractionalPart(camera.getPos().y * 0.5));
                float o = n + (float) h;
                bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
                double p = Math.max((double) MathHelper.floor(g - d), domainBorder.getBoundNorth());
                double q = Math.min((double) MathHelper.ceil(g + d), domainBorder.getBoundSouth());
                float r = (float) (MathHelper.floor(p) & 1) * 0.5F;
                if (f > domainBorder.getBoundEast() - d) {
                    float s = r;

                    for (double t = p; t < q; s += 0.5F) {
                        double u = Math.min(1.0, q - t);
                        float v = (float) u * 0.5F;
                        bufferBuilder.vertex(domainBorder.getBoundEast() - f, -h, t - g).texture(m - s, m + o).next();
                        bufferBuilder.vertex(domainBorder.getBoundEast() - f, -h, t + u - g).texture(m - (v + s), m + o).next();
                        bufferBuilder.vertex(domainBorder.getBoundEast() - f, h, t + u - g).texture(m - (v + s), m + n).next();
                        bufferBuilder.vertex(domainBorder.getBoundEast() - f, h, t - g).texture(m - s, m + n).next();
                        t++;
                    }
                }

                if (f < domainBorder.getBoundWest() + d) {
                    float s = r;

                    for (double t = p; t < q; s += 0.5F) {
                        double u = Math.min(1.0, q - t);
                        float v = (float) u * 0.5F;
                        bufferBuilder.vertex(domainBorder.getBoundWest() - f, -h, t - g).texture(m + s, m + o).next();
                        bufferBuilder.vertex(domainBorder.getBoundWest() - f, -h, t + u - g).texture(m + v + s, m + o).next();
                        bufferBuilder.vertex(domainBorder.getBoundWest() - f, h, t + u - g).texture(m + v + s, m + n).next();
                        bufferBuilder.vertex(domainBorder.getBoundWest() - f, h, t - g).texture(m + s, m + n).next();
                        t++;
                    }
                }

                p = Math.max((double) MathHelper.floor(f - d), domainBorder.getBoundWest());
                q = Math.min((double) MathHelper.ceil(f + d), domainBorder.getBoundEast());
                r = (float) (MathHelper.floor(p) & 1) * 0.5F;
                if (g > domainBorder.getBoundSouth() - d) {
                    float s = r;

                    for (double t = p; t < q; s += 0.5F) {
                        double u = Math.min(1.0, q - t);
                        float v = (float) u * 0.5F;
                        bufferBuilder.vertex(t - f, -h, domainBorder.getBoundSouth() - g).texture(m + s, m + o).next();
                        bufferBuilder.vertex(t + u - f, -h, domainBorder.getBoundSouth() - g).texture(m + v + s, m + o).next();
                        bufferBuilder.vertex(t + u - f, h, domainBorder.getBoundSouth() - g).texture(m + v + s, m + n).next();
                        bufferBuilder.vertex(t - f, h, domainBorder.getBoundSouth() - g).texture(m + s, m + n).next();
                        t++;
                    }
                }

                if (g < domainBorder.getBoundNorth() + d) {
                    float s = r;

                    for (double t = p; t < q; s += 0.5F) {
                        double u = Math.min(1.0, q - t);
                        float v = (float) u * 0.5F;
                        bufferBuilder.vertex(t - f, -h, domainBorder.getBoundNorth() - g).texture(m - s, m + o).next();
                        bufferBuilder.vertex(t + u - f, -h, domainBorder.getBoundNorth() - g).texture(m - (v + s), m + o).next();
                        bufferBuilder.vertex(t + u - f, h, domainBorder.getBoundNorth() - g).texture(m - (v + s), m + n).next();
                        bufferBuilder.vertex(t - f, h, domainBorder.getBoundNorth() - g).texture(m - s, m + n).next();
                        t++;
                    }
                }

                BufferRenderer.drawWithGlobalProgram(bufferBuilder.end());
                RenderSystem.enableCull();
                RenderSystem.polygonOffset(0.0F, 0.0F);
                RenderSystem.disablePolygonOffset();
                RenderSystem.disableBlend();
                RenderSystem.defaultBlendFunc();
                matrixStack.pop();
                RenderSystem.applyModelViewMatrix();
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                RenderSystem.depthMask(true);
            }
        }
    }
    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/WorldRenderer;renderWorldBorder(Lnet/minecraft/client/render/Camera;)V"))
    private void domainExpansion$renderDomainBorder(MatrixStack matrices, float tickDelta, long limitTime, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, Matrix4f projectionMatrix, CallbackInfo ci, @Local LocalRef<Profiler> profilerLocalRef) {
        //this.renderDomainBorder(camera);
    }
}
