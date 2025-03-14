package com.bcsmp.bcsmp_content.main.domain_expansion.item;

import com.bcsmp.bcsmp_content.BCSMPContentMain;
import com.bcsmp.bcsmp_content.main.domain_expansion.block.DEModBlocks;
import com.bcsmp.bcsmp_content.main.domain_expansion.util.SubModState;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemGroups;
import net.minecraft.server.MinecraftServer;

public class DEModItemGroups {
    private static void addItemsToToolsGroup(FabricItemGroupEntries entries) {
        MinecraftClient client = MinecraftClient.getInstance();
        MinecraftServer server = client.getServer();
        SubModState state = SubModState.getServerState(server);
        if (state.getDomainExpansionModEnabled()) {
            entries.add(DEModItems.DOMAIN_EXPANDER.getDefaultStack());
            entries.add(DEModBlocks.DOMAIN_PILLAR);
        }
    }
    public static void registerDomainExpansionItemGroups() {
        BCSMPContentMain.LOGGER.info("Domain Expansion -> Registering Mod Item Groups");
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(DEModItemGroups::addItemsToToolsGroup);
    }
}
