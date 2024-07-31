package net.grid.vampiresdelight;

import net.grid.vampiresdelight.client.event.ClientSetupEvents;
import net.grid.vampiresdelight.common.CommonSetup;
import net.grid.vampiresdelight.common.VDConfiguration;
import net.grid.vampiresdelight.common.registry.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.loading.FMLEnvironment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(VampiresDelight.MODID)
public class VampiresDelight {
    public static final String MODID = "vampiresdelight";
    public static final Logger LOGGER = LogManager.getLogger();

    public VampiresDelight(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(CommonSetup::init);
        if (FMLEnvironment.dist.isClient()) {
            modEventBus.addListener(ClientSetupEvents::init);
        }

        modContainer.registerConfig(ModConfig.Type.COMMON, VDConfiguration.COMMON_CONFIG);
        modContainer.registerConfig(ModConfig.Type.CLIENT, VDConfiguration.CLIENT_CONFIG);

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
        VDBlockEntityTypes.TILES.register(modEventBus);
        VDLootModifiers.LOOT_MODIFIERS.register(modEventBus);
        VDAdvancementTriggers.TRIGGERS.register(modEventBus);
    }
}
