package net.grid.vampiresdelight.common.loot.modifier;

import com.google.common.base.Suppliers;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.grid.vampiresdelight.common.VDConfiguration;
import net.grid.vampiresdelight.common.registry.VDLootModifiers;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.AddTableLootModifier;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

import static net.minecraft.world.level.storage.loot.LootTable.createStackSplitter;

@SuppressWarnings("deprecation")
public class VDAddLootTableModifier extends AddTableLootModifier {
    public static final Supplier<MapCodec<VDAddLootTableModifier>> CODEC = Suppliers.memoize(() ->
            RecordCodecBuilder.mapCodec(inst -> codecStart(inst)
                    .and(ResourceKey.codec(Registries.LOOT_TABLE).fieldOf("lootTable").forGetter((m) -> m.lootTable))
                    .apply(inst, VDAddLootTableModifier::new)));

    private final ResourceKey<LootTable> lootTable;

    public VDAddLootTableModifier(LootItemCondition[] conditionsIn, ResourceKey<LootTable> lootTable) {
        super(conditionsIn, lootTable);
        this.lootTable = lootTable;
    }

    @Nonnull
    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        if (VDConfiguration.GENERATE_VD_CHEST_LOOT.get()) {
            context.getResolver().get(Registries.LOOT_TABLE, this.lootTable).ifPresent((extraTable) -> extraTable.value().getRandomItemsRaw(context, createStackSplitter(context.getLevel(), generatedLoot::add)));
        }
        return generatedLoot;
    }

    @Override
    public MapCodec<? extends IGlobalLootModifier> codec() {
        return VDLootModifiers.ADD_LOOT_TABLE.get();
    }
}
