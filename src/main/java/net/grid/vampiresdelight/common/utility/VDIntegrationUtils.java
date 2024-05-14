package net.grid.vampiresdelight.common.utility;

import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.LoadingModList;

public class VDIntegrationUtils {
    public static boolean isModPresent(String string) {
        return ModList.get().isLoaded(string);
    }

    public static boolean isLoadingModPresent(String string) {
        return LoadingModList.get().getModFileById(string) != null;
    }

    public static final String WEREWOLVES = "werewolves";
    public static final String APPLESKIN = "appleskin";
}
