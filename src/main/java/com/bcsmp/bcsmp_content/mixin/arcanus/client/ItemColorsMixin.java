package com.bcsmp.bcsmp_content.mixin.arcanus.client;

import com.bcsmp.bcsmp_content.main.arcanus_clothes.item.ACModItems;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.component.type.DyedColorComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemColors.class)
public abstract class ItemColorsMixin {
    @Inject(method = "create", at = @At("TAIL"))
    private static void arcanusClothes$addArmorColors(BlockColors blockColors, CallbackInfoReturnable<ItemColors> cir, @Local ItemColors itemColors) {
        itemColors.register(
                (stack, tintIndex) -> tintIndex > 0 ? -1 : DyedColorComponent.getColor(stack, -6265536),
                ACModItems.HAT,
                ACModItems.ROBES,
                ACModItems.PANTS,
                ACModItems.BOOTS
        );
    }
}
