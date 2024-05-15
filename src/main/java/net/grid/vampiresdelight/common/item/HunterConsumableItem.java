package net.grid.vampiresdelight.common.item;

import de.teamlapen.vampirism.VampirismMod;
import de.teamlapen.vampirism.api.VReference;
import de.teamlapen.vampirism.core.ModEffects;
import de.teamlapen.vampirism.util.Helper;
import net.grid.vampiresdelight.common.utility.VDEntityUtils;
import net.grid.vampiresdelight.common.utility.VDTextUtils;
import net.grid.vampiresdelight.common.utility.VDTooltipUtils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
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
import vectorwing.farmersdelight.common.utility.TextUtils;

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

    public HunterConsumableItem(Properties properties, boolean hasFoodEffectTooltip, boolean hasCustomTooltip) {
        super(properties);
        this.hunterFood = null;
        this.hasFoodEffectTooltip = hasFoodEffectTooltip;
        this.hasCustomTooltip = hasCustomTooltip;
        this.containsGarlic = true;
    }

    public HunterConsumableItem(Properties properties, FoodProperties hunterFood, boolean hasFoodEffectTooltip, boolean hasCustomTooltip, boolean containsGarlic) {
        super(properties);
        this.hunterFood = hunterFood;
        this.hasFoodEffectTooltip = hasFoodEffectTooltip;
        this.hasCustomTooltip = hasCustomTooltip;
        this.containsGarlic = containsGarlic;
    }

    @NotNull
    @Override
    public ItemStack finishUsingItem(@NotNull ItemStack stack, @NotNull Level level, @NotNull LivingEntity consumer) {
        if (!level.isClientSide) {
            if (containsGarlic)
                VDEntityUtils.cureEffect(ModEffects.SANGUINARE.get(), consumer);

            this.affectConsumer(stack, level, consumer);
        }

        ItemStack containerStack = stack.getCraftingRemainingItem();

        if (stack.isEdible()) {
            if (hunterFood == null)
                super.finishUsingItem(stack, level, consumer);
            else {
                VDEntityUtils.eatFood(level, consumer, stack, Helper.isHunter(consumer) ? hunterFood : stack.getFoodProperties(consumer));

                if (consumer instanceof Player player && !player.isCreative() || !(consumer instanceof Player))
                    stack.shrink(1);
            }
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

    public boolean doesContainGarlic() {
        return containsGarlic;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltip, @NotNull TooltipFlag isAdvanced) {
        Player player = VampirismMod.proxy.getClientPlayer();
        assert player != null;

        if (Configuration.FOOD_EFFECT_TOOLTIP.get()) {
            if (this.hasCustomTooltip) {
                MutableComponent textEmpty = VDTextUtils.getTranslation("tooltip." + this);
                tooltip.add(textEmpty.withStyle(ChatFormatting.BLUE));
            }
            if (this.hasFoodEffectTooltip) {
                TextUtils.addFoodEffectTooltip(stack, tooltip, 1.0F);
            }
        }
        if (Helper.isVampire(player)) VDTooltipUtils.addFactionFoodToolTips(tooltip, VampirismMod.proxy.getClientPlayer(), VReference.HUNTER_FACTION);
    }
}
