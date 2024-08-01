package net.grid.vampiresdelight.client.extension;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.fml.common.asm.enumextension.EnumProxy;
import net.neoforged.neoforge.client.IArmPoseTransformer;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

public class PourableBottleItemExtension  implements IClientItemExtensions {
    public static final EnumProxy<HumanoidModel.ArmPose> POURING_POSE = new EnumProxy<>(HumanoidModel.ArmPose.class, true, (IArmPoseTransformer) PourableBottleItemExtension::makePose);

    private static void makePose(HumanoidModel<?> model, LivingEntity entity, HumanoidArm arm) {
        boolean isRightArmPouring = arm == HumanoidArm.RIGHT;
        int multiplier = isRightArmPouring ? -1 : 1;

        ModelPart pouringHand = isRightArmPouring ? model.rightArm : model.leftArm;
        ModelPart glassHand = isRightArmPouring ? model.leftArm : model.rightArm;
        ModelPart head = model.head;

        pouringHand.xRot = (float) Math.toRadians(-135) + head.xRot / 2f;
        pouringHand.yRot = (float) Math.toRadians(45) * multiplier + head.yRot / 2.5f;

        glassHand.xRot = (float) Math.toRadians(-50) + head.xRot / 2f;
        glassHand.yRot = (float) Math.toRadians(25) * -multiplier + head.yRot / 2.5f;
    }

    @Override
    public HumanoidModel.ArmPose getArmPose(LivingEntity entityLiving, InteractionHand hand, ItemStack itemStack) {
        return (!itemStack.isEmpty() && entityLiving.getUsedItemHand() == hand && entityLiving.getUseItemRemainingTicks() > 0) ? POURING_POSE.getValue() : HumanoidModel.ArmPose.EMPTY;
    }
}
