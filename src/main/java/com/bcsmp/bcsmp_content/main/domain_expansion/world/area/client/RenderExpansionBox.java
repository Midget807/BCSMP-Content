package com.bcsmp.bcsmp_content.main.domain_expansion.world.area.client;

import com.bcsmp.bcsmp_content.main.domain_expansion.world.area.ExpansionBox;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;

/**
 * CREDIT: Made by Midget807
 */

//TODO: rescale uv rendering for all
public class RenderExpansionBox {

    /**
     * Makes a box from a single texture.
     * The ends are iterated along the x-axis.
     * @param r red shader value
     * @param g green shader value
     * @param b blue shader value
     * Alpha is controlled by render distance.
     */
    public static void renderBoxCube(WorldRenderContext context, ExpansionBox box, Identifier texture, float r, float g, float b) {
        double viewDistance = context.worldRenderer().client.options.getClampedViewDistance() * 16;
        ServerTickEvents.START_SERVER_TICK.register(server -> {
            box.tick();
        });
        Camera camera = context.camera();
        if (!(camera.getPos().x < box.getBoundEast() - viewDistance)
                || !(camera.getPos().x > box.getBoundWest() + viewDistance)
                || !(camera.getPos().y < box.getBoundUp() - viewDistance)
                || !(camera.getPos().y > box.getBoundDown() + viewDistance)
                || !(camera.getPos().z < box.getBoundSouth() - viewDistance)
                || !(camera.getPos().z > box.getBoundNorth() + viewDistance)) {
            double distanceRatio = 1.0 - box.getDistanceInsideBorder(camera.getPos().x, camera.getPos().y, camera.getPos().z) / viewDistance;
            distanceRatio = Math.pow(distanceRatio, 4.0);
            distanceRatio = MathHelper.clamp(distanceRatio, 0.0, 1.0);
            double camX = context.camera().getPos().x;
            double camY = context.camera().getPos().y;
            double camZ = context.camera().getPos().z;
            double farPlane = context.gameRenderer().getFarPlaneDistance();
            RenderSystem.enableBlend();
            RenderSystem.enableDepthTest();
            RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE, GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ZERO);
            RenderSystem.setShaderTexture(0, texture);
            RenderSystem.depthMask(MinecraftClient.isFabulousGraphicsOrBetter());
            RenderSystem.setShaderColor(r, g, b, (float) distanceRatio);
            RenderSystem.setShader(GameRenderer::getPositionTexProgram);
            RenderSystem.polygonOffset(-3.0f, -3.0f);
            RenderSystem.enablePolygonOffset();
            RenderSystem.disableCull();
            float time = (float) (Util.getMeasuringTimeMs() % 3000L) / 3000.0F;
            float fraction = (float) (-MathHelper.fractionalPart(camY * 0.25));
            float fractionWDistance = fraction + (float) farPlane;
            float vScalar = 0.2f;
            BufferBuilder builder = Tessellator.getInstance().begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);

            double beforeNorthBound = Math.max(MathHelper.floor(camZ - viewDistance), box.getBoundNorth());
            double beforeSouthBound = Math.min(MathHelper.ceil(camZ + viewDistance), box.getBoundSouth());
            float zBound = (MathHelper.floor(beforeNorthBound) & 1) * 0.5f;
            /*
             * East Plane
             */
            if (camX > box.getBoundEast() - viewDistance) {
                float iteration = zBound;
                for (double t = beforeNorthBound; t < beforeSouthBound; iteration += 0.5f) {
                    double u = Math.min(1.0, beforeSouthBound - t);
                    float v = (float)u * 0.5F;
                    builder.vertex((float) (box.getBoundEast() - camX), (float) (box.getBoundDown() - camY), (float) (t - camZ)).texture(1 - iteration, (float) (time + (fraction + vScalar * box.getBoundUp())));
                    builder.vertex((float) (box.getBoundEast() - camX), (float) (box.getBoundDown() - camY), (float) (t + u - camZ)).texture(1 - (v + iteration), (float) (time + (fraction + vScalar * box.getBoundUp())));
                    builder.vertex((float) (box.getBoundEast() - camX), (float) (box.getBoundUp() - camY), (float) (t + u - camZ)).texture(1 - (v + iteration), (float) (time + fraction));
                    builder.vertex((float) (box.getBoundEast() - camX), (float) (box.getBoundUp() - camY), (float) (t - camZ)).texture(1 - iteration, (float) (time + fraction));
                    t++;
                }
            }
            /*
             * West Plane
             */
            if (camX < box.getBoundWest() + viewDistance) {
                float iteration = zBound;
                for (double t = beforeNorthBound; t < beforeSouthBound; iteration += 0.5f) {
                    double u = Math.min(1.0, beforeSouthBound - t);
                    float v = (float)u * 0.5F;
                    builder.vertex((float) (box.getBoundWest() - camX), (float) (box.getBoundDown() - camY), (float) (t - camZ)).texture(1 + iteration, (float) (time + (fraction + vScalar * box.getBoundUp())));
                    builder.vertex((float) (box.getBoundWest() - camX), (float) (box.getBoundDown() - camY), (float) (t + u - camZ)).texture(1 + (v + iteration), (float) (time + (fraction + vScalar * box.getBoundUp())));
                    builder.vertex((float) (box.getBoundWest() - camX), (float) (box.getBoundUp() - camY), (float) (t + u - camZ)).texture(1 + (v + iteration), (float) (time + fraction));
                    builder.vertex((float) (box.getBoundWest() - camX), (float) (box.getBoundUp() - camY), (float) (t - camZ)).texture(1 + iteration, (float) (time + fraction));
                    t++;
                }
            }

