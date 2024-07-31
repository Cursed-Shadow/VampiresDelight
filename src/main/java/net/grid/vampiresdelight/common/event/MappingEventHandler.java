package net.grid.vampiresdelight.common.event;

import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.registry.VDItems;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import org.jetbrains.annotations.NotNull;

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

        public void remap(ResourceLocation id, ResourceLocation object) {
            register.addAlias(id, object);
        }
    }

    private static void fixItems(@NotNull Mapping mapping) {
        mapping.remap("vampiresdelight:eye_toast", "vampiresdelight:eye_croissant");
        mapping.remap("vampiresdelight:wine_glass", "vampiresdelight:blood_wine_glass");
    }
}
