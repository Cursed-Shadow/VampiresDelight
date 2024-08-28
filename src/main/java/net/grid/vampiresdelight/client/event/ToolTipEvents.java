package net.grid.vampiresdelight.client.event;

import de.teamlapen.vampirism.VampirismMod;
import de.teamlapen.vampirism.api.VReference;
import de.teamlapen.vampirism.items.BloodBottleItem;
import de.teamlapen.vampirism.items.GarlicBreadItem;
import de.teamlapen.vampirism.items.VampirismItemBloodFoodItem;
import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.VDConfiguration;
import net.grid.vampiresdelight.common.item.FactionConsumableItem;
import net.grid.vampiresdelight.common.tag.VDTags;
import net.grid.vampiresdelight.common.utility.VDHelper;
import net.grid.vampiresdelight.common.utility.VDIntegrationUtils;
import net.grid.vampiresdelight.common.utility.VDNameUtils;
import net.grid.vampiresdelight.common.utility.VDTooltipUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderTooltipEvent;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

import java.awt.*;
import java.util.List;

@SuppressWarnings("unused")
@EventBusSubscriber(modid = VampiresDelight.MODID, value = Dist.CLIENT)
public class ToolTipEvents {
    @SubscribeEvent
    public static void onTooltipColorEvent(RenderTooltipEvent.Color event) {
        ItemStack stack = event.getItemStack();
        Item item = stack.getItem();
        Player player = VampirismMod.proxy.getClientPlayer();

        if (!VDConfiguration.COLORED_TOOLTIPS.get() || stack.isEmpty()) {
            return;
        }

        if (item instanceof FactionConsumableItem factionConsumableItem && !factionConsumableItem.hasColoredTooltipMargins(stack, player)) {
            return;
        }

        // If the item isn't from Vampire's Delight and coloredTooltipsForVampirismItems is set to false, the tooltips won't be colored
        if (!VDNameUtils.isItemFrom(item, VampiresDelight.MODID) && !VDConfiguration.COLORED_TOOLTIPS_FOR_VAMPIRISM_ITEMS.get()) {
            return;
        }

        if (stack.is(VDTags.VAMPIRE_FOOD)) {
            setBorderColors(VDConfiguration.VAMPIRE_FOOD_TOOLTIP_START_COLOR, VDConfiguration.VAMPIRE_FOOD_TOOLTIP_END_COLOR, event);
        } else if (stack.is(VDTags.HUNTER_FOOD) && (player != null && VDHelper.isVampire(player) || VDConfiguration.HUNTER_TOOLTIPS_FOR_EVERYONE.get())) {
            setBorderColors(VDConfiguration.HUNTER_FOOD_TOOLTIP_START_COLOR, VDConfiguration.HUNTER_FOOD_TOOLTIP_END_COLOR, event);
        } else if (stack.is(VDTags.WEREWOLF_ONLY_FOOD)) {
            setBorderColors(VDConfiguration.WEREWOLF_FOOD_TOOLTIP_START_COLOR, VDConfiguration.WEREWOLF_FOOD_TOOLTIP_END_COLOR, event);
        }
    }

    // Color values must be in HEX
    public static void setBorderColors(ModConfigSpec.ConfigValue<String> startColorConfigValue, ModConfigSpec.ConfigValue<String> endColorConfigValue, RenderTooltipEvent.Color event) {
        Color startColor, endColor;

        try {
            startColor = Color.decode(startColorConfigValue.get());
        } catch (NumberFormatException e) {
            startColor = Color.decode(startColorConfigValue.getDefault());
        }
        try {
            endColor = Color.decode(endColorConfigValue.get());
        } catch (NumberFormatException e) {
            endColor = Color.decode(endColorConfigValue.getDefault());
        }

        event.setBorderStart(startColor.getRGB());
        event.setBorderEnd(endColor.getRGB());
    }

    @SubscribeEvent
    public static void addVDTooltipsToOtherItems(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        Item item = stack.getItem();
        List<Component> tooltipComponents = event.getToolTip();
        Player player = VampirismMod.proxy.getClientPlayer();

        if (player == null) {
            return;
        }

        if (VDConfiguration.FACTION_TOOLTIPS_FOR_VAMPIRISM_ITEMS.get()) {
            if (item instanceof VampirismItemBloodFoodItem || item instanceof BloodBottleItem) {
                VDTooltipUtils.addFactionFoodToolTips(tooltipComponents, player, VReference.VAMPIRE_FACTION);
            } else if (item instanceof GarlicBreadItem) {
                VDTooltipUtils.addFactionFoodToolTips(tooltipComponents, player, VReference.HUNTER_FACTION);
            } else if (VDHelper.isSame(item, VDIntegrationUtils.WOLF_BERRIES)) {
                VDTooltipUtils.addWerewolfFactionFoodToolTips(tooltipComponents, player); // TODO: Check if it works when Werewolves updates
            }
        }

        if (stack.getFoodProperties(player) != null) {
            VDTooltipUtils.addFoodPropertiesDebugTooltip(stack, tooltipComponents);
        }
    }
}
