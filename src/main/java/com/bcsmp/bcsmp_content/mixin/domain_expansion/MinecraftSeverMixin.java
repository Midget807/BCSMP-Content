package com.bcsmp.bcsmp_content.mixin.domain_expansion;

import com.bcsmp.bcsmp_content.main.domain_expansion.util.DomainBorderState;
import com.bcsmp.bcsmp_content.main.domain_expansion.worldgen.border.DomainBorder;
import com.bcsmp.bcsmp_content.main.domain_expansion.worldgen.border.DomainBorderListener;
import com.bcsmp.bcsmp_content.main.domain_expansion.worldgen.border.ServerWorldDomainProperties;
import com.bcsmp.bcsmp_content.main.domain_expansion.worldgen.border.WorldWithDomainBorder;
import com.bcsmp.bcsmp_content.main.domain_expansion.worldgen.dimension.DEModDimensions;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.WorldGenerationProgressListener;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.SaveProperties;
import net.minecraft.world.World;
import net.minecraft.world.level.ServerWorldProperties;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(MinecraftServer.class)
public abstract class MinecraftSeverMixin {
    @Shadow @Final private Map<RegistryKey<World>, ServerWorld> worlds;
    @Deprecated
    @Inject(method = "createWorlds", at = @At(value = "TAIL"))
    public void domainExpansion$loadDomainBorder(WorldGenerationProgressListener worldGenerationProgressListener, CallbackInfo ci) {
        worlds.forEach((worldRegistryKey, world) -> {
            if (worldRegistryKey == DEModDimensions.DOMAIN_1_LEVEL_KEY
                    || worldRegistryKey == DEModDimensions.DOMAIN_2_LEVEL_KEY
                    || worldRegistryKey == DEModDimensions.DOMAIN_3_LEVEL_KEY
                    || worldRegistryKey == DEModDimensions.DOMAIN_4_LEVEL_KEY
            ) {
                DomainBorder domainBorder = ((WorldWithDomainBorder)world).getDomainBorder();
                DomainBorderState domainBorderState = world.getPersistentStateManager().getOrCreate(DomainBorderState::createFromNbt, DomainBorderState::createNew, "domain_border");
                if (domainBorder != null) {
                    domainBorder.setCenter(domainBorderState.getCenterX(), domainBorderState.getCenterZ());
                    domainBorder.setSize(domainBorder.getSize());
                    domainBorder.setSafeZone(domainBorderState.getSafeZone());
                    domainBorder.setDamagePerBlock(domainBorderState.getDamagePerBlock());
                    domainBorder.setWarningBlocks(domainBorder.getWarningBlocks());
                    domainBorder.setWarningTime(domainBorder.getWarningTime());

                    //domainBorder.addListener(new DomainBorderListener.DomainBorderSyncer(domainBorder));
                    domainBorder.load(domainBorder.write());
                }
            }
        });
    }

}
