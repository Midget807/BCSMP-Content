package com.bcsmp.main.domain_expansion.cca;

public class DEModComponents implements WorldComponentInitializer {
    public static final ComponentKey<BooleanCompoment> DEF_DOMAIN_1_IN_USE = 
        ComponentRegistry.getOrCreate(BCSMPS2ContentMain.domainExpansionId("def_domain_1_in_use"), BooleanComponent.class);

    @Override
    public void registerWorldComponentFactories(WorldComponentFactoryRegistry registry) {
        registry.register(DEF_DOMAIN_1_IN_USE, WorldBooleanComponent::new); //todo make "WorldBooleanComponent" custom component class
    }
}