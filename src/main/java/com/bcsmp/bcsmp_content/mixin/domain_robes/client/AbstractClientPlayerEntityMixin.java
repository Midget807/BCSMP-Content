package com.bcsmp.bcsmp_content.mixin.domain_robes.client;

import com.bcsmp.bcsmp_content.main.domain_robes.item.custom.DomainRobesArmorItem;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(AbstractClientPlayerEntity.class)
public abstract class AbstractClientPlayerEntityMixin extends PlayerEntity {

    public AbstractClientPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile gameProfile) {
        super(world, pos, yaw, gameProfile);
    }

    @Override
    public boolean shouldRender(double cameraX, double cameraY, double cameraZ) {
        if (DomainRobesArmorItem.hasFullSuitOfArmor(this)
                && this.getStackInHand(Hand.MAIN_HAND).isEmpty()
                && this.getStackInHand(Hand.OFF_HAND).isEmpty()
                && this.isSneaking()
        ) {
            return false;
        }
        return super.shouldRender(cameraX, cameraY, cameraZ);
    }
}
