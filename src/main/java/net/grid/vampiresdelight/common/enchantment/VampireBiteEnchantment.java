package net.grid.vampiresdelight.common.enchantment;

import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.VDConfiguration;
import net.grid.vampiresdelight.common.registry.VDEnchantments;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class VampireBiteEnchantment {
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

    @EventBusSubscriber(modid = VampiresDelight.MODID, bus = EventBusSubscriber.Bus.GAME)
    public static class VampireBiteEvent {
        @SubscribeEvent
        public static void onVampireBite(LivingIncomingDamageEvent event) {
            if (event.getSource().getEntity() instanceof LivingEntity attacker) {
                Level level = attacker.level();
                if (level instanceof ServerLevel) {
                    ItemStack weapon = attacker.getWeaponItem();
                    Holder<Enchantment> vampireBite = attacker.level().registryAccess().holderOrThrow(VDEnchantments.VAMPIRE_BITE);
                    int enchantmentLevel = weapon.getEnchantmentLevel(vampireBite);

                    if (!attacker.getCommandSenderWorld().isClientSide && enchantmentLevel != 0) { // 0 = not enchanted with Vampire Bite
                        healFromDamage(attacker, enchantmentLevel, event.getAmount());
                    }
                }
            }
        }
    }
}
