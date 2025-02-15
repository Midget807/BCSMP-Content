package com.bcsmp.bcsmp_content.main.domain_expansion.worldgen.feature.custom;

import com.bcsmp.bcsmp_content.main.domain_expansion.block.DEModBlocks;
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
            this.generateSpike(SpikeRotation.getRandomRotation(random), SpikeSize.getRandomSize(random), SpikeShape.getRandomShape(random), origin, structureWorldAccess);

            //todo Debug -> just make preset shapes
            if (!(origin.getY() >= 0)) {
                this.setBlockState(structureWorldAccess, origin, Blocks.GOLD_BLOCK.getDefaultState());
                for (int k = 1; k < 10; k++) {
                    this.setBlockState(structureWorldAccess, origin.add(0, k, 0), Blocks.OBSIDIAN.getDefaultState());
                }

            }

            return true;
        }
    }

    private void generateSpike(SpikeRotation.Rotation rotation, SpikeSize.Size size, SpikeShape.Shape shape, BlockPos origin, StructureWorldAccess structureWorldAccess) {
        switch (rotation) {
            case EAST: {
                switch (size) {
                    case MEDIUM: {
                        switch (shape) {
                            case CURVE: {

                            }
                            case WIDE: {

                            }
                            default: {

                            }
                        }
                    }
                    case LARGE: {
                        switch (shape) {
                            case CURVE: {

                            }
                            case WIDE: {

                            }
                            default: {

                            }
                        }
                    }
                    default: {
                        switch (shape) {
                            case CURVE: {

                            }
                            case WIDE: {

                            }
                            default: {

                            }
                        }
                    }
                }
            }
            case SOUTH: {
                switch (size) {
                    case MEDIUM: {
                        switch (shape) {
                            case CURVE: {

                            }
                            case WIDE: {

                            }
                            default: {

                            }
                        }
                    }
                    case LARGE: {
                        switch (shape) {
                            case CURVE: {

                            }
                            case WIDE: {

                            }
                            default: {

                            }
                        }
                    }
                    default: {
                        switch (shape) {
                            case CURVE: {

                            }
                            case WIDE: {

                            }
                            default: {

                            }
                        }
                    }
                }
            }
            case WEST: {
                switch (size) {
                    case MEDIUM: {
                        switch (shape) {
                            case CURVE: {

                            }
                            case WIDE: {

                            }
                            default: {

                            }
                        }
                    }
                    case LARGE: {
                        switch (shape) {
                            case CURVE: {

                            }
                            case WIDE: {

                            }
                            default: {

                            }
                        }
                    }
                    default: {
                        switch (shape) {
                            case CURVE: {

                            }
                            case WIDE: {

                            }
                            default: {

                            }
                        }
                    }
                }
            }
            default /*NORTH*/: {
                switch (size) {
                    case MEDIUM: {
                        switch (shape) {
                            case CURVE: {

                            }
                            case WIDE: {

                            }
                            default: {

                            }
                        }
                    }
                    case LARGE: {
                        switch (shape) {
                            case CURVE: {

                            }
                            case WIDE: {

                            }
                            default: {

                            }
                        }
                    }
                    default /*SMALL*/: {
                        switch (shape) {
                            case CURVE: {

                            }
                            case WIDE: {

                            }
                            default  /*THIN*/: {
                                /*
                                * Makes:
                                *       []
                                *     [][][]
                                *   [][][][][]
                                * [][][][][][][]
                                *   [][][][][]
                                *     [][][]
                                *       []
                                * */
                                int j1 = 0;
                                for (int i = origin.getX() - 3; i < origin.getX() + 3; i++) {
                                    j1 = (i > 4) ? (j1 - 1) : (j1 + 1);
                                    for (int j2 = 0; j2 < j1; j2++) {
                                        this.setBlockState(structureWorldAccess, new BlockPos(i, origin.getY(), origin.getZ() - j2), DEModBlocks.DOMAIN_OBSIDIAN.getDefaultState());
                                        this.setBlockState(structureWorldAccess, new BlockPos(i, origin.getY(), origin.getZ() + j2), DEModBlocks.DOMAIN_OBSIDIAN.getDefaultState());
                                    }
                                }

                            }
                        }
                    }
                }
            }
        }
    }


    public static class SpikeRotation {
        public static Rotation getRandomRotation(Random random) {
            int rot = random.nextBetween(0, 3);
            return switch (rot) {
                case 1 -> Rotation.EAST;
                case 2 -> Rotation.SOUTH;
                case 3 -> Rotation.WEST;
                default -> Rotation.NORTH;
            };
        }
        public enum Rotation {
            NORTH,
            EAST,
            SOUTH,
            WEST
        }
    }
    public static class SpikeSize {
        public static Size getRandomSize(Random random) {
            int size = random.nextBetween(0, 3);
            return switch (size) {
                case 1 -> Size.MEDIUM;
                case 2 -> Size.LARGE;
                default -> Size.SMALL;
            };
        }
        public enum Size {
            SMALL,
            MEDIUM,
            LARGE
        }
    }
    public static class SpikeShape {
        public static Shape getRandomShape(Random random) {
            int shape = random.nextBetween(0, 2);
            return switch (shape) {
                case 1 -> Shape.CURVE;
                case 2 -> Shape.WIDE;
                default -> Shape.THIN;
            };
        }
        public enum Shape {
            THIN,
            CURVE,
            WIDE
        }
    }


}
