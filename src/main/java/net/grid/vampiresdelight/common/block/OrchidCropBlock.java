package net.grid.vampiresdelight.common.block;

import de.teamlapen.vampirism.core.ModBlocks;
import de.teamlapen.vampirism.world.LevelFog;
import net.grid.vampiresdelight.common.registry.VDItems;
import net.grid.vampiresdelight.common.tag.VDTags;
import net.grid.vampiresdelight.common.utility.VDHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.CommonHooks;
import net.neoforged.neoforge.common.util.TriState;
import org.jetbrains.annotations.NotNull;

public class OrchidCropBlock extends CropBlock {
    public static final int MAX_AGE = 2;
    public static final IntegerProperty AGE = BlockStateProperties.AGE_1;
    private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{
            Block.box(5.0D, 0.0D, 5.0D, 11.0D, 6.0D, 11.0D),
            Block.box(5.0D,0.0D, 5.0D, 11.0D, 10.0D, 11.00)};

    public OrchidCropBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected boolean mayPlaceOn(BlockState blockState, BlockGetter block, BlockPos pos) {
        return blockState.is(VDTags.CURSED_FARMLANDS);
    }

    @Override
    protected @NotNull ItemLike getBaseSeedId() {
        return VDItems.ORCHID_SEEDS.get();
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos pos, CollisionContext context) {
        return SHAPE_BY_AGE[this.getAge(blockState)];
    }

    // TODO: Fix Orchid Crop and Bloody Soil boost

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        TriState soilDecision = level.getBlockState(pos.below()).canSustainPlant(level, pos.below(), Direction.UP, state);
        //if (!isDarkOrFoggyEnough(level, pos)) {
            //return false;
        //} else
        if (!soilDecision.isDefault()) {
            return soilDecision.isTrue();
        } else {
            return super.canSurvive(state, level, pos);
        }
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (level.isAreaLoaded(pos, 1)) {
            if (isDarkOrFoggyEnough(level, pos)) {
                int age = this.getAge(state);
                if (age < this.getMaxAge()) {
                    float growthSpeed = getGrowthSpeed(state, level, pos);
                    if (CommonHooks.canCropGrow(level, pos, state, random.nextInt((int) (25.0F / growthSpeed) + 1) == 0)) {
                        level.setBlock(pos, this.getStateForAge(age + 1), 2);
                        CommonHooks.fireCropGrowPost(level, pos, state);
                    }
                }
            }
        }
    }

    public static boolean isDarkOrFoggyEnough(LevelReader levelReader, BlockPos pos) {
        return levelReader.getRawBrightness(pos, 0) < 13 || levelReader instanceof Level level && LevelFog.get(level).isInsideArtificialVampireFogArea(pos) || levelReader instanceof LevelAccessor levelAccessor && VDHelper.isPosInVampireBiome(pos, levelAccessor);
    }

    public @NotNull BlockState getStateForAge(int age) {
        return age == 2 ? ModBlocks.VAMPIRE_ORCHID.get().defaultBlockState() : super.getStateForAge(age);
    }

    @Override
    public int getMaxAge() {
        return MAX_AGE;
    }

    @Override
    public @NotNull IntegerProperty getAgeProperty() {
        return AGE;
    }

    @Override
    protected int getBonemealAgeIncrease(Level worldIn) {
        return 1;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateDefinition) {
        stateDefinition.add(AGE);
    }
}
