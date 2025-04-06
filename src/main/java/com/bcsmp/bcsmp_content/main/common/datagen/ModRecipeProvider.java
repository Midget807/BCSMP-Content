package com.bcsmp.bcsmp_content.main.common.datagen;

import com.bcsmp.bcsmp_content.main.domain_expansion.block.DEModBlocks;
import com.bcsmp.bcsmp_content.main.domain_expansion.item.DEModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryFuture) {
        super(output, registryFuture);
    }

    @Override
    public void generate(RecipeExporter exporter) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, DEModBlocks.DOMAIN_PILLAR, 1)
                .pattern("SDS")
                .pattern("SDS")
                .pattern("SXS")
                .input('S', Items.STONE)
                .input('D', Items.DIAMOND)
                .input('X', ModItemTagProvider.PILLAR_SOUL_BLOCKS)
                .criterion(hasItem(Items.STONE), conditionsFromItem(Items.STONE))
                .criterion(hasItem(Items.GOLD_INGOT), conditionsFromItem(Items.GOLD_INGOT))
                .criterion(hasItem(Items.SOUL_SAND), conditionsFromItem(Items.SOUL_SAND))
                .criterion(hasItem(Items.SOUL_SOIL), conditionsFromItem(Items.SOUL_SOIL))
                .offerTo(exporter, Identifier.of(getRecipeName(DEModBlocks.DOMAIN_PILLAR)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, DEModItems.DOMAIN_EXPANDER, 1)
                .pattern("E")
                .pattern("D")
                .pattern("E")
                .input('E', Items.ENDER_EYE)
                .input('D', Items.DIAMOND)
                .criterion(hasItem(Items.ENDER_EYE), conditionsFromItem(Items.ENDER_EYE))
                .criterion(hasItem(Items.DIAMOND), conditionsFromItem(Items.DIAMOND))
                .offerTo(exporter, Identifier.of(getRecipeName(DEModItems.DOMAIN_EXPANDER)));


    }
}
