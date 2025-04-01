package com.bcsmp.bcsmp_content.main.domain_expansion.world.gen.feature;

import com.bcsmp.bcsmp_content.BCSMPContentMain;
import com.bcsmp.bcsmp_content.main.domain_expansion.world.gen.feature.custom.ObsidianSpikeConfig;
import com.bcsmp.bcsmp_content.main.domain_expansion.world.gen.feature.custom.ObsidianSpikeFeature;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.SquarePlacementModifier;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DEModFeatures {

    //Features
    public static final ObsidianSpikeFeature OBSIDIAN_SPIKE_FEATURE = new ObsidianSpikeFeature(ObsidianSpikeConfig.CODEC);

    public static Identifier OBSIDIAN_SPIKE_ID = BCSMPContentMain.domainExpansionId("obsidian_spike");
    //Placed Features
    public static final RegistryKey<PlacedFeature> OBSIDIAN_SPIKE_PLACED_KEY = RegistryKey.of(RegistryKeys.PLACED_FEATURE, OBSIDIAN_SPIKE_ID);


    //Main Registry ===============================================================================

    public static void registerDomainExpansionFeatures() {
        BCSMPContentMain.LOGGER.info("Domain Expansion -> Registering Mod Features");
        Registry.register(Registries.FEATURE, OBSIDIAN_SPIKE_ID, OBSIDIAN_SPIKE_FEATURE);
    }
}
