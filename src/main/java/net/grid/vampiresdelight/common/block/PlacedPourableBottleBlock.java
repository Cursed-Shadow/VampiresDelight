package net.grid.vampiresdelight.common.block;

import net.grid.vampiresdelight.common.item.PourableBottleItem;
import net.grid.vampiresdelight.common.registry.VDSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import vectorwing.farmersdelight.common.utility.MathUtils;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

@SuppressWarnings("deprecation")
public class PlacedPourableBottleBlock extends Block implements SimpleWaterloggedBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final IntegerProperty SERVINGS = IntegerProperty.create("servings", 0, 64);
    public static final VoxelShape BLOOD_WINE_SHAPE = Stream.of(Block.box(5.5, 0, 5.5, 10.5, 10, 10.5), Block.box(6.5, 14, 6.5, 9.5, 15, 9.5), Block.box(7, 10, 7, 9, 16, 9)).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    private final Supplier<PourableBottleItem> bottleItem;
    private final VoxelShape shape;

    public PlacedPourableBottleBlock(Properties properties, Supplier<PourableBottleItem> bottleItem, VoxelShape shape) {
        super(properties.instabreak().noOcclusion().pushReaction(PushReaction.DESTROY));
        this.bottleItem = bottleItem;
        this.shape = shape;

        this.registerDefaultState(this.stateDefinition.any()
                .setValue(WATERLOGGED, false)
                .setValue(SERVINGS, 0));
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);

        if (pStack.getItem() instanceof PourableBottleItem) {
            pLevel.setBlock(pPos, pState.setValue(SERVINGS, pStack.getDamageValue()), 2);
        }
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide && pPlayer.isCrouching()){
            ItemStack itemStack = getBottleStack(pState);
            if (pPlayer.getItemInHand(pHand).isEmpty()) {
                pPlayer.setItemInHand(InteractionHand.MAIN_HAND, itemStack);
            } else {
                popResource(pLevel, pPos, itemStack);
            }
            pLevel.removeBlock(pPos, false);

            return InteractionResult.SUCCESS;
        }

        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    @Override
    public List<ItemStack> getDrops(BlockState pState, LootParams.Builder pParams) {
        return List.of(getBottleStack(pState));
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter level, BlockPos pos, Player player) {
        return getBottleStack(state);
    }

    public ItemStack getBottleStack(BlockState state) {
        ItemStack itemStack = new ItemStack(bottleItem.get());
        itemStack.setDamageValue(state.getValue(SERVINGS));
        return itemStack;
    }

    @Override
    public void onProjectileHit(Level pLevel, BlockState pState, BlockHitResult pHit, Projectile pProjectile) {
        BlockPos blockPos = pHit.getBlockPos();
        if (!pLevel.isClientSide && pProjectile.mayInteract(pLevel, blockPos)) {
            pLevel.playSound(null, blockPos, VDSounds.BOTTLE_BREAKS.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
            pLevel.destroyBlock(blockPos, true);
        }
    }

    @Override
    public @Nullable PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.DESTROY;
    }

    @Override
    public boolean isPathfindable(BlockState pState, BlockGetter pLevel, BlockPos pPos, PathComputationType pType) {
        return false;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        FluidState fluidState = pContext.getLevel().getFluidState(pContext.getClickedPos());
        return this.defaultBlockState().setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        if (pState.getValue(WATERLOGGED)) {
            pLevel.scheduleTick(pCurrentPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
        }

        return super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(WATERLOGGED, SERVINGS);
    }

    @Override
    public FluidState getFluidState(BlockState pState) {
        return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return shape;
    }
}
