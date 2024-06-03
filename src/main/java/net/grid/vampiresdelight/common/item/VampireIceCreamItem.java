package net.grid.vampiresdelight.common.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class VampireIceCreamItem extends VampireConsumableItem {
    public VampireIceCreamItem(Properties properties, FoodProperties vampireFood) {
        super(properties, vampireFood, true, false, false, true);
    }

    public VampireIceCreamItem(Properties properties, FoodProperties vampireFood, boolean hasFoodEffectTooltip, boolean hasCustomTooltip, boolean hasHumanFoodEffectTooltip, boolean hasFactionTooltip) {
        super(properties, vampireFood, hasFoodEffectTooltip, hasCustomTooltip, hasHumanFoodEffectTooltip, hasFactionTooltip);
    }

    @Override
    public void affectConsumer(ItemStack stack, Level level, LivingEntity consumer) {
        consumer.clearFire();
    }
}
