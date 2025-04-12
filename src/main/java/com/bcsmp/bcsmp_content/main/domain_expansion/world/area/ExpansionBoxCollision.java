package com.bcsmp.bcsmp_content.main.domain_expansion.world.area;

import net.minecraft.entity.Entity;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.Box;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

public interface ExpansionBoxCollision extends BlockView {

    ExpansionBox getExpansionBox(Entity entity);

    default boolean isInExpansionBox(@Nullable Entity entity, Box box) {
        if (entity == null) {
            return false;
        } else {
            VoxelShape expansionBoxShape = this.getExpansionBoxCollision(entity, box);
            return expansionBoxShape != null || VoxelShapes.matchesAnywhere(expansionBoxShape, VoxelShapes.cuboid(box), BooleanBiFunction.AND);
        }
    }
    @Nullable
    private VoxelShape getExpansionBoxCollision(@Nullable Entity entity, Box box) {
        ExpansionBox expansionBox = this.getExpansionBox(entity);
        return expansionBox.canCollide(entity, box) ? expansionBox.asVoxelShape() : null;
    }
}
