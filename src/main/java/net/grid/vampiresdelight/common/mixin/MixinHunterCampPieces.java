package net.grid.vampiresdelight.common.mixin;

import de.teamlapen.vampirism.world.gen.structure.huntercamp.HunterCampPieces;
import net.grid.vampiresdelight.common.VDConfiguration;
import net.grid.vampiresdelight.common.mixin.accessor.StructurePieceAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vectorwing.farmersdelight.common.block.CookingPotBlock;
import vectorwing.farmersdelight.common.block.state.CookingPotSupport;
import vectorwing.farmersdelight.common.registry.ModBlocks;

@Mixin(HunterCampPieces.SpecialBlock.class)
public class MixinHunterCampPieces {
    @Final
    @Shadow
    private boolean advanced;

    @Inject(at = @At("TAIL"), method = "postProcess(Lnet/minecraft/world/level/WorldGenLevel;Lnet/minecraft/world/level/StructureManager;Lnet/minecraft/world/level/chunk/ChunkGenerator;Lnet/minecraft/util/RandomSource;Lnet/minecraft/world/level/levelgen/structure/BoundingBox;Lnet/minecraft/world/level/ChunkPos;Lnet/minecraft/core/BlockPos;)V", remap = false)
    public void placeCookingPot(WorldGenLevel worldIn, StructureManager structureManager, ChunkGenerator chunkGenerator, RandomSource random, BoundingBox structureBoundingBoxIn, ChunkPos chunkPos, BlockPos blockPos, CallbackInfo ci) {
        if (random.nextInt(100) <= VDConfiguration.COOKING_POT_IN_HUNTER_CAMP_CHANCE.get() && VDConfiguration.GENERATE_COOKING_POT_IN_HUNTER_CAMP.get()) {
            ((StructurePieceAccessor) this).vampiresdelight$placeBlock(worldIn, Blocks.CAMPFIRE.defaultBlockState(), advanced ? 1 : 3, 0, advanced ? 3 : 1, structureBoundingBoxIn);
            ((StructurePieceAccessor) this).vampiresdelight$placeBlock(worldIn, ModBlocks.COOKING_POT.get().defaultBlockState().setValue(CookingPotBlock.SUPPORT, CookingPotSupport.TRAY), advanced ? 1 : 3, 1, advanced ? 3 : 1, structureBoundingBoxIn);
        }
    }
}
