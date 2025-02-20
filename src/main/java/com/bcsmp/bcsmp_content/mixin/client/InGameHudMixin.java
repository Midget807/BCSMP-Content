package com.bcsmp.bcsmp_content.mixin.client;

import com.bcsmp.bcsmp_content.main.domain_expansion.effect.DEModEffects;
import com.bcsmp.bcsmp_content.main.domain_expansion.util.DEModOverlayIds;
import com.bcsmp.bcsmp_content.mixin.EntityMixin;
import com.bcsmp.bcsmp_content.mixin.PlayerEntityMixin;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.MathHelper;
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

    @Shadow
    protected abstract void renderOverlay(DrawContext context, Identifier texture, float opacity);

    @Shadow
    @Final
    private MinecraftClient client;

    @Shadow
    private int scaledWidth;

    @Shadow
    private int scaledHeight;

    @Shadow
    public abstract void render(DrawContext context, float tickDelta);

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;getFrozenTicks()I", shift = At.Shift.BEFORE))
    private void domainExpansion$renderDomainDeathOverlay(DrawContext context, float tickDelta, CallbackInfo ci) {
        if (this.client.player != null && this.client.player.hasStatusEffect(DEModEffects.DOMAIN_DEATH_EFFECT)) {
            this.renderOverlay(context, DEModOverlayIds.DOMAIN_DEATH_OVERLAY, 0.3f);
        }
        if (this.client.player != null) {
            if (this.client.player.getStatusEffect(DEModEffects.DOMAIN_TP_EFFECT) != null && this.client.player.hasStatusEffect(DEModEffects.DOMAIN_TP_EFFECT)) {
                this.domainTpEffectTicks = this.client.player.getStatusEffect(DEModEffects.DOMAIN_TP_EFFECT).getDuration();
                this.domainTpEffectTicks--;
                this.renderOverlay(context, DEModOverlayIds.DOMAIN_TP_OVERLAY, getDomainTpPercent((float) (this.domainTpEffectTicks / 50), this.client.player.getStatusEffect(DEModEffects.DOMAIN_TP_EFFECT).getDuration()));
            }
        }
    }

    @Unique
    private float getDomainTpPercent(float effectTicks, float effectDuration) {
        return MathHelper.clamp(1 - (effectTicks / effectDuration), 0, 1);
    }

}
