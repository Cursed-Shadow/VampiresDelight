package net.grid.vampiresdelight.data;

import de.teamlapen.vampirism.core.ModItems;
import net.grid.vampiresdelight.common.registry.VDItems;
import net.grid.vampiresdelight.common.tag.VDCompatibilityTags;
import net.grid.vampiresdelight.common.tag.VDForgeTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import vectorwing.farmersdelight.common.tag.*;

public class VDItemTags extends ItemTagsProvider {
    public VDItemTags(DataGenerator generatorIn, BlockTagsProvider blockTagProvider, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(generatorIn, blockTagProvider, modId, existingFileHelper);
    }

    @Override
    protected void addTags() {
        this.registerFarmersDelightTags();
        this.registerForgeTags();
        this.registerCompatibilityTags();
    }

    private void registerFarmersDelightTags() {
    }

    @SuppressWarnings("unchecked")
    private void registerForgeTags() {
        tag(ForgeTags.VEGETABLES).addTags(VDForgeTags.VEGETABLES_GARLIC);
        tag(VDForgeTags.VEGETABLES_GARLIC).add(ModItems.ITEM_GARLIC.get());
    }

    public void registerCompatibilityTags() {
        tag(VDCompatibilityTags.CREATE_UPRIGHT_ON_BELT)
                .add(VDItems.ORCHID_TEA.get());

        tag(VDCompatibilityTags.SERENE_SEASONS_AUTUMN_CROPS)
                .add(ModItems.ITEM_GARLIC.get());
        tag(VDCompatibilityTags.SERENE_SEASONS_SUMMER_CROPS)
                .add(ModItems.ITEM_GARLIC.get());
    }
}
