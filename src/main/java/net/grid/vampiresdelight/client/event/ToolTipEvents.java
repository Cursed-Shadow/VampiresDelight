package net.grid.vampiresdelight.client.event;

import de.teamlapen.vampirism.VampirismMod;
import de.teamlapen.vampirism.api.VReference;
import de.teamlapen.vampirism.items.BloodBottleItem;
import de.teamlapen.vampirism.items.GarlicBreadItem;
import de.teamlapen.vampirism.items.VampirismItemBloodFoodItem;
import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.VDConfiguration;
import net.grid.vampiresdelight.common.tag.VDTags;
import net.grid.vampiresdelight.common.utility.VDHelper;
import net.grid.vampiresdelight.common.utility.VDIntegrationUtils;
import net.grid.vampiresdelight.common.utility.VDTooltipUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderTooltipEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

import java.awt.*;
import java.util.List;

@EventBusSubscriber(modid = VampiresDelight.MODID, value = Dist.CLIENT)
public class ToolTipEvents {
    @SubscribeEvent
    public static void onTooltipColorEvent(RenderTooltipEvent.Color event) {
        ItemStack stack = event.getItemStack();
        Player player = VampirismMod.proxy.getClientPlayer();

        if (!VDConfiguration.COLORED_TOOLTIPS.get() || stack.isEmpty())
            return;

        if (stack.is(VDTags.VAMPIRE_FOOD)) {
            setBorderColors(VDConfiguration.VAMPIRE_FOOD_TOOLTIP_START_COLOR.get(), VDConfiguration.VAMPIRE_FOOD_TOOLTIP_END_COLOR.get(), event);
        } else if (stack.is(VDTags.HUNTER_FOOD) && (player != null && VDHelper.isVampire(player) || VDConfiguration.HUNTER_TOOLTIPS_FOR_EVERYONE.get())) {
            setBorderColors(VDConfiguration.HUNTER_FOOD_TOOLTIP_START_COLOR.get(), VDConfiguration.HUNTER_FOOD_TOOLTIP_END_COLOR.get(), event);
        } else if (stack.is(VDTags.WEREWOLF_ONLY_FOOD)) {
            setBorderColors(VDConfiguration.WEREWOLF_FOOD_TOOLTIP_START_COLOR.get(), VDConfiguration.WEREWOLF_FOOD_TOOLTIP_END_COLOR.get(), event);
        }
    }

    // Color values must be in HEX
    public static void setBorderColors(String borderStartColor, String borderEndColor, RenderTooltipEvent.Color event) {
        event.setBorderStart(Color.decode(borderStartColor).getRGB());
        event.setBorderEnd(Color.decode(borderEndColor).getRGB());
    }

    @SubscribeEvent
    public static void addVDTooltips(ItemTooltipEvent event) {
        ItemStack itemStack = event.getItemStack();
        Item item = itemStack.getItem();
        List<Component> tooltip = event.getToolTip();
        Player player = VampirismMod.proxy.getClientPlayer();

        if (player != null) {
            if (item instanceof VampirismItemBloodFoodItem || item instanceof BloodBottleItem) {
                VDTooltipUtils.addFactionFoodToolTips(tooltip, player, VReference.VAMPIRE_FACTION);
            } else if (item instanceof GarlicBreadItem) {
                VDTooltipUtils.addFactionFoodToolTips(tooltip, player, VReference.HUNTER_FACTION);
            } else if (VDHelper.isSame(item, VDIntegrationUtils.WOLF_BERRIES)) {
                VDTooltipUtils.addWerewolfFactionFoodToolTips(tooltip, player);
            }

            //tooltip.add(Component.literal("Color Test 1").withStyle(Style.EMPTY.withColor(new Color(66, 33, 133).getRGB()).withObfuscated(true)));
            //tooltip.add(Component.literal("Color Test 2").withStyle(Style.EMPTY.withColor(new Color(90, 90, 200).getRGB()).withUnderlined(true)));
        }
    }
}
