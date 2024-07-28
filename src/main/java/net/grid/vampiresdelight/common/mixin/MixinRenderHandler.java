package net.grid.vampiresdelight.common.mixin;

import de.teamlapen.vampirism.VampirismMod;
import de.teamlapen.vampirism.client.renderer.RenderHandler;
import de.teamlapen.vampirism.config.VampirismConfig;
import net.grid.vampiresdelight.common.registry.VDBlocks;
import net.grid.vampiresdelight.common.registry.VDEffects;
import net.grid.vampiresdelight.common.registry.VDItems;
import net.grid.vampiresdelight.common.utility.VDHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(RenderHandler.class)
public class MixinRenderHandler {
    @Shadow
    private float vampireBiomeFogDistanceMultiplier = 1;

    @Inject(at = @At("TAIL"), method = "onClientTick(Lnet/minecraftforge/event/TickEvent$ClientTickEvent;)V", remap = false)
    public void onFogClientTick(TickEvent.@NotNull ClientTickEvent event, CallbackInfo ci) {
        Player player = VampirismMod.proxy.getClientPlayer();
        if (player != null && player.tickCount % 10 == 0) {
            if ((VampirismConfig.CLIENT.renderVampireForestFog.get() || VampirismConfig.SERVER.enforceRenderForestFog.get()) && (VDHelper.isEntityInArtificalVampireFogArea(player) || VDHelper.isEntityInVampireBiome(player))) {
                float fogDistanceMultiplier = player.hasEffect(VDEffects.FOG_VISION.get()) ? Objects.requireNonNull(player.getEffect(VDEffects.FOG_VISION.get())).getAmplifier() + 1 : 0;

                if (player.getItemInHand(InteractionHand.MAIN_HAND).getItem() == VDBlocks.SPIRIT_LANTERN.get().asItem() || player.getItemInHand(InteractionHand.OFF_HAND).getItem() == VDBlocks.SPIRIT_LANTERN.get().asItem())
                    fogDistanceMultiplier += 0.4f;

                vampireBiomeFogDistanceMultiplier += fogDistanceMultiplier;
            }
        }
    }
}
