package net.grid.vampiresdelight.common.registry;

import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.effect.ClothesDissolvingEffect;
import net.grid.vampiresdelight.common.effect.BlessingEffect;
import net.grid.vampiresdelight.common.effect.DefaultEffect;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.awt.*;

public class VDEffects {
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT, VampiresDelight.MODID);

    public static final DeferredHolder<MobEffect, MobEffect> BLESSING = EFFECTS.register("blessing",
            () -> new BlessingEffect(MobEffectCategory.BENEFICIAL, new Color(250, 218, 94).getRGB()));
    public static final DeferredHolder<MobEffect, MobEffect> CLOTHES_DISSOLVING = EFFECTS.register("clothes_dissolving",
            () -> new ClothesDissolvingEffect(MobEffectCategory.HARMFUL, new Color(206, 177, 128).getRGB()));
    public static final DeferredHolder<MobEffect, MobEffect> FOG_VISION = EFFECTS.register("fog_vision",
            () -> new DefaultEffect(MobEffectCategory.BENEFICIAL, new Color(135, 105, 150).getRGB()));
}
