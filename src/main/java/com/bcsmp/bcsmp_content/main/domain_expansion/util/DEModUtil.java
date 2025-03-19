package com.bcsmp.bcsmp_content.main.domain_expansion.util;

import com.bcsmp.bcsmp_content.main.domain_expansion.block.DEModBlocks;
import com.bcsmp.bcsmp_content.main.domain_expansion.effect.DEModEffects;
import com.bcsmp.bcsmp_content.main.domain_expansion.item.DEModItems;
import com.bcsmp.bcsmp_content.main.domain_expansion.world.gen.dimension.DEModDimensions;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class DEModUtil {
    public static boolean shouldLockPlayerMovement(@Nullable LivingEntity entity) {
        return  entity != null &&
                entity.hasStatusEffect(DEModEffects.DOMAIN_TP_EFFECT) &&
                !entity.isSpectator() &&
                !(entity instanceof PlayerEntity player && player.isCreative());
    }
    public static DefaultedList<Item> getDomainExpansionItems() {
        DefaultedList<Item> domainExpansionItems = DefaultedList.of();
        domainExpansionItems.add(DEModItems.DOMAIN_EXPANDER);
        domainExpansionItems.add(DEModBlocks.DOMAIN_PILLAR.asItem());
        return domainExpansionItems;
    }
    public static DefaultedList<RegistryKey<World>> getDomainKeys() {
        DefaultedList<RegistryKey<World>> domainKeys = DefaultedList.of();
        domainKeys.add(DEModDimensions.DOMAIN_1_LEVEL_KEY);
        domainKeys.add(DEModDimensions.DOMAIN_2_LEVEL_KEY);
        domainKeys.add(DEModDimensions.DOMAIN_3_LEVEL_KEY);
        domainKeys.add(DEModDimensions.DOMAIN_4_LEVEL_KEY);
        return domainKeys;
    }
}
