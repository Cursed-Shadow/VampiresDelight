package net.grid.vampiresdelight.common.utility;

import com.mojang.datafixers.util.Pair;
import de.teamlapen.vampirism.api.EnumStrength;
import de.teamlapen.vampirism.api.entity.vampire.IVampire;
import de.teamlapen.vampirism.entity.player.vampire.VampirePlayer;
import de.teamlapen.vampirism.entity.vampire.DrinkBloodContext;
import de.teamlapen.vampirism.util.DamageHandler;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.awt.*;
import java.util.List;

public class VDEntityUtils {
    public static void addFoodEffects(FoodProperties foodProperties, Level level, LivingEntity entity) {
        for (Pair<MobEffectInstance, Float> pair : foodProperties.getEffects()) {
            if (!level.isClientSide && pair.getFirst() != null && level.random.nextFloat() < pair.getSecond()) {
                entity.addEffect(new MobEffectInstance(pair.getFirst()));
            }
        }
    }

    public static void consumeBloodFood(ItemStack stack, Level level, LivingEntity consumer) {
        feedVampire(stack, level, consumer);

        if (VDHelper.isVampire(consumer)) {
            FoodProperties foodProperties = stack.getFoodProperties(consumer);
            if (foodProperties != null) {
                addFoodEffects(foodProperties, level, consumer);
            }

            if (consumer instanceof Player player && !player.isCreative() || !(consumer instanceof Player)) {
                stack.shrink(1);
            }
        }
    }

    public static void feedVampire(ItemStack stack, Level level, LivingEntity consumer) {
        FoodProperties foodProperties = stack.getFoodProperties(consumer);

        if (foodProperties != null) {
            FoodProperties bloodFoodProperties = VDHelper.isVampire(consumer) ? foodProperties : new FoodProperties.Builder().nutrition(0).saturationMod(0).build();
            if (consumer instanceof Player player) {
                VampirePlayer.getOpt(player).ifPresent(v -> v.drinkBlood(bloodFoodProperties.getNutrition(), bloodFoodProperties.getSaturationModifier(), new DrinkBloodContext(stack)));
            }
            if (consumer instanceof IVampire) {
                ((IVampire) consumer).drinkBlood(bloodFoodProperties.getNutrition(), bloodFoodProperties.getSaturationModifier(), new DrinkBloodContext(stack));
            } else if (!VDHelper.isVampire(consumer))
                consumer.eat(level, stack);
        }
    }

    public static boolean hasPoison(FoodProperties foodProperties) {
        if (foodProperties != null) {
            for (Pair<MobEffectInstance, Float> effect : foodProperties.getEffects()) {
                if (effect.getFirst() != null) {
                    effect.getFirst().getEffect();
                    if (effect.getFirst().getEffect() == MobEffects.POISON)
                        return true;
                }
            }
        }

        return false;
    }

    public static void cureEffect(MobEffect mobEffect, LivingEntity entity) {
        if (entity.hasEffect(mobEffect)){
            entity.removeEffect(mobEffect);
        }
    }

    public static void cureMultipleEffects(List<MobEffect> mobEffects, LivingEntity entity) {
        mobEffects.forEach(effect -> {
            if (entity.hasEffect(effect)) {
                entity.removeEffect(effect);
            }
        });
    }

    public static boolean canConsumeHumanFood(LivingEntity consumer) {
        if (VDHelper.isVampire(consumer)) {
            return false;
        }
        // Werewolf players with "not_meat" skill can consume human food
        if (VDIntegrationUtils.isWerewolf(consumer)) {
            return consumer instanceof Player player && VDIntegrationUtils.isWerewolfVegetarian(player);
        }
        return true;
    }

    public static void affectVampireEntityWithGarlic(LivingEntity livingEntity, EnumStrength strength) {
        if (livingEntity instanceof IVampire) {
            DamageHandler.affectVampireGarlicDirect((IVampire) livingEntity, strength);
        } else if (livingEntity instanceof Player player) {
            VampirePlayer.getOpt(player).ifPresent(vampire -> DamageHandler.affectVampireGarlicDirect(vampire, strength));
        }
    }

    public static void spawnParticlesAroundEntity(ParticleOptions particle, LivingEntity livingEntity, int amount, double speedMultiplier, double yOffset) {
        for (int i = 0; i <= amount; i++) {
            double d0 = livingEntity.getRandom().nextGaussian() * speedMultiplier;
            double d1 = livingEntity.getRandom().nextGaussian() * speedMultiplier;
            double d2 = livingEntity.getRandom().nextGaussian() * speedMultiplier;
            livingEntity.level().addParticle(particle, livingEntity.getRandomX(1.0D), livingEntity.getRandomY() + yOffset, livingEntity.getRandomZ(1.0D), d0, d1, d2);
        }
    }

    public static void spawnParticlesAroundEntity(ParticleOptions particle, LivingEntity livingEntity, int amount) {
        for (int i = 0; i <= amount; i++) {
            double d0 = livingEntity.getRandom().nextGaussian() * 0.02D;
            double d1 = livingEntity.getRandom().nextGaussian() * 0.02D;
            double d2 = livingEntity.getRandom().nextGaussian() * 0.02D;
            livingEntity.level().addParticle(particle, livingEntity.getRandomX(1.0D), livingEntity.getRandomY() + 0.5D, livingEntity.getRandomZ(1.0D), d0, d1, d2);
        }
    }

    public static void spawnBlessingParticlesAroundEntity(ParticleOptions particle, LivingEntity livingEntity, int amount) {
        for (int i = 0; i < amount; i++) {
            double x = livingEntity.getX() + (2.0F * livingEntity.getRandom().nextFloat() - 1.0F) * 0.65;
            double y = livingEntity.getY() + 0.1 + livingEntity.getRandom().nextFloat() * 0.8;
            double z = livingEntity.getZ() + (2.0F * livingEntity.getRandom().nextFloat() - 1.0F) * 0.65;
            livingEntity.level().addParticle(particle, x, y, z, 0.0, 0.0, 0.0);
        }
    }

    // LivingEntity.spawnItemParticlesAroundEntity but with configurable particle type
    public static void spawnParticlesOnItemEntityHolding(ParticleOptions particleOptions, LivingEntity livingEntity, Color color) {
        Vec3 vec3 = new Vec3(((double) livingEntity.getRandom().nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, 0.0D);
        vec3 = vec3.xRot(-livingEntity.getXRot() * ((float) Math.PI / 180F));
        vec3 = vec3.yRot(-livingEntity.getYRot() * ((float) Math.PI / 180F));
        double d0 = (double)(-livingEntity.getRandom().nextFloat()) * 0.6D - 0.3D;
        Vec3 vec31 = new Vec3(((double) livingEntity.getRandom().nextFloat() - 0.5D) * 0.3D, d0, 0.6D);
        vec31 = vec31.xRot(-livingEntity.getXRot() * ((float) Math.PI / 180F));
        vec31 = vec31.yRot(-livingEntity.getYRot() * ((float) Math.PI / 180F));
        vec31 = vec31.add(livingEntity.getX(), livingEntity.getEyeY(), livingEntity.getZ());

        if (livingEntity.level() instanceof ServerLevel) {
            ((ServerLevel) livingEntity.level()).sendParticles(particleOptions, vec31.x, vec31.y, vec31.z, 1, vec3.x, vec3.y + 0.05D, vec3.z, 0.0D);
        } else {
            livingEntity.level().addParticle(particleOptions, vec31.x, vec31.y, vec31.z, vec3.x, vec3.y + 0.05D, vec3.z);
        }
    }
}
