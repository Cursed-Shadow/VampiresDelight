package net.grid.vampiresdelight.common.block;

import net.grid.vampiresdelight.common.registry.VDBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class BloodySoilFarmlandBlock extends CursedFarmlandBlock {
    public BloodySoilFarmlandBlock(Properties properties) {
        super(properties);
    }

    public static void turnToBloodySoil(BlockState state, Level level, BlockPos pos) {
        level.setBlockAndUpdate(pos, pushEntitiesUp(state, VDBlocks.BLOODY_SOIL.get().defaultBlockState(), level, pos));
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockState aboveState = level.getBlockState(pos.above());
        return super.canSurvive(state, level, pos) || aboveState.getBlock().equals(Blocks.MELON) || aboveState.getBlock().equals(Blocks.PUMPKIN);
    }

    @Override
    public boolean isFertile(BlockState state, BlockGetter world, BlockPos pos) {
        if (state.is(VDBlocks.BLOODY_SOIL_FARMLAND.get()))
            return state.getValue(CursedFarmlandBlock.MOISTURE) > 0;

        return false;
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource rand) {
        if (!state.canSurvive(level, pos)) {
            turnToBloodySoil(state, level, pos);
        }
    }

    @Override
    public void randomTick(BlockState blockState, ServerLevel worldIn, BlockPos pos, RandomSource randomSource) {
        int moisture = blockState.getValue(MOISTURE);
        if (!isNearWater(worldIn, pos) && !worldIn.isRainingAt(pos.above())) {
            if (moisture > 0) {
                worldIn.setBlock(pos, blockState.setValue(MOISTURE, moisture - 1), 2);
            }
        } else if (moisture < 7) {
            worldIn.setBlock(pos, blockState.setValue(MOISTURE, 7), 2);
        } else if (moisture == 7) {
            BloodySoilBlock.boostCrop(blockState, worldIn, pos);
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return !this.defaultBlockState().canSurvive(context.getLevel(), context.getClickedPos()) ? VDBlocks.BLOODY_SOIL.get().defaultBlockState() : super.getStateForPlacement(context);
    }

    @Override
    public void fallOn(Level level, BlockState state, BlockPos pos, Entity entityIn, float fallDistance) {
        // Bloody Soil is immune to trampling
    }
}
