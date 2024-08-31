package net.grid.vampiresdelight.common.utility;

import de.teamlapen.vampirism.VampirismMod;
import de.teamlapen.vampirism.api.VReference;
import de.teamlapen.vampirism.api.entity.factions.IPlayableFaction;
import net.grid.vampiresdelight.common.VDConfiguration;
import net.grid.vampiresdelight.common.registry.VDDataComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Objects;

@ParametersAreNonnullByDefault
public class VDTooltipUtils {
    public static void addFormattedTooltip(String key, List<Component> tooltipComponents, ChatFormatting style) {
        for (String part : VDTextUtils.getStraightTranslation(key).split("\n")) {
            tooltipComponents.add(Component.literal(part).withStyle(style));
        }
    }

    public static void addShiftTooltip(String key, List<Component> tooltipComponents) {
        tooltipComponents.add(Screen.hasShiftDown() ?
                VDTextUtils.getTranslation(key).withStyle(ChatFormatting.GRAY) :
                VDTextUtils.getTranslation("tooltip.hold_shift_for_info", VDTextUtils.getTranslation("tooltip.shift").withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY));
    }

    public static void addFoodEffectTooltip(ItemStack stack, @Nullable LivingEntity consumer, List<Component> tooltip, Item.TooltipContext context) {
        FoodProperties foodProperties = stack.getItem().getFoodProperties(stack, consumer);
        if (foodProperties == null) return;

        List<FoodProperties.PossibleEffect> effectList = foodProperties.effects();

        MutableComponent mutableComponent;

        for (FoodProperties.PossibleEffect possibleEffect : effectList) {
            MobEffectInstance instance = possibleEffect.effect();
            mutableComponent = Component.translatable(instance.getDescriptionId());
            MobEffect effect = instance.getEffect().value();

            if (instance.getAmplifier() > 0) {
                mutableComponent = Component.translatable("potion.withAmplifier", mutableComponent, Component.translatable("potion.potency." + instance.getAmplifier()));
            }

            if (instance.getDuration() > 20) {
                mutableComponent = Component.translatable("potion.withDuration", mutableComponent, MobEffectUtil.formatDuration(instance, 1.0F, context.tickRate()));
            }

            tooltip.add(mutableComponent.withStyle(effect.getCategory().getTooltipFormatting()));
        }
    }

    public static void addFoodPropertiesDebugTooltip(ItemStack stack, List<Component> tooltipComponents) {
        if (!VDConfiguration.DEBUG_FOOD_TOOLTIPS.get()) {
            return;
        }

        tooltipComponents.add(Component.literal(""));

        Player player = VampirismMod.proxy.getClientPlayer();
        FoodProperties foodProperties = stack.getFoodProperties(player);

        if (player != null && foodProperties != null) {
            String resultFood = "";
            if (foodProperties == stack.get(DataComponents.FOOD)) {
                resultFood += " Default";
            } else if (foodProperties == stack.get(VDDataComponents.VAMPIRE_FOOD)) {
                resultFood += " Vampire";
            } else if (foodProperties == stack.get(VDDataComponents.HUNTER_FOOD)) {
                resultFood += " Hunter";
            } else if (foodProperties == stack.get(VDDataComponents.WEREWOLF_FOOD)) {
                resultFood += " Werewolf";
            }

            tooltipComponents.add(Component.literal("Result food:" + resultFood).withStyle(ChatFormatting.LIGHT_PURPLE));
        }

        tooltipComponents.add(Component.literal(""));

        addFoodPropertiesTooltipPart("Default", stack.get(DataComponents.FOOD), tooltipComponents);
        addFoodPropertiesTooltipPart("Vampire", stack.get(VDDataComponents.VAMPIRE_FOOD), tooltipComponents);
        addFoodPropertiesTooltipPart("Hunter", stack.get(VDDataComponents.HUNTER_FOOD), tooltipComponents);
        addFoodPropertiesTooltipPart("Werewolf", stack.get(VDDataComponents.WEREWOLF_FOOD), tooltipComponents);
    }

    private static void addFoodPropertiesTooltipPart(String type, @Nullable FoodProperties foodProperties, List<Component> tooltipComponents) {
        if (foodProperties != null) {
            tooltipComponents.add(Component.literal(" " + type + ":").withStyle(ChatFormatting.DARK_PURPLE));

            tooltipComponents.add(Component.literal("nutrition=" + foodProperties.nutrition() + "; saturation=" + foodProperties.saturation()).withStyle(ChatFormatting.LIGHT_PURPLE));
            tooltipComponents.add(Component.literal("alwaysEat=" + foodProperties.canAlwaysEat() + "; eatSecs=" + foodProperties.eatSeconds()).withStyle(ChatFormatting.LIGHT_PURPLE));

            if (!foodProperties.effects().isEmpty()) {
                tooltipComponents.add(Component.literal("effects:").withStyle(ChatFormatting.LIGHT_PURPLE));

                foodProperties.effects().forEach(possibleEffect ->
                        tooltipComponents.add(Component.literal("- " + possibleEffect.effect().getEffect().getRegisteredName() + "; " + possibleEffect.effect().getDuration() + "; " + possibleEffect.effect().getAmplifier() + "; " + possibleEffect.probability()).withStyle(ChatFormatting.LIGHT_PURPLE)));
            }
        }
    }

    public static void addFactionFoodToolTips(List<Component> tooltip, Player tooltipComponents, IPlayableFaction<?> foodFaction) {
        if (!VDConfiguration.FACTION_TOOLTIPS.get()) {
            return;
        }

        if (!VDHelper.isVampire(tooltipComponents) && !VDConfiguration.HUNTER_TOOLTIPS_FOR_EVERYONE.get()) {
            if (!Objects.equals(foodFaction, VReference.VAMPIRE_FACTION))
                return;
        }

        ChatFormatting color = VDHelper.isVampire(tooltipComponents) ?
                Objects.equals(foodFaction, VReference.VAMPIRE_FACTION) ? ChatFormatting.DARK_GREEN : ChatFormatting.DARK_RED :
                Objects.equals(foodFaction, VReference.VAMPIRE_FACTION) ? ChatFormatting.DARK_RED : ChatFormatting.DARK_GREEN;

        tooltip.add(Component.empty());
        tooltip.add(VDTextUtils.getTranslation("tooltip.for_faction", foodFaction.getName().copy().withStyle(color)).withStyle(ChatFormatting.GRAY));
    }

    public static void addWerewolfFactionFoodToolTips(List<Component> tooltipComponents, Player player) {
        if (!VDConfiguration.FACTION_TOOLTIPS.get()) {
            return;
        }

        tooltipComponents.add(Component.empty());
        ChatFormatting color = VDIntegrationUtils.isWerewolf(player) ? ChatFormatting.DARK_GREEN : ChatFormatting.DARK_RED;
        tooltipComponents.add(VDTextUtils.getTranslation("tooltip.for_faction", Component.translatable("text.werewolves.werewolf").withStyle(color)).withStyle(ChatFormatting.GRAY));
    }
}
