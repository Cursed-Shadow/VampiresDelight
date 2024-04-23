package net.grid.vampiresdelight.data.recipe;

import de.teamlapen.vampirism.core.ModBlocks;
import de.teamlapen.vampirism.core.ModItems;
import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.registry.VDItems;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.registries.ForgeRegistries;
import vectorwing.farmersdelight.common.crafting.ingredient.ToolActionIngredient;
import vectorwing.farmersdelight.common.tag.ForgeTags;
import vectorwing.farmersdelight.data.builder.CuttingBoardRecipeBuilder;

import java.util.Objects;
import java.util.function.Consumer;

public class VDCuttingRecipes {
    public static void register(Consumer<FinishedRecipe> consumer) {
        // Knife
        cuttingAnimalItems(consumer);
        cuttingFoods(consumer);
        cuttingFlowers(consumer);

        // Pickaxe
        salvagingMinerals(consumer);

        // Axe
        strippingWood(consumer);
        salvagingWoodenFurniture(consumer);

        // Shears
        salvagingUsingShears(consumer);
    }

    private static void cuttingAnimalItems(Consumer<FinishedRecipe> consumer) {
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(ModItems.HUMAN_HEART.get()), Ingredient.of(ForgeTags.TOOLS_KNIVES), VDItems.HEART_PIECES.get(), 2)
                .build(consumer, itemLocationCutting(ModItems.HUMAN_HEART.get()));
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(VDItems.RAW_BAT.get()), Ingredient.of(ForgeTags.TOOLS_KNIVES), VDItems.RAW_BAT_CHOPS.get(), 2)
                .build(consumer, itemLocationCutting(VDItems.RAW_BAT.get()));
    }

    private static void cuttingFoods(Consumer<FinishedRecipe> consumer) {
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(VDItems.BLOOD_PIE.get()), Ingredient.of(ForgeTags.TOOLS_KNIVES), VDItems.BLOOD_PIE_SLICE.get(), 4)
                .build(consumer, itemLocationCutting(VDItems.BLOOD_PIE.get()));
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(VDItems.ORCHID_CAKE.get()), Ingredient.of(ForgeTags.TOOLS_KNIVES), VDItems.ORCHID_CAKE_SLICE.get(), 7)
                .build(consumer, itemLocationCutting(VDItems.ORCHID_CAKE.get()));
    }

    private static void cuttingFlowers(Consumer<FinishedRecipe> consumer) {
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(VDItems.WILD_GARLIC.get()), Ingredient.of(ForgeTags.TOOLS_KNIVES), ModItems.ITEM_GARLIC.get(), 1)
                .addResult(Items.LIGHT_GRAY_DYE, 2)
                .addResultWithChance(Items.GREEN_DYE, 0.25F)
                .build(consumer, itemLocationCutting(VDItems.WILD_GARLIC.get()));
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(ModBlocks.VAMPIRE_ORCHID.get()), Ingredient.of(ForgeTags.TOOLS_KNIVES), VDItems.ORCHID_PETALS.get(), 2)
                .addResultWithChance(VDItems.ORCHID_SEEDS.get(), 0.7F, 2)
                .addResultWithChance(ModBlocks.CURSED_ROOTS.get(), 0.30F, 1)
                .build(consumer, blockLocationCutting(ModBlocks.VAMPIRE_ORCHID.get()));
    }

    private static void salvagingMinerals(Consumer<FinishedRecipe> consumer) {
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(ModBlocks.DARK_STONE.get()), new ToolActionIngredient(ToolActions.PICKAXE_DIG), ModBlocks.COBBLED_DARK_STONE.get(), 1)
                .build(consumer, blockLocationCutting(ModBlocks.DARK_STONE.get()));
    }

    private static void strippingWood(Consumer<FinishedRecipe> consumer) {
        stripLogForBark(consumer, ModBlocks.CURSED_SPRUCE_LOG.get(), ModBlocks.STRIPPED_CURSED_SPRUCE_LOG.get());
        stripLogForBark(consumer, ModBlocks.DARK_SPRUCE_LOG.get(), ModBlocks.STRIPPED_DARK_SPRUCE_LOG.get());
    }

    private static void salvagingWoodenFurniture(Consumer<FinishedRecipe> consumer) {
        salvagePlankFromFurniture(consumer, ModBlocks.CURSED_SPRUCE_PLANKS.get(), ModBlocks.CURSED_SPRUCE_DOOR.get(), ModBlocks.CURSED_SPRUCE_TRAPDOOR.get(), ModBlocks.CURSED_SPRUCE_SIGN.get(), ModBlocks.CURSED_SPRUCE_HANGING_SIGN.get());
        salvagePlankFromFurniture(consumer, ModBlocks.DARK_SPRUCE_PLANKS.get(), ModBlocks.DARK_SPRUCE_DOOR.get(), ModBlocks.DARK_SPRUCE_TRAPDOOR.get(), ModBlocks.DARK_SPRUCE_SIGN.get(), ModBlocks.DARK_SPRUCE_HANGING_SIGN.get());
    }

    private static void salvagingUsingShears(Consumer<FinishedRecipe> consumer) {
        salvageLeatherArmor(consumer, 1,
                ModItems.ARMOR_OF_SWIFTNESS_HEAD_NORMAL.get(),
                ModItems.ARMOR_OF_SWIFTNESS_CHEST_NORMAL.get(),
                ModItems.ARMOR_OF_SWIFTNESS_LEGS_NORMAL.get(),
                ModItems.ARMOR_OF_SWIFTNESS_FEET_NORMAL.get()
        );

        salvageLeatherArmor(consumer, 2,
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

    private static void stripLogForBark(Consumer<FinishedRecipe> consumer, Block log, Block strippedLog) {
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(log), new ToolActionIngredient(ToolActions.AXE_STRIP), strippedLog)
                .addResult(vectorwing.farmersdelight.common.registry.ModItems.TREE_BARK.get())
                .addSound(Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getKey(SoundEvents.AXE_STRIP)).toString())
                .build(consumer, blockLocationCutting(log));
    }

    private static void salvagePlankFromFurniture(Consumer<FinishedRecipe> consumer, Block plank, Block door, Block trapdoor, Block sign, Block hangingSign) {
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(door), new ToolActionIngredient(ToolActions.AXE_DIG), plank)
                .build(consumer, blockLocationCutting(door));
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(trapdoor), new ToolActionIngredient(ToolActions.AXE_DIG), plank)
                .build(consumer, blockLocationCutting(trapdoor));
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(sign), new ToolActionIngredient(ToolActions.AXE_DIG), plank)
                .build(consumer, blockLocationCutting(sign));
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(hangingSign), new ToolActionIngredient(ToolActions.AXE_DIG), plank)
                .build(consumer, blockLocationCutting(hangingSign));
    }

    private static void salvageLeatherArmor(Consumer<FinishedRecipe> consumer, int leatherAmount, Item... items) {
        for (Item item : items) {
            CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(item), Ingredient.of(Tags.Items.SHEARS), Items.LEATHER, leatherAmount)
                    .build(consumer, itemLocationCutting(item));
        }
    }

    private static ResourceLocation itemLocationCutting(Item item) {
        return new ResourceLocation(VampiresDelight.MODID + ":cutting/" + Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item)).getPath());
    }
    private static ResourceLocation blockLocationCutting(Block block) {
        return new ResourceLocation(VampiresDelight.MODID + ":cutting/" + Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block)).getPath());
    }
}
