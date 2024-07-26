package net.grid.vampiresdelight.common.item;

import net.grid.vampiresdelight.client.extension.PourableBottleItemExtension;
import net.grid.vampiresdelight.common.block.PlacedPourableBottleBlock;
import net.grid.vampiresdelight.common.registry.VDAdvancementTriggers;
import net.grid.vampiresdelight.common.registry.VDSounds;
import net.grid.vampiresdelight.common.utility.VDTextUtils;
import net.grid.vampiresdelight.common.utility.VDTooltipUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.common.util.FakePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

/**
 * Credits to the Create mod for mechanic
 * <a href="https://github.com/Creators-of-Create/Create">...</a>
 */
public class PourableBottleItem extends Item implements ICustomUseItem {
    private final PlacedPourableBottleBlock placedBottleBlock;
    private final Item serving;
    private final Item servingContainer;

    public PourableBottleItem(Properties properties, PlacedPourableBottleBlock placedBottleBlock, Item serving, Item servingContainer, int servings) {
        super(properties.defaultDurability(servings).setNoRepair());
        this.placedBottleBlock = placedBottleBlock;
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
        BlockHitResult hitResult = getPlayerPOVHitResult(level, player, ClipContext.Fluid.NONE);
        InteractionHand offHand = mainHand == InteractionHand.MAIN_HAND ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
        ItemStack pouringBottle = player.getItemInHand(mainHand);
        ItemStack glassBottle = player.getItemInHand(offHand);

        if (hitResult.getType() == HitResult.Type.BLOCK && player.isCrouching()) {
            BlockPos blockPos = hitResult.getBlockPos();
            BlockPos targetPos = player.isCrouching() ? blockPos.relative(hitResult.getDirection()) : blockPos;
            BlockState targetState = level.getBlockState(targetPos);
            BlockState bottleBlockToPlace = placedBottleBlock.getStateForPlacement(new BlockPlaceContext(level, player, mainHand, pouringBottle, hitResult));

            if (targetState.canBeReplaced() && bottleBlockToPlace != null) {
                level.setBlock(targetPos, bottleBlockToPlace, 3);

                bottleBlockToPlace.getBlock().setPlacedBy(level, targetPos, bottleBlockToPlace, player, pouringBottle);
                if (player instanceof ServerPlayer) {
                    CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer)player, targetPos, pouringBottle);
                }
                SoundType soundtype = bottleBlockToPlace.getSoundType(level, targetPos, player);
                level.playSound(player, targetPos, soundtype.getPlaceSound(), SoundSource.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
                level.gameEvent(GameEvent.BLOCK_PLACE, targetPos, GameEvent.Context.of(player, bottleBlockToPlace));

                if (!player.getAbilities().instabuild) {
                    pouringBottle.shrink(1);
                }

                return new InteractionResultHolder<>(InteractionResult.SUCCESS, pouringBottle);
            }
        }

        if (pouringBottle.getOrCreateTag().contains("Pouring")) {
            player.startUsingItem(mainHand);
            return new InteractionResultHolder<>(InteractionResult.PASS, pouringBottle);
        }

        if (glassBottle.getItem() == servingContainer) {
            ItemStack itemUsed = glassBottle.copy();
            ItemStack toPour = itemUsed.split(1);
            player.startUsingItem(mainHand);
            pouringBottle.getOrCreateTag().put("Pouring", toPour.serializeNBT());
            player.setItemInHand(offHand, itemUsed);
            return new InteractionResultHolder<>(InteractionResult.SUCCESS, pouringBottle);
        }

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
                pouringBottle.getOrCreateTag().put("Pouring", toPour.serializeNBT());
                if (pickedItem.isEmpty()) pickUp.discard();
                else pickUp.setItem(pickedItem);
            }

            return new InteractionResultHolder<>(InteractionResult.SUCCESS, pouringBottle);
        }

        return new InteractionResultHolder<>(InteractionResult.FAIL, pouringBottle);
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
            VDAdvancementTriggers.DRINK_POURED.trigger((ServerPlayer) player, itemStack);
            entity.playSound(VDSounds.POURING_FINISH.get(), 1.2F, 1.0F);
            itemStack.setDamageValue(itemStack.getDamageValue() + 1);
            if (itemStack.getDamageValue() >= itemStack.getMaxDamage()) itemStack = new ItemStack(servingContainer);
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
        return true;
    }

    @Override
    public boolean triggerUseEffects(ItemStack stack, LivingEntity entity, int amount) {
        //if (entity.getTicksUsingItem() % 3 == 0) {
            //VDEntityUtils.spawnParticlesOnItemEntityHolding(new DrinkSplashOptions(new Color(112, 18, 27).getRGB()), entity);
        //}

        if ((entity.getTicksUsingItem() - 6) % 7 == 0) {
            entity.playSound(VDSounds.POURING_SHORT.get(), 1.2F, entity.getRandom().nextFloat() * 0.2F + 0.9F + ((float) entity.getTicksUsingItem() / 128));
        }

        return true;
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
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag isAdvanced) {
        int servings = stack.getMaxDamage() - stack.getDamageValue();
        MutableComponent textTooltip = VDTextUtils.getTranslation("tooltip." + this + (servings == 1 ? ".single_serving" : ".multiple_servings"), servings);
        tooltip.add(textTooltip.withStyle(ChatFormatting.GRAY));
        VDTooltipUtils.addShiftTooltip("tooltip.pourable_drink_item", tooltip);
    }

    @Override
    public @NotNull UseAnim getUseAnimation(ItemStack itemStack) {
        return UseAnim.DRINK;
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new PourableBottleItemExtension());
    }
}
