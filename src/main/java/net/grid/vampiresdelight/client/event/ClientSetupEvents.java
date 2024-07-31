package net.grid.vampiresdelight.client.event;

import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.client.particle.BlessingParticle;
import net.grid.vampiresdelight.client.renderer.DarkStoneStoveRenderer;
import net.grid.vampiresdelight.common.item.AlchemicalCocktailItem;
import net.grid.vampiresdelight.common.registry.VDBlockEntityTypes;
import net.grid.vampiresdelight.common.registry.VDEntityTypes;
import net.grid.vampiresdelight.common.registry.VDItems;
import net.grid.vampiresdelight.common.registry.VDParticleTypes;
import net.grid.vampiresdelight.common.utility.VDIntegrationUtils;
import net.grid.vampiresdelight.integration.appleskin.VDAppleSkinEventHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.common.NeoForge;

import java.util.stream.Stream;

@EventBusSubscriber(modid = VampiresDelight.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetupEvents {
    public static void init(final FMLClientSetupEvent event) {
        event.enqueueWork(ClientSetupEvents::registerItemProperties);

        //NourishmentBloodOverlay.init();
        //HUDOverlayEvents.init();

        if (VDIntegrationUtils.isModPresent(VDIntegrationUtils.APPLESKIN)) {
            NeoForge.EVENT_BUS.register(new VDAppleSkinEventHandler());
        }
    }

    private static void registerItemProperties() {
        Stream.of(VDItems.DANDELION_BEER_BOTTLE.get(), VDItems.BLOOD_WINE_BOTTLE.get())
                .forEach(item -> ItemProperties.register(item, ResourceLocation.fromNamespaceAndPath(VampiresDelight.MODID, "pouring"), (stack, level, entity, id) ->
                        entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F));

        ItemProperties.register(VDItems.ALCHEMICAL_COCKTAIL.get(), ResourceLocation.fromNamespaceAndPath(VampiresDelight.MODID, "metal_pipe"), (stack, level, entity, id) ->
                AlchemicalCocktailItem.isMetalPipe(stack) ? 1.0F : 0.0F);
    }

    @SubscribeEvent
    public static void onEntityRendererRegister(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(VDEntityTypes.ALCHEMICAL_COCKTAIL.get(), ThrownItemRenderer::new);
    }

    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(VDBlockEntityTypes.DARK_STONE_STOVE.get(), DarkStoneStoveRenderer::new);
    }

    @SuppressWarnings("deprecation")
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void registerParticles(RegisterParticleProvidersEvent event) {
        Minecraft.getInstance().particleEngine.register(VDParticleTypes.BLESSING.get(), BlessingParticle.Provider::new);
    }
}
