package com.bcsmp.bcsmp_content.mixin.charter_fix;

import com.bcsmp.bcsmp_content.main.charter_fix.entity.CharterAttacker;
import net.minecraft.entity.Attackable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements Attackable, CharterAttacker {
    private boolean usingCharter = false;
    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Override
    public boolean usingCharter() {
        return this.usingCharter;
    }

    @Override
    public void setUsingCharter(boolean bl) {
        this.usingCharter = bl;
    }
}
