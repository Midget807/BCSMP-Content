package com.bcsmp.bcsmp_content;

import com.bcsmp.bcsmp_content.main.arcanus_clothes.item.ACModItems;
import com.bcsmp.bcsmp_content.main.charter_fix.effect.CFModEffects;
import com.bcsmp.bcsmp_content.main.common.command.CommonModCommands;
import com.bcsmp.bcsmp_content.main.common.item.CommonModItemGroups;
import com.bcsmp.bcsmp_content.main.common.item.CommonModItems;
import com.bcsmp.bcsmp_content.main.domain_expansion.block.DEModBlockEntities;
import com.bcsmp.bcsmp_content.main.domain_expansion.block.DEModBlocks;
import com.bcsmp.bcsmp_content.main.domain_expansion.command.DEModCommands;
import com.bcsmp.bcsmp_content.main.domain_expansion.component.DEModDataComponentTypes;
import com.bcsmp.bcsmp_content.main.domain_expansion.config.DEModMidnightConfig;
import com.bcsmp.bcsmp_content.main.domain_expansion.effect.DEModEffects;
import com.bcsmp.bcsmp_content.main.domain_expansion.entity.DEModAttributes;
import com.bcsmp.bcsmp_content.main.domain_expansion.entity.DEModEntities;
import com.bcsmp.bcsmp_content.main.domain_expansion.item.DEModItems;
import com.bcsmp.bcsmp_content.main.domain_expansion.network.DEModMessages;
import com.bcsmp.bcsmp_content.main.domain_expansion.particle.DEModParticles;
import com.bcsmp.bcsmp_content.main.domain_expansion.screen.DEModScreenHandlers;
import com.bcsmp.bcsmp_content.main.domain_expansion.world.gen.feature.DEModFeatures;
import com.bcsmp.bcsmp_content.main.domain_robes.item.DRModItems;
import eu.midnightdust.lib.config.MidnightConfig;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BCSMPContentMain implements ModInitializer {
	public static Identifier domainExpansionId(String path) {
		return Identifier.of(DE_MOD_ID, path);
	}
	public static Identifier charterFixId(String path) {
		return Identifier.of(CF_MOD_ID, path);
	}
	public static Identifier domainRobesId(String path) {
		return Identifier.of(DR_MOD_ID, path);
	}
	public static Identifier arcanusClothesId(String path) {
		return Identifier.of(AC_MOD_ID, path);
	}
	public static Identifier globalId(String path) {
		return Identifier.of(GLOBAL_MOD_ID, path);
	}
	public static final String DE_MOD_ID = "domain_expansion";
	public static final String CF_MOD_ID = "charter_fix";
	public static final String DR_MOD_ID = "domain_robes";
	public static final String AC_MOD_ID = "arcanus_clothes";
	public static final String GLOBAL_MOD_ID = "bcsmp_content";
	public static final Logger LOGGER = LoggerFactory.getLogger(GLOBAL_MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("yeh hello");

		//Common
		CommonModCommands.registerCommonModCommands();
		CommonModItems.registerCommonModItems();
		CommonModItemGroups.registerCommonItemGroups();



		//Charter Fix Mod
		CFModEffects.registerCharterFixEffects();


		//Domain Expansion Mod
		MidnightConfig.init(DE_MOD_ID, DEModMidnightConfig.class);

		DEModItems.registerDomainExpansionItems();
		DEModBlocks.registerDomainExpansionBlocks();
		DEModBlockEntities.registerDomainExpansionBlockEntities();
		DEModEntities.registerDomainExpansionEntities();
		DEModAttributes.registerDomainExpansionAttributes();
		DEModScreenHandlers.registerDomainExpansionScreenHandlers();
		DEModMessages.registerC2SPackets();
		DEModFeatures.registerDomainExpansionFeatures();
		//DEModBiomeModifier.registerDomainExpansionBiomeModifier();
		DEModEffects.registerDomainExpansionEffects();
		DEModCommands.registerDomainExpansionCommands();
		DEModDataComponentTypes.registerDomainExpansionDataComponentTypes();
		DEModParticles.registerDomainExpansionParticles();


		//Domain Robes Mod
		DRModItems.registerDomainRobesItems();

		//Arcanus Clothes Mod
		ACModItems.registerArcanusClothesItems();
	}
}