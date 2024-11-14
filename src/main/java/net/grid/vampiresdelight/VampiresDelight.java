package net.grid.vampiresdelight;

import net.grid.vampiresdelight.client.event.ClientSetupEventHandler;
import net.grid.vampiresdelight.common.VDConfiguration;
import net.grid.vampiresdelight.common.event.CommonSetupEventHandler;
import net.grid.vampiresdelight.common.registry.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(VampiresDelight.MODID)
public class VampiresDelight {
    public static final String MODID = "vampiresdelight";

    public VampiresDelight(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(CommonSetupEventHandler::setupCommon);
        if (FMLEnvironment.dist.isClient()) {
            modEventBus.addListener(ClientSetupEventHandler::setupClient);
        }

        modContainer.registerConfig(ModConfig.Type.COMMON, VDConfiguration.COMMON_CONFIG);
        modContainer.registerConfig(ModConfig.Type.CLIENT, VDConfiguration.CLIENT_CONFIG);

        if (FMLEnvironment.dist.isClient()) {
            modContainer.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
        }

        VDParticleTypes.PARTICLE_TYPES.register(modEventBus);
        VDItems.ITEMS.register(modEventBus);
        VDDataComponents.DATA_COMPONENTS.register(modEventBus);
        VDPotions.POTIONS.register(modEventBus);
        VDOils.OILS.register(modEventBus);
        VDBlocks.BLOCKS.register(modEventBus);
        VDCreativeTabs.CREATIVE_TABS.register(modEventBus);
        VDEffects.EFFECTS.register(modEventBus);
        VDSounds.SOUNDS.register(modEventBus);
        VDEntityTypes.ENTITIES.register(modEventBus);
        VDFeatures.FEATURES.register(modEventBus);
        VDBlockEntityTypes.BLOCK_ENTITIES.register(modEventBus);
        VDLootFunctions.LOOT_FUNCTIONS.register(modEventBus);
        VDLootModifiers.LOOT_MODIFIERS.register(modEventBus);
        VDAdvancementTriggers.TRIGGERS.register(modEventBus);
        VDStats.CUSTOM_STATS.register(modEventBus);
    }
}
