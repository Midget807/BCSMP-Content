package com.bcsmp.bcsmp_content.mixin;

import com.bcsmp.bcsmp_content.main.domain_expansion.effect.DEModEffects;
import com.bcsmp.bcsmp_content.main.domain_expansion.worldgen.dimension.DEModDimensions;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    public PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }
    @Inject(method = "onDeath", at = @At("HEAD"), cancellable = true)
    private void domainExpansion$noDeathInDim(DamageSource damageSource, CallbackInfo ci) {
        if (this.getWorld().getRegistryKey() == DEModDimensions.DOMAIN_1_LEVEL_KEY ||
                this.getWorld().getRegistryKey() == DEModDimensions.DOMAIN_2_LEVEL_KEY ||
                this.getWorld().getRegistryKey() == DEModDimensions.DOMAIN_3_LEVEL_KEY ||
                this.getWorld().getRegistryKey() == DEModDimensions.DOMAIN_4_LEVEL_KEY
        ) {
            this.dead = false;
            this.setHealth(20.0f);
            this.addStatusEffect(new StatusEffectInstance(DEModEffects.DOMAIN_DEATH_EFFECT, 5, 0));
            List<LivingEntity> entities = this.getWorld().getEntitiesByClass(LivingEntity.class, this.getBoundingBox().expand(7, 7, 7), EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR);
            for (LivingEntity entity : entities) {
                entity.takeKnockback(4.0, entity.getX() - this.getX(), entity.getZ() - this.getZ());
            }
            ci.cancel();
        } else {
            super.onDeath(damageSource);
        }
    }
    /*
    @Override
    public void onDeath(DamageSource damageSource) {

    }*/
}
