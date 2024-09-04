package net.grid.vampiresdelight.common.mixin;

import de.teamlapen.vampirism.core.ModOils;
import de.teamlapen.vampirism.items.OilBottleItem;
import de.teamlapen.vampirism.util.ItemDataUtils;
import de.teamlapen.vampirism.world.loot.functions.SetOilFunction;
import net.grid.vampiresdelight.common.registry.VDOils;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SetOilFunction.class)
public class MixinSetOilFunction {
    @Inject(at = @At("TAIL"), method = "run(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/level/storage/loot/LootContext;)Lnet/minecraft/world/item/ItemStack;", cancellable = true)
    public void checkOilBeforeRun(ItemStack pStack, LootContext pContext, CallbackInfoReturnable<ItemStack> cir) {
        if (pStack.getItem() instanceof OilBottleItem oilBottleItem) {
            if (oilBottleItem.getOil(pStack) == VDOils.CLOTHES_DISSOLVING.getDelegate()) {
                cir.setReturnValue(ItemDataUtils.setOil(pStack, ModOils.VAMPIRE_BLOOD));
            }
        }
    }
}
