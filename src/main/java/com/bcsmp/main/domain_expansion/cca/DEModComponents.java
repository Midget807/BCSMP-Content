package com.bcsmp.main.domain_expansion.cca;

import com.bcsmp.BCSMPS2ContentMain;
import com.bcsmp.main.domain_expansion.block.custom.entity.DomainPillarBlockEntity;
import com.bcsmp.main.domain_expansion.cca.custom.DomainPillarEntityComponent;
import dev.onyxstudios.cca.api.v3.block.BlockComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.block.BlockComponentInitializer;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;

public class DEModComponents implements BlockComponentInitializer {
    public static final ComponentKey<DomainPillarEntityComponent> DOMAIN_PILLAR =
        ComponentRegistry.getOrCreate(BCSMPS2ContentMain.domainExpansionId("domain_usage"), DomainPillarEntityComponent.class);

    @Override
    public void registerBlockComponentFactories(BlockComponentFactoryRegistry registry) {
        registry.beginRegistration(DomainPillarBlockEntity.class, DOMAIN_PILLAR).end(DomainPillarEntityComponent::new);
    }
}