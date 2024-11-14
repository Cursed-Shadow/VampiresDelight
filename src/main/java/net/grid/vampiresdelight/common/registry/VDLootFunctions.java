package net.grid.vampiresdelight.common.registry;

import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.loot.function.SetLetterFunction;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class VDLootFunctions {
    public static final DeferredRegister<LootItemFunctionType<?>> LOOT_FUNCTIONS = DeferredRegister.create(Registries.LOOT_FUNCTION_TYPE, VampiresDelight.MODID);

    public static final Supplier<LootItemFunctionType<SetLetterFunction>> SET_LETTER = LOOT_FUNCTIONS.register("set_letter", () -> new LootItemFunctionType<>(SetLetterFunction.CODEC));
}
