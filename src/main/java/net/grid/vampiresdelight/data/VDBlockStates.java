package net.grid.vampiresdelight.data;

import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.block.*;
import net.grid.vampiresdelight.common.registry.VDBlocks;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraft.world.level.block.Block;
import vectorwing.farmersdelight.FarmersDelight;
import vectorwing.farmersdelight.common.block.*;

import java.util.Map;
import java.util.function.Function;

public class VDBlockStates extends BlockStateProvider {
    private static final int DEFAULT_ANGLE_OFFSET = 180;
    public VDBlockStates(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, VampiresDelight.MODID, existingFileHelper);
    }

    public String blockName(Block block) {
        return ForgeRegistries.BLOCKS.getKey(block).getPath();
    }

    public ResourceLocation resourceBlock(String path) {
        return new ResourceLocation(VampiresDelight.MODID, "block/" + path);
    }

    public ModelFile existingModel(Block block) {
        return new ModelFile.ExistingModelFile(resourceBlock(blockName(block)), models().existingFileHelper);
    }

    public ModelFile existingModel(String path) {
        return new ModelFile.ExistingModelFile(resourceBlock(path), models().existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        this.simpleBlock(VDBlocks.BLOODY_SOIL.get(), cubeRandomRotation(VDBlocks.BLOODY_SOIL.get(), ""));

        this.cabinetBlock(VDBlocks.DARK_SPRUCE_CABINET.get(), "dark_spruce");
        this.cabinetBlock(VDBlocks.CURSED_SPRUCE_CABINET.get(), "cursed_spruce");
        this.cabinetBlock(VDBlocks.JACARANDA_CABINET.get(), "jacaranda");
        this.cabinetBlock(VDBlocks.MAGIC_CABINET.get(), "magic");

        this.wildCropBlock(VDBlocks.WILD_GARLIC.get());

        this.horizontalBlock(VDBlocks.DARK_STONE_STOVE.get(), state -> {
            String name = blockName(VDBlocks.DARK_STONE_STOVE.get());
            String suffix = state.getValue(DarkStoneStoveBlock.LIT) ? "_on" : "";

            return models().orientableWithBottom(name + suffix,
                    resourceBlock(name + "_side"),
                    resourceBlock(name + "_front" + suffix),
                    resourceBlock(name + "_bottom"),
                    resourceBlock(name + "_top" + suffix));
        });

        getVariantBuilder(VDBlocks.VAMPIRE_ORCHID_CROP.get())
                .partialState().with(VampireOrchidCropBlock.AGE, 0).modelForState().modelFile(models().getExistingFile(modLoc("vampire_orchid_crop_stage0"))).addModel()
                .partialState().with(VampireOrchidCropBlock.AGE, 1).modelForState().modelFile(models().getExistingFile(modLoc("vampire_orchid_crop_stage1"))).addModel();

        this.feastBlock(VDBlocks.WEIRD_JELLY_BLOCK.get());

        this.simpleBlock(VDBlocks.BLOOD_WINE_BOTTLE_PLACED.get(), existingModel(VDBlocks.BLOOD_WINE_BOTTLE_PLACED.get()));

        this.crateBlock(VDBlocks.GARLIC_CRATE.get(), "garlic");
        this.bagBlock(VDBlocks.ORCHID_BAG.get());

        this.pieBlock(VDBlocks.BLOOD_PIE.get());

        this.hugeBlackMushroomBlock(VDBlocks.BLACK_MUSHROOM_BLOCK.get());
        this.hugeBlackMushroomBlock(VDBlocks.BLACK_MUSHROOM_STEM.get());
        this.plantBlock(VDBlocks.BLACK_MUSHROOM.get());
        this.pottedPlantBlock(VDBlocks.POTTED_BLACK_MUSHROOM.get(), VDBlocks.BLACK_MUSHROOM.get());

        this.farmlandBlock(VDBlocks.CURSED_FARMLAND.get());
        this.farmlandBlock(VDBlocks.BLOODY_SOIL_FARMLAND.get());

        WineShelfBlock.getAllShelveBlocks().forEach(this::wineShelfBlock);
        BarStoolBlock.getBarStoolBlocks().forEach(block -> this.simpleBlock(block, existingModel(block)));

        ConsumableCandleCakeBlock.getAllCandleCakes().forEach(block -> this.candleCakeBlock((ConsumableCandleCakeBlock) block));
        this.cakeBlock(VDBlocks.ORCHID_CAKE.get());
    }

    public ConfiguredModel[] cubeRandomRotation(Block block, String suffix) {
        String formattedName = blockName(block) + (suffix.isEmpty() ? "" : "_" + suffix);
        return ConfiguredModel.allYRotations(models().cubeAll(formattedName, resourceBlock(formattedName)), 0, false);
    }

    public void wildCropBlock(Block block) {
        this.wildCropBlock(block, false);
    }

    public void wildCropBlock(Block block, boolean isBushCrop) {
        if (isBushCrop) {
            this.simpleBlock(block, models().singleTexture(blockName(block), resourceBlock("bush_crop"), "crop", resourceBlock(blockName(block))).renderType("cutout"));
        } else {
            this.simpleBlock(block, models().cross(blockName(block), resourceBlock(blockName(block))).renderType("cutout"));
        }
    }

    public void crateBlock(Block block, String cropName) {
        this.simpleBlock(block,
                models().cubeBottomTop(blockName(block),
                        resourceBlock(cropName + "_crate_side"),
                        new ResourceLocation(FarmersDelight.MODID, "block/crate_bottom"),
                        resourceBlock(cropName + "_crate_top")));
    }

    public void bagBlock(Block block) {
        String name = blockName(block);
        this.simpleBlock(block, models().withExistingParent(name, "cube")
                .texture("particle", resourceBlock(name + "_top"))
                .texture("down", resourceBlock(name + "_bottom"))
                .texture("up", resourceBlock(name + "_top"))
                .texture("north", resourceBlock(name + "_side_tied"))
                .texture("south", resourceBlock(name + "_side_tied"))
                .texture("east", resourceBlock(name + "_side"))
                .texture("west", resourceBlock(name + "_side")));
    }

    public void plantBlock(Block block) {
        this.simpleBlock(block, models()
                .cross(blockName(block), resourceBlock(blockName(block))).renderType("cutout"));
    }

    public void pottedPlantBlock(Block pottedBlock, Block plantBlock) {
        simpleBlock(pottedBlock, models()
                .withExistingParent(blockName(pottedBlock), "minecraft:block/flower_pot_cross")
                .texture("plant", resourceBlock(blockName(plantBlock))).renderType("cutout"));
    }

    public void cabinetBlock(Block block, String woodType) {
        this.horizontalBlock(block, state -> {
            String suffix = state.getValue(CabinetBlock.OPEN) ? "_open" : "";
            return models().orientable(blockName(block) + suffix,
                    resourceBlock(woodType + "_cabinet_side"),
                    resourceBlock(woodType + "_cabinet_front" + suffix),
                    resourceBlock(woodType + "_cabinet_top"));
        });
    }

    public void feastBlock(FeastBlock block) {
        getVariantBuilder(block)
                .forAllStates(state -> {
                    IntegerProperty servingsProperty = block.getServingsProperty();
                    int servings = state.getValue(servingsProperty);

                    String suffix = "_stage" + (block.getMaxServings() - servings);

                    if (servings == 0) {
                        suffix = block.hasLeftovers ? "_leftover" : "_stage" + (servingsProperty.getPossibleValues().toArray().length - 2);
                    }

                    return ConfiguredModel.builder()
                            .modelFile(existingModel(blockName(block) + suffix))
                            .rotationY(((int) state.getValue(FeastBlock.FACING).toYRot() + DEFAULT_ANGLE_OFFSET) % 360)
                            .build();
                });
    }

    public void pieBlock(Block block) {
        getVariantBuilder(block)
                .forAllStates(state -> {
                            int bites = state.getValue(PieBlock.BITES);
                            String suffix = bites > 0 ? "_slice" + bites : "";
                            return ConfiguredModel.builder()
                                    .modelFile(existingModel(blockName(block) + suffix))
                                    .rotationY(((int) state.getValue(PieBlock.FACING).toYRot() + DEFAULT_ANGLE_OFFSET) % 360)
                                    .build();
                        }
                );
    }

    public void hugeBlackMushroomBlock(Block block) {
        for (boolean boolValue : new boolean[]{true, false}) {
            for (Map.Entry<Direction, BooleanProperty> entry : PipeBlock.PROPERTY_BY_DIRECTION.entrySet()) {
                int xRot = 0;
                int yRot = 0;
                switch (entry.getKey()) {
                    case EAST -> yRot = 90;
                    case SOUTH -> yRot = 180;
                    case WEST -> yRot = 270;
                    case UP -> xRot = 270;
                    case DOWN -> xRot = 90;
                    default -> {
                    }
                }
                getMultipartBuilder(block).part()
                        .modelFile((boolValue) ? existingModel(blockName(block)) : existingModel("black_mushroom_block_inside")).rotationX(xRot).rotationY(yRot).uvLock(boolValue).addModel()
                        .condition(entry.getValue(), boolValue).end();
            }
        }
    }

    public void farmlandBlock(Block block) {
        getVariantBuilder(block)
                .forAllStates(blockState -> {
                    int moisture = blockState.getValue(BlockStateProperties.MOISTURE);
                    String suffix = moisture >= 7 ? "_moist" : "";
                    return ConfiguredModel.builder()
                            .modelFile(existingModel(blockName(block) + suffix))
                            .build();
                });
    }

    public void cakeBlock(Block block) {
        getVariantBuilder(block)
                .forAllStates(blockState -> {
                    int bites = blockState.getValue(ConsumableCakeBlock.BITES);
                    String suffix = bites > 0 ? "_slice" + bites : "";
                    return ConfiguredModel.builder()
                            .modelFile(existingModel(blockName(block) + suffix))
                            .build();
                });
    }

    // Credits to the Neapolitan mod for candle cake code generation.
    public void candleCakeBlock(ConsumableCandleCakeBlock block) {
        Block candle = block.getCandleBlock();
        Block cake = block.getCakeBlock();

        ModelFile candleCake = models().withExistingParent(blockName(block), "block/template_cake_with_candle")
                .texture("candle", blockTexture(candle))
                .texture("bottom", withSuffix(blockTexture(cake), "_bottom"))
                .texture("side", withSuffix(blockTexture(cake), "_side"))
                .texture("top", withSuffix(blockTexture(cake), "_top"))
                .texture("particle", withSuffix(blockTexture(cake), "_side"));

        ModelFile candleCakeLit = models().withExistingParent(blockName(block) + "_lit", "block/template_cake_with_candle")
                .texture("candle", withSuffix(blockTexture(candle), "_lit"))
                .texture("bottom", withSuffix(blockTexture(cake), "_bottom"))
                .texture("side", withSuffix(blockTexture(cake), "_side"))
                .texture("top", withSuffix(blockTexture(cake), "_top"))
                .texture("particle", withSuffix(blockTexture(cake), "_side"));

        Function<BlockState, ModelFile> function = blockState -> blockState.getValue(BlockStateProperties.LIT) ? candleCakeLit : candleCake;

        this.getVariantBuilder(block).forAllStates(state -> ConfiguredModel.builder().modelFile(function.apply(state)).build());
    }

    public void wineShelfBlock(Block block) {
        MultiPartBlockStateBuilder builder = getMultipartBuilder(block);
        int[] rotations = {270, 0, 90, 180};
        Direction[] directions = {Direction.WEST, Direction.NORTH, Direction.EAST, Direction.SOUTH};

        ModelFile baseModel = existingModel(blockName(block));
        ModelFile supportModel = existingModel(blockName(block) + "_support");

        for (int i = 0; i < rotations.length; i++) {
            int rotation = rotations[i];
            Direction direction = directions[i];

            builder.part().modelFile(baseModel).rotationY(rotation).addModel()
                    .condition(HorizontalDirectionalBlock.FACING, direction).end();

            builder.part().modelFile(supportModel).rotationY(rotation).addModel()
                    .condition(HorizontalDirectionalBlock.FACING, direction)
                    .condition(WineShelfBlock.HAS_UPPER_SUPPORT, true).end();

            builder.part().modelFile(existingModel("blood_wine_bottle_slot_top_left")).rotationY(rotation).addModel()
                    .condition(HorizontalDirectionalBlock.FACING, direction)
                    .condition(WineShelfBlock.WINE_SHELF_SLOT_0, WineShelfBlock.Slot.BLOOD_WINE_BOTTLE).end();
            builder.part().modelFile(existingModel("blood_wine_bottle_slot_top_right")).rotationY(rotation).addModel()
                    .condition(HorizontalDirectionalBlock.FACING, direction)
                    .condition(WineShelfBlock.WINE_SHELF_SLOT_1, WineShelfBlock.Slot.BLOOD_WINE_BOTTLE).end();
            builder.part().modelFile(existingModel("blood_wine_bottle_slot_bottom_left")).rotationY(rotation).addModel()
                    .condition(HorizontalDirectionalBlock.FACING, direction)
                    .condition(WineShelfBlock.WINE_SHELF_SLOT_2, WineShelfBlock.Slot.BLOOD_WINE_BOTTLE).end();
            builder.part().modelFile(existingModel("blood_wine_bottle_slot_bottom_right")).rotationY(rotation).addModel()
                    .condition(HorizontalDirectionalBlock.FACING, direction)
                    .condition(WineShelfBlock.WINE_SHELF_SLOT_3, WineShelfBlock.Slot.BLOOD_WINE_BOTTLE).end();
        }
    }

    public ResourceLocation withSuffix(ResourceLocation resourceLocation, String suffix) {
        return new ResourceLocation(resourceLocation.getNamespace(), resourceLocation.getPath() + suffix);
    }
}
