package com.bcsmp.bcsmp_content.main.arcanus_clothes.item.custom;

import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.registry.entry.RegistryEntry;

public class WizardArmorItem extends ArmorItem {
    public WizardArmorItem(RegistryEntry<ArmorMaterial> material, Type type, Settings settings) {
        super(material, type, settings);
        CauldronBehavior.WATER_CAULDRON_BEHAVIOR.map().put(this, CauldronBehavior.CLEAN_DYEABLE_ITEM);
    }

}
