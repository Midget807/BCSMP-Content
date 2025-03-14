package com.bcsmp.bcsmp_content.main.domain_expansion.worldgen.border;

import net.minecraft.world.World;

public interface DomainBorderWithWorld {
    World getWorld();

    void setWorld(World world);
}
