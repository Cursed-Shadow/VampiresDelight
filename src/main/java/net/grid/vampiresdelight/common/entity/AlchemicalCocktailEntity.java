package net.grid.vampiresdelight.common.entity;

import de.teamlapen.vampirism.core.ModBlocks;
import net.grid.vampiresdelight.common.VDConfiguration;
import net.grid.vampiresdelight.common.item.AlchemicalCocktailItem;
import net.grid.vampiresdelight.common.registry.VDEntityTypes;
import net.grid.vampiresdelight.common.registry.VDItems;
import net.grid.vampiresdelight.common.registry.VDSounds;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Random;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class AlchemicalCocktailEntity extends ThrowableItemProjectile  {
    public AlchemicalCocktailEntity(EntityType<? extends AlchemicalCocktailEntity> entityType, Level level) {
        super(entityType, level);
    }

    public AlchemicalCocktailEntity(Level level, LivingEntity entity) {
        super(VDEntityTypes.ALCHEMICAL_COCKTAIL.get(), entity, level);
    }

    public AlchemicalCocktailEntity(Level level, double x, double y, double z) {
        super(VDEntityTypes.ALCHEMICAL_COCKTAIL.get(), x, y, z, level);
    }

    @Override
    protected Item getDefaultItem() {
        return VDItems.ALCHEMICAL_COCKTAIL.get();
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        Entity entity = result.getEntity();
        entity.hurt(this.damageSources().thrown(this, this.getOwner()), 0);
        entity.setSecondsOnFire(16);
        setOnFire(result);
        playLandingSound();
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        if (!level().isClientSide) {
            this.level().broadcastEntityEvent(this, (byte) 3);
            setOnFire(result);
            playLandingSound();
            this.discard();
        }
    }

    private void setOnFire(HitResult result) {
        if (VDConfiguration.ALCHEMICAL_COCKTAIL_BURNS_GROUND.get() && !level().isClientSide) {
            BlockPos blockPos = BlockPos.containing(result.getLocation());
            double radius = VDConfiguration.ALCHEMICAL_COCKTAIL_SPLASH_RADIUS.get();

            for (int dx = (int) -Math.ceil(radius); dx <= radius; dx++) {
                for (int dz = (int) -Math.ceil(radius); dz <= radius; dz++) {
                    double distance = Math.sqrt(dx * dx + dz * dz);
                    if (distance > radius) continue;

                    for (int dy = -2; dy < 2; dy++) {
                        BlockPos pos = blockPos.offset(dx, dy, dz);
                        BlockState blockState = level().getBlockState(pos);
                        BlockState blockStateBelow = level().getBlockState(pos.below());

                        Random random = new Random();
                        double probability = (radius - distance) / radius;

                        //level().setBlockAndUpdate(pos, Blocks.EMERALD_BLOCK.defaultBlockState());
                        if (blockState.canBeReplaced() && isProperBlockBelow(blockStateBelow, pos.below(), level()) && random.nextDouble() < probability) {
                            level().setBlockAndUpdate(pos, ModBlocks.ALCHEMICAL_FIRE.get().defaultBlockState());
                        }
                    }
                }
            }
        }
    }

    private static boolean isProperBlockBelow(BlockState blockStateBelow, BlockPos posBelow, Level level) {
        Block blockBelow = blockStateBelow.getBlock();
        return blockStateBelow.isFaceSturdy(level, posBelow, Direction.UP) || blockStateBelow.is(BlockTags.LEAVES) || blockBelow instanceof StairBlock;
    }

    private void playLandingSound() {
        if (isMetalPipe()) {
            this.playSound(VDSounds.METAL_PIPE.get(), 2.0F, this.random.nextFloat() * 0.1F + 1.0F);
        } else {
            this.playSound(SoundEvents.GLASS_BREAK, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
        }
    }

    private boolean isMetalPipe() {
        return AlchemicalCocktailItem.isMetalPipe(getItem());
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected float getGravity() {
        return 0.1F;
    }
}
