package com.bcsmp.bcsmp_content.main.common.util;

import com.bcsmp.bcsmp_content.main.arcanus_clothes.item.ACModItems;
import com.bcsmp.bcsmp_content.main.domain_expansion.block.DEModBlocks;
import com.bcsmp.bcsmp_content.main.domain_expansion.item.DEModItems;
import com.bcsmp.bcsmp_content.main.domain_robes.item.DRModItems;
import net.minecraft.item.Item;
import net.minecraft.util.collection.DefaultedList;

public class CommonModUtil {

    public static DefaultedList<Item> getDomainExpansionItems() {
        DefaultedList<Item> modItems = DefaultedList.of();
        modItems.add(DEModItems.DOMAIN_EXPANDER);
        modItems.add(DEModBlocks.DOMAIN_PILLAR.asItem());
        return modItems;
    }
    public static DefaultedList<Item> getDomainRobesItems() {
        DefaultedList<Item> modItems = DefaultedList.of();
        modItems.add(DRModItems.HOOD);
        modItems.add(DRModItems.ROBE);
        modItems.add(DRModItems.PANTS);
        modItems.add(DRModItems.BOOTS);
        modItems.add(DRModItems.DIMENSIONAL_STRING);
        modItems.add(DRModItems.DIMENSIONAL_CLOTH);
        modItems.add(DRModItems.INTERDIMENSIONAL_LENS);
        return modItems;
    }
    public static DefaultedList<Item> getArcanusClothesItems() {
        DefaultedList<Item> modItems = DefaultedList.of();
        modItems.add(ACModItems.HAT);
        modItems.add(ACModItems.ROBES);
        modItems.add(ACModItems.PANTS);
        modItems.add(ACModItems.BOOTS);
        return modItems;
    }
}
