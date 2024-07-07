package net.grid.vampiresdelight.client.extension;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.grid.vampiresdelight.common.registry.VDBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

public class SpiritLanternExtension implements IClientItemExtensions {
    private static final HumanoidModel.ArmPose POSE = HumanoidModel.ArmPose.create("vd_spirit_lantern_pose", false, (model, entity, arm) -> {
        ModelPart armModel = arm == HumanoidArm.RIGHT ? model.rightArm : model.leftArm;

        armModel.xRot = (float) Math.toRadians(-120) + model.getHead().xRot;
        armModel.yRot = model.getHead().yRot;
    });

    @Override
    public HumanoidModel.ArmPose getArmPose(LivingEntity entityLiving, InteractionHand hand, ItemStack itemStack) {
        return POSE;
    }

    /**
     * Credits to the Amendments mod
     * <a href="https://github.com/MehVahdJukaar/amendments">...</a>
     */
    public static void renderThirdPersonItem(HumanoidModel<?> parentModel, LivingEntity livingEntity, ItemStack itemStack, ItemDisplayContext displayContext, HumanoidArm arm, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();

        boolean isLeftHand = arm == HumanoidArm.LEFT;
        ModelPart armModel = isLeftHand ? parentModel.leftArm : parentModel.rightArm;

        float shoulderOffset = 1 / 32F;
        poseStack.translate(-shoulderOffset, 0, 0);
        parentModel.translateToHand(arm, poseStack);

        poseStack.translate(shoulderOffset + (isLeftHand ? 1 : -1) / 16.0F, 20 / 32f, 1 / 16f);

        poseStack.mulPose(Axis.XP.rotationDegrees(245 - Math.max((int) Math.toDegrees(parentModel.getHead().xRot), -35)));
        poseStack.mulPose(Axis.YP.rotationDegrees(15));
        poseStack.mulPose(Axis.ZP.rotationDegrees(15));

        if (livingEntity instanceof Player player) player.displayClientMessage(Component.literal(String.valueOf(Math.toDegrees(parentModel.getHead().xRot))), true);

        poseStack.scale(1.6f, 1.6f, 1.6f);
        poseStack.translate(0, -4 / 16f + Math.toRadians(armModel.yRot), -2 / 16f);

        renderLanternItemModel(itemStack, displayContext, poseStack, buffer, packedLight, isLeftHand);

        poseStack.popPose();
    }

    private static void renderLanternItemModel(ItemStack itemStack, ItemDisplayContext displayContext, PoseStack poseStack, MultiBufferSource buffer, int packedLight, boolean isLeftHand) {
        Minecraft minecraft = Minecraft.getInstance();
        BakedModel model = minecraft.getBlockRenderer().getBlockModel(VDBlocks.SPIRIT_LANTERN.get().defaultBlockState());
        minecraft.getItemRenderer().render(itemStack, displayContext, isLeftHand, poseStack, buffer, packedLight, OverlayTexture.NO_OVERLAY, model);
    }
}
