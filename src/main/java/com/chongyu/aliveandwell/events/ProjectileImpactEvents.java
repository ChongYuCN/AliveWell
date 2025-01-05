package com.chongyu.aliveandwell.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.hit.HitResult;

public interface ProjectileImpactEvents {
    Event<ProjectileImpactEvents> EVENT = EventFactory.createArrayBacked(ProjectileImpactEvents.class, callbacks -> (proj, hit) -> {
        for (ProjectileImpactEvents callback : callbacks) {
            if (callback.onImpact(proj, hit)) return true;
        }
        return false;
    });

    boolean onImpact(ProjectileEntity projectile, HitResult hitResult);
}