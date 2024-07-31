package net.grid.vampiresdelight.common.block;

import de.teamlapen.vampirism.api.EnumStrength;
import net.grid.vampiresdelight.common.utility.VDEntityUtils;
import net.grid.vampiresdelight.common.utility.VDHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import vectorwing.farmersdelight.common.block.WildCropBlock;

public class WildGarlicBlock extends WildCropBlock {
    public WildGarlicBlock(Holder<MobEffect> suspiciousStewEffect, int effectDuration, Properties properties) {
        super(suspiciousStewEffect, effectDuration, properties);
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (entity instanceof LivingEntity livingEntity && VDHelper.isVampire(entity)) {
            VDEntityUtils.affectVampireEntityWithGarlic(livingEntity, EnumStrength.WEAK);
        }

        super.entityInside(state, level, pos, entity);
    }
}
