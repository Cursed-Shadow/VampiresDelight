package net.grid.vampiresdelight.data.recipe;

import de.teamlapen.vampirism.core.ModBlocks;
import de.teamlapen.vampirism.core.ModItems;
import de.teamlapen.vampirism.core.ModTags;
import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.registry.VDItems;
import net.grid.vampiresdelight.common.tag.VDCommonTags;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import vectorwing.farmersdelight.client.recipebook.CookingPotRecipeBookTab;
import vectorwing.farmersdelight.data.builder.CookingPotRecipeBuilder;

import java.util.stream.Stream;

import static net.grid.vampiresdelight.common.utility.VDNameUtils.blockName;
import static net.grid.vampiresdelight.common.utility.VDNameUtils.itemName;

@SuppressWarnings("unused")
public class VDCookingRecipes {
    public static final int FAST_COOKING = 100;      // 5 seconds
    public static final int NORMAL_COOKING = 200;    // 10 seconds
    public static final int SLOW_COOKING = 400;      // 20 seconds

    public static final int SLOW_FERMENTING = 1200;  // 60 seconds

    public static final float SMALL_EXP = 0.35F;
    public static final float MEDIUM_EXP = 1.0F;
    public static final float LARGE_EXP = 2.0F;

    public static void register(RecipeOutput output) {
        cookMiscellaneous(output);
        cookMeals(output);
        fermentingAlternatives(output);
    }

