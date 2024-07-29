package net.grid.vampiresdelight.common.utility;

import net.grid.vampiresdelight.VampiresDelight;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class VDTextUtils {
    public static MutableComponent getTranslation(String key, Object... args) {
        return Component.translatable(VampiresDelight.MODID + "." + key, args);
    }

    public static String getStraightTranslation(String key) {
        return I18n.get(VampiresDelight.MODID + "." + key);
    }

    public static void addFoodEffectTooltip(ItemStack stack, LivingEntity consumer, List<Component> tooltip, Item.TooltipContext context) {
        FoodProperties foodProperties = stack.getItem().getFoodProperties(stack, consumer);
        if (foodProperties == null) return;

        List<FoodProperties.PossibleEffect> effectList = foodProperties.effects();

        MutableComponent mutableComponent;

        for (FoodProperties.PossibleEffect possibleEffect : effectList) {
            MobEffectInstance instance = possibleEffect.effect();
            mutableComponent = Component.translatable(instance.getDescriptionId());
            MobEffect effect = instance.getEffect().value();

            if (instance.getAmplifier() > 0) {
                mutableComponent = Component.translatable("potion.withAmplifier", mutableComponent, Component.translatable("potion.potency." + instance.getAmplifier()));
            }

            if (instance.getDuration() > 20) {
                mutableComponent = Component.translatable("potion.withDuration", mutableComponent, MobEffectUtil.formatDuration(instance, 1.0F, context.tickRate()));
            }

            tooltip.add(mutableComponent.withStyle(effect.getCategory().getTooltipFormatting()));
        }
    }
}
