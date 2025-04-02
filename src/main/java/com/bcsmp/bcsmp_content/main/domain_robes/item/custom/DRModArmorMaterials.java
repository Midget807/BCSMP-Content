package com.bcsmp.bcsmp_content.main.domain_robes.item.custom;

import com.bcsmp.bcsmp_content.BCSMPContentMain;
import com.bcsmp.bcsmp_content.main.domain_robes.item.DRModItems;
import com.google.common.base.Supplier;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ArmorMaterials;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Util;

import java.util.EnumMap;
import java.util.List;

public class DRModArmorMaterials {
    public static final RegistryEntry<ArmorMaterial> DIMENSIONAL_CLOTH = registerArmorMaterial("dimensional_cloth",
            () -> new ArmorMaterial(Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                map.put(ArmorItem.Type.HELMET, 3);
                map.put(ArmorItem.Type.CHESTPLATE, 8);
                map.put(ArmorItem.Type.LEGGINGS, 6);
                map.put(ArmorItem.Type.BOOTS, 3);
                map.put(ArmorItem.Type.BODY, 11);
            }), 20, SoundEvents.ITEM_ARMOR_EQUIP_ELYTRA, () -> Ingredient.ofItems(DRModItems.DIMENSIONAL_CLOTH),
                    List.of(new ArmorMaterial.Layer(BCSMPContentMain.domainRobesId("dimensional_cloth"), "", true)), 2.0f, 0.05f)
    );

    public static RegistryEntry<ArmorMaterial> registerArmorMaterial(String name, Supplier<ArmorMaterial> materialSupplier) {
        return Registry.registerReference(Registries.ARMOR_MATERIAL, BCSMPContentMain.domainRobesId(name), materialSupplier.get());
    }
}
