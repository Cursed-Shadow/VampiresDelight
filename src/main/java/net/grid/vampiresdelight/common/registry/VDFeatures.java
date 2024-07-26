package net.grid.vampiresdelight.common.registry;

import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.world.feature.HugeBlackMushroomFeature;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class VDFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(Registries.FEATURE, VampiresDelight.MODID);

    public static final Supplier<Feature<NoneFeatureConfiguration>> HUGE_BLACK_MUSHROOM = FEATURES.register("huge_black_mushroom", () -> new HugeBlackMushroomFeature(NoneFeatureConfiguration.CODEC));
}
