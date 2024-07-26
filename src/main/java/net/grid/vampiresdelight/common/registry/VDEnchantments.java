package net.grid.vampiresdelight.common.registry;

import de.teamlapen.vampirism.items.VampirismVampireSwordItem;
import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.enchantment.VampireBiteEnchantment;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class VDEnchantments {
    public static final EnchantmentCategory VAMPIRE_SWORD = EnchantmentCategory.create("vampire_sword", item -> item instanceof VampirismVampireSwordItem);

    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(Registries.ENCHANTMENT, VampiresDelight.MODID);

    public static final Supplier<Enchantment> VAMPIRE_BITE = ENCHANTMENTS.register("vampire_bite",
            () -> new VampireBiteEnchantment(Enchantment.Rarity.VERY_RARE, VAMPIRE_SWORD));
}
