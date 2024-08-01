package net.grid.vampiresdelight.data;

import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.tag.VDTags;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;

public class VDEnchantments {
    public static final ResourceKey<Enchantment> VAMPIRE_BITE = ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(VampiresDelight.MODID, "vampire_bite"));

    public static void createEnchantments(BootstrapContext<Enchantment> context) {
        HolderGetter<Item> items = context.lookup(Registries.ITEM);

        context.register(VAMPIRE_BITE, new Enchantment.Builder(Enchantment.definition(
                items.getOrThrow(VDTags.VAMPIRE_BITE_ENCHANTABLE),
                2,
                3,
                Enchantment.dynamicCost(25, 10),
                Enchantment.dynamicCost(50, 9),
                2, EquipmentSlotGroup.MAINHAND))
                .build(VAMPIRE_BITE.location()));
    }
}
