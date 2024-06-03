package net.grid.vampiresdelight.common.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class WerewolfIceCreamItem extends WerewolfConsumableItem {
    public WerewolfIceCreamItem(Properties pProperties, FoodProperties werewolfFood) {
        super(pProperties, werewolfFood);
    }

    @Override
    public void affectConsumer(ItemStack stack, Level level, LivingEntity consumer) {
        consumer.clearFire();
    }
}
