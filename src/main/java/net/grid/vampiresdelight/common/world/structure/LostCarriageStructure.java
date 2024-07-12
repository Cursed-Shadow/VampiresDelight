package net.grid.vampiresdelight.common.world.structure;

import com.mojang.serialization.Codec;
import de.teamlapen.vampirism.world.gen.structure.StructureEx;
import net.grid.vampiresdelight.common.registry.VDStructures;
import net.minecraft.world.level.levelgen.structure.*;

import java.util.Optional;

public class LostCarriageStructure extends StructureEx {

    public static final Codec<LostCarriageStructure> CODEC = simpleCodec(LostCarriageStructure::new);

    public LostCarriageStructure(StructureSettings pSettings) {
        super(pSettings);
    }

    @Override
    protected Optional<GenerationStub> findGenerationPoint(GenerationContext pContext) {
        return onSurface(pContext, (builder, pos) ->
                LostCarriagePieces.addPieces(pContext.structureTemplateManager(), builder, pContext.random(), pos)
        );
    }

    /*
    @Override
    public void afterPlace(WorldGenLevel level, StructureManager structureManager, ChunkGenerator chunkGenerator, RandomSource random, BoundingBox boundingBox, ChunkPos chunkPos, PiecesContainer piecesContainer) {
        super.afterPlace(level, structureManager, chunkGenerator, random, boundingBox, chunkPos, piecesContainer);

        placeFeaturesOnStructure(level, boundingBox);
        //placeFeature(level, boundingBox.getCenter());
    }

    private void placeFeaturesOnStructure(WorldGenLevel level, BoundingBox boundingBox) {
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();

        for (int x = boundingBox.minX(); x <= boundingBox.maxX(); x++) {
            for (int z = boundingBox.minZ(); z <= boundingBox.maxZ(); z++) {
                for (int y = boundingBox.minY(); y <= boundingBox.maxY(); y++) {
                    mutablePos.set(x, y, z);
                    placeFeature(level, mutablePos.above());
                }
            }
        }
    }

    private void placeFeature(WorldGenLevel level, BlockPos pos) {
        ResourceLocation resourceLocation = new ResourceLocation(VampiresDelight.MODID, "patch_wild_garlic");
        if (ForgeRegistries.FEATURES.getHolder(resourceLocation).isPresent()) {
            ForgeRegistries.FEATURES.getHolder(resourceLocation).get().get().place(new FeaturePlaceContext(Optional.empty(), level, level.getLevel().getChunkSource().getGenerator(), level.getRandom(), pos, FeatureConfiguration.NONE));
            //ForgeRegistries.FEATURES.getHolder(resourceLocation).get().get().place(FeatureConfiguration.NONE, level, level.getLevel().getChunkSource().getGenerator(), level.getRandom(), pos);
        }
    }
     */

    @Override
    public StructureType<?> type() {
        return VDStructures.LOST_CARRIAGE_TYPE.get();
    }
}
