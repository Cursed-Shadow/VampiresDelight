package net.grid.vampiresdelight.common.registry;

import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.block.entity.DarkStoneStoveBlockEntity;
import net.grid.vampiresdelight.common.block.entity.WineShelfBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class VDBlockEntityTypes {
    public static final DeferredRegister<BlockEntityType<?>> TILES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, VampiresDelight.MODID);

    public static final Supplier<BlockEntityType<DarkStoneStoveBlockEntity>> DARK_STONE_STOVE = TILES.register("dark_stone_stove",
            () -> BlockEntityType.Builder.of(DarkStoneStoveBlockEntity::new, VDBlocks.DARK_STONE_STOVE.get()).build(null));
    public static final Supplier<BlockEntityType<WineShelfBlockEntity>> WINE_SHELF = TILES.register("wine_shelf",
            () -> BlockEntityType.Builder.of(WineShelfBlockEntity::new,
                            VDBlocks.OAK_WINE_SHELF.get(),
                            VDBlocks.SPRUCE_WINE_SHELF.get(),
                            VDBlocks.BIRCH_WINE_SHELF.get(),
                            VDBlocks.JUNGLE_WINE_SHELF.get(),
                            VDBlocks.ACACIA_WINE_SHELF.get(),
                            VDBlocks.DARK_OAK_WINE_SHELF.get(),
                            VDBlocks.MANGROVE_WINE_SHELF.get(),
                            VDBlocks.CHERRY_WINE_SHELF.get(),
                            VDBlocks.BAMBOO_WINE_SHELF.get(),
                            VDBlocks.CRIMSON_WINE_SHELF.get(),
                            VDBlocks.WARPED_WINE_SHELF.get(),
                            VDBlocks.CURSED_SPRUCE_WINE_SHELF.get(),
                            VDBlocks.DARK_SPRUCE_WINE_SHELF.get(),
                            VDBlocks.JACARANDA_WINE_SHELF.get(),
                            VDBlocks.MAGIC_WINE_SHELF.get())
                    .build(null));
}
