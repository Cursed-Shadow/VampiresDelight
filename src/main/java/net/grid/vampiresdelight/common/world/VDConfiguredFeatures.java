package net.grid.vampiresdelight.common.world;

import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.registry.VDBlocks;
import net.grid.vampiresdelight.common.registry.VDFeatures;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class VDConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> HUGE_BLACK_MUSHROOM = registerKey("huge_black_mushroom");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_BLACK_MUSHROOM = registerKey("patch_black_mushroom");

    public static void createConfiguredFeatures(BootstapContext<ConfiguredFeature<?, ?>> context) {
        register(context, HUGE_BLACK_MUSHROOM, VDFeatures.HUGE_BLACK_MUSHROOM.get(), NoneFeatureConfiguration.INSTANCE);

        register(context, PATCH_BLACK_MUSHROOM, Feature.RANDOM_PATCH, FeatureUtils.simpleRandomPatchConfiguration(
                8, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(VDBlocks.BLACK_MUSHROOM.get())))));
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(VampiresDelight.MODID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
