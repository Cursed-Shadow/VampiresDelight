# Changelog

## 0.1.3

### Additions
- Added new configs:
  - `vampireBiteMaxHealingValue`. `1.5` by default;
  - `disableVampireBite` `false` by default;
- Added **Bar Stools**;
- Added advancements (*WIP*);

### Updates
- Removed **Alchemical Fire** from vanilla combat creative tab;
- Added JEI ingredient information for **Black Mushroom**;
- **Black Stone Stove** is now considered as campfire cooking recipe catalyst in JEI;
- **Cursed Farmland** now can get moistened;

### Translations
- Added:
  - uk_UA;
- Corrected:
  - ru_RU;

## 0.1.2

### Additions
- Added new configs:
  - `alchemicalCocktailBurnsGround`. `true` by default;
  - `backstabbingCanBeAppliedToHunterWeapon`. `true` by default;
  - `vampireBiteChanceLevel1`. `20` by default;
  - `vampireBiteChanceLevel2`. `25` by default;
  - `vampireBiteChanceLevel3`. `30` by default;
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