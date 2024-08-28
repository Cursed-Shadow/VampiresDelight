package net.grid.vampiresdelight.common.item;

import de.teamlapen.vampirism.api.VReference;
import net.grid.vampiresdelight.common.utility.VDEntityUtils;
import net.grid.vampiresdelight.common.utility.VDHelper;
import net.grid.vampiresdelight.common.utility.VDTooltipUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public class HunterConsumableItem extends FactionConsumableItem {
    public HunterConsumableItem(Properties properties, @Nullable Consumer<LivingEntity> features) {
        super(properties, features, false, false, false, true);
    }

    public HunterConsumableItem(Properties properties, @Nullable Consumer<LivingEntity> features, boolean hasFoodEffectTooltip) {
        super(properties, features, hasFoodEffectTooltip, false, false, true);
    }

    public HunterConsumableItem(Properties properties, @Nullable Consumer<LivingEntity> features, boolean hasFoodEffectTooltip, boolean hasCustomTooltip, boolean containsGarlic) {
        super(properties, features, hasFoodEffectTooltip, hasCustomTooltip, false, containsGarlic);
    }

    @Override
    public boolean hasCustomTooltip(ItemStack stack, @Nullable Player player) {
        return VDEntityUtils.canConsumeHumanFood(player) && super.hasCustomTooltip(stack, player);
    }

    @Override
    public boolean hasFoodEffectTooltip(ItemStack stack, @Nullable Player player) {
        return VDEntityUtils.canConsumeHumanFood(player) && super.hasFoodEffectTooltip(stack, player);
    }

    @Override
    public boolean hasFactionTooltip(ItemStack stack, Player player) {
        return VDHelper.isVampire(player);
    }

    @Override
    public void addFactionFoodTooltip(List<Component> tooltipComponents, Player player) {
        VDTooltipUtils.addFactionFoodToolTips(tooltipComponents, player, VReference.HUNTER_FACTION);
    }
}
