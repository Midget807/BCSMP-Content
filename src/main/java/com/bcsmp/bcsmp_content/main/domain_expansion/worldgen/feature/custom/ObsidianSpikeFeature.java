package com.bcsmp.bcsmp_content.main.domain_expansion.worldgen.feature.custom;

import com.bcsmp.bcsmp_content.main.domain_expansion.block.DEModBlocks;
import com.mojang.serialization.Codec;
import net.minecraft.block.Blocks;
import net.minecraft.text.Text;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
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

        if (!structureWorldAccess.getBlockState(origin).isOf(DEModBlocks.WITHERED_SOIL) ||
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


            if (!(origin.getY() >= 0)) {
                //prevents spawn from being shat on
                for (int n = 0; i < 10; i++) {
                    for (int o = 0; j < 10; j++) {
                        if (origin.equals(new BlockPos(n, -1, o))) {
                            return false;
                        }
                    }
                }

                //spike
                this.generateSpike(SpikeRotation.getRandomRotation(random), SpikeSize.getRandomSize(random), SpikeShape.getRandomShape(random), origin, structureWorldAccess);
            }

            return true;
        }
    }

    private void generateSpike(SpikeRotation.Rotation rotation, SpikeSize.Size size, SpikeShape.Shape shape, BlockPos origin, StructureWorldAccess structureWorldAccess) {
        switch (shape) {
            case CURVE: {

                //break;
            }
            case WIDE: {
                switch (size) {
                    case MEDIUM: {
                        //break;
                    }
                    case LARGE: {
                        //break;
                    }
                    default: case SMALL: {
                        this.generateSmallWideBase(structureWorldAccess, origin);
                        switch (rotation) {
                            case EAST: {

                                //break;
                            }
                            case SOUTH: {

                                //break;
                            }
                            case WEST: {

                                //break
                            }
                            default: case NORTH: {
                                // TODO: 16/02/2025 this shit -> smalls only then later update can be for more sizes 
                                break;
                            }
                        }
                        break;
                    }
                }
                //break;
            }
            default: case THIN: {
                switch (size) {
                    case MEDIUM: {

                        //break;
                    }
                    case LARGE: {

                        //break;
                    }
                    default: case SMALL: {
                        this.generateSmallThinBase(structureWorldAccess, origin);
                        switch (rotation) {
                            case EAST: {
                                /*
                                 * Makes:
                                 *       ()
                                 *     ()()()
                                 *   ()[]()()()
                                 * ()()()()()()()
                                 *   ()()()()()
                                 *     ()()()
                                 *       ()
                                 * */
                                for (int k = 0; k < 6; k++) {
                                    this.setBlockState(structureWorldAccess, origin.add(-1, 4 + k, -1), DEModBlocks.DOMAIN_OBSIDIAN.getDefaultState());
                                }

                                /*
                                 * Makes:
                                 *       ()
                                 *     ()()()
                                 *   []()()()()
                                 * ()[]()()()()()
                                 *   []()()()()
                                 *     ()()()
                                 *       ()
                                 * */
                                this.setBlockState(structureWorldAccess, origin.add(-2, 1, 1), DEModBlocks.DOMAIN_OBSIDIAN.getDefaultState());
                                for (int k = 0; k < 2; k++) {
                                    this.setBlockState(structureWorldAccess, origin.add(-2, 1 + k, -1), DEModBlocks.DOMAIN_OBSIDIAN.getDefaultState());
                                }
                                for (int k = 0; k < 5; k++) {
                                    this.setBlockState(structureWorldAccess, origin.add(-2, 1 + k, 0), DEModBlocks.DOMAIN_OBSIDIAN.getDefaultState());
                                }

                                /*
                                 * Makes:
                                 *       ()
                                 *     ()[][]
                                 *   ()()()()()
                                 * ()()()()()()()
                                 *   ()()()()()
                                 *     ()()()
                                 *       ()
                                 * */
                                this.setBlockState(structureWorldAccess, origin.add(1, 1, -2), DEModBlocks.DOMAIN_OBSIDIAN.getDefaultState());
                                for (int k = 0; k < 3; k++) {
                                    this.setBlockState(structureWorldAccess, origin.add(0, 1 + k, -2), DEModBlocks.DOMAIN_OBSIDIAN.getDefaultState());
                                }

                                /*
                                 * Makes:
                                 *       ()
                                 *     ()()()
                                 *   ()()()()()
                                 * ()()()()()()()
                                 *   ()()()()()
                                 *     [][][]
                                 *       ()
                                 * */
                                this.setBlockState(structureWorldAccess, origin.add(0, 3, 2), DEModBlocks.DOMAIN_OBSIDIAN.getDefaultState());
                                this.setBlockState(structureWorldAccess, origin.add(1, 1, 2), DEModBlocks.DOMAIN_OBSIDIAN.getDefaultState());
                                for (int i = -1; i <= 0; i++) {
                                    for (int k = 0; k < 2; k++) {
                                        this.setBlockState(structureWorldAccess, origin.add(i, 1 + k, 2), DEModBlocks.DOMAIN_OBSIDIAN.getDefaultState());
                                    }
                                }

                                /*
                                 * Makes:
                                 *       ()
                                 *     ()()()
                                 *   ()()()[]()
                                 * ()()()()()()()
                                 *   ()()()[]()
                                 *     ()()()
                                 *       ()
                                 * */
                                this.setBlockState(structureWorldAccess, origin.add(1, 4, 1), DEModBlocks.DOMAIN_OBSIDIAN.getDefaultState());
                                this.setBlockState(structureWorldAccess, origin.add(1, 4, -1), DEModBlocks.DOMAIN_OBSIDIAN.getDefaultState());

                                /*
                                 * Makes:
                                 *       ()
                                 *     ()()()
                                 *   ()()()()[]
                                 * ()()()()()[]()
                                 *   ()()()()()
                                 *     ()()()
                                 *       ()
                                 * */
                                for (int k = 0; k < 2; k++) {
                                    this.setBlockState(structureWorldAccess, origin.add(2, 1 + k, -1), DEModBlocks.DOMAIN_OBSIDIAN.getDefaultState());
                                }
                                for (int k = 0; k < 5; k++) {
                                    this.setBlockState(structureWorldAccess, origin.add(2, 1 + k, 0), DEModBlocks.DOMAIN_OBSIDIAN.getDefaultState());
                                }
                                break;
                            }
                            case SOUTH: {
                                /*
                                 * Makes:
                                 *       ()
                                 *     ()()()
                                 *   ()()()[]()
                                 * ()()()()()()()
                                 *   ()()()()()
                                 *     ()()()
                                 *       ()
                                 * */
                                for (int k = 0; k < 6; k++) {
                                    this.setBlockState(structureWorldAccess, origin.add(1, 4 + k, -1), DEModBlocks.DOMAIN_OBSIDIAN.getDefaultState());
                                }

                                /*
                                 * Makes:
                                 *       ()
                                 *     [][][]
                                 *   ()()()()()
                                 * ()()()()()()()
                                 *   ()()()()()
                                 *     ()()()
                                 *       ()
                                 * */
                                this.setBlockState(structureWorldAccess, origin.add(-1, 1, -2), DEModBlocks.DOMAIN_OBSIDIAN.getDefaultState());
                                for (int k = 0; k < 2; k++) {
                                    this.setBlockState(structureWorldAccess, origin.add(1, 1 + k, -2), DEModBlocks.DOMAIN_OBSIDIAN.getDefaultState());
                                }
                                for (int k = 0; k < 5; k++) {
                                    this.setBlockState(structureWorldAccess, origin.add(0, 1 + k, -2), DEModBlocks.DOMAIN_OBSIDIAN.getDefaultState());
                                }

                                /*
                                 * Makes:
                                 *       ()
                                 *     ()()()
                                 *   ()()()()()
                                 * ()()()()()[]()
                                 *   ()()()()[]
                                 *     ()()()
                                 *       ()
                                 * */
                                this.setBlockState(structureWorldAccess, origin.add(-2, 1, 1), DEModBlocks.DOMAIN_OBSIDIAN.getDefaultState());
                                for (int k = 0; k < 3; k++) {
                                    this.setBlockState(structureWorldAccess, origin.add(2, 1 + k, 0), DEModBlocks.DOMAIN_OBSIDIAN.getDefaultState());
                                }

                                /*
                                 * Makes:
                                 *       ()
                                 *     ()()()
                                 *   ()()()()()
                                 * ()()()()()()()
                                 *   ()()()()()
                                 *     [][][]
                                 *       ()
                                 * */
                                this.setBlockState(structureWorldAccess, origin.add(2, 3, 0), DEModBlocks.DOMAIN_OBSIDIAN.getDefaultState());
                                this.setBlockState(structureWorldAccess, origin.add(2, 1, -1), DEModBlocks.DOMAIN_OBSIDIAN.getDefaultState());
                                for (int j = 0; j <= 1; j++) {
                                    for (int k = 0; k < 2; k++) {
                                        this.setBlockState(structureWorldAccess, origin.add(2, 1 + k, j), DEModBlocks.DOMAIN_OBSIDIAN.getDefaultState());
                                    }
                                }

                                /*
                                 * Makes:
                                 *       ()
                                 *     ()()()
                                 *   ()()()()()
                                 * ()()()()()()()
                                 *   ()[]()[]()
                                 *     ()()()
                                 *       ()
                                 * */
                                this.setBlockState(structureWorldAccess, origin.add(-1, 4, 1), DEModBlocks.DOMAIN_OBSIDIAN.getDefaultState());
                                this.setBlockState(structureWorldAccess, origin.add(1, 4, 1), DEModBlocks.DOMAIN_OBSIDIAN.getDefaultState());

                                /*
                                 * Makes:
                                 *       ()
                                 *     ()()()
                                 *   ()()()()()
                                 * ()()()()()()()
                                 *   ()()()()()
                                 *     ()[][]
                                 *       ()
                                 * */
                                for (int k = 0; k < 2; k++) {
                                    this.setBlockState(structureWorldAccess, origin.add(1, 1 + k, 2), DEModBlocks.DOMAIN_OBSIDIAN.getDefaultState());
                                }
                                for (int k = 0; k < 5; k++) {
                                    this.setBlockState(structureWorldAccess, origin.add(0, 1 + k, 2), DEModBlocks.DOMAIN_OBSIDIAN.getDefaultState());
                                }
                                break;
                            }
                            case WEST: {
                                /*
                                 * Makes:
                                 *       ()
                                 *     ()()()
                                 *   ()()()()()
                                 * ()()()()()()()
                                 *   ()()()[]()
                                 *     ()()()
                                 *       ()
                                 * */
                                for (int k = 0; k < 6; k++) {
                                    this.setBlockState(structureWorldAccess, origin.add(1, 4 + k, 1), DEModBlocks.DOMAIN_OBSIDIAN.getDefaultState());
                                }

                                /*
                                 * Makes:
                                 *       ()
                                 *     ()()()
                                 *   ()()()()[]
                                 * ()()()()()[]()
                                 *   ()()()()[]
                                 *     ()()()
                                 *       ()
                                 * */
                                this.setBlockState(structureWorldAccess, origin.add(2, 1, -1), DEModBlocks.DOMAIN_OBSIDIAN.getDefaultState());
                                for (int k = 0; k < 2; k++) {
                                    this.setBlockState(structureWorldAccess, origin.add(2, 1 + k, 1), DEModBlocks.DOMAIN_OBSIDIAN.getDefaultState());
                                }
                                for (int k = 0; k < 5; k++) {
                                    this.setBlockState(structureWorldAccess, origin.add(2, 1 + k, 0), DEModBlocks.DOMAIN_OBSIDIAN.getDefaultState());
                                }

                                /*
                                 * Makes:
                                 *       ()
                                 *     ()()()
                                 *   ()()()()()
                                 * ()()()()()()()
                                 *   ()()()()()
                                 *     [][]()
                                 *       ()
                                 * */
                                this.setBlockState(structureWorldAccess, origin.add(-1, 1, 2), DEModBlocks.DOMAIN_OBSIDIAN.getDefaultState());
                                for (int k = 0; k < 3; k++) {
                                    this.setBlockState(structureWorldAccess, origin.add(0, 1 + k, 2), DEModBlocks.DOMAIN_OBSIDIAN.getDefaultState());
                                }

                                /*
                                 * Makes:
                                 *       ()
                                 *     [][][]
                                 *   ()()()()()
                                 * ()()()()()()()
                                 *   ()()()()()
                                 *     ()()()
                                 *       ()
                                 * */
                                this.setBlockState(structureWorldAccess, origin.add(0, 3, -2), DEModBlocks.DOMAIN_OBSIDIAN.getDefaultState());
                                this.setBlockState(structureWorldAccess, origin.add(-1, 1, -2), DEModBlocks.DOMAIN_OBSIDIAN.getDefaultState());
                                for (int i = 0; i <= 1; i++) {
                                    for (int k = 0; k < 2; k++) {
                                        this.setBlockState(structureWorldAccess, origin.add(i, 1 + k, -2), DEModBlocks.DOMAIN_OBSIDIAN.getDefaultState());
                                    }
                                }

                                /*
                                 * Makes:
                                 *       ()
                                 *     ()()()
                                 *   ()[]()()()
                                 * ()()()()()()()
                                 *   ()[]()()()
                                 *     ()()()
                                 *       ()
                                 * */
                                this.setBlockState(structureWorldAccess, origin.add(-1, 4, -1), DEModBlocks.DOMAIN_OBSIDIAN.getDefaultState());
                                this.setBlockState(structureWorldAccess, origin.add(-1, 4, 1), DEModBlocks.DOMAIN_OBSIDIAN.getDefaultState());

                                /*
                                 * Makes:
                                 *       ()
                                 *     ()()()
                                 *   ()()()()()
                                 * ()[]()()()()()
                                 *   []()()()()
                                 *     ()()()
                                 *       ()
                                 * */
                                for (int k = 0; k < 2; k++) {
                                    this.setBlockState(structureWorldAccess, origin.add(-2, 1 + k, 1), DEModBlocks.DOMAIN_OBSIDIAN.getDefaultState());
                                }
                                for (int k = 0; k < 5; k++) {
                                    this.setBlockState(structureWorldAccess, origin.add(-2, 1 + k, 0), DEModBlocks.DOMAIN_OBSIDIAN.getDefaultState());
                                }
                                break;
                            }
                            default: case NORTH: {
                                /*
                                 * Makes:
                                 *       ()
                                 *     ()()()
                                 *   ()()()()()
                                 * ()()()()()()()
                                 *   ()[]()()()
                                 *     ()()()
                                 *       ()
                                 * */
                                for (int k = 0; k < 6; k++) {
                                    this.setBlockState(structureWorldAccess, origin.add(-1, 4 + k, 1), DEModBlocks.DOMAIN_OBSIDIAN.getDefaultState());
                                }

                                /*
                                 * Makes:
                                 *       ()
                                 *     ()()()
                                 *   ()()()()()
                                 * ()()()()()()()
                                 *   ()()()()()
                                 *     [][][]
                                 *       ()
                                 * */
                                this.setBlockState(structureWorldAccess, origin.add(1, 1, 2), DEModBlocks.DOMAIN_OBSIDIAN.getDefaultState());
                                for (int k = 0; k < 2; k++) {
                                    this.setBlockState(structureWorldAccess, origin.add(-1, 1 + k, 2), DEModBlocks.DOMAIN_OBSIDIAN.getDefaultState());
                                }
                                for (int k = 0; k < 5; k++) {
                                    this.setBlockState(structureWorldAccess, origin.add(0, 1 + k, 2), DEModBlocks.DOMAIN_OBSIDIAN.getDefaultState());
                                }

                                /*
                                 * Makes:
                                 *       ()
                                 *     ()()()
                                 *   []()()()()
                                 * ()[]()()()()()
                                 *   ()()()()()
                                 *     ()()()
                                 *       ()
                                 * */
                                this.setBlockState(structureWorldAccess, origin.add(-2, 1, -1), DEModBlocks.DOMAIN_OBSIDIAN.getDefaultState());
                                for (int k = 0; k < 3; k++) {
                                    this.setBlockState(structureWorldAccess, origin.add(-2, 1 + k, 0), DEModBlocks.DOMAIN_OBSIDIAN.getDefaultState());
                                }

                                /*
                                 * Makes:
                                 *       ()
                                 *     ()()()
                                 *   ()()()()[]
                                 * ()()()()()[]()
                                 *   ()()()()[]
                                 *     ()()()
                                 *       ()
                                 * */
                                this.setBlockState(structureWorldAccess, origin.add(2, 3, 0), DEModBlocks.DOMAIN_OBSIDIAN.getDefaultState());
                                this.setBlockState(structureWorldAccess, origin.add(2, 1, -1), DEModBlocks.DOMAIN_OBSIDIAN.getDefaultState());
                                for (int j = -1; j <= 0; j++) {
                                    for (int k = 0; k < 2; k++) {
                                        this.setBlockState(structureWorldAccess, origin.add(2, 1 + k, j), DEModBlocks.DOMAIN_OBSIDIAN.getDefaultState());
                                    }
                                }

                                /*
                                 * Makes:
                                 *       ()
                                 *     ()()()
                                 *   ()[]()[]()
                                 * ()()()()()()()
                                 *   ()()()()()
                                 *     ()()()
                                 *       ()
                                 * */
                                this.setBlockState(structureWorldAccess, origin.add(1, 4, -1), DEModBlocks.DOMAIN_OBSIDIAN.getDefaultState());
                                this.setBlockState(structureWorldAccess, origin.add(-1, 4, -1), DEModBlocks.DOMAIN_OBSIDIAN.getDefaultState());

                                /*
                                 * Makes:
                                 *       ()
                                 *     [][]()
                                 *   ()()()()()
                                 * ()()()()()()()
                                 *   ()()()()()
                                 *     ()()()
                                 *       ()
                                 * */
                                for (int k = 0; k < 2; k++) {
                                    this.setBlockState(structureWorldAccess, origin.add(-1, 1 + k, -2), DEModBlocks.DOMAIN_OBSIDIAN.getDefaultState());
                                }
                                for (int k = 0; k < 5; k++) {
                                    this.setBlockState(structureWorldAccess, origin.add(0, 1 + k, -2), DEModBlocks.DOMAIN_OBSIDIAN.getDefaultState());
                                }
                                break;
                            }
                        }
                        break;
                    }
                }
                break;
            }
        }
    }
    private void generateSmallWideBase(StructureWorldAccess structureWorldAccess, BlockPos origin) {

    }

    private void generateSmallThinBase(StructureWorldAccess structureWorldAccess, BlockPos origin) {
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
        for (int i = - 3; i <= 3; i++) {
            for (int j = 0; j <= (3 - Math.abs(i)); j++) {
                this.setBlockState(structureWorldAccess, origin.add(i, 0, -j), DEModBlocks.DOMAIN_OBSIDIAN.getDefaultState());
                this.setBlockState(structureWorldAccess, origin.add(i, 0, j), DEModBlocks.DOMAIN_OBSIDIAN.getDefaultState());
            }
        }

        /*
         * Makes:
         *       ()
         *     ()()()
         *   ()[][][]()
         * ()()[][][]()()
         *   ()[][][]()
         *     ()()()
         *       ()
         * */
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                for (int k = 0; k < 3; k++) {
                    this.setBlockState(structureWorldAccess, origin.add(i, 1 + k, j), DEModBlocks.DOMAIN_OBSIDIAN.getDefaultState());
                }
            }
        }

        /*
         * Makes:
         *       ()
         *     ()()()
         *   ()()[]()()
         * ()()[][][]()()
         *   ()()[]()()
         *     ()()()
         *       ()
         * */
        for (int i = - 1; i <= 1; i++) {
            for (int j = 0; j <= (1 - Math.abs(i)); j++) {
                for (int k = 0; k < 4; k++) {
                    this.setBlockState(structureWorldAccess, origin.add(i, 4 + k, -j), DEModBlocks.DOMAIN_OBSIDIAN.getDefaultState());
                    this.setBlockState(structureWorldAccess, origin.add(i, 4 + k, j), DEModBlocks.DOMAIN_OBSIDIAN.getDefaultState());
                }
            }
        }

        /*
         * Makes:
         *       ()
         *     ()()()
         *   ()()()()()
         * ()()()[]()()()
         *   ()()()()()
         *     ()()()
         *       ()
         * */
        for (int k = 0; k < 3; k++) {
            this.setBlockState(structureWorldAccess, new BlockPos(origin.getX(), origin.getY() + 8 + k, origin.getZ()), Blocks.OBSIDIAN.getDefaultState());
        }


        /*
         * Makes:
         *       ()
         *     ()[]()
         *   ()[][][]()
         * ()[][]][][][]()
         *   ()[][][]()
         *     ()[]()
         *       ()
         * */
        for (int i = - 2; i <= 2; i++) {
            for (int j = 0; j <= (2 - Math.abs(i)); j++) {
                this.setBlockState(structureWorldAccess, origin.add(i, -1, -j), DEModBlocks.DOMAIN_OBSIDIAN.getDefaultState());
                this.setBlockState(structureWorldAccess, origin.add(i, -1, j), DEModBlocks.DOMAIN_OBSIDIAN.getDefaultState());
            }
        }

        /*
         * Makes:
         *       ()
         *     ()()()
         *   ()()[]()()
         * ()()[][][]()()
         *   ()()[]()()
         *     ()()()
         *       ()
         * */
        for (int i = - 1; i <= 1; i++) {
            for (int j = 0; j <= (1 - Math.abs(i)); j++) {
                this.setBlockState(structureWorldAccess, origin.add(i, -2, -j), DEModBlocks.DOMAIN_OBSIDIAN.getDefaultState());
                this.setBlockState(structureWorldAccess, origin.add(i, -2, j), DEModBlocks.DOMAIN_OBSIDIAN.getDefaultState());
            }
        }

        /*
         * Makes:
         *       ()
         *     ()()()
         *   ()()()()()
         * ()()()[]()()()
         *   ()()()()()
         *     ()()()
         *       ()
         * */
        this.setBlockState(structureWorldAccess, origin.add(0, -3, 0), DEModBlocks.DOMAIN_OBSIDIAN.getDefaultState());

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
