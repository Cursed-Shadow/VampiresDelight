package net.grid.vampiresdelight.common.item;

import de.teamlapen.vampirism.VampirismMod;
import de.teamlapen.vampirism.api.VReference;
import de.teamlapen.vampirism.config.VampirismConfig;
import net.grid.vampiresdelight.common.food.VDFoodFeatures;
import net.grid.vampiresdelight.common.food.VDFoodValues;
import net.grid.vampiresdelight.common.registry.VDItems;
import net.grid.vampiresdelight.common.utility.VDHelper;
import net.grid.vampiresdelight.common.utility.VDTextUtils;
import net.grid.vampiresdelight.common.utility.VDTooltipUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;
import vectorwing.farmersdelight.common.Configuration;

import java.util.List;

public class OrchidTeaItem extends VampireDrinkableItem {
    public OrchidTeaItem() {
        super(VDItems.drinkItem(VDFoodValues.ORCHID_TEA_HUMAN), VDFoodValues.ORCHID_TEA_VAMPIRE, VDFoodValues.ORCHID_TEA_IMMUNE, VDFoodFeatures.ORCHID_TEA);
    }

    @Override
    public @Nullable FoodProperties getFoodProperties(ItemStack stack, @Nullable LivingEntity entity) {
        if (entity == null) {
            entity = VampirismMod.proxy.getClientPlayer();
        }

        return VDHelper.isVampire(entity) ? getVampireFood() :
                (entity instanceof Player player && VDHelper.canBeInfectedFromItem(player) ?
                        super.getFoodProperties(stack, entity) : getHunterFood());
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag isAdvanced) {
        Player player = VampirismMod.proxy.getClientPlayer();

        if (Configuration.FOOD_EFFECT_TOOLTIP.get()) {
            if (player != null && VDHelper.canBecomeVampire(player) && !VampirismConfig.SERVER.disableFangInfection.get()) {
                MutableComponent textEmpty = VDTextUtils.getTranslation("tooltip." + this);
                tooltip.add(textEmpty.withStyle(ChatFormatting.BLUE));
            } else {
                VDTextUtils.addFoodEffectTooltip(stack, player, tooltip);
            }
        }

        if (player != null)
            VDTooltipUtils.addFactionFoodToolTips(tooltip, player, VReference.VAMPIRE_FACTION);
    }
}
