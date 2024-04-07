package net.grid.vampiresdelight.data.recipe;

import de.teamlapen.vampirism.core.ModBlocks;
import de.teamlapen.vampirism.core.ModItems;
import de.teamlapen.vampirism.core.ModTags;
import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.client.recipebook.BrewingBarrelRecipeBookTab;
import net.grid.vampiresdelight.common.registry.VDItems;
import net.grid.vampiresdelight.common.tag.VDForgeTags;
import net.grid.vampiresdelight.data.builder.BrewingBarrelRecipeBuilder;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;
import vectorwing.farmersdelight.client.recipebook.CookingPotRecipeBookTab;
import vectorwing.farmersdelight.common.tag.ForgeTags;
import vectorwing.farmersdelight.data.builder.CookingPotRecipeBuilder;

import java.util.function.Consumer;
import java.util.stream.Stream;

public class VDCookingRecipes {
    public static final int FAST_COOKING = 100;      // 5 seconds
    public static final int NORMAL_COOKING = 200;    // 10 seconds
    public static final int SLOW_FERMENTING = 1200;  // 60 seconds

    public static final float SMALL_EXP = 0.35F;
    public static final float MEDIUM_EXP = 1.0F;
    public static final float LARGE_EXP = 2.0F;

    public static void register(Consumer<FinishedRecipe> consumer) {
        // Cooking
        cookMiscellaneous(consumer);
        cookMeals(consumer);
        fermentingAlternatives(consumer);
        // Fermenting
        fermenting(consumer);
    }

