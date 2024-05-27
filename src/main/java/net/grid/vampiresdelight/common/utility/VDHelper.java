package net.grid.vampiresdelight.common.utility;

import com.google.common.collect.ImmutableSet;
import de.teamlapen.vampirism.items.VampirismItemBloodFoodItem;
import net.grid.vampiresdelight.common.item.VampireConsumableItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.PlantType;
import net.minecraftforge.registries.ForgeRegistries;

import java.io.File;
import java.util.Objects;

public class VDHelper {
    // Plant type for plants that grow on cursed soil
    public static final PlantType CURSED_PLANT_TYPE = PlantType.get("vampiresdelight_cursed");

    public static boolean isDebugger(Player player) {
        if (Objects.equals(player.getGameProfile().getName(), "Dev")) return true;
        return DEBUGGERS_UUID.contains(player.getUUID().toString());
    }

    private static final ImmutableSet<String> DEBUGGERS_UUID = ImmutableSet.of(
            "052ef844-4947-452c-867d-902c8fa1cd94" // GridExpert
    );

    public static boolean isItemOfVampireFoodClass(Item item) {
        return item instanceof VampireConsumableItem || item instanceof VampirismItemBloodFoodItem;
    }

    public static String getItemId(Item item) {
        ResourceLocation location = ForgeRegistries.ITEMS.getKey(item);
        return (location != null) ? location.getPath() : "";
    }

    public static String getItemModId(Item item) {
        ResourceLocation location = ForgeRegistries.ITEMS.getKey(item);
        return (location != null) ? location.getNamespace() : "";
    }

    // I'm not proud of this... I'm sure there's a better way of doing this /:
    public static boolean isRightItem(Item item, String name) {
        String predictedName = getItemModId(item) + ":" + getItemId(item);
        return Objects.equals(name, predictedName);
    }

    /**
     * Credit: WaterMedia
     * <a href="https://github.com/SrRapero720/watermedia">...</a>
     */
    public static boolean isLauncherPirate() {
        return VDIntegrationUtils.isModPresent("tlskincape") || new File("").toPath().toAbsolutePath().toString().contains("tlauncher");
    }
}
