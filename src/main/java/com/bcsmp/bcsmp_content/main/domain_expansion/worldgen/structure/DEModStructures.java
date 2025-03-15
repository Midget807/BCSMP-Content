package com.bcsmp.bcsmp_content.main.domain_expansion.worldgen.structure;

import com.bcsmp.bcsmp_content.main.domain_expansion.datagen.DEModBiomeTagProvider;
import com.bcsmp.bcsmp_content.main.domain_expansion.worldgen.biome.DEModBiomes;
import com.bcsmp.bcsmp_content.main.domain_expansion.worldgen.structure.custom.domain_1.ObsidianSpikeStructure;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.world.StructureSpawns;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.StructureTerrainAdaptation;
import net.minecraft.world.gen.structure.Structure;
import net.minecraft.world.gen.structure.StructureKeys;

import java.util.Map;

public class DEModStructures {
    public static void bootstrap(Registerable<Structure> structureRegisterable) {
        RegistryEntryLookup<Biome> registryEntryLookup = structureRegisterable.getRegistryLookup(RegistryKeys.BIOME);
        RegistryEntryLookup<StructurePool> registryEntryLookup2 = structureRegisterable.getRegistryLookup(RegistryKeys.TEMPLATE_POOL);
        structureRegisterable.register(
                DEModStructureKeys.OBSIDIAN_SPIKE_THIN,
                new ObsidianSpikeStructure(createConfig(registryEntryLookup.getOrThrow(DEModBiomeTagProvider.DARK_PLAINS), StructureTerrainAdaptation.NONE), 0)
        );
        structureRegisterable.register(
                DEModStructureKeys.OBSIDIAN_SPIKE_CURVED,
                new ObsidianSpikeStructure(createConfig(registryEntryLookup.getOrThrow(DEModBiomeTagProvider.DARK_PLAINS), StructureTerrainAdaptation.NONE), 1)
        );
        structureRegisterable.register(
                DEModStructureKeys.OBSIDIAN_SPIKE_WIDE,
                new ObsidianSpikeStructure(createConfig(registryEntryLookup.getOrThrow(DEModBiomeTagProvider.DARK_PLAINS), StructureTerrainAdaptation.NONE), 2)
        );
    }

    private static Structure.Config createConfig(RegistryEntryList<Biome> biomes, StructureTerrainAdaptation terrainAdaptation) {
        return createConfig(biomes, Map.of(), GenerationStep.Feature.SURFACE_STRUCTURES, terrainAdaptation);
    }
    private static Structure.Config createConfig(RegistryEntryList<Biome> biomes, GenerationStep.Feature featureStep, StructureTerrainAdaptation terrainAdaptation) {
        return createConfig(biomes, Map.of(), featureStep, terrainAdaptation);
    }

    private static Structure.Config createConfig(RegistryEntryList<Biome> biomes, Map<SpawnGroup, StructureSpawns> spawns, GenerationStep.Feature featureStep, StructureTerrainAdaptation terrainAdaptation) {
        return new Structure.Config(biomes, spawns, featureStep, terrainAdaptation);
    }
}
