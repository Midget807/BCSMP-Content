package com.bcsmp.bcsmp_content.mixin.domain_expansion;

import com.bcsmp.bcsmp_content.main.domain_expansion.effect.DEModEffects;
import com.bcsmp.bcsmp_content.main.domain_expansion.worldgen.dimension.DEModDimensions;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
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

    @Override
    public boolean isDead() {
        if (this.getHealth() <= 0.0f) {
            if ((this.getWorld().getRegistryKey() == DEModDimensions.DOMAIN_1_LEVEL_KEY ||
                    this.getWorld().getRegistryKey() == DEModDimensions.DOMAIN_2_LEVEL_KEY ||
                    this.getWorld().getRegistryKey() == DEModDimensions.DOMAIN_3_LEVEL_KEY ||
                    this.getWorld().getRegistryKey() == DEModDimensions.DOMAIN_4_LEVEL_KEY)
            ) {
                this.dead = false;
                this.setHealth(20.0f);
                this.addStatusEffect(new StatusEffectInstance(DEModEffects.DOMAIN_DEATH_EFFECT, 5 * 20, 0, false, false));
                MinecraftClient client = MinecraftClient.getInstance();
                client.particleManager.addEmitter(this, ParticleTypes.TOTEM_OF_UNDYING, 30);
                List<LivingEntity> entities = this.getWorld().getEntitiesByClass(LivingEntity.class, this.getBoundingBox().expand(7, 7, 7), EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR);
                for (LivingEntity entity : entities) {
                    entity.takeKnockback(2.0, this.getX() - entity.getX(), this.getZ() - entity.getZ());
                }
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }
}
