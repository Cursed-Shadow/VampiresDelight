package net.grid.vampiresdelight.common.utility;

import de.teamlapen.vampirism.api.VReference;
import de.teamlapen.vampirism.api.entity.factions.IPlayableFaction;
import net.grid.vampiresdelight.common.VDConfiguration;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Objects;

@ParametersAreNonnullByDefault
public class VDTooltipUtils {
    public static void addSplitTooltip(String key, List<Component> tooltip, ChatFormatting style) {
        for (String part : VDTextUtils.getStraightTranslation(key).split("\n")) {
            tooltip.add(Component.literal(part).withStyle(style));
        }
    }

    public static void addShiftTooltip(String key, List<Component> tooltip) {
        tooltip.add(Screen.hasShiftDown() ?
                VDTextUtils.getTranslation(key).withStyle(ChatFormatting.GRAY) :
                VDTextUtils.getTranslation("tooltip.hold_shift_for_info", VDTextUtils.getTranslation("tooltip.shift").withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY));
    }

    public static void addFactionFoodToolTips(List<Component> tooltip, Player player, IPlayableFaction<?> foodFaction) {
        if (!VDConfiguration.FACTION_TOOLTIPS.get()) {
            return;
        }

        if (!VDHelper.isVampire(player) && !VDConfiguration.HUNTER_TOOLTIPS_FOR_EVERYONE.get()) {
            if (!Objects.equals(foodFaction, VReference.VAMPIRE_FACTION))
                return;
        }

        ChatFormatting color = VDHelper.isVampire(player) ?
                Objects.equals(foodFaction, VReference.VAMPIRE_FACTION) ? ChatFormatting.DARK_GREEN : ChatFormatting.DARK_RED :
                Objects.equals(foodFaction, VReference.VAMPIRE_FACTION) ? ChatFormatting.DARK_RED : ChatFormatting.DARK_GREEN;

        tooltip.add(Component.empty());
        tooltip.add(VDTextUtils.getTranslation("tooltip.for_faction", foodFaction.getName().copy().withStyle(color)).withStyle(ChatFormatting.GRAY));
    }

    public static void addWerewolfFactionFoodToolTips(List<Component> tooltip, Player player) {
        if (!VDConfiguration.FACTION_TOOLTIPS.get()) {
            return;
        }
        tooltip.add(Component.empty());
        ChatFormatting color = VDIntegrationUtils.isWerewolf(player) ? ChatFormatting.DARK_GREEN : ChatFormatting.DARK_RED;
        tooltip.add(VDTextUtils.getTranslation("tooltip.for_faction", Component.translatable("text.werewolves.werewolf").withStyle(color)).withStyle(ChatFormatting.GRAY));
    }
}
