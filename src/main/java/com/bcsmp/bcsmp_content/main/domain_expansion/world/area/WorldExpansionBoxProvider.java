package com.bcsmp.bcsmp_content.main.domain_expansion.world.area;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.HashMap;

public interface WorldExpansionBoxProvider {
    ExpansionBox createExpansionBox();

    ExpansionBox getExpansionBox(Vec3d boxCenter);

    void addExpansionBox(Vec3d boxCenter, ExpansionBox box);

    void removeExpansionBox(Vec3d boxCenter);

    HashMap<Vec3d, ExpansionBox> getAllExpansionBoxes();

    void setAllExpansionBoxes(HashMap<Vec3d, ExpansionBox> boxMap);
}
