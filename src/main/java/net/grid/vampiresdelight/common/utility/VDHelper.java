package net.grid.vampiresdelight.common.utility;

import de.teamlapen.vampirism.util.Helper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.PlantType;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

public class VDHelper extends Helper {
    // Plant type for plants that grow on cursed soil
    public static final PlantType CURSED_PLANT_TYPE = PlantType.get("vampiresdelight_cursed");

    public static String getItemId(Item item) {
        ResourceLocation location = ForgeRegistries.ITEMS.getKey(item);
        return (location != null) ? location.getPath() : "";
    }

    public static String getItemModId(Item item) {
        ResourceLocation location = ForgeRegistries.ITEMS.getKey(item);
        return (location != null) ? location.getNamespace() : "";
    }

    // I'm not proud of this... I'm sure there's a better way of doing this /:
    public static boolean doesMatch(Item item, String name) {
        String predictedName = getItemModId(item) + ":" + getItemId(item);
        return Objects.equals(name, predictedName);
    }

    // TODO: test this (it doesn't work yet)
    //public static boolean matches(Item item, ResourceLocation id) {
        //return RegUtil.id(item) == id;
    //}
}
