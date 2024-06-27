package net.grid.vampiresdelight.common.mixin;

import net.grid.vampiresdelight.common.item.ICustomUseItem;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Credits to the Create mod
 * <a href="https://github.com/Creators-of-Create/Create">...</a>
 */
@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity extends Entity {
    public MixinLivingEntity(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Shadow
    public abstract ItemStack getUseItem();

    @Inject(method = "shouldTriggerItemUseEffects()Z", at = @At("HEAD"), cancellable = true)
    private void create$onShouldTriggerUseEffects(CallbackInfoReturnable<Boolean> cir) {
        Item usedItem = getUseItem().getItem();
        if (usedItem instanceof ICustomUseItem customUseItem) {
            Boolean result = customUseItem.hasCustomUseEffects();
            cir.setReturnValue(result);
        }
    }

    @Inject(method = "triggerItemUseEffects(Lnet/minecraft/world/item/ItemStack;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;getUseAnimation()Lnet/minecraft/world/item/UseAnim;", ordinal = 0), cancellable = true)
    private void create$onTriggerUseEffects(ItemStack stack, int amount, CallbackInfo ci) {
        Item usedItem = stack.getItem();
        if (usedItem instanceof ICustomUseItem customUseItem) {
            if (customUseItem.triggerUseEffects(stack, (LivingEntity) (Object) this, amount)) {
                ci.cancel();
            }
        }
    }
}
