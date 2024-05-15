package net.grid.vampiresdelight.common.mixin;

import de.teamlapen.vampirism.core.ModItems;
import de.teamlapen.vampirism.core.ModTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.PowderSnowBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PowderSnowBlock.class)
public class MixinPowderSnowBlock {
    @Inject(at = @At("HEAD"), method = "canEntityWalkOnPowderSnow(Lnet/minecraft/world/entity/Entity;)Z", cancellable = true)
    private static void canHunterWalkOnPowderSnow(Entity pEntity, CallbackInfoReturnable<Boolean> cir) {
        if (pEntity instanceof LivingEntity livingEntity) {
            ItemStack boots = livingEntity.getItemBySlot(EquipmentSlot.FEET);
             if (boots.is(ModTags.Items.HUNTER_COAT) || boots.getItem() == ModItems.ARMOR_OF_SWIFTNESS_FEET_NORMAL.get() || boots.getItem() == ModItems.ARMOR_OF_SWIFTNESS_FEET_ENHANCED.get() || boots.getItem() == ModItems.ARMOR_OF_SWIFTNESS_FEET_ULTIMATE.get())
                 cir.setReturnValue(true);
        }
    }
}
