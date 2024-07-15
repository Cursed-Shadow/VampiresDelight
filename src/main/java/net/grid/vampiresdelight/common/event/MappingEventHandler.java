package net.grid.vampiresdelight.common.event;

import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.registry.VDItems;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.MissingMappingsEvent;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;

@Mod.EventBusSubscriber(modid = VampiresDelight.MODID)
public class MappingEventHandler {
    @SubscribeEvent
    public static void onMissingMappings(@NotNull MissingMappingsEvent event) {
        event.getAllMappings(ForgeRegistries.Keys.ITEMS).forEach(MappingEventHandler::fixItems);
    }

    public static void fixItems(@NotNull MissingMappingsEvent.Mapping<Item> mapping) {
        switch (mapping.getKey().toString()) {
            case "vampiresdelight:eye_toast" -> mapping.remap(VDItems.EYE_CROISSANT.get());
            case "vampiresdelight:wine_glass" -> mapping.remap(VDItems.BLOOD_WINE_GLASS.get());
        }
    }
}
