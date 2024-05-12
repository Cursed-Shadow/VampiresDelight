package net.grid.vampiresdelight.common.mixin;

import de.teamlapen.vampirism.core.ModOils;
import de.teamlapen.vampirism.items.oil.EffectWeaponOil;
import de.teamlapen.vampirism.util.OilUtils;
import de.teamlapen.vampirism.world.loot.functions.SetOilFunction;
import net.grid.vampiresdelight.common.registry.VDEffects;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SetOilFunction.class)
public class MixinSetOilFunction {
    @Inject(at = @At("TAIL"), method = "run(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/level/storage/loot/LootContext;)Lnet/minecraft/world/item/ItemStack;", remap = false, cancellable = true)
    public void checkOilBeforeRun(ItemStack pStack, LootContext pContext, CallbackInfoReturnable<ItemStack> cir) {
        if (OilUtils.getOil(pStack) instanceof EffectWeaponOil effectOil)
            if (effectOil.getEffect() == VDEffects.CLOTHES_DISSOLVING.get())
                cir.setReturnValue(OilUtils.createOilItem(ModOils.VAMPIRE_BLOOD.get()));
    }
}
