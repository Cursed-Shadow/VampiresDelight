package net.grid.vampiresdelight.common.world.structure;

import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.registry.VDLootTables;
import net.grid.vampiresdelight.common.registry.VDStructures;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

public class LostCarriagePieces {
    private static final ResourceLocation LOCATION_1 = new ResourceLocation(VampiresDelight.MODID, "lost_carriage_1");
    private static final ResourceLocation LOCATION_2 = new ResourceLocation(VampiresDelight.MODID, "lost_carriage_2");
    private static final ResourceLocation LOCATION_3 = new ResourceLocation(VampiresDelight.MODID, "lost_carriage_3");
    private static final ResourceLocation LOCATION_4 = new ResourceLocation(VampiresDelight.MODID, "lost_carriage_4");

    public static void addPieces(StructureTemplateManager structureTemplateManager, StructurePieceAccessor pieceAccessor, RandomSource random, BlockPos pos)  {
        ResourceLocation location;
        location = switch (random.nextInt(4)) {
            case 0 -> LOCATION_1;
            case 1 -> LOCATION_2;
            case 2 -> LOCATION_3;
            default -> LOCATION_4;
        };
        pieceAccessor.addPiece(new LostCarriagePiece(structureTemplateManager, location, pos));
    }

    public static class LostCarriagePiece extends TemplateStructurePiece {

        public LostCarriagePiece(StructureTemplateManager pStructureTemplateManager, ResourceLocation pLocation, BlockPos pPos) {
            super(VDStructures.LOST_CARRIAGE_PIECE.get(), 0, pStructureTemplateManager, pLocation, pLocation.toString(), makeSettings(), pPos);
        }

        public LostCarriagePiece(StructureTemplateManager pStructureTemplateManager, CompoundTag pTag) {
            super(VDStructures.LOST_CARRIAGE_PIECE.get(), pTag, pStructureTemplateManager, (id) -> makeSettings());
        }

        @Override
        protected void handleDataMarker(String pName, BlockPos pPos, ServerLevelAccessor pLevel, RandomSource pRandom, BoundingBox pBox) {
            if (pName.equals("chest")) {
                pLevel.setBlock(pPos, Blocks.AIR.defaultBlockState(), 3);
                BlockEntity blockEntity = pLevel.getBlockEntity(pPos.below());
                if (blockEntity instanceof ChestBlockEntity chest) {
                    chest.setLootTable(VDLootTables.CHEST_LOST_CARRIAGE, pRandom.nextLong());
                }
            }
        }

        @Override
        public void postProcess(WorldGenLevel pLevel, StructureManager pStructureManager, ChunkGenerator pGenerator, RandomSource pRandom, BoundingBox pBox, ChunkPos pChunkPos, BlockPos pPos) {
            /*
            BoundingBox boundingBox = this.template.getBoundingBox(this.placeSettings, this.templatePosition);
            this.placeBlock(pLevel, Blocks.GOLD_BLOCK.defaultBlockState(), 0, 0, 0, boundingBox);
            this.placeBlock(pLevel, Blocks.EMERALD_BLOCK.defaultBlockState(), 0, 5, 0, boundingBox);
             */

            /*
            BlockPos centerPos = this.boundingBox.getCenter();
            int centerX = centerPos.getX();
            int centerZ = centerPos.getZ();
            int topY = this.boundingBox.maxY();

            for (int x = centerX - 5; x <= centerX + 5; x++)
                for (int z = centerZ - 5; z <= centerZ + 5; z++) {
                    BlockPos placePos = getGroundPosition(new BlockPos(centerX, topY, centerZ), pLevel);
                    if (placePos != null)
                        setBlockWithChance(Blocks.COARSE_DIRT, placePos, 100, pRandom, pLevel);
                }
             */

            super.postProcess(pLevel, pStructureManager, pGenerator, pRandom, pBox, pChunkPos, pPos);
        }

        private static void setBlockWithChance(Block pBlock, BlockPos pPos, int chance, RandomSource pRandom, WorldGenLevel pLevel) {
            if (pRandom.nextInt(100) <= chance)
                pLevel.setBlock(pPos, pBlock.defaultBlockState(), 3);
        }

        private static BlockPos getGroundPosition(BlockPos pPos, WorldGenLevel pLevel) {
            BlockPos topPos = pPos;
            for (int i = 0; i < 10; i++) {
                if (pLevel.getBlockState(topPos).isAir())
                    topPos = topPos.below();
                else
                    return topPos;
            }
            return null;
        }

        /*
        private void placeCoarseDirt(RandomSource pRandom, LevelAccessor pLevel) {
            BoundingBox pBoundingBox = this.boundingBox;
            int centerX = pBoundingBox.getCenter().getX();
            int centerZ = pBoundingBox.getCenter().getZ();

            for (int x = centerZ - 5; x < centerZ + 5; ++x)
                for (int z = centerZ - 5; z < centerZ + 5; ++z) {
                    int deltaXdistToDot = x - centerX;
                    int deltaZdistToDot = z - centerZ;
                    double distToDot = Math.sqrt(deltaXdistToDot * deltaXdistToDot + deltaZdistToDot * deltaZdistToDot);

                    //if (distToDot < 6) {
                        //BlockPos pPos = new BlockPos(x, pBoundingBox.minY() + 3, z);
                        for (int i = 0; i < 6; i++) {
                                pLevel.setBlock(new BlockPos(x, pBoundingBox.minY() + 3, z), Blocks.COARSE_DIRT.defaultBlockState(), 3);
                            //pPos = pPos.below();
                        }
                    //}
                }
        }
         */

        private static StructurePlaceSettings makeSettings() {
            return (new StructurePlaceSettings()).setRotation(Rotation.NONE).setMirror(Mirror.NONE).addProcessor(BlockIgnoreProcessor.STRUCTURE_AND_AIR);
        }
    }
}
