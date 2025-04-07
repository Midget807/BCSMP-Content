package com.bcsmp.bcsmp_content.main.arcanus_clothes.item.custom;

import com.bcsmp.bcsmp_content.BCSMPContentMain;
import com.bcsmp.bcsmp_content.main.arcanus_clothes.item.ACModItems;
import com.google.common.base.Supplier;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Util;

import java.util.EnumMap;
import java.util.List;

public class ACModArmorMaterials {
    public static final RegistryEntry<ArmorMaterial> WIZARD = registerArmorMaterial("wizard",
            () -> new ArmorMaterial(Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                map.put(ArmorItem.Type.HELMET, 2);
                map.put(ArmorItem.Type.CHESTPLATE, 5);
                map.put(ArmorItem.Type.LEGGINGS, 4);
                map.put(ArmorItem.Type.BOOTS, 1);
            }), 25, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, () -> Ingredient.ofItems(Items.LEATHER),
                    List.of(new ArmorMaterial.Layer(BCSMPContentMain.arcanusClothesId("wizard"), "", true)), 0.0f, 0.0f)
    );

    public static RegistryEntry<ArmorMaterial> registerArmorMaterial(String name, Supplier<ArmorMaterial> materialSupplier) {
        return Registry.registerReference(Registries.ARMOR_MATERIAL, BCSMPContentMain.domainRobesId(name), materialSupplier.get());
    }
}
