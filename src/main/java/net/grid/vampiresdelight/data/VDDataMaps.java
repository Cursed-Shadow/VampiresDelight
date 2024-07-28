package net.grid.vampiresdelight.data;

import net.grid.vampiresdelight.common.registry.VDItems;
import net.grid.vampiresdelight.common.utility.VDNameUtils;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.Compostable;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;

import java.util.concurrent.CompletableFuture;

public class VDDataMaps extends DataMapProvider {
    protected VDDataMaps(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void gather() {
        gatherCompostables();
    }

    protected void gatherCompostables() {
        var compostables = builder(NeoForgeDataMaps.COMPOSTABLES);

        // 30% chance
        compostables.add(item(VDItems.ORCHID_SEEDS.get()), chance(0.3F), false);

        // 50% chance
        compostables.add(item(VDItems.ORCHID_PETALS.get()), chance(0.5F), false);

        // 65% chance
        compostables.add(item(VDItems.WILD_GARLIC.get()), chance(0.65F), false);

        // 85% chance
        compostables.add(item(VDItems.BLOOD_PIE_SLICE.get()), chance(0.85F), false);
        compostables.add(item(VDItems.ORCHID_COOKIE.get()), chance(0.85F), false);

        // 100% chance
        compostables.add(item(VDItems.BLOOD_PIE.get()), chance(1.0F), false);
        compostables.add(item(VDItems.WEIRD_JELLY_BLOCK.get()), chance(1.0F), false);
    }
    
    protected ResourceLocation item(Item item) {
        return VDNameUtils.itemLocation(item);
    }

    protected Compostable chance(float chance) {
        return new Compostable(chance);
    }
}
