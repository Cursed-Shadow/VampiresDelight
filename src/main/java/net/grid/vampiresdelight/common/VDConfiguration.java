package net.grid.vampiresdelight.common;

import com.google.common.collect.ImmutableList;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.List;
import java.util.function.Predicate;

public class VDConfiguration {
    public static final ModConfigSpec COMMON_CONFIG;
    public static final ModConfigSpec CLIENT_CONFIG;

    //COMMON
    public static final String CATEGORY_VILLAGE = "village";
    public static final ModConfigSpec.BooleanValue FARMERS_BUY_GARLIC;
    public static final ModConfigSpec.BooleanValue WANDERING_TRADER_SELLS_VAMPIRISM_ITEMS;

    public static final String CATEGORY_BLOCKS = "blocks";
    public static final ModConfigSpec.DoubleValue BLOODY_SOIL_BOOST_CHANCE;

    public static final String CATEGORY_ITEMS = "items";
    public static final ModConfigSpec.BooleanValue ALCHEMICAL_COCKTAIL_BURNS_GROUND;
    public static final ModConfigSpec.DoubleValue ALCHEMICAL_COCKTAIL_SPLASH_RADIUS;
    public static final ModConfigSpec.IntValue ALCHEMICAL_COCKTAIL_STACK_SIZE;

    public static final String CATEGORY_EFFECTS = "effects";
    public static final ModConfigSpec.BooleanValue REPLACE_WEIRD_JELLY_SUNSCREEN_WITH_JUMPBOOST;
    public static final ModConfigSpec.BooleanValue BAT_MEAT_WITHERS_HUMANS;
    public static final ModConfigSpec.BooleanValue BLESSING_HELPS_AGAINST_GHOSTS;
    public static final ModConfigSpec.BooleanValue ARMOR_DISSOLVES_FULLY;

    public static final String CATEGORY_ENCHANTMENTS = "enchantments";
    public static final ModConfigSpec.DoubleValue VAMPIRE_BITE_MAX_HEALING_VALUE;
    public static final ModConfigSpec.IntValue VAMPIRE_BITE_HEALING_CHANCE_1;
    public static final ModConfigSpec.IntValue VAMPIRE_BITE_HEALING_CHANCE_2;
    public static final ModConfigSpec.IntValue VAMPIRE_BITE_HEALING_CHANCE_3;

    public static final String CATEGORY_WORLD = "world";
    public static final ModConfigSpec.BooleanValue GENERATE_VD_CHEST_LOOT;
    public static final ModConfigSpec.BooleanValue GENERATE_COOKING_POT_IN_HUNTER_CAMP;
    public static final ModConfigSpec.BooleanValue GENERATE_COOKING_POT_NEAR_HUNTER_CAMP;
    public static final ModConfigSpec.IntValue COOKING_POT_IN_HUNTER_CAMP_CHANCE;

    // CLIENT
    public static final String CATEGORY_TOOLTIPS = "tooltips";
    public static final ModConfigSpec.BooleanValue HUNTER_TOOLTIPS_FOR_EVERYONE;
    public static final ModConfigSpec.BooleanValue COLORED_TOOLTIPS;

    public static final String CATEGORY_TOOLTIP_COLORS = "tooltip_colors";
    public static final ModConfigSpec.ConfigValue<List<? extends Integer>> VAMPIRE_FOOD_TOOLTIP_START_COLOR;
    public static final ModConfigSpec.ConfigValue<List<? extends Integer>> VAMPIRE_FOOD_TOOLTIP_END_COLOR;
    public static final ModConfigSpec.ConfigValue<List<? extends Integer>> HUNTER_FOOD_TOOLTIP_START_COLOR;
    public static final ModConfigSpec.ConfigValue<List<? extends Integer>> HUNTER_FOOD_TOOLTIP_END_COLOR;
    public static final ModConfigSpec.ConfigValue<List<? extends Integer>> WEREWOLF_FOOD_TOOLTIP_START_COLOR;
    public static final ModConfigSpec.ConfigValue<List<? extends Integer>> WEREWOLF_FOOD_TOOLTIP_END_COLOR;

    public static final String CATEGORY_APPLESKIN = "appleskin";
    public static final ModConfigSpec.BooleanValue CORRECT_APPLE_SKIN_TOOLTIPS;
    public static final ModConfigSpec.BooleanValue HIDE_APPLE_SKIN_HUMAN_FOOD_TOOLTIPS_FOR_VAMPIRES;
    public static final ModConfigSpec.BooleanValue HIDE_APPLE_SKIN_HUMAN_FOOD_TOOLTIPS_FOR_WEREWOLVES;

