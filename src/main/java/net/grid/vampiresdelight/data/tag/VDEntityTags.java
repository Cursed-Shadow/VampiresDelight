package net.grid.vampiresdelight.data.tag;

import de.teamlapen.vampirism.core.ModEntities;
import de.teamlapen.vampirism.core.ModTags;
import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.tag.VDTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.EntityType;
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
        registerModTags();
        registerMinecraftTags();
    }

    private void registerModTags() {
        tag(VDTags.UNHOLY_SPIRITS)
                .add(EntityType.PHANTOM)
                .add(ModEntities.GHOST.get());
        tag(VDTags.DROP_HUMAN_EYE)
                .addTag(ModTags.Entities.HUNTER)
                .addTag(EntityTypeTags.RAIDERS)
                .add(EntityType.VILLAGER)
                .add(EntityType.WANDERING_TRADER);
    }

    private void registerMinecraftTags() {
        tag(EntityTypeTags.POWDER_SNOW_WALKABLE_MOBS)
                .addTag(ModTags.Entities.HUNTER);
    }
}
