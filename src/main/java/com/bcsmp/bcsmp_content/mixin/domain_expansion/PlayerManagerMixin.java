package com.bcsmp.bcsmp_content.mixin.domain_expansion;

import com.bcsmp.bcsmp_content.main.domain_expansion.network.packet.S2C.DomainBorderInitializePacket;
import com.bcsmp.bcsmp_content.main.domain_expansion.worldgen.border.DomainBorder;
import com.bcsmp.bcsmp_content.main.domain_expansion.worldgen.border.DomainBorderListener;
import com.bcsmp.bcsmp_content.main.domain_expansion.worldgen.border.DomainBorderWithWorld;
import com.bcsmp.bcsmp_content.main.domain_expansion.worldgen.border.WorldWithDomainBorder;
import com.bcsmp.bcsmp_content.main.domain_expansion.worldgen.dimension.DEModDimensions;
import net.minecraft.network.packet.s2c.play.WorldBorderSizeChangedS2CPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerManager.class)
public abstract class PlayerManagerMixin {
    @Shadow @Final private MinecraftServer server;

    @Deprecated
    @Inject(method = "sendWorldInfo", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/ServerWorld;getWorldBorder()Lnet/minecraft/world/border/WorldBorder;"))
    public void domainExpansion$sendDomainBorderInitPacket(ServerPlayerEntity player, ServerWorld world, CallbackInfo ci) {
        ServerWorld serverWorld = this.server.getWorld(DEModDimensions.DOMAIN_1_LEVEL_KEY);
        if (serverWorld != null) {
            DomainBorder domainBorder = ((WorldWithDomainBorder) serverWorld).getDomainBorder();
            //player.networkHandler.sendPacket(new DomainBorderInitializePacket(domainBorder));

        }
    }
    @Deprecated
    @Inject(method = "setMainWorld", at = @At("HEAD"))
    public void domainExpansion$addListener(ServerWorld world, CallbackInfo ci) {
        DomainBorder domainBorder = ((WorldWithDomainBorder) world).getDomainBorder();
        if (domainBorder != null) {
            ((WorldWithDomainBorder) world).getDomainBorder().addListener(new DomainBorderListener.DomainBorderSyncer(domainBorder));
        }
    }
}
