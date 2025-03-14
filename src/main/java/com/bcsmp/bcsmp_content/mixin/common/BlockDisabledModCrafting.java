package com.bcsmp.bcsmp_content.mixin.common;

import com.bcsmp.bcsmp_content.main.domain_expansion.util.SubModState;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.CraftingScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CraftingScreenHandler.class)
public abstract class BlockDisabledModCrafting {
    @Inject(method = "updateResult", at = @At(value = "INVOKE", target = "Lnet/minecraft/inventory/CraftingResultInventory;setStack(ILnet/minecraft/item/ItemStack;)V"), cancellable = true)
    private static void bcsmp$blockDomainExpansionCrafting(ScreenHandler handler, World world, PlayerEntity player, RecipeInputInventory craftingInventory, CraftingResultInventory resultInventory, CallbackInfo ci, @Local ItemStack itemStack) {
        SubModState state = SubModState.getServerState(player.getServer());
        if (!state.getDomainExpansionModEnabled()) {
            if (itemStack.getItem().getName().contains(Text.literal("domain_expansion"))) {
                handler.setPreviousTrackedSlot(0, ItemStack.EMPTY);
                ci.cancel();
            }
        }

    }
}
