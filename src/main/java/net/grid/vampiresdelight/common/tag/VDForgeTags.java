package net.grid.vampiresdelight.common.tag;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class VDForgeTags {
    public static final TagKey<Item> SILVER_INGOT = forgeItemTag("ingots/silver");
    public static final TagKey<Block> NEEDS_SILVER_TOOL = forgeBlockTag("needs_silver_tool");

    public static final TagKey<Item> BREAD_RICE = forgeItemTag("bread/rice");
    public static final TagKey<Item> DOUGH_RICE = forgeItemTag("dough/rice");

    public static final TagKey<Item> RAW_BAT = forgeItemTag("raw_bat");
    public static final TagKey<Item> COOKED_BAT = forgeItemTag("cooked_bat");

    public static final TagKey<Item> VEGETABLES_GARLIC = forgeItemTag("vegetables/garlic");


    private static TagKey<Block> forgeBlockTag(String path) {
        return BlockTags.create(new ResourceLocation("forge", path));
    }

    private static TagKey<Item> forgeItemTag(String path) {
        return ItemTags.create(new ResourceLocation("forge", path));
    }
}
