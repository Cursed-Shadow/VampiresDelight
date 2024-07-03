package net.grid.vampiresdelight.common.utility;

import de.teamlapen.vampirism.REFERENCE;
import de.teamlapen.vampirism.VampirismMod;
import de.teamlapen.vampirism.config.VampirismConfig;
import de.teamlapen.vampirism.effects.SanguinareEffect;
import de.teamlapen.vampirism.util.Helper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.PlantType;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

public class VDHelper extends Helper {
    // Plant type for plants that grow on cursed soil.
    public static final PlantType CURSED_PLANT_TYPE = PlantType.get("vampiresdelight_cursed");

    public static final ResourceLocation BLOOD_BAR_ELEMENT = new ResourceLocation(REFERENCE.MODID, "blood_bar");

    public static boolean canBeInfectedFromItem(Player player) {
        return canBecomeVampire(player) && !VampirismConfig.SERVER.disableFangInfection.get();
    }

    // Infects consumer in case it's player and only if they can become a vampire.
    public static void tryInfectWithoutPoisoning(LivingEntity consumer) {
        if (consumer instanceof Player player && canBeInfectedFromItem(player)) {
            SanguinareEffect.addRandom(consumer, true);
        }
    }

    public static boolean isSame(Item item, ResourceLocation key) {
        Item requiedItem = ForgeRegistries.ITEMS.getValue(key);
        if (requiedItem == null) return false;

        return item == requiedItem;
    }

    public static boolean isDev() {
        Player player = VampirismMod.proxy.getClientPlayer();

        return player != null && Objects.equals(player.getGameProfile().getName(), "Dev");
    }
}
