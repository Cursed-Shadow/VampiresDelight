package net.grid.vampiresdelight.common.block;

import de.teamlapen.vampirism.api.blocks.HolyWaterEffectConsumer;
import de.teamlapen.vampirism.api.items.IItemWithTier;
import de.teamlapen.vampirism.core.ModBlocks;
import de.teamlapen.vampirism.core.ModItems;
import de.teamlapen.vampirism.items.HolyWaterBottleItem;
import de.teamlapen.vampirism.items.HolyWaterSplashBottleItem;
import net.grid.vampiresdelight.common.registry.VDBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.common.CommonHooks;
import net.neoforged.neoforge.common.FarmlandWaterManager;
import net.neoforged.neoforge.common.util.TriState;
import org.jetbrains.annotations.NotNull;
import vectorwing.farmersdelight.common.block.BuddingTomatoBlock;

import java.util.Iterator;

public class CursedFarmlandBlock extends FarmBlock implements HolyWaterEffectConsumer {
    public CursedFarmlandBlock(Properties properties) {
        super(properties);
    }

    public static void turnToCursedEarth(BlockState blockState, Level worldIn, BlockPos pos) {
        worldIn.setBlockAndUpdate(pos, pushEntitiesUp(blockState, de.teamlapen.vampirism.core.ModBlocks.CURSED_EARTH.get().defaultBlockState(), worldIn, pos));
    }

    @Override
    public boolean isFertile(BlockState state, BlockGetter world, BlockPos pos) {
        if (state.is(VDBlocks.CURSED_FARMLAND.get()))
            return state.getValue(CursedFarmlandBlock.MOISTURE) > 0;

        return false;
    }

    @Override
    public void randomTick(BlockState blockState, ServerLevel worldIn, BlockPos pos, RandomSource randomSource) {
        int i = blockState.getValue(MOISTURE);
        if (!isNearWater(worldIn, pos) && !worldIn.isRainingAt(pos.above())) {
            if (i > 0) {
                worldIn.setBlock(pos, blockState.setValue(MOISTURE, i - 1), 2);
            } else if (!shouldMaintainFarmland(worldIn, pos)) {
                turnToCursedEarth(blockState, worldIn, pos);
            }
        } else if (i < 7) {
            worldIn.setBlock(pos, blockState.setValue(MOISTURE, 7), 2);
        }
    }

    public static boolean shouldMaintainFarmland(BlockGetter level, BlockPos pos) {
        return level.getBlockState(pos.above()).is(BlockTags.MAINTAINS_FARMLAND);
    }

    public static boolean isNearWater(LevelReader level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        Iterator<BlockPos> var3 = BlockPos.betweenClosed(pos.offset(-4, 0, -4), pos.offset(4, 1, 4)).iterator();

        BlockPos blockpos;
        do {
            if (!var3.hasNext()) {
                return FarmlandWaterManager.hasBlockWaterTicket(level, pos);
            }

            blockpos = var3.next();
        } while(!state.canBeHydrated(level, pos, level.getFluidState(blockpos), blockpos));

        return true;
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource rand) {
        if (!state.canSurvive(level, pos)) {
            turnToCursedEarth(state, level, pos);
        }
    }

    @Override
    public ItemInteractionResult useItemOn(ItemStack stack, @NotNull BlockState state, @NotNull Level worldIn, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand handIn, @NotNull BlockHitResult hit) {
        Item heldItem = stack.getItem();
        if (heldItem instanceof HolyWaterBottleItem&& !(heldItem instanceof HolyWaterSplashBottleItem)) {
            int uses = heldItem == ModItems.HOLY_WATER_BOTTLE_ULTIMATE.get() ? 100 : (heldItem == ModItems.HOLY_WATER_BOTTLE_ENHANCED.get() ? 50 : 25);
            if (!player.getAbilities().instabuild && player.getRandom().nextInt(uses) == 0) {
                stack.setCount(stack.getCount() - 1);
            }
            worldIn.setBlockAndUpdate(pos, Blocks.FARMLAND.defaultBlockState());
            return ItemInteractionResult.SUCCESS;
        }
        return super.useItemOn(stack, state, worldIn, pos, player, handIn, hit);
    }

    @Override
    public void onHolyWaterEffect(Level level, BlockState state, BlockPos pos, ItemStack holyWaterStack, IItemWithTier.TIER tier) {
        level.setBlockAndUpdate(pos, Blocks.GRASS_BLOCK.defaultBlockState());
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return !this.defaultBlockState().canSurvive(context.getLevel(), context.getClickedPos()) ? ModBlocks.CURSED_EARTH.get().defaultBlockState() : super.getStateForPlacement(context);
    }

    @Override
    public void fallOn(Level worldIn, @NotNull BlockState blockState, @NotNull BlockPos pos, @NotNull Entity entity, float damage) {
        if (!worldIn.isClientSide && CommonHooks.onFarmlandTrample(worldIn, pos, ModBlocks.CURSED_EARTH.get().defaultBlockState(), damage, entity)) {
            turnToCursedEarth(blockState, worldIn, pos);
        }
        //Super turns block to default dirt, so it mustn't be called here
        entity.causeFallDamage(damage, 1.0F, entity.damageSources().fall());
    }

    @Override
    public TriState canSustainPlant(BlockState state, BlockGetter level, BlockPos soilPosition, Direction facing, BlockState plant) {
        // Garlic must not be able to be planted, while tomato only supports vanilla Farmland and Rich Soil Farmland block, so we need to override it here
        return plant.is(ModBlocks.GARLIC.get()) ? TriState.FALSE : plant.getBlock() instanceof BuddingTomatoBlock ? TriState.TRUE : super.canSustainPlant(state, level, soilPosition, facing, plant);
    }
}
