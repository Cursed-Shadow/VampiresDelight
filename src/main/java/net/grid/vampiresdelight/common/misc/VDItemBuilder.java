package net.grid.vampiresdelight.common.misc;

import net.grid.vampiresdelight.common.registry.VDDataComponents;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class VDItemBuilder {

    Item.Properties properties;

    public VDItemBuilder() {
        properties = new Item.Properties();
    }

    public VDItemBuilder inBowl() {
        properties.craftRemainder(Items.BOWL).stacksTo(16);
        return this;
    }

    public VDItemBuilder inGlass() {
        properties.craftRemainder(Items.GLASS_BOTTLE).stacksTo(16);
        return this;
    }

    public VDItemBuilder food(FoodProperties defaultFood) {
        properties.food(defaultFood);
        return this;
    }

    public VDItemBuilder vampireFood(FoodProperties vampireFood) {
        properties.component(VDDataComponents.VAMPIRE_FOOD.get(), vampireFood);
        return this;
    }

    public VDItemBuilder hunterFood(FoodProperties hunterFood) {
        properties.component(VDDataComponents.HUNTER_FOOD.get(), hunterFood);
        return this;
    }

    public VDItemBuilder werewolfFood(FoodProperties werewolfFood) {
        properties.component(VDDataComponents.WEREWOLF_FOOD.get(), werewolfFood);
        return this;
    }

    public VDItemBuilder universalFood(FoodProperties universalFood) {
        food(universalFood);
        vampireFood(universalFood);
        return this;
    }

    public Item.Properties get() {
        return properties;
    }
}
