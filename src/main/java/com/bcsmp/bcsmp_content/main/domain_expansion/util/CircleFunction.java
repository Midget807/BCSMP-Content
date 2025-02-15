package com.bcsmp.bcsmp_content.main.domain_expansion.util;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.List;

public class CircleFunction {

    public static List<BlockPos> generateCircle(BlockPos origin, int radius) {
        Vec3d originVec = new Vec3d(origin.getX(), origin.getY(), origin.getZ());
        Vec3d pointVec;
        List<BlockPos> circle = new ArrayList<>(List.of());
        for (int x = origin.getX() - radius; x < origin.getX() + radius; x++) {
            for (int y = origin.getZ() - radius; y < origin.getZ() + radius; y++) {
                pointVec = new Vec3d(x - origin.getX(), origin.getY(), y - origin.getY());
                if (checkDistance(pointVec.distanceTo(originVec), radius)) {
                    BlockPos targetPos = new BlockPos(x, origin.getY(), y);
                    circle.add(targetPos);
                }
            }
        }
        return circle;
    }
    public static boolean checkDistance(double distance, int radius) {
        return (int) distance <= radius;
    }
}