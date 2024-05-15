package net.grid.vampiresdelight.common.item;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class HardtackItem extends HunterConsumableItem {
    public HardtackItem(Properties properties, FoodProperties hunterFood) {
        super(properties, hunterFood, false, false, false);
    }

    @Override
    public int getUseDuration(@NotNull ItemStack stack) {
        return 48;
    }
}
