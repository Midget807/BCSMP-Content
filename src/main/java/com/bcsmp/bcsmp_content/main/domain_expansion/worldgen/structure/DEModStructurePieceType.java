package com.bcsmp.bcsmp_content.main.domain_expansion.worldgen.structure;

import com.bcsmp.bcsmp_content.main.domain_expansion.worldgen.structure.custom.domain_1.ObsidianSpikeGenerator;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.structure.StructurePieceType;

import java.util.Locale;

public interface DEModStructurePieceType {
    StructurePieceType OBSIDIAN_SPIKE = register(ObsidianSpikeGenerator.Piece::new, "obsidian_spike");

    public static StructurePieceType register(StructurePieceType type, String name) {
        return Registry.register(Registries.STRUCTURE_PIECE, name.toLowerCase(Locale.ROOT), type);
    }
    public static StructurePieceType register(StructurePieceType.Simple type, String name) {
        return register((StructurePieceType) type, name);
    }
    public static StructurePieceType register(StructurePieceType.ManagerAware type, String name) {
        return register((StructurePieceType) type, name);
    }
}
