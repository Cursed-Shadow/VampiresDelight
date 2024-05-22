package net.grid.vampiresdelight.data.loot;

import de.teamlapen.vampirism.core.ModBlocks;
import net.grid.vampiresdelight.common.block.BarStoolBlock;
import net.grid.vampiresdelight.common.block.WineShelfBlock;
import net.grid.vampiresdelight.common.registry.VDBlocks;
import net.grid.vampiresdelight.common.registry.VDItems;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootTable;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public class VDBlockLootTables extends BlockLootSubProvider {
    private final Set<Block> generatedLootTables = new HashSet<>();

    public VDBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        dropSelf(VDBlocks.DARK_STONE_STOVE.get());
        dropSelf(VDBlocks.BREWING_BARREL.get());

        dropSelf(VDBlocks.GARLIC_CRATE.get());
        dropSelf(VDBlocks.ORCHID_BAG.get());

        dropNamedContainer(VDBlocks.DARK_SPRUCE_CABINET.get());
        dropNamedContainer(VDBlocks.CURSED_SPRUCE_CABINET.get());

        WineShelfBlock.getAllShelveBlocks().forEach(this::dropSelf);
        BarStoolBlock.getBarStoolBlocks().forEach(this::dropSelf);

        dropOther(VDBlocks.CURSED_FARMLAND.get(), ModBlocks.CURSED_EARTH.get());

        add(VDBlocks.BLACK_MUSHROOM_BLOCK.get(), block ->
                createMushroomBlockDrop(block, VDItems.BLACK_MUSHROOM.get()));
        dropWhenSilkTouch(VDBlocks.BLACK_MUSHROOM_STEM.get());
        dropPottedContents(VDBlocks.POTTED_BLACK_MUSHROOM.get());
        dropSelf(VDBlocks.BLACK_MUSHROOM.get());

        dropSelf(VDBlocks.SPIRIT_LANTERN.get());

        add(VDBlocks.ORCHID_CAKE.get(), noDrop());
        add(VDBlocks.ORCHID_CANDLE_CAKE.get(), createCandleCakeDrops(Blocks.CANDLE));
        add(VDBlocks.WHITE_ORCHID_CANDLE_CAKE.get(), createCandleCakeDrops(Blocks.WHITE_CANDLE));
        add(VDBlocks.ORANGE_ORCHID_CANDLE_CAKE.get(), createCandleCakeDrops(Blocks.ORANGE_CANDLE));
        add(VDBlocks.MAGENTA_ORCHID_CANDLE_CAKE.get(), createCandleCakeDrops(Blocks.MAGENTA_CANDLE));
        add(VDBlocks.LIGHT_BLUE_ORCHID_CANDLE_CAKE.get(), createCandleCakeDrops(Blocks.LIGHT_BLUE_CANDLE));
        add(VDBlocks.YELLOW_ORCHID_CANDLE_CAKE.get(), createCandleCakeDrops(Blocks.YELLOW_CANDLE));
        add(VDBlocks.LIME_ORCHID_CANDLE_CAKE.get(), createCandleCakeDrops(Blocks.LIME_CANDLE));
        add(VDBlocks.PINK_ORCHID_CANDLE_CAKE.get(), createCandleCakeDrops(Blocks.PINK_CANDLE));
        add(VDBlocks.GRAY_ORCHID_CANDLE_CAKE.get(), createCandleCakeDrops(Blocks.GRAY_CANDLE));
        add(VDBlocks.LIGHT_GRAY_ORCHID_CANDLE_CAKE.get(), createCandleCakeDrops(Blocks.LIGHT_GRAY_CANDLE));
        add(VDBlocks.CYAN_ORCHID_CANDLE_CAKE.get(), createCandleCakeDrops(Blocks.CYAN_CANDLE));
        add(VDBlocks.PURPLE_ORCHID_CANDLE_CAKE.get(), createCandleCakeDrops(Blocks.PURPLE_CANDLE));
        add(VDBlocks.BLUE_ORCHID_CANDLE_CAKE.get(), createCandleCakeDrops(Blocks.BLUE_CANDLE));
        add(VDBlocks.BROWN_ORCHID_CANDLE_CAKE.get(), createCandleCakeDrops(Blocks.BROWN_CANDLE));
        add(VDBlocks.GREEN_ORCHID_CANDLE_CAKE.get(), createCandleCakeDrops(Blocks.GREEN_CANDLE));
        add(VDBlocks.RED_ORCHID_CANDLE_CAKE.get(), createCandleCakeDrops(Blocks.RED_CANDLE));
        add(VDBlocks.BLACK_ORCHID_CANDLE_CAKE.get(), createCandleCakeDrops(Blocks.BLACK_CANDLE));
    }

    protected void dropNamedContainer(Block block) {
        add(block, this::createNameableBlockEntityTable);
    }

    @Override
    protected void add(@NotNull Block block, LootTable.@NotNull Builder builder) {
        this.generatedLootTables.add(block);
        this.map.put(block.getLootTable(), builder);
    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        return generatedLootTables;
    }
}
