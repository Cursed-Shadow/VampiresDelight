package net.grid.vampiresdelight.common.food;

import net.grid.vampiresdelight.common.utility.VDEntityUtils;
import net.grid.vampiresdelight.common.utility.VDHelper;
import net.grid.vampiresdelight.common.utility.VDIntegrationUtils;
import net.minecraft.world.entity.LivingEntity;

public enum AffectCategory {
    /**
     * "EVERYONE" - all consumers are affected.
     * "HUMAN" - affects any creature that can eat human food. It means that werewolves with "Not Meat" skill will be able to eat it as well.
     * "VAMPIRE" - only affects vampires.
     * "HUNTER" - only affects hunters.
     * "WEREWOLF" - only affects werewolves.
     */
    EVERYONE, HUMAN, VAMPIRE, HUNTER, WEREWOLF;

    public static boolean canAffectConsumer(AffectCategory category, LivingEntity consumer) {
        return switch (category) {
            case EVERYONE -> true;
            case HUMAN -> VDEntityUtils.canConsumeHumanFood(consumer);
            case VAMPIRE -> VDHelper.isVampire(consumer);
            case HUNTER -> VDHelper.isHunter(consumer);
            case WEREWOLF -> VDIntegrationUtils.isWerewolf(consumer);
        };
    }
}
