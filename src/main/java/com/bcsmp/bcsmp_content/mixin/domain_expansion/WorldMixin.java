package com.bcsmp.bcsmp_content.mixin.domain_expansion;

import com.bcsmp.bcsmp_content.main.domain_expansion.world.area.ExpansionBox;
import com.bcsmp.bcsmp_content.main.domain_expansion.world.area.ExpansionBoxCollision;
import com.bcsmp.bcsmp_content.main.domain_expansion.world.area.ExpansionBoxListener;
import com.bcsmp.bcsmp_content.main.domain_expansion.world.area.WorldExpansionBoxProvider;
import net.fabricmc.fabric.api.attachment.v1.AttachmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.HashMap;
import java.util.List;

@Mixin(World.class)
public abstract class WorldMixin implements WorldExpansionBoxProvider, ExpansionBoxCollision, WorldAccess, AutoCloseable, AttachmentTarget {
    @Unique
    private HashMap<Vec3d, ExpansionBox> allExpansionBoxes = new HashMap<>();

    @Override
    public ExpansionBox createExpansionBox() {
        final ExpansionBox box = new ExpansionBox();
        /*box.addListener(new ExpansionBoxListener() {
            @Override
            public void onSizeChange(ExpansionBox box, double size) {
                server.getPlayerManager().sendToAll(new ExpansionBoxSizeChangedPacket(box));
            }

            @Override
            public void onInterpolateSize(ExpansionBox box, double fromSize, double toSize, long time) {
                server.getPlayerManager().sendToAll(new ExpansionBoxInterpolateSizePacket(box));
            }

            @Override
            public void onCenterChanged(ExpansionBox box, double centerX, double centerY, double centerZ) {
                server.getPlayerManager().sendToAll(new ExpansionBoxCenterChangedPacket(box));
            }
        });*/
        box.addListener(new ExpansionBoxListener.ExpansionBoxSyncer(box));
        box.load(box.write());

        this.allExpansionBoxes.put(new Vec3d(box.getCenterX(), box.getCenterY(), box.getCenterZ()), box);
        return box;
    }

    @Override
    public ExpansionBox getExpansionBox(Vec3d boxCenter) {
        return this.allExpansionBoxes.get(boxCenter);
    }

    @Override
    public void addExpansionBox(Vec3d boxCenter, ExpansionBox box) {
        this.allExpansionBoxes.put(boxCenter, box);
    }

    @Override
    public void removeExpansionBox(Vec3d boxCenter) {
        this.allExpansionBoxes.remove(boxCenter);
    }

    @Override
    public HashMap<Vec3d, ExpansionBox> getAllExpansionBoxes() {
        return this.allExpansionBoxes;
    }

    @Override
    public void setAllExpansionBoxes(HashMap<Vec3d, ExpansionBox> boxMap) {
        this.allExpansionBoxes = boxMap;
    }

    @Override
    public ExpansionBox getExpansionBox(Entity entity) {
        List<ExpansionBox> list = List.of();
        this.allExpansionBoxes.forEach((vec3d, box) -> list.add(box));
        for (ExpansionBox box : list) {
            if (box.contains(entity.getBlockPos())) {
                return box;
            }
        }
        return null;
    }
}
