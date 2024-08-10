package net.grid.vampiresdelight.common.block;

import net.grid.vampiresdelight.common.VDConfiguration;
import net.grid.vampiresdelight.common.registry.VDBlocks;
import net.grid.vampiresdelight.common.tag.VDTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.TallFlowerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.CommonHooks;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import net.neoforged.neoforge.common.util.TriState;
import org.jetbrains.annotations.Nullable;
import vectorwing.farmersdelight.common.registry.ModBlocks;
import vectorwing.farmersdelight.common.tag.ModTags;
import vectorwing.farmersdelight.common.utility.MathUtils;

public class BloodySoilBlock extends Block {
    public BloodySoilBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!level.isClientSide) {
            if (!transformPlants(level, pos)) {
                boostCrop(state, level, pos);
            }
        }
    }

    public static boolean transformPlants(ServerLevel level, BlockPos pos) {
        BlockPos abovePos = pos.above();
        BlockState aboveState = level.getBlockState(abovePos);
        Block aboveBlock = aboveState.getBlock();

        // TODO: Add black mushroom transformation as well

        // Convert mushrooms to colonies if it's dark enough
        if (aboveBlock == Blocks.BROWN_MUSHROOM) {
            level.setBlockAndUpdate(pos.above(), ModBlocks.BROWN_MUSHROOM_COLONY.get().defaultBlockState());
            return true;
        } else if (aboveBlock == Blocks.RED_MUSHROOM) {
            level.setBlockAndUpdate(pos.above(), ModBlocks.RED_MUSHROOM_COLONY.get().defaultBlockState());
            return true;
        }

        // "false" if transformed and not boosted
        return false;
    }

    public static void boostCrop(BlockState state, ServerLevel level, BlockPos pos) {
        BlockPos abovePos = pos.above();
        BlockState aboveState = level.getBlockState(abovePos);

        if (canBoostCrop(state)) {
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

    public static boolean canBoostCrop(BlockState state) {
        return state.is(VDTags.CURSED_PLANTS) && VDConfiguration.BLOODY_SOIL_BOOST_CHANCE.get() != 0.0;
    }

    @Override
    public @Nullable BlockState getToolModifiedState(BlockState state, UseOnContext context, ItemAbility itemAbility, boolean simulate) {
        if (itemAbility.equals(ItemAbilities.HOE_TILL) && context.getLevel().getBlockState(context.getClickedPos().above()).isAir()) {
            return VDBlocks.BLOODY_SOIL_FARMLAND.get().defaultBlockState();
        }
        return null;
    }

    @Override
    public TriState canSustainPlant(BlockState state, BlockGetter level, BlockPos soilPosition, Direction facing, BlockState plant) {
        return TriState.DEFAULT;
    }
}
