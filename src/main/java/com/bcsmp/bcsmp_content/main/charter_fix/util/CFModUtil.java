package com.bcsmp.bcsmp_content.main.charter_fix.util;

import com.bcsmp.bcsmp_content.main.charter_fix.effect.CFModEffects;
import com.bcsmp.bcsmp_content.main.domain_expansion.effect.DEModEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.jetbrains.annotations.Nullable;

public class CFModUtil {
    public static boolean isPlayerChained(@Nullable LivingEntity entity) {
        return  entity != null &&
                entity.hasStatusEffect(CFModEffects.CHAINED) &&
                !entity.isSpectator() &&
                !(entity instanceof PlayerEntity player && player.isCreative());
    }
}
