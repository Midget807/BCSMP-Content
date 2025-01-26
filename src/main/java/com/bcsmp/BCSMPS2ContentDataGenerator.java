package com.bcsmp;

import com.bcsmp.main.domain_expansion.datagen.DEModBlockTagProvider;
import com.bcsmp.main.domain_expansion.datagen.DEModLootTableGenerator;
import com.bcsmp.main.domain_expansion.datagen.DEModModelProvider;
import com.bcsmp.main.domain_expansion.datagen.DEModWorldGenerator;
import com.bcsmp.main.domain_expansion.world.biome.DEModBiomes;
import com.bcsmp.main.domain_expansion.world.dimension.DEModDimensions;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;

public class BCSMPS2ContentDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

		//Domain Expansion Mod
		pack.addProvider(DEModModelProvider::new);
		pack.addProvider(DEModBlockTagProvider::new);
		pack.addProvider(DEModLootTableGenerator::new);
		pack.addProvider(DEModWorldGenerator::new);
	}

	@Override
	public void buildRegistry(RegistryBuilder registryBuilder) {
		registryBuilder.addRegistry(RegistryKeys.BIOME, DEModBiomes::bootstrap);
		registryBuilder.addRegistry(RegistryKeys.DIMENSION_TYPE, DEModDimensions::bootstrapType);
	}
}
