package net.grid.vampiresdelight.data.tag;

import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.item.component.WeatheredLetter;
import net.grid.vampiresdelight.common.registry.VDRegistries;
import net.grid.vampiresdelight.common.registry.VDWeatheredLetters;
import net.grid.vampiresdelight.common.tag.VDTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class VDWeatheredLetterTagProvider extends TagsProvider<WeatheredLetter> {

    public VDWeatheredLetterTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, VDRegistries.WEATHERED_LETTER, lookupProvider, VampiresDelight.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        registerModTags();
    }

    private void registerModTags() {
        tag(VDTags.HUNTER_WRITINGS)
                .add(VDWeatheredLetters.DEAR_MARCUS);
        tag(VDTags.HUNTER_RECIPES)
                .add(VDWeatheredLetters.BORSCHT_RECIPE);
    }
}
