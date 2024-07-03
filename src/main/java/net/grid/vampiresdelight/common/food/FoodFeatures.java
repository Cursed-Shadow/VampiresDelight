package net.grid.vampiresdelight.common.food;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;

import java.util.List;
import java.util.function.Consumer;

public class FoodFeatures {
    private final int useDuration;
    private final List<Pair<AffectCategory, MobEffect[]>> curesEffects;
    private final Consumer<LivingEntity> customAction;

    public FoodFeatures(Builder builder) {
        this.useDuration = builder.useDuration;
        this.curesEffects = builder.curesEffects;
        this.customAction = builder.customAction;
    }

    public void execute(LivingEntity consumer) {
        if (!curesEffects.isEmpty()) {
            for (Pair<AffectCategory, MobEffect[]> pair : curesEffects) {
                for (MobEffect effect : pair.getSecond()) {
                    if (consumer.hasEffect(effect) && AffectCategory.canAffectConsumer(pair.getFirst(), consumer)) {
                        consumer.removeEffect(effect);
                    }
                }
            }
        }

        if (customAction != null) {
            customAction.accept(consumer);
        }
    }

    public int getUseDuration() {
        return useDuration;
    }

    public static class Builder {
        private int useDuration;
        private final List<Pair<AffectCategory, MobEffect[]>> curesEffects = Lists.newArrayList();
        private Consumer<LivingEntity> customAction;

        public Builder eatingDuration(int duration) {
            this.useDuration = duration;
            return this;
        }

        public Builder curesEffects(AffectCategory affectedConsumers, MobEffect... curedEffects) {
            this.curesEffects.add(Pair.of(affectedConsumers, curedEffects));
            return this;
        }

        public Builder custom(Consumer<LivingEntity> action) {
            this.customAction = action;
            return this;
        }

        public FoodFeatures build() {
            return new FoodFeatures(this);
        }
    }
}
