package com.bcsmp.bcsmp_content.main.arcanus_clothes.item;

import com.bcsmp.bcsmp_content.BCSMPContentMain;
import com.bcsmp.bcsmp_content.main.arcanus_clothes.item.custom.ACModArmorMaterials;
import com.bcsmp.bcsmp_content.main.arcanus_clothes.item.custom.WizardRobesArmorItem;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ACModItems {
    public static final Item HAT = registerItem("wizard_hat", new WizardRobesArmorItem(ACModArmorMaterials.WIZARD, ArmorItem.Type.HELMET, new Item.Settings().maxCount(1)));
    public static final Item ROBES = registerItem("wizard_robes", new WizardRobesArmorItem(ACModArmorMaterials.WIZARD, ArmorItem.Type.CHESTPLATE, new Item.Settings().maxCount(1)));
    public static final Item PANTS = registerItem("wizard_pants", new WizardRobesArmorItem(ACModArmorMaterials.WIZARD, ArmorItem.Type.LEGGINGS, new Item.Settings().maxCount(1)));
    public static final Item BOOTS = registerItem("wizard_boots", new WizardRobesArmorItem(ACModArmorMaterials.WIZARD, ArmorItem.Type.BOOTS, new Item.Settings().maxCount(1)));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, BCSMPContentMain.arcanusClothesId(name), item);
    }

    public static void registerArcanusClothesItems() {
        BCSMPContentMain.LOGGER.info("Arcanus Clothes -> Registering Mod Items");
    }
}
