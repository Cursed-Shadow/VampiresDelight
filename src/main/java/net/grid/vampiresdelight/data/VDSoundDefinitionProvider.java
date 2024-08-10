package net.grid.vampiresdelight.data;

import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.registry.VDSounds;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SoundDefinition;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;

public class VDSoundDefinitionProvider extends SoundDefinitionsProvider {
    protected VDSoundDefinitionProvider(PackOutput output, ExistingFileHelper helper) {
        super(output, VampiresDelight.MODID, helper);
    }

    @Override
    public void registerSounds() {
        this.addSimpleSound(VDSounds.METAL_PIPE.get(), "metal_pipe", true);

        this.addVariedSound(VDSounds.TRIANGLE.get(), "triangle", 3, true);

        this.addSimpleSound(VDSounds.POURING_FULL.get(), "pouring_full", false);
        this.addSimpleSound(VDSounds.POURING_SHORT.get(), "pouring_short", true);
        this.addSimpleSound(VDSounds.POURING_FINISH.get(), "pouring_finish", false);

        this.add(VDSounds.BOTTLE_BREAKS.get(), definition().subtitle(getSubtitle(VDSounds.BOTTLE_BREAKS.get()))
                .with(SoundDefinition.Sound.sound(ResourceLocation.fromNamespaceAndPath(VampiresDelight.MODID, "bottle_breaks_1"), SoundDefinition.SoundType.SOUND))
                .with(SoundDefinition.Sound.sound(ResourceLocation.fromNamespaceAndPath(VampiresDelight.MODID, "bottle_breaks_1"), SoundDefinition.SoundType.SOUND))
                .with(SoundDefinition.Sound.sound(ResourceLocation.fromNamespaceAndPath(VampiresDelight.MODID, "bottle_breaks_2"), SoundDefinition.SoundType.SOUND))
                .with(SoundDefinition.Sound.sound(ResourceLocation.fromNamespaceAndPath(VampiresDelight.MODID, "bottle_breaks_2"), SoundDefinition.SoundType.SOUND))
                .with(SoundDefinition.Sound.sound(ResourceLocation.withDefaultNamespace("random/glass2"), SoundDefinition.SoundType.SOUND))
                .with(SoundDefinition.Sound.sound(ResourceLocation.withDefaultNamespace("random/glass3"), SoundDefinition.SoundType.SOUND)));

        // TODO: Also make one for Alchemical Cocktail landing sound (for better subtitles)
        this.addOverwrittenSound(VDSounds.ALCHEMICAL_COCKTAIL_THROW.get(), SoundEvents.SNOWBALL_THROW);
    }

    private void addSimpleSound(SoundEvent sound, String fileName, boolean hasSubtitle) {
        SoundDefinition soundDefinition = definition();
        if (hasSubtitle) {
            soundDefinition.subtitle(getSubtitle(sound));
        }
        soundDefinition.with(SoundDefinition.Sound.sound(ResourceLocation.fromNamespaceAndPath(VampiresDelight.MODID, fileName), SoundDefinition.SoundType.SOUND));

        this.add(sound, soundDefinition);
    }

    private void addVariedSound(SoundEvent sound, String fileName, int count, boolean hasSubtitle) {
        SoundDefinition soundDefinition = definition();
        if (hasSubtitle) {
            soundDefinition.subtitle(getSubtitle(sound));
        }
        for (int i = 1; i <= count; i++) {
            soundDefinition.with(SoundDefinition.Sound.sound(ResourceLocation.fromNamespaceAndPath(VampiresDelight.MODID, fileName + "_" + i), SoundDefinition.SoundType.SOUND));
        }

        this.add(sound, soundDefinition);
    }

    public void addOverwrittenSound(SoundEvent sound, SoundEvent overwrittenSound) {
        SoundDefinition soundDefinition = definition().subtitle(getSubtitle(sound));
        soundDefinition.with(SoundDefinition.Sound.sound(overwrittenSound.getLocation(), SoundDefinition.SoundType.EVENT));

        this.add(sound, soundDefinition);
    }

    private static String getSubtitle(SoundEvent sound) {
        return VampiresDelight.MODID + ".subtitles." + sound.getLocation().getPath();
    }
}
