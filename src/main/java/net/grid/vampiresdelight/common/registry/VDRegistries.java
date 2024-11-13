package net.grid.vampiresdelight.common.registry;

import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.item.component.WeatheredLetter;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;

@EventBusSubscriber(modid = VampiresDelight.MODID, bus = EventBusSubscriber.Bus.MOD)
public class VDRegistries {
    public static final ResourceKey<Registry<WeatheredLetter>> WEATHERED_LETTER = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(VampiresDelight.MODID, "weathered_letter"));

    @SubscribeEvent
    public static void registerDatapackRegistries(DataPackRegistryEvent.NewRegistry event) {
        event.dataPackRegistry(WEATHERED_LETTER, WeatheredLetter.CODEC, WeatheredLetter.CODEC);
    }
}
