package com.bcsmp.bcsmp_content.main.domain_expansion.world.area;

import com.google.common.collect.Lists;
import com.mojang.serialization.DynamicLike;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Util;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;

import java.util.List;

/*
 * SOUTH -> pos Z
 * NORTH -> neg Z
 * EAST -> pos X
 * WEST -> neg X
 * */
public class ExpansionBox {
    public static final double STATIC_AREA_SIZE = 20;
    public static final double MAX_CENTER_COORDINATES = 2.9999984E7;
    private final List<ExpansionBoxListener> listeners = Lists.<ExpansionBoxListener>newArrayList();
    private double damagePerBlock = 0.0;
    private double safeZone = 0.0;
    private int warningTime = 0;
    private int warningBlocks = 0;
    private double centerX;
    private double centerY;
    private double centerZ;
    int maxRadius = 100;
    private ExpansionBox.Area area = new ExpansionBox.StaticArea(STATIC_AREA_SIZE);
    public static final ExpansionBox.Properties DEFAULT_AREA = new Properties(0.0, 0.0, 0.0, 0, 0, 0, 0, STATIC_AREA_SIZE, 0L, 0.0);

    public double getDistanceInsideBorder(Entity entity) {
        return this.getDistanceInsideBorder(entity.getX(), entity.getY(), entity.getZ());
    }
    /**
     * Tests to see if each coordinate component is within the bounds of their respective limiting axis.
     * E.g. {@code yMoreDown} checks if the entity y-coordinate is greater than {@link #getBoundDown()} bound.
     * If so, it means the entity is within the bounds of the border.
     * <br>
     * {@code test} provides the smallest from a bound within the x-axis.
     * E.g. if the entity is within the eastern half of the box, the x distance is the distance to the east boundary.
     * The x distance is then compared to each half along the other axes to get the min value from a boundary.
    */
    public double getDistanceInsideBorder(double x, double y, double z) {
        double yMoreDown = y - this.getBoundDown();
        double yLessUp = this.getBoundUp() - y;
        double zMoreNorth = z - this.getBoundNorth();
        double zLessSouth = this.getBoundSouth() - z;
        double xMoreWest = x - this.getBoundWest();
        double xLessEast = this.getBoundEast() - x;
        double test = Math.min(xMoreWest, xLessEast);
        test = Math.min(test, zMoreNorth);
        test = Math.min(test, zLessSouth);
        test = Math.min(test, yMoreDown);
        return Math.min(test, yLessUp);
    }

    public VoxelShape asVoxelShape() {
        return this.area.asVoxelShape();
    }

    public double getBoundDown() {
        return this.area.getBoundDown();
    }
    public double getBoundUp() {
        return this.area.getBoundUp();
    }
    public double getBoundNorth() {
        return this.area.getBoundNorth();
    }
    public double getBoundSouth() {
        return this.area.getBoundSouth();
    }
    public double getBoundWest() {
        return this.area.getBoundWest();
    }
    public double getBoundEast() {
        return this.area.getBoundEast();
    }

    public double getCenterX() {
        return centerX;
    }
    public double getCenterY() {
        return centerY;
    }
    public double getCenterZ() {
        return centerZ;
    }

    public void setCenter(double x, double y, double z) {
        this.centerX = x;
        this.centerY = y;
        this.centerZ = z;

        /*for (ExpansionBoxListener expansionBoxListener : this.getListeners()) {
            expansionBoxListener.onCenterChanged(this, x, y, z);
        }*/
    }

    /**
     * Should be double the radius
     */
    public double getSize() {
        return this.area.getSize();
    }

    public long getSizeLerpTime() {
        return this.area.getSizeLerpTime();
    }

    public double getSizeLerpTarget() {
        return this.area.getLerpTargetSize();
    }

    public void setSize(double size) {
        this.area = new ExpansionBox.StaticArea(size);

        /*for (ExpansionBoxListener expansionBoxListener : this.getListeners()) {
            expansionBoxListener.onSizeChange(this, size);
        }*/
    }

    public void interpolateSize(double fromSize, double toSize, long time) {
        this.area = (ExpansionBox.Area)(fromSize == toSize ? new ExpansionBox.StaticArea(toSize) : new ExpansionBox.MovingArea(fromSize, toSize, time));

        /*for (ExpansionBoxListener expansionBoxListener : this.getListeners()) {
            expansionBoxListener.onInterpolateSize(this, fromSize, toSize, time);
        }*/
    }

