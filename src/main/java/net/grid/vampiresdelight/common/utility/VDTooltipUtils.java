package net.grid.vampiresdelight.common.utility;

import de.teamlapen.vampirism.api.VReference;
import de.teamlapen.vampirism.api.entity.factions.IPlayableFaction;
import net.grid.vampiresdelight.common.VDConfiguration;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
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
        if (Screen.hasShiftDown()) {
            tooltip.add(VDTextUtils.getTranslation(key).withStyle(ChatFormatting.GRAY));
        } else {
            String[] mainTooltip = VDTextUtils.getStraightTranslation("tooltip.holdShiftForInfo").split("%s");
            MutableComponent resultTooltip = Component.empty()
                    .append(Component.literal(mainTooltip[0]).withStyle(ChatFormatting.DARK_GRAY))
                    .append(Component.literal(VDTextUtils.getStraightTranslation("tooltip.shift")).withStyle(ChatFormatting.GRAY))
                    .append(Component.literal(mainTooltip[1]).withStyle(ChatFormatting.DARK_GRAY));

            tooltip.add(resultTooltip);
        }
    }

    public static void addFactionFoodToolTips(List<Component> tooltip, Player player, IPlayableFaction<?> foodFaction) {
        if (!VDHelper.isVampire(player) && !VDConfiguration.HUNTER_TOOLTIPS_FOR_EVERYONE.get()) {
            if (!Objects.equals(foodFaction, VReference.VAMPIRE_FACTION))
                return;
        }

        tooltip.add(Component.empty());
        tooltip.add(Component.translatable("text.vampirism.faction_specifics").withStyle(ChatFormatting.GRAY));
        ChatFormatting color;

        if (Objects.equals(foodFaction, VReference.VAMPIRE_FACTION)) {
            if (VDHelper.isVampire(player)) {
                color = ChatFormatting.DARK_GREEN;
            } else {
                color = ChatFormatting.DARK_RED;
            }
        } else {
            if (!VDHelper.isVampire(player)) {
                color = ChatFormatting.DARK_GREEN;
            } else {
                color = ChatFormatting.DARK_RED;
            }
        }

        tooltip.add(Component.literal(" ").append(foodFaction.getName()).withStyle(color));
    }

    public static void addWerewolfFactionFoodToolTips(List<Component> tooltip, Player player) {
        tooltip.add(Component.empty());
        tooltip.add(Component.translatable("text.vampirism.faction_specifics").withStyle(ChatFormatting.GRAY));
        ChatFormatting color;

        if (VDIntegrationUtils.isWerewolf(player)) {
            color = ChatFormatting.DARK_GREEN;
        } else {
            color = ChatFormatting.DARK_RED;
        }

        tooltip.add(Component.literal(" ").append(Component.translatable("text.werewolves.werewolf")).withStyle(color));
    }
}
