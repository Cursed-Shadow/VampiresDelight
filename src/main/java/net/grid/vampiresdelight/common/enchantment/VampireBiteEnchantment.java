package net.grid.vampiresdelight.common.enchantment;

import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.VDConfiguration;
import net.grid.vampiresdelight.common.registry.VDEnchantments;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

public class VampireBiteEnchantment {
    public static void healFromDamage(LivingEntity user, int level, float damage) {
        RandomSource randomSource = user.getRandom();
        int chance = switch (level) {
            case 1 -> VDConfiguration.VAMPIRE_BITE_HEALING_CHANCE_1.get();
            case 2 -> VDConfiguration.VAMPIRE_BITE_HEALING_CHANCE_2.get();
            case 3 -> VDConfiguration.VAMPIRE_BITE_HEALING_CHANCE_3.get();
            default -> 20;
        };
        int maxHealingValue = (int) (VDConfiguration.VAMPIRE_BITE_MAX_HEALING_VALUE.get() * 2);

        if (user instanceof Player player && randomSource.nextInt(100) <= chance) {
            float healAmount = (float) Math.ceil((double) level / 30 * damage);

            if (!user.getCommandSenderWorld().isClientSide) {
                player.heal(Math.min(healAmount, maxHealingValue));
            }
        }
    }

    @EventBusSubscriber(modid = VampiresDelight.MODID, bus = EventBusSubscriber.Bus.FORGE)
    public static class VampireBiteEvent {
        @SubscribeEvent
        public static void onVampireBite(LivingIncomingDamageEvent event) {
            if (event.getSource().getEntity() instanceof Player player) {
                ItemStack weapon = player.getMainHandItem();
                int enchantmentLevel = weapon.getEnchantmentLevel(VDEnchantments.VAMPIRE_BITE.get());

                if (!(VDConfiguration.DISABLE_VAMPIRE_BITE.get() && player.getCommandSenderWorld().isClientSide)) {
                    healFromDamage(player, enchantmentLevel, event.getAmount());
                }
            }
        }
    }

    @Override
    public int getMinCost(int enchantmentLevel) {
        return enchantmentLevel * 30;
    }

    @Override
    public int getMaxCost(int enchantmentLevel) {
        return super.getMinCost(enchantmentLevel) + 50;
    }

    @Override
    public boolean isTreasureOnly() {
        return true;
    }

    @Override
    public boolean isTradeable() {
        return false;
    }

    @Override
    public boolean isDiscoverable() {
        return false;
    }

    @Override
    public boolean canEnchant(ItemStack pStack) {
        return !VDConfiguration.DISABLE_VAMPIRE_BITE.get() && super.canEnchant(pStack);
    }
}
