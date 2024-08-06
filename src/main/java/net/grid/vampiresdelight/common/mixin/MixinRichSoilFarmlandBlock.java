package net.grid.vampiresdelight.common.mixin;

import net.grid.vampiresdelight.common.registry.VDBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.util.TriState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vectorwing.farmersdelight.common.block.RichSoilFarmlandBlock;

@Mixin(RichSoilFarmlandBlock.class)
public class MixinRichSoilFarmlandBlock {
    @Inject(at = @At(value = "HEAD"), method = "canSustainPlant(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/Direction;Lnet/minecraft/world/level/block/state/BlockState;)Lnet/neoforged/neoforge/common/util/TriState;", cancellable = true)
    private void cancelSetDirtIfRichSoil(BlockState state, BlockGetter world, BlockPos pos, Direction facing, BlockState plantState, CallbackInfoReturnable<TriState> cir) {
        if (plantState.is(VDBlocks.VAMPIRE_ORCHID_CROP)) {
            cir.setReturnValue(TriState.FALSE);
        }
    }
}
