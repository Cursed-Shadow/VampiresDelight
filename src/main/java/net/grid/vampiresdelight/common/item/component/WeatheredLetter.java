package net.grid.vampiresdelight.common.item.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.registry.VDDataComponents;
import net.grid.vampiresdelight.common.registry.VDRegistries;
import net.grid.vampiresdelight.common.registry.VDWeatheredLetters;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

// itemModel will be usable after Minecraft updates. They added a data component which allows changing the model of an item. For now, it just exists
public record WeatheredLetter(ResourceLocation id, ResourceLocation itemModel, ResourceLocation backgroundTexture) {
    private static final WeatheredLetter DEFAULT = new WeatheredLetter(ResourceLocation.fromNamespaceAndPath(VampiresDelight.MODID, "unknown_id"), VDWeatheredLetters.DEFAULT_ITEM_MODEL, VDWeatheredLetters.DEFAULT_BACKGROUND_TEXTURE);

    public static final Codec<WeatheredLetter> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                    ResourceLocation.CODEC.fieldOf("id").forGetter(WeatheredLetter::id),
                    ResourceLocation.CODEC.optionalFieldOf("item_model", VDWeatheredLetters.DEFAULT_ITEM_MODEL).forGetter(WeatheredLetter::itemModel),
                    ResourceLocation.CODEC.optionalFieldOf("background_texture", VDWeatheredLetters.DEFAULT_BACKGROUND_TEXTURE).forGetter(WeatheredLetter::backgroundTexture)
            ).apply(instance, WeatheredLetter::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, WeatheredLetter> STREAM_CODEC = StreamCodec.composite(
            ResourceLocation.STREAM_CODEC, WeatheredLetter::id,
            ResourceLocation.STREAM_CODEC, WeatheredLetter::itemModel,
            ResourceLocation.STREAM_CODEC, WeatheredLetter::backgroundTexture,
            WeatheredLetter::new
    );

    public static void addToStack(ItemStack stack, WeatheredLetter letter) {
        stack.set(VDDataComponents.WEATHERED_LETTER.get(), letter);
    }

    public static WeatheredLetter get(ItemStack stack) {
        return stack.getOrDefault(VDDataComponents.WEATHERED_LETTER.get(), DEFAULT);
    }

    public static WeatheredLetter getRandomLetter(RegistryAccess registryAccess, @Nullable TagKey<WeatheredLetter> tag) {
        Registry<WeatheredLetter> registry = registryAccess.registryOrThrow(VDRegistries.WEATHERED_LETTER);

        if (tag == null) {
            return registry.stream().findAny().orElse(DEFAULT);
        }

        return registry.getTag(tag).flatMap(taggedLetters -> taggedLetters.stream().findAny()).map(Holder::value).orElse(DEFAULT);
    }

    public MutableComponent name() {
        return Component.translatable("weathered_letter." + id().toLanguageKey());
    }

    public MutableComponent author() {
        return Component.translatable("weathered_letter." + id().toLanguageKey() + ".author");
    }

    public MutableComponent text() {
        return Component.translatable("weathered_letter." + id().toLanguageKey() + ".text");
    }
}
