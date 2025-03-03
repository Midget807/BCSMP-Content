package com.bcsmp.bcsmp_content.main.domain_expansion.effect;

import com.bcsmp.bcsmp_content.BCSMPContentMain;
import com.bcsmp.bcsmp_content.main.domain_expansion.effect.custom.DomainDeathEffect;
import com.bcsmp.bcsmp_content.main.domain_expansion.effect.custom.EmptyStatusEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class DEModEffects {
    public static final StatusEffect DOMAIN_DEATH_EFFECT = registerEffect("domain_death", new DomainDeathEffect(StatusEffectCategory.NEUTRAL, 0x050d21));
    public static final StatusEffect DOMAIN_TP_EFFECT = registerEffect("domain_tp", new EmptyStatusEffect(StatusEffectCategory.NEUTRAL, 0x050d21));

    private static StatusEffect registerEffect(String name, StatusEffect effect) {
        return Registry.register(Registries.STATUS_EFFECT, BCSMPContentMain.domainExpansionId(name), effect);
    }

    public static void registerDomainExpansionEffects() {
        BCSMPContentMain.LOGGER.info("Domain Expansion -> Registering Mod Effects");
    }
}
