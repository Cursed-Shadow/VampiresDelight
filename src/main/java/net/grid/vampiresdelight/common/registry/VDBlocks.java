package net.grid.vampiresdelight.common.registry;

import de.teamlapen.lib.lib.util.UtilLib;
import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.block.*;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import vectorwing.farmersdelight.common.block.*;

import java.util.function.ToIntFunction;

public class VDBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, VampiresDelight.MODID);

    public static ToIntFunction<BlockState> litBlockEmission(int lightValue) {
        return (state) -> state.getValue(BlockStateProperties.LIT) ? lightValue : 0;
    }

    // Workstations
    public static final RegistryObject<DarkStoneStoveBlock> DARK_STONE_STOVE = BLOCKS.register("dark_stone_stove",
            () -> new DarkStoneStoveBlock(Block.Properties.of().mapColor(MapColor.DEEPSLATE).requiresCorrectToolForDrops().strength(2f, 10f).sound(SoundType.STONE).lightLevel(litBlockEmission(13))));

    // Crop Storage
    public static final RegistryObject<Block> GARLIC_CRATE = BLOCKS.register("garlic_crate",
            () -> new Block(Block.Properties.copy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> ORCHID_BAG = BLOCKS.register("orchid_bag",
            () -> new Block(Block.Properties.copy(Blocks.WHITE_WOOL)));

    // Building
    public static final RegistryObject<SpiritLanternBlock> SPIRIT_LANTERN = BLOCKS.register("spirit_lantern",
            () -> new SpiritLanternBlock(Block.Properties.of().mapColor(MapColor.METAL).forceSolidOn()
                    .requiresCorrectToolForDrops().strength(3.5F).sound(SoundType.LANTERN)
                    .lightLevel((state) -> 12).noOcclusion().pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<CabinetBlock> DARK_SPRUCE_CABINET = BLOCKS.register("dark_spruce_cabinet",
            () -> new CabinetBlock(Block.Properties.copy(Blocks.BARREL)));
    public static final RegistryObject<CabinetBlock> CURSED_SPRUCE_CABINET = BLOCKS.register("cursed_spruce_cabinet",
            () -> new CabinetBlock(Block.Properties.copy(Blocks.BARREL)));
    public static final RegistryObject<CabinetBlock> JACARANDA_CABINET = BLOCKS.register("jacaranda_cabinet",
            () -> new CabinetBlock(Block.Properties.copy(Blocks.BARREL)));
    public static final RegistryObject<CabinetBlock> MAGIC_CABINET = BLOCKS.register("magic_cabinet",
            () -> new CabinetBlock(Block.Properties.copy(Blocks.BARREL)));
    public static final RegistryObject<WineShelfBlock> OAK_WINE_SHELF = BLOCKS.register("oak_wine_shelf",
            () -> new WineShelfBlock(Block.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistryObject<WineShelfBlock> SPRUCE_WINE_SHELF = BLOCKS.register("spruce_wine_shelf",
            () -> new WineShelfBlock(Block.Properties.copy(Blocks.SPRUCE_PLANKS)));
    public static final RegistryObject<WineShelfBlock> BIRCH_WINE_SHELF = BLOCKS.register("birch_wine_shelf",
            () -> new WineShelfBlock(Block.Properties.copy(Blocks.BIRCH_PLANKS)));
    public static final RegistryObject<WineShelfBlock> JUNGLE_WINE_SHELF = BLOCKS.register("jungle_wine_shelf",
            () -> new WineShelfBlock(Block.Properties.copy(Blocks.JUNGLE_PLANKS)));
    public static final RegistryObject<WineShelfBlock> ACACIA_WINE_SHELF = BLOCKS.register("acacia_wine_shelf",
            () -> new WineShelfBlock(Block.Properties.copy(Blocks.ACACIA_PLANKS)));
    public static final RegistryObject<WineShelfBlock> DARK_OAK_WINE_SHELF = BLOCKS.register("dark_oak_wine_shelf",
            () -> new WineShelfBlock(Block.Properties.copy(Blocks.DARK_OAK_PLANKS)));
    public static final RegistryObject<WineShelfBlock> MANGROVE_WINE_SHELF = BLOCKS.register("mangrove_wine_shelf",
            () -> new WineShelfBlock(Block.Properties.copy(Blocks.MANGROVE_PLANKS)));
    public static final RegistryObject<WineShelfBlock> CHERRY_WINE_SHELF = BLOCKS.register("cherry_wine_shelf",
            () -> new WineShelfBlock(Block.Properties.copy(Blocks.CHERRY_PLANKS)));
    public static final RegistryObject<WineShelfBlock> BAMBOO_WINE_SHELF = BLOCKS.register("bamboo_wine_shelf",
            () -> new WineShelfBlock(Block.Properties.copy(Blocks.BAMBOO_PLANKS)));
    public static final RegistryObject<WineShelfBlock> CRIMSON_WINE_SHELF = BLOCKS.register("crimson_wine_shelf",
            () -> new WineShelfBlock(Block.Properties.copy(Blocks.CRIMSON_PLANKS)));
    public static final RegistryObject<WineShelfBlock> WARPED_WINE_SHELF = BLOCKS.register("warped_wine_shelf",
            () -> new WineShelfBlock(Block.Properties.copy(Blocks.WARPED_PLANKS)));
    public static final RegistryObject<WineShelfBlock> CURSED_SPRUCE_WINE_SHELF = BLOCKS.register("cursed_spruce_wine_shelf",
            () -> new WineShelfBlock(Block.Properties.of().ignitedByLava().mapColor(MapColor.CRIMSON_HYPHAE).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<WineShelfBlock> DARK_SPRUCE_WINE_SHELF = BLOCKS.register("dark_spruce_wine_shelf",
            () -> new WineShelfBlock(Block.Properties.of().ignitedByLava().mapColor(MapColor.COLOR_GRAY).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<WineShelfBlock> JACARANDA_WINE_SHELF = BLOCKS.register("jacaranda_wine_shelf",
            () -> new WineShelfBlock(Block.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistryObject<WineShelfBlock> MAGIC_WINE_SHELF = BLOCKS.register("magic_wine_shelf",
            () -> new WineShelfBlock(Block.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistryObject<BarStoolBlock> WHITE_BAR_STOOL = BLOCKS.register("white_bar_stool", BarStoolBlock::new);
    public static final RegistryObject<BarStoolBlock> ORANGE_BAR_STOOL = BLOCKS.register("orange_bar_stool", BarStoolBlock::new);
    public static final RegistryObject<BarStoolBlock> MAGENTA_BAR_STOOL = BLOCKS.register("magenta_bar_stool", BarStoolBlock::new);
    public static final RegistryObject<BarStoolBlock> LIGHT_BLUE_BAR_STOOL = BLOCKS.register("light_blue_bar_stool", BarStoolBlock::new);
    public static final RegistryObject<BarStoolBlock> YELLOW_BAR_STOOL = BLOCKS.register("yellow_bar_stool", BarStoolBlock::new);
    public static final RegistryObject<BarStoolBlock> LIME_BAR_STOOL = BLOCKS.register("lime_bar_stool", BarStoolBlock::new);
    public static final RegistryObject<BarStoolBlock> PINK_BAR_STOOL = BLOCKS.register("pink_bar_stool", BarStoolBlock::new);
    public static final RegistryObject<BarStoolBlock> GRAY_BAR_STOOL = BLOCKS.register("gray_bar_stool", BarStoolBlock::new);
    public static final RegistryObject<BarStoolBlock> LIGHT_GRAY_BAR_STOOL = BLOCKS.register("light_gray_bar_stool", BarStoolBlock::new);
    public static final RegistryObject<BarStoolBlock> CYAN_BAR_STOOL = BLOCKS.register("cyan_bar_stool", BarStoolBlock::new);
    public static final RegistryObject<BarStoolBlock> PURPLE_BAR_STOOL = BLOCKS.register("purple_bar_stool", BarStoolBlock::new);
    public static final RegistryObject<BarStoolBlock> BLUE_BAR_STOOL = BLOCKS.register("blue_bar_stool", BarStoolBlock::new);
    public static final RegistryObject<BarStoolBlock> BROWN_BAR_STOOL = BLOCKS.register("brown_bar_stool", BarStoolBlock::new);
    public static final RegistryObject<BarStoolBlock> GREEN_BAR_STOOL = BLOCKS.register("green_bar_stool", BarStoolBlock::new);
    public static final RegistryObject<BarStoolBlock> RED_BAR_STOOL = BLOCKS.register("red_bar_stool", BarStoolBlock::new);
    public static final RegistryObject<BarStoolBlock> BLACK_BAR_STOOL = BLOCKS.register("black_bar_stool", BarStoolBlock::new);

    // Farming
    public static final RegistryObject<CursedFarmlandBlock> CURSED_FARMLAND = BLOCKS.register("cursed_farmland",
            () -> new CursedFarmlandBlock(Block.Properties.copy(Blocks.FARMLAND).strength(0.6f, 2.0f).sound(SoundType.GRAVEL).mapColor(MapColor.TERRACOTTA_BROWN)));
    public static final RegistryObject<HugeMushroomBlock> BLACK_MUSHROOM_BLOCK = BLOCKS.register("black_mushroom_block",
            () -> new HugeMushroomBlock(Block.Properties.copy(Blocks.RED_MUSHROOM_BLOCK).mapColor(MapColor.TERRACOTTA_BLACK)));
    public static final RegistryObject<HugeMushroomBlock> BLACK_MUSHROOM_STEM  = BLOCKS.register("black_mushroom_stem",
            () -> new HugeMushroomBlock(Block.Properties.copy(Blocks.MUSHROOM_STEM).mapColor(MapColor.TERRACOTTA_GRAY)));
    public static final RegistryObject<BlackMushroomBlock> BLACK_MUSHROOM = BLOCKS.register("black_mushroom",
            () -> new BlackMushroomBlock(Block.Properties.copy(Blocks.RED_MUSHROOM).mapColor(MapColor.TERRACOTTA_BLACK).sound(SoundType.FUNGUS)));
    public static final RegistryObject<FlowerPotBlock> POTTED_BLACK_MUSHROOM = BLOCKS.register("potted_black_mushroom",
            () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, BLACK_MUSHROOM, Block.Properties.of().noCollission().isViewBlocking(UtilLib::never).pushReaction(PushReaction.DESTROY).instabreak()));

    // Composting
    public static final RegistryObject<BloodySoilBlock> BLOODY_SOIL = BLOCKS.register("bloody_soil",
            () -> new BloodySoilBlock(Block.Properties.copy(Blocks.DIRT).strength(0.5f, 2.0f).sound(SoundType.GRAVEL).mapColor(MapColor.TERRACOTTA_RED).randomTicks()));
    public static final RegistryObject<BloodySoilFarmlandBlock> BLOODY_SOIL_FARMLAND = BLOCKS.register("bloody_soil_farmland",
            () -> new BloodySoilFarmlandBlock(Block.Properties.copy(Blocks.FARMLAND).strength(0.5f, 2.0f).sound(SoundType.GRAVEL).mapColor(MapColor.TERRACOTTA_RED)));

    // Pastries
    public static final RegistryObject<VampirePieBlock> BLOOD_PIE = BLOCKS.register("blood_pie",
            () -> new VampirePieBlock(Block.Properties.copy(Blocks.CAKE), VDItems.BLOOD_PIE_SLICE::get));

    // Wild Crops
    public static final RegistryObject<WildGarlicBlock> WILD_GARLIC = BLOCKS.register("wild_garlic",
            () -> new WildGarlicBlock(MobEffects.BLINDNESS, 8, Block.Properties.copy(Blocks.TALL_GRASS)));

    // Crops
    public static final RegistryObject<VampireOrchidCropBlock> VAMPIRE_ORCHID_CROP = BLOCKS.register("vampire_orchid_crop",
            () -> new VampireOrchidCropBlock(Block.Properties.copy(Blocks.WHEAT).mapColor(MapColor.TERRACOTTA_MAGENTA).instabreak().noCollission().sound(SoundType.CROP)));

    // Feasts
    public static final RegistryObject<WeirdJellyBlock> WEIRD_JELLY_BLOCK = BLOCKS.register("weird_jelly_block",
            () -> new WeirdJellyBlock(Block.Properties.copy(Blocks.SLIME_BLOCK), VDItems.WEIRD_JELLY::get, true));

    // Cakes
    public static final RegistryObject<ConsumableCakeBlock> ORCHID_CAKE = BLOCKS.register("orchid_cake",
            () -> new ConsumableCakeBlock(Block.Properties.copy(Blocks.CAKE).mapColor(MapColor.TERRACOTTA_PURPLE), VDItems.ORCHID_CAKE_SLICE::get));
    public static final RegistryObject<ConsumableCandleCakeBlock> ORCHID_CANDLE_CAKE = BLOCKS.register("orchid_candle_cake",
            () -> candleCakeBlock(Blocks.CANDLE, ORCHID_CAKE.get(), MapColor.TERRACOTTA_PURPLE));
    public static final RegistryObject<ConsumableCandleCakeBlock> WHITE_ORCHID_CANDLE_CAKE = BLOCKS.register("white_orchid_candle_cake",
            () -> candleCakeBlock(Blocks.WHITE_CANDLE, ORCHID_CAKE.get(), MapColor.TERRACOTTA_PURPLE));
    public static final RegistryObject<ConsumableCandleCakeBlock> ORANGE_ORCHID_CANDLE_CAKE = BLOCKS.register("orange_orchid_candle_cake",
            () -> candleCakeBlock(Blocks.ORANGE_CANDLE, ORCHID_CAKE.get(), MapColor.TERRACOTTA_PURPLE));
    public static final RegistryObject<ConsumableCandleCakeBlock> MAGENTA_ORCHID_CANDLE_CAKE = BLOCKS.register("magenta_orchid_candle_cake",
            () -> candleCakeBlock(Blocks.MAGENTA_CANDLE, ORCHID_CAKE.get(), MapColor.TERRACOTTA_PURPLE));
    public static final RegistryObject<ConsumableCandleCakeBlock> LIGHT_BLUE_ORCHID_CANDLE_CAKE = BLOCKS.register("light_blue_orchid_candle_cake",
            () -> candleCakeBlock(Blocks.LIGHT_BLUE_CANDLE, ORCHID_CAKE.get(), MapColor.TERRACOTTA_PURPLE));
    public static final RegistryObject<ConsumableCandleCakeBlock> YELLOW_ORCHID_CANDLE_CAKE = BLOCKS.register("yellow_orchid_candle_cake",
            () -> candleCakeBlock(Blocks.YELLOW_CANDLE, ORCHID_CAKE.get(), MapColor.TERRACOTTA_PURPLE));
    public static final RegistryObject<ConsumableCandleCakeBlock> LIME_ORCHID_CANDLE_CAKE = BLOCKS.register("lime_orchid_candle_cake",
            () -> candleCakeBlock(Blocks.LIME_CANDLE, ORCHID_CAKE.get(), MapColor.TERRACOTTA_PURPLE));
    public static final RegistryObject<ConsumableCandleCakeBlock> PINK_ORCHID_CANDLE_CAKE = BLOCKS.register("pink_orchid_candle_cake",
            () -> candleCakeBlock(Blocks.PINK_CANDLE, ORCHID_CAKE.get(), MapColor.TERRACOTTA_PURPLE));
    public static final RegistryObject<ConsumableCandleCakeBlock> GRAY_ORCHID_CANDLE_CAKE = BLOCKS.register("gray_orchid_candle_cake",
            () -> candleCakeBlock(Blocks.GRAY_CANDLE, ORCHID_CAKE.get(), MapColor.TERRACOTTA_PURPLE));
    public static final RegistryObject<ConsumableCandleCakeBlock> LIGHT_GRAY_ORCHID_CANDLE_CAKE = BLOCKS.register("light_gray_orchid_candle_cake",
            () -> candleCakeBlock(Blocks.LIGHT_GRAY_CANDLE, ORCHID_CAKE.get(), MapColor.TERRACOTTA_PURPLE));
    public static final RegistryObject<ConsumableCandleCakeBlock> CYAN_ORCHID_CANDLE_CAKE = BLOCKS.register("cyan_orchid_candle_cake",
            () -> candleCakeBlock(Blocks.CYAN_CANDLE, ORCHID_CAKE.get(), MapColor.TERRACOTTA_PURPLE));
    public static final RegistryObject<ConsumableCandleCakeBlock> PURPLE_ORCHID_CANDLE_CAKE = BLOCKS.register("purple_orchid_candle_cake",
            () -> candleCakeBlock(Blocks.PURPLE_CANDLE, ORCHID_CAKE.get(), MapColor.TERRACOTTA_PURPLE));
    public static final RegistryObject<ConsumableCandleCakeBlock> BLUE_ORCHID_CANDLE_CAKE = BLOCKS.register("blue_orchid_candle_cake",
            () -> candleCakeBlock(Blocks.BLUE_CANDLE, ORCHID_CAKE.get(), MapColor.TERRACOTTA_PURPLE));
    public static final RegistryObject<ConsumableCandleCakeBlock> BROWN_ORCHID_CANDLE_CAKE = BLOCKS.register("brown_orchid_candle_cake",
            () -> candleCakeBlock(Blocks.BROWN_CANDLE, ORCHID_CAKE.get(), MapColor.TERRACOTTA_PURPLE));
    public static final RegistryObject<ConsumableCandleCakeBlock> GREEN_ORCHID_CANDLE_CAKE = BLOCKS.register("green_orchid_candle_cake",
            () -> candleCakeBlock(Blocks.GREEN_CANDLE, ORCHID_CAKE.get(), MapColor.TERRACOTTA_PURPLE));
    public static final RegistryObject<ConsumableCandleCakeBlock> RED_ORCHID_CANDLE_CAKE = BLOCKS.register("red_orchid_candle_cake",
            () -> candleCakeBlock(Blocks.RED_CANDLE, ORCHID_CAKE.get(), MapColor.TERRACOTTA_PURPLE));
    public static final RegistryObject<ConsumableCandleCakeBlock> BLACK_ORCHID_CANDLE_CAKE = BLOCKS.register("black_orchid_candle_cake",
            () -> candleCakeBlock(Blocks.BLACK_CANDLE, ORCHID_CAKE.get(), MapColor.TERRACOTTA_PURPLE));

    public static ConsumableCandleCakeBlock candleCakeBlock(Block candleBlock, ConsumableCakeBlock cakeBlock, MapColor mapColor) {
        return new ConsumableCandleCakeBlock(Block.Properties.copy(Blocks.CAKE)
                .mapColor(mapColor).lightLevel(litBlockEmission(3)), cakeBlock, candleBlock);
    }
}
