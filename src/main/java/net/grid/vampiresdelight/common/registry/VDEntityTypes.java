package net.grid.vampiresdelight.common.registry;

import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.entity.AlchemicalCocktailEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class VDEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(Registries.ENTITY_TYPE, VampiresDelight.MODID);

    public static final Supplier<EntityType<AlchemicalCocktailEntity>> ALCHEMICAL_COCKTAIL = ENTITIES.register("alchemical_cocktail", () -> (
            EntityType.Builder.<AlchemicalCocktailEntity>of(AlchemicalCocktailEntity::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .clientTrackingRange(4)
                    .updateInterval(10)
                    .build("alchemical_cocktail")));
}
