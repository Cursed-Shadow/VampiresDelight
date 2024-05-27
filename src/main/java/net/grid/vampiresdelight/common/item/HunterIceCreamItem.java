package net.grid.vampiresdelight.common.item;

import de.teamlapen.vampirism.util.Helper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class HunterIceCreamItem extends HunterConsumableItem {
    public HunterIceCreamItem(Properties properties, boolean hasFoodEffectTooltip) {
        super(properties, hasFoodEffectTooltip);
    }

    @Override
    public void affectConsumer(ItemStack stack, Level level, LivingEntity consumer) {
        if (!Helper.isVampire(consumer)) consumer.clearFire();
    }
}
