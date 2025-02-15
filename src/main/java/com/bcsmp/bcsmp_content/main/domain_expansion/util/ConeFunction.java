package com.bcsmp.bcsmp_content.main.domain_expansion.util;

import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class ConeFunction {
    public static List<List<BlockPos>> generateCone(BlockPos origin, int height, int baseRadius) {
        List<List<BlockPos>> cone = new ArrayList<>(List.of());
        for (float h = 0.5F; h < (height - 0.5); h++) {
            float percentRad = 1 - (h / height);
            int localRadius = (int) percentRad * baseRadius;
            cone.add(CircleFunction.generateCircle(origin, localRadius));
        }
        return cone;
    }
}
