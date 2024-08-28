package net.grid.vampiresdelight.integration.appleskin;

import de.teamlapen.vampirism.VampirismMod;
import de.teamlapen.vampirism.items.VampirismItemBloodFoodItem;
import net.grid.vampiresdelight.common.VDConfiguration;
import net.grid.vampiresdelight.common.item.VampireConsumableItem;
import net.grid.vampiresdelight.common.registry.VDDataComponents;
import net.grid.vampiresdelight.common.tag.VDTags;
import net.grid.vampiresdelight.common.utility.VDHelper;
import net.grid.vampiresdelight.common.utility.VDIntegrationUtils;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import squeek.appleskin.api.event.HUDOverlayEvent;
import squeek.appleskin.api.event.TooltipOverlayEvent;


@OnlyIn(Dist.CLIENT)
@SuppressWarnings("unused")
public class VDAppleSkinEventHandler {
    @SubscribeEvent
    public void onPreTooltipEvent(TooltipOverlayEvent.Pre event) {
        Player player = VampirismMod.proxy.getClientPlayer();
        ItemStack itemStack = event.itemStack;
        Item item = itemStack.getItem();

        if (player != null) {
            if (VDHelper.isVampire(player) && VDConfiguration.HIDE_APPLE_SKIN_HUMAN_FOOD_TOOLTIPS_FOR_VAMPIRES.get() && !(item instanceof VampireConsumableItem || item instanceof VampirismItemBloodFoodItem || itemStack.is(VDTags.VAMPIRE_FOOD) || itemStack.is(VDTags.BLOOD_FOOD) || itemStack.get(VDDataComponents.VAMPIRE_FOOD.get()) != null || VDHelper.isSame(item, VDIntegrationUtils.LIVER))) {
                event.setCanceled(true);
            }

            if (VDIntegrationUtils.isModPresent(VDIntegrationUtils.WEREWOLVES) && VDIntegrationUtils.isWerewolf(player) && VDConfiguration.HIDE_APPLE_SKIN_HUMAN_FOOD_TOOLTIPS_FOR_WEREWOLVES.get() && !VDIntegrationUtils.canWerewolfEatFood(player, itemStack)) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public void onSaturationHUDOverlayEvent(HUDOverlayEvent.Saturation event) {
        Player player = VampirismMod.proxy.getClientPlayer();

        if (player != null && VDHelper.isVampire(player)) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onSaturationHUDOverlayEvent(HUDOverlayEvent.HungerRestored event) {
        Player player = VampirismMod.proxy.getClientPlayer();

        if (player != null && VDHelper.isVampire(player)) {
            event.setCanceled(true);
        }
    }
}
