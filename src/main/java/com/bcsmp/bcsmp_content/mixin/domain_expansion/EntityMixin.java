package com.bcsmp.bcsmp_content.mixin.domain_expansion;

import com.llamalad7.mixinextras.sugar.Local;
import net.fabricmc.fabric.api.attachment.v1.AttachmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.command.CommandOutput;
import net.minecraft.util.Nameable;
import net.minecraft.world.World;
import net.minecraft.world.entity.EntityLike;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin implements Nameable, EntityLike, CommandOutput, AttachmentTarget {
    @Unique
    private static final String TP_TICKS_KEY = "DomainTpEffectTicks";
    @Shadow @Final protected DataTracker dataTracker;
    private static final TrackedData<Integer> DOMAIN_TP_EFFECT_TICKS = DataTracker.registerData(Entity.class, TrackedDataHandlerRegistry.INTEGER);
    @Inject(method = "<init>", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;FROZEN_TICKS:Lnet/minecraft/entity/data/TrackedData;"))
    public void domainExpansion$addCustomData(EntityType<?> type, World world, CallbackInfo ci, @Local DataTracker.Builder builder) {
        builder.add(DOMAIN_TP_EFFECT_TICKS, 0);
    }
    @Unique
    public int getDomainTpEffectTicks() {
        return this.dataTracker.get(DOMAIN_TP_EFFECT_TICKS);
    }
    @Unique
    public void setDomainTpEffectTicks(int tpEffectTicks) {
        this.dataTracker.set(DOMAIN_TP_EFFECT_TICKS, tpEffectTicks);
    }

    @Inject(method = "writeNbt", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;getFrozenTicks()I", shift = At.Shift.BEFORE))
    public void domainExpansion$writeCustomData(NbtCompound nbt, CallbackInfoReturnable<NbtCompound> cir) {
        if (this.getDomainTpEffectTicks() > 0) {
            nbt.putInt(TP_TICKS_KEY, this.getDomainTpEffectTicks());
        }
    }
    @Inject(method = "readNbt", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;setFrozenTicks(I)V"))
    public void domainExpansion$writeCustomData(NbtCompound nbt, CallbackInfo ci) {
        this.setDomainTpEffectTicks(nbt.getInt(TP_TICKS_KEY));
    }
}
