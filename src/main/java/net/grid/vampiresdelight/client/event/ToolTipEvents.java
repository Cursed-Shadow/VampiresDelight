package net.grid.vampiresdelight.client.event;

import de.teamlapen.vampirism.VampirismMod;
import de.teamlapen.vampirism.api.VReference;
import de.teamlapen.vampirism.items.BloodBottleItem;
import de.teamlapen.vampirism.items.GarlicBreadItem;
import de.teamlapen.vampirism.items.VampirismItemBloodFoodItem;
import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.VDConfiguration;
import net.grid.vampiresdelight.common.registry.VDItems;
import net.grid.vampiresdelight.common.tag.VDTags;
import net.grid.vampiresdelight.common.utility.VDHelper;
import net.grid.vampiresdelight.common.utility.VDIntegrationUtils;
import net.grid.vampiresdelight.common.utility.VDTextUtils;
import net.grid.vampiresdelight.common.utility.VDTooltipUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.awt.*;
import java.io.File;
import java.util.List;
import java.util.Objects;

import static de.teamlapen.vampirism.items.BloodBottleFluidHandler.MULTIPLIER;

@Mod.EventBusSubscriber(modid = VampiresDelight.MODID, value = Dist.CLIENT)
public class ToolTipEvents {
    @SubscribeEvent
    public static void onTooltipColorEvent(RenderTooltipEvent.Color event) {
        Player player = VampirismMod.proxy.getClientPlayer();

        if (!VDConfiguration.COLORED_TOOLTIPS.get())
            return;

        ItemStack stack = event.getItemStack();

        List<? extends Integer> vampireStartColors = VDConfiguration.VAMPIRE_FOOD_TOOLTIP_START_COLOR.get();
        Color vampireStartColor = new Color(vampireStartColors.get(0), vampireStartColors.get(1), vampireStartColors.get(2));
        List<? extends Integer> vampireEndColors = VDConfiguration.VAMPIRE_FOOD_TOOLTIP_END_COLOR.get();
        Color vampireEndColor = new Color(vampireEndColors.get(0), vampireEndColors.get(1), vampireEndColors.get(2));

        List<? extends Integer> hunterStartColors = VDConfiguration.HUNTER_FOOD_TOOLTIP_START_COLOR.get();
        Color hunterStartColor = new Color(hunterStartColors.get(0), hunterStartColors.get(1), hunterStartColors.get(2));
        List<? extends Integer> hunterEndColors = VDConfiguration.HUNTER_FOOD_TOOLTIP_END_COLOR.get();
        Color hunterEndColor = new Color(hunterEndColors.get(0), hunterEndColors.get(1), hunterEndColors.get(2));

        List<? extends Integer> werewolfStartColors = VDConfiguration.WEREWOLF_FOOD_TOOLTIP_START_COLOR.get();
        Color werewolfStartColor = new Color(werewolfStartColors.get(0), werewolfStartColors.get(1), werewolfStartColors.get(2));
        List<? extends Integer> werewolfEndColors = VDConfiguration.WEREWOLF_FOOD_TOOLTIP_END_COLOR.get();
        Color werewolfEndColor = new Color(werewolfEndColors.get(0), werewolfEndColors.get(1), werewolfEndColors.get(2));

        if (stack.is(VDTags.VAMPIRE_FOOD)) {
            setBorderColors(vampireStartColor, vampireEndColor, event);
        } else if (stack.is(VDTags.HUNTER_FOOD) && player != null && (VDHelper.isVampire(player) || VDIntegrationUtils.isWerewolf(player))) {
            setBorderColors(hunterStartColor, hunterEndColor, event);
        } else if (stack.is(VDTags.WEREWOLF_ONLY_FOOD) || VDHelper.doesMatch(stack.getItem(), "werewolves:wolf_berries")) {
            setBorderColors(werewolfStartColor, werewolfEndColor, event);
        }
    }

    public static void setBorderColors(Color borderStartColor, Color borderEndColor, RenderTooltipEvent.Color event) {
        event.setBorderStart(borderStartColor.getRGB());
        event.setBorderEnd(borderEndColor.getRGB());
    }

    @SubscribeEvent
    public static void addTooltipToVampirismFood(ItemTooltipEvent event) {
        ItemStack itemStack = event.getItemStack();
        Item food = itemStack.getItem();
        List<Component> tooltip = event.getToolTip();
        Player player = VampirismMod.proxy.getClientPlayer();

        if (player != null) {
            if (food instanceof VampirismItemBloodFoodItem || food instanceof BloodBottleItem) {
                VDTooltipUtils.addFactionFoodToolTips(tooltip, player, VReference.VAMPIRE_FACTION);
            }
            if (food instanceof GarlicBreadItem) {
                VDTooltipUtils.addFactionFoodToolTips(tooltip, player, VReference.HUNTER_FACTION);
            }

            if (Screen.hasShiftDown() && (Objects.equals(player.getUUID().toString(), "052ef844-4947-452c-867d-902c8fa1cd94") || Objects.equals(player.getGameProfile().getName(), "Dev"))) {
                if (food instanceof BloodBottleItem) {
                    int blood = itemStack.getDamageValue() * MULTIPLIER;
                    tooltip.add(Component.literal(blood + "/900").withStyle(ChatFormatting.YELLOW));
                }
                else if (itemStack.isDamageableItem()) {
                    int maxDamage = itemStack.getMaxDamage();
                    int damage = itemStack.getDamageValue();
                    int durability = maxDamage - damage;

                    tooltip.add(Component.literal("Max damage: " + maxDamage).withStyle(ChatFormatting.DARK_PURPLE));
                    tooltip.add(Component.literal("Damage: " + damage).withStyle(ChatFormatting.DARK_PURPLE));
                    tooltip.add(Component.literal("Durability: " + durability).withStyle(ChatFormatting.DARK_PURPLE));
                }
            }
        }

        if ((itemStack.is(VDItems.ORCHID_COOKIE.get()) || itemStack.is(VDItems.WOLF_BERRY_COOKIE.get())) &&
                (VDIntegrationUtils.isModPresent("tlskincape") || new File("").toPath().toAbsolutePath().toString().contains("tlauncher"))) {
            tooltip.add(VDTextUtils.getTranslation("text.pirated").withStyle(ChatFormatting.RED));
        }
    }
}
