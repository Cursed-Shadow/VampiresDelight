package net.grid.vampiresdelight.common.registry;

import net.grid.vampiresdelight.VampiresDelight;
import net.minecraft.core.component.DataComponentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import vectorwing.farmersdelight.common.item.component.ItemStackWrapper;

public class VDDataComponents {
    public static final DeferredRegister.DataComponents DATA_COMPONENTS = DeferredRegister.createDataComponents(VampiresDelight.MODID);

    // Pourable Bottle Item
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<ItemStackWrapper>> SERVING_HELD = DATA_COMPONENTS.registerComponentType(
            "serving_held", (builder) -> builder.persistent(ItemStackWrapper.CODEC).networkSynchronized(ItemStackWrapper.STREAM_CODEC).cacheEncoding()
    );
}
