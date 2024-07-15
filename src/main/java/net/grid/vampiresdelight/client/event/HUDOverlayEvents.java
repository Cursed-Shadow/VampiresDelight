package net.grid.vampiresdelight.client.event;

import net.grid.vampiresdelight.common.utility.VDIntegrationUtils;
import squeek.appleskin.util.IntPoint;

import java.util.Random;
import java.util.Vector;

public class HUDOverlayEvents {
    private static float unclampedFlashAlpha = 0f;
    private static float flashAlpha = 0f;
    private static byte alphaDir = 1;
    protected static int bloodIconsOffset;

    public static final Vector<IntPoint> bloodBarOffsets = new Vector<>();

    private static final Random random = new Random();

    public static void init() {
        if (VDIntegrationUtils.isModPresent(VDIntegrationUtils.APPLESKIN)) {
            //MinecraftForge.EVENT_BUS.register(new HUDOverlayEvents());
        }
    }

    /*
    static ResourceLocation BLOOD_BAR_ELEMENT = VDHelper.BLOOD_BAR_ELEMENT;

    static ResourceLocation VP_ICONS = new ResourceLocation(REFERENCE.MODID, "textures/gui/icons.png");

    @SubscribeEvent
    public void onRenderGuiOverlayPre(RenderGuiOverlayEvent.Pre event) {
        if (event.getOverlay() == GuiOverlayManager.findOverlay(BLOOD_BAR_ELEMENT))
        {
            Minecraft mc = Minecraft.getInstance();
            ForgeGui gui = (ForgeGui) mc.gui;
            LocalPlayer player = mc.player;
            if (player == null) return;

            boolean isMounted = player.getVehicle() instanceof LivingEntity;
            if (!isMounted && !mc.options.hideGui && gui.shouldDrawSurvivalElements())
            {
                renderExhaustion(gui, event.getGuiGraphics(), event.getPartialTick(), event.getWindow().getScreenWidth(), event.getWindow().getScreenHeight());
            }
        }
    }

    public static void renderExhaustion(ForgeGui gui, GuiGraphics guiGraphics, float partialTicks, int screenWidth, int screenHeight) {
        bloodIconsOffset = gui.rightHeight;

        if (!ModConfig.SHOW_FOOD_EXHAUSTION_UNDERLAY.get())
            return;

        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        assert player != null;

        int right = mc.getWindow().getGuiScaledWidth() / 2 + 91;
        int top = mc.getWindow().getGuiScaledHeight() - bloodIconsOffset;
        float exhaustion = player.getFoodData().getExhaustionLevel();

        drawExhaustionOverlay(exhaustion, mc, guiGraphics, right, top, 1f);
    }

    public static void drawExhaustionOverlay(float exhaustion, Minecraft mc, GuiGraphics guiGraphics, int right, int top, float alpha) {
        float maxExhaustion = HungerHelper.getMaxExhaustion(mc.player);
        // clamp between 0 and 1
        float ratio = Math.min(1, Math.max(0, exhaustion / maxExhaustion));
        int width = (int) (ratio * 81);
        int height = 9;

        enableAlpha(.75f);
        guiGraphics.blit(VP_ICONS, right - width, top, 81 - width, 18, width, height);
        disableAlpha();

        // rebind default icons
        RenderSystem.setShaderTexture(0, TextureHelper.MC_ICONS);
    }

    public static void enableAlpha(float alpha) {
        RenderSystem.enableBlend();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, alpha);
        RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    }

    public static void disableAlpha() {
        RenderSystem.disableBlend();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    }

    @SubscribeEvent
    public void onRenderGuiOverlayPost(RenderGuiOverlayEvent.Post event) {
        if (event.getOverlay() == GuiOverlayManager.findOverlay(BLOOD_BAR_ELEMENT))
        {
            Minecraft mc = Minecraft.getInstance();
            ForgeGui gui = (ForgeGui) mc.gui;
            LocalPlayer player = mc.player;
            if (player == null) return;

            boolean isMounted = player.getVehicle() instanceof LivingEntity;
            if (!isMounted && !mc.options.hideGui && gui.shouldDrawSurvivalElements())
            {
                renderFoodOrHealthOverlay(gui, event.getGuiGraphics(), event.getPartialTick(), event.getWindow().getScreenWidth(), event.getWindow().getScreenHeight());
            }
        }
    }

    public static void renderFoodOrHealthOverlay(ForgeGui gui, GuiGraphics guiGraphics, float partialTicks, int screenWidth, int screenHeight) {
        if (!shouldRenderAnyOverlays()) {
            return;
        }

        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if (player == null) return;

        FoodData stats = player.getFoodData();

        int top = mc.getWindow().getGuiScaledHeight() - bloodIconsOffset;
        int left = mc.getWindow().getGuiScaledWidth() / 2 - 91; // left of health bar
        int right = mc.getWindow().getGuiScaledWidth() / 2 + 91; // right of food bar

        generateBloodBarOffsets(top, left, right, mc.gui.getGuiTicks(), player);

        drawSaturationOverlay(mc, 0, 1f);

        // try to get the item stack in the player hand
        ItemStack heldItem = player.getMainHandItem();
        if (ModConfig.SHOW_FOOD_VALUES_OVERLAY_WHEN_OFFHAND.get() && !FoodHelper.canConsume(heldItem, player)) {
            heldItem = player.getOffhandItem();
        }

        boolean shouldRenderHeldItemValues = !heldItem.isEmpty() && FoodHelper.canConsume(heldItem, player);
        if (!shouldRenderHeldItemValues) {
            resetFlash();
            return;
        }

        if (!ModConfig.SHOW_FOOD_VALUES_OVERLAY.get()) {
            return;
        }

        FoodProperties foodProperties = heldItem.getFoodProperties(player);
        FoodValues foodValues = (foodProperties != null) ? new FoodValues(foodProperties.getNutrition(), foodProperties.getSaturationModifier()) : new FoodValues(0, 0);

        int foodHunger = foodValues.hunger;
        float foodSaturationIncrement = foodValues.getSaturationIncrement();

        // restored hunger/saturation overlay while holding food
        drawHungerOverlay(mc, foodHunger, flashAlpha, FoodHelper.isRotten(heldItem, player));

        int newFoodValue = stats.getFoodLevel() + foodHunger;
        float newSaturationValue = stats.getSaturationLevel() + foodSaturationIncrement;
        float saturationGained = newSaturationValue > newFoodValue ? newFoodValue - stats.getSaturationLevel() : foodSaturationIncrement;

        // Redraw saturation overlay for gained
        drawSaturationOverlay(mc, saturationGained, flashAlpha);
    }

    private static boolean shouldRenderAnyOverlays() {
        return ModConfig.SHOW_FOOD_VALUES_OVERLAY.get() || ModConfig.SHOW_SATURATION_OVERLAY.get() || ModConfig.SHOW_FOOD_HEALTH_HUD_OVERLAY.get();
    }

    private static void generateBloodBarOffsets(int top, int left, int right, int ticks, Player player) {
        final int preferFoodBars = 10;

        boolean shouldAnimatedFood = false;

        // when some mods using custom render, we need to least provide an option to cancel animation
        if (ModConfig.SHOW_VANILLA_ANIMATION_OVERLAY.get())
        {
            FoodData stats = player.getFoodData();

            // in vanilla saturation level is zero will show hunger animation
            float saturationLevel = stats.getSaturationLevel();
            int foodLevel = stats.getFoodLevel();
            shouldAnimatedFood = saturationLevel <= 0.0F && ticks % (foodLevel * 3 + 1) == 0;
        }

        if (bloodBarOffsets.size() != preferFoodBars)
            bloodBarOffsets.setSize(preferFoodBars);

        // right alignment, single row
        for (int i = 0; i < preferFoodBars; ++i)
        {
            int x = right - i * 8 - 9;
            int y = top;

            // apply the animated offset
            if (shouldAnimatedFood)
                y += random.nextInt(3) - 1;

            // reuse the point object to reduce memory usage
            IntPoint point = foodBarOffsets.get(i);
            if (point == null)
            {
                point = new IntPoint();
                foodBarOffsets.set(i, point);
            }

            point.x = x - right;
            point.y = y - top;
        }
    }
     */
}
