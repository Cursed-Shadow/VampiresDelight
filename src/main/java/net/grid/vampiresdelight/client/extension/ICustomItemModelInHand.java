package net.grid.vampiresdelight.client.extension;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

/**
 * Credits to the Moonlight Lib
 * <a href="https://github.com/MehVahdJukaar/Moonlight">...</a>
 */
public interface ICustomItemModelInHand {
    default boolean renderThirdPersonItem(HumanoidModel<?> parentModel, LivingEntity livingEntity, ItemStack itemStack, ItemDisplayContext displayContext, HumanoidArm arm, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        return false;
    }
}
