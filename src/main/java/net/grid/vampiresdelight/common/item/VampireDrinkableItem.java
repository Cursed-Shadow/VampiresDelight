package net.grid.vampiresdelight.common.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

import java.util.Objects;
import java.util.function.Consumer;

public class VampireDrinkableItem extends VampireConsumableItem {
    public VampireDrinkableItem(Properties properties, FoodProperties vampireFood) {
        super(properties, vampireFood, null, true, false, false);
    }

    public VampireDrinkableItem(Properties properties, FoodProperties vampireFood, FoodProperties hunterFood, Consumer<LivingEntity> features) {
        super(properties, vampireFood, hunterFood, features);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack heldStack = player.getItemInHand(hand);
        if (heldStack.getFoodProperties(player) != null) {
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
