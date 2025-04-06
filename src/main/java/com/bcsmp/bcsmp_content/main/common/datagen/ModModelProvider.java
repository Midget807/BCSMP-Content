package com.bcsmp.bcsmp_content.main.common.datagen;

import com.bcsmp.bcsmp_content.BCSMPContentMain;
import com.bcsmp.bcsmp_content.main.domain_expansion.block.DEModBlocks;
import com.bcsmp.bcsmp_content.main.domain_expansion.item.DEModItems;
import com.bcsmp.bcsmp_content.main.domain_robes.item.DRModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.*;
import net.minecraft.item.Items;

public class ModModelProvider extends FabricModelProvider {

    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerDoubleBlock(DEModBlocks.DOMAIN_PILLAR, BCSMPContentMain.domainExpansionId("block/domain_pillar_top"), BCSMPContentMain.domainExpansionId("block/domain_pillar_bottom"));
        blockStateModelGenerator.registerSimpleCubeAll(DEModBlocks.WITHERED_SOIL);
        blockStateModelGenerator.registerSimpleCubeAll(DEModBlocks.DOMAIN_OBSIDIAN);

        blockStateModelGenerator.registerParentedItemModel(DEModBlocks.WITHERED_SOIL, ModelIds.getBlockModelId(DEModBlocks.WITHERED_SOIL));
        blockStateModelGenerator.registerParentedItemModel(DEModBlocks.DOMAIN_OBSIDIAN, ModelIds.getBlockModelId(DEModBlocks.DOMAIN_OBSIDIAN));
        blockStateModelGenerator.registerItemModel(DEModBlocks.DOMAIN_PILLAR);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(DEModItems.DEBUGGER, Items.STICK, Models.GENERATED);
        itemModelGenerator.register(DEModItems.DOMAIN_EXPANDER, Models.GENERATED);
        itemModelGenerator.register(DEModItems.DOMAIN_COMPRESSOR, Models.GENERATED);

        itemModelGenerator.register(DRModItems.HOOD, Models.GENERATED);
        itemModelGenerator.register(DRModItems.ROBE, Models.GENERATED);
        itemModelGenerator.register(DRModItems.PANTS, Models.GENERATED);
        itemModelGenerator.register(DRModItems.BOOTS, Models.GENERATED);
    }
}
