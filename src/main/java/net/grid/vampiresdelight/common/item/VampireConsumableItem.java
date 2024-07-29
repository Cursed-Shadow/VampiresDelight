package net.grid.vampiresdelight.common.item;

import de.teamlapen.vampirism.VampirismMod;
import de.teamlapen.vampirism.api.VReference;
import net.grid.vampiresdelight.common.utility.VDEntityUtils;
import net.grid.vampiresdelight.common.utility.VDHelper;
import net.grid.vampiresdelight.common.utility.VDTextUtils;
import net.grid.vampiresdelight.common.utility.VDTooltipUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import vectorwing.farmersdelight.common.Configuration;
import net.minecraft.network.chat.Component;

import java.util.List;
import java.util.function.Consumer;

@SuppressWarnings("unused")
public class VampireConsumableItem extends Item {
    private final FoodProperties vampireFood;
    private final FoodProperties hunterFood;
    private final Consumer<LivingEntity> features;
    private final boolean hasFoodEffectTooltip;
    private final boolean hasHumanFoodEffectTooltip;
    private final boolean hasCustomTooltip;
    private final boolean hasFactionTooltip;

    public VampireConsumableItem(Properties properties, FoodProperties vampireFood, @Nullable Consumer<LivingEntity> features) {
        super(properties);
        this.vampireFood = vampireFood;
        this.hunterFood = null;
        this.features = features;
        this.hasFoodEffectTooltip = true;
        this.hasCustomTooltip = false;
        this.hasHumanFoodEffectTooltip = false;
        this.hasFactionTooltip = true;
    }

    public VampireConsumableItem(Properties properties, FoodProperties vampireFood, FoodProperties hunterFood, @Nullable Consumer<LivingEntity> features) {
        super(properties);
        this.vampireFood = vampireFood;
        this.hunterFood = hunterFood;
        this.features = features;
        this.hasFoodEffectTooltip = true;
        this.hasCustomTooltip = false;
        this.hasHumanFoodEffectTooltip = false;
        this.hasFactionTooltip = true;
    }

    public VampireConsumableItem(Properties properties, FoodProperties vampireFood, @Nullable Consumer<LivingEntity> features, boolean hasFoodEffectTooltip) {
        super(properties);
        this.vampireFood = vampireFood;
        this.hunterFood = null;
        this.features = features;
        this.hasFoodEffectTooltip = hasFoodEffectTooltip;
        this.hasCustomTooltip = false;
        this.hasHumanFoodEffectTooltip = false;
        this.hasFactionTooltip = true;
    }

    public VampireConsumableItem(Properties properties, FoodProperties vampireFood, @Nullable Consumer<LivingEntity> features, boolean hasFoodEffectTooltip, boolean hasCustomTooltip, boolean hasHumanFoodEffectTooltip) {
        super(properties);
        this.vampireFood = vampireFood;
        this.hunterFood = null;
        this.features = features;
        this.hasFoodEffectTooltip = hasFoodEffectTooltip;
        this.hasCustomTooltip = hasCustomTooltip;
        this.hasHumanFoodEffectTooltip = hasHumanFoodEffectTooltip;
        this.hasFactionTooltip = true;
    }

    public VampireConsumableItem(Properties properties, FoodProperties vampireFood, @Nullable Consumer<LivingEntity> features, boolean hasFoodEffectTooltip, boolean hasCustomTooltip, boolean hasHumanFoodEffectTooltip, boolean hasFactionTooltip) {
        super(properties);
        this.vampireFood = vampireFood;
        this.hunterFood = null;
        this.features = features;
        this.hasFoodEffectTooltip = hasFoodEffectTooltip;
        this.hasCustomTooltip = hasCustomTooltip;
        this.hasHumanFoodEffectTooltip = hasHumanFoodEffectTooltip;
        this.hasFactionTooltip = hasFactionTooltip;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity consumer) {
        if (!level.isClientSide) {
            if (features != null) {
                features.accept(consumer);
            }

            this.affectConsumer(stack, level, consumer);
        }

        ItemStack containerStack = stack.getCraftingRemainingItem();

        if (stack.getFoodProperties(consumer) != null) {
            // TODO: Make werewolves also get nasty affects from vampire food
            VDEntityUtils.consumeBloodFood(stack, level, consumer);
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

        return VDHelper.isVampire(entity) ? vampireFood : (VDHelper.isHunter(entity) && hunterFood != null ? hunterFood : super.getFoodProperties(stack, entity));
    }

    /**
     * Override this to apply changes to the consumer (e.g. curing effects).
     */
    public void affectConsumer(ItemStack stack, Level level, LivingEntity consumer) {
    }

    public FoodProperties getVampireFood() {
        return vampireFood;
    }

    public @Nullable FoodProperties getHunterFood() {
        return hunterFood;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        Player player = VampirismMod.proxy.getClientPlayer();

        if (Configuration.FOOD_EFFECT_TOOLTIP.get()) {
            if (this.hasCustomTooltip) {
                MutableComponent textEmpty = VDTextUtils.getTranslation("tooltip." + this);
                tooltipComponents.add(textEmpty.withStyle(ChatFormatting.BLUE));
            }
            if (this.hasFoodEffectTooltip) {
                if (player != null && (VDHelper.isVampire(player) || hasHumanFoodEffectTooltip)) {
                    VDTextUtils.addFoodEffectTooltip(stack, player, tooltipComponents, context);
                }
            }
        }

        if (hasFactionTooltip && player != null)
            VDTooltipUtils.addFactionFoodToolTips(tooltipComponents, player, VReference.VAMPIRE_FACTION);
    }
}
