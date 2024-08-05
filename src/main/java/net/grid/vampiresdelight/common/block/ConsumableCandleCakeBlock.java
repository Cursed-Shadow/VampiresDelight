package net.grid.vampiresdelight.common.block;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.grid.vampiresdelight.VampiresDelight;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Credits to the Neapolitan and Just More Cakes mods
 */
@SuppressWarnings("deprecation")
public class ConsumableCandleCakeBlock extends AbstractCandleBlock {
    public static final MapCodec<ConsumableCandleCakeBlock> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                    propertiesCodec(),
                    BuiltInRegistries.BLOCK.byNameCodec().fieldOf("cake").forGetter(o -> o.cakeBlock),
                    BuiltInRegistries.BLOCK.byNameCodec().fieldOf("candle").forGetter(o -> o.candleBlock))
            .apply(instance, ConsumableCandleCakeBlock::new));
    public static final BooleanProperty LIT = AbstractCandleBlock.LIT;
    protected static final VoxelShape CAKE_SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 8.0D, 15.0D);
    protected static final VoxelShape CANDLE_SHAPE = Block.box(7.0D, 8.0D, 7.0D, 9.0D, 14.0D, 9.0D);
    protected static final VoxelShape SHAPE = Shapes.or(CAKE_SHAPE, CANDLE_SHAPE);
    private static final Map<Pair<Block, Block>, ConsumableCandleCakeBlock> BY_CANDLE_AND_CAKE = Maps.newHashMap();
    private static final Iterable<Vec3> PARTICLE_OFFSETS = ImmutableList.of(new Vec3(0.5D, 1.0D, 0.5D));

    private final Block cakeBlock;
    private final Block candleBlock;

    @Override
    protected MapCodec<? extends AbstractCandleBlock> codec() {
        return CODEC;
    }

    public ConsumableCandleCakeBlock(BlockBehaviour.Properties properties, Block cakeBlock, Block candleBlock) {
        super(properties);
        this.cakeBlock = cakeBlock;
        this.candleBlock = candleBlock;
        this.registerDefaultState(this.stateDefinition.any().setValue(LIT, Boolean.FALSE));
        BY_CANDLE_AND_CAKE.put(Pair.of(candleBlock, cakeBlock), this);
    }

    @Override
    protected Iterable<Vec3> getParticleOffsets(BlockState pState) {
        return PARTICLE_OFFSETS;
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (!stack.is(Items.FLINT_AND_STEEL) && !stack.is(Items.FIRE_CHARGE)) {
            if (candleHit(hitResult) && stack.isEmpty() && state.getValue(LIT)) {
                extinguish(player, state, level, pos);
                return ItemInteractionResult.sidedSuccess(level.isClientSide);
            } else {
                return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
            }
        } else {
            return ItemInteractionResult.SKIP_DEFAULT_BLOCK_INTERACTION;
        }
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (cakeBlock instanceof ConsumableCakeBlock consumableCakeBlock) {
            InteractionResult interactionresult = consumableCakeBlock.consumeBite(level, pos, cakeBlock.defaultBlockState(), player);
            if (interactionresult.consumesAction()) {
                dropResources(state, level, pos);
            }

            return interactionresult;
        }

        return InteractionResult.FAIL;
    }

    private static boolean candleHit(BlockHitResult pHit) {
        return pHit.getLocation().y - pHit.getBlockPos().getY() > 0.5D;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(LIT);
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, LevelReader level, BlockPos pos, Player player) {
        return new ItemStack(cakeBlock);
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor pLevel, BlockPos pPos, BlockPos pNeighborPos) {
        return pDirection == Direction.DOWN && !pState.canSurvive(pLevel, pPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(pState, pDirection, pNeighborState, pLevel, pPos, pNeighborPos);
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        return pLevel.getBlockState(pPos.below()).isSolid();
    }

    @Override
    public int getAnalogOutputSignal(BlockState pState, Level pLevel, BlockPos pPos) {
        return ConsumableCakeBlock.FULL_CAKE_SIGNAL;
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState pState) {
        return true;
    }

    @Override
    protected boolean isPathfindable(BlockState state, PathComputationType pathComputationType) {
        return false;
    }

    public static BlockState byCandle(Block candleBlock, Block cakeBlock) {
        return BY_CANDLE_AND_CAKE.get(Pair.of(candleBlock, cakeBlock)).defaultBlockState();
    }

    public static boolean hasCandle(Block candleBlock, Block cakeBlock) {
        return BY_CANDLE_AND_CAKE.get(Pair.of(candleBlock, cakeBlock)) != null;
    }

    public Block getCandleBlock() {
        return candleBlock;
    }

    public Block getCakeBlock() {
        return cakeBlock;
    }

    public static Iterable<Block> getAllCandleCakes() {
        return BuiltInRegistries.BLOCK.stream().filter(block -> VampiresDelight.MODID.equals(BuiltInRegistries.BLOCK.getKey(block).getNamespace()) && block instanceof ConsumableCandleCakeBlock).collect(Collectors.toList());
    }
}