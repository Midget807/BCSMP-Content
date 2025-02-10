package com.bcsmp.bcsmp_content.main.domain_expansion.worldgen.feature.custom;

import com.mojang.serialization.Codec;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class ObsidianSpikeFeature extends Feature<DefaultFeatureConfig> {
    public ObsidianSpikeFeature(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        BlockPos origin = context.getOrigin();
        Random random = context.getRandom();
        StructureWorldAccess structureWorldAccess = context.getWorld();

        while (structureWorldAccess.isAir(origin) && origin.getY() > structureWorldAccess.getBottomY() + 2) {
            origin = origin.down();
        }

        if (!structureWorldAccess.getBlockState(origin).isOf(Blocks.COAL_BLOCK) ||
                structureWorldAccess.getBlockState(origin).isOf(Blocks.AIR)
        ) {
            return false;
        } else {
            //idk what this shit does but keep it
            origin = origin.up(random.nextInt(4));
            int i = random.nextInt(4) + 7;
            int j = i / 4 + random.nextInt(2);
            if (j > 1 && random.nextInt(60) == 0) {
                origin = origin.up(10 + random.nextInt(30));
            }

            //spike structure todo make actual structure and use a rng to choose direction (4) and size (3)
            if (!(origin.getY() >= 0)) {
                for (int k = 0; k < 10; k++) {
                    this.setBlockState(structureWorldAccess, origin.add(0, k, 0), Blocks.OBSIDIAN.getDefaultState());
                }
            }

            return true;
        }
    }
}
