package net.grid.vampiresdelight.common.item;

import net.grid.vampiresdelight.client.extension.PourableItemExtension;
import net.grid.vampiresdelight.client.particle.DrinkSplashOptions;
import net.grid.vampiresdelight.common.registry.VDAdvancementTriggers;
import net.grid.vampiresdelight.common.registry.VDSounds;
import net.grid.vampiresdelight.common.utility.VDEntityUtils;
import net.grid.vampiresdelight.common.utility.VDTextUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.common.util.FakePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.List;
import java.util.function.Consumer;

/**
 * Credits to the Create mod for mechanic
 * <a href="https://github.com/Creators-of-Create/Create">...</a>
 */
public class PourableBottleItem extends Item implements ICustomUseItem {
    private final Item serving;
    private final Item servingContainer;

    public PourableBottleItem(Properties properties, int servings, Item serving, Item servingContainer) {
        super(properties.defaultDurability(servings).setNoRepair());
        this.serving = serving;
        this.servingContainer = servingContainer;
    }

    @Override
    public boolean isEnchantable(@NotNull ItemStack stack) {
        return false;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return false;
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return false;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand mainHand) {
        InteractionHand offHand = mainHand == InteractionHand.MAIN_HAND ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
        ItemStack itemStack = player.getItemInHand(mainHand);

        if (itemStack.getOrCreateTag().contains("Pouring")) {
            player.startUsingItem(mainHand);
            return new InteractionResultHolder<>(InteractionResult.PASS, itemStack);
        }

        ItemStack bottle = player.getItemInHand(offHand);
        if (bottle.getItem() == servingContainer) {
            ItemStack itemUsed = bottle.copy();
            ItemStack toPour = itemUsed.split(1);
            player.startUsingItem(mainHand);
            itemStack.getOrCreateTag().put("Pouring", toPour.serializeNBT());
            player.setItemInHand(offHand, itemUsed);
            return new InteractionResultHolder<>(InteractionResult.SUCCESS, itemStack);
        }

        HitResult hitResult = getPlayerPOVHitResult(level, player, ClipContext.Fluid.NONE);
        //if (!(hitResult instanceof BlockHitResult)) return new InteractionResultHolder<>(InteractionResult.FAIL, itemStack);

        Vec3 POVHit = hitResult.getLocation();
        AABB aabb = new AABB(POVHit, POVHit).inflate(1f);
        ItemEntity pickUp = null;
        for (ItemEntity itemEntity : level.getEntitiesOfClass(ItemEntity.class, aabb)) {
            if (!itemEntity.isAlive() && itemEntity.position().distanceTo(player.position()) > 3 && !itemEntity.isAlive() &&
                    itemEntity.getItem().getItem() != servingContainer)
                continue;
            pickUp = itemEntity;
            break;
        }
        if (pickUp != null) {

            ItemStack pickedItem = pickUp.getItem().copy();
            ItemStack toPour = pickedItem.split(1);
            player.startUsingItem(mainHand);

            if (!level.isClientSide) {
                itemStack.getOrCreateTag().put("Pouring", toPour.serializeNBT());
                if (pickedItem.isEmpty()) pickUp.discard();
                else pickUp.setItem(pickedItem);
            }

            return new InteractionResultHolder<>(InteractionResult.SUCCESS, itemStack);
        }
        return new InteractionResultHolder<>(InteractionResult.FAIL, itemStack);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity entity) {
        if (!(entity instanceof Player player))
            return itemStack;

        CompoundTag compoundTag = itemStack.getOrCreateTag();
        if (compoundTag.contains("Pouring")) {
            if (player instanceof FakePlayer) {
                player.drop(new ItemStack(serving), false, false);
            } else {
                player.getInventory().placeItemBackInInventory(new ItemStack(serving));
            }
            compoundTag.remove("Pouring");
            itemStack.setDamageValue(itemStack.getDamageValue() + 1);
            if (itemStack.getDamageValue() >= itemStack.getMaxDamage()) itemStack = new ItemStack(servingContainer);
            VDAdvancementTriggers.BLOOD_WINE_POURED.trigger((ServerPlayer) player);
            entity.playSound(VDSounds.POURING_FINISH.get(), 1.2F, 1.0F);
        }

        return itemStack;
    }

    @Override
    public void releaseUsing(ItemStack itemStack, Level level, LivingEntity entity, int durationTime) {
        if (!(entity instanceof Player player))
            return;

        CompoundTag compoundTag = itemStack.getOrCreateTag();
        if (compoundTag.contains("Pouring")) {
            player.getInventory().placeItemBackInInventory(ItemStack.of(compoundTag.getCompound("Pouring")));
            entity.playSound(VDSounds.POURING_FINISH.get(), 1.2F, 1.0F);
            compoundTag.remove("Pouring");
        }
    }

    @Override
    public boolean hasCustomUseEffects() {
        return false;
    }

    @Override
    public boolean triggerUseEffects(ItemStack stack, LivingEntity entity, int amount) {
        /*
        CompoundTag tag = stack.getOrCreateTag();
        if (tag.contains("Pouring") && entity.getTicksUsingItem() % 3 == 0) {
            ItemStack toPour = ItemStack.of(tag.getCompound("Pouring"));
            VDEntityUtils.spawnParticlesOnItemEntityHolding(new ItemParticleOption(ParticleTypes.ITEM, toPour), entity);
        }
         */

        /*
        if (entity.getTicksUsingItem() % 3 == 0) {
            VDEntityUtils.spawnParticlesOnItemEntityHolding(ParticleTypes.EFFECT, entity);
        }
         */

        //VDEntityUtils.spawnParticlesOnItemEntityHolding(new DrinkSplashOptions(new Color(112, 18, 27).getRGB()), entity);

        if ((entity.getTicksUsingItem() - 6) % 7 == 0) {
            entity.playSound(VDSounds.POURING_SHORT.get(), 1.2F, entity.getRandom().nextFloat() * 0.2F + 0.9F + ((float) entity.getTicksUsingItem() / 128));
        }

        return false;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 32;
    }

    @Override
    public boolean hasCraftingRemainingItem(ItemStack stack) {
        return true;
    }

    @Override
    public ItemStack getCraftingRemainingItem(ItemStack itemStack) {
        ItemStack item = itemStack.copy();
        item.setDamageValue(item.getDamageValue() + 1);
        if (item.getDamageValue() > 3) return new ItemStack(servingContainer);
        return item;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        int servings = pStack.getMaxDamage() - pStack.getDamageValue();
        MutableComponent tooltip = VDTextUtils.getTranslation("tooltip." + this + (servings == 1 ? ".single_serving" : ".multiple_servings"), servings);
        pTooltipComponents.add(tooltip.withStyle(ChatFormatting.GRAY));
    }

    @Override
    public @NotNull UseAnim getUseAnimation(ItemStack itemStack) {
        return UseAnim.CUSTOM;
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new PourableItemExtension());
    }
}
