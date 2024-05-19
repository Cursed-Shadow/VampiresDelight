package net.grid.vampiresdelight.common.block;

import de.teamlapen.vampirism.api.entity.vampire.IVampire;
import de.teamlapen.vampirism.entity.player.vampire.VampirePlayer;
import de.teamlapen.vampirism.entity.vampire.DrinkBloodContext;
import de.teamlapen.vampirism.util.Helper;
import net.grid.vampiresdelight.common.VDFoodValues;
import net.grid.vampiresdelight.common.item.VampireConsumableItem;
import net.grid.vampiresdelight.common.utility.VDEntityUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import vectorwing.farmersdelight.common.block.PieBlock;

import java.util.function.Supplier;

public class VampirePieBlock extends PieBlock {

    public VampirePieBlock(Properties properties, Supplier<Item> pieSlice) {
        super(properties, pieSlice);
    }

    @Override
    protected InteractionResult consumeBite(Level level, BlockPos pos, BlockState state, Player consumer) {
        if (!consumer.canEat(false)) {
            return InteractionResult.PASS;
        } else {
            ItemStack stack = this.getPieSliceItem();
            FoodProperties humanFood = stack.getItem().getFoodProperties();
            FoodProperties vampireFood = (stack.getItem() instanceof VampireConsumableItem vampireConsumableItem) ? vampireConsumableItem.getVampireFood() : VDFoodValues.BLOOD_PIE_SLICE;

            VampirePlayer.getOpt(consumer).ifPresent(v -> v.drinkBlood(vampireFood.getNutrition(), vampireFood.getSaturationModifier(), new DrinkBloodContext(stack)));

            if (consumer instanceof IVampire) {
                ((IVampire) consumer).drinkBlood(vampireFood.getNutrition(), vampireFood.getSaturationModifier(), new DrinkBloodContext(stack));
            } else if (!Helper.isVampire(consumer))
                VDEntityUtils.eatFood(level, consumer, stack, humanFood);

            if (Helper.isVampire(consumer)) {
                VDEntityUtils.addFoodEffects(vampireFood, level, consumer);
            }

            int bites = state.getValue(BITES);
            if (bites < getMaxBites() - 1) {
                level.setBlock(pos, state.setValue(BITES, bites + 1), 3);
            } else {
                level.removeBlock(pos, false);
            }
            level.playSound(null, pos, SoundEvents.GENERIC_EAT, SoundSource.PLAYERS, 0.8F, 0.8F);
            return InteractionResult.SUCCESS;
        }
    }
}
