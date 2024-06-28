package net.grid.vampiresdelight.common.registry;

import net.grid.vampiresdelight.VampiresDelight;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class VDSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, VampiresDelight.MODID);

    public static final RegistryObject<SoundEvent> TRIANGLE = SOUNDS.register("triangle",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(VampiresDelight.MODID, "triangle")));
    public static final RegistryObject<SoundEvent> POURING_FULL = SOUNDS.register("item.pouring_full",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(VampiresDelight.MODID, "item.pouring_full")));
    public static final RegistryObject<SoundEvent> POURING_SHORT = SOUNDS.register("item.pouring_short",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(VampiresDelight.MODID, "item.pouring_short")));
    public static final RegistryObject<SoundEvent> POURING_FINISH = SOUNDS.register("item.pouring_finish",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(VampiresDelight.MODID, "item.pouring_finish")));
}
