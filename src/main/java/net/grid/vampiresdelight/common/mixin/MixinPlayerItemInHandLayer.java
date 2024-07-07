package net.grid.vampiresdelight.common.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.grid.vampiresdelight.client.extension.IThirdPersonItemRenderExtension;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.PlayerItemInHandLayer;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Credits to the Moonlight Lib
 * <a href="https://github.com/MehVahdJukaar/Moonlight">...</a>
 */
@Mixin(PlayerItemInHandLayer.class)
public class MixinPlayerItemInHandLayer {
    @Inject(method = "renderArmWithItem", at = @At("HEAD"), cancellable = true)
    private void vampiresdelight$renderArmWithItem(LivingEntity pLivingEntity, ItemStack pItemStack, ItemDisplayContext pDisplayContext, HumanoidArm pArm, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, CallbackInfo ci) {
        if (pItemStack.getItem() instanceof IThirdPersonItemRenderExtension extension) {
            extension.renderThirdPersonItem((HumanoidModel<?>) ((PlayerItemInHandLayer<?, ?>) (Object) this).getParentModel(), pLivingEntity, pItemStack, pDisplayContext, pArm, pPoseStack, pBuffer, pPackedLight);
            ci.cancel();
        }
    }
}