    static {
        // COMMON
        ModConfigSpec.Builder COMMON_BUILDER = new ModConfigSpec.Builder();

        COMMON_BUILDER.push(CATEGORY_VILLAGE);
        FARMERS_BUY_GARLIC = COMMON_BUILDER
                .comment("Should Farmers buy garlic? (May reduce chances of other trades appearing)")
                .define("farmersBuyGarlic", true);
        WANDERING_TRADER_SELLS_VAMPIRISM_ITEMS = COMMON_BUILDER
                .comment("Should the Wandering Trader sell some of vampirism's and this mod's items? (Including seeds and some blocks)")
                .define("wanderingTraderSellsVampirismItems", true);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.push(CATEGORY_BLOCKS);
        BLOODY_SOIL_BOOST_CHANCE = COMMON_BUILDER
                .comment("How often (in percentage) should Bloody Soil succeed in boosting a plant's growth at each random tick? Set it to 0.0 to disable this.")
                .defineInRange("bloodySoilBoostChance", 0.2, 0.0, 1.0);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.push(CATEGORY_ITEMS);
        ALCHEMICAL_COCKTAIL_BURNS_GROUND = COMMON_BUILDER
                .comment("Should the Alchemical Cocktail burn ground when thrown? Recommended to set this to \"false\" on servers with claims.")
                .define("alchemicalCocktailBurnsGround", true);
        ALCHEMICAL_COCKTAIL_SPLASH_RADIUS = COMMON_BUILDER
                .comment("What should be the radius of Alchemical Cocktail fire splash?")
                .defineInRange("alchemicalCocktailSplashRadius", 3.5, 1.0, 99.0);
        ALCHEMICAL_COCKTAIL_STACK_SIZE = COMMON_BUILDER
                .comment("What should be the maximum stack size of the Alchemical Cocktail?")
                .defineInRange("alchemicalCocktailStackSize", 8, 1, 99);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.push(CATEGORY_EFFECTS);
        REPLACE_WEIRD_JELLY_SUNSCREEN_WITH_JUMPBOOST = COMMON_BUILDER
                .comment("Should the Weird Jelly Sunscreen effect be replaced with Jump Boost?")
                .define("replaceWeirdJellySunscreenWithJumpboost", false);
        BAT_MEAT_WITHERS_HUMANS = COMMON_BUILDER
                .comment("Should the bat meat and food made of it have chance of giving Wither effect when eaten by humans?")
                .define("batMeatWithersHumans", true);
        BLESSING_HELPS_AGAINST_GHOSTS = COMMON_BUILDER
                .comment("Should the blessing effect banish ghosts just like phantoms?")
                .define("blessingHelpsAgainstGhosts", true);
        ARMOR_DISSOLVES_FULLY = COMMON_BUILDER
                .comment("Should \"weak\" armor such as leather and chain dissolve fully because of Clothes Dissolving effect?")
                .define("armorDissolvesFully", true);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.push(CATEGORY_ENCHANTMENTS);
        VAMPIRE_BITE_MAX_HEALING_VALUE = COMMON_BUILDER
                .comment("What should be the maximum amount of hearts that Vampire Bite enchantment should heal?")
                .defineInRange("vampireBiteMaxHealingValue", 1.5, 0.5, 10.0);
        VAMPIRE_BITE_HEALING_CHANCE_1 = COMMON_BUILDER
                .comment("With what chance should Vampire Bite enchantment regenerate health?")
                .defineInRange("vampireBiteChanceLevel1", 25, 1, 100);
        VAMPIRE_BITE_HEALING_CHANCE_2 = COMMON_BUILDER
                .defineInRange("vampireBiteChanceLevel2", 30, 1, 100);
        VAMPIRE_BITE_HEALING_CHANCE_3 = COMMON_BUILDER
                .defineInRange("vampireBiteChanceLevel3", 35, 1, 100);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.push(CATEGORY_WORLD);
        GENERATE_VD_CHEST_LOOT = COMMON_BUILDER
                .comment("Should this mod add some of its items as extra chest loot across Minecraft and Vampirism structures?")
                .define("generateVDChestLoot", true);
        GENERATE_COOKING_POT_IN_HUNTER_CAMP = COMMON_BUILDER
                .comment("Should a Cooking Pot on a Campfire/Fire Place be generated in hunter camps?")
                .define("generateCookingPotInHunterCamp", true);
        GENERATE_COOKING_POT_NEAR_HUNTER_CAMP = COMMON_BUILDER
                .comment("Should a Cooking Pot be generated on the campfire near hunter camps? By default it's generated on the Campfire/Fire Place in the middle of it.")
                .define("generateCookingPotNearHunterCamp", false);
        COOKING_POT_IN_HUNTER_CAMP_CHANCE = COMMON_BUILDER
                .comment("With what chance should a Cooking Pot on a Campfire/Fire Place be generated in hunter camps?")
                .defineInRange("cookingPotInHunterCampSpawnChance", 40, 1, 100);
        COMMON_BUILDER.pop();

        COMMON_CONFIG = COMMON_BUILDER.build();


        // CLIENT
        ModConfigSpec.Builder CLIENT_BUILDER = new ModConfigSpec.Builder();

        CLIENT_BUILDER.push(CATEGORY_TOOLTIPS);
        COLORED_TOOLTIPS = CLIENT_BUILDER
                .comment("Should the mod change the color of tooltips?")
                .define("coloredTooltips", true);
        HUNTER_TOOLTIPS_FOR_EVERYONE = CLIENT_BUILDER
                .comment("Should hunter food tooltips and tooltip's color be shown to all fractions? (Only shown to vampires by default)")
                .define("hunterTooltipsForEveryone", false);
        CLIENT_BUILDER.pop();

        CLIENT_BUILDER.push(CATEGORY_TOOLTIP_COLORS);
        VAMPIRE_FOOD_TOOLTIP_START_COLOR = CLIENT_BUILDER
                .comment("What color (rgb) should be used for vampire food tooltips as the start color? (The shade it starts with)")
                .comment("Default: 124, 40, 124")
                .defineList("vampireFoodTooltipStartColor", ImmutableList.of(124, 40, 124), () -> 0, new ConfigTypePredicate(Integer.class));
        VAMPIRE_FOOD_TOOLTIP_END_COLOR = CLIENT_BUILDER
                .comment("What color (rgb) should be used for vampire food tooltips as the end color? (The shade it ends with)")
                .comment("Default: 50, 0, 70")
                .defineList("vampireFoodTooltipEndColor", ImmutableList.of(50, 0, 70), () -> 0, new ConfigTypePredicate(Integer.class));
        HUNTER_FOOD_TOOLTIP_START_COLOR = CLIENT_BUILDER
                .comment("What color (rgb) should be used for hunter food tooltips as the start color? (The shade it starts with)")
                .comment("Default: 65, 65, 220")
                .defineList("hunterFoodTooltipStartColor", ImmutableList.of(65, 65, 220), () -> 0, new ConfigTypePredicate(Integer.class));
        HUNTER_FOOD_TOOLTIP_END_COLOR = CLIENT_BUILDER
                .comment("What color (rgb) should be used for hunter food tooltips as the end color? (The shade it ends with)")
                .comment("Default: 30, 30, 90")
                .defineList("hunterFoodTooltipEndColor", ImmutableList.of(30, 30, 90), () -> 0, new ConfigTypePredicate(Integer.class));
        WEREWOLF_FOOD_TOOLTIP_START_COLOR = CLIENT_BUILDER
                .comment("What color (rgb) should be used for werewolf food tooltips as the start color? (Werewolves mod only) (The shade it starts with)")
                .comment("Default: 250, 135, 0")
                .defineList("werewolfFoodTooltipStartColor", ImmutableList.of(250, 135, 0), () -> 0, new ConfigTypePredicate(Integer.class));
        WEREWOLF_FOOD_TOOLTIP_END_COLOR = CLIENT_BUILDER
                .comment("What color (rgb) should be used for werewolf food tooltips as the end color? (Werewolves mod only) (The shade it ends with)")
                .comment("Default: 115, 45, 0")
                .defineList("werewolfFoodTooltipEndColor", ImmutableList.of(115, 45, 0), () -> 0, new ConfigTypePredicate(Integer.class));
        CLIENT_BUILDER.pop();

        CLIENT_BUILDER.push(CATEGORY_APPLESKIN);
        CORRECT_APPLE_SKIN_TOOLTIPS = CLIENT_BUILDER
                .comment("Should AppleSkin tooltips' food values be fixed depending on player's race? (In case the player is a vampire, it'll show blood values for vampire food)")
                .define("correctAppleSkinTooltips", true);
        HIDE_APPLE_SKIN_HUMAN_FOOD_TOOLTIPS_FOR_VAMPIRES = CLIENT_BUILDER
                .comment("Should AppleSkin tooltips be hidden for human food if the player is a vampire?")
                .define("hideAppleSkinHumanFoodTooltipsForVampires", true);
        HIDE_APPLE_SKIN_HUMAN_FOOD_TOOLTIPS_FOR_WEREWOLVES = CLIENT_BUILDER
                .comment("Should AppleSkin tooltips be hidden if the player is a werewolf for food they can't eat?")
                .define("hideAppleSkinHumanFoodTooltipsForWerewolves", true);

        CLIENT_BUILDER.pop();
        CLIENT_CONFIG = CLIENT_BUILDER.build();
    }

    // Needed for list configs in order not to make it create additional toml.bak files
    private record ConfigTypePredicate(Class<?> configType) implements Predicate<Object> {
        @Override
        public boolean test(Object o) {
            return configType.isInstance(o);
        }
    }
}