    public List<ExpansionBoxListener> getListeners() {
        return Lists.<ExpansionBoxListener>newArrayList(this.listeners);
    }
    public void addListener(ExpansionBoxListener listener) {
        this.listeners.add(listener);
    }
    public void removeListener(ExpansionBoxListener listener) {
        this.listeners.remove(listener);
    }

    public void setMaxRadius(int maxRadius) {
        this.maxRadius = maxRadius;
        this.area.onMaxRadiusChanged();
    }

    public int getMaxRadius() {
        return this.maxRadius;
    }

    public double getSafeZone() {
        return this.safeZone;
    }
    public void setSafeZone(double safeZone) {
        this.safeZone = safeZone;

        //todo addForListeners
    }

    public double getDamagePerBlock() {
        return this.damagePerBlock;
    }
    public void setDamagePerBlock(double damagePerBlock) {
        this.damagePerBlock = damagePerBlock;

        //todo addForListeners
    }

    public double getShrinkSpeed() {
        return this.area.getShrinkSpeed();
    }

    public int getWarningTime() {
        return this.warningTime;
    }

    public void setWarningTime(int warningTime) {
        this.warningTime = warningTime;

        //todo addForListeners
    }

    public int getWarningBlocks() {
        return this.warningBlocks;
    }
    public void setWarningBlocks(int warningBlocks) {
        this.warningBlocks = warningBlocks;
    }

    public void tick() {
        this.area = this.area.getAreaInstance();
    }

    public ExpansionBox.Properties write() {
        return new Properties(this);
    }

    public void load(ExpansionBox.Properties properties) {
        this.setCenter(properties.getCenterX(), properties.getCenterY(), properties.getCenterZ());

        if (properties.getSizeLerpTime() > 0L) {
            this.interpolateSize(properties.getSize(), properties.getSizeLerpTarget(), properties.getSizeLerpTime());
        } else {
            this.setSize(properties.getSize());
        }
        //this.setDamagePerBlock(properties.getDamagePerBlock());
        //this.setSafeZone(properties.getSafeZone());
        //this.setWarningBlocks(properties.getWarningBlocks());
        //this.setWarningTime(properties.getWarningTime());
    }

    public class MovingArea implements ExpansionBox.Area {
        private final double oldSize;
        private final double newSize;
        private final long timeEnd;
        private final long timeStart;
        private final double timeDuration;

        public MovingArea(final double oldSize, final double newSize, final long timeDuration) {
            this.oldSize = oldSize;
            this.newSize = newSize;
            this.timeDuration = timeDuration;
            this.timeStart = Util.getMeasuringTimeMs();
            this.timeEnd = this.timeStart + timeDuration;
        }

        @Override
        public double getBoundUp() {
            return MathHelper.clamp(ExpansionBox.this.getCenterY() + this.getSize() / 2.0, (double)(-ExpansionBox.this.maxRadius), (double)(ExpansionBox.this.maxRadius));
        }

        @Override
        public double getBoundDown() {
            return MathHelper.clamp(ExpansionBox.this.getCenterY() - this.getSize() / 2.0, (double)(-ExpansionBox.this.maxRadius), (double)(ExpansionBox.this.maxRadius));
        }

        @Override
        public double getBoundNorth() {
            return MathHelper.clamp(ExpansionBox.this.getCenterZ() - this.getSize() / 2.0, (double)(-ExpansionBox.this.maxRadius), (double)(ExpansionBox.this.maxRadius));
        }

        @Override
        public double getBoundSouth() {
            return MathHelper.clamp(ExpansionBox.this.getCenterZ() + this.getSize() / 2.0, (double)(-ExpansionBox.this.maxRadius), (double)(ExpansionBox.this.maxRadius));
        }

        @Override
        public double getBoundWest() {
            return MathHelper.clamp(ExpansionBox.this.getCenterX() - this.getSize() / 2.0, (double)(-ExpansionBox.this.maxRadius), (double)(ExpansionBox.this.maxRadius));
        }

        @Override
        public double getBoundEast() {
            return MathHelper.clamp(ExpansionBox.this.getCenterY() + this.getSize() / 2.0, (double)(-ExpansionBox.this.maxRadius), (double)(ExpansionBox.this.maxRadius));
        }

        @Override
        public double getSize() {
            double d = (Util.getMeasuringTimeMs() - this.timeStart) / this.timeDuration;
            return d < 1.0 ? MathHelper.lerp(d, this.oldSize, this.newSize) : this.newSize;
        }

