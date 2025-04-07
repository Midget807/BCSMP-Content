package com.bcsmp.bcsmp_content.mixin.domain_robes;

import com.bcsmp.bcsmp_content.main.common.datagen.ModBlockTagProvider;
import com.bcsmp.bcsmp_content.main.common.util.SubModState;
import com.bcsmp.bcsmp_content.main.domain_robes.item.DRModItems;
import com.bcsmp.bcsmp_content.main.domain_robes.item.custom.SpyglassSearch;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpyglassItem;
import net.minecraft.particle.ParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SpyglassItem.class)
public abstract class SpyglassItemMixin extends Item implements SpyglassSearch {
    @Unique
    private int searchTicks = 0;
    public SpyglassItemMixin(Settings settings) {
        super(settings);
    }

    @Override
    public int getSearchTicks() {
        return this.searchTicks;
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {

            PlayerEntity player = (PlayerEntity) user;
            if ((player.getAbilities().creativeMode || player.totalExperience >= 50) && player.getStackInHand(Hand.OFF_HAND).isOf(DRModItems.INTERDIMENSIONAL_LENS) && player.isSneaking()) {
                if (!world.isClient) {
                    HitResult hitResult = user.raycast(4.0, 0.0f, false);
                    if (hitResult.getType() == HitResult.Type.BLOCK) {
                        BlockHitResult blockHitResult = (BlockHitResult) hitResult;
                        if (world.getBlockState(blockHitResult.getBlockPos()).isIn(ModBlockTagProvider.DIMENSIONAL_BLOCKS)) {
                            this.searchTicks++;
                            ((ServerWorld) world).spawnParticles(ParticleTypes.REVERSE_PORTAL, player.getX(), player.getY(), player.getZ(), 10, 0.1, 0.1, 0.1, 0.1);
                            if (this.searchTicks >= 5 * 20) {
                                player.giveItemStack(new ItemStack(DRModItems.DIMENSIONAL_STRING));
                                player.addExperience(-50);
                                searchTicks = 0;
                            }
                        }
                    } else {
                        this.searchTicks = 0;
                    }
                }

        }
    }
    @Inject(method = "onStoppedUsing", at = @At("HEAD"))
    private void domainRobes$cancelSearch(ItemStack stack, World world, LivingEntity user, int remainingUseTicks, CallbackInfo ci) {
        this.searchTicks = 0;
    }
}
