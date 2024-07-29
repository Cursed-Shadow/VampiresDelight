package net.grid.vampiresdelight.common.item;

import de.teamlapen.vampirism.VampirismMod;
import net.grid.vampiresdelight.common.utility.VDEntityUtils;
import net.grid.vampiresdelight.common.utility.VDTextUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import vectorwing.farmersdelight.common.Configuration;
import vectorwing.farmersdelight.common.item.DrinkableItem;

import java.util.List;

public class DandelionBeerMugItem extends DrinkableItem {
    public DandelionBeerMugItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag isAdvanced) {
        Player player = VampirismMod.proxy.getClientPlayer();

        if (Configuration.FOOD_EFFECT_TOOLTIP.get() && player != null & VDEntityUtils.canConsumeHumanFood(player)) {
            VDTextUtils.addFoodEffectTooltip(stack, player, tooltip, context);
        }
    }
}
