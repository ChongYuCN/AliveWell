package aliveandwell.aliveandwell.equipmentlevels.handle.callback;

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
}

