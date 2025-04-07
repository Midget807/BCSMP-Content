package com.bcsmp.bcsmp_content.main.domain_robes.item.custom.client;

import com.bcsmp.bcsmp_content.BCSMPContentMain;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;

public class DomainRobesArmorModel<T extends LivingEntity> extends BipedEntityModel<T> {
    public static final EntityModelLayer MODEL_LAYER = new EntityModelLayer(BCSMPContentMain.domainRobesId("domain_robes"), "main");
    public final ModelPart hood;
    public final ModelPart robe;
    public final ModelPart rightSleeve;
    public final ModelPart leftSleeve;
    public final ModelPart rightPants;
    public final ModelPart leftPants;
    public final ModelPart rightBoot;
    public final ModelPart leftBoot;
    public DomainRobesArmorModel(ModelPart root) {
        super(root);
        hood = head.getChild("hood");
        robe = body.getChild("robe");
        rightSleeve = rightArm.getChild("rightSleeve");
        leftSleeve = leftArm.getChild("leftSleeve");
        rightPants = rightLeg.getChild("rightPants");
        leftPants = leftLeg.getChild("leftPants");
        rightBoot = rightLeg.getChild("rightBoot");
        leftBoot = leftLeg.getChild("leftBoot");
    }
    public static TexturedModelData getTexturedModelData() {
        ModelData data = BipedEntityModel.getModelData(Dilation.NONE, 0);
        ModelPartData head = data.getRoot().getChild(EntityModelPartNames.HEAD);
        ModelPartData body = data.getRoot().getChild(EntityModelPartNames.BODY);
        ModelPartData rightArm = data.getRoot().getChild(EntityModelPartNames.RIGHT_ARM);
        ModelPartData leftArm = data.getRoot().getChild(EntityModelPartNames.LEFT_ARM);
        ModelPartData rightLeg = data.getRoot().getChild(EntityModelPartNames.RIGHT_LEG);
        ModelPartData leftLeg = data.getRoot().getChild(EntityModelPartNames.LEFT_LEG);

        /*ModelPartData hood = head.addChild("hood", ModelPartBuilder.create().uv(0, 0).cuboid(-4.5F, -32.5F, -4.5F, 9.0F, 9.0F, 9.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData robe = body.addChild("robe", ModelPartBuilder.create().uv(0, 18).cuboid(-4.5F, -24.5F, -2.5F, 9.0F, 13.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
        ModelPartData leftFlap = robe.addChild("leftFlap", ModelPartBuilder.create().uv(48, 16).cuboid(-2.0F, -1.0F, -2.5F, 2.0F, 11.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(4.5F, -11.6F, 0.0F, 0.0436F, 0.0F, -0.2618F));
        ModelPartData rightFlap = robe.addChild("rightFlap", ModelPartBuilder.create().uv(36, 0).cuboid(0.0F, -1.0F, -2.5F, 2.0F, 11.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(-4.5F, -11.6F, 0.0F, 0.0436F, 0.0F, 0.2618F));
        ModelPartData backFlap = robe.addChild("backFlap", ModelPartBuilder.create().uv(0, 36).cuboid(-4.5F, -1.0F, -2.0F, 9.0F, 11.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -11.5F, 2.5F, 0.2618F, 0.0F, 0.0F));

        ModelPartData leftSleeve = leftArm.addChild("leftSleeve", ModelPartBuilder.create().uv(28, 18).cuboid(3.5F, -24.5F, -2.5F, 5.0F, 12.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
        ModelPartData leftCuff = leftSleeve.addChild("leftCuff", ModelPartBuilder.create().uv(0, 49).cuboid(-2.5F, -5.0F, 0.0F, 5.0F, 5.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(6.0F, -12.5F, 2.5F, 0.6981F, 0.0F, 0.0F));

        ModelPartData rightSleeve = rightArm.addChild("rightSleeve", ModelPartBuilder.create().uv(28, 35).cuboid(3.5F, -24.5F, -2.5F, 5.0F, 12.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(-12.0F, 24.0F, 0.0F));
        ModelPartData rightCuff = rightSleeve.addChild("rightCuff", ModelPartBuilder.create().uv(50, 0).cuboid(-2.5F, -5.0F, 0.0F, 5.0F, 5.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(6.0F, -12.5F, 2.5F, 0.6981F, 0.0F, 0.0F));

        ModelPartData leftPants = leftLeg.addChild("leftPants", ModelPartBuilder.create().uv(48, 32).cuboid(-4.0F, -12.0F, -2.0F, 4.0F, 10.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(4.0F, 24.0F, 0.0F));
        ModelPartData frontLeftFlap = leftPants.addChild("frontLeftFlap", ModelPartBuilder.create().uv(18, 52).cuboid(0.0F, 0.0F, 0.0F, 4.0F, 11.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-4.0F, -12.0F, -2.5F, -0.1309F, 0.0F, -0.0873F));

        ModelPartData rightPants = rightLeg.addChild("rightPants", ModelPartBuilder.create().uv(48, 46).cuboid(-4.0F, -12.0F, -2.0F, 4.0F, 10.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
        ModelPartData frontRightFlap = rightPants.addChild("frontRightFlap", ModelPartBuilder.create().uv(30, 52).cuboid(-4.0F, 0.0F, 0.0F, 4.0F, 11.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -12.0F, -2.5F, -0.1309F, 0.0F, 0.0873F));

        ModelPartData leftBoot = leftLeg.addChild("leftBoot", ModelPartBuilder.create().uv(0, 58).cuboid(-1.0F, -4.0F, -2.0F, 4.0F, 4.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(1.0F, 24.0F, 0.0F));

        ModelPartData rightBoot = rightLeg.addChild("rightBoot", ModelPartBuilder.create().uv(42, 60).cuboid(-4.0F, -4.0F, -2.0F, 4.0F, 4.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
*/

        ModelPartData hood = head.addChild("hood", ModelPartBuilder.create().uv(0, 84).cuboid(-4.5F, -8.5F, -4.5F, 9F, 9F, 9F, new Dilation(0.11F)), ModelTransform.pivot(0F, 0F, 0F));

        ModelPartData robe = body.addChild("robe", ModelPartBuilder.create().uv(0, 83).cuboid(-4.5F, -0.5F, -2.5F, 9F, 13F, 5F, new Dilation(0.05F)).uv(28, 83).cuboid(-5F, -0.5F, -2.5F, 10F, 13F, 5F, new Dilation(0.1F)), ModelTransform.pivot(0F, 0F, 0F));
        ModelPartData leftFlap = robe.addChild("leftFlap", ModelPartBuilder.create().uv(36, 101).cuboid(-2.5F, 12.6F, -0.95F, 5F, 9F, 2F, new Dilation(0F)), ModelTransform.of(0F, 0F, 0F, 0.2618F, 1.5708F, 0F));
        ModelPartData rightFlap = robe.addChild("rightFlap", ModelPartBuilder.create().uv(22, 101).cuboid(-2.5F, 12.6F, -0.95F, 5F, 9F, 2F, new Dilation(0F)), ModelTransform.of(0F, 0F, 0F, 0.2618F, -1.5708F, 0F));
        ModelPartData backFlap = robe.addChild("backFlap", ModelPartBuilder.create().uv(0, 101).cuboid(-4.5F, 12.1F, -2.75F, 9F, 9F, 2F, new Dilation(0F)), ModelTransform.of(0F, 0F, 0F, 0.2618F, 0F, 0F));

        ModelPartData rightSleeve = rightArm.addChild("rightSleeve", ModelPartBuilder.create().uv(58, 84).cuboid(-3.5F, -2.5F, -2.5F, 5F, 12F, 5F, new Dilation(0.01F)), ModelTransform.pivot(0F, 0F, 0F));
        ModelPartData rightCuff = rightSleeve.addChild("rightCuff", ModelPartBuilder.create().uv(78, 92).cuboid(-2.5F, -4.6F, -0.3F, 5F, 5F, 4F, new Dilation(0F)), ModelTransform.of(-1F, 9F, 2.5F, 0.7418F, 0F, 0F));
        ModelPartData leftSleeve = leftArm.addChild("leftSleeve", ModelPartBuilder.create().uv(58, 84).mirrored().cuboid(-1.5F, -2.5F, -2.5F, 5F, 12F, 5F, new Dilation(0.01F)).mirrored(false), ModelTransform.pivot(0F, 0F, 0F));
        ModelPartData leftCuff = leftSleeve.addChild("leftCuff", ModelPartBuilder.create().uv(78, 92).mirrored().cuboid(-2.5F, -4.6F, -0.3F, 5F, 5F, 4F, new Dilation(0F)).mirrored(false), ModelTransform.of(1F, 9F, 2.5F, 0.7418F, 0F, 0F));

        ModelPartData rightPants = rightLeg.addChild("rightPants", ModelPartBuilder.create().uv(0, 112).cuboid(-2F, 0F, -2F, 4F, 12F, 4F, new Dilation(0.3F)), ModelTransform.pivot(0F, 0F, 0F));
        ModelPartData leftPants = leftLeg.addChild("leftPants", ModelPartBuilder.create().uv(0, 112).mirrored().cuboid(-2F, 0F, -2F, 4F, 12F, 4F, new Dilation(0.3F)).mirrored(false), ModelTransform.pivot(0F, 0F, 0F));

        ModelPartData rightBoot = rightLeg.addChild("rightBoot", ModelPartBuilder.create().uv(16, 112).cuboid(-2F, 0F, -2F, 4F, 12F, 4F, new Dilation(0.4F)), ModelTransform.pivot(0F, 0F, 0F));
        ModelPartData leftBoot = leftLeg.addChild("leftBoot", ModelPartBuilder.create().uv(16, 112).mirrored().cuboid(-2F, 0F, -2F, 4F, 12F, 4F, new Dilation(0.4F)).mirrored(false), ModelTransform.pivot(0F, 0F, 0F));

        return TexturedModelData.of(data, 128, 128);
    }

    @Override
    public void setAngles(T livingEntity, float f, float g, float h, float i, float j) {
        super.setAngles(livingEntity, f, g, h, i, j);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
        super.render(matrices, vertices, light, overlay, color);
    }
}