    private static void cookMiscellaneous(Consumer<FinishedRecipe> consumer) {
        CookingPotRecipeBuilder.cookingPotRecipe(VDItems.ORCHID_TEA.get(), 1, NORMAL_COOKING, MEDIUM_EXP)
                .addIngredient(ForgeTags.MILK)
                .addIngredient(VDItems.ORCHID_PETALS.get())
                .addIngredient(VDItems.ORCHID_PETALS.get())
                .unlockedByAnyIngredient(VDItems.ORCHID_PETALS.get(), ModBlocks.VAMPIRE_ORCHID.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.DRINKS)
                .build(consumer, itemLocationCooking(VDItems.ORCHID_TEA.get()));
        CookingPotRecipeBuilder.cookingPotRecipe(VDItems.WEIRD_JELLY_BLOCK.get(), 1, NORMAL_COOKING, MEDIUM_EXP)
                .addIngredient(Items.BONE)
                .addIngredient(Items.SLIME_BALL)
                .addIngredient(Items.SLIME_BALL)
                .addIngredient(Items.SWEET_BERRIES)
                .addIngredient(Items.SWEET_BERRIES)
                .addIngredient(ModTags.Items.PURE_BLOOD)
                .unlockedByAnyIngredient(ModItems.PURE_BLOOD_0.get(), ModItems.PURE_BLOOD_1.get(), ModItems.PURE_BLOOD_2.get(), ModItems.PURE_BLOOD_3.get(), ModItems.PURE_BLOOD_4.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                .build(consumer, itemLocationCooking(VDItems.WEIRD_JELLY_BLOCK.get()));
        CookingPotRecipeBuilder.cookingPotRecipe(VDItems.TRICOLOR_DANGO.get(), 1, NORMAL_COOKING, MEDIUM_EXP, Items.STICK)
                .addIngredient(VDForgeTags.DOUGH_RICE)
                .addIngredient(VDItems.BLOOD_SYRUP.get())
                .addIngredient(VDItems.ORCHID_PETALS.get())
                .addIngredient(Items.SUGAR)
                .unlockedByAnyIngredient(VDItems.ORCHID_PETALS.get(), VDItems.BLOOD_SYRUP.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.MISC)
                .build(consumer, itemLocationCooking(VDItems.TRICOLOR_DANGO.get()));
        CookingPotRecipeBuilder.cookingPotRecipe(VDItems.MULLED_WINE_GLASS.get(), 1, NORMAL_COOKING, MEDIUM_EXP)
                .addIngredient(VDItems.WINE_GLASS.get())
                .addIngredient(Ingredient.fromValues(Stream.of(
                        new Ingredient.ItemValue(new ItemStack(ModBlocks.CURSED_ROOTS.get())),
                        new Ingredient.ItemValue(new ItemStack(ModBlocks.CURSED_SPRUCE_SAPLING.get())),
                        new Ingredient.ItemValue(new ItemStack(ModBlocks.DARK_SPRUCE_SAPLING.get())),
                        new Ingredient.ItemValue(new ItemStack(ModBlocks.DARK_SPRUCE_LEAVES.get()))
                )))
                .addIngredient(Items.SUGAR)
                .unlockedByAnyIngredient(VDItems.WINE_GLASS.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.DRINKS)
                .build(consumer, itemLocationCooking(VDItems.MULLED_WINE_GLASS.get()));
    }

    private static void cookMeals(Consumer<FinishedRecipe> consumer) {
        CookingPotRecipeBuilder.cookingPotRecipe(VDItems.BLACK_MUSHROOM_SOUP.get(), 1, NORMAL_COOKING, MEDIUM_EXP)
                .addIngredient(VDItems.BLACK_MUSHROOM.get())
                .addIngredient(Items.POTATO)
                .addIngredient(Items.CARROT)
                .addIngredient(ForgeTags.VEGETABLES_ONION)
                .unlockedByAnyIngredient(VDItems.BLACK_MUSHROOM.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                .build(consumer, itemLocationCooking(VDItems.BLACK_MUSHROOM_SOUP.get()));
        CookingPotRecipeBuilder.cookingPotRecipe(VDItems.GARLIC_SOUP.get(), 1, NORMAL_COOKING, MEDIUM_EXP, Items.BREAD)
                .addIngredient(ForgeTags.RAW_CHICKEN)
                .addIngredient(VDForgeTags.VEGETABLES_GARLIC)
                .addIngredient(ForgeTags.VEGETABLES)
                .unlockedByAnyIngredient(VDItems.GRILLED_GARLIC.get(), ModItems.ITEM_GARLIC.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                .build(consumer, itemLocationCooking(VDItems.GARLIC_SOUP.get()));
        CookingPotRecipeBuilder.cookingPotRecipe(VDItems.BORSCHT.get(), 1, NORMAL_COOKING, MEDIUM_EXP)
                .addIngredient(Ingredient.fromValues(Stream.of(
                        new Ingredient.TagValue(ForgeTags.RAW_PORK),
                        new Ingredient.ItemValue(new ItemStack(Items.BEEF)),
                        new Ingredient.TagValue(ForgeTags.RAW_CHICKEN)
                )))
                .addIngredient(Ingredient.fromValues(Stream.of(
                        new Ingredient.TagValue(ForgeTags.VEGETABLES_POTATO),
                        new Ingredient.TagValue(ForgeTags.VEGETABLES_CARROT)
                )))
                .addIngredient(ForgeTags.VEGETABLES_BEETROOT)
                .addIngredient(VDForgeTags.VEGETABLES_GARLIC)
                .unlockedByAnyIngredient(VDItems.GRILLED_GARLIC.get(), ModItems.ITEM_GARLIC.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                .build(consumer, itemLocationCooking(VDItems.BORSCHT.get()));
    }

    // Temporary
    private static void fermentingAlternatives(Consumer<FinishedRecipe> consumer) {
        CookingPotRecipeBuilder.cookingPotRecipe(VDItems.BLOOD_WINE_BOTTLE.get(), 1, SLOW_FERMENTING, LARGE_EXP, Items.GLASS_BOTTLE)
                .addIngredient(VDItems.BLOOD_SYRUP.get())
                .addIngredient(VDItems.BLOOD_SYRUP.get())
                .addIngredient(VDItems.BLOOD_SYRUP.get())
                .addIngredient(Items.SWEET_BERRIES)
                .addIngredient(Items.SUGAR)
                .unlockedByAnyIngredient(VDItems.BLOOD_SYRUP.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.DRINKS)
                .build(consumer, itemLocationCooking(VDItems.BLOOD_WINE_BOTTLE.get()));
    }

    private static void fermenting(Consumer<FinishedRecipe> consumer) {
        BrewingBarrelRecipeBuilder.brewingBarrelRecipe(VDItems.BLOOD_WINE_BOTTLE.get(), 1, SLOW_FERMENTING, LARGE_EXP)
                .addIngredient(ModItems.BLOOD_BOTTLE.get())
                .addIngredient(ModItems.BLOOD_BOTTLE.get())
                .addIngredient(ModItems.BLOOD_BOTTLE.get())
                .addIngredient(Items.SUGAR)
                .unlockedByAnyIngredient(ModItems.BLOOD_BOTTLE.get())
                .setRecipeBookTab(BrewingBarrelRecipeBookTab.DRINKS)
                .build(consumer);
    }

    public static ResourceLocation itemLocationCooking(Item item) {
        return new ResourceLocation(VampiresDelight.MODID + ":cooking/" + ForgeRegistries.ITEMS.getKey(item).getPath());
    }
    public static ResourceLocation blockLocationCooking(Block block) {
        return new ResourceLocation(VampiresDelight.MODID + ":cooking/" + ForgeRegistries.BLOCKS.getKey(block).getPath());
    }
}
