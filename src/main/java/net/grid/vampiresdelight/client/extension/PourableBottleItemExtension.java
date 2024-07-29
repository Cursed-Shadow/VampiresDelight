package net.grid.vampiresdelight.client.extension;

import net.grid.vampiresdelight.VampiresDelight;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

// TODO: Fix this and find how to use it with bottles as it's now off
public class PourableBottleItemExtension  implements IClientItemExtensions {
    private static final HumanoidModel.ArmPose POSE = HumanoidModel.ArmPose.create(VampiresDelight.MODID + "_pouring_pose", true, (model, entity, arm) -> {
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
}
