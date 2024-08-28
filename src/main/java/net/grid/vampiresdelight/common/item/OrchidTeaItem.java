package net.grid.vampiresdelight.common.item;

import de.teamlapen.vampirism.VampirismMod;
import net.grid.vampiresdelight.common.constant.VDFoodFeatures;
import net.grid.vampiresdelight.common.constant.VDFoodValues;
import net.grid.vampiresdelight.common.registry.VDItems;
import net.grid.vampiresdelight.common.utility.VDHelper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class OrchidTeaItem extends VampireDrinkableItem {
    public OrchidTeaItem() {
        super(VDItems.vampireDrinkItem(VDFoodValues.ORCHID_TEA_HUMAN, VDFoodValues.ORCHID_TEA_VAMPIRE, VDFoodValues.ORCHID_TEA_IMMUNE), VDFoodFeatures.ORCHID_TEA);
    }

    @Override
    public @Nullable FoodProperties getFoodProperties(ItemStack stack, @Nullable LivingEntity entity) {
        if (entity == null) {
            entity = VampirismMod.proxy.getClientPlayer();
        }

        return VDHelper.isVampire(entity) ? getVampireFood(stack, entity) :
                (entity instanceof Player player && VDHelper.canBeInfectedFromItem(player) ?
                        super.getFoodProperties(stack, entity) : getHunterFood(stack, entity));
    }

    @Override
    public boolean hasCustomTooltip(ItemStack stack, @Nullable Player player) {
        return player != null && VDHelper.canBeInfectedFromItem(player);
    }

    @Override
    public boolean hasFoodEffectTooltip(ItemStack stack, @Nullable Player player) {
        return !VDHelper.canBeInfectedFromItem(player);
    }

    @Override
    public boolean hasFactionTooltip(ItemStack stack, Player player) {
        return !VDHelper.canBeInfectedFromItem(player);
    }

    @Override
    public boolean hasColoredTooltipMargins(ItemStack stack, @Nullable Player player) {
        return !VDHelper.canBeInfectedFromItem(player);
    }
}
