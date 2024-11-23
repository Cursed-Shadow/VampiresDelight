package net.grid.vampiresdelight.common.network;

import net.grid.vampiresdelight.client.gui.WeatheredLetterScreen;
import net.minecraft.client.Minecraft;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class VDClientPayloadHandler {
    public static void handleWeatheredLetterPacket(OpenWeatheredLetterPacket packet, IPayloadContext context) {
        context.enqueueWork(() -> Minecraft.getInstance().setScreen(new WeatheredLetterScreen(packet.letter())));
    }
}
