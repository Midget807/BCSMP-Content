package com.bcsmp.bcsmp_content.main.domain_expansion.worldgen.border;

import net.minecraft.world.border.WorldBorder;

public interface ServerWorldDomainProperties {
    DomainBorder.Properties getDomainBorderProperties();
    void setWorldBorderProperties(WorldBorder.Properties properties);
}
