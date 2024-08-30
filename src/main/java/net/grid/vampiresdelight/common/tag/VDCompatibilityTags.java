package net.grid.vampiresdelight.common.tag;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import vectorwing.farmersdelight.common.tag.CompatibilityTags;

public class VDCompatibilityTags extends CompatibilityTags {
    // Werewolves
    public static final String WEREWOLVES = "werewolves";
    public static final TagKey<Item> SILVER_TOOL = externalItemTag(WEREWOLVES, "tools/silver");
    public static final TagKey<Item> WEREWOLF_FOOD = externalItemTag(WEREWOLVES, "werewolf_food");

    public static final TagKey<Block> INCORRECT_FOR_SILVER_TOOL = externalBlockTag("c", "incorrect_for_silver_tool");

    private static TagKey<Item> externalItemTag(String modId, String path) {
        return ItemTags.create(ResourceLocation.fromNamespaceAndPath(modId, path));
    }

    private static TagKey<Block> externalBlockTag(String modId, String path) {
        return BlockTags.create(ResourceLocation.fromNamespaceAndPath(modId, path));
    }
}
