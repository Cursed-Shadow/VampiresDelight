package net.grid.vampiresdelight.data.recipe;

import de.teamlapen.vampirism.core.ModBlocks;
import de.teamlapen.vampirism.core.ModItems;
import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.registry.VDItems;
import net.grid.vampiresdelight.common.tag.VDCommonTags;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.Tags;
import vectorwing.farmersdelight.common.crafting.ingredient.ItemAbilityIngredient;
import vectorwing.farmersdelight.data.builder.CuttingBoardRecipeBuilder;

import static net.grid.vampiresdelight.common.utility.VDNameUtils.blockName;
import static net.grid.vampiresdelight.common.utility.VDNameUtils.itemName;

public class VDCuttingRecipes {
    public static void register(RecipeOutput output) {
        // Knife
        cuttingAnimalItems(output);
        cuttingFoods(output);
        cuttingFlowers(output);

        // Pickaxe
        salvagingMinerals(output);

        // Axe
        strippingWood(output);
        salvagingWoodenFurniture(output);

        // Shears
        salvagingUsingShears(output);
    }

    private static void cuttingAnimalItems(RecipeOutput output) {
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(ModItems.HUMAN_HEART.get()), Ingredient.of(VDCommonTags.TOOLS_KNIFE), VDItems.HEART_PIECES.get(), 2)
                .build(output, itemLocationCutting(ModItems.HUMAN_HEART.get()));
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(VDItems.RAW_BAT.get()), Ingredient.of(VDCommonTags.TOOLS_KNIFE), VDItems.RAW_BAT_CHOPS.get(), 2)
                .build(output, itemLocationCutting(VDItems.RAW_BAT.get()));
    }

    private static void cuttingFoods(RecipeOutput output) {
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(VDItems.BLOOD_PIE.get()), Ingredient.of(VDCommonTags.TOOLS_KNIFE), VDItems.BLOOD_PIE_SLICE.get(), 4)
                .build(output, itemLocationCutting(VDItems.BLOOD_PIE.get()));
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(VDItems.ORCHID_CAKE.get()), Ingredient.of(VDCommonTags.TOOLS_KNIFE), VDItems.ORCHID_CAKE_SLICE.get(), 7)
                .build(output, itemLocationCutting(VDItems.ORCHID_CAKE.get()));
    }

    private static void cuttingFlowers(RecipeOutput output) {
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(VDItems.WILD_GARLIC.get()), Ingredient.of(VDCommonTags.TOOLS_KNIFE), ModBlocks.GARLIC.asItem(), 1)
                .addResult(Items.LIGHT_GRAY_DYE, 2)
                .addResultWithChance(Items.GREEN_DYE, 0.25F)
                .build(output, itemLocationCutting(VDItems.WILD_GARLIC.get()));
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(ModBlocks.VAMPIRE_ORCHID.get()), Ingredient.of(VDCommonTags.TOOLS_KNIFE), VDItems.ORCHID_PETALS.get(), 2)
                .addResultWithChance(VDItems.ORCHID_SEEDS.get(), 0.7F, 2)
                .addResultWithChance(ModBlocks.CURSED_ROOTS.get(), 0.30F, 1)
                .build(output, blockLocationCutting(ModBlocks.VAMPIRE_ORCHID.get()));
    }

    private static void salvagingMinerals(RecipeOutput output) {
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(ModBlocks.DARK_STONE.get()), new ItemAbilityIngredient(ItemAbilities.PICKAXE_DIG).toVanilla(), ModBlocks.COBBLED_DARK_STONE.get(), 1)
                .build(output, blockLocationCutting(ModBlocks.DARK_STONE.get()));
    }

    private static void strippingWood(RecipeOutput output) {
        stripLogForBark(output, ModBlocks.CURSED_SPRUCE_LOG.get(), ModBlocks.STRIPPED_CURSED_SPRUCE_LOG.get());
        stripLogForBark(output, ModBlocks.DARK_SPRUCE_LOG.get(), ModBlocks.STRIPPED_DARK_SPRUCE_LOG.get());
    }

    private static void salvagingWoodenFurniture(RecipeOutput output) {
        salvagePlankFromFurniture(output, ModBlocks.CURSED_SPRUCE_PLANKS.get(), ModBlocks.CURSED_SPRUCE_DOOR.get(), ModBlocks.CURSED_SPRUCE_TRAPDOOR.get(), ModBlocks.CURSED_SPRUCE_SIGN.get(), ModBlocks.CURSED_SPRUCE_HANGING_SIGN.get());
        salvagePlankFromFurniture(output, ModBlocks.DARK_SPRUCE_PLANKS.get(), ModBlocks.DARK_SPRUCE_DOOR.get(), ModBlocks.DARK_SPRUCE_TRAPDOOR.get(), ModBlocks.DARK_SPRUCE_SIGN.get(), ModBlocks.DARK_SPRUCE_HANGING_SIGN.get());
    }

    private static void salvagingUsingShears(RecipeOutput output) {
        salvageLeatherArmor(output, 1,
                ModItems.ARMOR_OF_SWIFTNESS_HEAD_NORMAL.get(),
                ModItems.ARMOR_OF_SWIFTNESS_CHEST_NORMAL.get(),
                ModItems.ARMOR_OF_SWIFTNESS_LEGS_NORMAL.get(),
                ModItems.ARMOR_OF_SWIFTNESS_FEET_NORMAL.get()
        );

        salvageLeatherArmor(output, 2,
                ModItems.ARMOR_OF_SWIFTNESS_HEAD_ENHANCED.get(),
                ModItems.ARMOR_OF_SWIFTNESS_CHEST_ENHANCED.get(),
                ModItems.ARMOR_OF_SWIFTNESS_LEGS_ENHANCED.get(),
                ModItems.ARMOR_OF_SWIFTNESS_FEET_ENHANCED.get(),

                ModItems.ARMOR_OF_SWIFTNESS_HEAD_ULTIMATE.get(),
                ModItems.ARMOR_OF_SWIFTNESS_CHEST_ULTIMATE.get(),
                ModItems.ARMOR_OF_SWIFTNESS_LEGS_ULTIMATE.get(),
                ModItems.ARMOR_OF_SWIFTNESS_FEET_ULTIMATE.get()
        );
    }

    private static void stripLogForBark(RecipeOutput output, Block log, Block strippedLog) {
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(log), new ItemAbilityIngredient(ItemAbilities.AXE_STRIP).toVanilla(), strippedLog)
                .addResult(vectorwing.farmersdelight.common.registry.ModItems.TREE_BARK.get())
                .addSound(SoundEvents.AXE_STRIP)
                .build(output, blockLocationCutting(log));
    }

    private static void salvagePlankFromFurniture(RecipeOutput output, Block plank, Block door, Block trapdoor, Block sign, Block hangingSign) {
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(door), new ItemAbilityIngredient(ItemAbilities.AXE_DIG).toVanilla(), plank)
                .build(output, blockLocationCutting(door));
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(trapdoor), new ItemAbilityIngredient(ItemAbilities.AXE_DIG).toVanilla(), plank)
                .build(output, blockLocationCutting(trapdoor));
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(sign), new ItemAbilityIngredient(ItemAbilities.AXE_DIG).toVanilla(), plank)
                .build(output, blockLocationCutting(sign));
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(hangingSign), new ItemAbilityIngredient(ItemAbilities.AXE_DIG).toVanilla(), plank)
                .build(output, blockLocationCutting(hangingSign));
    }

    private static void salvageLeatherArmor(RecipeOutput output, int leatherAmount, Item... items) {
        for (Item item : items) {
            CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(item), Ingredient.of(Tags.Items.TOOLS_SHEAR), Items.LEATHER, leatherAmount)
                    .build(output, itemLocationCutting(item));
        }
    }

    private static String itemLocationCutting(Item item) {
        return ResourceLocation.fromNamespaceAndPath(VampiresDelight.MODID, "cutting/" + itemName(item)).getPath();
    }

    private static String blockLocationCutting(Block block) {
        return ResourceLocation.fromNamespaceAndPath(VampiresDelight.MODID, "cutting/" + blockName(block)).getPath();
    }
}
