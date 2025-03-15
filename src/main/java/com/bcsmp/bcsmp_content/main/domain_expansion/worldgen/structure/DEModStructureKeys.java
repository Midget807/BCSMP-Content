package com.bcsmp.bcsmp_content.main.domain_expansion.worldgen.structure;

import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.structure.Structure;

public interface DEModStructureKeys {
    RegistryKey<Structure> OBSIDIAN_SPIKE_THIN = of("obsidian_spike_thin");
    RegistryKey<Structure> OBSIDIAN_SPIKE_CURVED = of("obsidian_spike_thin");
    RegistryKey<Structure> OBSIDIAN_SPIKE_WIDE = of("obsidian_spike_thin");

    private static RegistryKey<Structure> of(String id) {
        return RegistryKey.of(RegistryKeys.STRUCTURE, new Identifier(id));
    }
}
