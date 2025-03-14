package com.bcsmp.bcsmp_content.mixin.domain_expansion;

import com.bcsmp.bcsmp_content.main.domain_expansion.util.DomainBorderState;
import com.bcsmp.bcsmp_content.main.domain_expansion.worldgen.border.WorldWithDomainBorder;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ProgressListener;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerWorld.class)
public abstract class ServerWorldMixin {
    @Shadow @Final private MinecraftServer server;

    @Deprecated
    @Inject(method = "save", at = @At("HEAD"))
    public void domainExpansion$saveDomainBorder(ProgressListener progressListener, boolean flush, boolean savingDisabled, CallbackInfo ci) {
        ServerWorld serverWorld = (ServerWorld) (Object) this;
        DomainBorderState domainBorderState = DomainBorderState.getServerState(server);
        if (((WorldWithDomainBorder)serverWorld).getDomainBorder() != null) {
            domainBorderState.fromDomainBorder(((WorldWithDomainBorder) serverWorld).getDomainBorder());
        }
    }
}
