package net.grid.vampiresdelight.common.mixin;

import net.grid.vampiresdelight.common.utility.VDHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vectorwing.farmersdelight.client.gui.HUDOverlays;

@Mixin(HUDOverlays.NourishmentOverlay.class)
public class MixinNourishmentOverlay {
    @Inject(method = "shouldRenderOverlay(Lnet/minecraft/client/Minecraft;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/client/gui/GuiGraphics;I)Z", at = @At("HEAD"), cancellable = true)
    private void stopRenderingOverlayForVampire(Minecraft mc, Player player, GuiGraphics guiGraphics, int guiTicks, CallbackInfoReturnable<Boolean> cir) {
        if (VDHelper.isVampire(player)) {
            cir.setReturnValue(false);
        }
    }
}
