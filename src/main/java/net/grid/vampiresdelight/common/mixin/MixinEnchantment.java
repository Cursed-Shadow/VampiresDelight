package net.grid.vampiresdelight.common.mixin;

import de.teamlapen.vampirism.core.ModItems;
import net.grid.vampiresdelight.common.VDConfiguration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vectorwing.farmersdelight.FarmersDelight;

// TODO: Find an alternative way to make hunter weapons enchantable with Backstabbing
/*
@Mixin(Enchantment.class)
public class MixinEnchantment {
    @Inject(at = @At("RETURN"), method = "canEnchant", cancellable = true)
    public void canBackstabbingBeApplied(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        ResourceLocation resourceLocation = ResourceLocation.fromNamespaceAndPath(FarmersDelight.MODID, "backstabbing");
        if (ForgeRegistries.ENCHANTMENTS.getValue(resourceLocation) != null)
            if (((Object) this == ForgeRegistries.ENCHANTMENTS.getValue(resourceLocation)))
                if (VDConfiguration.BACKSTABBING_CAN_BE_APPLIED_TO_HUNTER_WEAPON.get())
                    if (stack.is(ModItems.HUNTER_AXE_NORMAL.get()) || stack.is(ModItems.HUNTER_AXE_ENHANCED.get()) || stack.is(ModItems.HUNTER_AXE_ULTIMATE.get()) || stack.is(ModItems.STAKE.get()))
                        cir.setReturnValue(true);
    }
}
 */
