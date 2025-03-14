package com.bcsmp.bcsmp_content.main.domain_expansion.util;

import com.bcsmp.bcsmp_content.main.domain_expansion.effect.DEModEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.jetbrains.annotations.Nullable;

public class DEModUtil {
    public static boolean shouldLockPlayerMovement(@Nullable LivingEntity entity) {
        return  entity != null &&
                entity.hasStatusEffect(DEModEffects.DOMAIN_TP_EFFECT) &&
                !entity.isSpectator() &&
                !(entity instanceof PlayerEntity player && player.isCreative());
    }
}
