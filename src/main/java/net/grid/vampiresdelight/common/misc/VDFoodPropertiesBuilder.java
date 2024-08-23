package net.grid.vampiresdelight.common.misc;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Supplier;

/**
 * This class is used for FoodProperties such as Hardtack in order to specify eating duration. In FoodProperties.Builder there's no way to set a custom value, only default ones
 */
@SuppressWarnings("unused")
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class VDFoodPropertiesBuilder extends FoodProperties.Builder {

    public VDFoodPropertiesBuilder() {
    }

    @Override
    public VDFoodPropertiesBuilder nutrition(int nutrition) {
        super.nutrition(nutrition);
        return this;
    }

    @Override
    public VDFoodPropertiesBuilder saturationModifier(float saturationModifier) {
        super.saturationModifier(saturationModifier);
        return this;
    }

    @Override
    public VDFoodPropertiesBuilder alwaysEdible() {
        super.alwaysEdible();
        return this;
    }

    @Override
    public VDFoodPropertiesBuilder fast() {
        super.fast();
        return this;
    }

    public VDFoodPropertiesBuilder eatSeconds(float eatSeconds) {
        super.eatSeconds = eatSeconds;
        return this;
    }

    public VDFoodPropertiesBuilder eatTicks(int eatTicks) {
        this.eatSeconds = eatTicks / 20F;
        return this;
    }

    @Override
    public VDFoodPropertiesBuilder effect(Supplier<MobEffectInstance> effectIn, float probability) {
        super.effect(effectIn, probability);
        return this;
    }
}
