package net.grid.vampiresdelight.common.mixin.accessor;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(LivingEntity.class)
public interface LivingEntityAccessor {
    @Invoker("spawnItemParticles")
    void vampiresdelight$callSpawnItemParticles(ItemStack stack, int count);
}
