package com.bcsmp.bcsmp_content;

import com.bcsmp.bcsmp_content.main.arcanus_clothes.item.ACModItems;
import com.bcsmp.bcsmp_content.main.arcanus_clothes.item.custom.client.WizardArmorModel;
import com.bcsmp.bcsmp_content.main.arcanus_clothes.item.custom.client.WizardArmorRenderer;
import com.bcsmp.bcsmp_content.main.domain_expansion.network.DEModMessages;
import com.bcsmp.bcsmp_content.main.domain_expansion.particle.DEModParticles;
import com.bcsmp.bcsmp_content.main.domain_expansion.particle.custom.PillarActiveParticle;
import com.bcsmp.bcsmp_content.main.domain_expansion.screen.DEModScreenHandlers;
import com.bcsmp.bcsmp_content.main.domain_expansion.screen.custom.client.DomainPillarScreen;
import com.bcsmp.bcsmp_content.main.domain_robes.item.DRModItems;
import com.bcsmp.bcsmp_content.main.domain_robes.item.custom.client.DomainRobesArmorModel;
import com.bcsmp.bcsmp_content.main.domain_robes.item.custom.client.DomainRobesArmorRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class BCSMPContentClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        HandledScreens.register(DEModScreenHandlers.DOMAIN_PILLAR_SCREEN_HANDLER, DomainPillarScreen::new);
        DEModMessages.registerS2CPackets();

        ParticleFactoryRegistry.getInstance().register(DEModParticles.PILLAR_ACTIVE_PARTICLE, PillarActiveParticle.Factory::new);

        EntityModelLayerRegistry.registerModelLayer(DomainRobesArmorModel.MODEL_LAYER, DomainRobesArmorModel::getTexturedModelData);
        ArmorRenderer.register(new DomainRobesArmorRenderer(), DRModItems.HOOD, DRModItems.ROBE, DRModItems.PANTS, DRModItems.BOOTS);

        EntityModelLayerRegistry.registerModelLayer(WizardArmorModel.MODEL_LAYER, WizardArmorModel::getTexturedModelData);
        ArmorRenderer.register(new WizardArmorRenderer(), ACModItems.HAT, ACModItems.ROBES, ACModItems.PANTS, ACModItems.BOOTS);
    }
}
