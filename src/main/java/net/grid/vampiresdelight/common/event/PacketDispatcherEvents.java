package net.grid.vampiresdelight.common.event;

import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.network.OpenWeatheredLetterPacket;
import net.grid.vampiresdelight.common.network.VDClientPayloadHandler;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = VampiresDelight.MODID, bus = EventBusSubscriber.Bus.MOD)
public class PacketDispatcherEvents {
    private static final String PROTOCOL_VERSION = Integer.toString(1);

    @SubscribeEvent
    public static void registerHandler(RegisterPayloadHandlersEvent event) {
        registerPackets(event.registrar(VampiresDelight.MODID).versioned(PROTOCOL_VERSION));
    }

    @SuppressWarnings("Convert2MethodRef")
    public static void registerPackets(PayloadRegistrar registrar) {
        registrar.playToClient(OpenWeatheredLetterPacket.TYPE, OpenWeatheredLetterPacket.CODEC, (message, context) -> VDClientPayloadHandler.handleWeatheredLetterPacket(message, context));
    }
}
