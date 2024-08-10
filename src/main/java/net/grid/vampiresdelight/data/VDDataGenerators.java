package net.grid.vampiresdelight.data;

import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.registry.VDEnchantments;
import net.grid.vampiresdelight.common.world.VDBiomeModifiers;
import net.grid.vampiresdelight.common.world.VDConfiguredFeatures;
import net.grid.vampiresdelight.common.world.VDPlacedFeatures;
import net.grid.vampiresdelight.data.loot.VDBlockLootTableProvider;
import net.grid.vampiresdelight.data.loot.VDChestLootTableProvider;
import net.grid.vampiresdelight.data.tag.*;
import net.minecraft.DetectedVersion;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.metadata.PackMetadataGenerator;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.*;
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

        VDBlockTagProvider blockTags = new VDBlockTagProvider(output, lookupProvider, helper);
        generator.addProvider(event.includeServer(), blockTags);
        generator.addProvider(event.includeServer(), new VDItemTagProvider(output, lookupProvider, blockTags.contentsGetter(), helper));
        generator.addProvider(event.includeServer(), new VDBiomeTagProvider(output, lookupProvider, helper));
        generator.addProvider(event.includeServer(), new VDEntityTagProvider(output, lookupProvider, helper));

        DatapackBuiltinEntriesProvider datapackProvider = new DatapackBuiltinEntriesProvider(output, lookupProvider, registrySetBuilder, Set.of(VampiresDelight.MODID));
        CompletableFuture<HolderLookup.Provider> builtinLookupProvider = datapackProvider.getRegistryProvider();
        generator.addProvider(event.includeServer(), datapackProvider);
        generator.addProvider(event.includeServer(), new VDEnchantmentTagProvider(output, builtinLookupProvider, helper));

        generator.addProvider(event.includeServer(), new VDParticleDescriptionProvider(output, helper));
        generator.addProvider(event.includeServer(), new VDRecipeProvider(output, lookupProvider));
        generator.addProvider(event.includeServer(), new VDDataMapProvider(output, lookupProvider));
        generator.addProvider(event.includeServer(), new VDAdvancementProvider.Provider(output, lookupProvider, helper));
        generator.addProvider(event.includeServer(), new VDLootModifierProvider(output, lookupProvider));
        generator.addProvider(event.includeServer(), new LootTableProvider(output, Collections.emptySet(), List.of(
                new LootTableProvider.SubProviderEntry(VDBlockLootTableProvider::new, LootContextParamSets.BLOCK),
                new LootTableProvider.SubProviderEntry(VDChestLootTableProvider::new, LootContextParamSets.CHEST)
        ), builtinLookupProvider));

        generator.addProvider(event.includeClient(), new VDBlockModelProvider(output, helper));
        VDBlockStateProvider blockStates = new VDBlockStateProvider(output, helper);
        generator.addProvider(event.includeClient(), blockStates);
        generator.addProvider(event.includeClient(), new VDItemModelProvider(output, blockStates.models().existingFileHelper));
        generator.addProvider(event.includeClient(), new VDSoundDefinitionProvider(output, helper));

        generator.addProvider(true, new PackMetadataGenerator(output).add(PackMetadataSection.TYPE, new PackMetadataSection(
                Component.literal("Vampire's Delight resources"),
                DetectedVersion.BUILT_IN.getPackVersion(PackType.CLIENT_RESOURCES))));
    }
}
