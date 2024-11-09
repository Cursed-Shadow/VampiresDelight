package net.grid.vampiresdelight.client.gui;

import net.grid.vampiresdelight.VampiresDelight;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.PageButton;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class LetterButton extends PageButton {

    private static final ResourceLocation LETTER_FORWARD_HIGHLIGHTED_SPRITE = ResourceLocation.fromNamespaceAndPath(VampiresDelight.MODID, "widget/letter_forward_highlighted");
    private static final ResourceLocation LETTER_FORWARD_SPRITE = ResourceLocation.fromNamespaceAndPath(VampiresDelight.MODID, "widget/letter_forward");
    private static final ResourceLocation LETTER_BACKWARD_HIGHLIGHTED_SPRITE = ResourceLocation.fromNamespaceAndPath(VampiresDelight.MODID, "widget/letter_backward_highlighted");
    private static final ResourceLocation LETTER_BACKWARD_SPRITE = ResourceLocation.fromNamespaceAndPath(VampiresDelight.MODID, "widget/letter_backward");
    private final boolean isForward;

    public LetterButton(int x, int y, boolean isForward, OnPress onPress, boolean playTurnSound) {
        super(x, y, isForward, onPress, playTurnSound);
        this.isForward = isForward;
    }

    @Override
    public void renderWidget(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        ResourceLocation resourcelocation;
        if (this.isForward) {
            resourcelocation = this.isHoveredOrFocused() ? LETTER_FORWARD_HIGHLIGHTED_SPRITE : LETTER_FORWARD_SPRITE;
        } else {
            resourcelocation = this.isHoveredOrFocused() ? LETTER_BACKWARD_HIGHLIGHTED_SPRITE : LETTER_BACKWARD_SPRITE;
        }

        guiGraphics.blitSprite(resourcelocation, this.getX(), this.getY(), 23, 13);
    }
}
