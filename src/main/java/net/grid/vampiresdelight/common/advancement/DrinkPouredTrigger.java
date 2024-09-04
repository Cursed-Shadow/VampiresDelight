package net.grid.vampiresdelight.common.advancement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.grid.vampiresdelight.common.registry.VDAdvancementTriggers;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.*;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

import java.util.Optional;

public class DrinkPouredTrigger extends SimpleCriterionTrigger<DrinkPouredTrigger.TriggerInstance> {
    public DrinkPouredTrigger() {
    }

    @Override
    public Codec<TriggerInstance> codec() {
        return TriggerInstance.CODEC;
    }

    public void trigger(ServerPlayer player, ItemStack itemStack) {
        this.trigger(player, (instance) -> instance.matches(itemStack));
    }

    public record TriggerInstance(Optional<ContextAwarePredicate> player, Optional<ItemPredicate> item) implements SimpleCriterionTrigger.SimpleInstance {
        public static final Codec<TriggerInstance> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(TriggerInstance::player),
                ItemPredicate.CODEC.optionalFieldOf("item").forGetter(TriggerInstance::item)
        ).apply(instance, TriggerInstance::new));

        public static Criterion<TriggerInstance> pouredDrinkBottle(ItemLike item) {
            return pouredDrinkBottle(ItemPredicate.Builder.item().of(item.asItem()));
        }

        public static Criterion<TriggerInstance> pouredDrinkBottle(ItemPredicate.Builder item) {
            return VDAdvancementTriggers.DRINK_POURED.get().createCriterion(new TriggerInstance(Optional.empty(), Optional.of(item.build())));
        }

        public boolean matches(ItemStack item) {
            return this.item.isEmpty() || this.item.get().test(item);
        }

        public Optional<ContextAwarePredicate> player() {
            return this.player;
        }

        public Optional<ItemPredicate> item() {
            return this.item;
        }
    }
}
