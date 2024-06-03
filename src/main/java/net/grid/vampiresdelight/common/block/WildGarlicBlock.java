package net.grid.vampiresdelight.common.block;

import de.teamlapen.vampirism.api.EnumStrength;
import de.teamlapen.vampirism.api.VReference;
import de.teamlapen.vampirism.api.entity.vampire.IVampire;
import de.teamlapen.vampirism.util.DamageHandler;
import net.grid.vampiresdelight.common.utility.VDHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import vectorwing.farmersdelight.common.block.WildCropBlock;

public class WildGarlicBlock extends WildCropBlock {
    public WildGarlicBlock(MobEffect suspiciousStewEffect, int effectDuration, Properties properties) {
        super(suspiciousStewEffect, effectDuration, properties);
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (VDHelper.isVampire(entity)) {
            if (entity instanceof Player) {
                VReference.VAMPIRE_FACTION.getPlayerCapability((Player) entity).ifPresent(vamp -> DamageHandler.affectVampireGarlicDirect(vamp, EnumStrength.WEAK));
            } else if (entity instanceof IVampire) {
                DamageHandler.affectVampireGarlicDirect((IVampire) entity, EnumStrength.WEAK);
            }
        }

        super.entityInside(state, level, pos, entity);
    }
}
