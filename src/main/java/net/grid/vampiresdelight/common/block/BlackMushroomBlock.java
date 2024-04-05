package net.grid.vampiresdelight.common.block;

import de.teamlapen.vampirism.api.VReference;
import de.teamlapen.vampirism.core.ModTags;
import de.teamlapen.vampirism.util.Helper;
import net.grid.vampiresdelight.common.utility.VDHelper;
import net.grid.vampiresdelight.common.world.VDConfiguredFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.MushroomBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.PlantType;

public class BlackMushroomBlock extends MushroomBlock {
    public BlackMushroomBlock(Properties properties) {
        super(properties, VDConfiguredFeatures.HUGE_BLACK_MUSHROOM_KEY);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader levelReader, BlockPos pos) {
        BlockPos blockPos = pos.below();
        BlockState blockState = levelReader.getBlockState(blockPos);

        if (blockState.is(ModTags.Blocks.CURSED_EARTH)) {
            return true;
        } else {
            // Black Mushrooms either require the same conditions as vanilla mushrooms or vampire fog to grow.
            // Hope such type of cast LevelReader to Level won't break anything.
            return (levelReader.getRawBrightness(pos, 0) < 13
                    || Helper.isPosInVampireBiome(pos, (LevelAccessor) levelReader)
                    || VDHelper.isBlockInVampireFogArea(pos, (Level) levelReader))
                    && blockState.canSustainPlant(levelReader, blockPos, net.minecraft.core.Direction.UP, this);
        }
    }

    @Override
    public PlantType getPlantType(BlockGetter level, BlockPos pos) {
        return VReference.VAMPIRE_PLANT_TYPE;
    }
}
