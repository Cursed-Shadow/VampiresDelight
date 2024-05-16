package net.grid.vampiresdelight.common.event;

import de.teamlapen.vampirism.api.EnumStrength;
import de.teamlapen.vampirism.core.ModItems;
import de.teamlapen.vampirism.util.Helper;
import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.item.HunterConsumableItem;
import net.grid.vampiresdelight.common.item.VampireConsumableItem;
import net.grid.vampiresdelight.common.registry.VDAdvancementTriggers;
import net.grid.vampiresdelight.common.registry.VDStats;
import net.grid.vampiresdelight.common.tag.VDTags;
import net.grid.vampiresdelight.common.utility.VDEntityUtils;
import net.minecraft.server.level.ServerPlayer;
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

@Mod.EventBusSubscriber(modid = VampiresDelight.MODID)
public class PlayerEventHandler {
    @SubscribeEvent
    public static void onItemUse(LivingEntityUseItemEvent.@NotNull Finish event) {
        ItemStack itemStack = event.getItem();
        Item item = itemStack.getItem();
        LivingEntity livingEntity = event.getEntity();

        if (!livingEntity.getCommandSenderWorld().isClientSide) {
            if (Helper.isVampire(livingEntity)) {
                if (item instanceof HunterConsumableItem hunterConsumableItem) {
                    if (hunterConsumableItem.doesContainGarlic()) {
                        VDEntityUtils.affectVampireEntityWithGarlic(livingEntity, EnumStrength.MEDIUM);
                        disgustingFoodConsumed(livingEntity);
                    }
                }

                if (item == ModItems.GARLIC_BREAD.get()) {
                    disgustingFoodConsumed(livingEntity);
                }
            } else {
                if (item instanceof VampireConsumableItem && itemStack.is(VDTags.VAMPIRE_FOOD)) {
                    disgustingFoodConsumed(livingEntity);
                }
            }
        }
    }

    private static void disgustingFoodConsumed(LivingEntity livingEntity) {
        if (livingEntity instanceof Player player) {
            player.awardStat(VDStats.disgusting_food_consumed);
            VDAdvancementTriggers.DISGUSTING_FOOD_CONSUMED.trigger((ServerPlayer) player);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onFoodEaten(final MobEffectEvent.Applicable event) {
        LivingEntity consumer = event.getEntity();
        ItemStack itemInHand = consumer.getItemInHand(consumer.getUsedItemHand());
        Item item = itemInHand.getItem();
        if (Helper.isVampire(consumer) && item instanceof ConsumableItem) {
            event.setResult(Event.Result.DENY);
        }
    }
}
