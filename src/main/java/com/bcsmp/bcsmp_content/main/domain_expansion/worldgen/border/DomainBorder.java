package com.bcsmp.bcsmp_content.main.domain_expansion.worldgen.border;

import com.google.common.collect.Lists;
import com.mojang.serialization.DynamicLike;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Util;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.World;
import net.minecraft.world.border.WorldBorderStage;

import java.util.List;

public class DomainBorder implements DomainBorderWithWorld {
    public World world;
    private final List<DomainBorderListener> listeners = Lists.<DomainBorderListener>newArrayList();
    private double damagePerBlock = 0.5;
    private double safeZone = 3.0;
    private int warningTime = 15;
    private int warningBlocks = 3;
    public double centerX;
    public double centerZ;
    int maxRadius = 29999984;
    public DomainBorder.Area area = new DomainBorder.StaticArea(10);
    public static final DomainBorder.Properties DEFAULT_BORDER = new Properties(0.0, 0.0, 0.5, 3.0, 3, 15, 20, 0L, 0.0);

    @Override
    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public boolean canCollide(Entity entity, Box box) {
        double d = Math.max(MathHelper.absMax(box.getXLength(), box.getZLength()), 1.0);
        return this.getDistanceInsideBorder(entity) < d * 2.0 && this.contains(entity.getX(), entity.getZ(), d);
    }

    public boolean contains(BlockPos pos) {
        return (double)(pos.getX() + 1) > this.getBoundWest()
                && (double)pos.getX() < this.getBoundEast()
                && (double)(pos.getZ() + 1) > this.getBoundNorth()
                && (double)pos.getZ() < this.getBoundSouth();
    }

    public boolean contains(ChunkPos pos) {
        return (double)pos.getEndX() > this.getBoundWest()
                && (double)pos.getStartX() < this.getBoundEast()
                && (double)pos.getEndZ() > this.getBoundNorth()
                && (double)pos.getStartZ() < this.getBoundSouth();
    }

    public boolean contains(double x, double z) {
        return x > this.getBoundWest() && x < this.getBoundEast() && z > this.getBoundNorth() && z < this.getBoundSouth();
    }
    public boolean contains(double x, double z, double margin) {
        return x > this.getBoundWest() - margin && x < this.getBoundEast() + margin && z > this.getBoundNorth() - margin && z < this.getBoundSouth() + margin;
    }
    public boolean contains(Box box) {
        return box.maxX > this.getBoundWest() && box.minX < this.getBoundEast() && box.maxZ > this.getBoundNorth() && box.minZ < this.getBoundSouth();
    }

    public double getDistanceInsideBorder(Entity entity) {
        return this.getDistanceInsideBorder(entity.getX(), entity.getZ());
    }

    public double getDistanceInsideBorder(double x, double z) {
        double d = z - this.getBoundNorth();
        double e = this.getBoundSouth() - z;
        double f = x - this.getBoundWest();
        double g = this.getBoundEast() - x;
        double h = Math.min(f, g);
        h = Math.min(h, d);
        return Math.min(h, e);
    }

    public double getBoundEast() {
        return area.getBoundEast();
    }

    public double getBoundWest() {
        return area.getBoundWest();
    }

    public double getBoundSouth() {
        return area.getBoundSouth();
    }

    public double getBoundNorth() {
        return area.getBoundNorth();
    }

    public VoxelShape asVoxelShape() {
        return area.asVoxelShape();
    }

    public double getCenterX() {
        return centerX;
    }

    public double getCenterZ() {
        return centerZ;
    }

    public void setCenter(double x, double z) {
        this.centerX = x;
        this.centerZ = z;
        area.onCenterChanged();
    }
    public double getSize() {
        return area.getSize();
    }
    public long getSizeLerpTime() {
        return area.getSizeLerpTime();
    }
    public double getSizeLerpTarget() {
        return area.getSizeLerpTarget();
    }
    public void setSize(double size) {
        area = new DomainBorder.StaticArea(size);
        for (DomainBorderListener domainBorderListener : this.getListeners()) {
            domainBorderListener.onSizeChange(this, size);
        }
    }

    public void interpolateSize(double fromSize, double toSize, long time) {
        area = (DomainBorder.Area)(fromSize == toSize? new DomainBorder.StaticArea(toSize) : new DomainBorder.MovingArea(fromSize, toSize, time));

        for (DomainBorderListener domainBorderListener : this.getListeners()) {
            domainBorderListener.onInterpolateSize(this, fromSize, toSize, time);
        }
    }

    public List<DomainBorderListener> getListeners() {
        return Lists.newArrayList(this.listeners);
    }
    public void addListener(DomainBorderListener listener) {
        this.listeners.add(listener);
    }
    public void removeListener(DomainBorderListener listener) {
        this.listeners.remove(listener);
    }
    public void setMaxRadius(int maxRadius) {
        this.maxRadius = maxRadius;
        area.onMaxRadiusChanged();
    }

    public int getMaxRadius() {
        return this.maxRadius;
    }

