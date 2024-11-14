package net.grid.vampiresdelight.common.registry;

import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.crafting.WeaveLettersRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class VDRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(Registries.RECIPE_SERIALIZER, VampiresDelight.MODID);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<WeaveLettersRecipe>> WEAVE_LETTERS = RECIPE_SERIALIZERS.register("weave_letters", () -> new SimpleCraftingRecipeSerializer<>(WeaveLettersRecipe::new));
}
