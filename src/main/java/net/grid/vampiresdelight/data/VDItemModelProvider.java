package net.grid.vampiresdelight.data;

import com.google.common.collect.Sets;
import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.registry.VDItems;
import net.grid.vampiresdelight.common.utility.VDNameUtils;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import vectorwing.farmersdelight.data.ItemModels;

import java.util.*;
import java.util.stream.Collectors;

import static net.grid.vampiresdelight.common.utility.VDNameUtils.itemName;
import static vectorwing.farmersdelight.data.ItemModels.takeAll;

public class VDItemModelProvider extends ItemModelProvider {
    public static final String GENERATED = "item/generated";
    public static final String HANDHELD = "item/handheld";
    public static final ResourceLocation COCKTAIL = ResourceLocation.fromNamespaceAndPath(VampiresDelight.MODID, "item/cocktail");
    public static final ResourceLocation POURING = resourceItem("template_bottle_pouring");

    public VDItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, VampiresDelight.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        Set<Item> items = BuiltInRegistries.ITEM.stream().filter(item -> VampiresDelight.MODID.equals(VDNameUtils.itemNamespace(item))).collect(Collectors.toSet());

        // Items that use its own model in models/item
        /*
        Set<Item> specialItems = Sets.newHashSet();
        takeAll(items, specialItems.toArray(new Item[0])).forEach(items::remove);
         */

        // Items that should be held like a mug
        Set<Item> mugItems = Sets.newHashSet(
                VDItems.DAISY_TEA.get(),
                VDItems.ORCHID_TEA.get(),
                VDItems.MULLED_WINE_GLASS.get(),
                VDItems.DANDELION_BEER_MUG.get());
        takeAll(items, mugItems.toArray(new Item[0])).forEach(item -> itemMugModel(item, resourceItem(itemName(item))));

        // Items that should be held like a cocktail
        Set<Item> cocktailItems = Sets.newHashSet(
                VDItems.BLOOD_WINE_GLASS.get());
        takeAll(items, cocktailItems.toArray(new Item[0])).forEach(item -> itemCocktailModel(item, resourceItem(itemName(item))));

        // Two models of items that are pourable
        Set<Item> pouringItems = Sets.newHashSet(
                VDItems.DANDELION_BEER_BOTTLE.get(),
                VDItems.BLOOD_WINE_BOTTLE.get());
        pouringItems.forEach(this::itemPouringModel);

        alchemicalCocktailModel(VDItems.ALCHEMICAL_COCKTAIL.get());

        // Blocks with special item sprites
        Set<Item> spriteBlockItems = Sets.newHashSet(
                VDItems.BLOOD_PIE.get(),
                VDItems.ORCHID_SEEDS.get(),
                VDItems.WEIRD_JELLY_BLOCK.get(),
                VDItems.ORCHID_CAKE.get(),
                VDItems.SPIRIT_LANTERN.get());
        takeAll(items, spriteBlockItems.toArray(new Item[0])).forEach(item -> withExistingParent(itemName(item), GENERATED).texture("layer0", resourceItem(itemName(item))));

        Set<Item> flatBlockItems = Sets.newHashSet(
                VDItems.WILD_GARLIC.get(),
                VDItems.BLACK_MUSHROOM.get());
        takeAll(items, flatBlockItems.toArray(new Item[0])).forEach(item -> itemGeneratedModel(item, resourceBlock(itemName(item))));

        Set<Item> inventoryBlockItems = Sets.newHashSet(
                VDItems.BLACK_MUSHROOM_BLOCK.get(),
                VDItems.BLACK_MUSHROOM_STEM.get());
        takeAll(items, inventoryBlockItems.toArray(new Item[0])).forEach(item -> blockBasedModel(item, "_inventory"));

        // Blocks whose items look alike
        takeAll(items, i -> i instanceof BlockItem).forEach(item -> blockBasedModel(item, ""));

        // Handheld items
        Set<Item> handheldItems = Sets.newHashSet(
                VDItems.ALCHEMICAL_COCKTAIL.get(),
                VDItems.TRICOLOR_DANGO.get(),
                VDItems.EYES_ON_STICK.get(),
                VDItems.SILVER_KNIFE.get()
        );
        takeAll(items, handheldItems.toArray(new Item[0])).forEach(item -> itemHandheldModel(item, resourceItem(itemName(item))));

        // Generated items
        items.forEach(item -> itemGeneratedModel(item, resourceItem(itemName(item))));

    }

    public void blockBasedModel(Item item, String suffix) {
        withExistingParent(itemName(item), resourceBlock(itemName(item) + suffix));
    }

    public void itemHandheldModel(Item item, ResourceLocation texture) {
        withExistingParent(itemName(item), HANDHELD).texture("layer0", texture);
    }

    public void itemGeneratedModel(Item item, ResourceLocation texture) {
        withExistingParent(itemName(item), GENERATED).texture("layer0", texture);
    }

    public void itemMugModel(Item item, ResourceLocation texture) {
        withExistingParent(itemName(item), ItemModels.MUG).texture("layer0", texture);
    }

    public void itemCocktailModel(Item item, ResourceLocation texture) {
        withExistingParent(itemName(item), COCKTAIL).texture("layer0", texture);
    }

    public void itemPouringModel(Item item) {
        ResourceLocation texture = resourceItem(itemName(item));
        withExistingParent(itemName(item), GENERATED).texture("layer0", texture)
                .override().predicate(modLoc("pouring"), 0.01f).model(
                        withExistingParent(itemName(item) + "_pouring", POURING).texture("layer0", texture));
    }

    public void alchemicalCocktailModel(Item item) {
        ResourceLocation texture = resourceItem(itemName(item));
        withExistingParent(itemName(item), HANDHELD).texture("layer0", texture)
                .override().predicate(modLoc("metal_pipe"), 0.01f).model(
                        withExistingParent(itemName(item) + "_metal_pipe", GENERATED).texture("layer0", resourceItem("metal_pipe")));
    }

    private static ResourceLocation resourceBlock(String path) {
        return modLocation("block/" + path);
    }

    private static ResourceLocation resourceItem(String path) {
        return modLocation("item/" + path);
    }

    private static ResourceLocation modLocation(String path) {
        return ResourceLocation.fromNamespaceAndPath(VampiresDelight.MODID, path);
    }
}
