package net.grid.vampiresdelight.common.registry;

import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.advancement.DrinkPouredTrigger;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.critereon.PlayerTrigger;
import net.minecraft.resources.ResourceLocation;

public class VDAdvancementTriggers {
    public static final PlayerTrigger DISGUSTING_FOOD_CONSUMED = new PlayerTrigger(new ResourceLocation(VampiresDelight.MODID, "disgusting_food_consumed"));
    public static final DrinkPouredTrigger DRINK_POURED = new DrinkPouredTrigger();

    public static void register() {
        CriteriaTriggers.register(DISGUSTING_FOOD_CONSUMED);
        CriteriaTriggers.register(DRINK_POURED);
    }
}
