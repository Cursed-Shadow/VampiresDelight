package net.grid.vampiresdelight.common.registry;

import com.mojang.serialization.Codec;
import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.client.particle.DrinkSplashOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

public class VDParticleTypes {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, VampiresDelight.MODID);

    public static final RegistryObject<SimpleParticleType> BLESSING = PARTICLE_TYPES.register("blessing",
            () -> new SimpleParticleType(true));
    /*
    public static final RegistryObject<ParticleType<DrinkSplashOptions>> DRINK_SPLASH = PARTICLE_TYPES.register("drink_splashes",
            () -> new ParticleType<>(false, DrinkSplashOptions.DESERIALIZER) {
                @Override
                public @NotNull Codec<DrinkSplashOptions> codec() {
                    return DrinkSplashOptions.CODEC;
                }
            });
     */
    public static final RegistryObject<SimpleParticleType> DRINK_SPLASH = PARTICLE_TYPES.register("drink_splashes",
            () -> new SimpleParticleType(true));
}
