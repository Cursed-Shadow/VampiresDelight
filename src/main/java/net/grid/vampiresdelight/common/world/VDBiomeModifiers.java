package net.grid.vampiresdelight.common.world;

import de.teamlapen.vampirism.core.ModBiomes;
import de.teamlapen.vampirism.core.ModTags;
import net.grid.vampiresdelight.VampiresDelight;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import vectorwing.farmersdelight.common.world.modifier.AddFeaturesByFilterBiomeModifier;

import java.util.Optional;

public class VDBiomeModifiers {
    public static final ResourceKey<BiomeModifier> WILD_GARLIC = registerKey("wild_garlic");
    public static final ResourceKey<BiomeModifier> PATCH_BLACK_MUSHROOM = registerKey("patch_black_mushroom");

    public static void createBiomeModifiers(BootstrapContext<BiomeModifier> context) {
        HolderGetter<Biome> biomeLookup = context.lookup(Registries.BIOME);
        HolderGetter<PlacedFeature> placedFeatureLookup = context.lookup(Registries.PLACED_FEATURE);
        context.register(WILD_GARLIC, new AddFeaturesByFilterBiomeModifier(biomeLookup.getOrThrow(BiomeTags.IS_OVERWORLD), Optional.of(HolderSet.direct(biomeLookup.getOrThrow(Biomes.LUSH_CAVES), biomeLookup.getOrThrow(Biomes.MUSHROOM_FIELDS), biomeLookup.getOrThrow(ModBiomes.VAMPIRE_FOREST))), Optional.of(0.4F), Optional.of(0.9F), HolderSet.direct(placedFeatureLookup.getOrThrow(VDPlacedFeatures.PATCH_WILD_GARLIC)), GenerationStep.Decoration.VEGETAL_DECORATION));
        context.register(PATCH_BLACK_MUSHROOM, new BiomeModifiers.AddFeaturesBiomeModifier(biomeLookup.getOrThrow(ModTags.Biomes.IS_VAMPIRE_BIOME), HolderSet.direct(placedFeatureLookup.getOrThrow(VDPlacedFeatures.PATCH_BLACK_MUSHROOM)), GenerationStep.Decoration.VEGETAL_DECORATION));
    }

    private static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ResourceLocation.fromNamespaceAndPath(VampiresDelight.MODID, name));
    }
}
