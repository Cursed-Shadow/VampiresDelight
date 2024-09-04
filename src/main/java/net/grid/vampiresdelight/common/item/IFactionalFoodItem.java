package net.grid.vampiresdelight.common.item;

import net.grid.vampiresdelight.common.registry.VDDataComponents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public interface IFactionalFoodItem {
    default @Nullable FoodProperties getVampireFoodProperties(ItemStack stack, @Nullable LivingEntity entity) {
        return stack.get(VDDataComponents.VAMPIRE_FOOD);
    }

    default @Nullable FoodProperties getHunterFoodProperties(ItemStack stack, @Nullable LivingEntity entity) {
        return stack.get(VDDataComponents.HUNTER_FOOD);
    }

    default @Nullable FoodProperties getWerewolfFoodProperties(ItemStack stack, @Nullable LivingEntity entity) {
        return stack.get(VDDataComponents.WEREWOLF_FOOD);
    }
}
