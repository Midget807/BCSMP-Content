package com.bcsmp.bcsmp_content.main.domain_robes.item.custom;

import net.minecraft.item.ArmorMaterial;
import net.minecraft.registry.entry.RegistryEntry;

public class DomainRobesArmorItem extends DomainRobesItem{
    public DomainRobesArmorItem(RegistryEntry<ArmorMaterial> material, Type type, Settings settings) {
        super(material, type, settings);
    }
}
