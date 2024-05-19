package net.grid.vampiresdelight.common.utility;

import com.mojang.datafixers.util.Pair;
import de.teamlapen.vampirism.api.EnumStrength;
import de.teamlapen.vampirism.api.entity.vampire.IVampire;
import de.teamlapen.vampirism.entity.player.vampire.VampirePlayer;
import de.teamlapen.vampirism.entity.vampire.DrinkBloodContext;
import de.teamlapen.vampirism.util.DamageHandler;
import de.teamlapen.vampirism.util.Helper;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

public class VDEntityUtils {
    // Applies food effect to living entity from food properties
    public static void addFoodEffects(FoodProperties foodProperties, Level level, LivingEntity entity) {
        for (Pair<MobEffectInstance, Float> pair : foodProperties.getEffects()) {
            if (!level.isClientSide && pair.getFirst() != null && level.random.nextFloat() < pair.getSecond()) {
                entity.addEffect(new MobEffectInstance(pair.getFirst()));
            }
        }
    }

    public static ItemStack eatFood(Level level, LivingEntity entity, ItemStack foodItem, FoodProperties foodProperties) {
        if (foodItem.isEdible()) {
            level.playSound(null, entity.getX(), entity.getY(), entity.getZ(), entity.getEatingSound(foodItem), SoundSource.NEUTRAL, 1.0F, 1.0F + (level.random.nextFloat() - level.random.nextFloat()) * 0.4F);
            addFoodEffects(foodProperties, level, entity);
            if (!(entity instanceof Player) || !((Player) entity).getAbilities().instabuild) {
                foodItem.shrink(1);
            }

            entity.gameEvent(GameEvent.EAT);
        }

        return foodItem;
    }

    public static void consumeBloodFood(ItemStack stack, Level level, LivingEntity consumer, FoodProperties vampireFood, FoodProperties hunterFood) {
        if (consumer instanceof Player player) {
            // Don't shrink stack before retrieving food
            VampirePlayer.getOpt(player).ifPresent(v -> v.drinkBlood(vampireFood.getNutrition(), vampireFood.getSaturationModifier(), new DrinkBloodContext(stack)));
        }
        if (consumer instanceof IVampire) {
            ((IVampire) consumer).drinkBlood(vampireFood.getNutrition(), vampireFood.getSaturationModifier(), new DrinkBloodContext(stack));
        } else if (!Helper.isVampire(consumer))
            eatFood(level, consumer, stack, Helper.isHunter(consumer) && hunterFood != null ? hunterFood : stack.getFoodProperties(consumer));

        if (consumer instanceof Player player && !player.isCreative() || !(consumer instanceof Player)) {
            stack.shrink(1);
        }

        if (Helper.isVampire(consumer)) {
            addFoodEffects(vampireFood, level, consumer);
        }
    }

    public static void cureEffect(MobEffect mobEffect, LivingEntity entity) {
        if (entity.hasEffect(mobEffect))
            entity.removeEffect(mobEffect);
    }

    public static void affectVampireEntityWithGarlic(LivingEntity livingEntity, EnumStrength strength) {
        if (livingEntity instanceof IVampire) {
            DamageHandler.affectVampireGarlicDirect((IVampire) livingEntity, strength);
        } else if (livingEntity instanceof Player player) {
            VampirePlayer.getOpt(player).ifPresent(vampire -> DamageHandler.affectVampireGarlicDirect(vampire, strength));
        }
    }

    public static void addParticlesAroundEntity(ParticleOptions pParticleOption, LivingEntity livingEntity, int amount, double speedMultiplier, double yOffset) {
        for (int i = 0; i <= amount; i++) {
            double d0 = livingEntity.getRandom().nextGaussian() * speedMultiplier;
            double d1 = livingEntity.getRandom().nextGaussian() * speedMultiplier;
            double d2 = livingEntity.getRandom().nextGaussian() * speedMultiplier;
            livingEntity.level().addParticle(pParticleOption, livingEntity.getRandomX(1.0D), livingEntity.getRandomY() + yOffset, livingEntity.getRandomZ(1.0D), d0, d1, d2);
        }
    }

    public static void addBlessingParticlesAroundEntity(ParticleOptions particleOptions, LivingEntity livingEntity, int amount) {
        for (int i = 0; i < amount; i++) {
            double x = livingEntity.getX() + (2.0F * livingEntity.getRandom().nextFloat() - 1.0F) * 0.65;
            double y = livingEntity.getY() + 0.1 + livingEntity.getRandom().nextFloat() * 0.8;
            double z = livingEntity.getZ() + (2.0F * livingEntity.getRandom().nextFloat() - 1.0F) * 0.65;
            livingEntity.level().addParticle(particleOptions, x, y, z, 0.0, 0.0, 0.0);
        }
    }
}
