package com.bcsmp.bcsmp_content.main.domain_expansion.item.custom;

import com.bcsmp.bcsmp_content.main.domain_expansion.component.DEModDataComponentTypes;
import com.bcsmp.bcsmp_content.main.domain_expansion.util.DEModTextureIds;
import com.bcsmp.bcsmp_content.main.domain_expansion.world.area.ExpansionSelectionFlash;
import com.bcsmp.bcsmp_content.mixin.domain_expansion.client.WorldRendererMixin;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.fabricmc.loader.impl.lib.accesswidener.AccessWidener;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.Util;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.List;
import java.util.UUID;

public class DomainExpansionItem extends Item {
    public static final String OWNER_KEY = "Owner";
    public static final String TARGETS_KEY = "Targets";
    public static final String RADIUS_KEY = "Radius";
    public PlayerEntity owner;
    public UUID ownerUuid;
    public DefaultedList<PlayerEntity> targets = DefaultedList.of();
    public DefaultedList<UUID> targetUuids = DefaultedList.of();
    public int domainRadius = 10;

    public DomainExpansionItem(Settings settings, PlayerEntity owner) {
        super(settings);
        this.owner = owner;
    }


    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack handStack = player.getStackInHand(hand);
        if (!world.isClient) {
            if (!player.isSneaking()) {
                //Info Check
                targets = this.getPlayersInRange(world, player);
                if (targets.isEmpty()) {
                    player.sendMessage(Text.literal("No players within range").formatted(Formatting.RED), true);
                } else {
                    for (PlayerEntity target : targets) {
                        target.sendMessage(Text.literal("You are within range of ").formatted(Formatting.RED).append(player.getName() + "'s").formatted(Formatting.BOLD).formatted(Formatting.RED).append(Text.literal(" domain expansion").formatted(Formatting.RED)), true);
                        if (targets.size() == 1) {
                            player.sendMessage(Text.literal(targets.size() + " player within range").formatted(Formatting.AQUA), true);
                        } else {
                            player.sendMessage(Text.literal(targets.size() + " players within range").formatted(Formatting.AQUA), true);
                        }
                    }
                }
                targets = DefaultedList.of();
                targetUuids = DefaultedList.of();
                if (owner != null) {
                    if (ownerUuid != null) {
                        ownerUuid = null;
                    }
                    owner = null;
                }
                this.domainRadius = 10;
                
            } else {
                //Applies info
                targets = this.getPlayersInRange(world, player);
                owner = player;
                ownerUuid = owner.getUuid();

                NbtCompound ownerCompound = new NbtCompound();
                ownerCompound.putUuid(OWNER_KEY, ownerUuid);
                handStack.set(DEModDataComponentTypes.EXPANDER_OWNER, NbtComponent.of(ownerCompound));

                for (PlayerEntity playerEntity : targets) {
                    targetUuids.add(playerEntity.getUuid());
                }
                NbtList targetNbtList = new NbtList();
                for (UUID targetUuid : targetUuids) {
                    NbtCompound newTargetNbt = new NbtCompound();
                    newTargetNbt.putUuid(TARGETS_KEY, targetUuid);
                    targetNbtList.add(newTargetNbt);
                }
                NbtCompound targetListCompound = new NbtCompound();
                targetListCompound.put(TARGETS_KEY, targetNbtList);
                handStack.set(DEModDataComponentTypes.EXPANDER_TARGETS, NbtComponent.of(targetListCompound));

                this.domainRadius = getDomainRadius(player);
                handStack.set(DEModDataComponentTypes.EXPANDER_RADIUS, this.domainRadius);
            }
        }
        return TypedActionResult.pass(handStack);
    }

    public int getDomainRadius(PlayerEntity player) {
        return (player.experienceLevel + 10);
    }

    public DefaultedList<PlayerEntity> getPlayersInRange(World world, PlayerEntity player) {
        Box box = player.getBoundingBox().expand(this.getDomainRadius(player), this.getDomainRadius(player), this.getDomainRadius(player));
        List<PlayerEntity> entitiesInBox = world.getEntitiesByClass(PlayerEntity.class, box, EntityPredicates.EXCEPT_SPECTATOR);
        DefaultedList<PlayerEntity> entitiesInSphere = DefaultedList.of();
        for (PlayerEntity target : entitiesInBox) {
            if (player.distanceTo(target) <= this.getDomainRadius(player) && target != player) {
                entitiesInSphere.add(target);
                target.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 20), player);
            }
        }
        flashSelection(player);
        //Damages player using item to avoid the glowing effect being abused outside the intended purposes
        player.damage(player.getDamageSources().magic(), 14.0f);
        return entitiesInSphere;
    }
    public void flashSelection(PlayerEntity player) {
        ExpansionSelectionFlash selectionFlash = new ExpansionSelectionFlash(player);
        WorldRenderEvents.START.register(context -> {
            Camera camera = context.gameRenderer().camera;
            double clampedViewDistance = context.worldRenderer().client.options.getClampedViewDistance() * 16;
            if (!(camera.getPos().x < selectionFlash.getBoundEast() - clampedViewDistance)
                    || !(camera.getPos().x < selectionFlash.getBoundWest() + clampedViewDistance)
                    || !(camera.getPos().z < selectionFlash.getBoundSouth() - clampedViewDistance)
                    || !(camera.getPos().z < selectionFlash.getBoundNorth() + clampedViewDistance)
            ) {
                double alpha = 1.0 - selectionFlash.getDistanceInArea(camera.getPos().x, camera.getPos().y) / clampedViewDistance;
                alpha = Math.pow(alpha, 4.0);
                alpha = MathHelper.clamp(alpha, 0.0, 1.0);
                double cameraX = camera.getPos().x;
                double cameraZ = camera.getPos().y;
                double planeDistance = context.gameRenderer().getFarPlaneDistance();
                RenderSystem.enableBlend();
                RenderSystem.enableDepthTest();
                RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE, GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ZERO);
                RenderSystem.setShaderTexture(0, DEModTextureIds.DOMAIN_BORDER_TEXTURE);
                RenderSystem.depthMask(MinecraftClient.isFabulousGraphicsOrBetter());
                int colour = 0xF1F152;
                float alphaSin = MathHelper.clamp(MathHelper.sin((float) alpha), 0.0f, 0.5f);
                RenderSystem.setShaderColor(0xF1, 0xF1, 0x52, alphaSin);
                RenderSystem.setShader(GameRenderer::getPositionTexProgram);
                RenderSystem.polygonOffset(-3.0f, -3.0f);
                RenderSystem.enablePolygonOffset();
                RenderSystem.disableCull();
                float m = (float) (Util.getMeasuringTimeMs() % 3000L) / 3000.0F;
                float n = (float) (-MathHelper.fractionalPart(cameraZ * 0.5));
                float o = n + (float)planeDistance;
                BufferBuilder bufferBuilder = Tessellator.getInstance().begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
                double p = Math.max(MathHelper.floor(cameraZ - clampedViewDistance), selectionFlash.getBoundNorth());
                double q = Math.max(MathHelper.ceil(cameraZ + clampedViewDistance), selectionFlash.getBoundSouth());
                float r = (MathHelper.floor(p) & 1) * 0.5F;
                if (cameraX > selectionFlash.getBoundEast() - clampedViewDistance) {
                    float s = r;
                    for (double t = p; t < q; s+= 0.5F) {
                        double u = Math.min(1.0, q -t);
                        float v = (float) u * 0.5f;
                        bufferBuilder.vertex((float)(selectionFlash.getBoundEast() - cameraX), (float)(-planeDistance), (float)(t - cameraZ)).texture(m - s, m + o);
                        bufferBuilder.vertex((float)(selectionFlash.getBoundEast() - cameraX), (float)(-planeDistance), (float)(t + u - cameraZ)).texture(m - (v + s), m + o);
                        bufferBuilder.vertex((float)(selectionFlash.getBoundEast() - cameraX), (float)(planeDistance), (float)(t + u - cameraZ)).texture(m - (v + s), m + n);
                        bufferBuilder.vertex((float)(selectionFlash.getBoundEast() - cameraX), (float)(planeDistance), (float)(t - cameraZ)).texture(m - s, m + n);
                        t++;
                    }
                }

                if (cameraX < selectionFlash.getBoundWest() + clampedViewDistance) {
                    float s = r;
                    for (double t = p; t < q; s+= 0.5F) {
                        double u = Math.min(1.0, q -t);
                        float v = (float) u * 0.5f;
                        bufferBuilder.vertex((float)(selectionFlash.getBoundWest() - cameraX), (float)(-planeDistance), (float)(t - cameraZ)).texture(m + s, m + o);
                        bufferBuilder.vertex((float)(selectionFlash.getBoundWest() - cameraX), (float)(-planeDistance), (float)(t + u - cameraZ)).texture(m + (v + s), m + o);
                        bufferBuilder.vertex((float)(selectionFlash.getBoundWest() - cameraX), (float)(planeDistance), (float)(t + u - cameraZ)).texture(m + (v + s), m + n);
                        bufferBuilder.vertex((float)(selectionFlash.getBoundWest() - cameraX), (float)(planeDistance), (float)(t - cameraZ)).texture(m + s, m + n);
                        t++;
                    }
                }

                p = Math.max(MathHelper.floor(cameraX - clampedViewDistance), selectionFlash.getBoundWest());
                q = Math.max(MathHelper.ceil(cameraX + clampedViewDistance), selectionFlash.getBoundEast());
                r = (MathHelper.floor(p) & 1) * 0.5f;
                if (cameraZ > selectionFlash.getBoundSouth() - clampedViewDistance) {
                    float s = r;
                    for (double t = p; t < q; s+= 0.5F) {
                        double u = Math.min(1.0, q -t);
                        float v = (float) u * 0.5f;
                        bufferBuilder.vertex((float)(t - cameraX), (float)(-planeDistance), (float)(selectionFlash.getBoundSouth() - cameraZ)).texture(m + s, m + o);
                        bufferBuilder.vertex((float)(t + u - cameraX), (float)(-planeDistance), (float)(selectionFlash.getBoundSouth() - cameraZ)).texture(m + (v + s), m + o);
                        bufferBuilder.vertex((float)(t + u - cameraX), (float)(planeDistance), (float)(selectionFlash.getBoundSouth() - cameraZ)).texture(m + (v + s), m + n);
                        bufferBuilder.vertex((float)(t - cameraX), (float)(planeDistance), (float)(selectionFlash.getBoundSouth() - cameraZ)).texture(m + s, m + n);
                        t++;
                    }
                }

                if (cameraZ < selectionFlash.getBoundNorth() + clampedViewDistance) {
                    float s = r;
                    for (double t = p; t < q; s+= 0.5F) {
                        double u = Math.min(1.0, q -t);
                        float v = (float) u * 0.5f;
                        bufferBuilder.vertex((float)(t - cameraX), (float)(-planeDistance), (float)(selectionFlash.getBoundNorth() - cameraZ)).texture(m - s, m + o);
                        bufferBuilder.vertex((float)(t + u - cameraX), (float)(-planeDistance), (float)(selectionFlash.getBoundNorth() - cameraZ)).texture(m - (v + s), m + o);
                        bufferBuilder.vertex((float)(t + u - cameraX), (float)(planeDistance), (float)(selectionFlash.getBoundNorth() - cameraZ)).texture(m - (v + s), m + n);
                        bufferBuilder.vertex((float)(t - cameraX), (float)(planeDistance), (float)(selectionFlash.getBoundNorth() - cameraZ)).texture(m - s, m + n);
                        t++;
                    }
                }

                BuiltBuffer builtBuffer = bufferBuilder.endNullable();
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
        });
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        if (owner == null) {
            tooltip.add(Text.literal("Owner not found").formatted(Formatting.DARK_GRAY));
        } else {
            tooltip.add(Text.literal("Owner: ").append(owner.getName()).formatted(Formatting.GRAY));
        }
        tooltip.add(Text.literal("Radius: " + this.domainRadius));
        super.appendTooltip(stack, context, tooltip, type);
        //todo check tooltip

    }

}