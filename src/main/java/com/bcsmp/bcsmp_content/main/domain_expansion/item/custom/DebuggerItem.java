package com.bcsmp.bcsmp_content.main.domain_expansion.item.custom;

import com.bcsmp.bcsmp_content.BCSMPContentMain;
import com.bcsmp.bcsmp_content.main.domain_expansion.network.packet.S2C.ExpansionBoxCenterChangedPacket;
import com.bcsmp.bcsmp_content.main.domain_expansion.network.packet.S2C.ExpansionBoxInterpolateSizePacket;
import com.bcsmp.bcsmp_content.main.domain_expansion.network.packet.S2C.ExpansionBoxSizeChangedPacket;
import com.bcsmp.bcsmp_content.main.domain_expansion.util.DEModTextureIds;
import com.bcsmp.bcsmp_content.main.domain_expansion.util.DebugState;
import com.bcsmp.bcsmp_content.main.domain_expansion.world.area.ExpansionBox;
import com.bcsmp.bcsmp_content.main.domain_expansion.world.area.ExpansionBoxListener;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class DebuggerItem extends Item {

    public DebuggerItem(Settings settings) {
        super(settings);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack handStack = player.getStackInHand(hand);
        MinecraftServer server = player.getServer();
        if (server == null) return TypedActionResult.fail(handStack);
        DebugState debugState = DebugState.getServerState(server);
        final ExpansionBox box = new ExpansionBox();
        /*box.addListener(new ExpansionBoxListener() {
            @Override
            public void onSizeChange(ExpansionBox box, double size) {
                server.getPlayerManager().sendToAll(new ExpansionBoxSizeChangedPacket(box));
            }

            @Override
            public void onInterpolateSize(ExpansionBox box, double fromSize, double toSize, long time) {
                server.getPlayerManager().sendToAll(new ExpansionBoxInterpolateSizePacket(box));
            }

            @Override
            public void onCenterChanged(ExpansionBox box, double centerX, double centerY, double centerZ) {
                server.getPlayerManager().sendToAll(new ExpansionBoxCenterChangedPacket(box));
            }
        });*/
        box.addListener(new ExpansionBoxListener.ExpansionBoxSyncer(box));
        box.load(box.write());
        box.setCenter(0.0, -60.0, 0.0);
        box.setSize(20);

        final ExpansionBox boxWithEnds = new ExpansionBox();
        boxWithEnds.addListener(new ExpansionBoxListener.ExpansionBoxSyncer(boxWithEnds));
        boxWithEnds.load(boxWithEnds.write());
        boxWithEnds.setCenter(0.0, -30.0, 0.0);
        boxWithEnds.setSize(20);
        if (!world.isClient) {
            if (!player.isSneaking()) {
                WorldRenderEvents.AFTER_ENTITIES.register(context -> {
                    if (debugState.shiftBool) {
                        this.renderBox(context, box, DEModTextureIds.DOMAIN_BORDER_TEXTURE, 1.0f, 1.0f, 1.0f);

                        //this.renderSides(context, boxWithEnds, DEModTextureIds.DOMAIN_BORDER_TEXTURE, 1.0f, 1.0f, 1.0f);
                        //this.renderEnds(context, boxWithEnds, DEModTextureIds.DOMAIN_BORDER_TEXTURE, 1.0f, 1.0f, 1.0f);
                    }
                });
                player.sendMessage(Text.literal("huzzah"));
            } else {
                if (debugState.shiftBool) {
                    debugState.setShiftBool(false);
                } else {
                    debugState.setShiftBool(true);
                }
                player.sendMessage(Text.literal("shift bool: " + debugState.shiftBool));
            }
        } else {
            if (!player.isSneaking()) {

            } else {

            }
        }
        return TypedActionResult.consume(handStack);
    }
    /**
     * {@link #renderBox(WorldRenderContext, ExpansionBox, Identifier, float, float, float)} makes a box from a single texture.
     * The ends are iterated along the x-axis.
     * {@code r}, {@code g}, {@code b} are shader color values. Alpha is controlled by render distance.
     */
    public void renderBox(WorldRenderContext context, ExpansionBox box, Identifier texture, float r, float g, float b) {
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
     * {@link #renderSides(WorldRenderContext, ExpansionBox, Identifier, float, float, float)} and {@link #renderEnds(WorldRenderContext, ExpansionBox, Identifier, float, float, float)}
     * should use the same {@code ExpansionBox} to ensure the ends are lined up with the sides.
     * <br>
     * {@link #renderEnds(WorldRenderContext, ExpansionBox, Identifier, float, float, float)} is used for a texture that radiated from the center point.
     * {@link #renderSides(WorldRenderContext, ExpansionBox, Identifier, float, float, float)} is used for a single texture similar to {@link #renderBox(WorldRenderContext, ExpansionBox, Identifier, float, float, float)}.
     */
    public void renderSides(WorldRenderContext context, ExpansionBox box, Identifier texture, float r, float g, float b) {
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

    public void renderEnds(WorldRenderContext context, ExpansionBox box, Identifier texture, float r, float g, float b) {
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
            BufferBuilder builder = Tessellator.getInstance().begin(VertexFormat.DrawMode.TRIANGLES, VertexFormats.POSITION_TEXTURE);

            double beforeWestBound = Math.max(MathHelper.floor(camZ - viewDistance), box.getBoundWest());
            double beforeEastBound = Math.min(MathHelper.ceil(camZ + viewDistance), box.getBoundEast());
            float xBound = (MathHelper.floor(beforeWestBound) & 1) * 0.5f;
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
                    //builder.vertex((float) (t  - camX), (float) (box.getBoundUp() - camY), (float) (box.getBoundNorth() / 2 - (camZ + box.getSize() / 4))).texture(1 - iteration, (float) (time + fractionWDistance));
                    builder.vertex((float) (t + u / 2 - camX), (float) (box.getBoundUp() - camY), (float) (box.getBoundNorth() / 2 - (camZ + box.getSize() / 4))).texture(1 - (v + iteration), (float) (time + (fraction + 0.25 * box.getBoundUp())));
                    builder.vertex((float) (t + u - camX), (float) (box.getBoundUp() - camY), (float) (box.getBoundSouth() / 2 - (camZ + box.getSize() / 4))).texture(1 - (v + iteration), (float) (time + fraction));
                    builder.vertex((float) (t - camX), (float) (box.getBoundUp() - camY), (float) (box.getBoundSouth() / 2 - (camZ + box.getSize() / 4)) / 2).texture(1 - iteration, (float) (time + fraction));
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
                    builder.vertex((float) (t - camX), (float) (box.getBoundDown() - camY), (float) (box.getBoundNorth() - camZ)).texture(1 + iteration, (float) (time + (fraction + 0.25 * box.getBoundUp())));
                    builder.vertex((float) (t + u - camX), (float) (box.getBoundDown() - camY), (float) (box.getBoundNorth() - camZ)).texture(1 + (v + iteration), (float) (time + (fraction + 0.25 * box.getBoundUp())));
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
