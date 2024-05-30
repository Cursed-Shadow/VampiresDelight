package net.grid.vampiresdelight.common;

import com.google.common.collect.ImmutableList;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.List;
import java.util.function.Predicate;

public class VDConfiguration {
    public static ForgeConfigSpec COMMON_CONFIG;
    public static ForgeConfigSpec CLIENT_CONFIG;

    //COMMON
    public static final String CATEGORY_VILLAGE = "village";
    public static ForgeConfigSpec.BooleanValue FARMERS_BUY_GARLIC;
    public static ForgeConfigSpec.BooleanValue WANDERING_TRADER_SELLS_VAMPIRISM_ITEMS;

    public static final String CATEGORY_RECIPE_BOOK = "recipe_book";
    public static ForgeConfigSpec.BooleanValue ENABLE_RECIPE_BOOK_BREWING_BARREL;

    public static final String CATEGORY_ITEMS = "items";
    public static ForgeConfigSpec.BooleanValue ALCHEMICAL_COCKTAIL_BURNS_GROUND;
    public static ForgeConfigSpec.DoubleValue ALCHEMICAL_COCKTAIL_SPLASH_RADIUS;
    public static ForgeConfigSpec.IntValue ALCHEMICAL_COCKTAIL_STACK_SIZE;

    public static final String CATEGORY_EFFECTS = "effects";
    public static ForgeConfigSpec.BooleanValue REPLACE_WEIRD_JELLY_SUNSCREEN_WITH_JUMPBOOST;
    public static ForgeConfigSpec.BooleanValue BAT_MEAT_WITHERS_HUMANS;
    public static ForgeConfigSpec.BooleanValue BLESSING_HELPS_AGAINST_GHOSTS;
    public static ForgeConfigSpec.BooleanValue ARMOR_DISSOLVES_FULLY;

    public static final String CATEGORY_ENCHANTMENTS = "enchantments";
    public static ForgeConfigSpec.BooleanValue BACKSTABBING_CAN_BE_APPLIED_TO_HUNTER_WEAPON;
    public static ForgeConfigSpec.DoubleValue VAMPIRE_BITE_MAX_HEALING_VALUE;
    public static ForgeConfigSpec.IntValue VAMPIRE_BITE_HEALING_CHANCE_1;
    public static ForgeConfigSpec.IntValue VAMPIRE_BITE_HEALING_CHANCE_2;
    public static ForgeConfigSpec.IntValue VAMPIRE_BITE_HEALING_CHANCE_3;
    public static ForgeConfigSpec.BooleanValue DISABLE_VAMPIRE_BITE;

    public static final String CATEGORY_WORLD = "world";
    public static ForgeConfigSpec.BooleanValue GENERATE_VD_CHEST_LOOT;
    public static ForgeConfigSpec.BooleanValue GENERATE_COOKING_POT_IN_HUNTER_CAMP;
    public static ForgeConfigSpec.BooleanValue GENERATE_COOKING_POT_NEAR_HUNTER_CAMP;
    public static ForgeConfigSpec.IntValue COOKING_POT_IN_HUNTER_CAMP_CHANCE;

    // CLIENT
    public static final String CATEGORY_TOOLTIPS = "tooltips";
    public static ForgeConfigSpec.BooleanValue HUNTER_TOOLTIPS_FOR_EVERYONE;
    public static ForgeConfigSpec.BooleanValue COLORED_TOOLTIPS;
    public static final String CATEGORY_TOOLTIP_COLORS = "tooltip_colors";
    public static ForgeConfigSpec.ConfigValue<List<? extends Integer>> VAMPIRE_FOOD_TOOLTIP_START_COLOR;
    public static ForgeConfigSpec.ConfigValue<List<? extends Integer>> VAMPIRE_FOOD_TOOLTIP_END_COLOR;
    public static ForgeConfigSpec.ConfigValue<List<? extends Integer>> HUNTER_FOOD_TOOLTIP_START_COLOR;
    public static ForgeConfigSpec.ConfigValue<List<? extends Integer>> HUNTER_FOOD_TOOLTIP_END_COLOR;
    public static ForgeConfigSpec.ConfigValue<List<? extends Integer>> WEREWOLF_FOOD_TOOLTIP_START_COLOR;
    public static ForgeConfigSpec.ConfigValue<List<? extends Integer>> WEREWOLF_FOOD_TOOLTIP_END_COLOR;

    public static final String CATEGORY_APPLESKIN = "appleskin";
    public static ForgeConfigSpec.BooleanValue CORRECT_APPLE_SKIN_TOOLTIPS;
    public static ForgeConfigSpec.BooleanValue HIDE_APPLE_SKIN_HUMAN_FOOD_TOOLTIPS_FOR_VAMPIRES;


