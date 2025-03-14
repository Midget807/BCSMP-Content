package com.bcsmp.bcsmp_content.mixin.domain_expansion;

import com.bcsmp.bcsmp_content.main.domain_expansion.worldgen.border.DomainBorder;
import com.bcsmp.bcsmp_content.main.domain_expansion.worldgen.border.WorldWithDomainBorder;
import com.bcsmp.bcsmp_content.main.domain_expansion.worldgen.dimension.DEModDimensions;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.MutableWorldProperties;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Supplier;

@Mixin(World.class)
public abstract class WorldMixin implements WorldAccess, AutoCloseable, WorldWithDomainBorder {
    @Shadow @Final private RegistryKey<World> registryKey;
    @Unique
    private DomainBorder domainBorder;

    @Override
    public DomainBorder getDomainBorder() {
        return domainBorder;
    }

    @Override
    public void setDomainBorder(DomainBorder domainBorder) {
        this.domainBorder = domainBorder;
    }
    @Deprecated
    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Ljava/lang/Thread;currentThread()Ljava/lang/Thread;"))
    private void domainExpansion$setDomainBorderWorld(MutableWorldProperties properties, RegistryKey registryRef, DynamicRegistryManager registryManager, RegistryEntry dimensionEntry, Supplier profiler, boolean isClient, boolean debugWorld, long biomeAccess, int maxChainedNeighborUpdates, CallbackInfo ci) {
        World world = (World) (Object) this;
        if (registryRef == DEModDimensions.DOMAIN_1_KEY) { // TODO: 8/03/2025 add other dim
            this.domainBorder = new DomainBorder();
            this.domainBorder.setWorld(world);
            //((WorldWithDomainBorder)world).getDomainBorder().setWorld(world);
        }
    }
}
