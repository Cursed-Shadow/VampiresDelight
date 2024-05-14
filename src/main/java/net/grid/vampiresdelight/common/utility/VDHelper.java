package net.grid.vampiresdelight.common.utility;

import com.google.common.collect.ImmutableSet;
import de.teamlapen.vampirism.items.VampirismItemBloodFoodItem;
import de.teamlapen.vampirism.util.Helper;
import net.grid.vampiresdelight.common.item.VampireConsumableItem;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.PlantType;

import java.util.Objects;

public class VDHelper {
    // Plant type for plants that grow on cursed soil
    public static final PlantType CURSED_PLANT_TYPE = PlantType.get("vampiresdelight_cursed");

    public static boolean isHuman(Entity entity) {
        return !Helper.isVampire(entity) && !Helper.isHunter(entity);
    }

    public static boolean isHuman(Player player) {
        return !Helper.isVampire(player) && !Helper.isHunter(player);
    }

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
}
