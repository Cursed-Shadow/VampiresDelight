package net.grid.vampiresdelight.data.builder;

import net.grid.vampiresdelight.VampiresDelight;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;
import vectorwing.farmersdelight.data.builder.CookingPotRecipeBuilder;

public class VDCookingPotRecipeBuilder extends CookingPotRecipeBuilder {
    public VDCookingPotRecipeBuilder(ItemLike result, int count, int cookingTime, float experience, @Nullable ItemLike container) {
        super(result, count, cookingTime, experience, container);
    }

    public static VDCookingPotRecipeBuilder cookingPotRecipe(ItemLike mainResult, int count, int cookingTime, float experience) {
        return new VDCookingPotRecipeBuilder(mainResult, count, cookingTime, experience, null);
    }

    public static VDCookingPotRecipeBuilder cookingPotRecipe(ItemLike mainResult, int count, int cookingTime, float experience, ItemLike container) {
        return new VDCookingPotRecipeBuilder(mainResult, count, cookingTime, experience, container);
    }

    @Override
    public void build(RecipeOutput output) {
        ResourceLocation location = BuiltInRegistries.ITEM.getKey(getResult());
        this.save(output, ResourceLocation.fromNamespaceAndPath(VampiresDelight.MODID, location.getPath()));
    }
}
