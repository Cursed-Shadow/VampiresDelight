package net.grid.vampiresdelight.common.world;

import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.registry.VDBlocks;
import net.grid.vampiresdelight.common.registry.VDFeatures;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.HugeMushroomBlock;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class VDConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> HUGE_BLACK_MUSHROOM_KEY = registerKey("huge_black_mushroom");

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        register(context, HUGE_BLACK_MUSHROOM_KEY, VDFeatures.HUGE_BLACK_MUSHROOM.get(),
                new HugeMushroomFeatureConfiguration(
                        BlockStateProvider.simple(VDBlocks.BLACK_MUSHROOM_BLOCK.get().defaultBlockState()
                                .setValue(HugeMushroomBlock.DOWN, Boolean.FALSE)),
                        BlockStateProvider.simple(VDBlocks.BLACK_MUSHROOM_STEM.get().defaultBlockState()
                                .setValue(HugeMushroomBlock.UP, Boolean.FALSE)
                                .setValue(HugeMushroomBlock.DOWN, Boolean.FALSE)), 2));
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(VampiresDelight.MODID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
