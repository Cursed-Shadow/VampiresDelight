package net.grid.vampiresdelight.client.event;

import de.teamlapen.vampirism.VampirismMod;
import de.teamlapen.vampirism.api.event.VampireFogEvent;
import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.VDConfiguration;
import net.grid.vampiresdelight.common.registry.VDBlocks;
import net.grid.vampiresdelight.common.registry.VDEffects;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

import java.util.Objects;

@SuppressWarnings("unused")
@EventBusSubscriber(modid = VampiresDelight.MODID, value = Dist.CLIENT)
public class RenderEventHandler {
    @SubscribeEvent
    public static void onVampireFogEvent(VampireFogEvent event) {
        Player player = VampirismMod.proxy.getClientPlayer();
        if (player == null) return;

        float multiplier = player.hasEffect(VDEffects.FOG_VISION) ? Objects.requireNonNull(player.getEffect(VDEffects.FOG_VISION)).getAmplifier() + 1 : 0;

        if (player.getItemInHand(InteractionHand.MAIN_HAND).getItem() == VDBlocks.SPIRIT_LANTERN.get().asItem() || player.getItemInHand(InteractionHand.OFF_HAND).getItem() == VDBlocks.SPIRIT_LANTERN.get().asItem()) {
            multiplier += VDConfiguration.SPIRIT_LANTERN_FOG_DISTANCE_MULTIPLIER.get();
        }

        event.setFogDistanceMultiplier(event.getFogDistanceMultiplier() + multiplier);
    }
}
