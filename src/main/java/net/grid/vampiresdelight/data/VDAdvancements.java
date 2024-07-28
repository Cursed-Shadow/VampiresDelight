package net.grid.vampiresdelight.data;

import de.teamlapen.vampirism.core.ModBlocks;
import de.teamlapen.vampirism.core.ModItems;
import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.advancement.DrinkPouredTrigger;
import net.grid.vampiresdelight.common.registry.VDAdvancementTriggers;
import net.grid.vampiresdelight.common.registry.VDBlocks;
import net.grid.vampiresdelight.common.registry.VDItems;
import net.grid.vampiresdelight.common.tag.VDTags;
import net.grid.vampiresdelight.common.utility.VDTextUtils;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

@SuppressWarnings("unused")
public class VDAdvancements implements AdvancementProvider.AdvancementGenerator {
    @Override
    public void generate(HolderLookup.Provider provider, Consumer<AdvancementHolder> consumer, ExistingFileHelper existingFileHelper) {
        AdvancementHolder vampiresDelight = Advancement.Builder.advancement()
                .display(VDItems.DARK_STONE_STOVE.get(),
                        VDTextUtils.getTranslation("advancement.root"),
                        VDTextUtils.getTranslation("advancement.root.desc"),
                        ResourceLocation.parse("vampiresdelight:textures/block/dark_stone_small_bricks.png"),
                        AdvancementType.TASK, false, false, false)
                .addCriterion("seeds", InventoryChangeTrigger.TriggerInstance.hasItems(new ItemLike[]{}))
                .save(consumer, getNameId("main/root"));

        // Eating Branch
        AdvancementHolder disgusting = getAdvancement(vampiresDelight, ModItems.HUMAN_HEART.get(), "consume_disgusting_food", AdvancementType.TASK, true, false)
                .addCriterion("disgusting_food", VDAdvancementTriggers.DISGUSTING_FOOD_CONSUMED.get().createCriterion(new PlayerTrigger.TriggerInstance(Optional.empty())))
                .save(consumer, getNameId("main/consume_disgusting_food"));

        // Human Branch
        AdvancementHolder iKnewYouElves = getAdvancement(vampiresDelight, VDItems.DANDELION_BEER_MUG.get(), "pour_dandelion_beer", AdvancementType.TASK, true, false)
                .addCriterion("pour_dandelion_beer", DrinkPouredTrigger.TriggerInstance.pouredDrinkBottle( VDItems.DANDELION_BEER_BOTTLE.get()))
                .save(consumer, getNameId("main/pour_dandelion_beer"));

        AdvancementHolder localBrewery = getAdvancement(iKnewYouElves, VDItems.SPRUCE_WINE_SHELF.get(), "place_dandelion_beer_bottle_on_shelf", AdvancementType.TASK, false, false)
                .addCriterion("wine_shelf", ItemUsedOnLocationTrigger.TriggerInstance.itemUsedOnBlock(
                        LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(VDTags.WINE_SHELF)),
                        ItemPredicate.Builder.item().of(VDItems.DANDELION_BEER_BOTTLE.get())))
                .save(consumer, getNameId("main/place_dandelion_beer_bottle_on_shelf"));

        // Hunter Branch
        AdvancementHolder messiahOfHumanity = getAdvancement(vampiresDelight, VDItems.WILD_GARLIC.get(), "get_garlic", AdvancementType.TASK, false, false)
                .addCriterion("garlic", InventoryChangeTrigger.TriggerInstance.hasItems(ModBlocks.GARLIC.asItem()))
                .save(consumer, getNameId("main/get_garlic"));

        // Vampire Branch
        AdvancementHolder yourBestFriend = getAdvancement(vampiresDelight, ModBlocks.VAMPIRE_ORCHID.get().asItem(), "get_vampire_orchid", AdvancementType.TASK, false, false)
                .addCriterion("vampire_orchid", InventoryChangeTrigger.TriggerInstance.hasItems(ModBlocks.VAMPIRE_ORCHID.get()))
                .save(consumer, getNameId("main/get_vampire_orchid"));

        AdvancementHolder bothBeautyAndHealth = getAdvancement(yourBestFriend, VDItems.ORCHID_PETALS.get(), "plant_vampire_orchid_crop", AdvancementType.TASK, false, false)
                .addCriterion("vampire_orchid", ItemUsedOnLocationTrigger.TriggerInstance.placedBlock(VDBlocks.VAMPIRE_ORCHID_CROP.get()))
                .save(consumer, getNameId("main/plant_vampire_orchid_crop"));

        AdvancementHolder putPepperInTea = getAdvancement(bothBeautyAndHealth, VDItems.ORCHID_TEA.get(), "get_orchid_tea", AdvancementType.TASK, false, false)
                .addCriterion("orchid_tea", InventoryChangeTrigger.TriggerInstance.hasItems(VDItems.ORCHID_TEA.get()))
                .save(consumer, getNameId("main/get_orchid_tea"));

        AdvancementHolder bloodWineTastesTheSameAsIRemember = getAdvancement(yourBestFriend, VDItems.BLOOD_WINE_GLASS.get(), "pour_blood_wine", AdvancementType.TASK, true, false)
                .addCriterion("pour_blood_wine", DrinkPouredTrigger.TriggerInstance.pouredDrinkBottle(VDItems.BLOOD_WINE_BOTTLE.get()))
                .save(consumer, getNameId("main/pour_blood_wine"));

        AdvancementHolder localBar = getAdvancement(bloodWineTastesTheSameAsIRemember, VDItems.DARK_SPRUCE_WINE_SHELF.get(), "place_blood_wine_bottle_on_shelf", AdvancementType.TASK, false, false)
                .addCriterion("wine_shelf", ItemUsedOnLocationTrigger.TriggerInstance.itemUsedOnBlock(
                        LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(VDTags.WINE_SHELF)),
                        ItemPredicate.Builder.item().of(VDItems.BLOOD_WINE_BOTTLE.get())))
                .save(consumer, getNameId("main/place_blood_wine_bottle_on_shelf"));

        AdvancementHolder funnyCutsOfChildren = getAdvancement(yourBestFriend, VDItems.HUMAN_EYE.get(), "get_human_eye", AdvancementType.TASK, true, false)
                .addCriterion("human_eye", InventoryChangeTrigger.TriggerInstance.hasItems(VDItems.HUMAN_EYE.get()))
                .save(consumer, getNameId("main/get_human_eye"));
    }

    protected static Advancement.Builder getAdvancement(AdvancementHolder parent, ItemLike display, String name, AdvancementType frame, boolean announceToChat, boolean hidden) {
        return Advancement.Builder.advancement().parent(parent).display(display,
                VDTextUtils.getTranslation("advancement." + name),
                VDTextUtils.getTranslation("advancement." + name + ".desc"),
                null, frame, true, announceToChat, hidden);
    }

    private String getNameId(String id) {
        return VampiresDelight.MODID + ":" + id;
    }

    static class Provider extends AdvancementProvider {
        public Provider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper existingFileHelper) {
            super(output, registries, existingFileHelper, List.of(new VDAdvancements()));
        }
    }
}
