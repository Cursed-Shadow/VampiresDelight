package net.grid.vampiresdelight.common.mixin;

import net.grid.vampiresdelight.common.utility.VDEntityUtils;

// TODO: Make it work somehow, because the initial method got changed
/*
@Mixin(FoodHelper.class)
public class MixinFoodHelper {
    @Inject(method = "isRotten(Lnet/minecraft/world/food/FoodProperties;)Z", at = @At("HEAD"), remap = false, cancellable = true)
    private static void isVampireFood(FoodProperties foodProperties, CallbackInfoReturnable<Boolean> cir) {
        if (isFood(itemStack, player)) {
            if ((itemStack.getItem() instanceof VampirismItemBloodFoodItem || itemStack.is(VDTags.VAMPIRE_FOOD)) && VDHelper.isVampire(player)) {
                FoodProperties foodProperties = null;

                if (itemStack.getItem() instanceof VampirismItemBloodFoodItem bloodFoodItem)
                    foodProperties = ((VampirismItemBloodFoodItemAccessor) bloodFoodItem).getVampireFood();

                cir.setReturnValue(VDEntityUtils.hasPoison(foodProperties));
            }

            if (VDHelper.isSame(itemStack.getItem(), VDIntegrationUtils.WOLF_BERRIES) && !VDIntegrationUtils.isWerewolf(player)) {
                cir.setReturnValue(true);
            }

            if (itemStack.is(VDTags.NOT_ROTTEN_FOOD)) {
                cir.setReturnValue(false);
            }
        }
    }
}
 */
