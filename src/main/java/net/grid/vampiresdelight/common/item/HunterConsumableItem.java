package net.grid.vampiresdelight.common.item;

import de.teamlapen.vampirism.VampirismMod;
import de.teamlapen.vampirism.api.VReference;
import de.teamlapen.vampirism.core.ModItems;
import net.grid.vampiresdelight.common.constant.VDFoodValues;
import net.grid.vampiresdelight.common.utility.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.TooltipFlag;
import vectorwing.farmersdelight.common.Configuration;

import java.util.List;
import java.util.function.Consumer;

@SuppressWarnings("unused")
public class HunterConsumableItem extends Item {
    private final FoodProperties hunterFood;
    private final Consumer<LivingEntity> features;
    private final boolean hasFoodEffectTooltip;
    private final boolean hasCustomTooltip;
    private final boolean containsGarlic;

    public HunterConsumableItem(Properties properties, @Nullable Consumer<LivingEntity> features) {
        super(properties);
        this.hunterFood = null;
        this.features = features;
        this.hasFoodEffectTooltip = false;
        this.hasCustomTooltip = false;
        this.containsGarlic = true;
    }

    public HunterConsumableItem(Properties properties, @Nullable Consumer<LivingEntity> features, boolean hasFoodEffectTooltip) {
        super(properties);
        this.hunterFood = null;
        this.features = features;
        this.hasFoodEffectTooltip = hasFoodEffectTooltip;
        this.hasCustomTooltip = false;
        this.containsGarlic = true;
    }

    public HunterConsumableItem(Properties properties, FoodProperties hunterFood, @Nullable Consumer<LivingEntity> features, boolean hasFoodEffectTooltip, boolean hasCustomTooltip, boolean containsGarlic) {
        super(properties);
        this.hunterFood = hunterFood;
        this.features = features;
        this.hasFoodEffectTooltip = hasFoodEffectTooltip;
        this.hasCustomTooltip = hasCustomTooltip;
        this.containsGarlic = containsGarlic;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity consumer) {
        if (!level.isClientSide) {
            if (containsGarlic) {
                consumer.removeEffectsCuredBy(ModItems.GARLIC_CURE);
            }

            if (features != null) {
                features.accept(consumer);
            }

            this.affectConsumer(stack, level, consumer);
        }

        ItemStack containerStack = stack.getCraftingRemainingItem();

        if (stack.getFoodProperties(consumer) != null) {
            super.finishUsingItem(stack, level, consumer);
        } else {
            Player player = consumer instanceof Player ? (Player) consumer : null;
            if (player instanceof ServerPlayer) {
                CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayer) player, stack);
            }
            if (player != null) {
                player.awardStat(Stats.ITEM_USED.get(this));
                if (!player.getAbilities().instabuild) {
                    stack.shrink(1);
                }
            }
        }

        if (stack.isEmpty()) {
            return containerStack;
        } else {
            if (consumer instanceof Player player && !((Player) consumer).getAbilities().instabuild) {
                if (!player.getInventory().add(containerStack)) {
                    player.drop(containerStack, false);
                }
            }
            return stack;
        }
    }

    @Override
    public @Nullable FoodProperties getFoodProperties(ItemStack stack, @Nullable LivingEntity entity) {
        if (entity == null) {
            entity = VampirismMod.proxy.getClientPlayer();
        }

        return VDHelper.isHunter(entity) && hunterFood != null ? hunterFood : (VDHelper.isVampire(entity) ? VDFoodValues.NONE : super.getFoodProperties(stack, entity));
    }

    /**
     * Override this to apply changes to the consumer (e.g. curing effects).
     */
    public void affectConsumer(ItemStack stack, Level level, LivingEntity consumer) {
    }

    public boolean doesContainGarlic() {
        return containsGarlic;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        Player player = VampirismMod.proxy.getClientPlayer();

        if (Configuration.FOOD_EFFECT_TOOLTIP.get() && VDEntityUtils.canConsumeHumanFood(player)) {
            if (this.hasCustomTooltip) {
                MutableComponent textEmpty = VDTextUtils.getTranslation("tooltip." + VDNameUtils.itemName(this));
                tooltipComponents.add(textEmpty.withStyle(ChatFormatting.BLUE));
            }
            if (this.hasFoodEffectTooltip) {
                VDTextUtils.addFoodEffectTooltip(stack, player, tooltipComponents, context);
            }
        }

        if (player != null && VDHelper.isVampire(player))
            VDTooltipUtils.addFactionFoodToolTips(tooltipComponents, player, VReference.HUNTER_FACTION);
    }
}
