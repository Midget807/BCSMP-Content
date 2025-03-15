package com.bcsmp.bcsmp_content.mixin.domain_expansion.client;

import com.bcsmp.bcsmp_content.BCSMPContentMain;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.minecraft.world.border.WorldBorderStage;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(WorldRenderer.class)
public abstract class WorldRendererMixin {
    @Unique
    private static final Identifier DOMAIN_BORDER_TEXTURE = BCSMPContentMain.domainExpansionId("textures/entity/domain_border_base.png");
    @Shadow private @Nullable ClientWorld world;

    @WrapOperation(method = "renderWorldBorder", at = @At(value = "FIELD", target = "Lnet/minecraft/client/render/WorldRenderer;FORCEFIELD:Lnet/minecraft/util/Identifier;"))
    private Identifier domainExpansion$replaceTexture(Operation<Identifier> original) {
        if (this.world.getRegistryKey() == World.OVERWORLD) {// TODO: 15/03/2025 add for domains
            return DOMAIN_BORDER_TEXTURE;
        } else {
            return original.call();
        }
    }
    @WrapOperation(method = "renderWorldBorder", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/border/WorldBorderStage;getColor()I"))
    private int domainExpansion$replaceColor(WorldBorderStage instance, Operation<Integer> original) {
        if (this.world.getRegistryKey() == World.OVERWORLD) {// TODO: 15/03/2025 add for domains
            return 0xF1F152;
        } else {
            return original.call(instance);
        }
    }

}
