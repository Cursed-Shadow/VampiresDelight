package net.grid.vampiresdelight.common.item;

import de.teamlapen.vampirism.VampirismMod;
import de.teamlapen.vampirism.api.VReference;
import de.teamlapen.vampirism.config.VampirismConfig;
import de.teamlapen.vampirism.effects.SanguinareEffect;
import de.teamlapen.vampirism.util.Helper;
import net.grid.vampiresdelight.common.utility.VDTextUtils;
import net.grid.vampiresdelight.common.utility.VDTooltipUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
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
    private final FoodProperties defaultFood;

    public OrchidTeaItem(Properties properties, FoodProperties vampireFood, FoodProperties defaultFood) {
        super(properties, vampireFood, defaultFood);
        this.defaultFood = defaultFood;
    }

    @Override
    public void affectConsumer(ItemStack stack, Level level, LivingEntity consumer) {
        if (Helper.canBecomeVampire((Player) consumer) && !VampirismConfig.SERVER.disableFangInfection.get()) {
            SanguinareEffect.addRandom(consumer, true);
        }
    }

    @Override
    public @Nullable FoodProperties getFoodProperties(ItemStack stack, @Nullable LivingEntity entity) {
        if (entity == null) {
            entity = VampirismMod.proxy.getClientPlayer();
        }

        return Helper.isVampire(entity) ? getVampireFood() :
                (entity instanceof Player player && Helper.canBecomeVampire(player) && !VampirismConfig.SERVER.disableFangInfection.get() ?
                        super.getFoodProperties(stack, entity) : defaultFood);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag isAdvanced) {
        Player player = Minecraft.getInstance().player;

        if (Configuration.FOOD_EFFECT_TOOLTIP.get()) {
            if (player != null && Helper.canBecomeVampire(player) && !VampirismConfig.SERVER.disableFangInfection.get()) {
                MutableComponent textEmpty = VDTextUtils.getTranslation("tooltip." + this);
                tooltip.add(textEmpty.withStyle(ChatFormatting.BLUE));
            } else {
                FoodProperties foodProperties = this.getFoodProperties(stack, player);

                if (foodProperties != null && !foodProperties.getEffects().isEmpty()) {
                    VDTextUtils.addFoodEffectTooltip(foodProperties, tooltip, 1.0F);
                }
            }
        }

        if (player != null)
            VDTooltipUtils.addFactionFoodToolTips(tooltip, player, VReference.VAMPIRE_FACTION);
    }
}
