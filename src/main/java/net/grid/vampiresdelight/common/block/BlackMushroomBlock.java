package net.grid.vampiresdelight.common.block;

import net.grid.vampiresdelight.common.tag.VDTags;
import net.grid.vampiresdelight.common.world.VDConfiguredFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.MushroomBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.util.TriState;

public class BlackMushroomBlock extends MushroomBlock {
    protected static final VoxelShape SHAPE = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 8.0D, 11.0D);

    public BlackMushroomBlock(Properties properties) {
        super(VDConfiguredFeatures.HUGE_BLACK_MUSHROOM, properties);
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos blockpos = pos.below();
        BlockState blockstate = level.getBlockState(blockpos);
        TriState soilDecision = blockstate.canSustainPlant(level, blockpos, Direction.UP, state);
        return blockstate.is(VDTags.BLACK_MUSHROOM_GROW_BLOCK) || (soilDecision.isDefault() ? level.getRawBrightness(pos, 0) < 13 && this.mayPlaceOn(blockstate, level, blockpos) : soilDecision.isTrue());
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }
}