    private static void cookMiscellaneous(RecipeOutput output) {
        CookingPotRecipeBuilder.cookingPotRecipe(VDItems.DAISY_TEA.get(), 1, NORMAL_COOKING, MEDIUM_EXP)
                .addIngredient(Items.OXEYE_DAISY)
                .addIngredient(Items.OXEYE_DAISY)
                .addIngredient(Items.SUGAR)
                .unlockedByAnyIngredient(Items.OXEYE_DAISY)
                .setRecipeBookTab(CookingPotRecipeBookTab.DRINKS)
                .build(output, itemLocationCooking(VDItems.DAISY_TEA.get()));

        CookingPotRecipeBuilder.cookingPotRecipe(VDItems.ORCHID_TEA.get(), 1, NORMAL_COOKING, MEDIUM_EXP)
                .addIngredient(VDCommonTags.FOODS_MILK)
                .addIngredient(VDItems.ORCHID_PETALS.get())
                .addIngredient(VDItems.ORCHID_PETALS.get())
                .unlockedByAnyIngredient(VDItems.ORCHID_PETALS.get(), ModBlocks.VAMPIRE_ORCHID.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.DRINKS)
                .build(output, itemLocationCooking(VDItems.ORCHID_TEA.get()));

        CookingPotRecipeBuilder.cookingPotRecipe(VDItems.WEIRD_JELLY_BLOCK.get(), 1, NORMAL_COOKING, MEDIUM_EXP)
                .addIngredient(Tags.Items.BONES)
                .addIngredient(Tags.Items.SLIMEBALLS)
                .addIngredient(Tags.Items.SLIMEBALLS)
                .addIngredient(Items.SWEET_BERRIES)
                .addIngredient(Items.SWEET_BERRIES)
                .addIngredient(ModTags.Items.PURE_BLOOD)
                .unlockedByAnyIngredient(ModItems.PURE_BLOOD_0.get(), ModItems.PURE_BLOOD_1.get(), ModItems.PURE_BLOOD_2.get(), ModItems.PURE_BLOOD_3.get(), ModItems.PURE_BLOOD_4.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                .build(output, itemLocationCooking(VDItems.WEIRD_JELLY_BLOCK.get()));

        CookingPotRecipeBuilder.cookingPotRecipe(VDItems.TRICOLOR_DANGO.get(), 1, NORMAL_COOKING, MEDIUM_EXP, Items.STICK)
                .addIngredient(VDCommonTags.FOODS_DOUGH_RICE)
                .addIngredient(VDItems.BLOOD_SYRUP.get())
                .addIngredient(VDItems.ORCHID_PETALS.get())
                .addIngredient(Items.SUGAR)
                .unlockedByAnyIngredient(VDItems.ORCHID_PETALS.get(), VDItems.BLOOD_SYRUP.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.MISC)
                .build(output, itemLocationCooking(VDItems.TRICOLOR_DANGO.get()));

        CookingPotRecipeBuilder.cookingPotRecipe(VDItems.MULLED_WINE_GLASS.get(), 1, NORMAL_COOKING, MEDIUM_EXP)
                .addIngredient(VDItems.BLOOD_WINE_GLASS.get())
                .addIngredient(Ingredient.fromValues(Stream.of(
                        new Ingredient.ItemValue(new ItemStack(ModBlocks.CURSED_ROOTS.get())),
                        new Ingredient.ItemValue(new ItemStack(ModBlocks.CURSED_SPRUCE_SAPLING.get())),
                        new Ingredient.ItemValue(new ItemStack(ModBlocks.DARK_SPRUCE_SAPLING.get())),
                        new Ingredient.ItemValue(new ItemStack(ModBlocks.DARK_SPRUCE_LEAVES.get()))
                )))
                .addIngredient(Items.SUGAR)
                .unlockedByAnyIngredient(VDItems.BLOOD_WINE_GLASS.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.DRINKS)
                .build(output, itemLocationCooking(VDItems.MULLED_WINE_GLASS.get()));
    }

    private static void cookMeals(RecipeOutput output) {
        CookingPotRecipeBuilder.cookingPotRecipe(VDItems.ORCHID_CREAM_SOUP.get(), 1, NORMAL_COOKING, MEDIUM_EXP)
                .addIngredient(VDItems.ORCHID_PETALS.get())
                .addIngredient(Tags.Items.FOODS_VEGETABLE)
                .addIngredient(VDCommonTags.CROPS_ONION)
                .addIngredient(Items.POTATO)
                .addIngredient(VDCommonTags.FOODS_MILK)
                .unlockedByAnyIngredient(VDItems.ORCHID_PETALS.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                .build(output, itemLocationCooking(VDItems.ORCHID_CREAM_SOUP.get()));

        CookingPotRecipeBuilder.cookingPotRecipe(VDItems.BLACK_MUSHROOM_SOUP.get(), 1, NORMAL_COOKING, MEDIUM_EXP)
                .addIngredient(VDItems.BLACK_MUSHROOM.get())
                .addIngredient(Items.POTATO)
                .addIngredient(Items.CARROT)
                .addIngredient(VDCommonTags.CROPS_ONION)
                .unlockedByAnyIngredient(VDItems.BLACK_MUSHROOM.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                .build(output, itemLocationCooking(VDItems.BLACK_MUSHROOM_SOUP.get()));

        CookingPotRecipeBuilder.cookingPotRecipe(VDItems.ORCHID_CURRY.get(), 1, NORMAL_COOKING, MEDIUM_EXP)
                .addIngredient(VDItems.ORCHID_PETALS.get())
                .addIngredient(VDItems.ORCHID_PETALS.get())
                .addIngredient(Items.POTATO)
                .addIngredient(VDCommonTags.CROPS_ONION)
                .addIngredient(Ingredient.fromValues(Stream.of(
                        new Ingredient.TagValue(VDCommonTags.FOODS_RAW_MUTTON),
                        new Ingredient.TagValue(VDCommonTags.FOODS_RAW_BEEF)
                )))
                .unlockedByAnyIngredient(VDItems.ORCHID_PETALS.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                .build(output, itemLocationCooking(VDItems.ORCHID_CURRY.get()));

        CookingPotRecipeBuilder.cookingPotRecipe(VDItems.BLACK_MUSHROOM_NOODLES.get(), 1, NORMAL_COOKING, MEDIUM_EXP)
                .addIngredient(VDItems.BLACK_MUSHROOM.get())
                .addIngredient(VDItems.BLACK_MUSHROOM.get())
                .addIngredient(VDCommonTags.FOODS_PASTA)
                .addIngredient(VDCommonTags.CROPS_ONION)
                .addIngredient(VDCommonTags.FOODS_MILK)
                .unlockedByAnyIngredient(VDItems.BLACK_MUSHROOM.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                .build(output, itemLocationCooking(VDItems.BLACK_MUSHROOM_NOODLES.get()));

        CookingPotRecipeBuilder.cookingPotRecipe(VDItems.GARLIC_SOUP.get(), 1, NORMAL_COOKING, MEDIUM_EXP, Items.BREAD)
                .addIngredient(VDCommonTags.FOODS_RAW_CHICKEN)
                .addIngredient(VDCommonTags.CROPS_GARLIC)
                .addIngredient(Tags.Items.FOODS_VEGETABLE)
                .unlockedByAnyIngredient(VDItems.ROASTED_GARLIC.get(), ModBlocks.GARLIC.asItem())
                .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                .build(output, itemLocationCooking(VDItems.GARLIC_SOUP.get()));

        CookingPotRecipeBuilder.cookingPotRecipe(VDItems.BORSCHT.get(), 1, NORMAL_COOKING, MEDIUM_EXP)
                .addIngredient(Ingredient.fromValues(Stream.of(
                        new Ingredient.TagValue(VDCommonTags.FOODS_RAW_PORK),
                        new Ingredient.ItemValue(new ItemStack(Items.BEEF)),
                        new Ingredient.TagValue(VDCommonTags.FOODS_RAW_CHICKEN)
                )))
                .addIngredient(Ingredient.fromValues(Stream.of(
                        new Ingredient.ItemValue(new ItemStack(Items.POTATO)),
                        new Ingredient.ItemValue(new ItemStack(Items.CARROT))
                )))
                .addIngredient(Items.BEETROOT)
                .addIngredient(VDCommonTags.CROPS_GARLIC)
                .unlockedByAnyIngredient(VDItems.ROASTED_GARLIC.get(), ModBlocks.GARLIC.asItem())
                .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                .build(output, itemLocationCooking(VDItems.BORSCHT.get()));
    }

    // Temporary
    private static void fermentingAlternatives(RecipeOutput output) {
        CookingPotRecipeBuilder.cookingPotRecipe(VDItems.DANDELION_BEER_BOTTLE.get(), 1, SLOW_FERMENTING, LARGE_EXP, Items.GLASS_BOTTLE)
                .addIngredient(Items.WHEAT)
                .addIngredient(Items.WHEAT)
                .addIngredient(Items.WHEAT)
                .addIngredient(Items.DANDELION)
                .addIngredient(Items.SUGAR)
                .unlockedByAnyIngredient(Items.WHEAT, Items.DANDELION)
                .setRecipeBookTab(CookingPotRecipeBookTab.DRINKS)
                .build(output, itemLocationCooking(VDItems.DANDELION_BEER_BOTTLE.get()));

        CookingPotRecipeBuilder.cookingPotRecipe(VDItems.BLOOD_WINE_BOTTLE.get(), 1, SLOW_FERMENTING, LARGE_EXP, Items.GLASS_BOTTLE)
                .addIngredient(VDItems.BLOOD_SYRUP.get())
                .addIngredient(VDItems.BLOOD_SYRUP.get())
                .addIngredient(VDItems.BLOOD_SYRUP.get())
                .addIngredient(Items.SWEET_BERRIES)
                .addIngredient(Items.SUGAR)
                .unlockedByAnyIngredient(VDItems.BLOOD_SYRUP.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.DRINKS)
                .build(output, itemLocationCooking(VDItems.BLOOD_WINE_BOTTLE.get()));
    }

    private static String itemLocationCooking(Item item) {
        return ResourceLocation.fromNamespaceAndPath(VampiresDelight.MODID, "cooking/" + itemName(item)).getPath();
    }

    private static String blockLocationCooking(Block block) {
        return ResourceLocation.fromNamespaceAndPath(VampiresDelight.MODID, "cooking/" + blockName(block)).getPath();
    }
}
