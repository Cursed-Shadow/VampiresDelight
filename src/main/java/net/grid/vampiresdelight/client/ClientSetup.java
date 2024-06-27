package net.grid.vampiresdelight.client;

import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.client.event.HUDOverlayEvents;
import net.grid.vampiresdelight.client.gui.BrewingBarrelScreen;
import net.grid.vampiresdelight.client.gui.NourishmentBloodOverlay;
import net.grid.vampiresdelight.common.registry.VDItems;
import net.grid.vampiresdelight.common.registry.VDMenuTypes;
import net.grid.vampiresdelight.common.utility.VDIntegrationUtils;
import net.grid.vampiresdelight.integration.appleskin.VDAppleSkinEventHandler;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.stream.Stream;

public class ClientSetup {
    public static void init(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            //registerItemProperties();
            MenuScreens.register(VDMenuTypes.BREWING_BARREL.get(), BrewingBarrelScreen::new);
        });

        NourishmentBloodOverlay.init();
        HUDOverlayEvents.init();

        if (VDIntegrationUtils.isModPresent(VDIntegrationUtils.APPLESKIN)) {
            MinecraftForge.EVENT_BUS.register(new VDAppleSkinEventHandler());
        }
    }

    private static void registerItemProperties() {
        Stream.of(VDItems.BLOOD_WINE_BOTTLE.get())
                .forEach(item -> ItemProperties.register(item, new ResourceLocation(VampiresDelight.MODID, "pouring"), (stack, level, entity, id) ->
                        entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F));
    }
}
