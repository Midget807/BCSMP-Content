package com.bcsmp.main.domain_expansion.block;

import com.bcsmp.BCSMPS2ContentMain;
import com.bcsmp.main.domain_expansion.block.custom.DomainPillarBlock;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;

public class DEModBlocks {
    public static final Block DOMAIN_PILLAR = registerBlock("domain_pillar", new DomainPillarBlock(FabricBlockSettings.create().mapColor(MapColor.IRON_GRAY).requiresTool().strength(3.5f).sounds(BlockSoundGroup.STONE).pistonBehavior(PistonBehavior.IGNORE).nonOpaque()));

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, BCSMPS2ContentMain.domainExpansionId(name), block);
    }

    private static Item registerBlockItem(String name, Block block) {
        return Registry.register(Registries.ITEM, BCSMPS2ContentMain.domainExpansionId(name), new BlockItem(block, new FabricItemSettings()));
    }

    public static void registerDomainExpansionBlocks() {
        BCSMPS2ContentMain.LOGGER.info("Domain Expansion -> Registering Mod Blocks");
    }
}
