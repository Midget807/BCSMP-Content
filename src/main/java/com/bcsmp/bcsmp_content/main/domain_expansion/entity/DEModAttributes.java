package com.bcsmp.bcsmp_content.main.domain_expansion.entity;

import com.bcsmp.bcsmp_content.BCSMPContentMain;

public class DEModAttributes {
    public static void registerAttributes() {

    }
    public static void registerDomainExpansionAttributes() {
        BCSMPContentMain.LOGGER.info("Domain Expansion -> Registering Mod Attributes");
        registerAttributes();
    }
}
