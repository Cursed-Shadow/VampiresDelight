package net.grid.vampiresdelight.data.tag;

import de.teamlapen.vampirism.core.ModBlocks;
import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.block.BarStoolBlock;
import net.grid.vampiresdelight.common.block.ConsumableCandleCakeBlock;
import net.grid.vampiresdelight.common.block.WineShelfBlock;
import net.grid.vampiresdelight.common.registry.VDBlocks;
import net.grid.vampiresdelight.common.tag.VDCompatibilityTags;
import net.grid.vampiresdelight.common.tag.VDTags;
import net.grid.vampiresdelight.common.utility.VDNameUtils;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import vectorwing.farmersdelight.common.tag.ModTags;

import java.util.concurrent.CompletableFuture;

public class VDBlockTags extends BlockTagsProvider {
    public VDBlockTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, VampiresDelight.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        this.registerModTags();
        this.registerBlockMineables();
        this.registerMinecraftTags();
        this.registerFarmersDelightTags();
        this.registerCommonTags();
        this.registerCompatibilityTags();
    }

    private void registerModTags() {
        WineShelfBlock.getAllShelveBlocks().forEach(block -> tag(VDTags.WINE_SHELF).add(block));

        ConsumableCandleCakeBlock.getAllCandleCakes().forEach(block -> {
            String name = VDNameUtils.blockName(block);
            if (name.contains("orchid"))
                tag(VDTags.DROPS_ORCHID_CAKE_SLICE).add(block);

            tag(BlockTags.CANDLE_CAKES).add(block);
        });

        tag(VDTags.BLACK_MUSHROOM_GROW_BLOCK)
                .addTag(de.teamlapen.vampirism.core.ModTags.Blocks.CURSED_EARTH)
                .add(VDBlocks.BLOODY_SOIL.get());

        tag(VDTags.CURSED_PLANTS).add(
                VDBlocks.VAMPIRE_ORCHID_CROP.get(),
                ModBlocks.DARK_SPRUCE_SAPLING.get(),
                ModBlocks.CURSED_SPRUCE_SAPLING.get()
        );
    }

    protected void registerBlockMineables() {
        tag(BlockTags.MINEABLE_WITH_AXE).add(
                VDBlocks.GARLIC_CRATE.get(),
                VDBlocks.DARK_SPRUCE_CABINET.get(),
                VDBlocks.CURSED_SPRUCE_CABINET.get(),
                VDBlocks.JACARANDA_CABINET.get(),
                VDBlocks.MAGIC_CABINET.get(),
                VDBlocks.BLACK_MUSHROOM_BLOCK.get(),
                VDBlocks.BLACK_MUSHROOM_STEM.get(),
                VDBlocks.BLACK_MUSHROOM.get()
        );

        WineShelfBlock.getAllShelveBlocks().forEach(block -> tag(BlockTags.MINEABLE_WITH_AXE).add(block));
        BarStoolBlock.getBarStoolBlocks().forEach(block -> tag(BlockTags.MINEABLE_WITH_AXE).add(block));

        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(
                VDBlocks.DARK_STONE_STOVE.get(),
                VDBlocks.SPIRIT_LANTERN.get()
        );
        tag(BlockTags.MINEABLE_WITH_SHOVEL).add(
                VDBlocks.CURSED_FARMLAND.get(),
                VDBlocks.BLOODY_SOIL.get(),
                VDBlocks.BLOODY_SOIL_FARMLAND.get()
        );
        tag(ModTags.MINEABLE_WITH_KNIFE)
                .addTag(VDTags.DROPS_ORCHID_CAKE_SLICE)
                .add(
                        VDBlocks.BLOOD_PIE.get(),
                        VDBlocks.WEIRD_JELLY_BLOCK.get(),
                        VDBlocks.ORCHID_CAKE.get()
                );
    }

    protected void registerMinecraftTags() {
        tag(BlockTags.BAMBOO_PLANTABLE_ON).add(
                VDBlocks.BLOODY_SOIL.get());
        tag(BlockTags.MUSHROOM_GROW_BLOCK).add(
                VDBlocks.BLOODY_SOIL.get());
        tag(BlockTags.CROPS).add(
                ModBlocks.GARLIC.get(),
                VDBlocks.VAMPIRE_ORCHID_CROP.get());
        tag(BlockTags.SMALL_FLOWERS).add(
                VDBlocks.WILD_GARLIC.get());
        tag(BlockTags.FLOWER_POTS)
                .add(VDBlocks.POTTED_BLACK_MUSHROOM.get());
        tag(BlockTags.SWORD_EFFICIENT)
                .add(VDBlocks.POTTED_BLACK_MUSHROOM.get());
    }

    private void registerFarmersDelightTags() {
        tag(ModTags.WILD_CROPS).add(
                VDBlocks.WILD_GARLIC.get());
        tag(ModTags.STRAW_BLOCKS).add(
                VDBlocks.ORCHID_BAG.get());
        tag(ModTags.TRAY_HEAT_SOURCES).add(
                ModBlocks.FIRE_PLACE.get());
        tag(ModTags.HEAT_SOURCES).add(
                VDBlocks.DARK_STONE_STOVE.get());
    }

    private void registerCommonTags() {
        BarStoolBlock.getBarStoolBlocks().forEach(block -> {
            tag(Tags.Blocks.DYED).add(block);

            if (block instanceof BarStoolBlock barStoolBlock) {
                ResourceLocation tag = ResourceLocation.fromNamespaceAndPath("c", "dyed/" + barStoolBlock.getColor().getName());
                tag(BlockTags.create(tag)).add(block);
            }
        });
    }

    private void registerCompatibilityTags() {
        tag(VDCompatibilityTags.CREATE_PASSIVE_BOILER_HEATERS).add(
                VDBlocks.DARK_STONE_STOVE.get());

        tag(VDCompatibilityTags.SERENE_SEASONS_AUTUMN_CROPS_BLOCK).add(
                ModBlocks.GARLIC.get());
        tag(VDCompatibilityTags.SERENE_SEASONS_SPRING_CROPS_BLOCK).add(
                VDBlocks.VAMPIRE_ORCHID_CROP.get());
        tag(VDCompatibilityTags.SERENE_SEASONS_SUMMER_CROPS_BLOCK).add(
                ModBlocks.GARLIC.get(),
                VDBlocks.VAMPIRE_ORCHID_CROP.get());
    }
}
