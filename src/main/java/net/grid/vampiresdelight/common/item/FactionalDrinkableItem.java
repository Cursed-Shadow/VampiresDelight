package net.grid.vampiresdelight.common.item;

import net.grid.vampiresdelight.common.utility.VDEntityUtils;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class FactionalDrinkableItem extends FactionalConsumableItem {

    private final boolean onlyHumanTooltip;

    public FactionalDrinkableItem(Properties properties, @Nullable Consumer<LivingEntity> features, boolean hasFoodEffectTooltip, boolean hasCustomTooltip) {
        super(properties, features, hasFoodEffectTooltip, hasCustomTooltip, false, false);
        this.onlyHumanTooltip = true;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 32;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        return useDrink(level, player, hand);
    }

    @Override
    public boolean hasAnyFoodTooltip(ItemStack stack, @Nullable Player player) {
        return onlyHumanTooltip ? player != null && VDEntityUtils.canConsumeHumanFood(player) : super.hasCustomTooltip(stack, player);
    }
}
