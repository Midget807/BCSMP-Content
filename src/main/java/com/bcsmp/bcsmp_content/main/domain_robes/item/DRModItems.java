package com.bcsmp.bcsmp_content.main.domain_robes.item;

import com.bcsmp.bcsmp_content.BCSMPContentMain;
import com.bcsmp.bcsmp_content.main.domain_robes.item.custom.DRModArmorMaterials;
import com.bcsmp.bcsmp_content.main.domain_robes.item.custom.DomainRobesArmorItem;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterials;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class DRModItems {
    public static final Item HOOD = registerItem("domain_hood", new DomainRobesArmorItem(DRModArmorMaterials.DIMENSIONAL_CLOTH, ArmorItem.Type.HELMET, new Item.Settings().maxCount(1)));
    public static final Item ROBE = registerItem("domain_robe", new DomainRobesArmorItem(DRModArmorMaterials.DIMENSIONAL_CLOTH, ArmorItem.Type.CHESTPLATE, new Item.Settings().maxCount(1)));
    public static final Item PANTS = registerItem("domain_pants", new DomainRobesArmorItem(DRModArmorMaterials.DIMENSIONAL_CLOTH, ArmorItem.Type.LEGGINGS, new Item.Settings().maxCount(1)));
    public static final Item BOOTS = registerItem("domain_boots", new DomainRobesArmorItem(DRModArmorMaterials.DIMENSIONAL_CLOTH, ArmorItem.Type.BOOTS, new Item.Settings().maxCount(1)));

    public static final Item DIMENSIONAL_STRING = registerItem("dimensional_string", new Item(new Item.Settings()));
    public static final Item DIMENSIONAL_CLOTH = registerItem("dimensional_cloth", new Item(new Item.Settings()));
    public static final Item INTERDIMENSIONAL_LENS = registerItem("interdimensional_lens", new Item(new Item.Settings().maxCount(1)));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, BCSMPContentMain.domainRobesId(name), item);
    }

    public static void registerDomainRobesItems() {
        BCSMPContentMain.LOGGER.info("Domain Robes -> Registering Mod Items");
    }
}
