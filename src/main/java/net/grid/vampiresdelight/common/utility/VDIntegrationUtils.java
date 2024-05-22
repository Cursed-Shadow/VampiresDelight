package net.grid.vampiresdelight.common.utility;

import de.teamlapen.vampirism.api.VampirismAPI;
import de.teamlapen.vampirism.api.entity.factions.IFaction;
import net.grid.vampiresdelight.common.tag.VDForgeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.LoadingModList;
import org.jetbrains.annotations.NotNull;

public class VDIntegrationUtils {
    public static boolean isModPresent(String string) {
        return ModList.get().isLoaded(string);
    }

    public static boolean isLoadingModPresent(String string) {
        return LoadingModList.get().getModFileById(string) != null;
    }

    // Mod ids
    public static final String WEREWOLVES = "werewolves";
    public static final String APPLESKIN = "appleskin";

    // Werewolves
    public static boolean isWerewolf(Entity entity) {
        if (entity instanceof Player player && isWerewolf(player))
            return true;

        IFaction<?> faction = VampirismAPI.factionRegistry().getFaction(entity);
        return faction != null && faction.getID().getPath().equals("werewolf");
    }

    public static boolean isWerewolf(Player player) {
        return VampirismAPI.getFactionPlayerHandler(player).map(handler ->
                handler.getCurrentFaction() != null &&
                        handler.getCurrentFaction().getFactionPlayerInterface().getSimpleName().equals("IWerewolfPlayer"))
                .orElse(false);
    }

    public static final Tier SILVER_ITEM_TIER = new Tier() {
        @Override
        public int getUses() {
            return 250;
        }

        @Override
        public float getSpeed() {
            return 6.0f;
        }

        @Override
        public float getAttackDamageBonus() {
            return 2f;
        }

        @Override
        public int getLevel() {
            return 2;
        }

        @Override
        public int getEnchantmentValue() {
            return 14;
        }

        @Override
        public @NotNull Ingredient getRepairIngredient() {
            return Ingredient.of(VDForgeTags.SILVER_INGOT);
        }

        @Override
        public TagKey<Block> getTag() {
            return VDForgeTags.NEEDS_SILVER_TOOL;
        }
    };
}
