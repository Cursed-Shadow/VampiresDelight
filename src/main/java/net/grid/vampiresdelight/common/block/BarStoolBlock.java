package net.grid.vampiresdelight.common.block;

import de.teamlapen.vampirism.sit.SitEntity;
import de.teamlapen.vampirism.sit.SitUtil;
import net.grid.vampiresdelight.VampiresDelight;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.stream.Collectors;

@SuppressWarnings("deprecation")
public class BarStoolBlock extends Block implements SimpleWaterloggedBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public BarStoolBlock() {
        super(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD).ignitedByLava());

        BlockState blockstate = this.stateDefinition.any()
                .setValue(WATERLOGGED, false);

        this.registerDefaultState(blockstate);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        BlockState blockAbove = pLevel.getBlockState(pPos.above());
        if (isClickedOnSeat(pPos, pHit) && (blockAbove.isAir() || blockAbove.is(BlockTags.BUTTONS) || blockAbove.is(BlockTags.TRAPDOORS) || blockAbove.is(Blocks.END_ROD))) {
            startSitting(pPlayer, pLevel, pPos, 0.75);
            return InteractionResult.SUCCESS;
        }

        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    private static boolean isClickedOnSeat(BlockPos pPos, BlockHitResult pHit) {
        return pHit.getLocation().y() >= pPos.getY() + 0.8125;
    }

    // Modified Vampirism's SitHandler
    public static void startSitting(@NotNull Player pPlayer, @NotNull Level pLevel, @NotNull BlockPos pPos, double offset) {
        if (!pLevel.isClientSide && !SitUtil.isPlayerSitting(pPlayer) && !pPlayer.isShiftKeyDown()) {
            if (isPlayerInRange(pPlayer, pPos) && !SitUtil.isOccupied(pLevel, pPos))
            {
                SitEntity sit = SitEntity.newEntity(pLevel, pPos, offset, pPlayer.position());

                if (SitUtil.addSitEntity(pLevel, pPos, sit)) {
                    pLevel.addFreshEntity(sit);
                    pPlayer.startRiding(sit);
                }
            }
        }
    }

    private static boolean isPlayerInRange(@NotNull Player pPlayer, BlockPos pPos) {
        Vec3 playerPos = pPlayer.position();
        Vec3 blockPos = new Vec3(pPos.getX(), pPos.getY(), pPos.getZ());
        AttributeInstance blockReach = pPlayer.getAttribute(ForgeMod.BLOCK_REACH.get());
        double blockReachDistance = (blockReach == null) ? 4.5D : blockReach.getValue();

        blockPos = blockPos.add(0.5D, 0.5D, 0.5D);

        AABB range = new AABB(blockPos.x() + blockReachDistance, blockPos.y() + blockReachDistance, blockPos.z() + blockReachDistance, blockPos.x() - blockReachDistance, blockPos.y() - blockReachDistance, blockPos.z() - blockReachDistance);

        playerPos = playerPos.add(0.5D, 0.5D, 0.5D);
        return range.minX <= playerPos.x() && range.minY <= playerPos.y() && range.minZ <= playerPos.z() && range.maxX >= playerPos.x() && range.maxY >= playerPos.y() && range.maxZ >= playerPos.z();
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pMovedByPiston) {
        super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
        SitEntity entity = SitUtil.getSitEntity(pLevel, pPos);
        if (entity != null) {
            entity.discard();
        }
    }

    @Override
    public void updateEntityAfterFallOn(BlockGetter pLevel, Entity pEntity) {
        if (pEntity.isSuppressingBounce()) {
            super.updateEntityAfterFallOn(pLevel, pEntity);
        } else {
            Vec3 vec3 = pEntity.getDeltaMovement();
            if (vec3.y < 0.0D) {
                double d0 = pEntity instanceof LivingEntity ? 1.0D : 0.8D;
                pEntity.setDeltaMovement(vec3.x, -vec3.y * (double)0.66F * d0, vec3.z);
            }
        }

    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState pState) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState pState, Level pLevel, BlockPos pPos) {
        return SitUtil.isOccupied(pLevel, pPos) ? 15 : 0;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(WATERLOGGED, pContext.getLevel().getFluidState(pContext.getClickedPos()).getType() == Fluids.WATER);
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
        pBuilder.add(WATERLOGGED);
    }

    @Override
    public FluidState getFluidState(BlockState pState) {
        return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
    }

    @Override
    public boolean isPathfindable(BlockState pState, BlockGetter pLevel, BlockPos pPos, PathComputationType pType) {
        return false;
    }

    @Override
    public @Nullable BlockPathTypes getBlockPathType(BlockState state, BlockGetter level, BlockPos pos, @Nullable Mob mob) {
        return BlockPathTypes.RAIL;
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return Shapes.join(Block.box(4, 0, 4, 12, 13, 12), Block.box(3, 13, 3, 13, 16, 13), BooleanOp.OR);
    }

    public static Iterable<Block> getBarStoolBlocks() {
        return ForgeRegistries.BLOCKS.getValues().stream().filter(block -> ForgeRegistries.BLOCKS.getKey(block) != null && VampiresDelight.MODID.equals(Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block)).getNamespace()) && block instanceof BarStoolBlock).collect(Collectors.toList());
    }
}
