package net.grid.vampiresdelight.data.recipe;

import de.teamlapen.vampirism.core.ModItems;
import de.teamlapen.vampirism.data.recipebuilder.AlchemyTableRecipeBuilder;
import de.teamlapen.vampirism.data.recipebuilder.ShapedWeaponTableRecipeBuilder;
import de.teamlapen.vampirism.data.recipebuilder.ShapelessWeaponTableRecipeBuilder;
import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.registry.VDBlocks;
import net.grid.vampiresdelight.common.registry.VDItems;
import net.grid.vampiresdelight.common.registry.VDOils;
import net.grid.vampiresdelight.common.registry.VDPotions;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.crafting.CompoundIngredient;
import net.neoforged.neoforge.common.crafting.DataComponentIngredient;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class VDVampirismRecipes {
    public static void register(RecipeOutput output) {
        recipesWeaponTable(output);
        recipesAlchemyTable(output);
    }

    private static void recipesWeaponTable(RecipeOutput output) {
        ShapelessWeaponTableRecipeBuilder.shapelessWeaponTable(RecipeCategory.COMBAT, VDItems.ALCHEMICAL_COCKTAIL.get())
                .requires(Items.GLASS_BOTTLE)
                .requires(ModItems.ITEM_ALCHEMICAL_FIRE.get(), 2)
                .unlockedBy("has_alchemical_fire", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.ITEM_ALCHEMICAL_FIRE.get()))
                .save(output);

        ShapedWeaponTableRecipeBuilder.shapedWeaponTable(RecipeCategory.COMBAT, VDBlocks.SPIRIT_LANTERN.get())
                .pattern(" gg ")
                .pattern("gssg")
                .pattern("gssg")
                .pattern(" gg ")
                .define('g', Items.GOLD_NUGGET)
                .define('s', ModItems.SOUL_ORB_VAMPIRE.get())
                .unlockedBy("has_vampire_soul", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.SOUL_ORB_VAMPIRE.get()))
                .save(output);
    }

    private static void recipesAlchemyTable(RecipeOutput output) {
        AlchemyTableRecipeBuilder
                .builder(VDOils.BLESSING)
                .bloodOilIngredient()
                .input(potion(VDPotions.BLESSING, VDPotions.STRONG_BLESSING, VDPotions.LONG_BLESSING))
                .save(output, ResourceLocation.fromNamespaceAndPath(VampiresDelight.MODID, "blessing_oil"));

        AlchemyTableRecipeBuilder
                .builder(VDOils.CLOTHES_DISSOLVING)
                .bloodOilIngredient()
                .input(potion(VDPotions.CLOTHES_DISSOLVING))
                .save(output, ResourceLocation.fromNamespaceAndPath(VampiresDelight.MODID, "clothes_dissolving_oil"));

        AlchemyTableRecipeBuilder
                .builder(VDOils.FOG_VISION)
                .bloodOilIngredient()
                .input(potion(VDPotions.FOG_VISION, VDPotions.STRONG_FOG_VISION, VDPotions.LONG_FOG_VISION))
                .save(output, ResourceLocation.fromNamespaceAndPath(VampiresDelight.MODID, "fog_vision_oil"));
    }

    @SafeVarargs
    private static @NotNull Ingredient potion(Holder<Potion> @NotNull ... potion) {
        return CompoundIngredient.of(Arrays.stream(potion).map(PotionContents::new).map(s -> DataComponentIngredient.of(false, DataComponents.POTION_CONTENTS, s, Items.POTION)).toArray(Ingredient[]::new));
    }

    private static @NotNull Ingredient potion(@NotNull Holder<Potion> potion) {
        return DataComponentIngredient.of(false, DataComponents.POTION_CONTENTS, new PotionContents(potion), Items.POTION);
    }
}
