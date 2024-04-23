package net.grid.vampiresdelight.data.recipe;

import de.teamlapen.vampirism.core.ModItems;
import de.teamlapen.vampirism.data.recipebuilder.AlchemyTableRecipeBuilder;
import de.teamlapen.vampirism.data.recipebuilder.ShapedWeaponTableRecipeBuilder;
import de.teamlapen.vampirism.data.recipebuilder.ShapelessWeaponTableRecipeBuilder;
import de.teamlapen.vampirism.util.NBTIngredient;
import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.registry.VDBlocks;
import net.grid.vampiresdelight.common.registry.VDItems;
import net.grid.vampiresdelight.common.registry.VDOils;
import net.grid.vampiresdelight.common.registry.VDPotions;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.function.Consumer;

public class VDVampirismRecipes {
    public static void register(Consumer<FinishedRecipe> consumer) {
        recipesWeaponTable(consumer);
        recipesAlchemyTable(consumer);
    }

    private static void recipesWeaponTable(Consumer<FinishedRecipe> consumer) {
        ShapelessWeaponTableRecipeBuilder.shapelessWeaponTable(RecipeCategory.COMBAT, VDItems.ALCHEMICAL_COCKTAIL.get())
                .requires(Items.GLASS_BOTTLE)
                .requires(ModItems.ITEM_ALCHEMICAL_FIRE.get(), 2)
                .unlockedBy("has_alchemical_fire", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.ITEM_ALCHEMICAL_FIRE.get()))
                .save(consumer);
        ShapedWeaponTableRecipeBuilder.shapedWeaponTable(RecipeCategory.COMBAT, VDBlocks.SPIRIT_LANTERN.get())
                .pattern(" gg ")
                .pattern("gssg")
                .pattern("gssg")
                .pattern(" gg ")
                .define('g', Items.GOLD_NUGGET)
                .define('s', ModItems.SOUL_ORB_VAMPIRE.get())
                .unlockedBy("has_vampire_soul", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.SOUL_ORB_VAMPIRE.get()))
                .save(consumer);
    }

    private static void recipesAlchemyTable(Consumer<FinishedRecipe> consumer) {
        AlchemyTableRecipeBuilder
                .builder(VDOils.BLESSING)
                .bloodOilIngredient()
                .input(potion(VDPotions.BLESSING.get(), VDPotions.STRONG_BLESSING.get(), VDPotions.LONG_BLESSING.get()))
                .build(consumer, new ResourceLocation(VampiresDelight.MODID, "blessing_oil"));
        AlchemyTableRecipeBuilder
                .builder(VDOils.CLOTHES_DISSOLVING)
                .bloodOilIngredient()
                .input(potion(VDPotions.CLOTHES_DISSOLVING.get()))
                .build(consumer, new ResourceLocation(VampiresDelight.MODID, "clothes_dissolving_oil"));
        AlchemyTableRecipeBuilder
                .builder(VDOils.FOG_VISION)
                .bloodOilIngredient()
                .input(potion(VDPotions.FOG_VISION.get(), VDPotions.STRONG_FOG_VISION.get(), VDPotions.LONG_FOG_VISION.get()))
                .build(consumer, new ResourceLocation(VampiresDelight.MODID, "fog_vision_oil"));
    }

    private static @NotNull Ingredient potion(Potion @NotNull ... potion) {
        return new NBTIngredient(Arrays.stream(potion).map(p -> PotionUtils.setPotion(new ItemStack(Items.POTION, 1), p)).toArray(ItemStack[]::new));
    }
}
