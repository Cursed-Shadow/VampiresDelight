package net.grid.vampiresdelight.data.recipe;

import de.teamlapen.vampirism.core.ModBlocks;
import de.teamlapen.vampirism.core.ModTags;
import net.grid.vampiresdelight.common.registry.*;
import net.grid.vampiresdelight.common.tag.VDCommonTags;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.grid.vampiresdelight.VampiresDelight;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;
import vectorwing.farmersdelight.common.tag.ForgeTags;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
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
    }

    private static void recipesBlocks(Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, VDBlocks.DARK_STONE_STOVE.get())
                .pattern("iii")
                .pattern("d d")
                .pattern("dCd")
                .define('i', Tags.Items.INGOTS_IRON)
                .define('d', ModBlocks.DARK_STONE_BRICKS.get())
                .define('C', Blocks.CAMPFIRE)
                .unlockedBy(hasBlock(ModBlocks.DARK_STONE_BRICKS.get()), has(ModBlocks.DARK_STONE_BRICKS.get()))
                .unlockedBy(hasBlock(Blocks.CAMPFIRE), has(Blocks.CAMPFIRE))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, VDBlocks.DARK_SPRUCE_CABINET.get())
                .pattern("___")
                .pattern("D D")
                .pattern("___")
                .define('_', ModBlocks.DARK_SPRUCE_SLAB.get())
                .define('D', ModBlocks.DARK_SPRUCE_TRAPDOOR.get())
                .unlockedBy(hasBlock(ModBlocks.DARK_SPRUCE_TRAPDOOR.get()), has(ModBlocks.DARK_SPRUCE_TRAPDOOR.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, VDBlocks.CURSED_SPRUCE_CABINET.get())
                .pattern("___")
                .pattern("D D")
                .pattern("___")
                .define('_', ModBlocks.CURSED_SPRUCE_SLAB.get())
                .define('D', ModBlocks.CURSED_SPRUCE_TRAPDOOR.get())
                .unlockedBy(hasBlock(ModBlocks.CURSED_SPRUCE_TRAPDOOR.get()), has(ModBlocks.CURSED_SPRUCE_TRAPDOOR.get()))
                .save(consumer);

        wineShelfRecipe(VDBlocks.OAK_WINE_SHELF.get(), Blocks.OAK_SLAB, Blocks.OAK_PLANKS, consumer);
        wineShelfRecipe(VDBlocks.SPRUCE_WINE_SHELF.get(), Blocks.SPRUCE_SLAB, Blocks.SPRUCE_PLANKS, consumer);
        wineShelfRecipe(VDBlocks.BIRCH_WINE_SHELF.get(), Blocks.BIRCH_SLAB, Blocks.BIRCH_PLANKS, consumer);
        wineShelfRecipe(VDBlocks.JUNGLE_WINE_SHELF.get(), Blocks.JUNGLE_SLAB, Blocks.JUNGLE_PLANKS, consumer);
        wineShelfRecipe(VDBlocks.ACACIA_WINE_SHELF.get(), Blocks.ACACIA_SLAB, Blocks.ACACIA_PLANKS, consumer);
        wineShelfRecipe(VDBlocks.DARK_OAK_WINE_SHELF.get(), Blocks.DARK_OAK_SLAB, Blocks.DARK_OAK_PLANKS, consumer);
        wineShelfRecipe(VDBlocks.MANGROVE_WINE_SHELF.get(), Blocks.MANGROVE_SLAB, Blocks.MANGROVE_PLANKS, consumer);
        wineShelfRecipe(VDBlocks.CHERRY_WINE_SHELF.get(), Blocks.CHERRY_SLAB, Blocks.CHERRY_PLANKS, consumer);
        wineShelfRecipe(VDBlocks.BAMBOO_WINE_SHELF.get(), Blocks.BAMBOO_SLAB, Blocks.BAMBOO_PLANKS, consumer);
        wineShelfRecipe(VDBlocks.CRIMSON_WINE_SHELF.get(), Blocks.CRIMSON_SLAB, Blocks.CRIMSON_PLANKS, consumer);
        wineShelfRecipe(VDBlocks.WARPED_WINE_SHELF.get(), Blocks.WARPED_SLAB, Blocks.WARPED_PLANKS, consumer);
        wineShelfRecipe(VDBlocks.CURSED_SPRUCE_WINE_SHELF.get(), ModBlocks.CURSED_SPRUCE_SLAB.get(), ModBlocks.CURSED_SPRUCE_PLANKS.get(), consumer);
        wineShelfRecipe(VDBlocks.DARK_SPRUCE_WINE_SHELF.get(), ModBlocks.DARK_SPRUCE_SLAB.get(), ModBlocks.DARK_SPRUCE_PLANKS.get(), consumer);
        // Werewolves wine shelves have to be made manually

        barStoolRecipe(VDBlocks.WHITE_BAR_STOOL.get(), Blocks.WHITE_WOOL, consumer);
        barStoolRecipe(VDBlocks.ORANGE_BAR_STOOL.get(), Blocks.ORANGE_WOOL, consumer);
        barStoolRecipe(VDBlocks.MAGENTA_BAR_STOOL.get(), Blocks.MAGENTA_WOOL, consumer);
        barStoolRecipe(VDBlocks.LIGHT_BLUE_BAR_STOOL.get(), Blocks.LIGHT_BLUE_WOOL, consumer);
        barStoolRecipe(VDBlocks.YELLOW_BAR_STOOL.get(), Blocks.YELLOW_WOOL, consumer);
        barStoolRecipe(VDBlocks.LIME_BAR_STOOL.get(), Blocks.LIME_WOOL, consumer);
        barStoolRecipe(VDBlocks.PINK_BAR_STOOL.get(), Blocks.PINK_WOOL, consumer);
        barStoolRecipe(VDBlocks.GRAY_BAR_STOOL.get(), Blocks.GRAY_WOOL, consumer);
        barStoolRecipe(VDBlocks.LIGHT_GRAY_BAR_STOOL.get(), Blocks.LIGHT_GRAY_WOOL, consumer);
        barStoolRecipe(VDBlocks.CYAN_BAR_STOOL.get(), Blocks.CYAN_WOOL, consumer);
        barStoolRecipe(VDBlocks.PURPLE_BAR_STOOL.get(), Blocks.PURPLE_WOOL, consumer);
        barStoolRecipe(VDBlocks.BLUE_BAR_STOOL.get(), Blocks.BLUE_WOOL, consumer);
        barStoolRecipe(VDBlocks.BROWN_BAR_STOOL.get(), Blocks.BROWN_WOOL, consumer);
        barStoolRecipe(VDBlocks.GREEN_BAR_STOOL.get(), Blocks.GREEN_WOOL, consumer);
        barStoolRecipe(VDBlocks.RED_BAR_STOOL.get(), Blocks.RED_WOOL, consumer);
        barStoolRecipe(VDBlocks.BLACK_BAR_STOOL.get(), Blocks.BLACK_WOOL, consumer);

        colorBlockWithDye(List.of(VDItems.BLACK_BAR_STOOL.get(), VDItems.BLUE_BAR_STOOL.get(), VDItems.BROWN_BAR_STOOL.get(), VDItems.CYAN_BAR_STOOL.get(), VDItems.GRAY_BAR_STOOL.get(), VDItems.GREEN_BAR_STOOL.get(), VDItems.LIGHT_BLUE_BAR_STOOL.get(), VDItems.LIGHT_GRAY_BAR_STOOL.get(), VDItems.LIME_BAR_STOOL.get(), VDItems.MAGENTA_BAR_STOOL.get(), VDItems.ORANGE_BAR_STOOL.get(), VDItems.PINK_BAR_STOOL.get(), VDItems.PURPLE_BAR_STOOL.get(), VDItems.RED_BAR_STOOL.get(), VDItems.YELLOW_BAR_STOOL.get(), VDItems.WHITE_BAR_STOOL.get()), consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, VDItems.GARLIC_CRATE.get(), 1)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', de.teamlapen.vampirism.core.ModItems.ITEM_GARLIC.get())
                .unlockedBy("has_garlic", has(de.teamlapen.vampirism.core.ModItems.ITEM_GARLIC.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, VDItems.ORCHID_BAG.get(), 1)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', VDItems.ORCHID_PETALS.get())
                .unlockedBy(hasItem(VDItems.ORCHID_PETALS.get()), has(VDItems.ORCHID_PETALS.get()))
                .save(consumer);
    }

    private static void recipesMaterials(Consumer<FinishedRecipe> consumer) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, de.teamlapen.vampirism.core.ModItems.ITEM_GARLIC.get(), 9)
                .requires(VDItems.GARLIC_CRATE.get())
                .unlockedBy(hasItem(VDItems.GARLIC_CRATE.get()), has(VDItems.GARLIC_CRATE.get()))
                .save(consumer, new ResourceLocation(VampiresDelight.MODID, "garlic_from_crate"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.ORCHID_PETALS.get(), 9)
                .requires(VDItems.ORCHID_BAG.get())
                .unlockedBy(hasItem(VDItems.ORCHID_BAG.get()), has(VDItems.ORCHID_BAG.get()))
                .save(consumer, new ResourceLocation(VampiresDelight.MODID, "orchid_petals_from_bag"));
    }

    private static void recipesFoodstuffs(Consumer<FinishedRecipe> consumer) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.RICE_DOUGH.get(), 3)
                .requires(Items.WATER_BUCKET)
                .requires(ForgeTags.CROPS_RICE)
                .requires(ForgeTags.CROPS_RICE)
                .requires(ForgeTags.CROPS_RICE)
                .unlockedBy("has_rice", has(ForgeTags.CROPS_RICE))
                .save(consumer, new ResourceLocation(VampiresDelight.MODID, "rice_dough_from_water"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.RICE_DOUGH.get(), 3)
                .requires(ForgeTags.EGGS)
                .requires(ForgeTags.CROPS_RICE)
                .requires(ForgeTags.CROPS_RICE)
                .requires(ForgeTags.CROPS_RICE)
                .unlockedBy("has_rice", has(ForgeTags.CROPS_RICE))
                .save(consumer, new ResourceLocation(VampiresDelight.MODID, "rice_dough_from_eggs"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.BLOOD_DOUGH.get(), 1)
                .requires(VDItems.BLOOD_SYRUP.get())
                .requires(VDItems.RICE_DOUGH.get())
                .unlockedBy(hasItem(VDItems.BLOOD_SYRUP.get()), has(VDItems.BLOOD_SYRUP.get()))
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
                .unlockedBy("has_pure_blood", has(ModTags.Items.PURE_BLOOD))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.SUGARED_BERRIES.get(), 1)
                .requires(Items.SWEET_BERRIES)
                .requires(Items.SUGAR)
                .unlockedBy(hasItem(Items.SWEET_BERRIES), has(Items.SWEET_BERRIES))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.ORCHID_COOKIE.get(), 8)
                .requires(VDItems.ORCHID_PETALS.get())
                .requires(Items.WHEAT)
                .requires(Items.WHEAT)
                .unlockedBy(hasItem(VDItems.ORCHID_PETALS.get()), has(VDItems.ORCHID_PETALS.get()))
                .save(consumer, new ResourceLocation(VampiresDelight.MODID, "orchid_cookie_from_wheat"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.ORCHID_COOKIE.get(), 8)
                .requires(VDItems.ORCHID_PETALS.get())
                .requires(ForgeTags.CROPS_RICE)
                .requires(ForgeTags.CROPS_RICE)
                .unlockedBy(hasItem(VDItems.ORCHID_PETALS.get()), has(VDItems.ORCHID_PETALS.get()))
                .save(consumer, new ResourceLocation(VampiresDelight.MODID, "orchid_cookie_from_rice"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.CURSED_CUPCAKE.get())
                .requires(VDItems.BLOOD_BAGEL.get())
                .requires(ForgeTags.MILK)
                .requires(Items.SUGAR)
                .unlockedBy(hasItem(VDItems.BLOOD_BAGEL.get()), has(VDItems.BLOOD_BAGEL.get()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.ORCHID_ECLAIR.get(), 1)
                .requires(VDItems.ORCHID_PETALS.get())
                .requires(ForgeTags.BREAD)
                .requires(Items.SWEET_BERRIES)
                .unlockedBy(hasItem(VDItems.ORCHID_PETALS.get()), has(VDItems.ORCHID_PETALS.get()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.ORCHID_ICE_CREAM.get(), 1)
                .requires(VDItems.ORCHID_PETALS.get())
                .requires(VDItems.ORCHID_PETALS.get())
                .requires(ForgeTags.MILK)
                .requires(Items.ICE)
                .requires(Items.SUGAR)
                .requires(Items.BOWL)
                .unlockedBy(hasItem(VDItems.ORCHID_PETALS.get()), has(VDItems.ORCHID_PETALS.get()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.SNOW_WHITE_ICE_CREAM.get(), 1)
                .requires(ModTags.Items.HOLY_WATER)
                .requires(ForgeTags.MILK)
                .requires(Items.COCOA_BEANS)
                .requires(Items.ICE)
                .requires(Items.SUGAR)
                .unlockedBy("has_holy_water", has(ModTags.Items.HOLY_WATER))
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
                .unlockedBy(hasItem(VDItems.BLOOD_SYRUP.get()), has(VDItems.BLOOD_SYRUP.get()))
                .save(consumer);
    }

    private static void recipesPouring(Consumer<FinishedRecipe> consumer) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.DANDELION_BEER_MUG.get(), 1)
                .requires(VDItems.DANDELION_BEER_BOTTLE.get())
                .requires(Items.GLASS_BOTTLE)
                .unlockedBy(hasItem(VDItems.DANDELION_BEER_BOTTLE.get()), has(VDItems.DANDELION_BEER_BOTTLE.get()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.BLOOD_WINE_GLASS.get(), 1)
                .requires(VDItems.BLOOD_WINE_BOTTLE.get())
                .requires(Items.GLASS_BOTTLE)
                .unlockedBy(hasItem(VDItems.BLOOD_WINE_BOTTLE.get()), has(VDItems.BLOOD_WINE_BOTTLE.get()))
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
                .unlockedBy(hasItem(VDItems.BLOOD_SYRUP.get()), has(VDItems.BLOOD_SYRUP.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, VDItems.BLOOD_PIE.get(), 1)
                .pattern("##")
                .pattern("##")
                .define('#', VDItems.BLOOD_PIE_SLICE.get())
                .unlockedBy(hasItem(VDItems.BLOOD_PIE_SLICE.get()), has(VDItems.BLOOD_PIE_SLICE.get()))
                .save(consumer, new ResourceLocation(VampiresDelight.MODID, "blood_pie_from_slices"));
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, VDItems.ORCHID_CAKE.get(), 1)
                .pattern("mmm")
                .pattern("oso")
                .pattern("www")
                .define('m', ForgeTags.MILK)
                .define('o', VDItems.ORCHID_PETALS.get())
                .define('s', Items.SUGAR)
                .define('w', ForgeTags.GRAIN_WHEAT)
                .unlockedBy(hasItem(VDItems.ORCHID_PETALS.get()), has(VDItems.ORCHID_PETALS.get()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.ORCHID_CAKE.get(), 1)
                .requires(VDItems.ORCHID_CAKE_SLICE.get())
                .requires(VDItems.ORCHID_CAKE_SLICE.get())
                .requires(VDItems.ORCHID_CAKE_SLICE.get())
                .requires(VDItems.ORCHID_CAKE_SLICE.get())
                .requires(VDItems.ORCHID_CAKE_SLICE.get())
                .requires(VDItems.ORCHID_CAKE_SLICE.get())
                .requires(VDItems.ORCHID_CAKE_SLICE.get())
                .unlockedBy(hasItem(VDItems.ORCHID_CAKE_SLICE.get()), has(VDItems.ORCHID_CAKE_SLICE.get()))
                .save(consumer, new ResourceLocation(VampiresDelight.MODID, "orchid_cake_from_slices"));
    }

    private static void recipesCraftedMeals(Consumer<FinishedRecipe> consumer) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.FISH_BURGER.get())
                .requires(VDCommonTags.BREAD_RICE)
                .requires(ForgeTags.COOKED_FISHES)
                .requires(ForgeTags.SALAD_INGREDIENTS)
                .requires(VDCommonTags.VEGETABLES_GARLIC)
                .unlockedBy("has_garlic", has(VDCommonTags.VEGETABLES_GARLIC))
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
                .unlockedBy(hasItem(VDItems.BLOOD_SYRUP.get()), has(VDItems.BLOOD_SYRUP.get()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.BLOOD_HOT_DOG.get())
                .requires(VDItems.BLOOD_SAUSAGE.get())
                .requires(ForgeTags.BREAD)
                .unlockedBy(hasItem(VDItems.BLOOD_SAUSAGE.get()), has(VDItems.BLOOD_SAUSAGE.get()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.BAGEL_SANDWICH.get())
                .requires(VDItems.BLOOD_BAGEL.get())
                .requires(ForgeTags.COOKED_BACON)
                .requires(vectorwing.farmersdelight.common.registry.ModItems.FRIED_EGG.get())
                .unlockedBy(hasItem(VDItems.BLOOD_BAGEL.get()), has(VDItems.BLOOD_BAGEL.get()))
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
                .unlockedBy(hasItem(VDItems.HUMAN_EYE.get()), has(VDItems.HUMAN_EYE.get()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.EYE_CROISSANT.get())
                .requires(ForgeTags.BREAD)
                .requires(VDItems.HUMAN_EYE.get())
                .requires(VDItems.HUMAN_EYE.get())
                .requires(ForgeTags.SALAD_INGREDIENTS)
                .requires(ForgeTags.CROPS_TOMATO)
                .unlockedBy(hasItem(VDItems.HUMAN_EYE.get()), has(VDItems.HUMAN_EYE.get()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.BAT_TACO.get())
                .requires(ForgeTags.BREAD)
                .requires(VDCommonTags.COOKED_BAT)
                .requires(ForgeTags.SALAD_INGREDIENTS)
                .requires(ForgeTags.CROPS_TOMATO)
                .unlockedBy("has_cooked_bat", has(VDCommonTags.COOKED_BAT))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.HARDTACK.get())
                .requires(Items.WHEAT)
                .requires(Items.WHEAT)
                .requires(Items.WHEAT)
                .requires(Items.WHEAT)
                .unlockedBy(hasItem(Items.WHEAT), has(Items.WHEAT))
                .save(consumer);
    }

    private static void wineShelfRecipe(Block wineShelfBlock, Block slabBlock, Block planksBlock, Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, wineShelfBlock)
                .pattern("SSS")
                .pattern(" P ")
                .pattern("SSS")
                .define('S', slabBlock)
                .define('P', planksBlock)
                .unlockedBy(hasBlock(slabBlock), has(slabBlock))
                .save(consumer);
    }

    private static void barStoolRecipe(Block barStoolBlock, Block woolBlock, Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, barStoolBlock)
                .pattern("W")
                .pattern("F")
                .pattern("F")
                .define('F', ItemTags.WOODEN_FENCES)
                .define('W', woolBlock)
                .unlockedBy(hasBlock(woolBlock), has(woolBlock))
                .save(consumer);
    }

    private static void colorBlockWithDye(List<Item> pDyeableItems, Consumer<FinishedRecipe> consumer) {
        for (int i = 0; i < DYES.size(); ++i) {
            Item dyeItem = DYES.get(i);
            Item dyeableItem = pDyeableItems.get(i);
            ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, dyeableItem)
                    .requires(dyeItem)
                    .requires(Ingredient.of(pDyeableItems.stream().filter(item -> !item.equals(dyeableItem)).map(ItemStack::new)))
                    .unlockedBy("has_needed_dye", has(dyeItem))
                    .save(consumer, new ResourceLocation(VampiresDelight.MODID, "dye_" + itemName(dyeableItem)));
        }
    }

    public static final List<Item> DYES = List.of(Items.BLACK_DYE, Items.BLUE_DYE, Items.BROWN_DYE, Items.CYAN_DYE, Items.GRAY_DYE, Items.GREEN_DYE, Items.LIGHT_BLUE_DYE, Items.LIGHT_GRAY_DYE, Items.LIME_DYE, Items.MAGENTA_DYE, Items.ORANGE_DYE, Items.PINK_DYE, Items.PURPLE_DYE, Items.RED_DYE, Items.YELLOW_DYE, Items.WHITE_DYE);

    private static InventoryChangeTrigger.TriggerInstance has(ItemLike pItemLike) {
        return inventoryTrigger(ItemPredicate.Builder.item().of(pItemLike).build());
    }

    private static InventoryChangeTrigger.TriggerInstance has(TagKey<Item> pTag) {
        return inventoryTrigger(ItemPredicate.Builder.item().of(pTag).build());
    }

    private static InventoryChangeTrigger.TriggerInstance inventoryTrigger(ItemPredicate... pPredicates) {
        return new InventoryChangeTrigger.TriggerInstance(ContextAwarePredicate.ANY, MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, pPredicates);
    }

    private static String itemName(Item item) {
        return ForgeRegistries.ITEMS.getKey(item).getPath();
    }

    private static String blockName(Block block) {
        return ForgeRegistries.BLOCKS.getKey(block).getPath();
    }

    private static String hasItem(Item item) {
        return "has_" + itemName(item);
    }

    private static String hasBlock(Block block) {
        return "has_" + blockName(block);
    }
}
