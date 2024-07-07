package net.grid.vampiresdelight.client.extension;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

public class PourableItemExtension implements IClientItemExtensions {
    private static final HumanoidModel.ArmPose POSE = HumanoidModel.ArmPose.create("vd_pouring_pose", true, (model, entity, arm) -> {
        boolean isRightArmPouring = arm == HumanoidArm.RIGHT;
        makePose(isRightArmPouring ? model.rightArm : model.leftArm, isRightArmPouring ? model.leftArm : model.rightArm, model.head, arm);
    });

    private static void makePose(ModelPart pouringHand, ModelPart glassHand, ModelPart head, HumanoidArm arm) {
        int multiplier = arm == HumanoidArm.RIGHT ? -1 : 1;

        pouringHand.xRot = (float) Math.toRadians(-135) + head.xRot / 2f;
        pouringHand.yRot = (float) Math.toRadians(45) * multiplier + head.yRot / 2.5f;

        glassHand.xRot = (float) Math.toRadians(-50) + head.xRot / 2f;
        glassHand.yRot = (float) Math.toRadians(25) * -multiplier + head.yRot / 2.5f;
    }

    @Override
    public HumanoidModel.ArmPose getArmPose(LivingEntity entityLiving, InteractionHand hand, ItemStack itemStack) {
        return (!itemStack.isEmpty() && entityLiving.getUsedItemHand() == hand && entityLiving.getUseItemRemainingTicks() > 0) ? POSE : HumanoidModel.ArmPose.EMPTY;
    }

    @Override
    public boolean applyForgeHandTransform(PoseStack poseStack, LocalPlayer player, HumanoidArm arm, ItemStack itemInHand, float partialTick, float equipProcess, float swingProcess) {
        if (player.isUsingItem() && player.getUseItemRemainingTicks() > 0) {
            int modifier = arm == HumanoidArm.RIGHT ? 1 : -1;

            float dY = Mth.abs(Mth.cos((player.getUseItemRemainingTicks() - partialTick + 1.0F) / 4.0F * (float) Math.PI) * 0.5F)  - 0.1f;
            float dZ = modifier * -0.2f;

            poseStack.translate(modifier * 0.3F, -0.4F, -0.4F);

            if (player.getUseItem() == itemInHand) {
                poseStack.mulPose(Axis.YP.rotationDegrees(modifier * 15));
                poseStack.mulPose(Axis.XP.rotationDegrees(-60));

                poseStack.translate(0.0, dY, dZ);
            }

            return true;
        }
        return false;
    }
}
