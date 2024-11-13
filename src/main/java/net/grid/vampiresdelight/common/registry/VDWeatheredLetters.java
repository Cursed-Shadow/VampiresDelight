package net.grid.vampiresdelight.common.registry;

import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.item.component.WeatheredLetter;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public class VDWeatheredLetters {
    public static final ResourceKey<WeatheredLetter> DEAR_MARCUS = createKey("dear_marcus");
    public static final ResourceKey<WeatheredLetter> BORSCHT_RECIPE = createKey("borscht_recipe");

    public static final ResourceLocation DEFAULT_ITEM_MODEL = ResourceLocation.fromNamespaceAndPath(VampiresDelight.MODID, "textures/gui/weathered_letter.png");

    public static final ResourceLocation DEFAULT_BACKGROUND_TEXTURE = ResourceLocation.fromNamespaceAndPath(VampiresDelight.MODID, "item/weathered_letter");

    public static void createWeatheredLetters(BootstrapContext<WeatheredLetter> context) {
        register(context, DEAR_MARCUS);
        register(context, BORSCHT_RECIPE);
    }

    private static ResourceKey<WeatheredLetter> createKey(String name) {
        return ResourceKey.create(VDRegistries.WEATHERED_LETTER, ResourceLocation.fromNamespaceAndPath(VampiresDelight.MODID, name));
    }

    private static void register(BootstrapContext<WeatheredLetter> context, ResourceKey<WeatheredLetter> key, ResourceLocation itemModel, ResourceLocation backgroundTexture) {
        context.register(key, new WeatheredLetter(key.location(), itemModel, backgroundTexture));
    }

    private static void register(BootstrapContext<WeatheredLetter> context, ResourceKey<WeatheredLetter> key) {
        register(context, key, DEFAULT_ITEM_MODEL, DEFAULT_BACKGROUND_TEXTURE);
    }
}
