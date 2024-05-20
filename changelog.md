# Changelog

## 0.1.5

### Additions
- Added new configs:
  - `generateCookingPotNearHunterCamp`:
    - `false` by default;

### Updates
- Renamed **Clothes Dissolving** effect to **Armor Dissolving** in order to avoid unnecessary questions;

## 0.1.4

### Updates
- **Lost Carriages** no longer have grass floor;

### Fixes
- Game no longer crashes because of oil function mixin and hunter camp structure mixin while loading;

## 0.1.3

### Additions
- Added new configs:
  - `vampireBiteMaxHealingValue`:
    - `1.5` by default;
  - `disableVampireBite`:
    - `false` by default;
  - `generateCookingPotInHunterCamp`:
    - `true` by default;
  - `cookingPotInHunterCampSpawnChance`:
    - `25` by default;
    - Range: from `0` to `100`;
  - `correctAppleSkinTooltips`:
    - `true` by default;
  - `hideAppleSkinHumanFoodTooltipsForVampires`:
    - `true` by default;
- Added **Bar Stools**;
- Added advancements (*WIP*);

### Updates
- Removed **Alchemical Fire** from vanilla combat creative tab;
- Added JEI ingredient information for **Black Mushroom**;
- **Black Stone Stove** is now considered as campfire cooking recipe catalyst in JEI;
- **Cursed Farmland** now can get moistened;
- **Black Mushroom** blocks are now a bit darker;
- The shape of the **Huge Black Mushroom** is now different from the Huge Red Mushroom's one;
- **Cooking Pot** on a Campfire now spawns in **Hunter Camps**;
- Added **Apple Skin** compatibility (*WIP*);
- **Hardtack** no longer gives Saturation effect, but restores more hunger for hunters;
- Hunter npcs and players wearing hunter armor boots are now able to walk on **Powder Snow**;

### Fixes
- Clothes Dissolving oil no longer spawns at hunter outpost alchemy chests;
- Food made of **Bat** is no longer marked as vampire faction only;

### Translations
- Added:
  - uk_UA;
  - en_GB;
- Corrected:
  - ru_RU;

## 0.1.2

### Additions
- Added new configs:
  - `alchemicalCocktailBurnsGround`:
    - `true` by default;
  - `backstabbingCanBeAppliedToHunterWeapon`:
    - `true` by default;
  - `vampireBiteChanceLevel1`:
    - `20` by default;
    - Range: from `0` to `100`;
  - `vampireBiteChanceLevel2`:
    - `25` by default;
    - Range: from `0` to `100`;
  - `vampireBiteChanceLevel3`:
    - `30` by default;
    - Range: from `0` to `100`;
- Added new cutting recipes:
  - Stripping Dark and Cursed Spruce Logs;
  - Salvaging Dark and Cursed Spruce furniture;
  - Breaking Dark Stone into Cobbled Dark Stone;
  - Cutting Armor of Swiftness into leather;

### Updates
- **Wild Garlic** now gives Garlic effect to vampires when inside;
- Tweaked **Black Mushroom** to spawn more frequently;
- **Vampire Orchid Crop** now grows only in dark areas with maximum light level of 12 or in vampire fog;
- The damage **Clothes Dissolving** effect deals to armor can now be reduced by Unbreaking enchantment;
- **Clothes Dissolving** potion is now creative only, and can't be crafted;
- Wandering traders now sell **Black Mushrooms**;
- Recalculated **Vampire Bite** enchantment's healing values. Level 1 now has 20% to healing, level 2 has 25%, and level 3 has 30% (can be configured). Max healing value is now 2 hearts;

### Fixes
- Fixed Hardtack giving bowl after being eaten;

### Translations
- Added:
  - ru_RU (Thanks, DimensionPainter!);

## 0.1.1

### Additions
- Added **Orchid Curry**;
- Added **Bat Taco**;
- Added **Black Mushroom**:
  - Spawns in vampire forest;
  - Can grow huge if bonemealed;
- Added **Black Mushroom Soup**;
- Added new configs:
    - `replaceWeirdJellySunscreenWithJumpboost`. `false` by default (Thanks, Zin!);
    - `blessingHelpsAgainstGhosts`. `true` by default;
    - `batMeatWithersHumans`. `true` by default;

### Updates
- Tweaked food values for some foods;

### Fixes
- Fixed decoding exception with not encoded brewing barrel recipe tab which resulted in the servers not working (Thanks, Cheaterpaul!);
- Fixed config indentation (Thanks, Zin!);
- Fixed Farmer's Bliss incompatibility;

## 0.1.0

### Additions
- Added **Dark Stone Stove**;
- Added **Blood Wine**;
- Added **Wine Shelves**;
- Added **Wine Glass**;
- Added **Tricolor Dango**;
- Added **Sugared Berries**;
- Added several new effects and hunter potions for them;
- And more;

### Updates
- Several texture updates;
- **Farmer Villagers** now buy **Garlic**;
- **Wandering Traders** now sell **Orchid Seeds**, **Orchid Petals**, **Garlic**, and **Cursed Earth**;
- Vampire food tooltip now shows custom fraction tooltips;
- All vampire food recipes now require blood syrup instead of blood bottles;
- Nourishment hunger overlay now works for blood bar as well;
- Rebalanced food values for all food;
- And more;

### Fixes
- Fix vampire bite crashing game;

## 0.0.1.2

### Additions
- **Vampire Orchid** is now growable;
- Added **Cursed Farmland** to grow **Vampire Orchid**;

### Updates
- Cutting **Vampire Orchid** now drops **Orchid Seeds**;
- Changed **Blood Dough** recipe to use **Rice** instead of **Wheat**;

### Fixes
- **Orchid Bad** now drops correctly;

## 0.0.1.1

### Additions
- Added recipe for **Alchemical Cocktail**
- Hunters, Villagers, and Wandering traders now drop **Human Eyes** when killed with a knife;

### Updates
- Changed **Alchemical Cocktail**'s physics to act like vanilla potions;

### Fixes
- Fixed a bug that crashes game while trying to eat vampire food;