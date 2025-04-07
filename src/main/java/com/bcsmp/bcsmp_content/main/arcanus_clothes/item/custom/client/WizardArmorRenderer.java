package com.bcsmp.bcsmp_content.main.arcanus_clothes.item.custom.client;

import com.bcsmp.bcsmp_content.main.arcanus_clothes.item.custom.WizardRobesArmorItem;
import com.bcsmp.bcsmp_content.main.arcanus_clothes.util.ACModTextureIds;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.component.type.DyedColorComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;

public class WizardArmorRenderer implements ArmorRenderer {
    private final MinecraftClient client = MinecraftClient.getInstance();
    private final Identifier mainTexture = ACModTextureIds.WIZARD_ROBES_TEXTURE;
    private final Identifier overlayTexture = ACModTextureIds.WIZARD_ROBES_OVERLAY_TEXTURE;
    private WizardArmorModel<LivingEntity> model;
    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, ItemStack stack, LivingEntity entity, EquipmentSlot slot, int light, BipedEntityModel<LivingEntity> contextModel) {
        if(model == null)
            model = new WizardArmorModel<>(client.getEntityModelLoader().getModelPart(WizardArmorModel.MODEL_LAYER));

        if(stack.getItem() instanceof WizardRobesArmorItem wizardArmour) {
            int hexColour = ColorHelper.Argb.fullAlpha(DyedColorComponent.getColor(stack, -6265536));
            float red = (hexColour >> 16 & 255) / 255F;
            float green = (hexColour >> 8 & 255) / 255F;
            float blue = (hexColour & 255) / 255F;

            int u;
            if(stack.getName().getString().equals("jeb_")) {
                int m = 25;
                int n = entity.age / 25 + entity.getId();
                int o = DyeColor.values().length;
                int p = n % o;
                int q = (n + 1) % o;
                float r = (entity.age % m + 1.0f) / 25.0F;
                int s = SheepEntity.getRgbColor(DyeColor.byId(p));
                int t = SheepEntity.getRgbColor(DyeColor.byId(q));
                u = ColorHelper.Argb.lerp(r, s, t);
            } else {
                u = hexColour;
            }

            contextModel.copyBipedStateTo(model);
            model.setVisible(true);
            model.hat.visible = slot == EquipmentSlot.HEAD;
            model.robes.visible = slot == EquipmentSlot.CHEST;
            model.rightSleeve.visible = slot == EquipmentSlot.CHEST;
            model.leftSleeve.visible = slot == EquipmentSlot.CHEST;
            model.rightPants.visible = slot == EquipmentSlot.LEGS;
            model.leftPants.visible = slot == EquipmentSlot.LEGS;
            model.rightBoot.visible = slot == EquipmentSlot.FEET;
            model.leftBoot.visible = slot == EquipmentSlot.FEET;

            model.render(matrices, ItemRenderer.getArmorGlintConsumer(vertexConsumers, RenderLayer.getArmorCutoutNoCull(mainTexture), false), light, OverlayTexture.DEFAULT_UV, u);
            model.render(matrices, ItemRenderer.getArmorGlintConsumer(vertexConsumers, RenderLayer.getArmorCutoutNoCull(overlayTexture), true), light, OverlayTexture.DEFAULT_UV);
        }
    }
}
