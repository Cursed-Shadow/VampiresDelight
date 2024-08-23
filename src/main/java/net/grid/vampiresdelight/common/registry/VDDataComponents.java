package net.grid.vampiresdelight.common.registry;

import com.mojang.serialization.Codec;
import net.grid.vampiresdelight.VampiresDelight;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.world.food.FoodProperties;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import vectorwing.farmersdelight.common.item.component.ItemStackWrapper;

public class VDDataComponents {
    public static final DeferredRegister.DataComponents DATA_COMPONENTS = DeferredRegister.createDataComponents(VampiresDelight.MODID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<FoodProperties>> VAMPIRE_FOOD = DATA_COMPONENTS.registerComponentType(
            "vampire_food", (builder) -> builder.persistent(FoodProperties.DIRECT_CODEC).networkSynchronized(FoodProperties.DIRECT_STREAM_CODEC).cacheEncoding());

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<FoodProperties>> HUNTER_FOOD = DATA_COMPONENTS.registerComponentType(
            "hunter_food", (builder) -> builder.persistent(FoodProperties.DIRECT_CODEC).networkSynchronized(FoodProperties.DIRECT_STREAM_CODEC).cacheEncoding());

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<FoodProperties>> WEREWOLF_FOOD = DATA_COMPONENTS.registerComponentType(
            "werewolf_food", (builder) -> builder.persistent(FoodProperties.DIRECT_CODEC).networkSynchronized(FoodProperties.DIRECT_STREAM_CODEC).cacheEncoding());

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Double>> SPLASH_RADIUS = DATA_COMPONENTS.registerComponentType(
            "splash_radius", (builder) -> builder.persistent(Codec.DOUBLE).networkSynchronized(ByteBufCodecs.DOUBLE));

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<ItemStackWrapper>> SERVING_HELD = DATA_COMPONENTS.registerComponentType(
            "serving_held", (builder) -> builder.persistent(ItemStackWrapper.CODEC).networkSynchronized(ItemStackWrapper.STREAM_CODEC).cacheEncoding());
}