        @Override
        public double getShrinkSpeed() {
            return Math.abs(this.oldSize - this.newSize) / (this.timeEnd - this.timeStart);
        }

        @Override
        public long getSizeLerpTime() {
            return this.timeEnd - Util.getMeasuringTimeMs();
        }

        @Override
        public double getLerpTargetSize() {
            return this.newSize;
        }

        @Override
        public void onMaxRadiusChanged() {

        }

        @Override
        public void onCenterChanged() {

        }

        @Override
        public Area getAreaInstance() {
            return (ExpansionBox.Area)(this.getSizeLerpTime() <= 0L ? ExpansionBox.this.new StaticArea(this.newSize) : this);
        }

        @Override
        public VoxelShape asVoxelShape() {
            return VoxelShapes.combineAndSimplify(
                    VoxelShapes.cuboid(
                            -ExpansionBox.this.maxRadius,
                            -ExpansionBox.this.maxRadius,
                            -ExpansionBox.this.maxRadius,
                            ExpansionBox.this.maxRadius,
                            ExpansionBox.this.maxRadius,
                            ExpansionBox.this.maxRadius
                    ),
                    VoxelShapes.cuboid(
                            Math.floor(this.getBoundWest()),
                            Math.floor(this.getBoundDown()),
                            Math.floor(this.getBoundNorth()),
                            Math.floor(this.getBoundEast()),
                            Math.floor(this.getBoundUp()),
                            Math.floor(this.getBoundSouth())
                    ),
                    BooleanBiFunction.ONLY_FIRST
            );
        }
    }
    public class StaticArea implements ExpansionBox.Area {
        private final double size;
        private double boundUp;
        private double boundDown;
        private double boundWest;
        private double boundNorth;
        private double boundEast;
        private double boundSouth;
        private VoxelShape shape;

        public StaticArea(double size) {
            this.size = size;
            this.recalculateBounds();
        }

        private void recalculateBounds() {
            this.boundUp = MathHelper.clamp(ExpansionBox.this.getCenterY() + this.size / 2.0, (double)(-ExpansionBox.this.maxRadius), (double)(ExpansionBox.this.maxRadius));
            this.boundDown = MathHelper.clamp(ExpansionBox.this.getCenterY() - this.size / 2.0, (double)(-ExpansionBox.this.maxRadius), (double)(ExpansionBox.this.maxRadius));
            this.boundWest = MathHelper.clamp(ExpansionBox.this.getCenterX() - this.size / 2.0, (double)(-ExpansionBox.this.maxRadius), (double)(ExpansionBox.this.maxRadius));
            this.boundEast = MathHelper.clamp(ExpansionBox.this.getCenterX() + this.size / 2.0, (double)(-ExpansionBox.this.maxRadius), (double)(ExpansionBox.this.maxRadius));
            this.boundNorth = MathHelper.clamp(ExpansionBox.this.getCenterZ() - this.size / 2.0, (double)(-ExpansionBox.this.maxRadius), (double)(ExpansionBox.this.maxRadius));
            this.boundSouth = MathHelper.clamp(ExpansionBox.this.getCenterZ() + this.size / 2.0, (double)(-ExpansionBox.this.maxRadius), (double)(ExpansionBox.this.maxRadius));
            this.shape = VoxelShapes.combineAndSimplify(
                    VoxelShapes.cuboid(
                            -ExpansionBox.this.maxRadius,
                            -ExpansionBox.this.maxRadius,
                            -ExpansionBox.this.maxRadius,
                            ExpansionBox.this.maxRadius,
                            ExpansionBox.this.maxRadius,
                            ExpansionBox.this.maxRadius
                    ),
                    VoxelShapes.cuboid(
                            Math.floor(this.getBoundWest()),
                            Math.floor(this.getBoundDown()),
                            Math.floor(this.getBoundNorth()),
                            Math.floor(this.getBoundEast()),
                            Math.floor(this.getBoundUp()),
                            Math.floor(this.getBoundSouth())
                    ),
                    BooleanBiFunction.ONLY_FIRST
            );
        }

        @Override
        public double getBoundUp() {
            return this.boundUp;
        }

