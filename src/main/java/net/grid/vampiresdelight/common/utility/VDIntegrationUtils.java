package net.grid.vampiresdelight.common.utility;

import de.teamlapen.vampirism.api.VampirismAPI;
import de.teamlapen.vampirism.api.VampirismRegistries;
import de.teamlapen.vampirism.api.entity.factions.IFaction;
import de.teamlapen.vampirism.api.entity.factions.IFactionPlayerHandler;
import de.teamlapen.vampirism.api.entity.player.skills.ISkill;
import net.grid.vampiresdelight.common.tag.VDCompatibilityTags;
import net.grid.vampiresdelight.common.tag.VDForgeTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.LoadingModList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class VDIntegrationUtils {
    public static boolean isModPresent(String string) {
        return ModList.get().isLoaded(string);
    }

    public static boolean isLoadingModPresent(String string) {
        return LoadingModList.get().getModFileById(string) != null;
    }

    // Mod ids
    public static final String WEREWOLVES = "werewolves";
    public static final String APPLESKIN = "appleskin";

    // Werewolves
    private static IFaction<?> werewolfFaction = null;

    public static IFaction<?> werewolfFaction() {
        if (werewolfFaction == null) {
            werewolfFaction = VampirismAPI.factionRegistry().getFactionByID(new ResourceLocation(WEREWOLVES, "werewolf"));
        }
        return werewolfFaction;
    }

    public static boolean isWerewolf(Entity entity) {
        if (entity instanceof Player player && isWerewolf(player))
            return true;

        IFaction<?> faction = VampirismAPI.factionRegistry().getFaction(entity);
        return faction != null && faction.getID().getPath().equals("werewolf");
    }

    public static boolean isWerewolf(Player player) {
        IFaction<?> werewolf = werewolfFaction();
        return werewolf != null && VampirismAPI.getFactionPlayerHandler(player).map(h -> werewolf.equals(h.getCurrentFaction())).orElse(false);
    }

    public static boolean hasSkill(Player player, ResourceLocation skillId) {
        LazyOptional<IFactionPlayerHandler> playerHandler = player.isAlive() ? VampirismAPI.getFactionPlayerHandler(player) : LazyOptional.empty();
        ISkill<?> requiredSkill = VampirismRegistries.SKILLS.get().getValue(skillId);
        if (requiredSkill != null) {
            return playerHandler.map(IFactionPlayerHandler::getCurrentFactionPlayer).flatMap(p -> p.map(d-> d.getSkillHandler().isSkillEnabled(requiredSkill))).orElse(false);
        } else {
            return false;
        }
    }

    public static ResourceLocation WOLF_BERRIES = new ResourceLocation(WEREWOLVES, "wolf_berries");
    public static ResourceLocation LIVER = new ResourceLocation(WEREWOLVES, "liver");

    public static ResourceLocation NOT_MEAT = new ResourceLocation(WEREWOLVES, "not_meat");

    public static boolean isWerewolfVegetarian(Player player) {
        return hasSkill(player, NOT_MEAT);
    }

    public static boolean isMeat(@Nullable LivingEntity entity, ItemStack stack) {
        FoodProperties foodProperties = stack.getFoodProperties(entity);
        return stack.isEdible() && foodProperties != null && (foodProperties.isMeat() || stack.is(VDCompatibilityTags.WEREWOLF_FOOD)); //|| WerewolvesConfig.SERVER.isCustomMeatItems(stack.getItem()));
    }

    public static boolean canWerewolfEatFood(LivingEntity entity, ItemStack stack) {
        return isMeat(entity, stack) || !(entity instanceof Player player) || !isWerewolf(player) || isWerewolfVegetarian(player);
    }

    public static final Tier SILVER_ITEM_TIER = new Tier() {
        @Override
        public int getUses() {
            return 250;
        }

        @Override
        public float getSpeed() {
            return 6.0f;
        }

        @Override
        public float getAttackDamageBonus() {
            return 2f;
        }

        @Override
        public int getLevel() {
            return 2;
        }

        @Override
        public int getEnchantmentValue() {
            return 14;
        }

        @Override
        public @NotNull Ingredient getRepairIngredient() {
            return Ingredient.of(VDForgeTags.SILVER_INGOT);
        }

        @Override
        public TagKey<Block> getTag() {
            return VDForgeTags.NEEDS_SILVER_TOOL;
        }
    };
}
