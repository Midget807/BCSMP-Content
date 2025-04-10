package com.bcsmp.bcsmp_content.main.common.datagen;

import com.bcsmp.bcsmp_content.main.domain_expansion.block.DEModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ModLootTableGenerator extends FabricBlockLootTableProvider {
    public ModLootTableGenerator(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        addDrop(DEModBlocks.DOMAIN_PILLAR, doorDrops(DEModBlocks.DOMAIN_PILLAR));
    }
}
