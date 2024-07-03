package net.grid.vampiresdelight.common.registry;

import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.food.VDFoodValues;
import net.grid.vampiresdelight.common.food.VDFoodFeatures;
import net.grid.vampiresdelight.common.item.*;
import net.grid.vampiresdelight.common.utility.VDIntegrationUtils;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import vectorwing.farmersdelight.common.FoodValues;
import vectorwing.farmersdelight.common.item.ConsumableItem;
import vectorwing.farmersdelight.common.item.FuelBlockItem;
import vectorwing.farmersdelight.common.item.KnifeItem;

public class VDItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, VampiresDelight.MODID);
    // Helper methods
    public static Item.Properties basicItem() {
        return new Item.Properties();
    }
    public static Item.Properties foodItem(FoodProperties food) {
        return new Item.Properties().food(food);
    }
    public static Item.Properties bowlFoodItem(FoodProperties food) {
        return new Item.Properties().food(food).craftRemainder(Items.BOWL).stacksTo(16);
    }
    public static Item.Properties drinkItem(FoodProperties food) {
        return new Item.Properties().food(food).craftRemainder(Items.GLASS_BOTTLE).stacksTo(16);
    }
    public static Item.Properties drinkItem() {
        return new Item.Properties().craftRemainder(Items.GLASS_BOTTLE).stacksTo(16);
    }


    // The neutral items go first, then the vampire and hunter ones

    // Blocks
    public static final RegistryObject<BlockItem> DARK_STONE_STOVE = ITEMS.register("dark_stone_stove",
            () -> new BlockItem(VDBlocks.DARK_STONE_STOVE.get(), basicItem()));
    public static final RegistryObject<BlockItem> BREWING_BARREL = ITEMS.register("brewing_barrel",
            () -> new BlockItem(VDBlocks.BREWING_BARREL.get(), basicItem()));
    public static final RegistryObject<BlockItem> GARLIC_CRATE = ITEMS.register("garlic_crate",
            () -> new BlockItem(VDBlocks.GARLIC_CRATE.get(), basicItem()));
    public static final RegistryObject<BlockItem> ORCHID_BAG = ITEMS.register("orchid_bag",
            () -> new BlockItem(VDBlocks.ORCHID_BAG.get(), basicItem()));
    public static final RegistryObject<FuelBlockItem> DARK_SPRUCE_CABINET = ITEMS.register("dark_spruce_cabinet",
            () -> new FuelBlockItem(VDBlocks.DARK_SPRUCE_CABINET.get(), basicItem(), 300));
    public static final RegistryObject<FuelBlockItem> CURSED_SPRUCE_CABINET = ITEMS.register("cursed_spruce_cabinet",
            () -> new FuelBlockItem(VDBlocks.CURSED_SPRUCE_CABINET.get(), basicItem(), 300));
    public static final RegistryObject<FuelBlockItem> JACARANDA_CABINET = ITEMS.register("jacaranda_cabinet",
            () -> new FuelBlockItem(VDBlocks.JACARANDA_CABINET.get(), basicItem(), 300));
    public static final RegistryObject<FuelBlockItem> MAGIC_CABINET = ITEMS.register("magic_cabinet",
            () -> new FuelBlockItem(VDBlocks.MAGIC_CABINET.get(), basicItem(), 300));
    public static final RegistryObject<FuelBlockItem> OAK_WINE_SHELF = ITEMS.register("oak_wine_shelf",
            () -> new FuelBlockItem(VDBlocks.OAK_WINE_SHELF.get(), basicItem(), 300));
    public static final RegistryObject<FuelBlockItem> SPRUCE_WINE_SHELF = ITEMS.register("spruce_wine_shelf",
            () -> new FuelBlockItem(VDBlocks.SPRUCE_WINE_SHELF.get(), basicItem(), 300));
    public static final RegistryObject<FuelBlockItem> BIRCH_WINE_SHELF = ITEMS.register("birch_wine_shelf",
            () -> new FuelBlockItem(VDBlocks.BIRCH_WINE_SHELF.get(), basicItem(), 300));
    public static final RegistryObject<FuelBlockItem> JUNGLE_WINE_SHELF = ITEMS.register("jungle_wine_shelf",
            () -> new FuelBlockItem(VDBlocks.JUNGLE_WINE_SHELF.get(), basicItem(), 300));
    public static final RegistryObject<FuelBlockItem> ACACIA_WINE_SHELF = ITEMS.register("acacia_wine_shelf",
            () -> new FuelBlockItem(VDBlocks.ACACIA_WINE_SHELF.get(), basicItem(), 300));
    public static final RegistryObject<FuelBlockItem> DARK_OAK_WINE_SHELF = ITEMS.register("dark_oak_wine_shelf",
            () -> new FuelBlockItem(VDBlocks.DARK_OAK_WINE_SHELF.get(), basicItem(), 300));
    public static final RegistryObject<FuelBlockItem> MANGROVE_WINE_SHELF = ITEMS.register("mangrove_wine_shelf",
            () -> new FuelBlockItem(VDBlocks.MANGROVE_WINE_SHELF.get(), basicItem(), 300));
    public static final RegistryObject<FuelBlockItem> CHERRY_WINE_SHELF = ITEMS.register("cherry_wine_shelf",
            () -> new FuelBlockItem(VDBlocks.CHERRY_WINE_SHELF.get(), basicItem(), 300));
    public static final RegistryObject<FuelBlockItem> BAMBOO_WINE_SHELF = ITEMS.register("bamboo_wine_shelf",
            () -> new FuelBlockItem(VDBlocks.BAMBOO_WINE_SHELF.get(), basicItem(), 300));
    public static final RegistryObject<BlockItem> CRIMSON_WINE_SHELF = ITEMS.register("crimson_wine_shelf",
            () -> new BlockItem(VDBlocks.CRIMSON_WINE_SHELF.get(), basicItem()));
    public static final RegistryObject<BlockItem> WARPED_WINE_SHELF = ITEMS.register("warped_wine_shelf",
            () -> new BlockItem(VDBlocks.WARPED_WINE_SHELF.get(), basicItem()));
    public static final RegistryObject<FuelBlockItem> CURSED_SPRUCE_WINE_SHELF = ITEMS.register("cursed_spruce_wine_shelf",
            () -> new FuelBlockItem(VDBlocks.CURSED_SPRUCE_WINE_SHELF.get(), basicItem(), 300));
    public static final RegistryObject<FuelBlockItem> DARK_SPRUCE_WINE_SHELF = ITEMS.register("dark_spruce_wine_shelf",
            () -> new FuelBlockItem(VDBlocks.DARK_SPRUCE_WINE_SHELF.get(), basicItem(), 300));
    public static final RegistryObject<FuelBlockItem> JACARANDA_WINE_SHELF = ITEMS.register("jacaranda_wine_shelf",
            () -> new FuelBlockItem(VDBlocks.JACARANDA_WINE_SHELF.get(), basicItem(), 300));
    public static final RegistryObject<FuelBlockItem> MAGIC_WINE_SHELF = ITEMS.register("magic_wine_shelf",
            () -> new FuelBlockItem(VDBlocks.MAGIC_WINE_SHELF.get(), basicItem(), 300));
    public static final RegistryObject<BlockItem> WHITE_BAR_STOOL = ITEMS.register("white_bar_stool",
            () -> new BlockItem(VDBlocks.WHITE_BAR_STOOL.get(), basicItem()));
    public static final RegistryObject<BlockItem> ORANGE_BAR_STOOL = ITEMS.register("orange_bar_stool",
            () -> new BlockItem(VDBlocks.ORANGE_BAR_STOOL.get(), basicItem()));
    public static final RegistryObject<BlockItem> MAGENTA_BAR_STOOL = ITEMS.register("magenta_bar_stool",
            () -> new BlockItem(VDBlocks.MAGENTA_BAR_STOOL.get(), basicItem()));
    public static final RegistryObject<BlockItem> LIGHT_BLUE_BAR_STOOL = ITEMS.register("light_blue_bar_stool",
            () -> new BlockItem(VDBlocks.LIGHT_BLUE_BAR_STOOL.get(), basicItem()));
    public static final RegistryObject<BlockItem> YELLOW_BAR_STOOL = ITEMS.register("yellow_bar_stool",
            () -> new BlockItem(VDBlocks.YELLOW_BAR_STOOL.get(), basicItem()));
    public static final RegistryObject<BlockItem> LIME_BAR_STOOL = ITEMS.register("lime_bar_stool",
            () -> new BlockItem(VDBlocks.LIME_BAR_STOOL.get(), basicItem()));
    public static final RegistryObject<BlockItem> PINK_BAR_STOOL = ITEMS.register("pink_bar_stool",
            () -> new BlockItem(VDBlocks.PINK_BAR_STOOL.get(), basicItem()));
    public static final RegistryObject<BlockItem> GRAY_BAR_STOOL = ITEMS.register("gray_bar_stool",
            () -> new BlockItem(VDBlocks.GRAY_BAR_STOOL.get(), basicItem()));
    public static final RegistryObject<BlockItem> LIGHT_GRAY_BAR_STOOL = ITEMS.register("light_gray_bar_stool",
            () -> new BlockItem(VDBlocks.LIGHT_GRAY_BAR_STOOL.get(), basicItem()));
    public static final RegistryObject<BlockItem> CYAN_BAR_STOOL = ITEMS.register("cyan_bar_stool",
            () -> new BlockItem(VDBlocks.CYAN_BAR_STOOL.get(), basicItem()));
    public static final RegistryObject<BlockItem> PURPLE_BAR_STOOL = ITEMS.register("purple_bar_stool",
            () -> new BlockItem(VDBlocks.PURPLE_BAR_STOOL.get(), basicItem()));
    public static final RegistryObject<BlockItem> BLUE_BAR_STOOL = ITEMS.register("blue_bar_stool",
            () -> new BlockItem(VDBlocks.BLUE_BAR_STOOL.get(), basicItem()));
    public static final RegistryObject<BlockItem> BROWN_BAR_STOOL = ITEMS.register("brown_bar_stool",
            () -> new BlockItem(VDBlocks.BROWN_BAR_STOOL.get(), basicItem()));
    public static final RegistryObject<BlockItem> GREEN_BAR_STOOL = ITEMS.register("green_bar_stool",
            () -> new BlockItem(VDBlocks.GREEN_BAR_STOOL.get(), basicItem()));
    public static final RegistryObject<BlockItem> RED_BAR_STOOL = ITEMS.register("red_bar_stool",
            () -> new BlockItem(VDBlocks.RED_BAR_STOOL.get(), basicItem()));
    public static final RegistryObject<BlockItem> BLACK_BAR_STOOL = ITEMS.register("black_bar_stool",
            () -> new BlockItem(VDBlocks.BLACK_BAR_STOOL.get(), basicItem()));
    public static final RegistryObject<BlockItem> WILD_GARLIC = ITEMS.register("wild_garlic",
            () -> new BlockItem(VDBlocks.WILD_GARLIC.get(), basicItem()));
    public static final RegistryObject<BlockItem> CURSED_FARMLAND = ITEMS.register("cursed_farmland",
            () -> new BlockItem(VDBlocks.CURSED_FARMLAND.get(), basicItem()));
    public static final RegistryObject<Item> BLOODY_SOIL = ITEMS.register("bloody_soil",
            () -> new BlockItem(VDBlocks.BLOODY_SOIL.get(), basicItem()));
    public static final RegistryObject<Item> BLOODY_SOIL_FARMLAND = ITEMS.register("bloody_soil_farmland",
            () -> new BlockItem(VDBlocks.BLOODY_SOIL_FARMLAND.get(), basicItem()));
    public static final RegistryObject<BlockItem> SPIRIT_LANTERN = ITEMS.register("spirit_lantern",
            () -> new BlockItem(VDBlocks.SPIRIT_LANTERN.get(), basicItem()));

    // Tools/Misc
    public static final RegistryObject<KnifeItem> SILVER_KNIFE = ITEMS.register("silver_knife",
            () -> new KnifeItem(VDIntegrationUtils.SILVER_ITEM_TIER, 0.5F, -2.0F, basicItem()));
    public static final RegistryObject<AlchemicalCocktailItem> ALCHEMICAL_COCKTAIL = ITEMS.register("alchemical_cocktail",
            () -> new AlchemicalCocktailItem(basicItem()));

    // Farming
    public static final RegistryObject<BlockItem> BLACK_MUSHROOM_BLOCK = ITEMS.register("black_mushroom_block",
            () -> new BlockItem(VDBlocks.BLACK_MUSHROOM_BLOCK.get(), basicItem()));
    public static final RegistryObject<BlockItem> BLACK_MUSHROOM_STEM = ITEMS.register("black_mushroom_stem",
            () -> new BlockItem(VDBlocks.BLACK_MUSHROOM_STEM.get(), basicItem()));
    public static final RegistryObject<BlockItem> BLACK_MUSHROOM = ITEMS.register("black_mushroom",
            () -> new BlockItem(VDBlocks.BLACK_MUSHROOM.get(), basicItem()));
    public static final RegistryObject<ItemNameBlockItem> ORCHID_SEEDS = ITEMS.register("orchid_seeds",
            () -> new ItemNameBlockItem(VDBlocks.VAMPIRE_ORCHID_CROP.get(), basicItem()));

    // Foodstuffs
    public static final RegistryObject<HunterConsumableItem> GRILLED_GARLIC = ITEMS.register("grilled_garlic",
            () -> new HunterConsumableItem(foodItem(VDFoodValues.GRILLED_GARLIC)));
    public static final RegistryObject<DaisyTeaItem> DAISY_TEA = ITEMS.register("daisy_tea",
            () -> new DaisyTeaItem(drinkItem()));
    public static final RegistryObject<VampireDrinkableItem> BLOOD_SYRUP = ITEMS.register("blood_syrup",
            () -> new VampireDrinkableItem(drinkItem(VDFoodValues.NASTY), VDFoodValues.BLOOD_SYRUP));
    public static final RegistryObject<OrchidTeaItem> ORCHID_TEA = ITEMS.register("orchid_tea",
            OrchidTeaItem::new);
    public static final RegistryObject<Item> ORCHID_PETALS = ITEMS.register("orchid_petals",
            () -> new Item(basicItem()));
    public static final RegistryObject<ConsumableItem> SUGARED_BERRIES = ITEMS.register("sugared_berries",
            () -> new ConsumableItem(foodItem(VDFoodValues.SUGARED_BERRIES)));
    public static final RegistryObject<VampireConsumableItem> HEART_PIECES = ITEMS.register("heart_pieces",
            () -> new VampireConsumableItem(foodItem(VDFoodValues.NASTY), VDFoodValues.HEART_PIECES));
    public static final RegistryObject<VampireConsumableItem> HUMAN_EYE = ITEMS.register("human_eye",
            () -> new VampireConsumableItem(foodItem(VDFoodValues.NASTY), VDFoodValues.HUMAN_EYE));
    public static final RegistryObject<Item> RICE_DOUGH = ITEMS.register("rice_dough",
            () -> new Item(foodItem(VDFoodValues.RICE_DOUGH)));
    public static final RegistryObject<Item> RICE_BREAD = ITEMS.register("rice_bread",
            () -> new Item(foodItem(VDFoodValues.RICE_BREAD)));
    public static final RegistryObject<VampireConsumableItem> BLOOD_DOUGH = ITEMS.register("blood_dough",
            () -> new VampireConsumableItem(foodItem(VDFoodValues.NASTY_BLOOD_DOUGH), VDFoodValues.BLOOD_DOUGH, false));
    public static final RegistryObject<VampireConsumableItem> BLOOD_BAGEL = ITEMS.register("blood_bagel",
            () -> new VampireConsumableItem(foodItem(VDFoodValues.NASTY), VDFoodValues.BLOOD_BAGEL));
    public static final RegistryObject<VampireConsumableItem> RAW_BAT = ITEMS.register("raw_bat",
            () -> new VampireConsumableItem(foodItem(VDFoodValues.RAW_BAT), VDFoodValues.RAW_BAT, true, false, false, false));
    public static final RegistryObject<VampireConsumableItem> RAW_BAT_CHOPS = ITEMS.register("raw_bat_chops",
            () -> new VampireConsumableItem(foodItem(VDFoodValues.RAW_BAT_CHOPS), VDFoodValues.RAW_BAT_CHOPS, true, false, false, false));
    public static final RegistryObject<VampireConsumableItem> COOKED_BAT = ITEMS.register("cooked_bat",
            () -> new VampireConsumableItem(foodItem(VDFoodValues.GRILLED_BAT_HUMAN), VDFoodValues.GRILLED_BAT_VAMPIRE, true, false, false, false));
    public static final RegistryObject<VampireConsumableItem> COOKED_BAT_CHOPS = ITEMS.register("cooked_bat_chops",
            () -> new VampireConsumableItem(foodItem(VDFoodValues.GRILLED_BAT_CHOPS_HUMAN), VDFoodValues.GRILLED_BAT_CHOPS_VAMPIRE, true, false, false, false));
    public static final RegistryObject<VampireDrinkableItem> WINE_GLASS = ITEMS.register("wine_glass",
            () -> new VampireDrinkableItem(drinkItem(VDFoodValues.WINE_GLASS_HUMAN), VDFoodValues.WINE_GLASS_VAMPIRE));
    public static final RegistryObject<PourableBottleItem> BLOOD_WINE_BOTTLE = ITEMS.register("blood_wine_bottle",
            () -> new PourableBottleItem(basicItem(), 3, VDItems.WINE_GLASS.get(), Items.GLASS_BOTTLE));
    public static final RegistryObject<VampireDrinkableItem> MULLED_WINE_GLASS = ITEMS.register("mulled_wine_glass",
            () -> new VampireDrinkableItem(drinkItem(VDFoodValues.MULLED_WINE_GLASS_HUMAN), VDFoodValues.MULLED_WINE_GLASS_VAMPIRE));
    public static final RegistryObject<BlockItem> BLOOD_PIE = ITEMS.register("blood_pie",
            () -> new BlockItem(VDBlocks.BLOOD_PIE.get(), basicItem()));
    public static final RegistryObject<VampireConsumableItem> BLOOD_PIE_SLICE = ITEMS.register("blood_pie_slice",
            () -> new VampireConsumableItem(foodItem(VDFoodValues.NASTY), VDFoodValues.BLOOD_PIE_SLICE, false));

    // Sweets
    public static final RegistryObject<VampireConsumableItem> PURE_SORBET = ITEMS.register("pure_sorbet",
            () -> new VampireConsumableItem(foodItem(VDFoodValues.PURE_SORBET), VDFoodValues.PURE_SORBET, VDFoodFeatures.ICE_CREAM, true, false, true, false));
    public static final RegistryObject<VampireConsumableItem> ORCHID_COOKIE = ITEMS.register("orchid_cookie",
            () -> new VampireConsumableItem(foodItem(VDFoodValues.NASTY_BLINDNESS), VDFoodValues.ORCHID_COOKIE));
    public static final RegistryObject<VampireConsumableItem> ORCHID_ECLAIR = ITEMS.register("orchid_eclair",
            () -> new VampireConsumableItem(foodItem(VDFoodValues.NASTY_BLINDNESS), VDFoodValues.ORCHID_ECLAIR));
    public static final RegistryObject<VampireConsumableItem> ORCHID_ICE_CREAM = ITEMS.register("orchid_ice_cream",
            () -> new VampireConsumableItem(bowlFoodItem(VDFoodValues.NASTY_BLINDNESS), VDFoodValues.ORCHID_ICE_CREAM, VDFoodFeatures.ICE_CREAM));
    public static final RegistryObject<VampireConsumableItem> TRICOLOR_DANGO = ITEMS.register("tricolor_dango",
            () -> new VampireConsumableItem(foodItem(VDFoodValues.NASTY), VDFoodValues.TRICOLOR_DANGO));
    public static final RegistryObject<VampireConsumableItem> CURSED_CUPCAKE = ITEMS.register("cursed_cupcake",
            () -> new VampireConsumableItem(foodItem(VDFoodValues.NASTY), VDFoodValues.CURSED_CUPCAKE, VDFoodFeatures.CURSED_CUPCAKE, true, true, false, true));
    public static final RegistryObject<VampireConsumableItem> DARK_ICE_CREAM = ITEMS.register("dark_ice_cream",
            () -> new VampireConsumableItem(foodItem(VDFoodValues.NASTY_DARKNESS), VDFoodValues.DARK_ICE_CREAM, VDFoodFeatures.ICE_CREAM));
    public static final RegistryObject<BlockItem> ORCHID_CAKE = ITEMS.register("orchid_cake",
            () -> new BlockItem(VDBlocks.ORCHID_CAKE.get(), basicItem().stacksTo(1)));
    public static final RegistryObject<VampireConsumableItem> ORCHID_CAKE_SLICE = ITEMS.register("orchid_cake_slice",
            () -> new VampireConsumableItem(foodItem(VDFoodValues.NASTY_BLINDNESS), VDFoodValues.ORCHID_CAKE_SLICE, false));
    public static final RegistryObject<HunterConsumableItem> SNOW_WHITE_ICE_CREAM = ITEMS.register("snow_white_ice_cream",
            () -> new HunterConsumableItem(foodItem(VDFoodValues.SNOW_WHITE_ICE_CREAM), VDFoodFeatures.ICE_CREAM, true));
    public static final RegistryObject<WerewolfConsumableItem> WOLF_BERRY_COOKIE = ITEMS.register("wolf_berry_cookie",
            () -> new WerewolfConsumableItem(foodItem(VDFoodValues.NASTY_POISON), FoodValues.COOKIES));
    public static final RegistryObject<WerewolfConsumableItem> WOLF_BERRY_ICE_CREAM = ITEMS.register("wolf_berry_ice_cream",
            () -> new WerewolfConsumableItem(bowlFoodItem(VDFoodValues.NASTY_POISON), VDFoodValues.WOLF_BERRY_ICE_CREAM, VDFoodFeatures.ICE_CREAM));

    // Basic Meals
    public static final RegistryObject<HunterConsumableItem> FISH_BURGER = ITEMS.register("fish_burger",
            () -> new HunterConsumableItem(foodItem(VDFoodValues.FISH_BURGER)));
    public static final RegistryObject<VampireConsumableItem> BLOOD_SAUSAGE = ITEMS.register("blood_sausage",
            () -> new VampireConsumableItem(foodItem(VDFoodValues.NASTY), VDFoodValues.BLOOD_SAUSAGE));
    public static final RegistryObject<VampireConsumableItem> BLOOD_HOT_DOG = ITEMS.register("blood_hot_dog",
            () -> new VampireConsumableItem(foodItem(VDFoodValues.NASTY), VDFoodValues.BLOOD_HOT_DOG));
    public static final RegistryObject<VampireConsumableItem> EYES_ON_STICK = ITEMS.register("eyes_on_stick",
            () -> new VampireConsumableItem(foodItem(VDFoodValues.NASTY), VDFoodValues.EYES_ON_STICK));
    public static final RegistryObject<VampireConsumableItem> EYE_CROISSANT = ITEMS.register("eye_croissant",
            () -> new VampireConsumableItem(foodItem(VDFoodValues.NASTY), VDFoodValues.EYE_CROISSANT));
    public static final RegistryObject<VampireConsumableItem> BAGEL_SANDWICH = ITEMS.register("bagel_sandwich",
            () -> new VampireConsumableItem(foodItem(VDFoodValues.NASTY), VDFoodValues.BAGEL_SANDWICH));
    public static final RegistryObject<VampireConsumableItem> BAT_TACO = ITEMS.register("bat_taco",
            () -> new VampireConsumableItem(foodItem(VDFoodValues.BAT_TACO_HUMAN), VDFoodValues.BAT_TACO, true, false, false, false));
    public static final RegistryObject<HunterConsumableItem> HARDTACK = ITEMS.register("hardtack",
            () -> new HunterConsumableItem(foodItem(VDFoodValues.HARDTACK_HUMAN), VDFoodValues.HARDTACK_HUNTER, VDFoodFeatures.HARDTACK, false, false, false));

    // Soups and Stews
    public static final RegistryObject<VampireConsumableItem> ORCHID_CREAM_SOUP = ITEMS.register("orchid_cream_soup",
            () -> new VampireConsumableItem(bowlFoodItem(VDFoodValues.NASTY_DARKNESS), VDFoodValues.ORCHID_CREAM_SOUP));
    public static final RegistryObject<VampireConsumableItem> BLACK_MUSHROOM_SOUP = ITEMS.register("black_mushroom_soup",
            () -> new VampireConsumableItem(bowlFoodItem(VDFoodValues.NASTY_DARKNESS), VDFoodValues.BLACK_MUSHROOM_SOUP));
    public static final RegistryObject<HunterConsumableItem> GARLIC_SOUP = ITEMS.register("garlic_soup",
            () -> new HunterConsumableItem(foodItem(VDFoodValues.GARLIC_SOUP), true));
    public static final RegistryObject<HunterConsumableItem> BORSCHT = ITEMS.register("borscht",
            () -> new HunterConsumableItem(bowlFoodItem(VDFoodValues.BORSCHT), true));

    // Plated Meals
    public static final RegistryObject<VampireConsumableItem> ORCHID_CURRY = ITEMS.register("orchid_curry",
            () -> new VampireConsumableItem(bowlFoodItem(VDFoodValues.NASTY_BLINDNESS), VDFoodValues.ORCHID_CURRY));

    // Feasts
    public static final RegistryObject<BlockItem> WEIRD_JELLY_BLOCK = ITEMS.register("weird_jelly_block",
            () -> new BlockItem(VDBlocks.WEIRD_JELLY_BLOCK.get(), basicItem().stacksTo(1)));
    public static final RegistryObject<VampireConsumableItem> WEIRD_JELLY = ITEMS.register("weird_jelly",
            () -> new VampireConsumableItem(bowlFoodItem(VDFoodValues.NASTY), VDFoodValues.WEIRD_JELLY));
}