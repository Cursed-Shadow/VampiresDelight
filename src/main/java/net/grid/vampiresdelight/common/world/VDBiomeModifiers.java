package net.grid.vampiresdelight.common.world;

import de.teamlapen.vampirism.core.ModTags;
import net.grid.vampiresdelight.VampiresDelight;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.registries.ForgeRegistries;

public class VDBiomeModifiers {
    public static final ResourceKey<BiomeModifier> PATCH_BLACK_MUSHROOM = registerKey("patch_black_mushroom");

    public static void createBiomeModifiers(BootstapContext<BiomeModifier> context) {
        HolderGetter<Biome> biomeLookup = context.lookup(Registries.BIOME);
        HolderGetter<PlacedFeature> placedFeatureLookup = context.lookup(Registries.PLACED_FEATURE);
        context.register(PATCH_BLACK_MUSHROOM, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(biomeLookup.getOrThrow(ModTags.Biomes.IS_VAMPIRE_BIOME), HolderSet.direct(placedFeatureLookup.getOrThrow(VDPlacedFeatures.PATCH_BLACK_MUSHROOM)), GenerationStep.Decoration.VEGETAL_DECORATION));
    }

    private static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(VampiresDelight.MODID, name));
    }
}
