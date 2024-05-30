package net.grid.vampiresdelight.common.entity;

import de.teamlapen.vampirism.core.ModBlocks;
import net.grid.vampiresdelight.common.VDConfiguration;
import net.grid.vampiresdelight.common.registry.VDEntityTypes;
import net.grid.vampiresdelight.common.registry.VDItems;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
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
    @OnlyIn(Dist.CLIENT)
    public void handleEntityEvent(byte id) {
        ItemStack entityStack = new ItemStack(this.getDefaultItem());
        if (id == 3) {
            ParticleOptions particleOptions = new ItemParticleOption(ParticleTypes.ITEM, entityStack);

            for (int i = 0; i < 12; ++i) {
                this.level().addParticle(particleOptions, this.getX(), this.getY(), this.getZ(),
                        ((double) this.random.nextFloat() * 2.0D - 1.0D) * 0.1F,
                        ((double) this.random.nextFloat() * 2.0D - 1.0D) * 0.1F + 0.1F,
                        ((double) this.random.nextFloat() * 2.0D - 1.0D) * 0.1F);
            }
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        Entity entity = result.getEntity();
        entity.hurt(this.damageSources().thrown(this, this.getOwner()), 0);
        entity.setSecondsOnFire(16);
        setOnFire(result);

        this.playSound(SoundEvents.GLASS_BREAK, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        if (!level().isClientSide) {
            this.level().broadcastEntityEvent(this, (byte) 3);
            setOnFire(result);
            this.playSound(SoundEvents.GLASS_BREAK, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
            this.discard();
        }
    }

    private void setOnFire(HitResult result) {
        if (VDConfiguration.ALCHEMICAL_COCKTAIL_BURNS_GROUND.get() && !level().isClientSide) {
            BlockPos blockPos = BlockPos.containing(result.getLocation());
            int radius = VDConfiguration.ALCHEMICAL_COCKTAIL_SPLASH_RADIUS.get();

            for (int dx = -radius; dx <= radius; dx++) {
                for (int dz = -radius; dz <= radius; dz++) {
                    double distance = Math.sqrt(dx * dx + dz * dz);
                    if (distance > radius) continue;

                    for (int dy = -2; dy < 2; dy++) {
                        BlockPos pos = blockPos.offset(dx, dy, dz);
                        BlockState blockState = level().getBlockState(pos);
                        BlockState blockStateBelow = level().getBlockState(pos.below());

                        Random random = new Random();
                        double probability = (radius - distance) / radius;

                        if (blockState.canBeReplaced() && blockStateBelow.isFaceSturdy(level(), pos.below(), Direction.UP) && random.nextDouble() < probability) {
                            level().setBlockAndUpdate(pos, ModBlocks.ALCHEMICAL_FIRE.get().defaultBlockState());
                        }
                    }
                }
            }
        }
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected float getGravity() {
        return 0.05F;
    }
}