        @Override
        public double getBoundDown() {
            return this.boundDown;
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
        public double getBoundWest() {
            return this.boundWest;
        }

        @Override
        public double getBoundEast() {
            return this.boundEast;
        }

        @Override
        public double getSize() {
            return this.size;
        }

        @Override
        public double getShrinkSpeed() {
            return 0.0;
        }

        @Override
        public long getSizeLerpTime() {
            return 0L;
        }

        @Override
        public double getLerpTargetSize() {
            return this.size;
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
        double getBoundUp();
        double getBoundDown();
        double getBoundNorth();
        double getBoundSouth();
        double getBoundWest();
        double getBoundEast();
        double getSize();
        double getShrinkSpeed();
        long getSizeLerpTime();
        double getLerpTargetSize();
        void onMaxRadiusChanged();
        void onCenterChanged();
        ExpansionBox.Area getAreaInstance();
        VoxelShape asVoxelShape();
    }
    public static class Properties {
        private final double centerX;
        private final double centerY;
        private final double centerZ;
        private final double damagePerBlock;
        private final double safeZone;
        private final int warningBlocks;
        private final int warningTime;
        private final double size;
        private final long sizeLerpTime;
        private final double sizeLerpTarget;

        public Properties(double centerX, double centerY, double centerZ, double damagePerBlock, double safeZone, int warningBlocks, int warningTime, double size, long sizeLerpTime, double sizeLerpTarget) {
            this.centerX = centerX;
            this.centerY = centerY;
            this.centerZ = centerZ;
            this.size = size;
            this.sizeLerpTime = sizeLerpTime;
            this.sizeLerpTarget = sizeLerpTarget;
            /*zero these*/
            this.damagePerBlock = damagePerBlock;
            this.safeZone = safeZone;
            this.warningBlocks = warningBlocks;
            this.warningTime = warningTime;
        }
        public Properties(ExpansionBox expansionBox) {
            this.centerX = expansionBox.getCenterX();
            this.centerY = expansionBox.getCenterY();
            this.centerZ = expansionBox.getCenterZ();
            this.size = expansionBox.getSize();
            this.sizeLerpTime = expansionBox.getSizeLerpTime();
            this.sizeLerpTarget = expansionBox.getSizeLerpTarget();
            /*these should be zero*/
            this.damagePerBlock = expansionBox.getDamagePerBlock();
            this.safeZone = expansionBox.getSafeZone();
            this.warningBlocks = expansionBox.getWarningBlocks();
            this.warningTime = expansionBox.getWarningTime();
        }

        public double getCenterX() {
            return this.centerX;
        }

        public double getCenterY() {
            return this.centerY;
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

        public static ExpansionBox.Properties fromDynamic(DynamicLike<?> dynamic, ExpansionBox.Properties properties) {
            double c = MathHelper.clamp(dynamic.get("BorderCenterX").asDouble(properties.centerX), -MAX_CENTER_COORDINATES, MAX_CENTER_COORDINATES);
            double d = MathHelper.clamp(dynamic.get("BorderCenterY").asDouble(properties.centerY), -MAX_CENTER_COORDINATES, MAX_CENTER_COORDINATES);
            double e = MathHelper.clamp(dynamic.get("BorderCenterZ").asDouble(properties.centerZ), -MAX_CENTER_COORDINATES, MAX_CENTER_COORDINATES);
            double f = dynamic.get("BorderSize").asDouble(properties.size);
            long l = dynamic.get("BorderSizeLerpTime").asLong(properties.sizeLerpTime);
            double g = dynamic.get("BorderSizeLerpTarget").asDouble(properties.sizeLerpTarget);
            double h = dynamic.get("BorderSafeZone").asDouble(properties.safeZone);
            double i = dynamic.get("BorderDamagePerBlock").asDouble(properties.damagePerBlock);
            int j = dynamic.get("BorderWarningBlocks").asInt(properties.warningBlocks);
            int k = dynamic.get("BorderWarningTime").asInt(properties.warningTime);
            return new ExpansionBox.Properties(c, d, e, i, h, j, k, f, l, g);
        }
        public void writeNbt(NbtCompound nbt) {
            nbt.putDouble("BorderCenterX", this.centerX);
            nbt.putDouble("BorderCenterY", this.centerY);
            nbt.putDouble("BorderCenterZ", this.centerZ);
            nbt.putDouble("BorderSize", this.size);
            nbt.putLong("BorderSizeLerpTime", this.sizeLerpTime);
            nbt.putDouble("BorderSafeZone", this.safeZone);
            nbt.putDouble("BorderDamagePerBlock", this.damagePerBlock);
            nbt.putDouble("BorderSizeLerpTarget", this.sizeLerpTarget);
            nbt.putDouble("BorderWarningBlocks", this.warningBlocks);
            nbt.putDouble("BorderWarningTime", this.warningTime);
        }
    }
}
