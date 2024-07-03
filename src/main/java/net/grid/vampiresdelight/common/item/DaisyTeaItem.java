package net.grid.vampiresdelight.common.item;

import de.teamlapen.vampirism.VampirismMod;
import net.grid.vampiresdelight.common.food.VDFoodFeatures;
import net.grid.vampiresdelight.common.utility.VDEntityUtils;
import net.grid.vampiresdelight.common.utility.VDTooltipUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import vectorwing.farmersdelight.common.Configuration;
import vectorwing.farmersdelight.common.item.DrinkableItem;

import javax.annotation.Nullable;
import java.util.List;

public class DaisyTeaItem extends DrinkableItem {
    public DaisyTeaItem(Properties properties) {
        super(properties, false, true);
    }

    @Override
    public void affectConsumer(ItemStack stack, Level level, LivingEntity consumer) {
        VDFoodFeatures.DAISY_TEA.execute(consumer);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag isAdvanced) {
        Player player = VampirismMod.proxy.getClientPlayer();

        if (Configuration.FOOD_EFFECT_TOOLTIP.get() && player != null && VDEntityUtils.canConsumeHumanFood(player)) {
            VDTooltipUtils.addSplitTooltip("tooltip." + this, tooltip, ChatFormatting.BLUE);
        }
    }
}
