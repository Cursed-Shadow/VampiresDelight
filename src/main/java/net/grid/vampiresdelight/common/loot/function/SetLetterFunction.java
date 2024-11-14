package net.grid.vampiresdelight.common.loot.function;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.grid.vampiresdelight.common.item.component.WeatheredLetter;
import net.grid.vampiresdelight.common.registry.VDLootFunctions;
import net.grid.vampiresdelight.common.registry.VDRegistries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

import javax.annotation.Nullable;
import java.util.List;

@SuppressWarnings("unused")
public class SetLetterFunction extends LootItemConditionalFunction {
    public static final MapCodec<SetLetterFunction> CODEC = RecordCodecBuilder.mapCodec(instance ->
            commonFields(instance)
                    .and(TagKey.codec(VDRegistries.WEATHERED_LETTER).fieldOf("tag").forGetter(setLetterFunction -> setLetterFunction.tag))
                    .apply(instance, SetLetterFunction::new)
    );
    private final @Nullable TagKey<WeatheredLetter> tag;

    public SetLetterFunction(List<LootItemCondition> predicates, @Nullable TagKey<WeatheredLetter> tag) {
        super(predicates);
        this.tag = tag;
    }

    @Override
    public LootItemFunctionType<? extends LootItemConditionalFunction> getType() {
        return VDLootFunctions.SET_LETTER.get();
    }

    @Override
    protected ItemStack run(ItemStack itemStack, LootContext lootContext) {
        WeatheredLetter letter = WeatheredLetter.getRandomLetter(lootContext.getLevel().registryAccess(), tag);
        WeatheredLetter.addToStack(itemStack, letter);
        return itemStack;
    }

    public static LootItemConditionalFunction.Builder<?> randomAny() {
        return simpleBuilder(conditions -> new SetLetterFunction(conditions, null));
    }

    public static LootItemConditionalFunction.Builder<?> randomTagged(TagKey<WeatheredLetter> tag) {
        return simpleBuilder(conditions -> new SetLetterFunction(conditions, tag));
    }
}
