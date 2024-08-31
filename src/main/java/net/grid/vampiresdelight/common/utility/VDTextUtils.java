package net.grid.vampiresdelight.common.utility;

import net.grid.vampiresdelight.VampiresDelight;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public class VDTextUtils {
    public static MutableComponent getTranslation(String key, Object... args) {
        return Component.translatable(VampiresDelight.MODID + "." + key, args);
    }

    public static String getStraightTranslation(String key) {
        return I18n.get(VampiresDelight.MODID + "." + key);
    }
}
