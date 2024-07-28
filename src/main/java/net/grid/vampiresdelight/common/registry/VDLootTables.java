package net.grid.vampiresdelight.common.registry;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import net.grid.vampiresdelight.VampiresDelight;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class VDLootTables {
    private static final Set<ResourceKey<LootTable>> LOOT_TABLES = Sets.newHashSet();

    public static final ResourceKey<LootTable> CHEST_LOST_CARRIAGE = register("chests/lost_carriage");

    public static final ResourceKey<LootTable> VD_CHEST_VAMPIRE_DUNGEON = register("chests/vd_vampire_dungeon");
    public static final ResourceKey<LootTable> VD_CHEST_VAMPIRE_HUT = register("chests/vd_vampire_hut");
    public static final ResourceKey<LootTable> VD_CHEST_ALTAR = register("chests/vd_altar");
    public static final ResourceKey<LootTable> VD_CHEST_CRYPT = register("chests/vd_crypt");
    public static final ResourceKey<LootTable> VD_CHEST_HUNTER_OUTPOST_TENT = register("chests/vd_hunter_outpost_tent");
    public static final ResourceKey<LootTable> VD_CHEST_HUNTER_OUTPOST_TOWER_FOOD = register("chests/vd_hunter_outpost_tower_food");
    public static final ResourceKey<LootTable> VD_CHEST_HUNTER_OUTPOST_TOWER_SPECIAL = register("chests/vd_hunter_outpost_tower_special");

    static @NotNull ResourceKey<LootTable> register(@NotNull String resourceName) {
        return register(ResourceLocation.fromNamespaceAndPath(VampiresDelight.MODID, resourceName));
    }

    static @NotNull ResourceKey<LootTable> register(@NotNull ResourceLocation resourceLocation) {
        ResourceKey<LootTable> key = ResourceKey.create(Registries.LOOT_TABLE, resourceLocation);
        LOOT_TABLES.add(key);
        return key;
    }

    public static @NotNull Set<ResourceKey<LootTable>> getLootTables() {
        return ImmutableSet.copyOf(LOOT_TABLES);
    }
}
