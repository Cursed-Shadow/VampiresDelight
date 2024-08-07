package net.grid.vampiresdelight.common.item;

import net.grid.vampiresdelight.common.VDConfiguration;
import net.grid.vampiresdelight.common.entity.AlchemicalCocktailEntity;
import net.grid.vampiresdelight.common.registry.VDItems;
import net.grid.vampiresdelight.common.registry.VDSounds;
import net.grid.vampiresdelight.common.utility.VDTextUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class AlchemicalCocktailItem extends Item implements ProjectileItem {
    public AlchemicalCocktailItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack heldStack = player.getItemInHand(hand);

        level.playSound(null, player.getX(), player.getY(), player.getZ(), VDSounds.ALCHEMICAL_COCKTAIL_THROW.get(), SoundSource.NEUTRAL, 0.5F, 0.4F / (level.random.nextFloat() * 0.4F + 0.8F));
        if (!level.isClientSide) {
            AlchemicalCocktailEntity projectile = new AlchemicalCocktailEntity(level, player);
            projectile.setItem(heldStack);
            projectile.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);
            level.addFreshEntity(projectile);
        }

        player.awardStat(Stats.ITEM_USED.get(this));
        if (!player.getAbilities().instabuild) {
            heldStack.shrink(1);
        }

        return InteractionResultHolder.sidedSuccess(heldStack, level.isClientSide());
    }

    @Override
    public int getMaxStackSize(ItemStack stack) {
        return VDConfiguration.ALCHEMICAL_COCKTAIL_STACK_SIZE.get();
    }

    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        if (isMetalPipe(pStack)) {
            pTarget.playSound(VDSounds.METAL_PIPE.get(), 2.0F, pTarget.getRandom().nextFloat() * 0.1F + 1.0F);
        }

        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }

    public static boolean isMetalPipe(ItemStack stack) {
        return stack.getHoverName().toString().toLowerCase().contains("metal pipe") && stack.getItem() == VDItems.ALCHEMICAL_COCKTAIL.get();
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        if (!VDConfiguration.ALCHEMICAL_COCKTAIL_BURNS_GROUND.get()) {
            MutableComponent textEmpty = VDTextUtils.getTranslation("tooltip.alchemical_cocktail.burn_land_disabled");
            tooltipComponents.add(textEmpty.withStyle(ChatFormatting.GRAY));
        }

        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    @Override
    public Projectile asProjectile(Level level, Position position, ItemStack itemStack, Direction direction) {
        AlchemicalCocktailEntity alchemicalCocktail = new AlchemicalCocktailEntity(level, position.x(), position.y(), position.z());
        alchemicalCocktail.setItem(itemStack);
        return alchemicalCocktail;
    }
}
