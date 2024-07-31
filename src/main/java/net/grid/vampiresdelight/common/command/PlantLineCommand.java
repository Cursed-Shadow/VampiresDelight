package net.grid.vampiresdelight.common.command;

import com.mojang.brigadier.builder.ArgumentBuilder;
import de.teamlapen.lib.lib.util.BasicCommand;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.blocks.BlockInput;
import net.minecraft.commands.arguments.blocks.BlockStateArgument;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluids;

import java.util.List;
import java.util.stream.Stream;

public class PlantLineCommand extends BasicCommand {
    /*
    public static ArgumentBuilder<CommandSourceStack, ?> register(CommandBuildContext buildContext) {
        return Commands.literal("spawnPlantLine")
                .requires(context -> context.hasPermission(PERMISSION_LEVEL_CHEAT))
                        .then(Commands.argument("pos", BlockPosArgument.blockPos())
                                .then(Commands.argument("soil", BlockStateArgument.block(buildContext))
                                        .executes(context -> createPlantLine(context.getSource(), BlockPosArgument.getLoadedBlockPos(context, "pos"), BlockStateArgument.getBlock(context, "soil")))));
    }

    private static int createPlantLine(CommandSourceStack source, BlockPos pos, BlockInput state) {
        ServerLevel level = source.getLevel();
        BlockState soilBlock = state.getState();

        Stream<Block> plantBlocks = BuiltInRegistries.BLOCK.stream();
        // All plants that can grow on soil block
        List<BlockState> plants = plantBlocks.map(Block::defaultBlockState).filter(blockState -> blockState.getBlock() instanceof IPlantable iPlantable && soilBlock.canSustainPlant(level, pos, Direction.NORTH, iPlantable)).toList();

        int j = 0;
        for (int i = 0; i < plants.size(); i++) {
            BlockState currentPlant = plants.get(i);
            level.setBlock(new BlockPos(pos.getX() + i + j + 1, pos.getY(), pos.getZ()), soilBlock, 2);
            level.setBlock(new BlockPos(pos.getX() + i + j + 1, pos.getY() + 1, pos.getZ()), currentPlant, 2);
            if (currentPlant.hasProperty(BlockStateProperties.WATERLOGGED) && currentPlant.getValue(BlockStateProperties.WATERLOGGED) || currentPlant.getFluidState().is(Fluids.WATER)) {
                level.setBlock(new BlockPos(pos.getX() + i + j + 1, pos.getY() + 1, pos.getZ() - 1), Blocks.GLASS.defaultBlockState(), 2);
                level.setBlock(new BlockPos(pos.getX() + i + j + 1, pos.getY() + 1, pos.getZ() + 1), Blocks.GLASS.defaultBlockState(), 2);

                level.setBlock(new BlockPos(pos.getX() + i + j, pos.getY() + 1, pos.getZ()), Blocks.GLASS.defaultBlockState(), 2);
                level.setBlock(new BlockPos(pos.getX() + i + j + 2, pos.getY() + 1, pos.getZ()), Blocks.GLASS.defaultBlockState(), 2);
            }
            j++;
        }

        return 0;
    }
     */
}
