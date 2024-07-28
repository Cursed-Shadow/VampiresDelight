package net.grid.vampiresdelight.client.event;

import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.client.particle.BlessingParticle;
import net.grid.vampiresdelight.client.particle.DrinkSplashParticle;
import net.grid.vampiresdelight.client.renderer.DarkStoneStoveRenderer;
import net.grid.vampiresdelight.common.registry.VDBlockEntityTypes;
import net.grid.vampiresdelight.common.registry.VDEntityTypes;
import net.grid.vampiresdelight.common.registry.VDParticleTypes;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;

@Mod.EventBusSubscriber(modid = VampiresDelight.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetupEvents {

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
