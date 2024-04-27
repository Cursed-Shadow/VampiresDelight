package net.grid.vampiresdelight.data;

import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.block.BarStoolBlock;
import net.grid.vampiresdelight.common.registry.VDBlocks;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

public class VDBlockModels extends BlockModelProvider {
    public VDBlockModels(PackOutput output,  ExistingFileHelper existingFileHelper) {
        super(output, VampiresDelight.MODID, existingFileHelper);
    }

    private String blockName(Block block) {
        return ForgeRegistries.BLOCKS.getKey(block).getPath();
    }

    @Override
    protected void registerModels() {
        // Wine shelves
        // Note: Cursed and dark spruce shelves can't be generated for some reason.
        wineShelfBlock(VDBlocks.OAK_WINE_SHELF.get(), Blocks.OAK_PLANKS);
        wineShelfBlock(VDBlocks.SPRUCE_WINE_SHELF.get(), Blocks.SPRUCE_PLANKS);
        wineShelfBlock(VDBlocks.BIRCH_WINE_SHELF.get(), Blocks. BIRCH_PLANKS);
        wineShelfBlock(VDBlocks.JUNGLE_WINE_SHELF.get(), Blocks.JUNGLE_PLANKS);
        wineShelfBlock(VDBlocks.ACACIA_WINE_SHELF.get(), Blocks.ACACIA_PLANKS);
        wineShelfBlock(VDBlocks.DARK_OAK_WINE_SHELF.get(), Blocks.DARK_OAK_PLANKS);
        wineShelfBlock(VDBlocks.MANGROVE_WINE_SHELF.get(), Blocks.MANGROVE_PLANKS);
        wineShelfBlock(VDBlocks.CHERRY_WINE_SHELF.get(), Blocks.CHERRY_PLANKS);
        wineShelfBlock(VDBlocks.BAMBOO_WINE_SHELF.get(), Blocks.BAMBOO_PLANKS);
        wineShelfBlock(VDBlocks.CRIMSON_WINE_SHELF.get(), Blocks.CRIMSON_PLANKS);
        wineShelfBlock(VDBlocks.WARPED_WINE_SHELF.get(), Blocks.WARPED_PLANKS);

        // Bar Stools
        BarStoolBlock.getBarStoolBlocks().forEach(this::barStoolBlock);

        // Huge black mushroom blocks
        hugeBlackMushroomBlock(VDBlocks.BLACK_MUSHROOM_BLOCK.get());
        hugeBlackMushroomBlock(VDBlocks.BLACK_MUSHROOM_STEM.get());
        hugeBlackMushroomBlock("black_mushroom_block_inside", false);
    }

    private void wineShelfBlock(Block shelfBlock, Block woodTypee) {
        ResourceLocation shelfTexture = resourceBlock(blockName(shelfBlock));
        ResourceLocation woodTypeTexture = new ResourceLocation("block/" + blockName(woodTypee));;
        String name = blockName(shelfBlock);

        // Shelf body
        withExistingParent(name, "vampiresdelight:block/template_wine_shelf")
                .texture("particle", woodTypeTexture)
                .texture("body", shelfTexture);
        // Shelf support
        withExistingParent(name + "_support", "vampiresdelight:block/template_wine_shelf_support")
                .texture("particle", woodTypeTexture)
                .texture("body", shelfTexture);
    }

    private void barStoolBlock(Block barStool) {
        ResourceLocation seatTexture = resourceBlock(blockName(barStool) + "_seat");
        String name = blockName(barStool);

        withExistingParent(name, "vampiresdelight:block/template_bar_stool")
                .texture("seat", seatTexture);
    }

    private void hugeBlackMushroomBlock(String name, ResourceLocation texture, boolean needsInventoryVersion) {
        getBuilder(name)
                .texture("texture", texture)
                .texture("particle", texture)
                .element().from(0, 0, 0).to(16, 16, 0).face(Direction.NORTH).texture("#texture").cullface(Direction.NORTH);
        if (needsInventoryVersion) cubeAll(name + "_inventory", texture);
    }

    private void hugeBlackMushroomBlock(Block block) {
        hugeBlackMushroomBlock(blockName(block), resourceBlock(blockName(block)), true);
    }

    private void hugeBlackMushroomBlock(String path, boolean needsInventoryVersion) {
        hugeBlackMushroomBlock(path, resourceBlock(path), needsInventoryVersion);
    }

    public ResourceLocation resourceBlock(String path) {
        return new ResourceLocation(VampiresDelight.MODID, "block/" + path);
    }
}
