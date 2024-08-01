package net.grid.vampiresdelight.common;

import net.grid.vampiresdelight.common.registry.VDItems;
import net.grid.vampiresdelight.common.registry.VDPotions;
import net.minecraft.world.level.block.DispenserBlock;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

public class CommonSetup {
    public static void init(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            VDPotions.registerPotionMixes();
            registerDispenserBehaviors();
        });
    }

    public static void registerDispenserBehaviors() {
        DispenserBlock.registerProjectileBehavior(VDItems.ALCHEMICAL_COCKTAIL.get());
    }
}
