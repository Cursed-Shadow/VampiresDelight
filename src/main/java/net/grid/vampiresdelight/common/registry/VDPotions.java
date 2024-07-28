package net.grid.vampiresdelight.common.registry;

import de.teamlapen.vampirism.api.VampirismAPI;
import de.teamlapen.vampirism.api.items.ExtendedPotionMix;
import de.teamlapen.vampirism.core.ModBlocks;
import de.teamlapen.vampirism.core.ModTags;
import de.teamlapen.vampirism.effects.VampirismPotion.HunterPotion;
import net.grid.vampiresdelight.VampiresDelight;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class VDPotions {
    public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(Registries.POTION, VampiresDelight.MODID);

    //Hunter
    public static final DeferredHolder<Potion, HunterPotion> BLESSING = POTIONS.register("blessing",
            () -> new HunterPotion(null, new MobEffectInstance(VDEffects.BLESSING, 7200)));
    public static final DeferredHolder<Potion, HunterPotion> LONG_BLESSING = POTIONS.register("long_blessing",
            () -> new HunterPotion("blessing", new MobEffectInstance(VDEffects.BLESSING, 19200)));
    public static final DeferredHolder<Potion, HunterPotion> STRONG_BLESSING = POTIONS.register("strong_blessing",
            () -> new HunterPotion("blessing", new MobEffectInstance(VDEffects.BLESSING, 1600, 1)));
    public static final DeferredHolder<Potion, HunterPotion> VERY_LONG_BLESSING = POTIONS.register("very_long_blessing",
            () -> new HunterPotion("blessing", new MobEffectInstance(VDEffects.BLESSING, 192000)));
    public static final DeferredHolder<Potion, HunterPotion> LONG_STRONG_BLESSING = POTIONS.register("long_strong_blessing",
            () -> new HunterPotion("blessing", new MobEffectInstance(VDEffects.BLESSING, 4800, 1)));

    public static final DeferredHolder<Potion, Potion> CLOTHES_DISSOLVING = POTIONS.register("clothes_dissolving",
            () -> new Potion(new MobEffectInstance(VDEffects.CLOTHES_DISSOLVING, 600)));

    public static final DeferredHolder<Potion, HunterPotion> FOG_VISION = POTIONS.register("fog_vision",
            () -> new HunterPotion(null, new MobEffectInstance(VDEffects.FOG_VISION, 3600)));
    public static final DeferredHolder<Potion, HunterPotion> LONG_FOG_VISION = POTIONS.register("long_fog_vision",
            () -> new HunterPotion("fog_vision", new MobEffectInstance(VDEffects.FOG_VISION, 9600)));
    public static final DeferredHolder<Potion, HunterPotion> STRONG_FOG_VISION = POTIONS.register("strong_fog_vision",
            () -> new HunterPotion("fog_vision", new MobEffectInstance(VDEffects.FOG_VISION, 800, 1)));
    public static final DeferredHolder<Potion, HunterPotion> VERY_LONG_FOG_VISION = POTIONS.register("very_long_fog_vision",
            () -> new HunterPotion("fog_vision", new MobEffectInstance(VDEffects.FOG_VISION, 96000)));
    public static final DeferredHolder<Potion, HunterPotion> LONG_STRONG_FOG_VISION = POTIONS.register("long_strong_fog_vision",
            () -> new HunterPotion("fog_vision", new MobEffectInstance(VDEffects.FOG_VISION, 2400, 1)));

    public static void registerPotionMixes() {
        master(BLESSING, () -> Ingredient.of(ModTags.Items.HOLY_WATER), 8, 4);
        durable(BLESSING, LONG_BLESSING);
        strong(BLESSING, STRONG_BLESSING);
        veryDurable(LONG_BLESSING, VERY_LONG_BLESSING);
        veryStrong(VERY_LONG_BLESSING, LONG_STRONG_BLESSING);

        master(FOG_VISION, () -> Ingredient.of(ModBlocks.VAMPIRE_ORCHID), 16, 8);
        durable(FOG_VISION, LONG_FOG_VISION);
        strong(FOG_VISION, STRONG_FOG_VISION);
        veryDurable(LONG_FOG_VISION, VERY_LONG_FOG_VISION);
        veryStrong(VERY_LONG_FOG_VISION, LONG_STRONG_FOG_VISION);
    }

    private static void durable(Holder<Potion> in, Holder<Potion> out) {
        VampirismAPI.extendedBrewingRecipeRegistry().addMix(new ExtendedPotionMix.Builder(in, out).ingredient(() -> Ingredient.of(Items.REDSTONE), 1).blood().build());
    }

    private static void strong(Holder<Potion> in, Holder<Potion> out) {
        VampirismAPI.extendedBrewingRecipeRegistry().addMix(new ExtendedPotionMix.Builder(in, out).ingredient(() -> Ingredient.of(Items.GLOWSTONE_DUST), 1).blood().build());
    }

    private static void veryDurable(Holder<Potion> in, Holder<Potion> out) {
        VampirismAPI.extendedBrewingRecipeRegistry().addMix(new ExtendedPotionMix.Builder(in, out).ingredient(() -> Ingredient.of(Items.REDSTONE_BLOCK), 32, 16).blood().durable().build());
    }

    private static void veryStrong(Holder<Potion> in, Holder<Potion> out) {
        VampirismAPI.extendedBrewingRecipeRegistry().addMix(new ExtendedPotionMix.Builder(in, out).ingredient(() -> Ingredient.of(Items.GLOWSTONE), 64, 32).blood().concentrated().build());
    }

    private static void master(Holder<Potion> out, Supplier<Ingredient> in, int count, int countReduced) {
        VampirismAPI.extendedBrewingRecipeRegistry().addMix(new ExtendedPotionMix.Builder(Potions.AWKWARD, out).master().ingredient(in, count, countReduced).blood().build());
    }
}
