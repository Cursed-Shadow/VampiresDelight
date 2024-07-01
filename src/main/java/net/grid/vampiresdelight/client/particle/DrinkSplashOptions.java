package net.grid.vampiresdelight.client.particle;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.grid.vampiresdelight.common.registry.VDParticleTypes;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.registries.ForgeRegistries;

public class DrinkSplashOptions implements ParticleOptions {
    protected final int color;

    public static final Codec<DrinkSplashOptions> CODEC = RecordCodecBuilder.create((instance) -> instance
            .group(Codec.INT.fieldOf("color").forGetter((options) -> options.color))
            .apply(instance, DrinkSplashOptions::new));

    public static final ParticleOptions.Deserializer<DrinkSplashOptions> DESERIALIZER = new ParticleOptions.Deserializer<>() {
        @Override
        public DrinkSplashOptions fromCommand(ParticleType<DrinkSplashOptions> pParticleType, StringReader pReader) throws CommandSyntaxException {
            return new DrinkSplashOptions(pReader.readInt());
        }

        @Override
        public DrinkSplashOptions fromNetwork(ParticleType<DrinkSplashOptions> pParticleType, FriendlyByteBuf pBuffer) {
            return new DrinkSplashOptions(pBuffer.readVarInt());
        }
    };

    public DrinkSplashOptions(int color) {
        this.color = color;
    }

    @Override
    public ParticleType<?> getType() {
        return VDParticleTypes.DRINK_SPLASH.get();
    }

    @Override
    public void writeToNetwork(FriendlyByteBuf pBuffer) {
        pBuffer.writeVarInt(this.color);
    }

    @Override
    public String writeToString() {
        return ForgeRegistries.PARTICLE_TYPES.getKey(this.getType()) + " " + color;
    }
}
