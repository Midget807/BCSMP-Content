package com.bcsmp.bcsmp_content.main.common.item;

import com.bcsmp.bcsmp_content.BCSMPContentMain;
import com.bcsmp.bcsmp_content.main.arcanus_clothes.item.ACModItems;
import com.bcsmp.bcsmp_content.main.domain_expansion.block.DEModBlocks;
import com.bcsmp.bcsmp_content.main.domain_expansion.item.DEModItems;
import com.bcsmp.bcsmp_content.main.domain_robes.item.DRModItems;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;

public class CommonModItemGroups {
    public static final RegistryKey<ItemGroup> BCSMP_ITEM_GROUP_KEY = RegistryKey.of(RegistryKeys.ITEM_GROUP, BCSMPContentMain.globalId("bcsmp_itemgroup"));
    public static final ItemGroup BCSMP_ITEM_GROUP = registerItemGroup(BCSMP_ITEM_GROUP_KEY, FabricItemGroup.builder()
            .icon(() -> new ItemStack(CommonModItems.BCSMP_ITEM_GROUP_ICON))
            .displayName(Text.translatable("itemGroup.bcsmp_content.global"))
            .build()
    );
    public static void addBCSMPItemGroupEntries() {
        ItemGroupEvents.modifyEntriesEvent(BCSMP_ITEM_GROUP_KEY).register(itemGroup -> {
            itemGroup.add(DEModBlocks.DOMAIN_OBSIDIAN);
            itemGroup.add(DEModBlocks.WITHERED_SOIL);

            itemGroup.add(DEModBlocks.DOMAIN_PILLAR);
            itemGroup.add(DEModItems.DOMAIN_EXPANDER);

            itemGroup.add(DRModItems.HOOD);
            itemGroup.add(DRModItems.ROBE);
            itemGroup.add(DRModItems.PANTS);
            itemGroup.add(DRModItems.BOOTS);
            itemGroup.add(DRModItems.DIMENSIONAL_CLOTH);

            itemGroup.add(ACModItems.HAT);
            itemGroup.add(ACModItems.ROBES);
            itemGroup.add(ACModItems.PANTS);
            itemGroup.add(ACModItems.BOOTS);
        });
    }
    private static ItemGroup registerItemGroup(RegistryKey<ItemGroup> key, ItemGroup itemGroup) {
        return Registry.register(Registries.ITEM_GROUP, key, itemGroup);
    }
    public static void registerCommonItemGroups() {
        BCSMPContentMain.LOGGER.info("Main -> Registering Mod Item Groups");
        addBCSMPItemGroupEntries();
    }
}
