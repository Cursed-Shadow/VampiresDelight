package net.grid.vampiresdelight.common.event;

import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.registry.VDItems;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
@EventBusSubscriber(modid = VampiresDelight.MODID, bus = EventBusSubscriber.Bus.MOD)
public class MappingEventHandler {
    @SubscribeEvent
    public static void fix(NewRegistryEvent event) {
        fixItems(new Mapping(VDItems.ITEMS));
    }

    public record Mapping(DeferredRegister<?> register) {
        public void remap(String id, String newId) {
            remap(ResourceLocation.parse(id), ResourceLocation.parse(newId));
        }

        public void remap(ResourceLocation id, ResourceLocation newId) {
            register.addAlias(id, newId);
        }
    }

    private static void fixItems(@NotNull Mapping mapping) {
        mapping.remap(id("eye_toast"), id("eye_croissant"));
        mapping.remap(id("wine_glass"), id("blood_wine_glass"));
        mapping.remap(id("grilled_garlic"), id("roasted_garlic"));
    }

    private static String id(String name) {
        return VampiresDelight.MODID + ":" + name;
    }
}
