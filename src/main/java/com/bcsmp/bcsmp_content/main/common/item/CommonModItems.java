package com.bcsmp.bcsmp_content.main.common.item;

import com.bcsmp.bcsmp_content.BCSMPContentMain;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class CommonModItems {
    public static final Item BCSMP_ITEM_GROUP_ICON = registerItem("itemgroup_icon", new Item(new Item.Settings()));
    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, BCSMPContentMain.globalId(name), item);
    }

    public static void registerCommonModItems() {
        BCSMPContentMain.LOGGER.info("Main -> Registering Mod Items");
    }
}
