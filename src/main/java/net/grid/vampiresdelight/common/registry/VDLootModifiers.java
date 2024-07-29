package net.grid.vampiresdelight.common.registry;

import com.mojang.serialization.MapCodec;
import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.loot.modifier.VDAddLootTableModifier;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class VDLootModifiers {
    public static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> LOOT_MODIFIERS = DeferredRegister.create(NeoForgeRegistries.GLOBAL_LOOT_MODIFIER_SERIALIZERS, VampiresDelight.MODID);

    public static final Supplier<MapCodec<? extends IGlobalLootModifier>> ADD_LOOT_TABLE = LOOT_MODIFIERS.register("add_loot_table", VDAddLootTableModifier.CODEC);
}
