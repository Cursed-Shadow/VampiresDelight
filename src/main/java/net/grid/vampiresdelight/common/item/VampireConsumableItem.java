package net.grid.vampiresdelight.common.item;

import de.teamlapen.vampirism.api.VReference;
import net.grid.vampiresdelight.common.utility.VDHelper;
import net.grid.vampiresdelight.common.utility.VDTooltipUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public class VampireConsumableItem extends FactionConsumableItem {
    public VampireConsumableItem(Properties properties, @Nullable Consumer<LivingEntity> features) {
        super(properties, features, true, false, true, false);
    }

    public VampireConsumableItem(Properties properties, @Nullable Consumer<LivingEntity> features, boolean hasFoodEffectTooltip) {
        super(properties, features, hasFoodEffectTooltip, false, true, false);
    }

    public VampireConsumableItem(Properties properties, @Nullable Consumer<LivingEntity> features, boolean hasFoodEffectTooltip, boolean hasCustomTooltip) {
        super(properties, features, hasFoodEffectTooltip, hasCustomTooltip, true, false);
    }

    @Override
    public boolean hasFoodEffectTooltip(ItemStack stack, @Nullable Player player) {
        return player != null && VDHelper.isVampire(player) && super.hasFoodEffectTooltip(stack, player);
    }

    @Override
    public void addFactionFoodTooltip(List<Component> tooltipComponents, Player player) {
        VDTooltipUtils.addFactionFoodToolTips(tooltipComponents, player, VReference.VAMPIRE_FACTION);
    }
}
