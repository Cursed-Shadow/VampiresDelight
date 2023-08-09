package net.grid.vampiresdelight.common.item;

import de.teamlapen.vampirism.VampirismMod;
import de.teamlapen.vampirism.api.VReference;
import de.teamlapen.vampirism.api.entity.factions.IFaction;
import de.teamlapen.vampirism.api.items.IFactionExclusiveItem;
import de.teamlapen.vampirism.config.VampirismConfig;
import de.teamlapen.vampirism.effects.SanguinareEffect;
import de.teamlapen.vampirism.util.Helper;
import net.grid.vampiresdelight.common.util.VDTextUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import vectorwing.farmersdelight.common.Configuration;
import vectorwing.farmersdelight.common.item.DrinkableItem;
import vectorwing.farmersdelight.common.utility.TextUtils;

import java.util.List;

public class OrchidTeaItem extends DrinkableItem implements IFactionExclusiveItem {
    public OrchidTeaItem(Properties properties) {
        super(properties, false, true);
    }

    @Nullable
    @Override
    public IFaction<?> getExclusiveFaction(@NotNull ItemStack stack) {
        return VReference.VAMPIRE_FACTION;
    }

    @Override
    public void affectConsumer(ItemStack stack, Level level, LivingEntity consumer) {
        if (Helper.isVampire(consumer)) {
            consumer.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 440));
        } else if (Helper.canBecomeVampire((Player) consumer)) {
            if (!VampirismConfig.SERVER.disableFangInfection.get()) {
                SanguinareEffect.addRandom(consumer, true);
                consumer.addEffect(new MobEffectInstance(MobEffects.POISON, 60));
            }
        } else {
            consumer.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 20 * 20));
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag isAdvanced) {
        MutableComponent textEmpty = TextUtils.getTranslation("tooltip." + this);
        tooltip.add(textEmpty.withStyle(ChatFormatting.BLUE));

        VDTextUtils.addFactionFoodToolTips(tooltip, VampirismMod.proxy.getClientPlayer(), "VAMPIRE");
    }
}
