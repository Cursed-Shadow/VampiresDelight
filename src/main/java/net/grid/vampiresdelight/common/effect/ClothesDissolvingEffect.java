package net.grid.vampiresdelight.common.effect;

import com.google.common.collect.ImmutableSet;
import net.grid.vampiresdelight.common.VDConfiguration;
import net.grid.vampiresdelight.common.utility.VDEntityUtils;
import net.minecraft.stats.Stats;
import net.minecraft.util.FastColor;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import org.jetbrains.annotations.NotNull;
import vectorwing.farmersdelight.common.registry.ModParticleTypes;

public class ClothesDissolvingEffect extends MobEffect {
    private static final ImmutableSet<ArmorMaterial> FULLY_BREAKABLE_ARMOR = ImmutableSet.of(
            ArmorMaterials.LEATHER,
            ArmorMaterials.CHAIN,
            ArmorMaterials.GOLD
    );

    public ClothesDissolvingEffect() {
        super(MobEffectCategory.HARMFUL, FastColor.ARGB32.color(100, 206, 177, 128));
    }

    @Override
    public void applyEffectTick(@NotNull LivingEntity livingEntity, int amplifier) {
        ItemStack[] armor = new ItemStack[] {
                livingEntity.getItemBySlot(EquipmentSlot.HEAD),
                livingEntity.getItemBySlot(EquipmentSlot.CHEST),
                livingEntity.getItemBySlot(EquipmentSlot.LEGS),
                livingEntity.getItemBySlot(EquipmentSlot.FEET)
        };

        for (ItemStack stack : armor) {
            int damagePerTick = getDamagePerTick(stack);
            int durability = stack.getMaxDamage() - stack.getDamageValue();

            if (damagePerTick < durability)
                stack.setDamageValue(stack.getDamageValue() + damagePerTick);
            else {
                if (stack.getItem() instanceof ArmorItem armorItem) {
                    if (FULLY_BREAKABLE_ARMOR.contains(armorItem.getMaterial()) && VDConfiguration.ARMOR_DISSOLVES_FULLY.get()) {
                        stack.shrink(1);
                        if (livingEntity instanceof Player) {
                            ((Player) livingEntity).awardStat(Stats.ITEM_BROKEN.get(stack.getItem()));
                        }
                    }
                }
            }
        }

        VDEntityUtils.addParticlesAroundEntity(ModParticleTypes.STEAM.get(), livingEntity,
                livingEntity.getRandom().nextInt(3, 8), 0.015D, -0.5D);
    }

    public int getDamagePerTick(ItemStack stack) {
        int maxDamage = stack.getMaxDamage();
        int damageDivider = 90;

        if (stack.getItem() instanceof ArmorItem armorItem) {
            damageDivider = (FULLY_BREAKABLE_ARMOR.contains(armorItem.getMaterial()) && VDConfiguration.ARMOR_DISSOLVES_FULLY.get()) ? 15 : 80;

            if (armorItem.getAllEnchantments(stack).containsKey(Enchantments.UNBREAKING)) {
                int level = armorItem.getAllEnchantments(stack).get(Enchantments.UNBREAKING);
                damageDivider += ((damageDivider / 2) * level);
            }
        }

        return maxDamage / damageDivider;
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        int j = 15 >> amplifier;
        if (j > 0) {
            return duration % j == 0;
        } else {
            return true;
        }
    }
}
