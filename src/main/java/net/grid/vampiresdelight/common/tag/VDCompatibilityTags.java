package net.grid.vampiresdelight.common.tag;

import net.grid.vampiresdelight.common.utility.VDIntegrationUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class VDCompatibilityTags {
    // Werewolves
    public static final String WEREWOLVES = VDIntegrationUtils.WEREWOLVES;
    public static final TagKey<Item> SILVER_TOOL = externalItemTag(WEREWOLVES, "tools/silver");
    public static final TagKey<Item> WEREWOLF_FOOD = externalItemTag(WEREWOLVES, "werewolf_food");

    private static TagKey<Item> externalItemTag(String modId, String path) {
        return ItemTags.create(new ResourceLocation(modId, path));
    }

    private static TagKey<Block> externalBlockTag(String modId, String path) {
        return BlockTags.create(new ResourceLocation(modId, path));
    }
}
