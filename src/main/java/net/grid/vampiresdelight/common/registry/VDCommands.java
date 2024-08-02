package net.grid.vampiresdelight.common.registry;

import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.command.HungerBarCommand;
import net.minecraft.commands.CommandSourceStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@EventBusSubscriber(modid = VampiresDelight.MODID)
public class VDCommands {
    @SubscribeEvent
    static void registerCommands(@NotNull RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();

        List<String> vampires_delight = Lists.newArrayList("vampires-delight");

        for (String s : vampires_delight) {
            dispatcher.register(
                    LiteralArgumentBuilder.<CommandSourceStack>literal(s)
                            .then(HungerBarCommand.register())
            );
        }
    }
}
