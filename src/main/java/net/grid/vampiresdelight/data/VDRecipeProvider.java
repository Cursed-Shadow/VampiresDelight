package net.grid.vampiresdelight.data;

import net.grid.vampiresdelight.data.recipe.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;

import java.util.concurrent.CompletableFuture;

public class VDRecipeProvider extends RecipeProvider {
    public VDRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput output) {
        VDCookingRecipeProvider.register(output);
        VDCraftingRecipeProvider.register(output);
        VDCuttingRecipeProvider.register(output);
        VDSmeltingRecipeProvider.register(output);
        VDVampirismRecipeProvider.register(output);
    }
}
