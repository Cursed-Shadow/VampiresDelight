package net.grid.vampiresdelight.common.registry;

import net.grid.vampiresdelight.VampiresDelight;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class VDStats {
    public static final DeferredRegister<ResourceLocation> CUSTOM_STATS = DeferredRegister.create(Registries.CUSTOM_STAT, VampiresDelight.MODID);

    public static final DeferredHolder<ResourceLocation, ResourceLocation> DISGUSTING_FOOD_CONSUMED = add("disgusting_food_consumed");

    private static DeferredHolder<ResourceLocation, ResourceLocation> add(String name) {
        var id = ResourceLocation.fromNamespaceAndPath(CUSTOM_STATS.getNamespace(), name);
        return CUSTOM_STATS.register(name, () -> id);
    }
}
