package com.bcsmp.bcsmp_content.main.domain_expansion.worldgen.structure.custom.domain_1;

import com.bcsmp.bcsmp_content.main.domain_expansion.worldgen.structure.DEModStructureType;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.structure.StructurePiecesCollector;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.structure.Structure;
import net.minecraft.world.gen.structure.StructureType;

import java.util.Optional;

public class ObsidianSpikeStructure extends Structure {
    public static final Codec<ObsidianSpikeStructure> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(configCodecBuilder(instance), Codec.INT.fieldOf("shape").forGetter(obsidianSpikeStructure -> obsidianSpikeStructure.shape.ordinal()))
                    .apply(instance, ObsidianSpikeStructure::new)
    );
    public final ObsidianSpikeGenerator.Shapes shape;
    public ObsidianSpikeStructure(Config config, int shape) {
        super(config);
        this.shape = switch (shape) {
            case 1 -> ObsidianSpikeGenerator.Shapes.CURVED;
            case 2 -> ObsidianSpikeGenerator.Shapes.WIDE;
            default -> ObsidianSpikeGenerator.Shapes.THIN;
        };
    }

    @Override
    public Optional<StructurePosition> getStructurePosition(Context context) {
        Heightmap.Type type = Heightmap.Type.WORLD_SURFACE_WG;
        return getStructurePosition(context, type, collector -> this.addPieces(collector, context));
    }

    private void addPieces(StructurePiecesCollector collector, Context context) {
        BlockRotation blockRotation = BlockRotation.random(context.random());
        BlockPos blockPos = new BlockPos(context.chunkPos().getStartX(), 90, context.chunkPos().getStartZ());
        ObsidianSpikeGenerator.addParts(context.structureTemplateManager(), blockPos, blockRotation, collector, context.random(), this.shape);
    }

    @Override
    public StructureType<?> getType() {
        return DEModStructureType.OBSIDIAN_SPIKE;
    }
}
