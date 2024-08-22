package net.grid.vampiresdelight.common.utility;

import de.teamlapen.vampirism.VampirismMod;
import de.teamlapen.vampirism.config.VampirismConfig;
import de.teamlapen.vampirism.core.ModDataComponents;
import de.teamlapen.vampirism.core.ModItems;
import de.teamlapen.vampirism.effects.SanguinareEffect;
import de.teamlapen.vampirism.items.component.BottleBlood;
import de.teamlapen.vampirism.util.Helper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.crafting.DataComponentIngredient;

import java.util.Objects;

public class VDHelper extends Helper {
    public static final Ingredient FULL_BLOOD_BOTTLE = DataComponentIngredient.of(false, ModDataComponents.BOTTLE_BLOOD.get(), new BottleBlood(9), ModItems.BLOOD_BOTTLE.get());

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
        return item == VDNameUtils.getItem(key);
    }

    public static boolean isDev() {
        Player player = VampirismMod.proxy.getClientPlayer();

        return player != null && Objects.equals(player.getGameProfile().getName(), "Dev");
    }
}
