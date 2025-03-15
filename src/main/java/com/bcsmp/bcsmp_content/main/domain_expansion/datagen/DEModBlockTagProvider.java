package com.bcsmp.bcsmp_content.main.domain_expansion.datagen;

import com.bcsmp.bcsmp_content.BCSMPContentMain;
import com.bcsmp.bcsmp_content.main.domain_expansion.block.DEModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;

import java.util.concurrent.CompletableFuture;

public class DEModBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public DEModBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        this.getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
                .add(DEModBlocks.DOMAIN_PILLAR);
    }
}
