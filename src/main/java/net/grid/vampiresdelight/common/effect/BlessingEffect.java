package net.grid.vampiresdelight.common.effect;

import de.teamlapen.vampirism.core.ModEntities;
import net.grid.vampiresdelight.common.VDConfiguration;
import net.grid.vampiresdelight.common.registry.VDEffects;
import net.grid.vampiresdelight.common.registry.VDParticleTypes;
import net.grid.vampiresdelight.common.registry.VDSounds;
import net.grid.vampiresdelight.common.tag.VDTags;
import net.grid.vampiresdelight.common.utility.VDEntityUtils;
import net.grid.vampiresdelight.common.utility.VDHelper;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.FastColor;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;

public class BlessingEffect extends MobEffect {

    public BlessingEffect() {
        super(MobEffectCategory.BENEFICIAL, FastColor.ARGB32.color(100, 250, 218, 94));
    }

    @Override
    public boolean applyEffectTick(@NotNull LivingEntity entity, int amplifier) {
        if (VDHelper.isVampire(entity)) {
            entity.removeEffect(VDEffects.BLESSING);
            return false;
        }

        resistUnholySpirits(entity, amplifier);

        return true;
    }

    public static void resistUnholySpirits(LivingEntity entity, int amplifier) {
        Level level = entity.level();
        Vec3 center = new Vec3(entity.getX(), entity.getY(), entity.getZ());

        level.getEntitiesOfClass(LivingEntity.class, new AABB(center, center).inflate(5.0 * (amplifier + 1))).stream().sorted(Comparator.comparingDouble(spirit -> spirit.distanceToSqr(center))).forEach(spirit -> {
            EntityType<?> type = spirit.getType();

            if (type.is(VDTags.UNHOLY_SPIRITS) && !(type == ModEntities.GHOST.get() && !VDConfiguration.BLESSING_HELPS_AGAINST_GHOSTS.get())) {
                spirit.remove(Entity.RemovalReason.DISCARDED);

                level.playSound(null, spirit.getX(), spirit.getY(), spirit.getZ(), VDSounds.TRIANGLE.get(), SoundSource.HOSTILE, 0.4f, entity.getRandom().nextFloat() * 0.5F);
                VDEntityUtils.spawnBlessingParticlesAroundEntity(VDParticleTypes.BLESSING.get(), spirit, type == ModEntities.GHOST.get() ? spirit.getRandom().nextInt(4, 10) : spirit.getRandom().nextInt(18, 25), type == ModEntities.GHOST.get() ? 0.4 : 0.6);
            }
        });
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }
}
