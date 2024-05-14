package net.grid.vampiresdelight.common.mixin;

import com.mojang.datafixers.util.Pair;
import de.teamlapen.vampirism.items.VampirismItemBloodFoodItem;
import de.teamlapen.vampirism.util.Helper;
import net.grid.vampiresdelight.common.item.VampireConsumableItem;
import net.grid.vampiresdelight.common.mixin.accessor.VampirismItemBloodFoodItemAccessor;
import net.grid.vampiresdelight.common.registry.VDItems;
import net.grid.vampiresdelight.common.tag.VDTags;
import net.grid.vampiresdelight.common.utility.VDHelper;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import squeek.appleskin.helpers.FoodHelper;

import java.util.List;

import static squeek.appleskin.helpers.FoodHelper.isFood;

@Mixin(FoodHelper.class)
public class MixinFoodHelper {
    @Inject(at = @At("HEAD"), method = "isRotten(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/player/Player;)Z", remap = false, cancellable = true)
    private static void isVampireFood(ItemStack itemStack, Player player, CallbackInfoReturnable<Boolean> cir) {
        if (isFood(itemStack, player) && (VDHelper.isItemOfVampireFoodClass(itemStack.getItem()) || itemStack.is(VDTags.VAMPIRE_FOOD)) && Helper.isVampire(player)) {
            FoodProperties foodProperties = null;

            if (itemStack.getItem() instanceof VampireConsumableItem vampireConsumableItem)
                foodProperties = vampireConsumableItem.getVampireFood();
            if (itemStack.getItem() instanceof VampirismItemBloodFoodItem bloodFoodItem)
                foodProperties = ((VampirismItemBloodFoodItemAccessor) bloodFoodItem).getVampireFood();

            boolean hasPoison = false;

            if (foodProperties != null) {
                for (Pair<MobEffectInstance, Float> effect : foodProperties.getEffects()) {
                    if (effect.getFirst() != null) {
                        effect.getFirst().getEffect();
                        if (effect.getFirst().getEffect() == MobEffects.POISON)
                            hasPoison = true;
                    }
                }
            }

            cir.setReturnValue(hasPoison);
        }

        List<Item> FOOD_CONTAINING_BAT = List.of(
                VDItems.COOKED_BAT.get(),
                VDItems.COOKED_BAT_CHOPS.get(),
                VDItems.BAT_TACO.get()
        );

        if (FOOD_CONTAINING_BAT.contains(itemStack.getItem()))
            cir.setReturnValue(false);
    }
}
