package com.bcsmp.bcsmp_content.mixin.domain_robes.client;

import com.bcsmp.bcsmp_content.main.domain_robes.item.DRModItems;
import com.llamalad7.mixinextras.sugar.Local;
import net.fabricmc.fabric.impl.client.rendering.ColorProviderRegistryImpl;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.component.type.DyedColorComponent;
import net.minecraft.util.math.ColorHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemColors.class)
public abstract class ItemColorsMixin {
    @Inject(method = "create", at = @At("TAIL"))
    private static void domainRobes$addArmorColors(BlockColors blockColors, CallbackInfoReturnable<ItemColors> cir, @Local ItemColors itemColors) {
        itemColors.register(
                (stack, tintIndex) -> tintIndex > 0 ? -1 : DyedColorComponent.getColor(stack, -6265536),
                DRModItems.HOOD,
                DRModItems.ROBE,
                DRModItems.PANTS,
                DRModItems.BOOTS
        );
    }
}
