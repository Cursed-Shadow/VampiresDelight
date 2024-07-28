package net.grid.vampiresdelight.data;

import net.grid.vampiresdelight.data.recipe.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;

import java.util.concurrent.CompletableFuture;

public class VDRecipes extends RecipeProvider {
    public VDRecipes(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput output) {
        VDCookingRecipes.register(output);
        VDCraftingRecipes.register(output);
        VDCuttingRecipes.register(output);
        VDSmeltingRecipes.register(output);
        VDVampirismRecipes.register(output);
    }
}
