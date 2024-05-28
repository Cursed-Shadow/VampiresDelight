package net.grid.vampiresdelight.common.item;

import de.teamlapen.vampirism.core.ModEffects;
import de.teamlapen.vampirism.util.Helper;
import net.grid.vampiresdelight.common.utility.VDEntityUtils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class CursedCupcakeItem extends VampireConsumableItem {
    public CursedCupcakeItem(Properties properties, FoodProperties vampireFood) {
        super(properties, vampireFood, true, true, false);
    }

    @Override
    public void affectConsumer(ItemStack stack, Level level, LivingEntity consumer) {
        if (Helper.isVampire(consumer)) {
            VDEntityUtils.cureEffect(ModEffects.GARLIC.get(), consumer);
        }
    }
}
