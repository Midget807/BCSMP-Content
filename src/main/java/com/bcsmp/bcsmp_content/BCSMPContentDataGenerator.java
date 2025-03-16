package com.bcsmp.bcsmp_content;

import com.bcsmp.bcsmp_content.main.domain_expansion.datagen.*;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;

public class BCSMPContentDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

		//Domain Expansion Mod
		pack.addProvider(DEModModelProvider::new);
		pack.addProvider(DEModBlockTagProvider::new);
		pack.addProvider(DEModItemTagProvider::new);
		pack.addProvider(DEModLootTableGenerator::new);
		pack.addProvider(DEModRecipeProvider::new);
		pack.addProvider(DEModWorldGenerator::new);
	}

	@Override
	public void buildRegistry(RegistryBuilder registryBuilder) {
		//registryBuilder.addRegistry(RegistryKeys.BIOME, DEModBiomes::bootstrap);
		//registryBuilder.addRegistry(RegistryKeys.DIMENSION_TYPE, DEModDimensions::bootstrapType);
	}
}
