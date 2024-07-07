package net.grid.vampiresdelight.common.item;

import com.mojang.blaze3d.vertex.PoseStack;
import net.grid.vampiresdelight.client.extension.IThirdPersonItemRenderExtension;
import net.grid.vampiresdelight.client.extension.SpiritLanternExtension;
import net.grid.vampiresdelight.common.utility.VDTextUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public class SpiritLanternItem extends BlockItem implements IThirdPersonItemRenderExtension {
    public SpiritLanternItem(Block pBlock, Properties pProperties) {
        super(pBlock, pProperties);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        pTooltip.add(VDTextUtils.getTranslation("tooltip.spirit_lantern").withStyle(ChatFormatting.GRAY));
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new SpiritLanternExtension());
    }

    @Override
    public void renderThirdPersonItem(HumanoidModel<?> parentModel, LivingEntity livingEntity, ItemStack itemStack, ItemDisplayContext displayContext, HumanoidArm arm, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        SpiritLanternExtension.renderThirdPersonItem(parentModel, livingEntity, itemStack, displayContext, arm, poseStack, buffer, packedLight);
    }
}
