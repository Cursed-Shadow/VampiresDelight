package net.grid.vampiresdelight.data;

import de.teamlapen.vampirism.core.ModBlocks;
import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.block.BarStoolBlock;
import net.grid.vampiresdelight.common.block.ConsumableCandleCakeBlock;
import net.grid.vampiresdelight.common.block.WineShelfBlock;
import net.grid.vampiresdelight.common.registry.VDBlocks;
import net.grid.vampiresdelight.common.tag.VDTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import vectorwing.farmersdelight.common.tag.CompatibilityTags;
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
        this.registerCompatibilityTags();
    }

    private void registerModTags() {
        WineShelfBlock.getAllShelveBlocks().forEach(block -> tag(VDTags.WINE_SHELF).add(block));

        ConsumableCandleCakeBlock.getAllCandleCakes().forEach(block -> {
            String name = ForgeRegistries.BLOCKS.getKey(block).getPath();
            if (name.contains("orchid"))
                    tag(VDTags.DROPS_ORCHID_CAKE_SLICE).add(block);

            tag(BlockTags.CANDLE_CAKES).add(block);
        });

        tag(VDTags.BLACK_MUSHROOM_GROW_BLOCK)
                .addTag(de.teamlapen.vampirism.core.ModTags.Blocks.CURSED_EARTH)
                .add(VDBlocks.BLOODY_SOIL.get());
    }

    protected void registerBlockMineables() {
        tag(BlockTags.MINEABLE_WITH_AXE).add(
                VDBlocks.GARLIC_CRATE.get(),
                VDBlocks.DARK_SPRUCE_CABINET.get(),
                VDBlocks.CURSED_SPRUCE_CABINET.get(),
                VDBlocks.JACARANDA_CABINET.get(),
                VDBlocks.MAGIC_CABINET.get(),
                VDBlocks.BREWING_BARREL.get(),
                VDBlocks.BLACK_MUSHROOM_BLOCK.get(),
                VDBlocks.BLACK_MUSHROOM_STEM.get(),
                VDBlocks.BLACK_MUSHROOM.get()
        );

        WineShelfBlock.getAllShelveBlocks().forEach(block -> tag(BlockTags.MINEABLE_WITH_AXE).add(block));
        BarStoolBlock.getBarStoolBlocks().forEach(block -> tag(BlockTags.MINEABLE_WITH_AXE).add(block));

        tag(ModTags.MINEABLE_WITH_KNIFE)
                .addTag(VDTags.DROPS_ORCHID_CAKE_SLICE)
                .add(
                        VDBlocks.BLOOD_PIE.get(),
                        VDBlocks.WEIRD_JELLY_BLOCK.get(),
                        VDBlocks.ORCHID_CAKE.get()
                );
        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(
                VDBlocks.DARK_STONE_STOVE.get(),
                VDBlocks.SPIRIT_LANTERN.get()
        );
        tag(BlockTags.MINEABLE_WITH_SHOVEL).add(
                VDBlocks.CURSED_FARMLAND.get(),
                VDBlocks.BLOODY_SOIL.get());
    }

    protected void registerMinecraftTags() {
        tag(BlockTags.CROPS).add(
                ModBlocks.GARLIC.get());
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
                ModBlocks.FIRE_PLACE.get()
        );
        tag(ModTags.HEAT_SOURCES).add(
                VDBlocks.DARK_STONE_STOVE.get()
        );
    }

    private void registerCompatibilityTags() {
        tag(CompatibilityTags.CREATE_PASSIVE_BOILER_HEATERS).add(
                VDBlocks.DARK_STONE_STOVE.get());
        tag(CompatibilityTags.SERENE_SEASONS_AUTUMN_CROPS_BLOCK).add(
                ModBlocks.GARLIC.get());
        tag(CompatibilityTags.SERENE_SEASONS_SUMMER_CROPS_BLOCK).add(
                ModBlocks.GARLIC.get());
    }
}
