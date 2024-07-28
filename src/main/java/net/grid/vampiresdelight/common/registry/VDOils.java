package net.grid.vampiresdelight.common.registry;

import de.teamlapen.vampirism.api.VampirismRegistries;
import de.teamlapen.vampirism.api.items.oil.IOil;
import de.teamlapen.vampirism.items.oil.EffectWeaponOil;
import net.grid.vampiresdelight.VampiresDelight;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class VDOils {
    public static final DeferredRegister<IOil> OILS = DeferredRegister.create(VampirismRegistries.OIL.get(), VampiresDelight.MODID);

    public static final DeferredHolder<IOil, EffectWeaponOil> BLESSING = OILS.register("blessing",
            () -> new EffectWeaponOil(VDEffects.BLESSING, 200, 20));
    public static final DeferredHolder<IOil, EffectWeaponOil> CLOTHES_DISSOLVING = OILS.register("clothes_dissolving",
            () -> new EffectWeaponOil(VDEffects.CLOTHES_DISSOLVING, 50, 15));
    public static final DeferredHolder<IOil, EffectWeaponOil> FOG_VISION = OILS.register("fog_vision",
            () -> new EffectWeaponOil(VDEffects.FOG_VISION, 200, 20));
}
