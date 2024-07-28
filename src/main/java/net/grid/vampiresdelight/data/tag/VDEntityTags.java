package net.grid.vampiresdelight.data.tag;

import net.grid.vampiresdelight.VampiresDelight;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.EntityTypeTags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class VDEntityTags extends EntityTypeTagsProvider {
    public VDEntityTags(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pProvider, VampiresDelight.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider pProvider) {
        registerMinecraftTags();
    }

    private void registerMinecraftTags() {
        tag(EntityTypeTags.POWDER_SNOW_WALKABLE_MOBS).addOptionalTag(new ResourceLocation("vampirism:hunter"));
    }
}
