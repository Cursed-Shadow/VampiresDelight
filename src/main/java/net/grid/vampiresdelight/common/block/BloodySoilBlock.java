package net.grid.vampiresdelight.common.block;

import de.teamlapen.vampirism.api.VReference;
import de.teamlapen.vampirism.blocks.VampirismFlowerBlock;
import net.grid.vampiresdelight.common.VDConfiguration;
import net.grid.vampiresdelight.common.utility.VDHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.TallFlowerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;
import vectorwing.farmersdelight.common.tag.ModTags;
import vectorwing.farmersdelight.common.utility.MathUtils;

public class BloodySoilBlock extends Block {
    public BloodySoilBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if (!pLevel.isClientSide) {
            BlockPos abovePos = pPos.above();
            BlockState aboveState = pLevel.getBlockState(abovePos);
            Block aboveBlock = aboveState.getBlock();

            // Do nothing if the plant is unaffected by bloody soil
            if (aboveState.is(ModTags.UNAFFECTED_BY_RICH_SOIL) || aboveBlock instanceof TallFlowerBlock) {
                return;
            }

            // Do nothing if it's not vampire plant
            if (!isCursedPlant(pLevel, abovePos) || isCrop(pLevel, abovePos)) {
                return;
            }

            // TODO: Add black mushroom transformation

            double boostChance = VDConfiguration.BLOODY_SOIL_BOOST_CHANCE.get();

            if (boostChance == 0.0) {
                return;
            }

            // If all else fails, and it's a plant, give it a growth boost now and then!
            if (aboveBlock instanceof BonemealableBlock growable && MathUtils.RAND.nextFloat() <= boostChance) {
                if (growable.isValidBonemealTarget(pLevel, abovePos, aboveState, false) && ForgeHooks.onCropsGrowPre(pLevel, abovePos, aboveState, true)) {
                    growable.performBonemeal(pLevel, pLevel.random, abovePos, aboveState);
                    pLevel.levelEvent(2005, abovePos, 0);
                    ForgeHooks.onCropsGrowPost(pLevel, abovePos, aboveState);
                }
            }
        }
    }

    public boolean isCursedPlant(BlockGetter world, BlockPos plantPos) {
        BlockState plantBlock = world.getBlockState(plantPos);
        if (plantBlock.getBlock() instanceof IPlantable iPlantable) {
            PlantType plantType = iPlantable.getPlantType(world, plantPos);

            return (iPlantable instanceof BushBlock && plantType != PlantType.CROP) || plantType == VReference.VAMPIRE_PLANT_TYPE || plantType == VDHelper.CURSED_PLANT_TYPE || plantBlock.getBlock() instanceof VampirismFlowerBlock;
        }
        return false;
    }

    public boolean isCrop(BlockGetter world, BlockPos plantPos) {
        BlockState plantBlock = world.getBlockState(plantPos);
        if (plantBlock.getBlock() instanceof IPlantable iPlantable) {
            PlantType plantType = iPlantable.getPlantType(world, plantPos);
            return plantType == PlantType.CROP;
        }
        return false;
    }

    @Override
    public boolean canSustainPlant(BlockState state, BlockGetter world, BlockPos pos, Direction facing, IPlantable iPlantable) {
        BlockPos plantPos = pos.relative(facing);
        PlantType plantType = iPlantable.getPlantType(world, plantPos);
        return plantType != PlantType.CROP && plantType != PlantType.NETHER && plantType != PlantType.WATER && isCursedPlant(world, plantPos);
    }
}
