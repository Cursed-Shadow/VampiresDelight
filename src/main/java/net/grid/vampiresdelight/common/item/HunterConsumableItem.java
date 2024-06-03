package net.grid.vampiresdelight.common.item;

import de.teamlapen.vampirism.VampirismMod;
import de.teamlapen.vampirism.api.VReference;
import de.teamlapen.vampirism.core.ModEffects;
import net.grid.vampiresdelight.common.VDFoodValues;
import net.grid.vampiresdelight.common.utility.VDEntityUtils;
import net.grid.vampiresdelight.common.utility.VDHelper;
import net.grid.vampiresdelight.common.utility.VDTextUtils;
import net.grid.vampiresdelight.common.utility.VDTooltipUtils;
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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import vectorwing.farmersdelight.common.Configuration;

import java.util.List;

public class HunterConsumableItem extends Item {
    private final FoodProperties hunterFood;
    private final boolean hasFoodEffectTooltip;
    private final boolean hasCustomTooltip;
    private final boolean containsGarlic;

    public HunterConsumableItem(Properties properties) {
        super(properties);
        this.hunterFood = null;
        this.hasFoodEffectTooltip = false;
        this.hasCustomTooltip = false;
        this.containsGarlic = true;
    }

    public HunterConsumableItem(Properties properties, boolean hasFoodEffectTooltip) {
        super(properties);
        this.hunterFood = null;
        this.hasFoodEffectTooltip = hasFoodEffectTooltip;
        this.hasCustomTooltip = false;
        this.containsGarlic = true;
    }

    public HunterConsumableItem(Properties properties, FoodProperties hunterFood, boolean hasFoodEffectTooltip, boolean hasCustomTooltip, boolean containsGarlic) {
        super(properties);
        this.hunterFood = hunterFood;
        this.hasFoodEffectTooltip = hasFoodEffectTooltip;
        this.hasCustomTooltip = hasCustomTooltip;
        this.containsGarlic = containsGarlic;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity consumer) {
        if (!level.isClientSide) {
            if (containsGarlic)
                VDEntityUtils.cureEffect(ModEffects.SANGUINARE.get(), consumer);

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
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag isAdvanced) {
        Player player = VampirismMod.proxy.getClientPlayer();

        if (Configuration.FOOD_EFFECT_TOOLTIP.get()) {
            if (this.hasCustomTooltip) {
                MutableComponent textEmpty = VDTextUtils.getTranslation("tooltip." + this);
                tooltip.add(textEmpty.withStyle(ChatFormatting.BLUE));
            }
            if (this.hasFoodEffectTooltip) {
                FoodProperties foodProperties = stack.getFoodProperties(player);

                if (foodProperties != null)
                    VDTextUtils.addFoodEffectTooltip(foodProperties, tooltip, 1.0F);
            }
        }

        if (player != null && VDHelper.isVampire(player))
            VDTooltipUtils.addFactionFoodToolTips(tooltip, player, VReference.HUNTER_FACTION);
    }
}
