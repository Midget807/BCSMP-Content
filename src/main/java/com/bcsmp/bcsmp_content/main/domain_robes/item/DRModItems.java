package com.bcsmp.bcsmp_content.main.domain_robes.item;

import com.bcsmp.bcsmp_content.BCSMPContentMain;
import com.bcsmp.bcsmp_content.main.domain_robes.item.custom.DomainRobesArmorItem;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterials;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class DRModItems {
    public static final Item HOOD = registerItem("hood", new DomainRobesArmorItem(ArmorMaterials.LEATHER, ArmorItem.Type.HELMET, new Item.Settings().maxCount(1)));
    public static final Item ROBE = registerItem("robe", new DomainRobesArmorItem(ArmorMaterials.LEATHER, ArmorItem.Type.CHESTPLATE, new Item.Settings().maxCount(1)));
    public static final Item PANTS = registerItem("pants", new DomainRobesArmorItem(ArmorMaterials.LEATHER, ArmorItem.Type.LEGGINGS, new Item.Settings().maxCount(1)));
    public static final Item BOOTS = registerItem("boots", new DomainRobesArmorItem(ArmorMaterials.LEATHER, ArmorItem.Type.BOOTS, new Item.Settings().maxCount(1)));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, BCSMPContentMain.domainRobesId(name), item);
    }

    public static void registerDomainRobesItems() {
        BCSMPContentMain.LOGGER.info("Domain Robes -> Registering Mod Items");
    }
}
