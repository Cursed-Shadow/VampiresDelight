package net.grid.vampiresdelight.common.utility;

import net.grid.vampiresdelight.common.tag.VDForgeTags;
import net.minecraft.tags.TagKey;
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
