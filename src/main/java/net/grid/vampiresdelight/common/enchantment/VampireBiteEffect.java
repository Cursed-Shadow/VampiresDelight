package net.grid.vampiresdelight.common.enchantment;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.grid.vampiresdelight.common.VDConfiguration;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;

import javax.annotation.ParametersAreNonnullByDefault;

/*
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public record VampireBiteEffect(LevelBasedValue level) implements EnchantmentEntityEffect {
    public static final MapCodec<VampireBiteEffect> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            LevelBasedValue.CODEC.fieldOf("level").forGetter(VampireBiteEffect::level))
            .apply(instance, VampireBiteEffect::new)
    );

    @Override
    public void apply(ServerLevel serverLevel, int enchantLevel, EnchantedItemInUse weapon, Entity victim, Vec3 position) {

    }

    public static void healFromDamage(LivingEntity user, int level, float damage) {
        RandomSource randomSource = user.getRandom();
        int chance = switch (level) {
            default -> VDConfiguration.VAMPIRE_BITE_HEALING_CHANCE_1.get();
            case 2 -> VDConfiguration.VAMPIRE_BITE_HEALING_CHANCE_2.get();
            case 3 -> VDConfiguration.VAMPIRE_BITE_HEALING_CHANCE_3.get();
        };
        int maxHealingValue = (int) (VDConfiguration.VAMPIRE_BITE_MAX_HEALING_VALUE.get() * 2);

        if (user instanceof Player player && randomSource.nextInt(100) <= chance) {
            float healAmount = (float) Math.ceil((double) level / 30 * damage);

            if (!user.getCommandSenderWorld().isClientSide) {
                player.heal(Math.min(healAmount, maxHealingValue));
            }
        }
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> codec() {
        return CODEC;
    }
}
 */
