package com.bcsmp.bcsmp_content.main.domain_expansion.item.custom;

import com.bcsmp.bcsmp_content.main.domain_expansion.util.DEModTextureIds;
import com.bcsmp.bcsmp_content.main.domain_expansion.util.DebugState;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
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
        if (!world.isClient) {
            if (!player.isSneaking()) {
                WorldRenderEvents.AFTER_ENTITIES.register(context -> {
                    if (debugState.shiftBool) {
                        this.renderBox(context, -5.0f, -5.0f, -5.0f, 5.0f, 5.0f, 5.0f, 1.0f, 1.0f, 1.0f, 1.0f);
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

    private void renderBox(WorldRenderContext context, float minX, float minY, float minZ, float maxX, float maxY, float maxZ, float r, float g, float b, float a) {
        double camX = context.camera().getPos().x;
        double camZ = context.camera().getPos().z;
        double viewDistance = context.worldRenderer().client.options.getClampedViewDistance() * 16;
        double farPlane = context.gameRenderer().getFarPlaneDistance();
        RenderSystem.enableBlend();
        RenderSystem.enableDepthTest();
        RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE, GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ZERO);
        RenderSystem.depthMask(MinecraftClient.isFabulousGraphicsOrBetter());
        RenderSystem.setShaderColor(r, g, b, a);
        RenderSystem.setShaderTexture(0, DEModTextureIds.DOMAIN_BORDER_TEXTURE);
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.polygonOffset(-3.0f, -3.0f);
        RenderSystem.enablePolygonOffset();
        RenderSystem.disableCull();
        float time = (float)(Util.getMeasuringTimeMs() % 3000L) / 3000.0F;
        float fraction = (float)(-MathHelper.fractionalPart(context.camera().getPos().y * 0.5));
        float fractionWDistance = fraction + (float)farPlane;
        BufferBuilder builder = Tessellator.getInstance().begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);

        float maxXBound = (MathHelper.floor(maxX) & 1) * 0.5f;
        if (camZ > minZ - viewDistance) {
            float iterator = maxXBound;
            for (double t = maxX; t < minX; iterator += 0.5f) {
                float minXBound = (float)(Math.min(1.0, minX - t)) * 0.5F;
                builder.vertex(minX, minY, minZ).texture(time - iterator, time + fractionWDistance);
                builder.vertex(maxX, minY, minZ).texture(time - (minXBound + iterator), time + fractionWDistance);
                builder.vertex(minX, maxY, minZ).texture(time - (minXBound + iterator), time + fraction);
                builder.vertex(maxX, maxY, minZ).texture(time - iterator, time + fraction);
                t++;
            }
        }

        BuiltBuffer builtBuffer = builder.endNullable();
        if (builtBuffer != null) {
            BufferRenderer.drawWithGlobalProgram(builtBuffer);
        }
    }
}
