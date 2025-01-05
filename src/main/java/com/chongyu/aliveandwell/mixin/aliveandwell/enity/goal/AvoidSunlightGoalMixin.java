package com.chongyu.aliveandwell.mixin.aliveandwell.enity.goal;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ai.NavigationConditions;
import net.minecraft.entity.ai.goal.AvoidSunlightGoal;
import net.minecraft.entity.mob.PathAwareEntity;
import org.spongepowered.asm.mixin.*;

@Mixin(AvoidSunlightGoal.class)
public class AvoidSunlightGoalMixin {
    @Mutable
    @Final
    @Shadow private final PathAwareEntity mob;

    public AvoidSunlightGoalMixin(PathAwareEntity mob) {
        this.mob = mob;
    }

    @Overwrite
    public boolean canStart() {
        return false;
    }
}
