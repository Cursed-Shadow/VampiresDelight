package net.grid.vampiresdelight.common.world.feature;

import com.mojang.serialization.Codec;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.HugeMushroomBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.AbstractHugeMushroomFeature;
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class HugeBlackMushroomFeature extends AbstractHugeMushroomFeature {
    public HugeBlackMushroomFeature(Codec<HugeMushroomFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    protected void makeCap(LevelAccessor level, RandomSource random, BlockPos pos, int treeHeight, BlockPos.MutableBlockPos mutablePos, HugeMushroomFeatureConfiguration config) {
        for(int i = treeHeight - 3; i <= treeHeight; ++i) {
            int j = i < treeHeight ? config.foliageRadius : config.foliageRadius - 1;
            int k = config.foliageRadius - 2;

            for(int l = -j; l <= j; ++l) {
                for(int i1 = -j; i1 <= j; ++i1) {
                    boolean flag = l == -j;
                    boolean flag1 = l == j;
                    boolean flag2 = i1 == -j;
                    boolean flag3 = i1 == j;
                    boolean flag4 = flag || flag1;
                    boolean flag5 = flag2 || flag3;

                    if (i >= treeHeight || flag4 != flag5) {
                        mutablePos.setWithOffset(pos, l, i, i1);
                        if (!level.getBlockState(mutablePos).isSolidRender(level, mutablePos)) {
                            BlockState blockstate = config.capProvider.getState(random, pos);
                            if (blockstate.hasProperty(HugeMushroomBlock.WEST) && blockstate.hasProperty(HugeMushroomBlock.EAST) && blockstate.hasProperty(HugeMushroomBlock.NORTH) && blockstate.hasProperty(HugeMushroomBlock.SOUTH) && blockstate.hasProperty(HugeMushroomBlock.UP)) {
                                blockstate = blockstate
                                        .setValue(HugeMushroomBlock.UP, i >= treeHeight - 1)
                                        .setValue(HugeMushroomBlock.WEST, l < -k)
                                        .setValue(HugeMushroomBlock.EAST, l > k)
                                        .setValue(HugeMushroomBlock.NORTH, i1 < -k)
                                        .setValue(HugeMushroomBlock.SOUTH, i1 > k);
                            }

                            this.setBlock(level, mutablePos, blockstate);
                        }
                    }
                }
            }
        }
    }

    @Override
    protected int getTreeRadiusForHeight(int p_65094_, int p_65095_, int pFoliageRadius, int pY) {
        int i = 0;
        if (pY < p_65095_ && pY >= p_65095_ - 3) {
            i = pFoliageRadius;
        } else if (pY == p_65095_) {
            i = pFoliageRadius;
        }

        return i;
    }

    @Override
    protected int getTreeHeight(RandomSource pRandom) {
        return pRandom.nextInt(3) + 4;
    }
}
