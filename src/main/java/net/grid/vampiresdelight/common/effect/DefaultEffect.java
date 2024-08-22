package net.grid.vampiresdelight.common.effect;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class DefaultEffect extends MobEffect {
    public DefaultEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    public DefaultEffect(MobEffectCategory category, int color, ParticleOptions particle) {
        super(category, color, particle);
    }
}
