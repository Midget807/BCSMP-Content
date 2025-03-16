package com.bcsmp.bcsmp_content.main.domain_expansion.worldgen.biome.surface;

import com.bcsmp.bcsmp_content.BCSMPContentMain;
import com.bcsmp.bcsmp_content.main.domain_expansion.worldgen.biome.DEModBiomes;
import com.bcsmp.bcsmp_content.main.domain_expansion.worldgen.dimension.DEModDimensions;
import com.bcsmp.bcsmp_content.main.domain_expansion.worldgen.feature.DEModFeatures;
import com.bcsmp.bcsmp_content.main.domain_expansion.worldgen.feature.custom.ObsidianSpikeFeature;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.minecraft.world.gen.GenerationStep;

public class DEModBiomeModifier {
    public static void addFeatures() {
        BiomeModifications.addFeature(
                biomeSelectionContext ->
                        biomeSelectionContext.canGenerateIn(DEModDimensions.DOMAIN_1_KEY) ||
                        biomeSelectionContext.getBiomeKey() == DEModBiomes.DARK_PLAINS,
                GenerationStep.Feature.VEGETAL_DECORATION,
                DEModFeatures.OBSIDIAN_SPIKE_PLACED
        );
    }
    public static void registerDomainExpansionBiomeModifier() {
        BCSMPContentMain.LOGGER.info("Domain Expansion -> Registering Mod Biome Modifier");
        addFeatures();
    }
}
