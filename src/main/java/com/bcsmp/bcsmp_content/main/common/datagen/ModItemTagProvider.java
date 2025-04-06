package com.bcsmp.bcsmp_content.main.common.datagen;

import com.bcsmp.bcsmp_content.BCSMPContentMain;
import com.bcsmp.bcsmp_content.main.domain_robes.item.DRModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public static final TagKey<Item> PILLAR_SOUL_BLOCKS = TagKey.of(RegistryKeys.ITEM, BCSMPContentMain.domainExpansionId("pillar_soul_blocks"));

    public ModItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        this.getOrCreateTagBuilder(PILLAR_SOUL_BLOCKS)
                .add(Items.SOUL_SAND)
                .add(Items.SOUL_SOIL);
        this.getOrCreateTagBuilder(ItemTags.DYEABLE)
                .add(DRModItems.HOOD)
                .add(DRModItems.ROBE)
                .add(DRModItems.PANTS)
                .add(DRModItems.BOOTS);
    }
}
