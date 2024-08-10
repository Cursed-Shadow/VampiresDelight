package net.grid.vampiresdelight.data.loot;

import de.teamlapen.vampirism.core.ModBlocks;
import net.grid.vampiresdelight.common.block.BarStoolBlock;
import net.grid.vampiresdelight.common.block.ConsumableCandleCakeBlock;
import net.grid.vampiresdelight.common.block.WineShelfBlock;
import net.grid.vampiresdelight.common.registry.VDBlocks;
import net.grid.vampiresdelight.common.registry.VDItems;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.AlternativesEntry;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.ApplyExplosionDecay;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.neoforged.neoforge.common.Tags;
import org.jetbrains.annotations.NotNull;
import vectorwing.farmersdelight.common.block.FeastBlock;

import java.util.HashSet;
import java.util.Set;

public class VDBlockLootTableProvider extends BlockLootSubProvider {
    private final Set<Block> generatedLootTables = new HashSet<>();

    public VDBlockLootTableProvider(HolderLookup.Provider lookupProvider) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), lookupProvider);
    }

    @Override
    protected void generate() {
        dropSelf(VDBlocks.DARK_STONE_STOVE.get());

        dropSelf(VDBlocks.GARLIC_CRATE.get());
        dropSelf(VDBlocks.ORCHID_BAG.get());

        dropNamedContainer(VDBlocks.DARK_SPRUCE_CABINET.get());
        dropNamedContainer(VDBlocks.CURSED_SPRUCE_CABINET.get());
        dropNamedContainer(VDBlocks.JACARANDA_CABINET.get());
        dropNamedContainer(VDBlocks.MAGIC_CABINET.get());

        WineShelfBlock.getAllShelveBlocks().forEach(this::dropSelf);
        BarStoolBlock.getBarStoolBlocks().forEach(this::dropSelf);

        dropOther(VDBlocks.CURSED_FARMLAND.get(), ModBlocks.CURSED_EARTH.get());
        dropSelf(VDBlocks.BLOODY_SOIL.get());
        dropOther(VDBlocks.BLOODY_SOIL_FARMLAND.get(), VDBlocks.BLOODY_SOIL.get());

        add(VDBlocks.BLACK_MUSHROOM_BLOCK.get(), block -> createMushroomBlockDrop(block, VDItems.BLACK_MUSHROOM.get()));
        dropWhenSilkTouch(VDBlocks.BLACK_MUSHROOM_STEM.get());
        dropPottedContents(VDBlocks.POTTED_BLACK_MUSHROOM.get());
        dropSelf(VDBlocks.BLACK_MUSHROOM.get());

        dropWildCrop(VDBlocks.WILD_GARLIC.get(), ModBlocks.GARLIC.asItem());

        dropSelf(VDBlocks.SPIRIT_LANTERN.get());

        add(VDBlocks.ORCHID_CAKE.get(), noDrop());
        ConsumableCandleCakeBlock.getAllCandleCakes().forEach(block -> add(block, createCandleCakeDrops(((ConsumableCandleCakeBlock) block).getCandleBlock())));

        dropFeastBlock(VDBlocks.WEIRD_JELLY_BLOCK.get(), 4);
    }

    protected void dropNamedContainer(Block block) {
        add(block, this::createNameableBlockEntityTable);
    }

    protected void dropWildCrop(Block block, Item cropItem) {
        HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);

        add(block, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .add(AlternativesEntry.alternatives(
                                LootItem.lootTableItem(block)
                                        .when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(Tags.Items.TOOLS_SHEAR))),
                                LootItem.lootTableItem(cropItem)
                                        .apply(ApplyExplosionDecay.explosionDecay())
                                        .apply(ApplyBonusCount.addUniformBonusCount(registrylookup.getOrThrow(Enchantments.FORTUNE), 2))))));
    }

    protected void dropFeastBlock(Block block, int servings) {
        add(block, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(block))
                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(FeastBlock.SERVINGS, servings))))
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(Items.BOWL))
                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(FeastBlock.SERVINGS, servings)).invert())));
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