            double beforeWestBound = Math.max(MathHelper.floor(camZ - viewDistance), box.getBoundWest());
            double beforeEastBound = Math.min(MathHelper.ceil(camZ + viewDistance), box.getBoundEast());
            float xBound = (MathHelper.floor(beforeWestBound) & 1) * 0.5f;
            /*
             * South Plane
             */
            if (camZ > box.getBoundSouth() - viewDistance) {
                float iteration = xBound;
                for (double t = beforeWestBound; t < beforeEastBound; iteration += 0.5f) {
                    double u = Math.min(1.0, beforeEastBound - t);
                    float v = (float)u * 0.5F;
                    builder.vertex((float) (t - camX), (float) (box.getBoundDown() - camY), (float) (box.getBoundSouth() - camZ)).texture(1 + iteration, (float) (time + (fraction + vScalar * box.getBoundUp())));
                    builder.vertex((float) (t + u - camX), (float) (box.getBoundDown() - camY), (float) (box.getBoundSouth() - camZ)).texture(1 + (v + iteration), (float) (time + (fraction + vScalar * box.getBoundUp())));
                    builder.vertex((float) (t + u - camX), (float) (box.getBoundUp() - camY), (float) (box.getBoundSouth() - camZ)).texture(1 + (v + iteration), (float) (time + fraction));
                    builder.vertex((float) (t - camX), (float) (box.getBoundUp() - camY), (float) (box.getBoundSouth() - camZ)).texture(1 + iteration, (float) (time + fraction));
                    t++;
                }
            }
            /*
             * North Plane
             */
            if (camZ < box.getBoundNorth() + viewDistance) {
                float iteration = xBound;
                for (double t = beforeWestBound; t < beforeEastBound; iteration += 0.5f) {
                    double u = Math.min(1.0, beforeEastBound - t);
                    float v = (float)u * 0.5F;
                    builder.vertex((float) (t - camX), (float) (box.getBoundDown() - camY), (float) (box.getBoundNorth() - camZ)).texture(1 - iteration, (float) (time + (fraction + vScalar * box.getBoundUp())));
                    builder.vertex((float) (t + u - camX), (float) (box.getBoundDown() - camY), (float) (box.getBoundNorth() - camZ)).texture(1 - (v + iteration), (float) (time + (fraction + vScalar * box.getBoundUp())));
                    builder.vertex((float) (t + u - camX), (float) (box.getBoundUp() - camY), (float) (box.getBoundNorth() - camZ)).texture(1 - (v + iteration), (float) (time + fraction));
                    builder.vertex((float) (t - camX), (float) (box.getBoundUp() - camY), (float) (box.getBoundNorth() - camZ)).texture(1 - iteration, (float) (time + fraction));
                    t++;
                }
            }

            double beforeDownBound = Math.max(MathHelper.floor(camY - viewDistance), box.getBoundDown());
            double beforeUpBound = Math.min(MathHelper.ceil(camY + viewDistance), box.getBoundUp());
            float yBound = (MathHelper.floor(beforeDownBound) & 1) * 0.5f;
            /*
             * Up Plane
             */
            if (camY > box.getBoundUp() - viewDistance) {
                float iteration = xBound;
                for (double t = beforeWestBound; t < beforeEastBound; iteration += 0.5f) {
                    double u = Math.min(1.0, beforeEastBound - t);
                    float v = (float)u * 0.5F;
                    builder.vertex((float) (t - camX), (float) (box.getBoundUp() - camY), (float) (box.getBoundNorth() - camZ)).texture(1 - iteration, (float) (time + (fraction + vScalar * box.getBoundUp())));
                    builder.vertex((float) (t + u - camX), (float) (box.getBoundUp() - camY), (float) (box.getBoundNorth() - camZ)).texture(1 - (v + iteration), (float) (time + (fraction + vScalar * box.getBoundUp())));
                    builder.vertex((float) (t + u - camX), (float) (box.getBoundUp() - camY), (float) (box.getBoundSouth() - camZ)).texture(1 - (v + iteration), (float) (time + fraction));
                    builder.vertex((float) (t - camX), (float) (box.getBoundUp() - camY), (float) (box.getBoundSouth() - camZ)).texture(1 - iteration, (float) (time + fraction));
                    t++;
                }
            }
            /*
             * Down Plane
             */
            if (camY < box.getBoundDown() + viewDistance) {
                float iteration = xBound;
                for (double t = beforeWestBound; t < beforeEastBound; iteration += 0.5f) {
                    double u = Math.min(1.0, beforeEastBound - t);
                    float v = (float)u * 0.5F;
                    builder.vertex((float) (t - camX), (float) (box.getBoundDown() - camY), (float) (box.getBoundNorth() - camZ)).texture(1 + iteration, (float) (time + (fraction + vScalar * box.getBoundUp())));
                    builder.vertex((float) (t + u - camX), (float) (box.getBoundDown() - camY), (float) (box.getBoundNorth() - camZ)).texture(1 + (v + iteration), (float) (time + (fraction + vScalar * box.getBoundUp())));
                    builder.vertex((float) (t + u - camX), (float) (box.getBoundDown() - camY), (float) (box.getBoundSouth() - camZ)).texture(1 + (v + iteration), (float) (time + fraction));
                    builder.vertex((float) (t - camX), (float) (box.getBoundDown() - camY), (float) (box.getBoundSouth() - camZ)).texture(1 + iteration, (float) (time + fraction));
                    t++;
                }
            }

