package net.grid.vampiresdelight.common.item.enchantment;

import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.VDConfiguration;
import net.grid.vampiresdelight.common.registry.VDEnchantments;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class VampireBiteEnchantment extends Enchantment {
    public VampireBiteEnchantment(Rarity rarity, EnchantmentCategory category) {
        super(rarity, category, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    public static void healFromDamage(LivingEntity user, int level, float damage) {
        RandomSource randomSource = user.getRandom();
        int chance = switch (level) {
            case 1 -> VDConfiguration.VAMPIRE_BITE_HEALING_CHANCE_1.get();
            case 2 -> VDConfiguration.VAMPIRE_BITE_HEALING_CHANCE_2.get();
            case 3 -> VDConfiguration.VAMPIRE_BITE_HEALING_CHANCE_3.get();
            default -> 20;
        };
        int maxHealingValue = (int) (VDConfiguration.VAMPIRE_BITE_MAX_HEALING_VALUE.get() * 2);

        if (randomSource.nextInt(100) <= chance && user instanceof Player player) {
            float healAmount = (float) level / 30 * damage;
            if (healAmount > maxHealingValue) healAmount = maxHealingValue;
            player.heal(healAmount);
        }
    }

    @Mod.EventBusSubscriber(modid = VampiresDelight.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class VampireBiteEvent {
        @SubscribeEvent
        public static void onVampireBite(LivingHurtEvent event) {
            if (event.getSource().getEntity() instanceof Player player) {
                ItemStack weapon = player.getMainHandItem();
                int enchantmentLevel = EnchantmentHelper.getItemEnchantmentLevel(VDEnchantments.VAMPIRE_BITE.get(), weapon);
                Level level = event.getEntity().getCommandSenderWorld();
                if (!level.isClientSide) {
                    if (!VDConfiguration.DISABLE_VAMPIRE_BITE.get())
                        healFromDamage(player, enchantmentLevel, event.getAmount());
                }
            }
        }
    }

    @Override
    public int getMaxLevel() {
        return 3;
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
