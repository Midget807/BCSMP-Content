package com.bcsmp.bcsmp_content.main.domain_expansion.datagen;

import com.bcsmp.bcsmp_content.main.domain_expansion.block.DEModBlocks;
import com.bcsmp.bcsmp_content.main.domain_expansion.item.DEModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

public class DEModRecipeProvider extends FabricRecipeProvider {
    public DEModRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, DEModBlocks.DOMAIN_PILLAR, 1)
                .pattern("SDS")
                .pattern("SDS")
                .pattern("SXS")
                .input('S', Items.STONE)
                .input('D', Items.DIAMOND)
                .input('X', Items.SOUL_SAND)
                .criterion(hasItem(Items.STONE), conditionsFromItem(Items.STONE))
                .criterion(hasItem(Items.DIAMOND), conditionsFromItem(Items.DIAMOND))
                .criterion(hasItem(Items.SOUL_SAND), conditionsFromItem(Items.SOUL_SAND))
                .offerTo(exporter, new Identifier(getRecipeName(DEModBlocks.DOMAIN_PILLAR)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, DEModItems.DOMAIN_EXPANDER, 1)
                .pattern("E")
                .pattern("D")
                .pattern("E")
                .input('E', Items.ENDER_EYE)
                .input('D', Items.DIAMOND)
                .criterion(hasItem(Items.ENDER_EYE), conditionsFromItem(Items.ENDER_EYE))
                .criterion(hasItem(Items.DIAMOND), conditionsFromItem(Items.DIAMOND))
                .offerTo(exporter, new Identifier(getRecipeName(DEModItems.DOMAIN_EXPANDER)));


    }
}
