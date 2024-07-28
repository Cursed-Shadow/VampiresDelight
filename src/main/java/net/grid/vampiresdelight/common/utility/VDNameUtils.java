package net.grid.vampiresdelight.common.utility;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

@SuppressWarnings("unused")
public class VDNameUtils {
    public static ResourceLocation itemLocation(Item item) {
        return BuiltInRegistries.ITEM.getKey(item);
    }

    public static String itemName(Item item) {
        return itemLocation(item).getPath();
    }

    public static String itemNamespace(Item item) {
        return itemLocation(item).getNamespace();
    }
    public static Item getItem(ResourceLocation key) {
        return BuiltInRegistries.ITEM.get(key);
    }

    public static ResourceLocation blockLocation(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block);
    }

    public static String blockName(Block block) {
        return blockLocation(block).getPath();
    }

    public static String blockNamespace(Block block) {
        return blockLocation(block).getNamespace();
    }
    public static Block getBlock(ResourceLocation key) {
        return BuiltInRegistries.BLOCK.get(key);
    }
}
