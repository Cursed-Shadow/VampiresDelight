package net.grid.vampiresdelight.common.food;

import de.teamlapen.vampirism.core.ModEffects;
import net.grid.vampiresdelight.common.utility.VDHelper;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;

public class VDFoodFeatures {
    public static final FoodFeatures DAISY_TEA = new FoodFeatures.Builder()
            .curesEffects(AffectCategory.HUMAN, MobEffects.MOVEMENT_SLOWDOWN, MobEffects.WEAKNESS, MobEffects.BLINDNESS, MobEffects.CONFUSION).build();
    public static final FoodFeatures ICE_CREAM = new FoodFeatures.Builder()
            .custom(Entity::clearFire).build();
    public static final FoodFeatures CURSED_CUPCAKE = new FoodFeatures.Builder()
            .curesEffects(AffectCategory.VAMPIRE, ModEffects.GARLIC.get()).build();
    public static final FoodFeatures ORCHID_TEA = new FoodFeatures.Builder()
            .custom(VDHelper::tryInfectWithoutPoisoning).build();
    public static final FoodFeatures HARDTACK = new FoodFeatures.Builder()
            .eatingDuration(48).build();
}
