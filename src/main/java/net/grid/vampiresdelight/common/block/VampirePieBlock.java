package net.grid.vampiresdelight.common.block;

import de.teamlapen.vampirism.api.entity.vampire.IVampire;
import de.teamlapen.vampirism.entity.player.vampire.VampirePlayer;
import de.teamlapen.vampirism.entity.vampire.DrinkBloodContext;
import net.grid.vampiresdelight.common.utility.VDEntityUtils;
import net.grid.vampiresdelight.common.utility.VDHelper;
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
            FoodProperties foodProperties = stack.getItem().getFoodProperties(stack, consumer);

            if (foodProperties != null) {
                VampirePlayer.getOpt(consumer).ifPresent(v -> v.drinkBlood(foodProperties.getNutrition(), foodProperties.getSaturationModifier(), new DrinkBloodContext(stack)));

                if (consumer instanceof IVampire) {
                    ((IVampire) consumer).drinkBlood(foodProperties.getNutrition(), foodProperties.getSaturationModifier(), new DrinkBloodContext(stack));
                } else if (!VDHelper.isVampire(consumer))
                    consumer.eat(level, stack);

                if (VDHelper.isVampire(consumer)) {
                    VDEntityUtils.addFoodEffects(foodProperties, level, consumer);
                }
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
