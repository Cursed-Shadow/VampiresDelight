package net.grid.vampiresdelight.common.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class VampireDrinkableItem extends VampireConsumableItem {
    public VampireDrinkableItem(Properties properties, FoodProperties vampireFood) {
        super(properties, vampireFood, true, false, false);
    }

    public VampireDrinkableItem(Properties properties, FoodProperties vampireFood, FoodProperties hunterFood) {
        super(properties, vampireFood, hunterFood);
    }

    @Override
    public int getUseDuration(@NotNull ItemStack stack) {
        return 32;
    }

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack stack) {
        return UseAnim.DRINK;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack heldStack = player.getItemInHand(hand);
        if (heldStack.isEdible()) {
            if (player.canEat(Objects.requireNonNull(heldStack.getFoodProperties(player)).canAlwaysEat())) {
                player.startUsingItem(hand);
                return InteractionResultHolder.consume(heldStack);
            } else {
                return InteractionResultHolder.fail(heldStack);
            }
        }
        return ItemUtils.startUsingInstantly(level, player, hand);
    }
}
