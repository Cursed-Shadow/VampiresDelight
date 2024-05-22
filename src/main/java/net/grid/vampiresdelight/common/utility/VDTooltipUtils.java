package net.grid.vampiresdelight.common.utility;

import de.teamlapen.vampirism.api.VReference;
import de.teamlapen.vampirism.api.entity.factions.IPlayableFaction;
import de.teamlapen.vampirism.util.Helper;
import net.grid.vampiresdelight.common.VDConfiguration;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Objects;

@ParametersAreNonnullByDefault
public class VDTooltipUtils {
    public static void addFactionFoodToolTips(List<Component> tooltip, @Nullable Player player, IPlayableFaction<?> foodFaction) {
        assert player != null;

        if (!Helper.isVampire(player) && !VDConfiguration.HUNTER_TOOLTIPS_FOR_EVERYONE.get()) {
            if (!Objects.equals(foodFaction, VReference.VAMPIRE_FACTION))
                return;
        }

        tooltip.add(Component.empty());
        tooltip.add(Component.translatable("text.vampirism.faction_specifics").withStyle(ChatFormatting.GRAY));
        ChatFormatting color;

        if (Objects.equals(foodFaction, VReference.VAMPIRE_FACTION)) {
            if (Helper.isVampire(player)) {
                color = ChatFormatting.DARK_GREEN;
            } else {
                color = ChatFormatting.DARK_RED;
            }
        } else {
            if (!Helper.isVampire(player)) {
                color = ChatFormatting.DARK_GREEN;
            } else {
                color = ChatFormatting.DARK_RED;
            }
        }

        tooltip.add(Component.literal(" ").append(foodFaction.getName()).withStyle(color));
    }

    public static void addWerewolfFactionFoodToolTips(List<Component> tooltip, @Nullable Player player) {
        assert player != null;

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
