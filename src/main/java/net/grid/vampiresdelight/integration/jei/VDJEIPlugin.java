package net.grid.vampiresdelight.integration.jei;

import de.teamlapen.vampirism.core.ModItems;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.registry.VDItems;
import net.grid.vampiresdelight.common.utility.VDTextUtils;
import net.grid.vampiresdelight.integration.jei.category.VDJEIPouringRecipeCategory;
import net.grid.vampiresdelight.integration.jei.resource.VDJEIPouringRecipe;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;

@JeiPlugin
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@SuppressWarnings("unused")
public class VDJEIPlugin implements IModPlugin {
    private static final ResourceLocation ID = new ResourceLocation(VampiresDelight.MODID, "jei_plugin");

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        registry.addRecipeCategories(new VDJEIPouringRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        VDJEIPouringRecipe[] recipes = new VDJEIPouringRecipe[] {
                new VDJEIPouringRecipe(new ItemStack(VDItems.BLOOD_WINE_BOTTLE.get()), new ItemStack(VDItems.WINE_GLASS.get()), new ItemStack(Items.GLASS_BOTTLE))
        };
        registration.addRecipes(VDJEIRecipeTypes.POURING, new ArrayList<>(Arrays.asList(recipes)));

        registerSingleIngredientInfo(VDItems.HUMAN_EYE.get(), registration);
        registerSingleIngredientInfo(VDItems.BLACK_MUSHROOM.get(), registration);
        registerMultipleIngredientInfo(List.of(new ItemStack(VDItems.WILD_GARLIC.get()), new ItemStack(ModItems.ITEM_GARLIC.get())), registration);
    }

    private static void registerSingleIngredientInfo(Item item, IRecipeRegistration registration) {
        registration.addIngredientInfo(new ItemStack(item), VanillaTypes.ITEM_STACK, VDTextUtils.getTranslation("jei.info." + ForgeRegistries.ITEMS.getKey(item).getPath()));
    }

    /**
     * ID of the first component in list is used for the translation.
     */
    private static void registerMultipleIngredientInfo(List<ItemStack> items, IRecipeRegistration registration) {
        registration.addIngredientInfo(items, VanillaTypes.ITEM_STACK, VDTextUtils.getTranslation("jei.info." + ForgeRegistries.ITEMS.getKey(items.get(0).getItem()).getPath()));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(VDItems.DARK_STONE_STOVE.get()), RecipeTypes.CAMPFIRE_COOKING);
        registration.addRecipeCatalyst(new ItemStack(VDItems.BLOOD_WINE_BOTTLE.get()), VDJEIRecipeTypes.POURING);
    }

    @Override
    public ResourceLocation getPluginUid() {
        return ID;
    }
}
