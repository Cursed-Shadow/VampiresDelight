package net.grid.vampiresdelight.common.item;

import de.teamlapen.vampirism.VampirismMod;
import de.teamlapen.vampirism.core.ModItems;
import net.grid.vampiresdelight.common.registry.VDDataComponents;
import net.grid.vampiresdelight.common.utility.*;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import vectorwing.farmersdelight.common.Configuration;

import java.util.List;
import java.util.function.Consumer;

@SuppressWarnings("unused")
public class FactionConsumableItem extends Item {
    private final @Nullable Consumer<LivingEntity> features;
    private final boolean hasFoodEffectTooltip;
    private final boolean hasCustomTooltip;
    private final boolean hasFactionTooltip;
    private final boolean hasGarlic;

    public FactionConsumableItem(Properties properties, @Nullable Consumer<LivingEntity> features, boolean hasFoodEffectTooltip, boolean hasCustomTooltip, boolean hasFactionTooltip, boolean hasGarlic) {
        super(properties);
        this.features = features;
        this.hasFoodEffectTooltip = hasFoodEffectTooltip;
        this.hasCustomTooltip = hasCustomTooltip;
        this.hasFactionTooltip = hasFactionTooltip;
        this.hasGarlic = hasGarlic;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity consumer) {
        if (!level.isClientSide) {
            if (hasGarlic()) {
                consumer.removeEffectsCuredBy(ModItems.GARLIC_CURE);
            }

            if (features != null) {
                features.accept(consumer);
            }

            this.affectConsumer(stack, level, consumer);
        }

        ItemStack containerStack = stack.getCraftingRemainingItem();

        if (stack.getFoodProperties(consumer) != null) {
            // TODO: Check if food eating finish sound is played for all races
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

    /**
     * Override this to apply changes to the consumer (e.g. curing effects).
     */
    public void affectConsumer(ItemStack stack, Level level, LivingEntity consumer) {
    }

    @Override
    public @Nullable FoodProperties getFoodProperties(ItemStack stack, @Nullable LivingEntity entity) {
        if (entity != null) {
            if (VDHelper.isVampire(entity) && getVampireFood(stack, entity) != null) {
                return getVampireFood(stack, entity);
            }

            if (VDHelper.isHunter(entity) && getHunterFood(stack, entity) != null) {
                return getHunterFood(stack, entity);
            }

            if (VDIntegrationUtils.isWerewolf(entity) && getWerewolfFood(stack, entity) != null) {
                return getWerewolfFood(stack, entity);
            }
        }

        return super.getFoodProperties(stack, entity);
    }

    public @Nullable FoodProperties getVampireFood(ItemStack stack, @Nullable LivingEntity entity) {
        return stack.get(VDDataComponents.VAMPIRE_FOOD);
    }

    public @Nullable FoodProperties getHunterFood(ItemStack stack, @Nullable LivingEntity entity) {
        return stack.get(VDDataComponents.HUNTER_FOOD);
    }

    public @Nullable FoodProperties getWerewolfFood(ItemStack stack, @Nullable LivingEntity entity) {
        return stack.get(VDDataComponents.WEREWOLF_FOOD);
    }

    public boolean hasGarlic() {
        return hasGarlic;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        Player player = VampirismMod.proxy.getClientPlayer();

        if (Configuration.FOOD_EFFECT_TOOLTIP.get()) {
            if (this.hasCustomTooltip) {
                MutableComponent textEmpty = VDTextUtils.getTranslation("tooltip." + VDNameUtils.itemName(this));
                tooltipComponents.add(textEmpty.withStyle(ChatFormatting.BLUE));
            }
            if (hasFoodEffectTooltip(stack, player)) {
                VDTextUtils.addFoodEffectTooltip(stack, player, tooltipComponents, context);
            }
        }

        if (player != null && hasFactionTooltip(stack, player))
            addFactionFoodTooltip(tooltipComponents, player);
    }

    public boolean hasFoodEffectTooltip(ItemStack stack, @Nullable Player player) {
        return this.hasFoodEffectTooltip;
    }

    public boolean hasFactionTooltip(ItemStack stack, Player player) {
        return this.hasFactionTooltip;
    }

    /**
     * This must be overridden in child classes representing factions since it's empty by default.
     */
    public void addFactionFoodTooltip(List<Component> tooltipComponents, Player player) {
    }
}
