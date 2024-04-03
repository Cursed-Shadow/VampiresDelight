package net.grid.vampiresdelight.common.utility;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.ModList;

public class VDIntegrationUtils {
    public boolean isModLoaded(String string) {
        return ModList.get().isLoaded(string);
    }

    // As Markiplier once said - later

    // Werewolves
    public static final String WEREWOLVES = "werewolves";

    public static final ResourceLocation LIVER = new ResourceLocation(WEREWOLVES, "liver");
    public static final ResourceLocation CRACKED_BONE = new ResourceLocation(WEREWOLVES, "cracked_bone");
    public static final ResourceLocation WOLF_BERRIES = new ResourceLocation(WEREWOLVES, "wolf_berries");
}
