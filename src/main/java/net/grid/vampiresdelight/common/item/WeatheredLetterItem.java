package net.grid.vampiresdelight.common.item;

import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.network.OpenWeatheredLetterPacket;
import net.grid.vampiresdelight.common.registry.VDDataComponents;
import net.grid.vampiresdelight.common.registry.VDItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.Arrays;
import java.util.List;

public class WeatheredLetterItem extends Item {

    public static ResourceLocation[] ALL_LETTERS = new ResourceLocation[] {
            ResourceLocation.fromNamespaceAndPath(VampiresDelight.MODID, "dear_marcus"),
            ResourceLocation.fromNamespaceAndPath(VampiresDelight.MODID, "martha")
    };

    public WeatheredLetterItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack stack = player.getItemInHand(usedHand);
        ResourceLocation letterId = stack.get(VDDataComponents.WEATHERED_LETTER);
        if (!level.isClientSide && player instanceof ServerPlayer serverPlayer && letterId != null) {
            serverPlayer.connection.send(new OpenWeatheredLetterPacket(letterId));
        }
        return new InteractionResultHolder<>(InteractionResult.SUCCESS, stack);
    }

    @Override
    public Component getName(ItemStack stack) {
        ResourceLocation letterId = stack.get(VDDataComponents.WEATHERED_LETTER);
        if (letterId != null) {
            return Component.translatable("weathered_letter." + letterId.toLanguageKey());
        }

        return super.getName(stack);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        ResourceLocation letterId = stack.get(VDDataComponents.WEATHERED_LETTER);
        if (letterId != null) {
            tooltipComponents.add(Component.translatable("weathered_letter." + letterId.toLanguageKey() + ".author").withStyle(ChatFormatting.GRAY));
        }
    }

    public static void generateCreativeTab(CreativeModeTab.Output output) {
        Arrays.stream(ALL_LETTERS).findAny().ifPresent(letter -> output.accept(makeLetter(letter), CreativeModeTab.TabVisibility.PARENT_TAB_ONLY));
        Arrays.stream(ALL_LETTERS).forEach(letter -> output.accept(makeLetter(letter), CreativeModeTab.TabVisibility.SEARCH_TAB_ONLY));
    }

    public static ItemStack makeLetter(ResourceLocation letter) {
        ItemStack stack = new ItemStack(VDItems.WEATHERED_LETTER.get());
        stack.set(VDDataComponents.WEATHERED_LETTER.get(), letter);
        return stack;
    }
}