    public double getSafeZone() {
        return this.safeZone;
    }
    public void setSafeZone(double safeZone) {
        this.safeZone = safeZone;

        for (DomainBorderListener domainBorderListener : this.getListeners()) {
            domainBorderListener.onSafeZoneChanged(this, safeZone);
        }
    }

    public double getDamagePerBlock() {
        return this.damagePerBlock;
    }

    public void setDamagePerBlock(double damagePerBlock) {
        this.damagePerBlock = damagePerBlock;

        for (DomainBorderListener domainBorderListener : this.getListeners()) {
            domainBorderListener.onDamagePerBlockChanged(this, damagePerBlock);
        }
    }
    public double getShrinkingSpeed() {
        return area.getShrinkingSpeed();
    }

    public int getWarningTime() {
        return this.warningTime;
    }

    public void setWarningTime(int warningTime) {
        this.warningTime = warningTime;

        for (DomainBorderListener domainBorderListener : this.getListeners()) {
            domainBorderListener.onWarningTimeChanged(this, warningTime);
        }
    }
    public int getWarningBlocks() {
        return this.warningBlocks;
    }

    public void setWarningBlocks(int warningBlocks) {
        this.warningBlocks = warningBlocks;

        for (DomainBorderListener domainBorderListener : this.getListeners()) {
            domainBorderListener.onWarningBlocksChanged(this, warningBlocks);
        }
    }
    public DomainBorderStage getStage() {
        return area.getStage();
    }

    public void tick() {
        area = area.getAreaInstance();
    }
    public DomainBorder.Properties write() {
        return new DomainBorder.Properties(this);
    }
    public void load(DomainBorder.Properties properties) {
        this.setCenter(properties.getCenterX(), properties.getCenterZ());
        this.setDamagePerBlock(properties.getDamagePerBlock());
        this.setSafeZone(properties.getSafeZone());
        this.setWarningBlocks(properties.getWarningBlocks());
        this.setWarningTime(properties.getWarningTime());
        if (properties.getSizeLerpTime() > 0L) {
            this.interpolateSize(properties.getSize(), properties.getSizeLerpTarget(), properties.getSizeLerpTime());
        } else {
            this.setSize(properties.getSize());
        }
    }
    public static class Properties {
        private final double centerX;
        private final double centerZ;
        private final double damagePerBlock;
        private final double safeZone;
        private final int warningBlocks;
        private final int warningTime;
        private final double size;
        private final long sizeLerpTime;
        private final double sizeLerpTarget;

        Properties(
                double centerX,
                double centerZ,
                double damagePerBlock,
                double safeZone,
                int warningBlocks,
                int warningTime,
                double size,
                long sizeLerpTime,
                double sizeLerpTarget
        ) {
            this.centerX = centerX;
            this.centerZ = centerZ;
            this.damagePerBlock = damagePerBlock;
            this.safeZone = safeZone;
            this.warningBlocks = warningBlocks;
            this.warningTime = warningTime;
            this.size = size;
            this.sizeLerpTime = sizeLerpTime;
            this.sizeLerpTarget = sizeLerpTarget;
        }

        Properties(DomainBorder domainBorder) {
            this.centerX = domainBorder.getCenterX();
            this.centerZ = domainBorder.getCenterZ();
            this.damagePerBlock = domainBorder.getDamagePerBlock();
            this.safeZone = domainBorder.getSafeZone();
            this.warningBlocks = domainBorder.getWarningBlocks();
            this.warningTime = domainBorder.getWarningTime();
            this.size = domainBorder.getSize();
            this.sizeLerpTime = domainBorder.getSizeLerpTime();
            this.sizeLerpTarget = domainBorder.getSizeLerpTarget();
        }

        public double getCenterX() {
            return this.centerX;
        }

        public double getCenterZ() {
            return this.centerZ;
        }

        public double getDamagePerBlock() {
            return this.damagePerBlock;
        }

        public double getSafeZone() {
            return this.safeZone;
        }

        public int getWarningBlocks() {
            return this.warningBlocks;
        }

        public int getWarningTime() {
            return this.warningTime;
        }

        public double getSize() {
            return this.size;
        }

        public long getSizeLerpTime() {
            return this.sizeLerpTime;
        }

        public double getSizeLerpTarget() {
            return this.sizeLerpTarget;
        }

        public static DomainBorder.Properties fromDynamic(DynamicLike<?> dynamic, DomainBorder.Properties properties) {
            double d = MathHelper.clamp(dynamic.get("BorderCenterX").asDouble(properties.centerX), -2.9999984E7, 2.9999984E7);
            double e = MathHelper.clamp(dynamic.get("BorderCenterZ").asDouble(properties.centerZ), -2.9999984E7, 2.9999984E7);
            double f = dynamic.get("BorderSize").asDouble(properties.size);
            long l = dynamic.get("BorderSizeLerpTime").asLong(properties.sizeLerpTime);
            double g = dynamic.get("BorderSizeLerpTarget").asDouble(properties.sizeLerpTarget);
            double h = dynamic.get("BorderSafeZone").asDouble(properties.safeZone);
            double i = dynamic.get("BorderDamagePerBlock").asDouble(properties.damagePerBlock);
            int j = dynamic.get("BorderWarningBlocks").asInt(properties.warningBlocks);
            int k = dynamic.get("BorderWarningTime").asInt(properties.warningTime);
            return new DomainBorder.Properties(d, e, i, h, j, k, f, l, g);
        }

