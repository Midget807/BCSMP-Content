package com.bcsmp.bcsmp_content.main.domain_expansion.world.gen.feature.custom;

import com.bcsmp.bcsmp_content.main.domain_expansion.block.DEModBlocks;
import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.StructureTemplate;
import net.minecraft.structure.StructureTemplateManager;
import net.minecraft.structure.processor.BlockIgnoreStructureProcessor;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.*;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class ObsidianSpikeFeature extends Feature<ObsidianSpikeConfig> {
    public ObsidianSpikeFeature(Codec<ObsidianSpikeConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<ObsidianSpikeConfig> context) {
        BlockPos origin = context.getOrigin();
        Random random = context.getRandom();
        StructureWorldAccess structureWorldAccess = context.getWorld();
        origin = new BlockPos(origin.getX(), -1, origin.getZ());

        BlockPos testPos = new BlockPos(origin);
        for (int y = -63; y < structureWorldAccess.getHeight(); y++) {
            testPos = testPos.up();
            if (structureWorldAccess.getBlockState(testPos).isOf(DEModBlocks.WITHERED_SOIL)) {
                if (structureWorldAccess.getBlockState(testPos.up()).isOf(Blocks.AIR)) {
                    for (int i = 0; i < 10; i++) {
                        structureWorldAccess.setBlockState(testPos, Blocks.GOLD_BLOCK.getDefaultState(), 0x10);
                        testPos.up();
                    }
                    return true;
                }
            }
        }
        return false;
        //if (structureWorldAccess.getBlockState(origin).isOf(DEModBlocks.WITHERED_SOIL) && !(structureWorldAccess.getBlockState(origin).isOf(Blocks.AIR))) {

            //prevents spawn from being shat on
            /*for (int n = 0; n < 10; n++) {
                for (int o = 0; o < 10; o++) {
                    if (origin.equals(new BlockPos(n, -1, o))) {
                        return false;
                    }
                }
            }
            origin = origin.up(random.nextInt(4));
            int i = random.nextInt(4) + 7;
            int j = i / 4 + random.nextInt(2);
            if (j > 1 && random.nextInt(60) == 0) {
                origin = origin.up(10 + random.nextInt(30));
            }
            BlockRotation blockRotation = BlockRotation.random(random);
            ObsidianSpikeConfig obsidianSpikeConfig = context.getConfig();
            int shape = random.nextInt(obsidianSpikeConfig.obsidianSpikes().size());
            StructureTemplateManager structureTemplateManager = structureWorldAccess.toServerWorld().getServer().getStructureTemplateManager();
            StructureTemplate structureTemplate = structureTemplateManager.getTemplateOrBlank(obsidianSpikeConfig.obsidianSpikes().get(shape));
            ChunkPos chunkPos = new ChunkPos(origin);
            BlockBox blockBox = new BlockBox(
                    chunkPos.getStartX() - 16,
                    structureWorldAccess.getBottomY(),
                    chunkPos.getStartZ() - 16,
                    chunkPos.getEndX() + 16,
                    structureWorldAccess.getTopY(),
                    chunkPos.getEndZ() + 16
            );
            Vec3i size = structureTemplate.getSize();
            BlockPos chunkCenterPos = origin;
            switch (blockRotation) {
                case NONE -> chunkCenterPos = chunkCenterPos.add(-size.getX() / 2, 0, -size.getZ() / 2);
                case CLOCKWISE_90 -> chunkCenterPos = chunkCenterPos.add(size.getX() / 2, 0, -size.getZ() / 2);
                case CLOCKWISE_180 -> chunkCenterPos = chunkCenterPos.add(size.getX() / 2, 0, size.getZ() / 2);
                case COUNTERCLOCKWISE_90 -> chunkCenterPos = chunkCenterPos.add(-size.getX() / 2, 0, size.getZ() / 2);
            }
            StructurePlacementData structurePlacementData = new StructurePlacementData().setRotation(blockRotation).setBoundingBox(blockBox).setRandom(random);
            structurePlacementData.clearProcessors();
            structurePlacementData.addProcessor(BlockIgnoreStructureProcessor.IGNORE_AIR_AND_STRUCTURE_BLOCKS);
            structureTemplate.place(structureWorldAccess, chunkCenterPos, chunkCenterPos, structurePlacementData, random, 4);
*/

        //}
    }
}
