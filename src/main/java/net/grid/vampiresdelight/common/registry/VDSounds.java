package net.grid.vampiresdelight.common.registry;

import net.grid.vampiresdelight.VampiresDelight;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

@SuppressWarnings("unused")
public class VDSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(Registries.SOUND_EVENT, VampiresDelight.MODID);

    // Source: https://youtu.be/EHm3rMwl4Gc?si=8uExBqU-7gTiJHcT
    public static final Supplier<SoundEvent> BOTTLE_BREAKS = SOUNDS.register("block.bottle_breaks",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(VampiresDelight.MODID, "block.bottle_breaks")));
    public static final Supplier<SoundEvent> METAL_PIPE = SOUNDS.register("metal_pipe",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(VampiresDelight.MODID, "metal_pipe")));
    public static final Supplier<SoundEvent> TRIANGLE = SOUNDS.register("triangle",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(VampiresDelight.MODID, "triangle")));
    public static final Supplier<SoundEvent> POURING_FULL = SOUNDS.register("item.pouring_full",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(VampiresDelight.MODID, "item.pouring_full")));
    public static final Supplier<SoundEvent> POURING_SHORT = SOUNDS.register("item.pouring_short",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(VampiresDelight.MODID, "item.pouring_short")));
    public static final Supplier<SoundEvent> POURING_FINISH = SOUNDS.register("item.pouring_finish",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(VampiresDelight.MODID, "item.pouring_finish")));
}
