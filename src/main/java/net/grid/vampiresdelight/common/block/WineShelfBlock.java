package net.grid.vampiresdelight.common.block;

import de.teamlapen.lib.lib.util.UtilLib;
import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.block.entity.WineShelfBlockEntity;
import net.grid.vampiresdelight.common.tag.VDTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.stats.Stats;
import net.minecraft.tags.TagKey;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@SuppressWarnings("deprecation")
public class WineShelfBlock extends BaseEntityBlock {
    public static final BooleanProperty HAS_UPPER_SUPPORT = BooleanProperty.create("has_upper_support");
    public static final EnumProperty<Slot> WINE_SHELF_SLOT_0 = EnumProperty.create("slot_0", Slot.class);
    public static final EnumProperty<Slot> WINE_SHELF_SLOT_1 = EnumProperty.create("slot_1", Slot.class);
    public static final EnumProperty<Slot> WINE_SHELF_SLOT_2 = EnumProperty.create("slot_2", Slot.class);
    public static final EnumProperty<Slot> WINE_SHELF_SLOT_3 = EnumProperty.create("slot_3", Slot.class);
    public static final List<EnumProperty<Slot>> SLOT_CONTENTS = List.of(WINE_SHELF_SLOT_0, WINE_SHELF_SLOT_1, WINE_SHELF_SLOT_2, WINE_SHELF_SLOT_3);

    @Override
    public @NotNull VoxelShape getShape(BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        VoxelShape shape;

        switch (state.getValue(HorizontalDirectionalBlock.FACING)) {
            case EAST -> shape = UtilLib.rotateShape(makeShape(state.getValue(HAS_UPPER_SUPPORT)), UtilLib.RotationAmount.NINETY);
            case SOUTH -> shape = UtilLib.rotateShape(makeShape(state.getValue(HAS_UPPER_SUPPORT)), UtilLib.RotationAmount.HUNDRED_EIGHTY);
            case WEST -> shape = UtilLib.rotateShape(makeShape(state.getValue(HAS_UPPER_SUPPORT)), UtilLib.RotationAmount.TWO_HUNDRED_SEVENTY);
            default -> shape = makeShape(state.getValue(HAS_UPPER_SUPPORT));
        }

        return shape;
    }

