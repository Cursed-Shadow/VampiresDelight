package net.grid.vampiresdelight.data;

import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.world.VDBiomeModifiers;
import net.grid.vampiresdelight.common.world.VDConfiguredFeatures;
import net.grid.vampiresdelight.common.world.VDPlacedFeatures;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class VDRegistrySets extends DatapackBuiltinEntriesProvider {
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, VDConfiguredFeatures::createConfiguredFeatures)
            .add(Registries.PLACED_FEATURE, VDPlacedFeatures::createPlacedFeatures)
            .add(ForgeRegistries.Keys.BIOME_MODIFIERS, VDBiomeModifiers::createBiomeModifiers);
            //.add(Registries.STRUCTURE, VDStructures::createStructures)
            //.add(Registries.STRUCTURE_SET, VDStructures::createStructureSets);

    public VDRegistrySets(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(VampiresDelight.MODID));
    }
}
