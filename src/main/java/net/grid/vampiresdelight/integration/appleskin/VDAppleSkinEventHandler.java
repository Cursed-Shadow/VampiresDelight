package net.grid.vampiresdelight.integration.appleskin;

import de.teamlapen.vampirism.VampirismMod;
import de.teamlapen.vampirism.items.VampirismItemBloodFoodItem;
import de.teamlapen.vampirism.util.Helper;
import net.grid.vampiresdelight.common.VDConfiguration;
import net.grid.vampiresdelight.common.VDFoodValues;
import net.grid.vampiresdelight.common.item.VampireConsumableItem;
import net.grid.vampiresdelight.common.mixin.accessor.VampirismItemBloodFoodItemAccessor;
import net.grid.vampiresdelight.common.tag.VDTags;
import net.grid.vampiresdelight.common.utility.VDHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import squeek.appleskin.api.event.FoodValuesEvent;
import squeek.appleskin.api.event.TooltipOverlayEvent;
import squeek.appleskin.api.food.FoodValues;

@OnlyIn(Dist.CLIENT)
public class VDAppleSkinEventHandler {
    @SubscribeEvent
    public void onPreTooltipEvent(TooltipOverlayEvent.Pre event) {
        if (VDConfiguration.HIDE_APPLE_SKIN_HUMAN_FOOD_TOOLTIPS_FOR_VAMPIRES.get()) {
            Player player = VampirismMod.proxy.getClientPlayer();
            assert player != null;

            ItemStack itemStack = event.itemStack;
            if (!(VDHelper.isItemOfVampireFoodClass(itemStack.getItem()) || itemStack.is(VDTags.VAMPIRE_FOOD)) && Helper.isVampire(player)) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public void onFoodValuesEvent(FoodValuesEvent event) {
        if (VDConfiguration.CORRECT_APPLE_SKIN_TOOLTIPS.get()) {
            Player player = event.player;
            Item item = event.itemStack.getItem();
            if ((VDHelper.isItemOfVampireFoodClass(item) && Helper.isVampire(player))) {
                FoodProperties foodProperties = VDFoodValues.NASTY;

                if (item instanceof VampireConsumableItem vampireConsumableItem)
                    foodProperties = vampireConsumableItem.getVampireFood();
                if (item instanceof VampirismItemBloodFoodItem bloodFoodItem)
                    foodProperties = ((VampirismItemBloodFoodItemAccessor) bloodFoodItem).getVampireFood();

                event.defaultFoodValues = new FoodValues(foodProperties.getNutrition(), foodProperties.getSaturationModifier());
                event.modifiedFoodValues = new FoodValues(foodProperties.getNutrition(), foodProperties.getSaturationModifier());
            }
        }
    }
}