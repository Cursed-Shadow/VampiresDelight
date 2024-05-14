package net.grid.vampiresdelight.common.mixin.accessor;

import de.teamlapen.vampirism.items.VampirismItemBloodFoodItem;
import net.minecraft.world.food.FoodProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(VampirismItemBloodFoodItem.class)
public interface VampirismItemBloodFoodItemAccessor {
    @Accessor("vampireFood")
    FoodProperties getVampireFood();
}
