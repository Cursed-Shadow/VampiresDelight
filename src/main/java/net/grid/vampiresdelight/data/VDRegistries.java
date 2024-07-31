package net.grid.vampiresdelight.data;

import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.world.VDBiomeModifiers;
import net.grid.vampiresdelight.common.world.VDConfiguredFeatures;
import net.grid.vampiresdelight.common.world.VDPlacedFeatures;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class VDRegistries extends DatapackBuiltinEntriesProvider {
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, VDConfiguredFeatures::createConfiguredFeatures)
            .add(Registries.PLACED_FEATURE, VDPlacedFeatures::createPlacedFeatures)
            .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, VDBiomeModifiers::createBiomeModifiers)
            .add(Registries.ENCHANTMENT, VDEnchantments::createEnchantments);

    public VDRegistries(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(VampiresDelight.MODID));
    }
}
