package com.bcsmp.bcsmp_content.main.domain_expansion.worldgen.structure;

import com.bcsmp.bcsmp_content.main.domain_expansion.worldgen.structure.custom.domain_1.ObsidianSpikeStructure;
import com.mojang.serialization.Codec;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.world.gen.structure.Structure;
import net.minecraft.world.gen.structure.StructureType;

public interface DEModStructureType<S extends Structure> {
    StructureType<ObsidianSpikeStructure> OBSIDIAN_SPIKE = register("obsidian_spike", ObsidianSpikeStructure.CODEC);

    Codec<S> codec();
    public static <S extends Structure> StructureType<S> register(String name, Codec<S> codec) {
        return Registry.register(Registries.STRUCTURE_TYPE, name, () -> codec);
    }
}
