package com.bcsmp.bcsmp_content.main.domain_expansion.world.area;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;

public class ExpansionSelectionFlash {
    public Area area;
    public final PlayerEntity player;
    private double centerX;
    private double centerZ;
    int maxRadius = 29999;

    public ExpansionSelectionFlash(PlayerEntity player) {
        this.player = player;
        this.area = new StaticArea((player.experienceLevel + 10) * 2 + 1);
    }
    public VoxelShape asVoxelShape() {
        return this.area.asVoxelShape();
    }

    public double getCenterX() {
        return this.centerX;
    }

    public double getCenterZ() {
        return this.centerZ;
    }

    //Careful of null instance
    public PlayerEntity getPlayer() {
        return player;
    }
    public void setCenter(double x, double z) {
        this.centerX = x;
        this.centerZ = z;
        this.area.onCenterChanged();
    }
    public double getBoundWest() {
        return this.area.getBoundWest();
    }
    public double getBoundEast() {
        return this.area.getBoundEast();
    }
    public double getBoundNorth() {
        return this.area.getBoundNorth();
    }
    public double getBoundSouth() {
        return this.area.getBoundSouth();
    }
    public double getDistanceInArea(double x, double z) {
        double d = z - this.getBoundNorth();
        double e = this.getBoundSouth() - z;
        double f = x - this.getBoundWest();
        double g = this.getBoundEast() - x;
        double h = Math.min(f, g);
        h = Math.min(h, d);
        return Math.min(h, e);
    }

    public class StaticArea implements Area {
        private final int size;
        private double boundWest;
        private double boundNorth;
        private double boundEast;
        private double boundSouth;
        private VoxelShape shape;
        public StaticArea(final int size) {
            this.size = size;
            this.recalculateBounds();
        }

        private void recalculateBounds() {
            this.boundWest = MathHelper.clamp(ExpansionSelectionFlash.this.getCenterX() - this.size / 2.0, (double)(-ExpansionSelectionFlash.this.maxRadius), (double)ExpansionSelectionFlash.this.maxRadius);
            this.boundNorth = MathHelper.clamp(
                    ExpansionSelectionFlash.this.getCenterZ() - this.size / 2.0, (double)(-ExpansionSelectionFlash.this.maxRadius), (double)ExpansionSelectionFlash.this.maxRadius
            );
            this.boundEast = MathHelper.clamp(ExpansionSelectionFlash.this.getCenterX() + this.size / 2.0, (double)(-ExpansionSelectionFlash.this.maxRadius), (double)ExpansionSelectionFlash.this.maxRadius);
            this.boundSouth = MathHelper.clamp(
                    ExpansionSelectionFlash.this.getCenterZ() + this.size / 2.0, (double)(-ExpansionSelectionFlash.this.maxRadius), (double)ExpansionSelectionFlash.this.maxRadius
            );
            this.shape = VoxelShapes.combineAndSimplify(
                    VoxelShapes.UNBOUNDED,
                    VoxelShapes.cuboid(
                            Math.floor(this.getBoundWest()),
                            Double.NEGATIVE_INFINITY,
                            Math.floor(this.getBoundNorth()),
                            Math.ceil(this.getBoundEast()),
                            Double.POSITIVE_INFINITY,
                            Math.ceil(this.getBoundSouth())
                    ),
                    BooleanBiFunction.ONLY_FIRST
            );
        }

        @Override
        public double getBoundWest() {
            return this.boundWest;
        }

        @Override
        public double getBoundEast() {
            return this.boundEast;
        }

        @Override
        public double getBoundNorth() {
            return this.boundNorth;
        }

        @Override
        public double getBoundSouth() {
            return this.boundSouth;
        }

        @Override
        public double getSize() {
            return this.size;
        }

        @Override
        public Area getAreaInstance() {
            return this;
        }

        @Override
        public VoxelShape asVoxelShape() {
            return this.shape;
        }

        @Override
        public void onCenterChanged() {
            this.recalculateBounds();
        }
    }
    static interface Area {
        double getBoundWest();

        double getBoundEast();

        double getBoundNorth();

        double getBoundSouth();

        double getSize();

        ExpansionSelectionFlash.Area getAreaInstance();

        VoxelShape asVoxelShape();
        void onCenterChanged();
    }
}
