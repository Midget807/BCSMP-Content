package com.bcsmp.bcsmp_content.mixin.common;

import com.bcsmp.bcsmp_content.main.common.util.CommonModUtil;
import com.bcsmp.bcsmp_content.main.domain_expansion.util.DEModUtil;
import com.bcsmp.bcsmp_content.main.common.util.SubModState;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.CraftingRecipe;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.screen.AbstractRecipeScreenHandler;
import net.minecraft.screen.CraftingScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CraftingScreenHandler.class)
public abstract class BlockDisabledModCrafting extends AbstractRecipeScreenHandler<CraftingRecipeInput, CraftingRecipe> {
    public BlockDisabledModCrafting(ScreenHandlerType<?> screenHandlerType, int i) {
        super(screenHandlerType, i);
    }


    @Inject(method = "updateResult", at = @At(value = "INVOKE", target = "Lnet/minecraft/inventory/CraftingResultInventory;setStack(ILnet/minecraft/item/ItemStack;)V"), cancellable = true)
    private static void bcsmp$blockCrafting(ScreenHandler handler, World world, PlayerEntity player, RecipeInputInventory craftingInventory, CraftingResultInventory resultInventory, @Nullable RecipeEntry<CraftingRecipe> recipe, CallbackInfo ci, @Local ItemStack itemStack) {
        SubModState state = SubModState.getServerState(player.getServer());
        if (!state.getDomainExpansionModEnabled()) {
            for (Item item : CommonModUtil.getDomainExpansionItems()) {
                if (itemStack.getItem() == item) {
                    handler.setPreviousTrackedSlot(0, ItemStack.EMPTY);
                    ci.cancel();
                }
            }
        }
        if (!state.getDomainRobesModEnabled()) {
            for (Item item : CommonModUtil.getDomainRobesItems()) {
                if (itemStack.getItem() == item) {
                    handler.setPreviousTrackedSlot(0, ItemStack.EMPTY);
                    ci.cancel();
                }
            }
        }
        if (!state.getArcanusClothesModEnabled()) {
            for (Item item : CommonModUtil.getArcanusClothesItems()) {
                if (itemStack.getItem() == item) {
                    handler.setPreviousTrackedSlot(0, ItemStack.EMPTY);
                    ci.cancel();
                }
            }
        }
    }
}
