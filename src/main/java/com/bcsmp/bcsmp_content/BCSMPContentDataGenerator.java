package com.bcsmp.bcsmp_content;

import com.bcsmp.bcsmp_content.main.common.datagen.*;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;

public class BCSMPContentDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

		pack.addProvider(ModModelProvider::new);
		pack.addProvider(ModBlockTagProvider::new);
		pack.addProvider(ModItemTagProvider::new);
		pack.addProvider(ModLootTableGenerator::new);
		pack.addProvider(ModRecipeProvider::new);
		pack.addProvider(ModWorldGenerator::new);

	}

	@Override
	public void buildRegistry(RegistryBuilder registryBuilder) {
		//registryBuilder.addRegistry(RegistryKeys.BIOME, DEModBiomes::bootstrap);
		//registryBuilder.addRegistry(RegistryKeys.DIMENSION_TYPE, DEModDimensions::bootstrapType);
	}
}
