package net.grid.vampiresdelight.common.food;

import de.teamlapen.vampirism.core.ModEffects;
import net.grid.vampiresdelight.common.utility.VDEntityUtils;
import net.grid.vampiresdelight.common.utility.VDHelper;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

import java.util.function.Consumer;

public class VDFoodFeatures {
    public static final Consumer<LivingEntity> DAISY_TEA = entity -> {
        if (VDEntityUtils.canConsumeHumanFood(entity)) {
            VDEntityUtils.cureEffects(entity, MobEffects.MOVEMENT_SLOWDOWN, MobEffects.WEAKNESS, MobEffects.BLINDNESS, MobEffects.CONFUSION);
        }
    };

    public static final Consumer<LivingEntity> ICE_CREAM =
            Entity::clearFire;

    public static final Consumer<LivingEntity> CURSED_CUPCAKE = entity -> {
        if (VDHelper.isVampire(entity)) {
            VDEntityUtils.cureEffect(ModEffects.GARLIC.get(), entity);
        }
    };

    public static final Consumer<LivingEntity> ORCHID_TEA =
            VDHelper::tryInfectWithoutPoisoning;
}
