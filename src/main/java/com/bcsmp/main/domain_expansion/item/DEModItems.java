package com.bcsmp.main.domain_expansion.item;

import com.bcsmp.BCSMPS2ContentMain;
import com.bcsmp.main.domain_expansion.item.custom.DebuggerItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class DEModItems {
    public static final Item DEBUGGER = registerItem("debugger", new DebuggerItem(new FabricItemSettings().maxCount(1).fireproof()));
    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, BCSMPS2ContentMain.domainExpansionId(name), item);
    }
    public static void registerDomainExpansionItems() {
        BCSMPS2ContentMain.LOGGER.info("Domain Expansion -> Registering Mod Items");
    }
}
