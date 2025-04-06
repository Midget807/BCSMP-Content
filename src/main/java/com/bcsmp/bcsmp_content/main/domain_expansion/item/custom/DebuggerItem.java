package com.bcsmp.bcsmp_content.main.domain_expansion.item.custom;

import com.bcsmp.bcsmp_content.BCSMPContentMain;
import com.bcsmp.bcsmp_content.main.domain_expansion.network.packet.S2C.ExpansionBoxCenterChangedPacket;
import com.bcsmp.bcsmp_content.main.domain_expansion.network.packet.S2C.ExpansionBoxInterpolateSizePacket;
import com.bcsmp.bcsmp_content.main.domain_expansion.network.packet.S2C.ExpansionBoxSizeChangedPacket;
import com.bcsmp.bcsmp_content.main.domain_expansion.util.DEModTextureIds;
import com.bcsmp.bcsmp_content.main.domain_expansion.util.DebugState;
import com.bcsmp.bcsmp_content.main.domain_expansion.world.area.ExpansionBox;
import com.bcsmp.bcsmp_content.main.domain_expansion.world.area.ExpansionBoxListener;
import com.bcsmp.bcsmp_content.main.domain_expansion.world.area.client.RenderExpansionBox;
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
        boxWithEnds.setCenter(0.0, -60.0, 0.0);
        boxWithEnds.setSize(20);
        if (!world.isClient) {
            if (!player.isSneaking()) {
                WorldRenderEvents.AFTER_ENTITIES.register(context -> {
                    if (debugState.shiftBool) {
                        //RenderExpansionBox.renderBoxCube(context, box, DEModTextureIds.DOMAIN_BORDER_TEXTURE, 1.0f, 1.0f, 1.0f);

                        RenderExpansionBox.renderBoxSides(context, boxWithEnds, DEModTextureIds.DOMAIN_BORDER_TEXTURE, 1.0f, 1.0f, 1.0f);
                        RenderExpansionBox.renderBoxRadialEnds(context, boxWithEnds, DEModTextureIds.DEBUG_SQUARE_TEXTURE, 1.0f, 1.0f, 1.0f);
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

}