    public WineShelfBlock(Properties properties) {
        super(properties.strength(1.5F));

        BlockState blockstate = this.stateDefinition.any()
                .setValue(HorizontalDirectionalBlock.FACING, Direction.NORTH)
                .setValue(HAS_UPPER_SUPPORT, false);

        for (EnumProperty<Slot> enumProperty : SLOT_CONTENTS) {
            blockstate = blockstate.setValue(enumProperty, Slot.EMPTY);
        }

        this.registerDefaultState(blockstate);
    }

    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState state) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockGetter world = context.getLevel();
        BlockPos posAbove = context.getClickedPos().above();
        return this.defaultBlockState()
                .setValue(HorizontalDirectionalBlock.FACING, context.getHorizontalDirection().getOpposite())
                .setValue(HAS_UPPER_SUPPORT, world.getBlockState(posAbove).getBlock() instanceof WineShelfBlock);
    }

    @Override
    public @NotNull BlockState updateShape(BlockState state, @NotNull Direction facing, @NotNull BlockState facingState, @NotNull LevelAccessor level, @NotNull BlockPos blockPos, @NotNull BlockPos facingPos) {
        return super.updateShape(state.setValue(HAS_UPPER_SUPPORT, level.getBlockState(blockPos.above()).getBlock() instanceof WineShelfBlock), facing, facingState, level, blockPos, facingPos);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(HorizontalDirectionalBlock.FACING, HAS_UPPER_SUPPORT);
        SLOT_CONTENTS.forEach(builder::add);
    }

    @Override
    public @NotNull InteractionResult use(@NotNull BlockState state, Level pLevel, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hitResult) {
        BlockEntity blockEntity = pLevel.getBlockEntity(pos);
        if (blockEntity instanceof WineShelfBlockEntity wineshelfblockentity) {
            Optional<Vec2> optional = getRelativeHitCoordinatesForBlockFace(hitResult, state.getValue(HorizontalDirectionalBlock.FACING));
            if (optional.isEmpty()) {
                return InteractionResult.PASS;
            } else {
                int i = getHitSlot(optional.get());
                if (state.getValue(SLOT_CONTENTS.get(i)) != Slot.EMPTY) {
                    removeBottle(pLevel, pos, player, wineshelfblockentity, i);
                    return InteractionResult.sidedSuccess(pLevel.isClientSide);
                } else {
                    ItemStack itemstack = player.getItemInHand(hand);
                    if (itemstack.is(VDTags.WINE_SHELF_BOTTLES)) {
                        addBottle(pLevel, pos, player, wineshelfblockentity, itemstack, i);
                        return InteractionResult.sidedSuccess(pLevel.isClientSide);
                    } else {
                        return InteractionResult.CONSUME;
                    }
                }
            }
        } else {
            return InteractionResult.PASS;
        }
    }

    private static Optional<Vec2> getRelativeHitCoordinatesForBlockFace(BlockHitResult hitResult, Direction blocFace) {
        Direction direction = hitResult.getDirection();
        if (blocFace != direction) {
            return Optional.empty();
        } else {
            BlockPos blockpos = hitResult.getBlockPos().relative(direction);
            Vec3 vec3 = hitResult.getLocation().subtract(blockpos.getX(), blockpos.getY(), blockpos.getZ());
            double d0 = vec3.x();
            double d1 = vec3.y();
            double d2 = vec3.z();

            return switch (direction) {
                case NORTH -> Optional.of(new Vec2((float) (1.0D - d0), (float) d1));
                case SOUTH -> Optional.of(new Vec2((float) d0, (float) d1));
                case WEST -> Optional.of(new Vec2((float) d2, (float) d1));
                case EAST -> Optional.of(new Vec2((float) (1.0D - d2), (float) d1));
                case DOWN, UP -> Optional.empty();
            };
        }
    }

    private static int getHitSlot(Vec2 hitPos) {
        int i = hitPos.y >= 0.5F ? 0 : 1;
        int j = getSection(hitPos.x);
        return j + i * 2;
    }

    private static int getSection(float pX) {
        float f = 0.5F;
        if (pX < f) {
            return 0;
        } else {
            return 1;
        }
    }

    // TODO: add sounds for wine shelves
    private static void addBottle(Level level, BlockPos pos, Player player, WineShelfBlockEntity blockEntity, ItemStack bottleStack, int slot) {
        if (!level.isClientSide) {
            player.awardStat(Stats.ITEM_USED.get(bottleStack.getItem()));
            //SoundEvent soundevent = pBookStack.is(Items.ENCHANTED_BOOK) ? SoundEvents.CHISELED_BOOKSHELF_INSERT_ENCHANTED : SoundEvents.CHISELED_BOOKSHELF_INSERT;
            blockEntity.setItem(slot, bottleStack.split(1));
            //pLevel.playSound((Player)null, pPos, soundevent, SoundSource.BLOCKS, 1.0F, 1.0F);
            if (player.isCreative()) {
                bottleStack.grow(1);
            }

            level.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
        }
    }

    private static void removeBottle(Level level, BlockPos pos, Player player, WineShelfBlockEntity blockEntity, int slot) {
        if (!level.isClientSide) {
            ItemStack itemstack = blockEntity.removeItem(slot, 1);
            //SoundEvent soundevent = itemstack.is(Items.ENCHANTED_BOOK) ? SoundEvents.CHISELED_BOOKSHELF_PICKUP_ENCHANTED : SoundEvents.CHISELED_BOOKSHELF_PICKUP;
            //level.playSound((Player)null, pos, soundevent, SoundSource.BLOCKS, 1.0F, 1.0F);
            if (!player.getInventory().add(itemstack)) {
                player.drop(itemstack, false);
            }

            level.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new WineShelfBlockEntity(pos, state);
    }

    @Override
    public void onRemove(BlockState state, @NotNull Level level, @NotNull BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (!state.is(newState.getBlock())) {
            BlockEntity blockentity = level.getBlockEntity(pos);
            if (blockentity instanceof WineShelfBlockEntity wineshelfblockentity) {
                if (!wineshelfblockentity.isEmpty()) {
                    for(int i = 0; i < 4; ++i) {
                        ItemStack itemstack = wineshelfblockentity.getItem(i);
                        if (!itemstack.isEmpty()) {
                            Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), itemstack);
                        }
                    }

                    wineshelfblockentity.clearContent();
                    level.updateNeighbourForOutputSignal(pos, this);
                }
            }

            super.onRemove(state, level, pos, newState, movedByPiston);
        }
    }

    public static @NotNull VoxelShape makeShape(boolean has_support) {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.or(shape, Shapes.box(0, 0.5, 0, 1, 0.625, 1));
        shape = Shapes.or(shape, Shapes.box(0, 0, 0, 1, 0.125, 1));
        shape = Shapes.or(shape, Shapes.box(0.4375, 0.125, 0.875, 0.5625, 0.5, 1));

        if (has_support) {
            shape = Shapes.or(shape, Shapes.box(0.4375, 0.625, 0.875, 0.5625, 1, 1));
        }

        return shape;
    }

    @Override
    public @NotNull BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(HorizontalDirectionalBlock.FACING, rotation.rotate(state.getValue(HorizontalDirectionalBlock.FACING)));
    }

    @Override
    public @NotNull BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(HorizontalDirectionalBlock.FACING)));
    }

    @Override
    public boolean hasAnalogOutputSignal(@NotNull BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(@NotNull BlockState state, Level level, @NotNull BlockPos pos) {
        if (level.isClientSide()) {
            return 0;
        } else {
            BlockEntity blockentity = level.getBlockEntity(pos);
            if (blockentity instanceof WineShelfBlockEntity wineshelfblockentity) {
                return wineshelfblockentity.getLastInteractedSlot() + 1;
            } else {
                return 0;
            }
        }
    }

    public static Slot getSlotTypeForStack(ItemStack stack) {
        for (Slot slot : Slot.values()) {
            TagKey<Item> tag = slot.tag;
            if (tag == null) continue;
            if (stack.is(tag)) {
                return slot;
            }
        }
        return stack.is(VDTags.WINE_SHELF_BOTTLES) ? Slot.WINE_BOTTLE : Slot.EMPTY;
    }

    public enum Slot implements StringRepresentable {
        EMPTY("empty", null),
        BEER_BOTTLE("beer_bottle", VDTags.BEER_BOTTLES),
        WINE_BOTTLE("wine_bottle", VDTags.WINE_BOTTLES);

        private final String name;
        private final @Nullable TagKey<Item> tag;

        Slot(String name, @Nullable TagKey<Item> tag) {
            this.name = name;
            this.tag = tag;
        }

        public @Nullable TagKey<Item> getTag() {
            return tag;
        }

        @Override
        public String getSerializedName() {
            return name;
        }
    }

    public static Iterable<Block> getAllShelveBlocks() {
        return ForgeRegistries.BLOCKS.getValues().stream().filter(block -> ForgeRegistries.BLOCKS.getKey(block) != null && VampiresDelight.MODID.equals(Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block)).getNamespace()) && block instanceof WineShelfBlock).collect(Collectors.toList());
    }
}
