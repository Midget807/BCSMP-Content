package com.bcsmp.bcsmp_content;

import com.bcsmp.bcsmp_content.main.domain_expansion.block.DEModBlockEntities;
import com.bcsmp.bcsmp_content.main.domain_expansion.block.DEModBlocks;
import com.bcsmp.bcsmp_content.main.domain_expansion.entity.DEModEntities;
import com.bcsmp.bcsmp_content.main.domain_expansion.item.DEModItemGroups;
import com.bcsmp.bcsmp_content.main.domain_expansion.item.DEModItems;
import com.bcsmp.bcsmp_content.main.domain_expansion.network.DEModMessages;
import com.bcsmp.bcsmp_content.main.domain_expansion.screen.DEModScreenHandlers;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BCSMPContentMain implements ModInitializer {
	public static Identifier domainExpansionId(String path) {
		return new Identifier(DE_MOD_ID, path);
	}
	public static final String DE_MOD_ID = "domain_expansion";
	public static final String GLOBAL_MOD_ID = "bcsmp_content";
	public static final Logger LOGGER = LoggerFactory.getLogger(GLOBAL_MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("yeh hello");

		//Domain Expansion Mod
		DEModItems.registerDomainExpansionItems();
		DEModItemGroups.registerDomainExpansionItemGroups();
		DEModBlocks.registerDomainExpansionBlocks();
		DEModBlockEntities.registerDomainExpansionBlockEntities();
		DEModEntities.registerDomainExpansionEntities();
		DEModScreenHandlers.registerDomainExpansionScreenHandlers();
		DEModMessages.registerC2SPackets();
	}
}