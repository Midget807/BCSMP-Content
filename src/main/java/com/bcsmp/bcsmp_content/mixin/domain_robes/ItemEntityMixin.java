package com.bcsmp.bcsmp_content.mixin.domain_robes;

import com.bcsmp.bcsmp_content.main.domain_robes.item.DRModItems;
import com.bcsmp.bcsmp_content.main.domain_robes.item.WeavingRecipe;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.Ownable;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin extends Entity implements Ownable, WeavingRecipe {
    @Shadow public abstract ItemStack getStack();

    @Unique
    private int weavingTicks = 0;

    @Unique
    private boolean shouldParticle = this.getStack().getCount() >= 4;

    public ItemEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Override
    public int getWeavingTicks() {
        return this.weavingTicks;
    }

    @Override
    public boolean shouldParticle() {
        return this.shouldParticle;
    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;tick()V"))
    private void domainRobes$processDimensionalString(CallbackInfo ci) {
        if (this.getWorld().getBlockState(this.getBlockPos().down(1)).isOf(Blocks.LOOM)) {
            if (this.weavingTicks >= 100) {
                int stackSize = this.getStack().getCount();
                int clothStackSize = MathHelper.floor((float) stackSize / 4);
                this.getStack().setCount(stackSize - clothStackSize * 4);
                if (!this.getWorld().isClient) {
                    ItemEntity clothStackEntity = new ItemEntity(this.getWorld(), this.getX(), this.getY(), this.getZ(), new ItemStack(DRModItems.DIMENSIONAL_CLOTH, clothStackSize));
                    this.getWorld().spawnEntity(clothStackEntity);
                }
                this.weavingTicks = 0;
                this.shouldParticle = false;
            }
            if (!this.getWorld().isClient) {
                this.weavingTicks++;
            } else {
                if (this.shouldParticle || this.getStack().getCount() >= 4) {
                    for (int i = 0; i < 2; i++) {
                        this.getWorld().addParticle(
                                ParticleTypes.CRIT,
                                this.getX(),
                                this.getY(),
                                this.getZ(),
                                this.getWorld().getRandom().nextBoolean() ? this.getWorld().getRandom().nextFloat() * 0.75 : -(this.getWorld().getRandom().nextFloat() * 0.75),
                                0.2,
                                this.getWorld().getRandom().nextBoolean() ? this.getWorld().getRandom().nextFloat() * 0.75 : -(this.getWorld().getRandom().nextFloat() * 0.75)
                        );
                    }
                }
            }
        }
    }
}
