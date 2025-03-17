package com.bcsmp.bcsmp_content.mixin.domain_expansion.client;

import com.bcsmp.bcsmp_content.main.domain_expansion.config.DEModMidnightConfig;
import com.bcsmp.bcsmp_content.main.domain_expansion.effect.DEModEffects;
import com.bcsmp.bcsmp_content.main.domain_expansion.util.DEModOverlayIds;
import com.bcsmp.bcsmp_content.main.domain_expansion.worldgen.dimension.DEModDimensions;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {
    private int domainTpEffectTicks = -10;
    private int domainTpEffectMaxDuration = 1;
    private boolean shouldTick = false;

    protected InGameHudMixin(int scaledWidth) {
    }

    @Shadow
    protected abstract void renderOverlay(DrawContext context, Identifier texture, float opacity);

    @Shadow
    @Final
    private MinecraftClient client;

    @Inject(method = "renderMiscOverlays", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;getFrozenTicks()I", shift = At.Shift.BEFORE))
    private void domainExpansion$renderDomainDeathOverlay(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        if (this.client.player != null && this.client.player.hasStatusEffect(DEModEffects.DOMAIN_DEATH_EFFECT)) {
            this.renderOverlay(context, DEModOverlayIds.DOMAIN_DEATH_OVERLAY, 0.3f);
        }
        if (this.client.player != null) {
            if (this.client.player.getStatusEffect(DEModEffects.DOMAIN_TP_EFFECT) != null && this.client.player.hasStatusEffect(DEModEffects.DOMAIN_TP_EFFECT)) {
                this.domainTpEffectMaxDuration = DEModMidnightConfig.domainTpEffectFade;
                this.domainTpEffectTicks = this.domainTpEffectMaxDuration;
                this.shouldTick = true;
                if (shouldTick) {
                    ClientTickEvents.START_CLIENT_TICK.register(client1 -> {
                        this.domainTpEffectTicks--;
                        if (this.domainTpEffectTicks < 0) {
                            this.shouldTick = false;
                        }
                    });
                    if (this.domainTpEffectTicks < 0) {
                        this.shouldTick = false;
                    }
                }
                this.renderOverlay(context, DEModOverlayIds.DOMAIN_TP_OVERLAY, getDomainTpPercent((float) (this.domainTpEffectTicks / 20), this.client.player.getStatusEffect(DEModEffects.DOMAIN_TP_EFFECT).getDuration()));
            } else {
                this.shouldTick = false;
            }
        }

    }

    @Unique
    private float getDomainTpPercent(float effectTicks, float effectDuration) {
        return MathHelper.clamp((effectTicks / effectDuration), 0, 1);
    }
    @Inject(method = "renderVignetteOverlay", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/world/ClientWorld;getWorldBorder()Lnet/minecraft/world/border/WorldBorder;"), cancellable = true)
    private void domainExpansion$noDomainBorderVignette(DrawContext context, Entity entity, CallbackInfo ci) {
        World world = this.client.player.getWorld();
        if (world.getRegistryKey() == DEModDimensions.DOMAIN_1_LEVEL_KEY
                || world.getRegistryKey() == DEModDimensions.DOMAIN_2_LEVEL_KEY
                || world.getRegistryKey() == DEModDimensions.DOMAIN_3_LEVEL_KEY
                || world.getRegistryKey() == DEModDimensions.DOMAIN_4_LEVEL_KEY
        ) {
            ci.cancel();
        }
    }

}
