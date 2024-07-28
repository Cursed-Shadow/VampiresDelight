package net.grid.vampiresdelight.data.tag;

import net.grid.vampiresdelight.VampiresDelight;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.EntityTypeTags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class VDEntityTags extends EntityTypeTagsProvider {

    public VDEntityTags(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, provider, VampiresDelight.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider pProvider) {
        registerMinecraftTags();
    }

    private void registerMinecraftTags() {
        tag(EntityTypeTags.POWDER_SNOW_WALKABLE_MOBS).addOptionalTag(ResourceLocation.parse("vampirism:hunter"));
    }
}
