package net.grid.vampiresdelight.common.world.feature;

import com.mojang.serialization.Codec;
import net.grid.vampiresdelight.common.registry.VDBlocks;
import net.grid.vampiresdelight.common.tag.VDTags;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.HugeMushroomBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.BiPredicate;

/**
 * Credit: Biomes O' Plenty
 * <a href="https://github.com/Glitchfiend/BiomesOPlenty">...</a>
 */
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class HugeBlackMushroomFeature extends Feature<NoneFeatureConfiguration> {
    protected BiPredicate<WorldGenLevel, BlockPos> replace = ((worldGenLevel, blockPos) -> TreeFeature.isAirOrLeaves(worldGenLevel, blockPos) || worldGenLevel.getBlockState(blockPos).getBlock() instanceof BushBlock);
    protected BiPredicate<WorldGenLevel, BlockPos> placeOn = ((worldGenLevel, blockPos) -> worldGenLevel.getBlockState(blockPos).is(VDTags.BLACK_MUSHROOM_GROW_BLOCK));

    public HugeBlackMushroomFeature(Codec<NoneFeatureConfiguration> pCodec) {
        super(pCodec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> pContext) {
        WorldGenLevel worldLevel = pContext.level();
        RandomSource randomSource = pContext.random();
        BlockPos originPos = pContext.origin();

        BlockState mushroomStem = VDBlocks.BLACK_MUSHROOM_STEM.get().defaultBlockState();
        BlockState mushroomCap = VDBlocks.BLACK_MUSHROOM_BLOCK.get().defaultBlockState().setValue(HugeMushroomBlock.DOWN, false);

        int stemHeight = 2 + randomSource.nextInt(3);
        int lowestPartHeight = randomSource.nextInt(2);
        int middlePartHeight = 1 + randomSource.nextInt(2);
        int topPartHeight = 2 + randomSource.nextInt(2);

        while (originPos.getY() >= worldLevel.getMinBuildHeight() + 1 && this.replace.test(worldLevel, originPos))
            originPos = originPos.below();

        // Abandoned if the mushroom can't be placed on this block or there isn't enough space
        if (!(this.placeOn.test(worldLevel, originPos.offset(0, 0, 0)) ||
                this.checkSpace(worldLevel, originPos.above())))
            return false;

        BlockPos pos = originPos.above();

        for (int height = 0; height < stemHeight; height++)
            this.setBlock(worldLevel, pos.above(height), mushroomStem);

        BlockPos highestPos = pos.offset(0, stemHeight, 0);

        // Makes curvy cap part
        // South
        this.setBlock(worldLevel, highestPos.offset(-1, -1, 2), mushroomCap.setValue(HugeMushroomBlock.NORTH, false));
        this.setBlock(worldLevel, highestPos.offset(0, -1, 2), mushroomCap.setValue(HugeMushroomBlock.NORTH, false));
        this.setBlock(worldLevel, highestPos.offset(1, -1, 2), mushroomCap.setValue(HugeMushroomBlock.NORTH, false));
        this.setBlock(worldLevel, highestPos.offset(0, 0, 2), mushroomCap.setValue(HugeMushroomBlock.NORTH, false));
        // Easy
        this.setBlock(worldLevel, highestPos.offset(2, -1, -1), mushroomCap.setValue(HugeMushroomBlock.WEST, false));
        this.setBlock(worldLevel, highestPos.offset(2, -1, 0), mushroomCap.setValue(HugeMushroomBlock.WEST, false));
        this.setBlock(worldLevel, highestPos.offset(2, -1, 1), mushroomCap.setValue(HugeMushroomBlock.WEST, false));
        this.setBlock(worldLevel, highestPos.offset(2, 0, 0), mushroomCap.setValue(HugeMushroomBlock.WEST, false));
        // North
        this.setBlock(worldLevel, highestPos.offset(-1, -1, -2), mushroomCap.setValue(HugeMushroomBlock.SOUTH, false));
        this.setBlock(worldLevel, highestPos.offset(0, -1, -2), mushroomCap.setValue(HugeMushroomBlock.SOUTH, false));
        this.setBlock(worldLevel, highestPos.offset(1, -1, -2), mushroomCap.setValue(HugeMushroomBlock.SOUTH, false));
        this.setBlock(worldLevel, highestPos.offset(0, 0, -2), mushroomCap.setValue(HugeMushroomBlock.SOUTH, false));
        // West
        this.setBlock(worldLevel, highestPos.offset(-2, -1, -1), mushroomCap.setValue(HugeMushroomBlock.EAST, false));
        this.setBlock(worldLevel, highestPos.offset(-2, -1, 0), mushroomCap.setValue(HugeMushroomBlock.EAST, false));
        this.setBlock(worldLevel, highestPos.offset(-2, -1, 1), mushroomCap.setValue(HugeMushroomBlock.EAST, false));
        this.setBlock(worldLevel, highestPos.offset(-2, 0, 0), mushroomCap.setValue(HugeMushroomBlock.EAST, false));

        // Makes the lowest cap part
        for (int x = -1; x <= 1; x++)
            for (int z = -1; z <= 1; z++)
                for (int height = 0; height <= lowestPartHeight; height++)
                    this.setBlock(worldLevel, highestPos.offset(x, height, z), mushroomCap);
        highestPos = highestPos.above(lowestPartHeight);

        // Makes the middle cap part
        for (int height = 0; height <= middlePartHeight; height++) {
            this.setBlock(worldLevel, highestPos.offset(0, height + 1, 0), mushroomCap);
            this.setBlock(worldLevel, highestPos.offset(1, height + 1, 0), mushroomCap);
            this.setBlock(worldLevel, highestPos.offset(-1, height + 1, 0), mushroomCap);
            this.setBlock(worldLevel, highestPos.offset(0, height + 1, 1), mushroomCap);
            this.setBlock(worldLevel, highestPos.offset(0, height + 1, -1), mushroomCap);
        }
        highestPos = highestPos.above(middlePartHeight);

        // Makes the top cap part
        for (int height = 1; height <= topPartHeight; height++)
            this.setBlock(worldLevel, highestPos.above(height), mushroomCap);

        return true;
    }

    public void setBlock(WorldGenLevel world, BlockPos pos, BlockState state) {
        if (this.replace.test(world, pos))
            super.setBlock(world, pos, state);
    }

    public boolean checkSpace(WorldGenLevel world, BlockPos pos) {
        for (int y = 0; y <= 13; y++)
            for (int x = -2; x <= 2; x++)
                for (int z = -2; z <= 2; z++)
                    if (pos.offset(x, y, z).getY() >= 255 || !this.replace.test(world, pos.offset(x, y, z)))
                        return false;
        return true;
    }
}
