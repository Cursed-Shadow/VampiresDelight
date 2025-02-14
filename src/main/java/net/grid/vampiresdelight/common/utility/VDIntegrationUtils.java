package net.grid.vampiresdelight.common.utility;

import de.teamlapen.vampirism.api.VampirismAPI;
import de.teamlapen.vampirism.api.VampirismRegistries;
import de.teamlapen.vampirism.api.entity.factions.IFaction;
import de.teamlapen.vampirism.api.entity.factions.IFactionPlayerHandler;
import de.teamlapen.vampirism.api.entity.player.skills.ISkill;
import net.grid.vampiresdelight.common.tag.VDCompatibilityTags;
import net.grid.vampiresdelight.common.tag.VDCommonTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.neoforged.fml.ModList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class VDIntegrationUtils {
    public static boolean isModPresent(String string) {
        return ModList.get().isLoaded(string);
    }

    // Mod ids
    public static final String WEREWOLVES = "werewolves";
    public static final String APPLESKIN = "appleskin";

    // Werewolves
    private static IFaction<?> werewolfFaction = null;

    public static IFaction<?> werewolfFaction() {
        if (werewolfFaction == null) {
            werewolfFaction = VampirismAPI.factionRegistry().getFactionByID(ResourceLocation.fromNamespaceAndPath(WEREWOLVES, "werewolf"));
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
        return werewolf != null && VampirismAPI.factionPlayerHandler(player).isInFaction(werewolf);
    }

    public static boolean hasSkill(Player player, ResourceLocation skillId) {
        IFactionPlayerHandler playerHandler = VampirismAPI.factionPlayerHandler(player);
        ISkill<?> requiredSkill = VampirismRegistries.SKILL.get().get(skillId);
        if (requiredSkill != null && playerHandler.getCurrentFactionPlayer().isPresent()) {
            return playerHandler.getCurrentFactionPlayer().get().getSkillHandler().isSkillEnabled(requiredSkill);
        } else {
            return false;
        }
    }

    public static final ResourceLocation WOLF_BERRIES = ResourceLocation.fromNamespaceAndPath(WEREWOLVES, "wolf_berries");
    public static final ResourceLocation LIVER = ResourceLocation.fromNamespaceAndPath(WEREWOLVES, "liver");

    public static final ResourceLocation NOT_MEAT = ResourceLocation.fromNamespaceAndPath(WEREWOLVES, "not_meat");

    public static boolean isWerewolfVegetarian(Player player) {
        return hasSkill(player, NOT_MEAT);
    }

    public static boolean isMeat(@Nullable LivingEntity entity, ItemStack stack) {
        // TODO: Access the configs somehow
        return stack.getFoodProperties(entity) != null && (stack.is(ItemTags.MEAT) || stack.is(VDCompatibilityTags.WEREWOLF_FOOD)); //|| WerewolvesConfig.SERVER.isCustomMeatItems(stack.getItem()));
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
        public @NotNull TagKey<Block> getIncorrectBlocksForDrops() {
            return VDCompatibilityTags.INCORRECT_FOR_SILVER_TOOL;
        }

        @Override
        public int getEnchantmentValue() {
            return 14;
        }

        @Override
        public @NotNull Ingredient getRepairIngredient() {
            return Ingredient.of(VDCommonTags.SILVER_INGOT);
        }
    };
}
