package com.bcsmp.bcsmp_content.main.domain_expansion.item.custom;

import com.bcsmp.bcsmp_content.main.domain_expansion.component.DEModDataComponentTypes;
import com.bcsmp.bcsmp_content.main.domain_expansion.config.DEModMidnightConfig;
import com.bcsmp.bcsmp_content.main.domain_expansion.effect.DEModEffects;
import com.bcsmp.bcsmp_content.main.domain_expansion.util.DEModUtil;
import com.bcsmp.bcsmp_content.main.domain_expansion.util.DomainAvailabilityState;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class DomainCompressorItem extends Item {
    public static final String CASTER_POS_KEY = "CasterOriginPos";
    private boolean shouldTick = false;

    public DomainCompressorItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack handStack = user.getStackInHand(hand);
        BlockPos originPosComponent = handStack.get(DEModDataComponentTypes.COMPRESSOR_ORIGIN_POS);
        if (DEModUtil.getDomainKeys().contains(world.getRegistryKey())) {
            if (user.isSneaking()) {
                if (originPosComponent == null) {
                    return TypedActionResult.pass(handStack);
                } else {
                    if (!world.isClient) {
                        MinecraftServer server = user.getServer();
                        if (user instanceof ServerPlayerEntity && server != null) {
                            ServerWorld overworld = server.getWorld(World.OVERWORLD);
                            if (overworld != null) {
                                ServerWorld serverWorld = ((ServerPlayerEntity) user).getServerWorld();
                                for (ServerPlayerEntity player : serverWorld.getPlayers()) {
                                    player.addStatusEffect(new StatusEffectInstance(DEModEffects.DOMAIN_TP_EFFECT, DEModMidnightConfig.domainTpEffectFade, 0, false, false));
                                    this.shouldTick = true;
                                }
                                for (ServerPlayerEntity player : serverWorld.getPlayers()) {
                                    if (this.shouldTick && player != null) {
                                        ServerTickEvents.START_SERVER_TICK.register(server1 -> {
                                            if (player.getStatusEffect(DEModEffects.DOMAIN_TP_EFFECT) != null && player.getStatusEffect(DEModEffects.DOMAIN_TP_EFFECT).getDuration() > 0) {
                                                if (player.getStatusEffect(DEModEffects.DOMAIN_TP_EFFECT).getDuration() == 1) {
                                                    player.teleport(overworld, originPosComponent.getX(), originPosComponent.getY(), originPosComponent.getZ(), player.getBodyYaw(), player.prevPitch);
                                                }
                                            } else {
                                                this.shouldTick = false;
                                            }
                                        });
                                        user.setStackInHand(hand, ItemStack.EMPTY);
                                    }
                                }
                                DomainAvailabilityState.setDomainAvailable(serverWorld.getRegistryKey(), server);
                            }
                        }
                    }
                }
            }
        } else {
            user.sendMessage(Text.literal("Must be in a domain to use").formatted(Formatting.RED), true);
        }
        return TypedActionResult.consume(handStack);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType options) {
        BlockPos originPosComponent = stack.get(DEModDataComponentTypes.COMPRESSOR_ORIGIN_POS);
        if (originPosComponent != null) {
            tooltip.add(Text.literal("Return position: " + originPosComponent));
        } else {
            tooltip.add(Text.literal("Return position not available").formatted(Formatting.DARK_GRAY));
        }
        super.appendTooltip(stack, context, tooltip, options);
    }
}