        public void writeNbt(NbtCompound nbt) {
            nbt.putDouble("BorderCenterX", this.centerX);
            nbt.putDouble("BorderCenterZ", this.centerZ);
            nbt.putDouble("BorderSize", this.size);
            nbt.putLong("BorderSizeLerpTime", this.sizeLerpTime);
            nbt.putDouble("BorderSafeZone", this.safeZone);
            nbt.putDouble("BorderDamagePerBlock", this.damagePerBlock);
            nbt.putDouble("BorderSizeLerpTarget", this.sizeLerpTarget);
            nbt.putDouble("BorderWarningBlocks", (double)this.warningBlocks);
            nbt.putDouble("BorderWarningTime", (double)this.warningTime);
        }
    }
    public class MovingArea implements Area {
        private final double oldSize;
        private final double newSize;
        private final long timeEnd;
        private final long timeStart;
        private final double timeDuration;

        MovingArea(double oldSize, double newSize, long timeDuration) {
            this.oldSize = oldSize;
            this.newSize = newSize;
            this.timeDuration = (double)timeDuration;
            this.timeStart = Util.getMeasuringTimeMs();
            this.timeEnd = this.timeStart + timeDuration;
        }

        @Override
        public double getBoundWest() {
            return MathHelper.clamp(DomainBorder.this.getCenterX() - this.getSize() / 2.0, (double)(-DomainBorder.this.maxRadius), (double)DomainBorder.this.maxRadius);
        }

        @Override
        public double getBoundNorth() {
            return MathHelper.clamp(DomainBorder.this.getCenterZ() - this.getSize() / 2.0, (double)(-DomainBorder.this.maxRadius), (double)DomainBorder.this.maxRadius);
        }

        @Override
        public double getBoundEast() {
            return MathHelper.clamp(DomainBorder.this.getCenterX() + this.getSize() / 2.0, (double)(-DomainBorder.this.maxRadius), (double)DomainBorder.this.maxRadius);
        }

        @Override
        public double getBoundSouth() {
            return MathHelper.clamp(DomainBorder.this.getCenterZ() + this.getSize() / 2.0, (double)(-DomainBorder.this.maxRadius), (double)DomainBorder.this.maxRadius);
        }

        @Override
        public double getSize() {
            double d = (double)(Util.getMeasuringTimeMs() - this.timeStart) / this.timeDuration;
            return d < 1.0 ? MathHelper.lerp(d, this.oldSize, this.newSize) : this.newSize;
        }

        @Override
        public double getShrinkingSpeed() {
            return Math.abs(this.oldSize - this.newSize) / (double)(this.timeEnd - this.timeStart);
        }

        @Override
        public long getSizeLerpTime() {
            return this.timeEnd - Util.getMeasuringTimeMs();
        }

        @Override
        public double getSizeLerpTarget() {
            return this.newSize;
        }

        @Override
        public DomainBorderStage getStage() {
            return this.newSize < this.oldSize ? DomainBorderStage.SHRINKING : DomainBorderStage.GROWING;
        }

        @Override
        public void onCenterChanged() {
        }

        @Override
        public void onMaxRadiusChanged() {
        }

        @Override
        public DomainBorder.Area getAreaInstance() {
            return (DomainBorder.Area)(this.getSizeLerpTime() <= 0L ? DomainBorder.this.new StaticArea(this.newSize) : this);
        }

        @Override
        public VoxelShape asVoxelShape() {
            return VoxelShapes.combineAndSimplify(
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
    }

    public class StaticArea implements Area {
        public final double size;
        public double boundWest;
        public double boundNorth;
        public double boundEast;
        public double boundSouth;
        public VoxelShape shape;
        public StaticArea(double size) {
            this.size = size;
            this.recalculateBounds();
        }

        private void recalculateBounds() {

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
        public double getShrinkingSpeed() {
            return 0.0;
        }

        @Override
        public long getSizeLerpTime() {
            return 0L;
        }

        @Override
        public double getSizeLerpTarget() {
            return this.size;
        }

        @Override
        public DomainBorderStage getStage() {
            return DomainBorderStage.STATIONARY;
        }

        @Override
        public void onMaxRadiusChanged() {
            this.recalculateBounds();
        }

        @Override
        public void onCenterChanged() {
            this.recalculateBounds();
        }

        @Override
        public Area getAreaInstance() {
            return this;
        }

        @Override
        public VoxelShape asVoxelShape() {
            return this.shape;
        }
    }

    public interface Area {
        double getBoundWest();

        double getBoundEast();

        double getBoundNorth();

        double getBoundSouth();

        double getSize();

        double getShrinkingSpeed();

        long getSizeLerpTime();

        double getSizeLerpTarget();

        DomainBorderStage getStage();

        void onMaxRadiusChanged();

        void onCenterChanged();

        DomainBorder.Area getAreaInstance();

        VoxelShape asVoxelShape();
    }

}
