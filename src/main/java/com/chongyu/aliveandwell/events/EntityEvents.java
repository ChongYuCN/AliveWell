package com.chongyu.aliveandwell.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

public class EntityEvents {

    private EntityEvents() { }

    public static final Event<Living_Tick> LIVING_TICK = EventFactory.createArrayBacked(Living_Tick.class, callbacks -> (world, entity) -> {
        for (Living_Tick callback : callbacks) {
            callback.onTick(world, entity);
        }
    });

    public static final Event<Living_Entity_Death> LIVING_ENTITY_DEATH = EventFactory.createArrayBacked(Living_Entity_Death.class, callbacks -> (world, entity, source) -> {
        for (Living_Entity_Death callback : callbacks) {
            callback.onDeath(world, entity, source);
        }
    });

    public static final Event<Entity_Living_Damage> ON_LIVING_DAMAGE_CALC = EventFactory.createArrayBacked(Entity_Living_Damage.class, callbacks -> (world, entity, damageSource, damageAmount) -> {
        for (Entity_Living_Damage callback : callbacks) {
            float newDamage = callback.onLivingDamageCalc(world, entity, damageSource, damageAmount);
            if (newDamage != damageAmount) {
                return newDamage;
            }
        }

        return -1;
    });

    public static final Event<Entity_Living_Attack> ON_LIVING_ATTACK = EventFactory.createArrayBacked(Entity_Living_Attack.class, callbacks -> (world, entity, damageSource, damageAmount) -> {
        for (Entity_Living_Attack callback : callbacks) {
            if (!callback.onLivingAttack(world, entity, damageSource, damageAmount)) {
                return false;
            }
        }

        return true;
    });

    public static final Event<Entity_Spawn> ON_ENITY_SPAWN = EventFactory.createArrayBacked(Entity_Spawn.class, callbacks -> (world, entity) -> {
        for (Entity_Spawn callback : callbacks) {
            if (!callback.onEnitySpawn(world, entity)) {
                return false;
            }
        }
        return true;
    });

    public static final Event<Pre_Entity_Join_World> PRE_ENTITY_JOIN_WORLD = EventFactory.createArrayBacked(Pre_Entity_Join_World.class, callbacks -> (world, entity) -> {
        for (Pre_Entity_Join_World callback : callbacks) {
            if (!callback.onPreSpawn(world, entity)) {
                return false;
            }
        }
        return true;
    });

    public static final Event<Entity_Fall_Damage_Calc> ON_FALL_DAMAGE_CALC = EventFactory.createArrayBacked(Entity_Fall_Damage_Calc.class, callbacks -> (world, entity, f, g) -> {
        for (Entity_Fall_Damage_Calc callback : callbacks) {
            if (callback.onFallDamageCalc(world, entity, f, g) == 0) {
                return 0;
            }
        }
        return 1;
    });



    public static final Event<Entity_Is_Dropping_Loot> ON_ENTITY_IS_DROPPING_LOOT = EventFactory.createArrayBacked(Entity_Is_Dropping_Loot.class, callbacks -> (world, entity, damageSource) -> {
        for (Entity_Is_Dropping_Loot callback : callbacks) {
            callback.onDroppingLoot(world, entity, damageSource);
        }
    });

    public static final Event<Entity_Is_Jumping> ON_ENTITY_IS_JUMPING = EventFactory.createArrayBacked(Entity_Is_Jumping.class, callbacks -> (world, entity) -> {
        for (Entity_Is_Jumping callback : callbacks) {
            callback.onLivingJump(world, entity);
        }
    });

    public static final Event<ENTITY_ATTACK_DISTANCE> ON_ENTITY_ATTACK_DISTANCE = EventFactory.createArrayBacked(ENTITY_ATTACK_DISTANCE.class, callbacks -> (world, entity) -> {
        for (ENTITY_ATTACK_DISTANCE callback : callbacks) {
            callback.onEntityAttackDistance(world, entity);
        }
    });

    @FunctionalInterface
    public interface Living_Tick {
        void onTick(World world, Entity entity);
    }

    @FunctionalInterface
    public interface Living_Entity_Death {
        void onDeath(World world, Entity entity, DamageSource source);
    }

    @FunctionalInterface
    public interface Entity_Living_Damage {
        float onLivingDamageCalc(World world, Entity entity, DamageSource damageSource, float damageAmount);
    }

    @FunctionalInterface
    public interface Entity_Living_Attack {
        boolean onLivingAttack(World world, Entity entity, DamageSource damageSource, float damageAmount);
    }

    @FunctionalInterface
    public interface Entity_Spawn {
        boolean onEnitySpawn(ServerWorld world, Entity entity);
    }

    @FunctionalInterface
    public interface Pre_Entity_Join_World {
        boolean onPreSpawn(World world, Entity entity);
    }

    @FunctionalInterface
    public interface Entity_Fall_Damage_Calc {
        int onFallDamageCalc(World world, Entity entity, float f, float g);
    }



    @FunctionalInterface
    public interface Entity_Is_Dropping_Loot {
        void onDroppingLoot(World world, Entity entity, DamageSource damageSource);
    }

    @FunctionalInterface
    public interface Entity_Is_Jumping {
        void onLivingJump(World world, Entity entity);
    }

    @FunctionalInterface
    public interface ENTITY_ATTACK_DISTANCE {
        void onEntityAttackDistance(World world, Entity entity);
    }
}

