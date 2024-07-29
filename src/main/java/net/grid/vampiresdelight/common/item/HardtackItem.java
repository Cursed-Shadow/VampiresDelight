package net.grid.vampiresdelight.common.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;

public class HardtackItem extends HunterConsumableItem {
    public HardtackItem(Properties properties, FoodProperties hunterFood, boolean hasFoodEffectTooltip) {
        super(properties, hunterFood, null, hasFoodEffectTooltip, false, false);
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 48;
    }
}
