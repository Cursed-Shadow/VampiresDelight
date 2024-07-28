package net.grid.vampiresdelight.common.tag;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import vectorwing.farmersdelight.common.tag.CommonTags;

public class VDCommonTags extends CommonTags {
    public static final TagKey<Item> SILVER_INGOT = forgeItemTag("ingots/silver");
    public static final TagKey<Block> NEEDS_SILVER_TOOL = forgeBlockTag("needs_silver_tool");

    public static final TagKey<Item> CROPS_GARLIC = commonItemTag("crops/garlic");

    public static final TagKey<Item> FOODS_GARLIC = commonItemTag("foods/garlic");

    public static final TagKey<Item> FOODS_BREADS_RICE = commonItemTag("foods/breads/rice");
    public static final TagKey<Item> FOODS_BREADS_BLOOD = commonItemTag("foods/breads/blood");

    public static final TagKey<Item> FOODS_DOUGH_RICE = commonItemTag("foods/dough/rice");
    public static final TagKey<Item> FOODS_DOUGH_BLOOD = commonItemTag("foods/dough/blood");

    public static final TagKey<Item> FOODS_RAW_BAT = commonItemTag("foods/raw_bat");
    public static final TagKey<Item> FOODS_COOKED_BAT = commonItemTag("foods/cooked_bat");



    private static TagKey<Block> commonBlockTag(String path) {
        return BlockTags.create(ResourceLocation.fromNamespaceAndPath("c", path));
    }

    private static TagKey<Item> commonItemTag(String path) {
        return ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", path));
    }

    private static TagKey<Block> forgeBlockTag(String path) {
        return BlockTags.create(ResourceLocation.fromNamespaceAndPath("forge", path));
    }

    private static TagKey<Item> forgeItemTag(String path) {
        return ItemTags.create(ResourceLocation.fromNamespaceAndPath("forge", path));
    }
}
