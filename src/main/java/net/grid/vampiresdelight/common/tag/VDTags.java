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
    // Vampire and Hunter food (mostly for tooltips and such).
    public static final TagKey<Item> VAMPIRE_FOOD = modItemTag("vampire_food");
    public static final TagKey<Item> HUNTER_FOOD = modItemTag("hunter_food");
    // Werewolf food. Made into a separate tag in order to define which food should be marked as werewolf-only.
    public static final TagKey<Item> WEREWOLF_FOOD = modItemTag("werewolf_food");
    // Vampire and Hunter food that can be fed to minions.
    public static final TagKey<Item> MINION_VAMPIRE_FOOD = modItemTag("minion_vampire_food");
    // All Wine Shelves.
    public static final TagKey<Block> WINE_SHELF = modBlockTag("wine_shelf");

    // Orchid cake variants that drop the cake slice when sliced by a knife.
    public static final TagKey<Block> DROPS_ORCHID_CAKE_SLICE = modBlockTag("drops_orchid_cake_slice");
    // Cold blocks brewing barrel won't work on.
    public static final TagKey<Block> COOLERS = modBlockTag("coolers");

    // Biomes lost carriage spawns in.
    public static final TagKey<Biome> HAS_LOST_CARRIAGE = modBiomeTag("has_structure/lost_carriage");


    private static TagKey<Item> modItemTag(String path) {
        return ItemTags.create(new ResourceLocation(VampiresDelight.MODID, path));
    }

    private static TagKey<Block> modBlockTag(String path) {
        return BlockTags.create(new ResourceLocation(VampiresDelight.MODID, path));
    }

    private static TagKey<EntityType<?>> modEntityTag(String path) {
        return TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation(VampiresDelight.MODID, path));
    }

    private static TagKey<Biome> modBiomeTag(String path) {
        return TagKey.create(Registries.BIOME, new ResourceLocation(VampiresDelight.MODID, path));
    }
}
