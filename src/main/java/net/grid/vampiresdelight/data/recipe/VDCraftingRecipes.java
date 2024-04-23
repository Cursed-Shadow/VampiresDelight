package net.grid.vampiresdelight.data.recipe;

import de.teamlapen.vampirism.core.ModBlocks;
import de.teamlapen.vampirism.core.ModItems;
import de.teamlapen.vampirism.core.ModTags;
import net.grid.vampiresdelight.common.registry.*;
import net.grid.vampiresdelight.common.tag.VDForgeTags;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.grid.vampiresdelight.VampiresDelight;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import vectorwing.farmersdelight.common.tag.ForgeTags;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Consumer;
import java.util.stream.Stream;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class VDCraftingRecipes {
    public static void register(Consumer<FinishedRecipe> consumer) {
        recipesBlocks(consumer);
        recipesMaterials(consumer);
        recipesFoodstuffs(consumer);
        recipesPouring(consumer);
        recipesFoodBlocks(consumer);
        recipesCraftedMeals(consumer);

        SpecialRecipeBuilder.special(VDRecipeSerializers.BARREL_POURING.get()).save(consumer, "barrel_pouring");
    }

    // Crafting
    private static void recipesBlocks(Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, VDBlocks.DARK_STONE_STOVE.get())
                .pattern("iii")
                .pattern("d d")
                .pattern("dCd")
                .define('i', Tags.Items.INGOTS_IRON)
                .define('d', ModBlocks.DARK_STONE_BRICKS.get())
                .define('C', Blocks.CAMPFIRE)
                .unlockedBy("has_dark_stone_bricks", InventoryChangeTrigger.TriggerInstance.hasItems(ModBlocks.DARK_STONE_BRICKS.get()))
                .unlockedBy("has_campfire", InventoryChangeTrigger.TriggerInstance.hasItems(Blocks.CAMPFIRE))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, VDBlocks.DARK_SPRUCE_CABINET.get())
                .pattern("___")
                .pattern("D D")
                .pattern("___")
                .define('_', ModBlocks.DARK_SPRUCE_SLAB.get())
                .define('D', ModBlocks.DARK_SPRUCE_TRAPDOOR.get())
                .unlockedBy("has_dark_spruce_trapdoor", InventoryChangeTrigger.TriggerInstance.hasItems(ModBlocks.DARK_SPRUCE_TRAPDOOR.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, VDBlocks.CURSED_SPRUCE_CABINET.get())
                .pattern("___")
                .pattern("D D")
                .pattern("___")
                .define('_', ModBlocks.CURSED_SPRUCE_SLAB.get())
                .define('D', ModBlocks.CURSED_SPRUCE_TRAPDOOR.get())
                .unlockedBy("has_cursed_spruce_trapdoor", InventoryChangeTrigger.TriggerInstance.hasItems(ModBlocks.CURSED_SPRUCE_TRAPDOOR.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, VDBlocks.OAK_WINE_SHELF.get())
                .pattern("SSS")
                .pattern(" P ")
                .pattern("SSS")
                .define('S', Blocks.OAK_SLAB)
                .define('P', Blocks.OAK_PLANKS)
                .unlockedBy("has_oak_slab", InventoryChangeTrigger.TriggerInstance.hasItems(Blocks.OAK_SLAB))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, VDBlocks.SPRUCE_WINE_SHELF.get())
                .pattern("SSS")
                .pattern(" P ")
                .pattern("SSS")
                .define('S', Blocks.SPRUCE_SLAB)
                .define('P', Blocks.SPRUCE_PLANKS)
                .unlockedBy("has_spruce_slab", InventoryChangeTrigger.TriggerInstance.hasItems(Blocks.SPRUCE_SLAB))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, VDBlocks.BIRCH_WINE_SHELF.get())
                .pattern("SSS")
                .pattern(" P ")
                .pattern("SSS")
                .define('S', Blocks.BIRCH_SLAB)
                .define('P', Blocks.BIRCH_PLANKS)
                .unlockedBy("has_birch_slab", InventoryChangeTrigger.TriggerInstance.hasItems(Blocks.BIRCH_SLAB))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, VDBlocks.JUNGLE_WINE_SHELF.get())
                .pattern("SSS")
                .pattern(" P ")
                .pattern("SSS")
                .define('S', Blocks.JUNGLE_SLAB)
                .define('P', Blocks.JUNGLE_PLANKS)
                .unlockedBy("has_jungle_slab", InventoryChangeTrigger.TriggerInstance.hasItems(Blocks.JUNGLE_SLAB))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, VDBlocks.ACACIA_WINE_SHELF.get())
                .pattern("SSS")
                .pattern(" P ")
                .pattern("SSS")
                .define('S', Blocks.ACACIA_SLAB)
                .define('P', Blocks.ACACIA_PLANKS)
                .unlockedBy("has_acacia_slab", InventoryChangeTrigger.TriggerInstance.hasItems(Blocks.ACACIA_SLAB))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, VDBlocks.DARK_OAK_WINE_SHELF.get())
                .pattern("SSS")
                .pattern(" P ")
                .pattern("SSS")
                .define('S', Blocks.DARK_OAK_SLAB)
                .define('P', Blocks.DARK_OAK_PLANKS)
                .unlockedBy("has_dark_oak_slab", InventoryChangeTrigger.TriggerInstance.hasItems(Blocks.DARK_OAK_SLAB))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, VDBlocks.MANGROVE_WINE_SHELF.get())
                .pattern("SSS")
                .pattern(" P ")
                .pattern("SSS")
                .define('S', Blocks.MANGROVE_SLAB)
                .define('P', Blocks.MANGROVE_PLANKS)
                .unlockedBy("has_mangrove_slab", InventoryChangeTrigger.TriggerInstance.hasItems(Blocks.MANGROVE_SLAB))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, VDBlocks.CHERRY_WINE_SHELF.get())
                .pattern("SSS")
                .pattern(" P ")
                .pattern("SSS")
                .define('S', Blocks.CHERRY_SLAB)
                .define('P', Blocks.CHERRY_PLANKS)
                .unlockedBy("has_cherry_slab", InventoryChangeTrigger.TriggerInstance.hasItems(Blocks.CHERRY_SLAB))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, VDBlocks.BAMBOO_WINE_SHELF.get())
                .pattern("SSS")
                .pattern(" P ")
                .pattern("SSS")
                .define('S', Blocks.BAMBOO_SLAB)
                .define('P', Blocks.BAMBOO_PLANKS)
                .unlockedBy("has_bamboo_slab", InventoryChangeTrigger.TriggerInstance.hasItems(Blocks.BAMBOO_SLAB))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, VDBlocks.CRIMSON_WINE_SHELF.get())
                .pattern("SSS")
                .pattern(" P ")
                .pattern("SSS")
                .define('S', Blocks.CRIMSON_SLAB)
                .define('P', Blocks.CRIMSON_PLANKS)
                .unlockedBy("has_crimson_slab", InventoryChangeTrigger.TriggerInstance.hasItems(Blocks.CRIMSON_SLAB))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, VDBlocks.WARPED_WINE_SHELF.get())
                .pattern("SSS")
                .pattern(" P ")
                .pattern("SSS")
                .define('S', Blocks.WARPED_SLAB)
                .define('P', Blocks.WARPED_PLANKS)
                .unlockedBy("has_warped_slab", InventoryChangeTrigger.TriggerInstance.hasItems(Blocks.WARPED_SLAB))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, VDBlocks.CURSED_SPRUCE_WINE_SHELF.get())
                .pattern("SSS")
                .pattern(" P ")
                .pattern("SSS")
                .define('S', ModBlocks.CURSED_SPRUCE_SLAB.get())
                .define('P', ModBlocks.CURSED_SPRUCE_PLANKS.get())
                .unlockedBy("has_cursed_spruce_slab", InventoryChangeTrigger.TriggerInstance.hasItems(ModBlocks.CURSED_SPRUCE_SLAB.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, VDBlocks.DARK_SPRUCE_WINE_SHELF.get())
                .pattern("SSS")
                .pattern(" P ")
                .pattern("SSS")
                .define('S', ModBlocks.DARK_SPRUCE_SLAB.get())
                .define('P', ModBlocks.DARK_SPRUCE_PLANKS.get())
                .unlockedBy("has_dark_spruce_slab", InventoryChangeTrigger.TriggerInstance.hasItems(ModBlocks.DARK_SPRUCE_SLAB.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, VDItems.GARLIC_CRATE.get(), 1)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', de.teamlapen.vampirism.core.ModItems.ITEM_GARLIC.get())
                .unlockedBy("has_garlic", InventoryChangeTrigger.TriggerInstance.hasItems(de.teamlapen.vampirism.core.ModItems.ITEM_GARLIC.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, VDItems.ORCHID_BAG.get(), 1)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', VDItems.ORCHID_PETALS.get())
                .unlockedBy("has_orchid_petals", InventoryChangeTrigger.TriggerInstance.hasItems(VDItems.ORCHID_PETALS.get()))
                .save(consumer);
    }

    private static void recipesMaterials(Consumer<FinishedRecipe> consumer) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, de.teamlapen.vampirism.core.ModItems.ITEM_GARLIC.get(), 9)
                .requires(VDItems.GARLIC_CRATE.get())
                .unlockedBy("has_garlic_crate", InventoryChangeTrigger.TriggerInstance.hasItems(VDItems.GARLIC_CRATE.get()))
                .save(consumer, new ResourceLocation(VampiresDelight.MODID, "garlic_from_crate"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.ORCHID_PETALS.get(), 9)
                .requires(VDItems.ORCHID_BAG.get())
                .unlockedBy("has_orchid_bag", InventoryChangeTrigger.TriggerInstance.hasItems(VDItems.ORCHID_BAG.get()))
                .save(consumer, new ResourceLocation(VampiresDelight.MODID, "orchid_petals_from_bag"));
    }

    private static void recipesFoodstuffs(Consumer<FinishedRecipe> consumer) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.RICE_DOUGH.get(), 3)
                .requires(Items.WATER_BUCKET)
                .requires(ForgeTags.CROPS_RICE)
                .requires(ForgeTags.CROPS_RICE)
                .requires(ForgeTags.CROPS_RICE)
                .unlockedBy("has_rice", InventoryChangeTrigger.TriggerInstance.hasItems(vectorwing.farmersdelight.common.registry.ModItems.RICE.get()))
                .save(consumer, new ResourceLocation(VampiresDelight.MODID, "rice_dough_from_water"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.RICE_DOUGH.get(), 3)
                .requires(ForgeTags.EGGS)
                .requires(ForgeTags.CROPS_RICE)
                .requires(ForgeTags.CROPS_RICE)
                .requires(ForgeTags.CROPS_RICE)
                .unlockedBy("has_rice", InventoryChangeTrigger.TriggerInstance.hasItems(vectorwing.farmersdelight.common.registry.ModItems.RICE.get()))
                .save(consumer, new ResourceLocation(VampiresDelight.MODID, "rice_dough_from_eggs"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.BLOOD_DOUGH.get(), 1)
                .requires(VDItems.BLOOD_SYRUP.get())
                .requires(VDItems.RICE_DOUGH.get())
                .unlockedBy("has_blood_syrup", InventoryChangeTrigger.TriggerInstance.hasItems(VDItems.BLOOD_SYRUP.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, VDItems.PURE_SORBET.get(), 1)
                .pattern(" sa")
                .pattern("ips")
                .pattern("ti ")
                .define('p', ModTags.Items.PURE_BLOOD)
                .define('s', Items.SWEET_BERRIES)
                .define('a', Items.APPLE)
                .define('i', Items.ICE)
                .define('t', Items.STICK)
                .unlockedBy("has_pure_blood", InventoryChangeTrigger.TriggerInstance.hasItems(Items.ICE))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.SUGARED_BERRIES.get(), 1)
                .requires(Items.SWEET_BERRIES)
                .requires(Items.SUGAR)
                .unlockedBy("has_sweet_berries", InventoryChangeTrigger.TriggerInstance.hasItems(Items.SWEET_BERRIES))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.ORCHID_COOKIE.get(), 8)
                .requires(VDItems.ORCHID_PETALS.get())
                .requires(Items.WHEAT)
                .requires(Items.WHEAT)
                .unlockedBy("has_orchid_petals", InventoryChangeTrigger.TriggerInstance.hasItems(VDItems.ORCHID_PETALS.get()))
                .save(consumer, new ResourceLocation(VampiresDelight.MODID, "orchid_cookie_from_wheat"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.ORCHID_COOKIE.get(), 8)
                .requires(VDItems.ORCHID_PETALS.get())
                .requires(ForgeTags.CROPS_RICE)
                .requires(ForgeTags.CROPS_RICE)
                .unlockedBy("has_orchid_petals", InventoryChangeTrigger.TriggerInstance.hasItems(VDItems.ORCHID_PETALS.get()))
                .save(consumer, new ResourceLocation(VampiresDelight.MODID, "orchid_cookie_from_rice"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.CURSED_CUPCAKE.get())
                .requires(VDItems.BLOOD_BAGEL.get())
                .requires(ForgeTags.MILK)
                .requires(Items.SUGAR)
                .unlockedBy("has_blood_bagel", InventoryChangeTrigger.TriggerInstance.hasItems(VDItems.BLOOD_BAGEL.get()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.ORCHID_ECLAIR.get(), 1)
                .requires(VDItems.ORCHID_PETALS.get())
                .requires(ForgeTags.BREAD)
                .requires(Items.SWEET_BERRIES)
                .unlockedBy("has_orchid_petals", InventoryChangeTrigger.TriggerInstance.hasItems(VDItems.ORCHID_PETALS.get()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.ORCHID_ICE_CREAM.get(), 1)
                .requires(VDItems.ORCHID_PETALS.get())
                .requires(VDItems.ORCHID_PETALS.get())
                .requires(ForgeTags.MILK)
                .requires(Items.ICE)
                .requires(Items.SUGAR)
                .requires(Items.BOWL)
                .unlockedBy("has_orchid_petals", InventoryChangeTrigger.TriggerInstance.hasItems(VDItems.ORCHID_PETALS.get()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.SNOW_WHITE_ICE_CREAM.get(), 1)
                .requires(ModTags.Items.HOLY_WATER)
                .requires(ForgeTags.MILK)
                .requires(Items.COCOA_BEANS)
                .requires(Items.ICE)
                .requires(Items.SUGAR)
                .unlockedBy("has_holy_water", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.HOLY_WATER_BOTTLE_NORMAL.get(), ModItems.HOLY_WATER_BOTTLE_ENHANCED.get(), ModItems.HOLY_WATER_BOTTLE_ULTIMATE.get()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.DARK_ICE_CREAM.get(), 1)
                .requires(VDItems.BLOOD_SYRUP.get())
                .requires(ForgeTags.GRAIN)
                .requires(ForgeTags.GRAIN)
                .requires(Items.ICE)
                .requires(Items.ICE)
                .requires(Ingredient.fromValues(Stream.of(
                        new Ingredient.ItemValue(new ItemStack(Items.INK_SAC)),
                        new Ingredient.ItemValue(new ItemStack(ModBlocks.CURSED_SPRUCE_SAPLING.get())),
                        new Ingredient.ItemValue(new ItemStack(ModBlocks.DARK_SPRUCE_SAPLING.get())),
                        new Ingredient.ItemValue(new ItemStack(ModBlocks.DARK_SPRUCE_LEAVES.get()))
                )))
                .unlockedBy("has_blood_syrup", InventoryChangeTrigger.TriggerInstance.hasItems(VDItems.BLOOD_SYRUP.get()))
                .save(consumer);
    }

    private static void recipesPouring(Consumer<FinishedRecipe> consumer) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.WINE_GLASS.get(), 1)
                .requires(VDItems.BLOOD_WINE_BOTTLE.get())
                .requires(Items.GLASS_BOTTLE)
                .unlockedBy("has_blood_wine_bottle", InventoryChangeTrigger.TriggerInstance.hasItems(VDItems.BLOOD_WINE_BOTTLE.get()))
                .save(consumer);
    }

    private static void recipesFoodBlocks(Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, VDItems.BLOOD_PIE.get(), 1)
                .pattern("sss")
                .pattern("wxw")
                .pattern("bOb")
                .define('w', Items.NETHER_WART)
                .define('b', VDItems.BLOOD_SYRUP.get())
                .define('x', Items.SUGAR)
                .define('s', Items.SWEET_BERRIES)
                .define('O', vectorwing.farmersdelight.common.registry.ModItems.PIE_CRUST.get())
                .unlockedBy("has_blood_syrup", InventoryChangeTrigger.TriggerInstance.hasItems(VDItems.BLOOD_SYRUP.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, VDItems.BLOOD_PIE.get(), 1)
                .pattern("##")
                .pattern("##")
                .define('#', VDItems.BLOOD_PIE_SLICE.get())
                .unlockedBy("has_blood_pie_slice", InventoryChangeTrigger.TriggerInstance.hasItems(VDItems.BLOOD_PIE_SLICE.get()))
                .save(consumer, new ResourceLocation(VampiresDelight.MODID, "blood_pie_from_slices"));
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, VDItems.ORCHID_CAKE.get(), 1)
                .pattern("mmm")
                .pattern("oso")
                .pattern("www")
                .define('m', ForgeTags.MILK)
                .define('o', VDItems.ORCHID_PETALS.get())
                .define('s', Items.SUGAR)
                .define('w', ForgeTags.GRAIN_WHEAT)
                .unlockedBy("has_orchid_petals", InventoryChangeTrigger.TriggerInstance.hasItems(VDItems.ORCHID_PETALS.get()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.ORCHID_CAKE.get(), 1)
                .requires(VDItems.ORCHID_CAKE_SLICE.get())
                .requires(VDItems.ORCHID_CAKE_SLICE.get())
                .requires(VDItems.ORCHID_CAKE_SLICE.get())
                .requires(VDItems.ORCHID_CAKE_SLICE.get())
                .requires(VDItems.ORCHID_CAKE_SLICE.get())
                .requires(VDItems.ORCHID_CAKE_SLICE.get())
                .requires(VDItems.ORCHID_CAKE_SLICE.get())
                .unlockedBy("has_orchid_cake_slice", InventoryChangeTrigger.TriggerInstance.hasItems(VDItems.ORCHID_CAKE_SLICE.get()))
                .save(consumer, new ResourceLocation(VampiresDelight.MODID, "orchid_cake_from_slices"));
    }

    private static void recipesCraftedMeals(Consumer<FinishedRecipe> consumer) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.FISH_BURGER.get())
                .requires(VDForgeTags.BREAD_RICE)
                .requires(ForgeTags.COOKED_FISHES)
                .requires(ForgeTags.SALAD_INGREDIENTS)
                .requires(VDForgeTags.VEGETABLES_GARLIC)
                .unlockedBy("has_garlic", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.ITEM_GARLIC.get()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.BLOOD_SAUSAGE.get())
                .requires(VDItems.BLOOD_SYRUP.get())
                .requires(ForgeTags.VEGETABLES_ONION)
                .requires(Ingredient.fromValues(Stream.of(
                        new Ingredient.TagValue(ForgeTags.COOKED_BEEF),
                        new Ingredient.TagValue(ForgeTags.COOKED_CHICKEN),
                        new Ingredient.TagValue(ForgeTags.COOKED_MUTTON),
                        new Ingredient.TagValue(ForgeTags.COOKED_PORK)
                )))
                .unlockedBy("has_blood_syrup", InventoryChangeTrigger.TriggerInstance.hasItems(VDItems.BLOOD_SYRUP.get()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.BLOOD_HOT_DOG.get())
                .requires(VDItems.BLOOD_SAUSAGE.get())
                .requires(ForgeTags.BREAD)
                .unlockedBy("has_blood_sausage", InventoryChangeTrigger.TriggerInstance.hasItems(VDItems.BLOOD_SAUSAGE.get()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.BAGEL_SANDWICH.get())
                .requires(VDItems.BLOOD_BAGEL.get())
                .requires(ForgeTags.COOKED_BACON)
                .requires(vectorwing.farmersdelight.common.registry.ModItems.FRIED_EGG.get())
                .unlockedBy("has_blood_bagel", InventoryChangeTrigger.TriggerInstance.hasItems(VDItems.BLOOD_BAGEL.get()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.EYES_ON_STICK.get())
                .requires(VDItems.HUMAN_EYE.get())
                .requires(VDItems.HUMAN_EYE.get())
                .requires(ForgeTags.CROPS_TOMATO)
                .requires(Ingredient.fromValues(Stream.of(
                        new Ingredient.ItemValue(new ItemStack(Items.BROWN_MUSHROOM)),
                        new Ingredient.ItemValue(new ItemStack(Items.RED_MUSHROOM))
                )))
                .requires(Items.STICK)
                .unlockedBy("has_human_eye", InventoryChangeTrigger.TriggerInstance.hasItems(VDItems.HUMAN_EYE.get()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.EYE_CROISSANT.get())
                .requires(ForgeTags.BREAD)
                .requires(VDItems.HUMAN_EYE.get())
                .requires(VDItems.HUMAN_EYE.get())
                .requires(ForgeTags.SALAD_INGREDIENTS)
                .requires(ForgeTags.CROPS_TOMATO)
                .unlockedBy("has_human_eye", InventoryChangeTrigger.TriggerInstance.hasItems(VDItems.HUMAN_EYE.get()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.BAT_TACO.get())
                .requires(ForgeTags.BREAD)
                .requires(VDForgeTags.COOKED_BAT)
                .requires(ForgeTags.SALAD_INGREDIENTS)
                .requires(ForgeTags.CROPS_TOMATO)
                .unlockedBy("has_cooked_bat", InventoryChangeTrigger.TriggerInstance.hasItems(VDItems.COOKED_BAT.get(), VDItems.COOKED_BAT_CHOPS.get()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.HARDTACK.get())
                .requires(Items.WHEAT)
                .requires(Items.WHEAT)
                .requires(Items.WHEAT)
                .requires(Items.WHEAT)
                .unlockedBy("has_wheat", InventoryChangeTrigger.TriggerInstance.hasItems(Items.WHEAT))
                .save(consumer);
    }
}
