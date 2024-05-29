package net.grid.vampiresdelight.common.mixin;

import de.teamlapen.vampirism.VampirismMod;
import de.teamlapen.vampirism.util.Helper;
import net.grid.vampiresdelight.common.tag.VDTags;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vectorwing.farmersdelight.common.item.ConsumableItem;
import vectorwing.farmersdelight.common.item.DrinkableItem;

import java.util.List;

@Mixin(ConsumableItem.class)
public class MixinConsumableItem {
    @Inject(method = "appendHoverText(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/level/Level;Ljava/util/List;Lnet/minecraft/world/item/TooltipFlag;)V", at = @At("HEAD"), cancellable = true)
    public void hideFoodTooltipsForVampires(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag isAdvanced, CallbackInfo ci) {
        Player player = VampirismMod.proxy.getClientPlayer();
        if (player != null)
            if (Helper.isVampire(player) && stack.getItem() instanceof ConsumableItem && !(stack.getItem() instanceof DrinkableItem) && !stack.is(VDTags.BLOOD_FOOD))
                ci.cancel();
    }
}
