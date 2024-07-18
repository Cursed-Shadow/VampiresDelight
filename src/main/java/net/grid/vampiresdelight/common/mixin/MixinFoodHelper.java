package net.grid.vampiresdelight.common.mixin;

import de.teamlapen.vampirism.items.VampirismItemBloodFoodItem;
import net.grid.vampiresdelight.common.mixin.accessor.VampirismItemBloodFoodItemAccessor;
import net.grid.vampiresdelight.common.tag.VDTags;
import net.grid.vampiresdelight.common.utility.VDEntityUtils;
import net.grid.vampiresdelight.common.utility.VDHelper;
import net.grid.vampiresdelight.common.utility.VDIntegrationUtils;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import squeek.appleskin.helpers.FoodHelper;

import static squeek.appleskin.helpers.FoodHelper.isFood;

@Mixin(FoodHelper.class)
public class MixinFoodHelper {
    @Inject(at = @At("HEAD"), method = "isRotten(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/player/Player;)Z", remap = false, cancellable = true)
    private static void isVampireFood(ItemStack itemStack, Player player, CallbackInfoReturnable<Boolean> cir) {
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
