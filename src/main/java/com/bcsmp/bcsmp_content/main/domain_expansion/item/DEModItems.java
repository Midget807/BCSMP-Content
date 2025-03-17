package com.bcsmp.bcsmp_content.main.domain_expansion.item;

import com.bcsmp.bcsmp_content.BCSMPContentMain;
import com.bcsmp.bcsmp_content.main.domain_expansion.item.custom.DebuggerItem;
import com.bcsmp.bcsmp_content.main.domain_expansion.item.custom.DomainCompressorItem;
import com.bcsmp.bcsmp_content.main.domain_expansion.item.custom.DomainExpansionItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class DEModItems {
    public static final Item DEBUGGER = registerItem("debugger", new DebuggerItem(new Item.Settings().maxCount(1).fireproof()));
    public static final Item DOMAIN_EXPANDER = registerItem("domain_expander", new DomainExpansionItem(new Item.Settings().maxCount(1).fireproof(), null));
    public static final Item DOMAIN_COMPRESSOR = registerItem("domain_compressor", new DomainCompressorItem(new Item.Settings().maxCount(1)));
    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, BCSMPContentMain.domainExpansionId(name), item);
    }
    public static void registerDomainExpansionItems() {
        BCSMPContentMain.LOGGER.info("Domain Expansion -> Registering Mod Items");
    }
}
