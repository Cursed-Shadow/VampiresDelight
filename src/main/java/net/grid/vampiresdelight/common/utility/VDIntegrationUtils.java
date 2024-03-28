package net.grid.vampiresdelight.common.utility;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;

public class VDIntegrationUtils {
    public boolean isModLoaded(String string) {
        return ModList.get().isLoaded(string);
    }

    public static boolean doesModItemExist(ResourceLocation resourceLocation) {
        return ForgeRegistries.ITEMS.getValue(resourceLocation) != null;
    }

    public static Item getModItem(ResourceLocation resourceLocation) {
        return ForgeRegistries.ITEMS.getValue(resourceLocation);
    }

    // Werewolves
    public static final String WEREWOLVES = "werewolves";

    public static final ResourceLocation LIVER = new ResourceLocation(WEREWOLVES, "liver");
    public static final ResourceLocation CRACKED_BONE = new ResourceLocation(WEREWOLVES, "cracked_bone");
    public static final ResourceLocation WOLF_BERRIES = new ResourceLocation(WEREWOLVES, "wolf_berries");
}
