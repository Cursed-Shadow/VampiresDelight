package net.grid.vampiresdelight.client.gui;

import net.grid.vampiresdelight.VampiresDelight;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.PageButton;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

import javax.annotation.ParametersAreNullableByDefault;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@MethodsReturnNonnullByDefault
@ParametersAreNullableByDefault
public class WeatheredLetterScreen extends Screen {

    private final static ResourceLocation BACKGROUND_TEXTURE = ResourceLocation.fromNamespaceAndPath(VampiresDelight.MODID, "textures/gui/weathered_letter.png");
    private final int xSize = 186;
    private final int ySize = 186;
    private final int textXSize = 130;
    private final int textYSize = 140;
    private int guiLeft, guiTop;
    private PageButton buttonNext;
    private PageButton buttonPrev;
    private int pageNumber;
    private final ResourceLocation letterId;
    private List<FormattedText> content;

    public WeatheredLetterScreen(ResourceLocation letterId) {
        super(Component.translatable("weathered_letter." + Objects.requireNonNull(letterId).toLanguageKey()));
        this.letterId = letterId;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        if (guiGraphics == null) return;
        super.render(guiGraphics, mouseX, mouseY, partialTick);

        pageNumber = Mth.clamp(pageNumber, 0, content.size() - 1);

        if (pageNumber < content.size()) {
            FormattedText toDraw = content.get(pageNumber);
            Font fontRenderer = Minecraft.getInstance().font;

            List<FormattedCharSequence> cutLines = fontRenderer.split(toDraw, textXSize);
            int y = guiTop + 12;
            for (FormattedCharSequence cut : cutLines) {
                guiGraphics.drawString(fontRenderer, cut, guiLeft + 30, y, 0, false);
                y += 10;
            }
        }

        drawCenteredStringWithoutShadow(guiGraphics, font, String.format("%d/%d", pageNumber + 1, content.size()), guiLeft + xSize / 2, guiTop + ySize * 8 / 9, 0);
        guiGraphics.drawCenteredString(font, title, guiLeft + xSize / 2, guiTop - 10, Color.WHITE.getRGB());

        buttonPrev.visible = pageNumber != 0;
        buttonNext.visible = pageNumber != content.size() - 1 && !content.isEmpty();
    }

    public static void drawCenteredStringWithoutShadow(@NotNull GuiGraphics graphics, @NotNull Font p_238471_1_, @NotNull String p_238471_2_, int p_238471_3_, int p_238471_4_, int p_238471_5_) {
        graphics.drawString(p_238471_1_, p_238471_2_, (p_238471_3_ - p_238471_1_.width(p_238471_2_) / 2), p_238471_4_, p_238471_5_, false);
    }

    @Override
    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        if (guiGraphics == null) return;
        super.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        guiGraphics.blit(BACKGROUND_TEXTURE, guiLeft, guiTop, 0, 0, xSize, ySize);
    }

    @Override
    protected void init() {
        super.init();

        guiLeft = (this.width - this.xSize) / 2;
        guiTop = (this.height - this.ySize) / 2;

        this.addRenderableWidget(buttonNext = new LetterButton(guiLeft + xSize * 4 / 6, guiTop + ySize * 8 / 9, true, (btn) -> {
            if (pageNumber + 1 < content.size()) {
                nextPage();
            }
        }, true));
        this.addRenderableWidget(buttonPrev = new LetterButton(guiLeft + xSize / 5, guiTop + ySize * 8 / 9, false, (btn) -> {
            if (pageNumber > 0) {
                prevPage();
            }
        }, true));

        content = prepareForLongText(Component.translatable("weathered_letter." + letterId.toLanguageKey() + ".text"), textXSize, textYSize);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (this.minecraft != null && (keyCode == GLFW.GLFW_KEY_BACKSPACE || keyCode == this.minecraft.options.keyUse.getKey().getValue())) {
            this.minecraft.setScreen(null);
            return true;
        } else if ((keyCode == GLFW.GLFW_KEY_UP || keyCode == GLFW.GLFW_KEY_RIGHT) && pageNumber + 1 < content.size()) {
            nextPage();
            return true;
        } else if ((keyCode == GLFW.GLFW_KEY_DOWN || keyCode == GLFW.GLFW_KEY_LEFT) && pageNumber > 0) {
            prevPage();
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        if (scrollY < 0) {
            nextPage();
        } else if (scrollY > 0) {
            prevPage();
        }
        if (scrollX < 0) {
            nextPage();
        } else if (scrollX > 0) {
            prevPage();
        }

        return (scrollX != 0 || scrollY != 0) || super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
    }

    public void nextPage() {
        if (pageNumber != content.size() - 1 && !content.isEmpty()) {
            pageNumber++;
        }
    }

    public void prevPage() {
        if (pageNumber != 0) {
            pageNumber--;
        }
    }

    private static @NotNull List<FormattedText> prepareForLongText(@NotNull Component text, int lineWidth, int firstHeight) {
        Font fontRenderer = Minecraft.getInstance().font;

        int count = firstHeight / fontRenderer.lineHeight;
        List<FormattedText> lines = new ArrayList<>(fontRenderer.getSplitter().splitLines(text, lineWidth, Style.EMPTY));
        List<FormattedText> pages = new ArrayList<>();

        while (!lines.isEmpty()) {
            List<FormattedText> pageLines = new ArrayList<>();
            if (pages.isEmpty() && lines.size() > count) {
                pageLines.addAll(lines.subList(0, count));
                lines = lines.subList(count, lines.size());
            } else {
                int limit = Math.min(count, lines.size());
                pageLines.addAll(lines.subList(0, limit));
                lines = lines.subList(limit, lines.size());
            }
            pages.add(combineWithNewLine(pageLines));
        }

        return pages;
    }

    private static @NotNull FormattedText combineWithNewLine(@NotNull List<FormattedText> elements) {
        FormattedText newLine = Component.literal("\n");
        List<FormattedText> copy = new ArrayList<>(elements.size() * 2);
        for (int i = 0; i < elements.size() - 1; i++) {
            copy.add(elements.get(i));
            copy.add(newLine);
        }
        copy.add(elements.getLast());
        return FormattedText.composite(copy);
    }
}
