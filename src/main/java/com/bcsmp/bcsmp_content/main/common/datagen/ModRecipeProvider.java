package com.bcsmp.bcsmp_content.main.common.datagen;

import com.bcsmp.bcsmp_content.BCSMPContentMain;
import com.bcsmp.bcsmp_content.main.arcanus_clothes.item.ACModItems;
import com.bcsmp.bcsmp_content.main.domain_expansion.block.DEModBlocks;
import com.bcsmp.bcsmp_content.main.domain_expansion.item.DEModItems;
import com.bcsmp.bcsmp_content.main.domain_robes.item.DRModItems;
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

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, DRModItems.INTERDIMENSIONAL_LENS, 1)
                .pattern("ANA")
                .pattern("NEN")
                .pattern("ANA")
                .input('A', Items.AMETHYST_SHARD)
                .input('N', Items.NETHERITE_INGOT)
                .input('E', Items.ENDER_EYE)
                .criterion(hasItem(Items.AMETHYST_SHARD), conditionsFromItem(Items.AMETHYST_SHARD))
                .criterion(hasItem(Items.NETHERITE_INGOT), conditionsFromItem(Items.NETHERITE_INGOT))
                .criterion(hasItem(Items.ENDER_EYE), conditionsFromItem(Items.ENDER_EYE))
                .offerTo(exporter, Identifier.of(getRecipeName(DRModItems.INTERDIMENSIONAL_LENS)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, DRModItems.HOOD, 1)
                .pattern("CCC")
                .pattern("C C")
                .input('C', DRModItems.DIMENSIONAL_CLOTH)
                .criterion(hasItem(DRModItems.DIMENSIONAL_CLOTH), conditionsFromItem(DRModItems.DIMENSIONAL_CLOTH))
                .offerTo(exporter, Identifier.of(getRecipeName(DRModItems.HOOD)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, DRModItems.ROBE, 1)
                .pattern("C C")
                .pattern("CCC")
                .pattern("CCC")
                .input('C', DRModItems.DIMENSIONAL_CLOTH)
                .criterion(hasItem(DRModItems.DIMENSIONAL_CLOTH), conditionsFromItem(DRModItems.DIMENSIONAL_CLOTH))
                .offerTo(exporter, Identifier.of(getRecipeName(DRModItems.ROBE)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, DRModItems.PANTS, 1)
                .pattern("CCC")
                .pattern("C C")
                .pattern("C C")
                .input('C', DRModItems.DIMENSIONAL_CLOTH)
                .criterion(hasItem(DRModItems.DIMENSIONAL_CLOTH), conditionsFromItem(DRModItems.DIMENSIONAL_CLOTH))
                .offerTo(exporter, Identifier.of(getRecipeName(DRModItems.PANTS)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, DRModItems.BOOTS, 1)
                .pattern("C C")
                .pattern("C C")
                .input('C', DRModItems.DIMENSIONAL_CLOTH)
                .criterion(hasItem(DRModItems.DIMENSIONAL_CLOTH), conditionsFromItem(DRModItems.DIMENSIONAL_CLOTH))
                .offerTo(exporter, Identifier.of(getRecipeName(DRModItems.BOOTS)));


        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ACModItems.HAT, 1)
                .pattern("LL ")
                .pattern("AXA")
                .input('X', Items.LEATHER_HELMET)
                .input('L', Items.LEATHER)
                .input('A', Items.AMETHYST_SHARD)
                .criterion(hasItem(Items.LEATHER_HELMET), conditionsFromItem(Items.LEATHER_HELMET))
                .criterion(hasItem(Items.LEATHER), conditionsFromItem(Items.LEATHER))
                .criterion(hasItem(Items.AMETHYST_SHARD), conditionsFromItem(Items.AMETHYST_SHARD))
                .offerTo(exporter, Identifier.of(getRecipeName(ACModItems.HAT)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ACModItems.ROBES, 1)
                .pattern("LXL")
                .pattern("ALA")
                .input('X', Items.LEATHER_CHESTPLATE)
                .input('L', Items.LEATHER)
                .input('A', Items.AMETHYST_SHARD)
                .criterion(hasItem(Items.LEATHER_CHESTPLATE), conditionsFromItem(Items.LEATHER_CHESTPLATE))
                .criterion(hasItem(Items.LEATHER), conditionsFromItem(Items.LEATHER))
                .criterion(hasItem(Items.AMETHYST_SHARD), conditionsFromItem(Items.AMETHYST_SHARD))
                .offerTo(exporter, Identifier.of(getRecipeName(ACModItems.ROBES)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ACModItems.PANTS, 1)
                .pattern("LXL")
                .pattern("A A")
                .input('X', Items.LEATHER_LEGGINGS)
                .input('L', Items.LEATHER)
                .input('A', Items.AMETHYST_SHARD)
                .criterion(hasItem(Items.LEATHER_LEGGINGS), conditionsFromItem(Items.LEATHER_LEGGINGS))
                .criterion(hasItem(Items.LEATHER), conditionsFromItem(Items.LEATHER))
                .criterion(hasItem(Items.AMETHYST_SHARD), conditionsFromItem(Items.AMETHYST_SHARD))
                .offerTo(exporter, Identifier.of(getRecipeName(ACModItems.PANTS)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ACModItems.BOOTS, 1)
                .pattern("AXA")
                .input('X', Items.LEATHER_BOOTS)
                .input('A', Items.AMETHYST_SHARD)
                .criterion(hasItem(Items.LEATHER_BOOTS), conditionsFromItem(Items.LEATHER_BOOTS))
                .criterion(hasItem(Items.AMETHYST_SHARD), conditionsFromItem(Items.AMETHYST_SHARD))
                .offerTo(exporter, Identifier.of(getRecipeName(ACModItems.BOOTS)));

    }
}
