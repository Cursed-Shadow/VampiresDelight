package net.grid.vampiresdelight.common.block;

import de.teamlapen.vampirism.api.VReference;
import net.grid.vampiresdelight.common.tag.VDTags;
import net.grid.vampiresdelight.common.utility.VDHelper;
import net.grid.vampiresdelight.common.world.VDConfiguredFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.MushroomBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.PlantType;

public class BlackMushroomBlock extends MushroomBlock {
    protected static final VoxelShape SHAPE = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 8.0D, 11.0D);

    public BlackMushroomBlock(Properties properties) {
        super(properties, VDConfiguredFeatures.HUGE_BLACK_MUSHROOM);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader levelReader, BlockPos pos) {
        BlockPos blockPos = pos.below();
        BlockState blockState = levelReader.getBlockState(blockPos);

        if (blockState.is(VDTags.BLACK_MUSHROOM_GROW_BLOCK)) {
            return true;
        } else {
            return (levelReader.getRawBrightness(pos, 0) < 13
                    || VDHelper.isPosInVampireBiome(pos, (LevelAccessor) levelReader))
                    && blockState.canSustainPlant(levelReader, blockPos, net.minecraft.core.Direction.UP, this);
        }
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public PlantType getPlantType(BlockGetter level, BlockPos pos) {
        return VReference.VAMPIRE_PLANT_TYPE;
    }
}
