package com.bcsmp.bcsmp_content.mixin.charter_fix;

import com.bcsmp.bcsmp_content.main.charter_fix.effect.CFModEffects;
import com.bcsmp.bcsmp_content.main.charter_fix.entity.CharterAttacker;
import com.bcsmp.bcsmp_content.main.domain_expansion.effect.DEModEffects;
import com.bcsmp.bcsmp_content.main.domain_expansion.worldgen.dimension.DEModDimensions;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

import java.util.List;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {

    public PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public boolean isDead() {// TODO: 14/03/2025 edit for charter effect and attacker conditions 
        if (this.getHealth() <= 0.0f) {
            if (this.getAttacker() != null && ((CharterAttacker)this.getAttacker()).usingCharter()) {
                this.dead = false;
                this.setHealth(20.0f);
                this.addStatusEffect(new StatusEffectInstance(CFModEffects.CHAINED, 60, 0, true, false));
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }
}
