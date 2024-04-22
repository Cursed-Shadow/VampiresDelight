package net.grid.vampiresdelight.common.event;

import de.teamlapen.vampirism.api.EnumStrength;
import de.teamlapen.vampirism.api.entity.vampire.IVampire;
import de.teamlapen.vampirism.core.ModEffects;
import de.teamlapen.vampirism.core.ModTags;
import de.teamlapen.vampirism.entity.player.vampire.VampirePlayer;
import de.teamlapen.vampirism.items.BloodBottleItem;
import de.teamlapen.vampirism.items.GarlicBreadItem;
import de.teamlapen.vampirism.items.VampirismItemBloodFoodItem;
import de.teamlapen.vampirism.util.DamageHandler;
import de.teamlapen.vampirism.util.Helper;
import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.item.HunterConsumableItem;
import net.grid.vampiresdelight.common.item.VampireConsumableItem;
import net.grid.vampiresdelight.common.registry.VDAdvancements;
import net.grid.vampiresdelight.common.registry.VDEffects;
import net.grid.vampiresdelight.common.registry.VDStats;
import net.grid.vampiresdelight.common.tag.VDTags;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import vectorwing.farmersdelight.common.item.ConsumableItem;

import java.util.Objects;

@Mod.EventBusSubscriber(modid = VampiresDelight.MODID)
public class PlayerEventHandler {
    @SubscribeEvent
    public static void onItemUse(LivingEntityUseItemEvent.@NotNull Finish event) {
        if (Helper.isVampire(event.getEntity())) {
            if (!event.getEntity().getCommandSenderWorld().isClientSide) {
                Item item = event.getItem().getItem();
                if (item instanceof HunterConsumableItem) {
                    if (event.getEntity() instanceof IVampire) {
                        DamageHandler.affectVampireGarlicDirect((IVampire) event.getEntity(), EnumStrength.MEDIUM);
                    } else if (event.getEntity() instanceof Player player) {
                        VampirePlayer.getOpt((Player) event.getEntity()).ifPresent(vampire -> DamageHandler.affectVampireGarlicDirect(vampire, EnumStrength.MEDIUM));
                        player.awardStat(VDStats.disgusting_food_consumed);
                        VDAdvancements.DISGUSTING_FOOD_CONSUMED.trigger((ServerPlayer) player);
                    }
                }
                if (item instanceof GarlicBreadItem && event.getEntity() instanceof Player player) {
                    player.awardStat(VDStats.disgusting_food_consumed);
                    VDAdvancements.DISGUSTING_FOOD_CONSUMED.trigger((ServerPlayer) player);
                }
            }
        } else {
            if (!event.getEntity().getCommandSenderWorld().isClientSide) {
                Item item = event.getItem().getItem();
                if ((item instanceof VampirismItemBloodFoodItem || item instanceof VampireConsumableItem) && event.getEntity() instanceof Player player) {
                    player.awardStat(VDStats.disgusting_food_consumed);
                    VDAdvancements.DISGUSTING_FOOD_CONSUMED.trigger((ServerPlayer) player);
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onFoodEaten(final MobEffectEvent.Applicable event) {
        LivingEntity consumer = event.getEntity();
        InteractionHand hand = consumer.getUsedItemHand();
        ItemStack itemInHand = consumer.getItemInHand(hand);
        if (Helper.isVampire(consumer) && itemInHand.getItem() instanceof ConsumableItem) {
            MobEffect effect = event.getEffectInstance().getEffect();
            if (effect != VDEffects.FOG_VISION.get()) {
                event.setResult(Event.Result.DENY);
            }
        }
    }
}
