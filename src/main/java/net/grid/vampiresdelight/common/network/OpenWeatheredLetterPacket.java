package net.grid.vampiresdelight.common.network;

import net.grid.vampiresdelight.VampiresDelight;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record OpenWeatheredLetterPacket(ResourceLocation letterId) implements CustomPacketPayload {
    public static final Type<OpenWeatheredLetterPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(VampiresDelight.MODID, "open_weathered_letter"));
    public static final StreamCodec<RegistryFriendlyByteBuf, OpenWeatheredLetterPacket> CODEC = StreamCodec.composite(ResourceLocation.STREAM_CODEC, OpenWeatheredLetterPacket::letterId, OpenWeatheredLetterPacket::new);

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
