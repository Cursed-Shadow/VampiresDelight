package net.grid.vampiresdelight.common.mixin;

import net.grid.vampiresdelight.common.tag.VDTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vectorwing.farmersdelight.common.block.BuddingTomatoBlock;

@Mixin(BuddingTomatoBlock.class)
public class MixinBuddingTomatoBlock {
    @Inject(at = @At(value = "HEAD"), method = "mayPlaceOn(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;)Z", cancellable = true)
    private void mayPlaceOnBloodyFarmland(BlockState pState, BlockGetter pLevel, BlockPos pPos, CallbackInfoReturnable<Boolean> cir) {
        if (pState.is(VDTags.CURSED_FARMLANDS)) {
            cir.setReturnValue(true);
        }
    }
}
