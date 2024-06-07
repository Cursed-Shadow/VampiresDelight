package net.grid.vampiresdelight.common.utility;

import de.teamlapen.vampirism.util.Helper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.PlantType;
import net.minecraftforge.registries.ForgeRegistries;

public class VDHelper extends Helper {
    // Plant type for plants that grow on cursed soil
    public static final PlantType CURSED_PLANT_TYPE = PlantType.get("vampiresdelight_cursed");

    public static boolean isSame(Item item, ResourceLocation key) {
        Item requiedItem = ForgeRegistries.ITEMS.getValue(key);
        if (requiedItem == null) return false;

        return item == requiedItem;
    }
}
