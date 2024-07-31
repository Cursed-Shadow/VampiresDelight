package net.grid.vampiresdelight.common.effect;

import de.teamlapen.vampirism.entity.GhostEntity;
import net.grid.vampiresdelight.common.VDConfiguration;
import net.grid.vampiresdelight.common.registry.VDEffects;
import net.grid.vampiresdelight.common.registry.VDParticleTypes;
import net.grid.vampiresdelight.common.registry.VDSounds;
import net.grid.vampiresdelight.common.utility.VDEntityUtils;
import net.grid.vampiresdelight.common.utility.VDHelper;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.FastColor;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.List;

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

        resistUnholySpirits(entity.level(), entity.getX(), entity.getY(), entity.getZ(), amplifier);

        return true;
    }

    public static void resistUnholySpirits(final LevelAccessor world, final double x, final double y, final double z, int amplifier) {
        Vec3 center = new Vec3(x, y, z);
        List<LivingEntity> unsortedEntityFound = world.getEntitiesOfClass(LivingEntity.class, new AABB(center, center).inflate(5.0 * (amplifier + 1)), e -> true);
        List<LivingEntity> sortedEntitiesFound = unsortedEntityFound.stream().sorted(Comparator.comparingDouble(t -> t.distanceToSqr(center))).toList();

        for (LivingEntity livingEntity : sortedEntitiesFound) {
            double entityX = livingEntity.getX();
            double entityY = livingEntity.getY();
            double entityZ = livingEntity.getZ();

            if (livingEntity instanceof Phantom || (livingEntity instanceof GhostEntity && VDConfiguration.BLESSING_HELPS_AGAINST_GHOSTS.get())) {
                //if (!livingEntity.getEntityData().isEmpty()) {
                    livingEntity.remove(Entity.RemovalReason.DISCARDED);

                    Level level = (Level) world;
                    level.playSound(null, entityX, entityY, entityZ, VDSounds.TRIANGLE.get(), SoundSource.NEUTRAL, 0.4f, level.random.nextFloat() * 0.5F);

                    int amount = (livingEntity instanceof Phantom) ?
                            livingEntity.getRandom().nextInt(18, 25) :
                            livingEntity.getRandom().nextInt(4, 10);

                    VDEntityUtils.spawnBlessingParticlesAroundEntity(VDParticleTypes.BLESSING.get(), livingEntity, amount);
                //}
            }
        }
    }
}
