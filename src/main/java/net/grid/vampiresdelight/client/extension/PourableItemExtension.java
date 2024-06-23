package net.grid.vampiresdelight.client.extension;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

public class PourableItemExtension implements IClientItemExtensions {
    private static final HumanoidModel.ArmPose POSE = HumanoidModel.ArmPose.create("vd_pouring_pose", true, (model, entity, arm) -> {
        if (arm == HumanoidArm.RIGHT) {
            poseRightArm(model);
        } else {
            poseLeftArm(model);
        }
    });

    public static void poseRightArm(HumanoidModel<?> model) {
        /*
        model.rightArm.xRot = model.rightArm.xRot * 0.5F - (float) (Math.PI / 5);
        model.rightArm.yRot = 0.0F;
         */

        model.rightArm.xRot = (float) (-Math.PI / 2);
        model.rightArm.zRot = (float) (-Math.PI / 2);
        model.rightArm.yRot = 0.0F;

        float shakeAmplitude = 0.1F; // Amplitude of the shake
        float shakeFrequency = 10.0F; // Frequency of the shake

        //model.rightArm.xRot += shakeAmplitude * Math.sin(time * shakeFrequency);
        //model.rightArm.zRot += shakeAmplitude * Math.cos(time * shakeFrequency);
    }

    public static void poseLeftArm(HumanoidModel<?> model) {
        model.leftArm.xRot = model.leftArm.xRot * 0.5F - (float) (Math.PI / 5);
        model.leftArm.yRot = 0.0F;
    }

    @Override
    public HumanoidModel.ArmPose getArmPose(LivingEntity entityLiving, InteractionHand hand, ItemStack itemStack) {
        if (!itemStack.isEmpty()) {
            if (entityLiving.getUsedItemHand() == hand && entityLiving.getUseItemRemainingTicks() > 0) {
                return POSE;
            }
        }
        return HumanoidModel.ArmPose.EMPTY;
    }

    @Override
    public boolean applyForgeHandTransform(PoseStack poseStack, LocalPlayer player, HumanoidArm arm, ItemStack itemInHand, float partialTick, float equipProcess, float swingProcess) {
        int modifier = (arm == HumanoidArm.RIGHT ? 1 : -1);

        float dY = Mth.abs(Mth.cos((player.getUseItemRemainingTicks() - partialTick + 1.0F) / 4.0F * (float) Math.PI) * 0.5F)  - 0.1f;
        float dZ = modifier * -0.25f;

        poseStack.translate(modifier * 0.3F, -0.4F, -0.4F);

        if (player.getUseItem() == itemInHand && player.isUsingItem()) {
            poseStack.mulPose(Axis.YP.rotationDegrees(modifier * 15));
            poseStack.mulPose(Axis.XP.rotationDegrees(-60));

            poseStack.translate(0.0, dY, dZ);
        }

        return true;
    }
}
