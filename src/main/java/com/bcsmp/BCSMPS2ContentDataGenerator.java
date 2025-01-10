package com.bcsmp;

import com.bcsmp.main.domain_expansion.datagen.DEModBlockTagProvider;
import com.bcsmp.main.domain_expansion.datagen.DEModModelProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class BCSMPS2ContentDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

		//Domain Expansion Mod
		pack.addProvider(DEModModelProvider::new);
		pack.addProvider(DEModBlockTagProvider::new);
	}
}
