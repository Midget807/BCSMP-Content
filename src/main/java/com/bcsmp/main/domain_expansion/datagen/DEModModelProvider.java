package com.bcsmp.main.domain_expansion.datagen;

import com.bcsmp.main.domain_expansion.item.DEModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class DEModModelProvider extends FabricModelProvider {

    public DEModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {

    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(DEModItems.DEBUGGER, Items.STICK, Models.GENERATED);
    }
}
