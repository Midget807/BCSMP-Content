package com.bcsmp.bcsmp_content.main.domain_expansion.worldgen.feature;

import com.bcsmp.bcsmp_content.BCSMPContentMain;
import com.bcsmp.bcsmp_content.main.domain_expansion.worldgen.feature.custom.ObsidianSpikeConfig;
import com.bcsmp.bcsmp_content.main.domain_expansion.worldgen.feature.custom.ObsidianSpikeFeature;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.BiomePlacementModifier;
import net.minecraft.world.gen.placementmodifier.CountPlacementModifier;
import net.minecraft.world.gen.placementmodifier.SquarePlacementModifier;

import java.util.LinkedHashMap;
import java.util.Map;

public class DEModFeatures {
    //Placed Features
    public static RegistryKey<PlacedFeature> OBSIDIAN_SPIKE_PLACED = RegistryKey.of(RegistryKeys.PLACED_FEATURE, BCSMPContentMain.domainExpansionId("obsidian_spike"));

    //Features
    public static Map<Feature<? extends FeatureConfig>, Identifier> FEATURES = new LinkedHashMap<>();

    public static final Feature<? extends FeatureConfig> OBSIDIAN_SPIKE = create("obsidian_spike", new ObsidianSpikeFeature(ObsidianSpikeConfig.CODEC));

    public static Feature<? extends FeatureConfig> create(String name, Feature<? extends FeatureConfig> feature) {
        FEATURES.put(feature, BCSMPContentMain.domainExpansionId(name));
        return feature;
    }

    //Main Registry ===============================================================================

    public static void registerDomainExpansionFeatures() {
        BCSMPContentMain.LOGGER.info("Domain Expansion -> Registering Mod Features");
        FEATURES.forEach((block, id) -> Registry.register(Registries.FEATURE, id, block));
    }
}
