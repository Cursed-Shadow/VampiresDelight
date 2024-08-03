package net.grid.vampiresdelight.data.recipe;

import de.teamlapen.vampirism.core.ModBlocks;
import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.registry.VDItems;
import net.grid.vampiresdelight.common.utility.VDNameUtils;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

public class VDSmeltingRecipes {
    public static void register(RecipeOutput output) {
        multipleSmeltingRecipes(VDItems.GRILLED_GARLIC.get(), ModBlocks.GARLIC.asItem(),
                0.35F, true, true, true, output);

        multipleSmeltingRecipes(VDItems.COOKED_BAT.get(), VDItems.RAW_BAT.get(),
                0.35F, true, true, true, output);

        multipleSmeltingRecipes(VDItems.COOKED_BAT_CHOPS.get(), VDItems.RAW_BAT_CHOPS.get(),
                0.35F, true, true, true, output);

        multipleSmeltingRecipes(VDItems.RICE_BREAD.get(), VDItems.RICE_DOUGH.get(),
                0.35F, true, true, false, output);

        multipleSmeltingRecipes(VDItems.BLOOD_BAGEL.get(), VDItems.BLOOD_DOUGH.get(),
                0.35F, true, true, false, output);
    }

    private static void multipleSmeltingRecipes(Item result, ItemLike ingredient, float experience, boolean hasSmeltingRecipe, boolean hasSmokingRecipe, boolean hasCampfireRecipe, RecipeOutput output) {
        multipleSmeltingRecipes(VDNameUtils.itemName(result), result, ingredient, experience, hasSmeltingRecipe, hasSmokingRecipe, hasCampfireRecipe, output);
    }

    private static void multipleSmeltingRecipes(String name, ItemLike result, ItemLike ingredient, float experience, boolean hasSmeltingRecipe, boolean hasSmokingRecipe, boolean hasCampfireRecipe, RecipeOutput output) {
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
