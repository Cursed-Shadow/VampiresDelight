package net.grid.vampiresdelight.data.loot;

import de.teamlapen.vampirism.core.ModBlocks;
import net.grid.vampiresdelight.common.registry.VDEnchantments;
import net.grid.vampiresdelight.common.registry.VDItems;
import net.grid.vampiresdelight.common.registry.VDLootTables;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.EnchantRandomlyFunction;
import net.minecraft.world.level.storage.loot.functions.EnchantWithLevelsFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import org.jetbrains.annotations.NotNull;
import vectorwing.farmersdelight.common.registry.ModItems;

import java.util.function.BiConsumer;

public class VDChestLootTables implements LootTableSubProvider {
    private final HolderLookup.Provider provider;

    public VDChestLootTables(HolderLookup.Provider provider) {
        this.provider = provider;
    }

    @Override
    public void generate(@NotNull BiConsumer<ResourceKey<LootTable>, LootTable.Builder> consumer) {
        lootModifiers(consumer);
        lootChests(consumer);
    }

    public void lootChests(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> consumer) {
        consumer.accept(VDLootTables.CHEST_LOST_CARRIAGE, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(9, 25))
                        .add(LootItem.lootTableItem(Items.BREAD).setWeight(10))
                        .add(LootItem.lootTableItem(de.teamlapen.vampirism.core.ModItems.GARLIC_BREAD.get()).setWeight(10))
                        .add(LootItem.lootTableItem(ModBlocks.GARLIC.asItem()).setWeight(10))
                        .add(LootItem.lootTableItem(Items.COBWEB).setWeight(20))
                        .add(LootItem.lootTableItem(Items.WHEAT).setWeight(15))
                        .add(LootItem.lootTableItem(Items.POTATO).setWeight(10))
                        .add(LootItem.lootTableItem(Items.CARROT).setWeight(10))
                        .add(LootItem.lootTableItem(Items.APPLE).setWeight(10))
                        .add(LootItem.lootTableItem(Items.IRON_INGOT).setWeight(5))
                        .add(LootItem.lootTableItem(Items.EMERALD).setWeight(5))
                )
        );
    }

    public void lootModifiers(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> consumer) {
        consumer.accept(VDLootTables.VD_CHEST_VAMPIRE_DUNGEON, LootTable.lootTable()
                .withPool(vampiresBiteBookLoot(1, 2))
        );
        consumer.accept(VDLootTables.VD_CHEST_VAMPIRE_HUT, LootTable.lootTable()
                .withPool(vampiresBiteBookLoot(1, 3))
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(5, 8))
                        .add(LootItem.lootTableItem(VDItems.ORCHID_COOKIE.get()).setWeight(10))
                        .add(LootItem.lootTableItem(VDItems.ORCHID_TEA.get()).setWeight(3))
                        .add(LootItem.lootTableItem(VDItems.BAGEL_SANDWICH.get()).setWeight(2))
                        .add(LootItem.lootTableItem(VDItems.HUMAN_EYE.get()).setWeight(5))
                        .add(LootItem.lootTableItem(VDItems.BLOOD_PIE_SLICE.get()).setWeight(3))
                )
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.IRON_KNIFE.get()).setWeight(4)
                                .apply(EnchantWithLevelsFunction.enchantWithLevels(provider, UniformGenerator.between(10.0F, 35.0F))))
                        .add(EmptyLootItem.emptyItem().setWeight(8))
                )
        );
        consumer.accept(VDLootTables.VD_CHEST_ALTAR, LootTable.lootTable()
                .withPool(vampiresBiteBookLoot(1, 2))
        );
        consumer.accept(VDLootTables.VD_CHEST_CRYPT, LootTable.lootTable()
                .withPool(vampiresBiteBookLoot(1, 4))
        );
        consumer.accept(VDLootTables.VD_CHEST_HUNTER_OUTPOST_TENT, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(5, 8))
                        .add(LootItem.lootTableItem(VDItems.HARDTACK.get()).setWeight(5))
                        .add(EmptyLootItem.emptyItem().setWeight(4))
                )
        );
        consumer.accept(VDLootTables.VD_CHEST_HUNTER_OUTPOST_TOWER_FOOD, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(5, 8))
                        .add(LootItem.lootTableItem(VDItems.HARDTACK.get()).setWeight(5))
                        .add(EmptyLootItem.emptyItem().setWeight(4))
                )
        );
        consumer.accept(VDLootTables.VD_CHEST_HUNTER_OUTPOST_TOWER_SPECIAL, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(0, 3))
                        .add(LootItem.lootTableItem(VDItems.ALCHEMICAL_COCKTAIL.get()).setWeight(5))
                        .add(EmptyLootItem.emptyItem().setWeight(4))
                )
        );
    }

    public static LootPool.Builder vampiresBiteBookLoot(int bookWeight, int emptyWeight) {
        return LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                .add(LootItem.lootTableItem(Items.BOOK).setWeight(bookWeight)
                        .apply((new EnchantRandomlyFunction.Builder()).withEnchantment(VDEnchantments.VAMPIRE_BITE.get())))
                .add(EmptyLootItem.emptyItem().setWeight(emptyWeight));
    }
}
