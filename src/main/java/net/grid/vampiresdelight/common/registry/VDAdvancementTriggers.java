package net.grid.vampiresdelight.common.registry;

import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.advancement.DrinkPouredTrigger;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.advancements.critereon.PlayerTrigger;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class VDAdvancementTriggers {
    public static final DeferredRegister<CriterionTrigger<?>> TRIGGERS = DeferredRegister.create(Registries.TRIGGER_TYPE, VampiresDelight.MODID);

    public static final Supplier<DrinkPouredTrigger> DRINK_POURED = TRIGGERS.register("drink_poured", DrinkPouredTrigger::new);
    public static final Supplier<PlayerTrigger> DISGUSTING_FOOD_CONSUMED = TRIGGERS.register("disgusting_food_consumed", PlayerTrigger::new);
}
