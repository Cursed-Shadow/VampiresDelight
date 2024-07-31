package net.grid.vampiresdelight.common.block;

import net.grid.vampiresdelight.common.VDConfiguration;
import net.grid.vampiresdelight.common.registry.VDBlocks;
import net.grid.vampiresdelight.common.tag.VDTags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.TallFlowerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.CommonHooks;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import org.jetbrains.annotations.Nullable;
import vectorwing.farmersdelight.common.tag.ModTags;
import vectorwing.farmersdelight.common.utility.MathUtils;

public class BloodySoilBlock extends Block {
    public BloodySoilBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!level.isClientSide) {
            // TODO: Add black mushroom transformation

            boostCrop(state, level, pos);
        }
    }

    public static void boostCrop(BlockState state, ServerLevel level, BlockPos pos) {
        BlockPos abovePos = pos.above();
        BlockState aboveState = level.getBlockState(abovePos);

        if (canBoostCrop(state, level, pos)) {
            if (aboveState.getBlock() instanceof BonemealableBlock growable && MathUtils.RAND.nextFloat() <= VDConfiguration.BLOODY_SOIL_BOOST_CHANCE.get()) {
                if (growable.isValidBonemealTarget(level, abovePos, aboveState) && CommonHooks.canCropGrow(level, abovePos, aboveState, true)) {
                    growable.performBonemeal(level, level.random, abovePos, aboveState);
                    if (!level.isClientSide) {
                        level.levelEvent(2005, pos.above(), 0);
                    }
                    CommonHooks.fireCropGrowPost(level, abovePos, aboveState);
                }
            }
        }
    }

    public static boolean canBoostCrop(BlockState state, ServerLevel level, BlockPos pos) {
        BlockPos abovePos = pos.above();
        BlockState aboveState = level.getBlockState(abovePos);
        Block aboveBlock = aboveState.getBlock();

        return state.is(VDTags.CURSED_PLANTS) && VDConfiguration.BLOODY_SOIL_BOOST_CHANCE.get() != 0.0 && !(aboveState.is(ModTags.UNAFFECTED_BY_RICH_SOIL) || aboveBlock instanceof TallFlowerBlock);
    }

    @Override
    public @Nullable BlockState getToolModifiedState(BlockState state, UseOnContext context, ItemAbility itemAbility, boolean simulate) {
        if (itemAbility.equals(ItemAbilities.HOE_TILL) && context.getLevel().getBlockState(context.getClickedPos().above()).isAir()) {
            return VDBlocks.BLOODY_SOIL_FARMLAND.get().defaultBlockState();
        }
        return null;
    }

    /*
    @Override
    public boolean canSustainPlant(BlockState state, BlockGetter world, BlockPos pos, Direction facing, IPlantable iPlantable) {
        PlantType plantType = iPlantable.getPlantType(world, pos);
        return plantType != PlantType.CROP && plantType != PlantType.NETHER && plantType != PlantType.WATER;
    }
     */
}
