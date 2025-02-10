package com.bcsmp.bcsmp_content.main.domain_expansion.worldgen.feature;

import com.bcsmp.bcsmp_content.BCSMPContentMain;
import com.bcsmp.bcsmp_content.main.domain_expansion.worldgen.feature.custom.ObsidianSpikeFeature;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.BiomePlacementModifier;
import net.minecraft.world.gen.placementmodifier.CountPlacementModifier;
import net.minecraft.world.gen.placementmodifier.SquarePlacementModifier;

public class DEModFeatures {
    //Features ====================================================================================
    public static final Feature<DefaultFeatureConfig> OBSIDIAN_SPIKE = registerFeature("obsidian_spike", new ObsidianSpikeFeature(DefaultFeatureConfig.CODEC));

    private static <C extends FeatureConfig, F extends Feature<C>> F registerFeature(String name, F feature) {
        return Registry.register(Registries.FEATURE, BCSMPContentMain.domainExpansionId(name), feature);
    }

    //Configured Feature ==========================================================================

    public static final RegistryKey<ConfiguredFeature<?, ?>> OBSIDIAN_SPIKE_CONFIGURED = ConfiguredFeatures.of("obsidian_spike");

    public static void boostrapConfiguredFeature(Registerable<ConfiguredFeature<?, ?>> featureRegisterable) {
        ConfiguredFeatures.register(featureRegisterable, OBSIDIAN_SPIKE_CONFIGURED, OBSIDIAN_SPIKE);
    }

    //Placed Feature ==============================================================================

    public static final RegistryKey<PlacedFeature> OBSIDIAN_SPIKE_PLACED = registerPlacedFeature("obsidian_spike");

    public static void bootstrapPlacedFeature(Registerable<PlacedFeature> featureRegisterable) {
        RegistryEntryLookup<ConfiguredFeature<?, ?>> registryEntryLookup = featureRegisterable.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);
        RegistryEntry<ConfiguredFeature<?, ?>> obsidianSpikeEntry = registryEntryLookup.getOrThrow(OBSIDIAN_SPIKE_CONFIGURED);
        PlacedFeatures.register(
                featureRegisterable,
                OBSIDIAN_SPIKE_PLACED,
                obsidianSpikeEntry,
                CountPlacementModifier.of(6),
                SquarePlacementModifier.of(),
                PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP,
                BiomePlacementModifier.of()
        );
    }

    private static RegistryKey<PlacedFeature> registerPlacedFeature(String name) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, BCSMPContentMain.domainExpansionId(name));
    }

    //Main Registry ===============================================================================

    public static void registerDomainExpansionFeatures() {
        BCSMPContentMain.LOGGER.info("Domain Expansion -> Registering Mod Features");
    }
}
