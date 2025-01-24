package com.bcsmp.main.domain_expansion.block;

import com.bcsmp.BCSMPS2ContentMain;
import com.bcsmp.main.domain_expansion.block.custom.entity.DomainPillarBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class DEModBlockEntities {
    public static final BlockEntityType<DomainPillarBlockEntity> DOMAIN_PILLAR_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, BCSMPS2ContentMain.domainExpansionId("domain_pillar"),
                    FabricBlockEntityTypeBuilder.create(DomainPillarBlockEntity::new, DEModBlocks.DOMAIN_PILLAR).build()
            );

    public static void registerDomainExpansionBlockEntities() {
        BCSMPS2ContentMain.LOGGER.info("Domain Expansion -> Registering Mod Block Entities");
    }
}
