package net.grid.vampiresdelight.integration.jei;

import de.teamlapen.vampirism.core.ModBlocks;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.crafting.WeaveLettersRecipe;
import net.grid.vampiresdelight.common.registry.VDItems;
import net.grid.vampiresdelight.common.utility.VDTextUtils;
import net.grid.vampiresdelight.integration.jei.category.VDJEIPouringRecipeCategory;
import net.grid.vampiresdelight.integration.jei.category.VDJEISpillingBloodRecipeCategory;
import net.grid.vampiresdelight.integration.jei.resource.VDJEIPouringRecipe;
import net.grid.vampiresdelight.integration.jei.resource.VDJEISpillingBloodRecipe;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import vectorwing.farmersdelight.common.registry.ModItems;
import vectorwing.farmersdelight.common.utility.TextUtils;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;

@JeiPlugin
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@SuppressWarnings("unused")
public class VDJEIPlugin implements IModPlugin {
    private static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(VampiresDelight.MODID, "jei_plugin");

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        registry.addRecipeCategories(new VDJEIPouringRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
        registry.addRecipeCategories(new VDJEISpillingBloodRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        VDJEIPouringRecipe[] pouringRecipes = new VDJEIPouringRecipe[] {
                new VDJEIPouringRecipe(new ItemStack(VDItems.BLOOD_WINE_BOTTLE.get()), new ItemStack(VDItems.BLOOD_WINE_GLASS.get()), new ItemStack(Items.GLASS_BOTTLE)),
                new VDJEIPouringRecipe(new ItemStack(VDItems.DANDELION_BEER_BOTTLE.get()), new ItemStack(VDItems.DANDELION_BEER_MUG.get()), new ItemStack(Items.GLASS_BOTTLE))
        };
        registration.addRecipes(VDJEIRecipeTypes.POURING, new ArrayList<>(Arrays.asList(pouringRecipes)));

        VDJEISpillingBloodRecipe[] spillingBloodRecipes = new VDJEISpillingBloodRecipe[] {
                new VDJEISpillingBloodRecipe(new ItemStack(ModItems.RICH_SOIL.get()), new ItemStack(VDItems.BLOODY_SOIL.get())),
                new VDJEISpillingBloodRecipe(new ItemStack(ModItems.RICH_SOIL_FARMLAND.get()), new ItemStack(VDItems.BLOODY_SOIL_FARMLAND.get()))
        };
        registration.addRecipes(VDJEIRecipeTypes.SPILLING_BLOOD, new ArrayList<>(Arrays.asList(spillingBloodRecipes)));

        registration.addRecipes(RecipeTypes.CRAFTING, List.of(new RecipeHolder<>(WeaveLettersRecipe.ID, new ShapelessRecipe("", CraftingBookCategory.MISC, de.teamlapen.vampirism.core.ModItems.VAMPIRE_BOOK.toStack(), NonNullList.of(Ingredient.EMPTY, Ingredient.of(VDItems.WEATHERED_LETTER), Ingredient.of(VDItems.WEATHERED_LETTER), Ingredient.of(VDItems.WEATHERED_LETTER))))));

        registerSingleIngredientInfo(VDItems.HUMAN_EYE.get(), registration);
        registerSingleIngredientInfo(VDItems.BLACK_MUSHROOM.get(), registration);
        registerSingleIngredientInfo(VDItems.SILVER_KNIFE.get(), TextUtils.getTranslation("jei.info.knife"), registration);
        registerMultipleIngredientInfo(List.of(new ItemStack(VDItems.WILD_GARLIC.get()), new ItemStack(ModBlocks.GARLIC.asItem())), registration);
    }

    private static void registerSingleIngredientInfo(Item item, IRecipeRegistration registration) {
        registration.addIngredientInfo(new ItemStack(item), VanillaTypes.ITEM_STACK, VDTextUtils.getTranslation("jei.info." + BuiltInRegistries.ITEM.getKey(item).getPath()));
    }

    private static void registerSingleIngredientInfo(Item item, MutableComponent translation, IRecipeRegistration registration) {
        registration.addIngredientInfo(new ItemStack(item), VanillaTypes.ITEM_STACK, translation);
    }

    /**
     * ID of the first component in list is used for the translation.
     */
    private static void registerMultipleIngredientInfo(List<ItemStack> items, IRecipeRegistration registration) {
        registration.addIngredientInfo(items, VanillaTypes.ITEM_STACK, VDTextUtils.getTranslation("jei.info." + BuiltInRegistries.ITEM.getKey(items.getFirst().getItem()).getPath()));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(VDItems.DARK_STONE_STOVE.get()), RecipeTypes.CAMPFIRE_COOKING);
        registration.addRecipeCatalyst(new ItemStack(VDItems.BLOOD_WINE_BOTTLE.get()), VDJEIRecipeTypes.POURING);
        registration.addRecipeCatalyst(new ItemStack(VDItems.DANDELION_BEER_BOTTLE.get()), VDJEIRecipeTypes.POURING);
        registration.addRecipeCatalyst(new ItemStack(de.teamlapen.vampirism.core.ModItems.PURE_BLOOD_0.get()), VDJEIRecipeTypes.SPILLING_BLOOD);
        registration.addRecipeCatalyst(new ItemStack(de.teamlapen.vampirism.core.ModItems.PURE_BLOOD_1.get()), VDJEIRecipeTypes.SPILLING_BLOOD);
        registration.addRecipeCatalyst(new ItemStack(de.teamlapen.vampirism.core.ModItems.PURE_BLOOD_2.get()), VDJEIRecipeTypes.SPILLING_BLOOD);
        registration.addRecipeCatalyst(new ItemStack(de.teamlapen.vampirism.core.ModItems.PURE_BLOOD_3.get()), VDJEIRecipeTypes.SPILLING_BLOOD);
        registration.addRecipeCatalyst(new ItemStack(de.teamlapen.vampirism.core.ModItems.PURE_BLOOD_4.get()), VDJEIRecipeTypes.SPILLING_BLOOD);
    }

    @Override
    public ResourceLocation getPluginUid() {
        return ID;
    }
}