    static {
        // COMMON
        ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
        COMMON_BUILDER.comment("Common settings");

        COMMON_BUILDER.comment("Village").push(CATEGORY_VILLAGE);
        FARMERS_BUY_GARLIC = COMMON_BUILDER.comment("Should Farmers buy garlic? (May reduce chances of other trades appearing)")
                .define("farmersBuyGarlic", true);
        WANDERING_TRADER_SELLS_VAMPIRISM_ITEMS = COMMON_BUILDER.comment("Should the Wandering Trader sell some of vampirism's and this mod's items? (Including seeds and some blocks)")
                .define("wanderingTraderSellsVampirismItems", true);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Recipe book").push(CATEGORY_RECIPE_BOOK);
        ENABLE_RECIPE_BOOK_BREWING_BARREL = COMMON_BUILDER.comment("Should the Brewing Barrel have a Recipe Book available on its interface?")
                .define("enableRecipeBookBrewingBarrel", true);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Items").push(CATEGORY_ITEMS);
        ALCHEMICAL_COCKTAIL_BURNS_GROUND = COMMON_BUILDER.comment("Should the Alchemical Cocktail burn ground when thrown? Recommended to set this to \"false\" on servers with claims.")
                .define("alchemicalCocktailBurnsGround", true);
        ALCHEMICAL_COCKTAIL_SPLASH_RADIUS = COMMON_BUILDER.comment("What should be the radius of Alchemical Cocktail fire splash?")
                .defineInRange("alchemicalCocktailSplashRadius", 3.5, 1.0, 99.0);
        ALCHEMICAL_COCKTAIL_STACK_SIZE = COMMON_BUILDER.comment("What should be the maximum stack size of the Alchemical Cocktail?")
                .defineInRange("alchemicalCocktailStackSize", 8, 1, 64);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Effects").push(CATEGORY_EFFECTS);
        REPLACE_WEIRD_JELLY_SUNSCREEN_WITH_JUMPBOOST = COMMON_BUILDER.comment("Should the weird jelly Sunscreen effect be replaced with Jump Boost?")
                .define("replaceWeirdJellySunscreenWithJumpboost", false);
        BAT_MEAT_WITHERS_HUMANS = COMMON_BUILDER.comment("Should the bat meat and food made of it have chance of giving Wither effect when eaten by humans?")
                .define("batMeatWithersHumans", true);
        BLESSING_HELPS_AGAINST_GHOSTS = COMMON_BUILDER.comment("Should the blessing effect banish ghosts just like phantoms?")
                .define("blessingHelpsAgainstGhosts", true);
        ARMOR_DISSOLVES_FULLY = COMMON_BUILDER.comment("Should 'weak' armor such as leather and chain dissolve fully because of Clothes Dissolving effect?")
                .define("armorDissolvesFully", true);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Enchantments").push(CATEGORY_ENCHANTMENTS);
        BACKSTABBING_CAN_BE_APPLIED_TO_HUNTER_WEAPON = COMMON_BUILDER.comment("Should it be possible to apply Backstabbing enchantment to some hunter axes and stakes?")
                .define("backstabbingCanBeAppliedToHunterWeapon", true);
        VAMPIRE_BITE_MAX_HEALING_VALUE = COMMON_BUILDER.comment("What should be the maximum amount of hearts that Vampire Bite enchantment should heal?")
                .defineInRange("vampireBiteMaxHealingValue", 1.5, 0.5, 10.0);
        VAMPIRE_BITE_HEALING_CHANCE_1 = COMMON_BUILDER.comment("With what chance should Vampire Bite enchantment regenerate health?")
                .defineInRange("vampireBiteChanceLevel1", 20, 1, 100);
        VAMPIRE_BITE_HEALING_CHANCE_2 = COMMON_BUILDER
                .defineInRange("vampireBiteChanceLevel2", 25, 1, 100);
        VAMPIRE_BITE_HEALING_CHANCE_3 = COMMON_BUILDER
                .defineInRange("vampireBiteChanceLevel3", 30, 1, 100);
        DISABLE_VAMPIRE_BITE = COMMON_BUILDER.comment("Should Vampire Bite enchantment be disabled?").comment("It won't be removed from the game, but it'll be impossible to apply it to tools and it won't heal the player.")
                .define("disableVampireBite", false);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("World generation").push(CATEGORY_WORLD);
        GENERATE_VD_CHEST_LOOT = COMMON_BUILDER.comment("Should this mod add some of its items as extra chest loot across Minecraft and Vampirism structures?")
                .define("generateVDChestLoot", true);
        GENERATE_COOKING_POT_IN_HUNTER_CAMP = COMMON_BUILDER.comment("Should a Cooking Pot on a Campfire be generated in hunter camps?")
                .define("generateCookingPotInHunterCamp", true);
        GENERATE_COOKING_POT_NEAR_HUNTER_CAMP = COMMON_BUILDER.comment("Should a Cooking Pot be generated on the campfire near hunter camps? By default it's generated on the fireplace in the middle of it.")
                .define("generateCookingPotNearHunterCamp", false);
        COOKING_POT_IN_HUNTER_CAMP_CHANCE = COMMON_BUILDER.comment("With what chance should a Cooking Pot on a Campfire be generated in hunter camps?")
                .defineInRange("cookingPotInHunterCampSpawnChance", 20, 1, 100);
        COMMON_BUILDER.pop();

        COMMON_CONFIG = COMMON_BUILDER.build();


        // CLIENT
        ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();
        CLIENT_BUILDER.comment("Client settings");

        CLIENT_BUILDER.comment("Tooltips").push(CATEGORY_TOOLTIPS);
        COLORED_TOOLTIPS = CLIENT_BUILDER.comment("Should the mod change the color of tooltips?")
                .define("coloredTooltips", true);
        HUNTER_TOOLTIPS_FOR_EVERYONE = CLIENT_BUILDER.comment("Should hunter food tooltips and tooltip's color be shown to all fractions? (Only shown to vampires by default)")
                .define("hunterTooltipsForEveryone", false);

        CLIENT_BUILDER.comment("Colors of tooltips").push(CATEGORY_TOOLTIP_COLORS);
        VAMPIRE_FOOD_TOOLTIP_START_COLOR = CLIENT_BUILDER.comment("What color (rgb) should be used for vampire food tooltips as the start color? (The shade it starts with)")
                .comment("Default: 124, 40, 124")
                .defineList("vampireFoodTooltipStartColor", ImmutableList.of(124, 40, 124), new ConfigTypePredicate(Integer.class));
        VAMPIRE_FOOD_TOOLTIP_END_COLOR = CLIENT_BUILDER.comment("What color (rgb) should be used for vampire food tooltips as the end color? (The shade it ends with)")
                .comment("Default: 50, 0, 70")
                .defineList("vampireFoodTooltipEndColor", ImmutableList.of(50, 0, 70), new ConfigTypePredicate(Integer.class));
        HUNTER_FOOD_TOOLTIP_START_COLOR = CLIENT_BUILDER.comment("What color (rgb) should be used for hunter food tooltips as the start color? (The shade it starts with)")
                .comment("Default: 65, 65, 220")
                .defineList("hunterFoodTooltipStartColor", ImmutableList.of(65, 65, 220), new ConfigTypePredicate(Integer.class));
        HUNTER_FOOD_TOOLTIP_END_COLOR = CLIENT_BUILDER.comment("What color (rgb) should be used for hunter food tooltips as the end color? (The shade it ends with)")
                .comment("Default: 30, 30, 90")
                .defineList("hunterFoodTooltipEndColor", ImmutableList.of(30, 30, 90), new ConfigTypePredicate(Integer.class));
        WEREWOLF_FOOD_TOOLTIP_START_COLOR = CLIENT_BUILDER.comment("What color (rgb) should be used for werewolf food tooltips as the start color? (Werewolves mod only) (The shade it starts with)")
                .comment("Default: 250, 135, 0")
                .defineList("werewolfFoodTooltipStartColor", ImmutableList.of(250, 135, 0), new ConfigTypePredicate(Integer.class));
        WEREWOLF_FOOD_TOOLTIP_END_COLOR = CLIENT_BUILDER.comment("What color (rgb) should be used for werewolf food tooltips as the end color? (Werewolves mod only) (The shade it ends with)")
                .comment("Default: 115, 45, 0")
                .defineList("werewolfFoodTooltipEndColor", ImmutableList.of(115, 45, 0), new ConfigTypePredicate(Integer.class));
        CLIENT_BUILDER.pop();

        CLIENT_BUILDER.comment("AppleSkin").push(CATEGORY_APPLESKIN);
        CORRECT_APPLE_SKIN_TOOLTIPS = CLIENT_BUILDER.comment("Should AppleSkin tooltips' food values be fixed depending on player's race? (In case the player is a vampire, it'll show blood values for vampire food)")
                .define("correctAppleSkinTooltips", true);
        HIDE_APPLE_SKIN_HUMAN_FOOD_TOOLTIPS_FOR_VAMPIRES = CLIENT_BUILDER.comment("Should AppleSkin tooltips be hidden for human food if the player is a vampire?")
                .define("hideAppleSkinHumanFoodTooltipsForVampires", true);

        CLIENT_BUILDER.pop();
        CLIENT_CONFIG = CLIENT_BUILDER.build();
    }

    // Needed for list configs in order not to make it create additional toml.bak files
    private record ConfigTypePredicate(Class configType) implements Predicate<Object> {
        @Override
            public boolean test(Object o) {
                return configType.isInstance(o);
            }
        }
}
