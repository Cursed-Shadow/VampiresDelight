package net.grid.vampiresdelight.common.advancement;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonObject;
import net.grid.vampiresdelight.VampiresDelight;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public class DrinkPouredTrigger extends SimpleCriterionTrigger<DrinkPouredTrigger.TriggerInstance> {
    static final ResourceLocation ID = new ResourceLocation(VampiresDelight.MODID, "drink_poured");

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    public TriggerInstance createInstance(JsonObject json, ContextAwarePredicate predicate, DeserializationContext deserializationContext) {
        return new TriggerInstance(predicate, ItemPredicate.fromJson(json.get("item")));
    }

    public void trigger(ServerPlayer player, ItemStack itemStack) {
        this.trigger(player, (instance) -> instance.matches(itemStack));
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {
        private final ItemPredicate item;

        public TriggerInstance(ContextAwarePredicate player, ItemPredicate item) {
            super(ID, player);
            this.item = item;
        }

        public static TriggerInstance pouredDrinkBottle(ItemLike pItem) {
            return new TriggerInstance(ContextAwarePredicate.ANY, new ItemPredicate(null, ImmutableSet.of(pItem.asItem()), MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, EnchantmentPredicate.NONE, EnchantmentPredicate.NONE, null, NbtPredicate.ANY));
        }

        public boolean matches(ItemStack pItem) {
            return this.item.matches(pItem);
        }

        public JsonObject serializeToJson(SerializationContext context) {
            JsonObject jsonobject = super.serializeToJson(context);
            jsonobject.add("item", item.serializeToJson());
            return jsonobject;
        }
    }
}
