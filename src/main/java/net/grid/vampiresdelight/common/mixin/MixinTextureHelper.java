package net.grid.vampiresdelight.common.mixin;

import de.teamlapen.vampirism.VampirismMod;
import de.teamlapen.vampirism.client.gui.overlay.BloodBarOverlay;
import net.grid.vampiresdelight.common.utility.VDHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import squeek.appleskin.helpers.TextureHelper;

//TODO: Would be cool if it worked... but it does not. Changing textures fully for vampires in tooltips would require too many mixins
/*
@Mixin(TextureHelper.class)
public class MixinTextureHelper {
    @Inject(at = @At("HEAD"), method = "getFoodTexture(ZLsqueek/appleskin/helpers/TextureHelper$FoodType;)Lnet/minecraft/resources/ResourceLocation;", cancellable = true)
    private static void hideFoodTooltips(boolean isRotten, TextureHelper.FoodType type, CallbackInfoReturnable<ResourceLocation> cir) {
        Player player = VampirismMod.proxy.getClientPlayer();
        if (player != null && VDHelper.isVampire(player)) {
            cir.setReturnValue(switch (type) {
                case EMPTY -> BloodBarOverlay.BACKGROUND;
                case HALF -> BloodBarOverlay.QUARTER;
                case FULL -> BloodBarOverlay.HALF;
            });
        }
    }
}
 */
