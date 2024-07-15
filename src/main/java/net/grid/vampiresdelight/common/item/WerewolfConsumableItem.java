package net.grid.vampiresdelight.common.item;

import de.teamlapen.vampirism.VampirismMod;
import net.grid.vampiresdelight.common.utility.VDIntegrationUtils;
import net.grid.vampiresdelight.common.utility.VDTextUtils;
import net.grid.vampiresdelight.common.utility.VDTooltipUtils;
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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;
import vectorwing.farmersdelight.common.Configuration;

import java.util.List;
import java.util.function.Consumer;

@SuppressWarnings("unused")
public class WerewolfConsumableItem extends Item {
    private final FoodProperties werewolfFood;
    private final Consumer<LivingEntity> features;
    private final boolean hasFoodEffectTooltip;
    private final boolean hasHumanFoodEffectTooltip;
    private final boolean hasCustomTooltip;
    private final boolean hasFactionTooltip;

    public WerewolfConsumableItem(Properties pProperties, FoodProperties werewolfFood, @Nullable Consumer<LivingEntity> features) {
        super(pProperties);
        this.werewolfFood = werewolfFood;
        this.features = features;
        this.hasFoodEffectTooltip = true;
        this.hasCustomTooltip = false;
        this.hasHumanFoodEffectTooltip = false;
        this.hasFactionTooltip = true;
    }

    public WerewolfConsumableItem(Properties pProperties, FoodProperties werewolfFood, @Nullable Consumer<LivingEntity> features, boolean hasFoodEffectTooltip, boolean hasCustomTooltip, boolean hasHumanFoodEffectTooltip, boolean hasFactionTooltip) {
        super(pProperties);
        this.werewolfFood = werewolfFood;
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

        if (stack.isEdible()) {
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

        return VDIntegrationUtils.isWerewolf(entity) ? werewolfFood : super.getFoodProperties(stack, entity);
    }

    /**
     * Override this to apply changes to the consumer (e.g. curing effects).
     */
    public void affectConsumer(ItemStack stack, Level level, LivingEntity consumer) {
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag isAdvanced) {
        Player player = VampirismMod.proxy.getClientPlayer();

        if (Configuration.FOOD_EFFECT_TOOLTIP.get()) {
            if (this.hasCustomTooltip) {
                MutableComponent textEmpty = VDTextUtils.getTranslation("tooltip." + this);
                tooltip.add(textEmpty.withStyle(ChatFormatting.BLUE));
            }
            if (this.hasFoodEffectTooltip && player != null) {
                if (VDIntegrationUtils.isWerewolf(player) || hasHumanFoodEffectTooltip) {
                    VDTextUtils.addFoodEffectTooltip(stack, player, tooltip);
                }
            }
        }

        if (hasFactionTooltip && player != null)
            VDTooltipUtils.addWerewolfFactionFoodToolTips(tooltip, player);
    }
}
