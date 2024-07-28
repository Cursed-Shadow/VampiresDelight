package net.grid.vampiresdelight.data.recipe;

import de.teamlapen.vampirism.core.ModBlocks;
import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.registry.VDItems;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

public class VDSmeltingRecipes {
    public static void register(RecipeOutput output) {
        multipleSmeltingRecipes("grilled_garlic", ModBlocks.GARLIC.asItem(), VDItems.GRILLED_GARLIC.get(),
                0.35F, true, true, true, output);
        multipleSmeltingRecipes("cooked_bat", VDItems.RAW_BAT.get(), VDItems.COOKED_BAT.get(),
                0.35F, true, true, true, output);
        multipleSmeltingRecipes("cooked_bat_chops", VDItems.RAW_BAT_CHOPS.get(), VDItems.COOKED_BAT_CHOPS.get(),
                0.35F, true, true, true, output);
        multipleSmeltingRecipes("rice_bread", VDItems.RICE_DOUGH.get(), VDItems.RICE_BREAD.get(),
                0.35F, true, true, false, output);
        multipleSmeltingRecipes("blood_bagel", VDItems.BLOOD_DOUGH.get(), VDItems.BLOOD_BAGEL.get(),
                0.35F, true, true, false, output);
    }

    private static void multipleSmeltingRecipes(String name, ItemLike ingredient, ItemLike result, float experience, boolean hasSmeltingRecipe, boolean hasSmokingRecipe, boolean hasCampfireRecipe, RecipeOutput output) {
        String namePrefix = ResourceLocation.fromNamespaceAndPath(VampiresDelight.MODID, name).toString();
        if (hasSmeltingRecipe) SimpleCookingRecipeBuilder.smelting(Ingredient.of(ingredient), RecipeCategory.FOOD, result, experience, 200)
                .unlockedBy(name, InventoryChangeTrigger.TriggerInstance.hasItems(ingredient))
                .save(output);
        if (hasCampfireRecipe) SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(ingredient), RecipeCategory.FOOD, result, experience, 600)
                .unlockedBy(name, InventoryChangeTrigger.TriggerInstance.hasItems(ingredient))
                .save(output, namePrefix + "_from_campfire_cooking");
        if (hasSmokingRecipe) SimpleCookingRecipeBuilder.smoking(Ingredient.of(ingredient), RecipeCategory.FOOD, result, experience, 100)
                .unlockedBy(name, InventoryChangeTrigger.TriggerInstance.hasItems(ingredient))
                .save(output, namePrefix + "_from_smoking");
    }
}
