package net.grid.vampiresdelight.common.mixin;

import net.grid.vampiresdelight.common.VDConfiguration;
import net.grid.vampiresdelight.common.item.FactionalConsumableItem;
import net.grid.vampiresdelight.common.item.IFactionalFoodItem;
import net.grid.vampiresdelight.common.utility.VDEntityUtils;
import net.grid.vampiresdelight.common.utility.VDHelper;
import net.grid.vampiresdelight.common.utility.VDIntegrationUtils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.extensions.IItemExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public abstract class MixinItem implements IItemExtension, IFactionalFoodItem {
    @Inject(at = @At("HEAD"), method = "finishUsingItem(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/LivingEntity;)Lnet/minecraft/world/item/ItemStack;", cancellable = true)
    private void finishEatingVampireFood(ItemStack stack, Level level, LivingEntity livingEntity, CallbackInfoReturnable<ItemStack> cir) {
        FoodProperties vampireFood = getVampireFoodProperties(stack, livingEntity);
        if (vampireFood != null && VDConfiguration.OVERRIDE_FOOD_PROPERTIES_FOR_FACTIONS.get()) {
            VDEntityUtils.consumeBloodFood(stack, level, livingEntity);
            cir.setReturnValue(stack);
        }
    }

    @Inject(at = @At("HEAD"), method = "getUseDuration(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/LivingEntity;)I", cancellable = true)
    private void getEatingVampireFoodDuration(ItemStack stack, LivingEntity entity, CallbackInfoReturnable<Integer> cir) {
        FoodProperties vampireFood = getVampireFoodProperties(stack, entity);
        if (vampireFood != null && VDConfiguration.OVERRIDE_FOOD_PROPERTIES_FOR_FACTIONS.get()) {
            cir.setReturnValue(vampireFood.eatDurationTicks());
        }
    }

    @Override
    public @Nullable FoodProperties getFoodProperties(@NotNull ItemStack stack, @Nullable LivingEntity entity) {
        // Do NOT "fix" this warning, it would just break everything.
        if (entity != null && (VDConfiguration.OVERRIDE_FOOD_PROPERTIES_FOR_FACTIONS.get() || (Item) (Object) this instanceof FactionalConsumableItem)) {
            if (VDHelper.isVampire(entity)) {
                FoodProperties vampireFood = getVampireFoodProperties(stack, entity);
                return vampireFood == null ? new FoodProperties.Builder().build() : vampireFood;
            }

            if (VDHelper.isHunter(entity) && getHunterFoodProperties(stack, entity) != null) {
                return getHunterFoodProperties(stack, entity);
            }

            if (VDIntegrationUtils.isWerewolf(entity) && getWerewolfFoodProperties(stack, entity) != null) {
                return getWerewolfFoodProperties(stack, entity);
            }
        }

        return IItemExtension.super.getFoodProperties(stack, entity);
    }
}
