package net.grid.vampiresdelight.data;

import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.world.VDBiomeModifiers;
import net.grid.vampiresdelight.common.world.VDConfiguredFeatures;
import net.grid.vampiresdelight.common.world.VDPlacedFeatures;
import net.grid.vampiresdelight.data.loot.VDBlockLootTables;
import net.grid.vampiresdelight.data.loot.VDChestLootTables;
import net.grid.vampiresdelight.data.tag.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@SuppressWarnings("unused")
@EventBusSubscriber(modid = VampiresDelight.MODID, bus = EventBusSubscriber.Bus.MOD)
public class VDDataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        ExistingFileHelper helper = event.getExistingFileHelper();
        RegistrySetBuilder registrySetBuilder = new RegistrySetBuilder()
                .add(Registries.CONFIGURED_FEATURE, VDConfiguredFeatures::createConfiguredFeatures)
                .add(Registries.PLACED_FEATURE, VDPlacedFeatures::createPlacedFeatures)
                .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, VDBiomeModifiers::createBiomeModifiers)
                .add(Registries.ENCHANTMENT, VDEnchantments::createEnchantments);

        VDBlockTags blockTags = new VDBlockTags(output, lookupProvider, helper);
        generator.addProvider(event.includeServer(), blockTags);
        generator.addProvider(event.includeServer(), new VDItemTags(output, lookupProvider, blockTags.contentsGetter(), helper));
        generator.addProvider(event.includeServer(), new VDBiomeTags(output, lookupProvider, helper));
        generator.addProvider(event.includeServer(), new VDEntityTags(output, lookupProvider, helper));

        DatapackBuiltinEntriesProvider datapackProvider = new DatapackBuiltinEntriesProvider(output, lookupProvider, registrySetBuilder, Set.of(VampiresDelight.MODID));
        CompletableFuture<HolderLookup.Provider> builtinLookupProvider = datapackProvider.getRegistryProvider();
        generator.addProvider(event.includeServer(), datapackProvider);
        generator.addProvider(event.includeServer(), new VDEnchantmentTags(output, builtinLookupProvider, helper));

        generator.addProvider(event.includeServer(), new VDRecipes(output, lookupProvider));
        generator.addProvider(event.includeServer(), new VDDataMaps(output, lookupProvider));
        generator.addProvider(event.includeServer(), new VDAdvancements.Provider(output, lookupProvider, helper));
        generator.addProvider(event.includeServer(), new LootTableProvider(output, Collections.emptySet(), List.of(
                new LootTableProvider.SubProviderEntry(VDBlockLootTables::new, LootContextParamSets.BLOCK),
                new LootTableProvider.SubProviderEntry(VDChestLootTables::new, LootContextParamSets.CHEST)
        ), builtinLookupProvider));

        generator.addProvider(event.includeClient(), new VDBlockModels(output, helper));
        VDBlockStates blockStates = new VDBlockStates(output, helper);
        generator.addProvider(event.includeClient(), blockStates);
        generator.addProvider(event.includeClient(), new VDItemModels(output, blockStates.models().existingFileHelper));
    }
}
