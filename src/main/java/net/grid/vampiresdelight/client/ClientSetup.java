package net.grid.vampiresdelight.client;

import net.grid.vampiresdelight.client.gui.BrewingBarrelScreen;
import net.grid.vampiresdelight.client.gui.NourishmentBloodOverlay;
import net.grid.vampiresdelight.common.registry.VDMenuTypes;
import net.grid.vampiresdelight.common.utility.VDIntegrationUtils;
import net.grid.vampiresdelight.integration.appleskin.VDAppleSkinEventHandler;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientSetup {
    public static void init(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> MenuScreens.register(VDMenuTypes.BREWING_BARREL.get(), BrewingBarrelScreen::new));

        NourishmentBloodOverlay.init();

        if (VDIntegrationUtils.isModPresent(VDIntegrationUtils.APPLESKIN)) {
            MinecraftForge.EVENT_BUS.register(new VDAppleSkinEventHandler());
        }
    }
}