            BuiltBuffer builtBuffer = builder.endNullable();
            if (builtBuffer != null) {
                BufferRenderer.drawWithGlobalProgram(builtBuffer);
            }
            RenderSystem.enableCull();
            RenderSystem.polygonOffset(0.0f, 0.0f);
            RenderSystem.disablePolygonOffset();
            RenderSystem.disableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
            RenderSystem.depthMask(true);
        }
    }

    /**
     * Renders the top and bottom face of a box and renders a "radial" texture.
     * Uses a single texture that spans and is scaled to the box size.
     */
    public static void renderBoxSides(WorldRenderContext context, ExpansionBox box, Identifier texture, float r, float g, float b) {
        double viewDistance = context.worldRenderer().client.options.getClampedViewDistance() * 16;
        ServerTickEvents.START_SERVER_TICK.register(server -> {
            box.tick();
        });
        Camera camera = context.camera();
        if (!(camera.getPos().x < box.getBoundEast() - viewDistance)
                || !(camera.getPos().x > box.getBoundWest() + viewDistance)
                || !(camera.getPos().y < box.getBoundUp() - viewDistance)
                || !(camera.getPos().y > box.getBoundDown() + viewDistance)
                || !(camera.getPos().z < box.getBoundSouth() - viewDistance)
                || !(camera.getPos().z > box.getBoundNorth() + viewDistance)) {
            double distanceRatio = 1.0 - box.getDistanceInsideBorder(camera.getPos().x, camera.getPos().y, camera.getPos().z) / viewDistance;
            distanceRatio = Math.pow(distanceRatio, 4.0);
            distanceRatio = MathHelper.clamp(distanceRatio, 0.0, 1.0);
            double camX = context.camera().getPos().x;
            double camY = context.camera().getPos().y;
            double camZ = context.camera().getPos().z;
            double farPlane = context.gameRenderer().getFarPlaneDistance();
            RenderSystem.enableBlend();
            RenderSystem.enableDepthTest();
            RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE, GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ZERO);
            RenderSystem.setShaderTexture(0, texture);
            RenderSystem.depthMask(MinecraftClient.isFabulousGraphicsOrBetter());
            RenderSystem.setShaderColor(r, g, b, (float) distanceRatio);
            RenderSystem.setShader(GameRenderer::getPositionTexProgram);
            RenderSystem.polygonOffset(-3.0f, -3.0f);
            RenderSystem.enablePolygonOffset();
            RenderSystem.disableCull();
            float time = (float) (Util.getMeasuringTimeMs() % 3000L) / 3000.0F;
            float fraction = (float) (-MathHelper.fractionalPart(camY * 0.25));
            float fractionWDistance = fraction + (float) farPlane;
            float vScalar = 0.2f;
            BufferBuilder builder = Tessellator.getInstance().begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);

            double beforeNorthBound = Math.max(MathHelper.floor(camZ - viewDistance), box.getBoundNorth());
            double beforeSouthBound = Math.min(MathHelper.ceil(camZ + viewDistance), box.getBoundSouth());
            float zBound = (MathHelper.floor(beforeNorthBound) & 1) * 0.5f;
            double beforeDownBound = Math.max(MathHelper.floor(camY - viewDistance), box.getBoundDown());
            double beforeUpBound = Math.min(MathHelper.ceil(camY + viewDistance), box.getBoundUp());
            float yBound = (MathHelper.floor(beforeDownBound) & 1) * 0.5f;
            /*
             * East Plane
             */
            if (camX > box.getBoundEast() - viewDistance) {
                float iteration = zBound;
                float iteration2 = yBound;
                for (double t = beforeNorthBound; t < beforeSouthBound; iteration += 0.5f, iteration2 += 0.5f) {
                    double u = Math.min(1.0, beforeSouthBound - t);
                    float v = (float)u * 0.5F;
                    double u2 = Math.min(1.0, beforeUpBound - t);
                    float v2 = (float)u2 * 0.5F;
                    builder.vertex((float) (box.getBoundEast() - camX), (float) (box.getBoundDown() - camY), (float) (t - camZ)).texture(time - iteration, (float) (time + (v2 + iteration2)));
                    builder.vertex((float) (box.getBoundEast() - camX), (float) (box.getBoundDown() - camY), (float) (t + u - camZ)).texture(time - (v + iteration), (float) (time - (v2 + iteration2)));
                    builder.vertex((float) (box.getBoundEast() - camX), (float) (box.getBoundUp() - camY), (float) (t + u - camZ)).texture(time - (v + iteration), (float) (time + (iteration2)));
                    builder.vertex((float) (box.getBoundEast() - camX), (float) (box.getBoundUp() - camY), (float) (t - camZ)).texture(time - iteration, (float) (time - (iteration2)));
                    t++;
                }
            }
            /*
             * West Plane
             */
            if (camX < box.getBoundWest() + viewDistance) {
                float iteration = zBound;
                for (double t = beforeNorthBound; t < beforeSouthBound; iteration += 0.5f) {
                    double u = Math.min(1.0, beforeSouthBound - t);
                    float v = (float)u * 0.5F;
                    builder.vertex((float) (box.getBoundWest() - camX), (float) (box.getBoundDown() - camY), (float) (t - camZ)).texture(1 + iteration, (float) (time - (fraction + vScalar * box.getBoundUp())));
                    builder.vertex((float) (box.getBoundWest() - camX), (float) (box.getBoundDown() - camY), (float) (t + u - camZ)).texture(1 + (v + iteration), (float) (time - (fraction + vScalar * box.getBoundUp())));
                    builder.vertex((float) (box.getBoundWest() - camX), (float) (box.getBoundUp() - camY), (float) (t + u - camZ)).texture(1 + (v + iteration), (float) (time + fraction));
                    builder.vertex((float) (box.getBoundWest() - camX), (float) (box.getBoundUp() - camY), (float) (t - camZ)).texture(1 + iteration, (float) (time + fraction));
                    t++;
                }
            }

            double beforeWestBound = Math.max(MathHelper.floor(camZ - viewDistance), box.getBoundWest());
            double beforeEastBound = Math.min(MathHelper.ceil(camZ + viewDistance), box.getBoundEast());
            float xBound = (MathHelper.floor(beforeWestBound) & 1) * 0.5f;
            /*
             * South Plane
             */
            if (camZ > box.getBoundSouth() - viewDistance) {
                float iteration = xBound;
                for (double t = beforeWestBound; t < beforeEastBound; iteration += 0.5f) {
                    double u = Math.min(1.0, beforeEastBound - t);
                    float v = (float)u * 0.5F;
                    builder.vertex((float) (t - camX), (float) (box.getBoundDown() - camY), (float) (box.getBoundSouth() - camZ)).texture(1 + iteration, (float) (time + (fraction + vScalar * box.getBoundUp())));
                    builder.vertex((float) (t + u - camX), (float) (box.getBoundDown() - camY), (float) (box.getBoundSouth() - camZ)).texture(1 + (v + iteration), (float) (time + (fraction + vScalar * box.getBoundUp())));
                    builder.vertex((float) (t + u - camX), (float) (box.getBoundUp() - camY), (float) (box.getBoundSouth() - camZ)).texture(1 + (v + iteration), (float) (time + fraction));
                    builder.vertex((float) (t - camX), (float) (box.getBoundUp() - camY), (float) (box.getBoundSouth() - camZ)).texture(1 + iteration, (float) (time + fraction));
                    t++;
                }
            }
            /*
             * North Plane
             */
            if (camZ < box.getBoundNorth() + viewDistance) {
                float iteration = xBound;
                for (double t = beforeWestBound; t < beforeEastBound; iteration += 0.5f) {
                    double u = Math.min(1.0, beforeEastBound - t);
                    float v = (float)u * 0.5F;
                    builder.vertex((float) (t - camX), (float) (box.getBoundDown() - camY), (float) (box.getBoundNorth() - camZ)).texture(1 - iteration, (float) (time + (1 - iteration)));
                    builder.vertex((float) (t + u - camX), (float) (box.getBoundDown() - camY), (float) (box.getBoundNorth() - camZ)).texture(1 - (v + iteration), (float) (time + (1 - iteration)));
                    builder.vertex((float) (t + u - camX), (float) (box.getBoundUp() - camY), (float) (box.getBoundNorth() - camZ)).texture(1 - (v + iteration), (float) (time + 1 - iteration));
                    builder.vertex((float) (t - camX), (float) (box.getBoundUp() - camY), (float) (box.getBoundNorth() - camZ)).texture(1 - iteration, (float) (time + 1 - iteration));
                    t++;
                }
            }

            BuiltBuffer builtBuffer = builder.endNullable();
            if (builtBuffer != null) {
                BufferRenderer.drawWithGlobalProgram(builtBuffer);
            }
            RenderSystem.enableCull();
            RenderSystem.polygonOffset(0.0f, 0.0f);
            RenderSystem.disablePolygonOffset();
            RenderSystem.disableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
            RenderSystem.depthMask(true);
        }
    }


    /**
     * Uses a single texture that is tiled like the one used in {@link #renderBoxCube(WorldRenderContext, ExpansionBox, Identifier, float, float, float)}
     */
    public static void renderBoxSidesTiled(WorldRenderContext context, ExpansionBox box, Identifier texture, float r, float g, float b) {
        double viewDistance = context.worldRenderer().client.options.getClampedViewDistance() * 16;
        ServerTickEvents.START_SERVER_TICK.register(server -> {
            box.tick();
        });
        Camera camera = context.camera();
        if (!(camera.getPos().x < box.getBoundEast() - viewDistance)
                || !(camera.getPos().x > box.getBoundWest() + viewDistance)
                || !(camera.getPos().y < box.getBoundUp() - viewDistance)
                || !(camera.getPos().y > box.getBoundDown() + viewDistance)
                || !(camera.getPos().z < box.getBoundSouth() - viewDistance)
                || !(camera.getPos().z > box.getBoundNorth() + viewDistance)) {
            double distanceRatio = 1.0 - box.getDistanceInsideBorder(camera.getPos().x, camera.getPos().y, camera.getPos().z) / viewDistance;
            distanceRatio = Math.pow(distanceRatio, 4.0);
            distanceRatio = MathHelper.clamp(distanceRatio, 0.0, 1.0);
            double camX = context.camera().getPos().x;
            double camY = context.camera().getPos().y;
            double camZ = context.camera().getPos().z;
            double farPlane = context.gameRenderer().getFarPlaneDistance();
            RenderSystem.enableBlend();
            RenderSystem.enableDepthTest();
            RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE, GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ZERO);
            RenderSystem.setShaderTexture(0, texture);
            RenderSystem.depthMask(MinecraftClient.isFabulousGraphicsOrBetter());
            RenderSystem.setShaderColor(r, g, b, (float) distanceRatio);
            RenderSystem.setShader(GameRenderer::getPositionTexProgram);
            RenderSystem.polygonOffset(-3.0f, -3.0f);
            RenderSystem.enablePolygonOffset();
            RenderSystem.disableCull();
            float time = (float) (Util.getMeasuringTimeMs() % 3000L) / 3000.0F;
            float fraction = (float) (-MathHelper.fractionalPart(camY * 0.25));
            float fractionWDistance = fraction + (float) farPlane;
            float vScalar = 0.2f;
            BufferBuilder builder = Tessellator.getInstance().begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);

            double beforeNorthBound = Math.max(MathHelper.floor(camZ - viewDistance), box.getBoundNorth());
            double beforeSouthBound = Math.min(MathHelper.ceil(camZ + viewDistance), box.getBoundSouth());
            float zBound = (MathHelper.floor(beforeNorthBound) & 1) * 0.5f;
            /*
             * East Plane
             */
            if (camX > box.getBoundEast() - viewDistance) {
                float iteration = zBound;
                for (double t = beforeNorthBound; t < beforeSouthBound; iteration += 0.5f) {
                    double u = Math.min(1.0, beforeSouthBound - t);
                    float v = (float)u * 0.5F;
                    builder.vertex((float) (box.getBoundEast() - camX), (float) (box.getBoundDown() - camY), (float) (t - camZ)).texture(1 - iteration, (float) (time + (fraction + vScalar * box.getBoundUp())));
                    builder.vertex((float) (box.getBoundEast() - camX), (float) (box.getBoundDown() - camY), (float) (t + u - camZ)).texture(1 - (v + iteration), (float) (time + (fraction + vScalar * box.getBoundUp())));
                    builder.vertex((float) (box.getBoundEast() - camX), (float) (box.getBoundUp() - camY), (float) (t + u - camZ)).texture(1 - (v + iteration), (float) (time + fraction));
                    builder.vertex((float) (box.getBoundEast() - camX), (float) (box.getBoundUp() - camY), (float) (t - camZ)).texture(1 - iteration, (float) (time + fraction));
                    t++;
                }
            }
            /*
             * West Plane
             */
            if (camX < box.getBoundWest() + viewDistance) {
                float iteration = zBound;
                for (double t = beforeNorthBound; t < beforeSouthBound; iteration += 0.5f) {
                    double u = Math.min(1.0, beforeSouthBound - t);
                    float v = (float)u * 0.5F;
                    builder.vertex((float) (box.getBoundWest() - camX), (float) (box.getBoundDown() - camY), (float) (t - camZ)).texture(1 + iteration, (float) (time + (fraction + vScalar * box.getBoundUp())));
                    builder.vertex((float) (box.getBoundWest() - camX), (float) (box.getBoundDown() - camY), (float) (t + u - camZ)).texture(1 + (v + iteration), (float) (time + (fraction + vScalar * box.getBoundUp())));
                    builder.vertex((float) (box.getBoundWest() - camX), (float) (box.getBoundUp() - camY), (float) (t + u - camZ)).texture(1 + (v + iteration), (float) (time + fraction));
                    builder.vertex((float) (box.getBoundWest() - camX), (float) (box.getBoundUp() - camY), (float) (t - camZ)).texture(1 + iteration, (float) (time + fraction));
                    t++;
                }
            }

            double beforeWestBound = Math.max(MathHelper.floor(camZ - viewDistance), box.getBoundWest());
            double beforeEastBound = Math.min(MathHelper.ceil(camZ + viewDistance), box.getBoundEast());
            float xBound = (MathHelper.floor(beforeWestBound) & 1) * 0.5f;
            /*
             * South Plane
             */
            if (camZ > box.getBoundSouth() - viewDistance) {
                float iteration = xBound;
                for (double t = beforeWestBound; t < beforeEastBound; iteration += 0.5f) {
                    double u = Math.min(1.0, beforeEastBound - t);
                    float v = (float)u * 0.5F;
                    builder.vertex((float) (t - camX), (float) (box.getBoundDown() - camY), (float) (box.getBoundSouth() - camZ)).texture(1 + iteration, (float) (time + (fraction + vScalar * box.getBoundUp())));
                    builder.vertex((float) (t + u - camX), (float) (box.getBoundDown() - camY), (float) (box.getBoundSouth() - camZ)).texture(1 + (v + iteration), (float) (time + (fraction + vScalar * box.getBoundUp())));
                    builder.vertex((float) (t + u - camX), (float) (box.getBoundUp() - camY), (float) (box.getBoundSouth() - camZ)).texture(1 + (v + iteration), (float) (time + fraction));
                    builder.vertex((float) (t - camX), (float) (box.getBoundUp() - camY), (float) (box.getBoundSouth() - camZ)).texture(1 + iteration, (float) (time + fraction));
                    t++;
                }
            }
            /*
             * North Plane
             */
            if (camZ < box.getBoundNorth() + viewDistance) {
                float iteration = xBound;
                for (double t = beforeWestBound; t < beforeEastBound; iteration += 0.5f) {
                    double u = Math.min(1.0, beforeEastBound - t);
                    float v = (float)u * 0.5F;
                    builder.vertex((float) (t - camX), (float) (box.getBoundDown() - camY), (float) (box.getBoundNorth() - camZ)).texture(1 - iteration, (float) (time + (fraction + vScalar * box.getBoundUp())));
                    builder.vertex((float) (t + u - camX), (float) (box.getBoundDown() - camY), (float) (box.getBoundNorth() - camZ)).texture(1 - (v + iteration), (float) (time + (fraction + vScalar * box.getBoundUp())));
                    builder.vertex((float) (t + u - camX), (float) (box.getBoundUp() - camY), (float) (box.getBoundNorth() - camZ)).texture(1 - (v + iteration), (float) (time + fraction));
                    builder.vertex((float) (t - camX), (float) (box.getBoundUp() - camY), (float) (box.getBoundNorth() - camZ)).texture(1 - iteration, (float) (time + fraction));
                    t++;
                }
            }

            BuiltBuffer builtBuffer = builder.endNullable();
            if (builtBuffer != null) {
                BufferRenderer.drawWithGlobalProgram(builtBuffer);
            }
            RenderSystem.enableCull();
            RenderSystem.polygonOffset(0.0f, 0.0f);
            RenderSystem.disablePolygonOffset();
            RenderSystem.disableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
            RenderSystem.depthMask(true);
        }
    }

    /**
     * Renders the top and bottom face of a box and renders a "radial" texture.
     * Uses a single texture that spans and is scaled to the box size.
     */
    public static void renderBoxRadialEnds(WorldRenderContext context, ExpansionBox box, Identifier texture, float r, float g, float b) {
        double viewDistance = context.worldRenderer().client.options.getClampedViewDistance() * 16;
        ServerTickEvents.START_SERVER_TICK.register(server -> {
            box.tick();
        });
        Camera camera = context.camera();
        if (!(camera.getPos().x < box.getBoundEast() - viewDistance)
                || !(camera.getPos().x > box.getBoundWest() + viewDistance)
                || !(camera.getPos().y < box.getBoundUp() - viewDistance)
                || !(camera.getPos().y > box.getBoundDown() + viewDistance)
                || !(camera.getPos().z < box.getBoundSouth() - viewDistance)
                || !(camera.getPos().z > box.getBoundNorth() + viewDistance)) {
            double distanceRatio = 1.0 - box.getDistanceInsideBorder(camera.getPos().x, camera.getPos().y, camera.getPos().z) / viewDistance;
            distanceRatio = Math.pow(distanceRatio, 4.0);
            distanceRatio = MathHelper.clamp(distanceRatio, 0.0, 1.0);
            double camX = context.camera().getPos().x;
            double camY = context.camera().getPos().y;
            double camZ = context.camera().getPos().z;
            double farPlane = context.gameRenderer().getFarPlaneDistance();
            RenderSystem.enableBlend();
            RenderSystem.enableDepthTest();
            RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE, GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ZERO);
            RenderSystem.setShaderTexture(0, texture);
            RenderSystem.depthMask(MinecraftClient.isFabulousGraphicsOrBetter());
            RenderSystem.setShaderColor(r, g, b, (float) distanceRatio);
            RenderSystem.setShader(GameRenderer::getPositionTexProgram);
            RenderSystem.polygonOffset(-3.0f, -3.0f);
            RenderSystem.enablePolygonOffset();
            RenderSystem.disableCull();
            float time = (float) (Util.getMeasuringTimeMs() % 3000L) / 3000.0F;
            float fraction = (float) (-MathHelper.fractionalPart(camY * 0.25));
            float fractionWDistance = fraction + (float) context.gameRenderer().getViewDistance();
            float vScalar = 0.2f;
            BufferBuilder builder = Tessellator.getInstance().begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);

            double beforeNorthBound = Math.max(MathHelper.floor(camZ - viewDistance), box.getBoundNorth());
            double beforeSouthBound = Math.min(MathHelper.ceil(camZ + viewDistance), box.getBoundSouth());
            float zBound = (MathHelper.floor(beforeNorthBound) & 1) * 0.5f;
            double beforeWestBound = Math.max(MathHelper.floor(camZ - viewDistance), box.getBoundWest());
            double beforeEastBound = Math.min(MathHelper.ceil(camZ + viewDistance), box.getBoundEast());
            float xBound = (MathHelper.floor(beforeWestBound) & 1) * 0.5f;
            double beforeDownBound = Math.max(MathHelper.floor(camY - viewDistance), box.getBoundDown());
            double beforeUpBound = Math.min(MathHelper.ceil(camY + viewDistance), box.getBoundUp());
            float yBound = (MathHelper.floor(beforeDownBound) & 1) * 0.5f;
            double beforeCenterZNorth = Math.min(MathHelper.ceil(camZ + viewDistance), box.getCenterZ());
            /*
             * Up Plane - North
             */
            if (camY > box.getBoundUp() - viewDistance) {
                float iteration = xBound;
                int i = 0;
                for (double t = beforeWestBound; t < beforeEastBound; iteration += 0.5f) {
                    double u = Math.min(1.0, beforeEastBound - t);
                    float v = (float)u * 0.5F;
                    double uZ = Math.min(1.0, beforeCenterZNorth - t);
                    double uZ2 = Math.min(1.0, beforeCenterZNorth - (t + 1));
                    builder.vertex((float) (t - camX), (float) (box.getBoundUp() - camY), (float) ((beforeNorthBound + uZ) - camZ)).texture(1 - iteration, (float) (time + (fraction + vScalar * box.getBoundUp())));
                    builder.vertex((float) (t + u - camX), (float) (box.getBoundUp() - camY), (float) ((beforeNorthBound + uZ2) - camZ)).texture(1 - (v + iteration), (float) (time + (fraction + vScalar * box.getBoundUp())));
                    builder.vertex((float) (t + u - camX), (float) (box.getBoundUp() - camY), (float) (box.getBoundNorth() - camZ)).texture(1 - (v + iteration), (float) (time + fraction));
                    builder.vertex((float) (t - camX), (float) (box.getBoundUp() - camY), (float) (box.getBoundNorth() - camZ)).texture(1 - iteration, (float) (time + fraction));
                    i++;
                    t++;
                }
            }
            /*
             * Down Plane
             */
            if (camY < box.getBoundDown() + viewDistance) {
                float iteration = xBound;
                for (double t = beforeWestBound; t < beforeEastBound; iteration += 0.5f) {
                    double u = Math.min(1.0, beforeEastBound - t);
                    float v = (float)u * 0.5F;
                    builder.vertex((float) (t - camX), (float) (box.getBoundDown() - camY), (float) (box.getBoundNorth() - camZ)).texture(1 + iteration, (float) (time + (fraction + vScalar * box.getBoundUp())));
                    builder.vertex((float) (t + u - camX), (float) (box.getBoundDown() - camY), (float) (box.getBoundNorth() - camZ)).texture(1 + (v + iteration), (float) (time + (fraction + vScalar * box.getBoundUp())));
                    builder.vertex((float) (t + u - camX), (float) (box.getBoundDown() - camY), (float) (box.getBoundSouth() - camZ)).texture(1 + (v + iteration), (float) (time + fraction));
                    builder.vertex((float) (t - camX), (float) (box.getBoundDown() - camY), (float) (box.getBoundSouth() - camZ)).texture(1 + iteration, (float) (time + fraction));
                    t++;
                }
            }

            BuiltBuffer builtBuffer = builder.endNullable();
            if (builtBuffer != null) {
                BufferRenderer.drawWithGlobalProgram(builtBuffer);
            }
            RenderSystem.enableCull();
            RenderSystem.polygonOffset(0.0f, 0.0f);
            RenderSystem.disablePolygonOffset();
            RenderSystem.disableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
            RenderSystem.depthMask(true);
        }
    }

    /**
     * Renders the top and bottom face of a box and renders a "radial" texture.
     * Uses a single texture that is tiled like the one used in {@link #renderBoxCube(WorldRenderContext, ExpansionBox, Identifier, float, float, float)}
     */
    public static void renderBoxRadialEndsTiled(WorldRenderContext context, ExpansionBox box, Identifier texture, float r, float g, float b) {
        double viewDistance = context.worldRenderer().client.options.getClampedViewDistance() * 16;
        ServerTickEvents.START_SERVER_TICK.register(server -> {
            box.tick();
        });
        Camera camera = context.camera();
        if (!(camera.getPos().x < box.getBoundEast() - viewDistance)
                || !(camera.getPos().x > box.getBoundWest() + viewDistance)
                || !(camera.getPos().y < box.getBoundUp() - viewDistance)
                || !(camera.getPos().y > box.getBoundDown() + viewDistance)
                || !(camera.getPos().z < box.getBoundSouth() - viewDistance)
                || !(camera.getPos().z > box.getBoundNorth() + viewDistance)) {
            double distanceRatio = 1.0 - box.getDistanceInsideBorder(camera.getPos().x, camera.getPos().y, camera.getPos().z) / viewDistance;
            distanceRatio = Math.pow(distanceRatio, 4.0);
            distanceRatio = MathHelper.clamp(distanceRatio, 0.0, 1.0);
            double camX = context.camera().getPos().x;
            double camY = context.camera().getPos().y;
            double camZ = context.camera().getPos().z;
            double farPlane = context.gameRenderer().getFarPlaneDistance();
            RenderSystem.enableBlend();
            RenderSystem.enableDepthTest();
            RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE, GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ZERO);
            RenderSystem.setShaderTexture(0, texture);
            RenderSystem.depthMask(MinecraftClient.isFabulousGraphicsOrBetter());
            RenderSystem.setShaderColor(r, g, b, (float) distanceRatio);
            RenderSystem.setShader(GameRenderer::getPositionTexProgram);
            RenderSystem.polygonOffset(-3.0f, -3.0f);
            RenderSystem.enablePolygonOffset();
            RenderSystem.disableCull();
            float time = (float) (Util.getMeasuringTimeMs() % 3000L) / 3000.0F;
            float fraction = (float) (-MathHelper.fractionalPart(camY * 0.25));
            float fractionWDistance = fraction + (float) context.gameRenderer().getViewDistance();
            float vScalar = 0.2f;
            BufferBuilder builder = Tessellator.getInstance().begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);

            double beforeNorthBound = Math.max(MathHelper.floor(camZ - viewDistance), box.getBoundNorth());
            double beforeSouthBound = Math.min(MathHelper.ceil(camZ + viewDistance), box.getBoundSouth());
            float zBound = (MathHelper.floor(beforeNorthBound) & 1) * 0.5f;
            double beforeWestBound = Math.max(MathHelper.floor(camZ - viewDistance), box.getBoundWest());
            double beforeEastBound = Math.min(MathHelper.ceil(camZ + viewDistance), box.getBoundEast());
            float xBound = (MathHelper.floor(beforeWestBound) & 1) * 0.5f;
            double beforeDownBound = Math.max(MathHelper.floor(camY - viewDistance), box.getBoundDown());
            double beforeUpBound = Math.min(MathHelper.ceil(camY + viewDistance), box.getBoundUp());
            float yBound = (MathHelper.floor(beforeDownBound) & 1) * 0.5f;
            double beforeCenterZNorth = Math.min(MathHelper.ceil(camZ + viewDistance), box.getCenterZ());
            /*
             * Up Plane - North
             */
            if (camY > box.getBoundUp() - viewDistance) {
                float iteration = xBound;
                int i = 0;
                for (double t = beforeWestBound; t < beforeEastBound; iteration += 0.5f) {
                    double u = Math.min(1.0, beforeEastBound - t);
                    float v = (float)u * 0.5F;
                    double uZ = Math.min(1.0, beforeCenterZNorth - t);
                    double uZ2 = Math.min(1.0, beforeCenterZNorth - (t + 1));
                    builder.vertex((float) (t - camX), (float) (box.getBoundUp() - camY), (float) ((beforeNorthBound + uZ) - camZ)).texture(1 - iteration, (float) (time + (fraction + vScalar * box.getBoundUp())));
                    builder.vertex((float) (t + u - camX), (float) (box.getBoundUp() - camY), (float) ((beforeNorthBound + uZ2) - camZ)).texture(1 - (v + iteration), (float) (time + (fraction + vScalar * box.getBoundUp())));
                    builder.vertex((float) (t + u - camX), (float) (box.getBoundUp() - camY), (float) (box.getBoundNorth() - camZ)).texture(1 - (v + iteration), (float) (time + fraction));
                    builder.vertex((float) (t - camX), (float) (box.getBoundUp() - camY), (float) (box.getBoundNorth() - camZ)).texture(1 - iteration, (float) (time + fraction));
                    i++;
                    t++;
                }
            }
            /*
             * Down Plane
             */
            if (camY < box.getBoundDown() + viewDistance) {
                float iteration = xBound;
                for (double t = beforeWestBound; t < beforeEastBound; iteration += 0.5f) {
                    double u = Math.min(1.0, beforeEastBound - t);
                    float v = (float)u * 0.5F;
                    builder.vertex((float) (t - camX), (float) (box.getBoundDown() - camY), (float) (box.getBoundNorth() - camZ)).texture(1 + iteration, (float) (time + (fraction + vScalar * box.getBoundUp())));
                    builder.vertex((float) (t + u - camX), (float) (box.getBoundDown() - camY), (float) (box.getBoundNorth() - camZ)).texture(1 + (v + iteration), (float) (time + (fraction + vScalar * box.getBoundUp())));
                    builder.vertex((float) (t + u - camX), (float) (box.getBoundDown() - camY), (float) (box.getBoundSouth() - camZ)).texture(1 + (v + iteration), (float) (time + fraction));
                    builder.vertex((float) (t - camX), (float) (box.getBoundDown() - camY), (float) (box.getBoundSouth() - camZ)).texture(1 + iteration, (float) (time + fraction));
                    t++;
                }
            }

            BuiltBuffer builtBuffer = builder.endNullable();
            if (builtBuffer != null) {
                BufferRenderer.drawWithGlobalProgram(builtBuffer);
            }
            RenderSystem.enableCull();
            RenderSystem.polygonOffset(0.0f, 0.0f);
            RenderSystem.disablePolygonOffset();
            RenderSystem.disableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
            RenderSystem.depthMask(true);
        }

    }
}
