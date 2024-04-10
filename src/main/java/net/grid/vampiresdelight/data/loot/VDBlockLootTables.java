package net.grid.vampiresdelight.data.loot;

import de.teamlapen.vampirism.core.ModBlocks;
import net.grid.vampiresdelight.common.block.WineShelfBlock;
import net.grid.vampiresdelight.common.registry.VDBlocks;
import net.grid.vampiresdelight.common.registry.VDItems;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
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

        dropOther(VDBlocks.CURSED_FARMLAND.get(), ModBlocks.CURSED_EARTH.get());

        add(VDBlocks.BLACK_MUSHROOM_BLOCK.get(), block ->
                createMushroomBlockDrop(block, VDItems.BLACK_MUSHROOM.get()));
        dropWhenSilkTouch(VDBlocks.BLACK_MUSHROOM_STEM.get());
        dropPottedContents(VDBlocks.POTTED_BLACK_MUSHROOM.get());
        dropSelf(VDBlocks.BLACK_MUSHROOM.get());

        dropSelf(VDBlocks.SPIRIT_LANTERN.get());
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
