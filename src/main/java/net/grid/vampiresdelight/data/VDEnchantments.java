package net.grid.vampiresdelight.data;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.MultiplyValue;
import vectorwing.farmersdelight.FarmersDelight;
import vectorwing.farmersdelight.common.registry.ModDataComponents;
import vectorwing.farmersdelight.common.tag.ModTags;

public class VDEnchantments {
    /*
    public static final ResourceKey<Enchantment> VAMPIRE_BITE = key("vampire_bite");

    public static void bootstrap(BootstrapContext<Enchantment> context) {
        HolderGetter<Item> items = context.lookup(Registries.ITEM);

        // All of that is BACKSTABBING, needs remake
        register(context, VAMPIRE_BITE, Enchantment.enchantment(Enchantment.definition(
                items.getOrThrow(ModTags.KNIFE_ENCHANTABLE),
                        5, 3,
                        Enchantment.dynamicCost(15, 9),
                        Enchantment.dynamicCost(50, 8),
                        2, EquipmentSlotGroup.MAINHAND)
                ).withEffect(ModDataComponents.BACKSTABBING.get(), new MultiplyValue(LevelBasedValue.perLevel(1.4F, 0.2F))));
    }

    private static void register(BootstrapContext<Enchantment> context, ResourceKey<Enchantment> key, Enchantment.Builder builder) {
        context.register(key, builder.build(key.location()));
    }

    private static ResourceKey<Enchantment> key(String name) {
        return ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(FarmersDelight.MODID, name));
    }
     */
}
