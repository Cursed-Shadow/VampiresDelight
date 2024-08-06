package net.grid.vampiresdelight.common.mixin;

import net.grid.vampiresdelight.common.registry.VDBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.levelgen.feature.Feature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Feature.class)
public class MixinKeepBloodySoilGiantTree {
    @Inject(at = @At(value = "HEAD"), method = "isGrassOrDirt", cancellable = true)
    private static void keepRichSoil(LevelSimulatedReader world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if (world.isStateAtPosition(pos, state -> state.is(VDBlocks.BLOODY_SOIL.get()))) {
            cir.setReturnValue(false);
            cir.cancel();
        }
    }
}
