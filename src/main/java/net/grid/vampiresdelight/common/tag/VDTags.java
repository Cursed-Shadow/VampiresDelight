package net.grid.vampiresdelight.common.tag;

import net.grid.vampiresdelight.VampiresDelight;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;

public class VDTags {

    // --Items--

    // Factional food (mostly for colored tooltips and such). (Look into the code before using).
    public static final TagKey<Item> VAMPIRE_FOOD = modItemTag("vampire_food");
    public static final TagKey<Item> HUNTER_FOOD = modItemTag("hunter_food");
    public static final TagKey<Item> WEREWOLF_ONLY_FOOD = modItemTag("werewolf_only_food");

    // Food that shouldn't be marked as rotten in AppleSkin food tooltip.
    public static final TagKey<Item> NOT_ROTTEN_FOOD = modItemTag("not_rotten_food");

    // Items which are compatible with the Vampire Bite enchantment.
    public static final TagKey<Item> VAMPIRE_BITE_ENCHANTABLE = modItemTag("enchantable/vampire_bite");

    // Bottle items that can be placed on Wine Shelves.
    public static final TagKey<Item> WINE_SHELF_BOTTLES = modItemTag("wine_shelf_bottles");
    public static final TagKey<Item> BEER_BOTTLES = modItemTag("beer_bottles");
    public static final TagKey<Item> WINE_BOTTLES = modItemTag("wine_bottles");

    // --Blocks--

    // All Wine Shelves (for advancements).
    public static final TagKey<Block> WINE_SHELVES = modBlockTag("wine_shelves");

    // Candle cake variants that drop the cake slice when sliced by a knife.
    public static final TagKey<Block> DROPS_ORCHID_CAKE_SLICE = modBlockTag("drops_orchid_cake_slice");

    // Blocks black mushroom can grow on.
    public static final TagKey<Block> BLACK_MUSHROOM_GROW_BLOCK = modBlockTag("black_mushroom_grow_block");

    // Blocks that can be booster by bloody soil.
    public static final TagKey<Block> CURSED_PLANTS = modBlockTag("cursed_plants");

    // Blocks that can sustain cursed crops.
    public static final TagKey<Block> CURSED_FARMLANDS = modBlockTag("cursed_farmlands");

    // --Biomes--

    // Biomes lost carriage spawns in.
    public static final TagKey<Biome> HAS_LOST_CARRIAGE = modBiomeTag("has_structure/lost_carriage");

    // --Entities--

    // Entities which are considered to be unholy spirits by blessing effect and vanished.
    public static final TagKey<EntityType<?>> UNHOLY_SPIRITS = modEntityTag("unholy_spirits");

    // Human-like entities which are supposed to drop human eyes when killed with
    public static final TagKey<EntityType<?>> DROP_HUMAN_EYE = modEntityTag("drops_human_eye");



    private static TagKey<Item> modItemTag(String path) {
        return ItemTags.create(ResourceLocation.fromNamespaceAndPath(VampiresDelight.MODID, path));
    }

    private static TagKey<Block> modBlockTag(String path) {
        return BlockTags.create(ResourceLocation.fromNamespaceAndPath(VampiresDelight.MODID, path));
    }

    private static TagKey<Biome> modBiomeTag(String path) {
        return TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(VampiresDelight.MODID, path));
    }

    private static TagKey<EntityType<?>> modEntityTag(String path) {
        return TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(VampiresDelight.MODID, path));
    }
}
