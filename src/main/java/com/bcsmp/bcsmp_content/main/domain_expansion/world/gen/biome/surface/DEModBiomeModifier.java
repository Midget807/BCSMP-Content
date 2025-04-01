package com.bcsmp.bcsmp_content.main.domain_expansion.world.gen.biome.surface;

import com.bcsmp.bcsmp_content.BCSMPContentMain;
import com.bcsmp.bcsmp_content.main.domain_expansion.world.gen.biome.DEModBiomes;
import com.bcsmp.bcsmp_content.main.domain_expansion.world.gen.dimension.DEModDimensions;
import com.bcsmp.bcsmp_content.main.domain_expansion.world.gen.feature.DEModFeatures;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.gen.GenerationStep;

public class DEModBiomeModifier {
    public static void addFeatures() {
        BiomeModifications.addFeature(
                biomeSelectionContext ->
                        biomeSelectionContext.canGenerateIn(DEModDimensions.DOMAIN_1_KEY) ||
                        biomeSelectionContext.getBiomeKey() == DEModBiomes.DARK_PLAINS,
                GenerationStep.Feature.LOCAL_MODIFICATIONS,
                DEModFeatures.OBSIDIAN_SPIKE_PLACED_KEY
        );
    }
    public static void registerDomainExpansionBiomeModifier() {
        BCSMPContentMain.LOGGER.info("Domain Expansion -> Registering Mod Biome Modifier");
        addFeatures();
    }
}
