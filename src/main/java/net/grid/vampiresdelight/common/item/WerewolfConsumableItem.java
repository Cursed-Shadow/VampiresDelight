package net.grid.vampiresdelight.common.item;

import net.grid.vampiresdelight.common.utility.*;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

@SuppressWarnings("unused")
public class WerewolfConsumableItem extends FactionalConsumableItem {
    public WerewolfConsumableItem(Properties properties, @Nullable Consumer<LivingEntity> features) {
        super(properties, features, true, false, true, false);
    }

    @Override
    public boolean hasFoodEffectTooltip(ItemStack stack, @Nullable Player player) {
        return player != null && VDIntegrationUtils.isWerewolf(player) && super.hasFoodEffectTooltip(stack, player);
    }

    @Override
    public void addFactionFoodTooltip(List<Component> tooltipComponents, Player player) {
        VDTooltipUtils.addWerewolfFactionFoodToolTips(tooltipComponents, player);
    }
}
