package net.grid.vampiresdelight.common.registry;

import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.grid.vampiresdelight.common.command.HungerBarCommand;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraftforge.event.RegisterCommandsEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class VDCommands {
    public static void onCommandsRegister(@NotNull RegisterCommandsEvent event) {
        registerCommands(event.getDispatcher(), event.getBuildContext());
    }

    public static void registerCommands(@NotNull CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext buildContext) {
        List<String> vampires_delight = Lists.newArrayList("vampires-delight");

        for (String s : vampires_delight) {
            dispatcher.register(
                    LiteralArgumentBuilder.<CommandSourceStack>literal(s)
                            .then(HungerBarCommand.register())
            );
        }
    }
}
