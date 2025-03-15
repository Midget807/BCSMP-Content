package com.bcsmp.bcsmp_content.main.domain_expansion.datagen;

import com.bcsmp.bcsmp_content.BCSMPContentMain;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.TagKey;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class DEModItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public static final TagKey<Item> PILLAR_SOUL_BLOCKS = TagKey.of(RegistryKeys.ITEM, BCSMPContentMain.domainExpansionId("pillar_soul_blocks"));
    public DEModItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture, @Nullable BlockTagProvider blockTagProvider) {
        super(output, completableFuture, blockTagProvider);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        this.getOrCreateTagBuilder(PILLAR_SOUL_BLOCKS)
                .add(Items.SOUL_SAND)
                .add(Items.SOUL_SOIL);
    }
}
