package net.grid.vampiresdelight.data;

import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.registry.VDLootTables;
import net.grid.vampiresdelight.data.loot.VDBlockLootTables;
import net.grid.vampiresdelight.data.loot.VDChestLootTables;
import net.grid.vampiresdelight.data.tag.VDBiomeTags;
import net.grid.vampiresdelight.data.tag.VDBlockTags;
import net.grid.vampiresdelight.data.tag.VDEntityTags;
import net.grid.vampiresdelight.data.tag.VDItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.List;
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

        VDBlockTags blockTags = new VDBlockTags(output, lookupProvider, helper);
        generator.addProvider(event.includeServer(), blockTags);
        generator.addProvider(event.includeServer(), new VDItemTags(output, lookupProvider, blockTags.contentsGetter(), helper));
        generator.addProvider(event.includeServer(), new VDBiomeTags(output, lookupProvider, helper));
        generator.addProvider(event.includeServer(), new VDEntityTags(output, lookupProvider, helper));

        generator.addProvider(event.includeServer(), new VDRecipes(output, lookupProvider));
        generator.addProvider(event.includeServer(), new VDDataMaps(output, lookupProvider));
        generator.addProvider(event.includeServer(), new VDRegistrySets(output, lookupProvider));
        generator.addProvider(event.includeServer(), new VDAdvancements(output, lookupProvider, helper));
        generator.addProvider(event.includeServer(), new LootTableProvider(output, VDLootTables.getLootTables(), List.of(
                new LootTableProvider.SubProviderEntry(VDBlockLootTables::new, LootContextParamSets.BLOCK),
                new LootTableProvider.SubProviderEntry(VDChestLootTables::new, LootContextParamSets.CHEST)
        )));

        VDBlockStates blockStates = new VDBlockStates(output, helper);
        generator.addProvider(event.includeClient(), blockStates);
        generator.addProvider(event.includeClient(), new VDBlockModels(output, helper));
        generator.addProvider(event.includeClient(), new VDItemModels(output, blockStates.models().existingFileHelper));
    }
}
