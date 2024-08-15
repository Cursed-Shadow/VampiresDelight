package net.grid.vampiresdelight.common.block;

import net.grid.vampiresdelight.common.VDConfiguration;
import net.grid.vampiresdelight.common.registry.VDBlocks;
import net.grid.vampiresdelight.common.tag.VDTags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.CommonHooks;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import org.jetbrains.annotations.Nullable;
import vectorwing.farmersdelight.common.registry.ModBlocks;
import vectorwing.farmersdelight.common.utility.MathUtils;

public class BloodySoilBlock extends Block {
    public BloodySoilBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!level.isClientSide) {
            transformPlants(level, pos);
            boostCrop(level, pos);
        }
    }

    public static void transformPlants(ServerLevel level, BlockPos pos) {
        BlockPos abovePos = pos.above();
        BlockState aboveState = level.getBlockState(abovePos);
        Block aboveBlock = aboveState.getBlock();

        // TODO: Add black mushroom transformation as well

        // Convert mushrooms to colonies if it's dark enough
        if (aboveBlock == Blocks.BROWN_MUSHROOM) {
            level.setBlockAndUpdate(pos.above(), ModBlocks.BROWN_MUSHROOM_COLONY.get().defaultBlockState());
        } else if (aboveBlock == Blocks.RED_MUSHROOM) {
            level.setBlockAndUpdate(pos.above(), ModBlocks.RED_MUSHROOM_COLONY.get().defaultBlockState());
        }
    }

    public static void boostCrop(ServerLevel level, BlockPos pos) {
        BlockPos abovePos = pos.above();
        BlockState aboveState = level.getBlockState(abovePos);
        Block aboveBlock = aboveState.getBlock();

        if (canBoostCrop(aboveState)) {
            if (aboveBlock instanceof BonemealableBlock growable && MathUtils.RAND.nextFloat() <= VDConfiguration.BLOODY_SOIL_BOOST_CHANCE.get()) {
                if (growable.isValidBonemealTarget(level, abovePos, aboveState) && CommonHooks.canCropGrow(level, pos.above(), aboveState, true)) {
                    growable.performBonemeal(level, level.random, abovePos, aboveState);
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
}
