package net.grid.vampiresdelight.common.registry;

import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.effect.ClothesDissolvingEffect;
import net.grid.vampiresdelight.common.effect.FogVisionEffect;
import net.grid.vampiresdelight.common.effect.BlessingEffect;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.neoforge.registries.DeferredRegister;

public class VDEffects {
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT, VampiresDelight.MODID);

    public static final Holder<MobEffect> BLESSING = EFFECTS.register("blessing", BlessingEffect::new);
    public static final Holder<MobEffect> CLOTHES_DISSOLVING = EFFECTS.register("clothes_dissolving", ClothesDissolvingEffect::new);
    public static final Holder<MobEffect> FOG_VISION = EFFECTS.register("fog_vision", FogVisionEffect::new);
}
