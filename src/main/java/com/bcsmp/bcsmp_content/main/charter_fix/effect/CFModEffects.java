package com.bcsmp.bcsmp_content.main.charter_fix.effect;

import com.bcsmp.bcsmp_content.BCSMPContentMain;
import com.bcsmp.bcsmp_content.main.common.effect.EmptyStatusEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class CFModEffects {
    public static final StatusEffect CHAINED = registerEffect("chained", new EmptyStatusEffect(StatusEffectCategory.NEUTRAL, 0xF1F152));

    public static StatusEffect registerEffect(String name, StatusEffect effect) {
        return Registry.register(Registries.STATUS_EFFECT, BCSMPContentMain.charterFixId(name), effect);
    }

    public static void registerCharterFixEffects() {
        BCSMPContentMain.LOGGER.info("Charter Fix -> Registering Mod Effects");
    }
}
