package com.bcsmp.bcsmp_content.main.domain_expansion.worldgen.structure.custom.domain_1;

import com.bcsmp.bcsmp_content.BCSMPContentMain;
import com.bcsmp.bcsmp_content.main.domain_expansion.worldgen.structure.DEModStructurePieceType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.structure.*;
import net.minecraft.structure.processor.BlockIgnoreStructureProcessor;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class ObsidianSpikeGenerator {
    private static final Identifier[] THIN_TEMPLATES = new Identifier[] {
            BCSMPContentMain.domainExpansionId("obsidian_spike/small_north"),
            BCSMPContentMain.domainExpansionId("obsidian_spike/small_east"),
            BCSMPContentMain.domainExpansionId("obsidian_spike/small_south"),
            BCSMPContentMain.domainExpansionId("obsidian_spike/small_west"),
            BCSMPContentMain.domainExpansionId("obsidian_spike/medium_north"),
            BCSMPContentMain.domainExpansionId("obsidian_spike/medium_east"),
            BCSMPContentMain.domainExpansionId("obsidian_spike/medium_south"),
            BCSMPContentMain.domainExpansionId("obsidian_spike/medium_west"),
            BCSMPContentMain.domainExpansionId("obsidian_spike/large_north"),
            BCSMPContentMain.domainExpansionId("obsidian_spike/large_east"),
            BCSMPContentMain.domainExpansionId("obsidian_spike/large_south"),
            BCSMPContentMain.domainExpansionId("obsidian_spike/large_west"),
    };
    private static final Identifier[] CURVED_TEMPLATES = new Identifier[] {
            BCSMPContentMain.domainExpansionId("obsidian_spike/small_north"),
            BCSMPContentMain.domainExpansionId("obsidian_spike/small_east"),
            BCSMPContentMain.domainExpansionId("obsidian_spike/small_south"),
            BCSMPContentMain.domainExpansionId("obsidian_spike/small_west"),
            BCSMPContentMain.domainExpansionId("obsidian_spike/medium_north"),
            BCSMPContentMain.domainExpansionId("obsidian_spike/medium_east"),
            BCSMPContentMain.domainExpansionId("obsidian_spike/medium_south"),
            BCSMPContentMain.domainExpansionId("obsidian_spike/medium_west"),
            BCSMPContentMain.domainExpansionId("obsidian_spike/large_north"),
            BCSMPContentMain.domainExpansionId("obsidian_spike/large_east"),
            BCSMPContentMain.domainExpansionId("obsidian_spike/large_south"),
            BCSMPContentMain.domainExpansionId("obsidian_spike/large_west"),
    };
    private static final Identifier[] WIDE_TEMPLATES = new Identifier[] {
            BCSMPContentMain.domainExpansionId("obsidian_spike/small_north"),
            BCSMPContentMain.domainExpansionId("obsidian_spike/small_east"),
            BCSMPContentMain.domainExpansionId("obsidian_spike/small_south"),
            BCSMPContentMain.domainExpansionId("obsidian_spike/small_west"),
            BCSMPContentMain.domainExpansionId("obsidian_spike/medium_north"),
            BCSMPContentMain.domainExpansionId("obsidian_spike/medium_east"),
            BCSMPContentMain.domainExpansionId("obsidian_spike/medium_south"),
            BCSMPContentMain.domainExpansionId("obsidian_spike/medium_west"),
            BCSMPContentMain.domainExpansionId("obsidian_spike/large_north"),
            BCSMPContentMain.domainExpansionId("obsidian_spike/large_east"),
            BCSMPContentMain.domainExpansionId("obsidian_spike/large_south"),
            BCSMPContentMain.domainExpansionId("obsidian_spike/large_west"),
    };
    public enum Shapes {
        THIN,
        CURVED,
        WIDE
    }
    public static void addParts(
            StructureTemplateManager structureTemplateManager, BlockPos pos, BlockRotation rotation, StructurePiecesHolder holder, Random random, Shapes shape
    ) {
        Identifier identifier = switch (shape) {
            case CURVED -> Util.getRandom(CURVED_TEMPLATES, random);
            case WIDE -> Util.getRandom(WIDE_TEMPLATES, random);
            default -> Util.getRandom(THIN_TEMPLATES, random);
        };
        holder.addPiece(new ObsidianSpikeGenerator.Piece(structureTemplateManager, identifier, pos, rotation/*, shape*/));
    }

    public static class Piece extends SimpleStructurePiece {
        public Piece(StructureTemplateManager manager, Identifier identifier, BlockPos pos, BlockRotation rotation) {
            super(DEModStructurePieceType.OBSIDIAN_SPIKE, 0, manager, identifier, identifier.toString(), createPlacementData(rotation), pos);
        }
        public Piece(StructureTemplateManager manager, NbtCompound nbt) {
            super(DEModStructurePieceType.OBSIDIAN_SPIKE, nbt, manager, id -> createPlacementData(BlockRotation.valueOf(nbt.getString("Rot"))));
        }

        private static StructurePlacementData createPlacementData(BlockRotation rotation) {
            return new StructurePlacementData()
                    .setRotation(rotation)
                    .setMirror(BlockMirror.NONE)
                    .setPosition(new BlockPos(4, 0, 15))
                    .addProcessor(BlockIgnoreStructureProcessor.IGNORE_AIR_AND_STRUCTURE_BLOCKS);
        }

        @Override
        public void writeNbt(StructureContext context, NbtCompound nbt) {
            super.writeNbt(context, nbt);
            nbt.putString("Rot", this.placementData.getRotation().name());
        }

        @Override
        public void handleMetadata(String metadata, BlockPos pos, ServerWorldAccess world, Random random, BlockBox boundingBox) {
        }

        @Override
        public void generate(StructureWorldAccess world, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox chunkBox, ChunkPos chunkPos, BlockPos pivot) {
            int i = world.getTopY();
            int j = 0;
            Vec3i vec3i = this.template.getSize();
            Heightmap.Type type = Heightmap.Type.WORLD_SURFACE_WG;
            int k = vec3i.getX() * vec3i.getZ();
            if (k == 0) {
                j = world.getTopY(type, this.pos.getX(), this.pos.getZ());
            } else {
                BlockPos blockPos = this.pos.add(vec3i.getX() - 1, 0, vec3i.getZ() - 1);

                for (BlockPos blockPos2 : BlockPos.iterate(this.pos, blockPos)) {
                    int l = world.getTopY(type, blockPos2.getX(), blockPos2.getZ());
                    j += l;
                    i = Math.min(i, l);
                }

                j /= k;
            }
            int m = i - vec3i.getY() / 2 - random.nextInt(3);
            this.pos = new BlockPos(this.pos.getX(), m, this.pos.getZ());
            super.generate(world, structureAccessor, chunkGenerator, random, chunkBox, chunkPos, pivot);
        }
    }
}
