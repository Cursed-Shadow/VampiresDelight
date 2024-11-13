package net.grid.vampiresdelight.common.item;

import net.grid.vampiresdelight.common.item.component.WeatheredLetter;
import net.grid.vampiresdelight.common.network.OpenWeatheredLetterPacket;
import net.grid.vampiresdelight.common.registry.VDItems;
import net.grid.vampiresdelight.common.registry.VDRegistries;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
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

import java.util.List;

public class WeatheredLetterItem extends Item {
    public WeatheredLetterItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack stack = player.getItemInHand(usedHand);
        if (!level.isClientSide && player instanceof ServerPlayer serverPlayer) {
            serverPlayer.connection.send(new OpenWeatheredLetterPacket(WeatheredLetter.get(stack)));
        }
        return new InteractionResultHolder<>(InteractionResult.SUCCESS, stack);
    }

    @Override
    public Component getName(ItemStack stack) {
        return WeatheredLetter.get(stack).name();
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(WeatheredLetter.get(stack).author().withStyle(ChatFormatting.GRAY));
    }

    public static void generateCreativeTab(CreativeModeTab.Output output) {
        ClientLevel level = Minecraft.getInstance().level;

        if (level != null) {
            Registry<WeatheredLetter> registry = level.registryAccess().registryOrThrow(VDRegistries.WEATHERED_LETTER);

            registry.stream().findAny().ifPresent(letter -> output.accept(makeLetter(letter), CreativeModeTab.TabVisibility.PARENT_TAB_ONLY));
            registry.stream().forEach(letter -> output.accept(makeLetter(letter), CreativeModeTab.TabVisibility.SEARCH_TAB_ONLY));
        }
    }

    public static ItemStack makeLetter(WeatheredLetter letter) {
        ItemStack stack = new ItemStack(VDItems.WEATHERED_LETTER.get());
        WeatheredLetter.addToStack(stack, letter);
        return stack;
    }
}
