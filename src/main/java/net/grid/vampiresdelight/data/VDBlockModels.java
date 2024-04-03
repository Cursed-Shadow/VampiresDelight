package net.grid.vampiresdelight.data;

import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.registry.VDBlocks;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
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
        // Huge black mushroom blocks
        hugeBlackMushroomBlock(VDBlocks.BLACK_MUSHROOM_BLOCK.get());
        hugeBlackMushroomBlock(VDBlocks.BLACK_MUSHROOM_STEM.get());
        hugeBlackMushroomBlock("black_mushroom_block_inside", false);
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
