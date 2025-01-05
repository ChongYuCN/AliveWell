package com.chongyu.aliveandwell.mixin.events;

import com.chongyu.aliveandwell.events.EntityEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    //生物tick事件
    @Inject(method = "tick()V", at = @At(value = "HEAD"))
    public void LivingEntity_tick(CallbackInfo ci) {
        Entity entity = (Entity)(Object)this;
        World world = entity.getWorld();
        EntityEvents.LIVING_TICK.invoker().onTick(world, entity);
    }

    //非玩家生物受到伤害事件
    @ModifyVariable(method = "applyDamage", at = @At(value= "INVOKE_ASSIGN", target = "Ljava/lang/Math;max(FF)F", ordinal = 0), ordinal = 0, argsOnly = true)
    private float LivingEntity_actuallyHurt(float f, DamageSource damageSource, float damage) {
        LivingEntity livingEntity = (LivingEntity)(Object)this;
        World world = livingEntity.getWorld();

        float newDamage = EntityEvents.ON_LIVING_DAMAGE_CALC.invoker().onLivingDamageCalc(world, livingEntity, damageSource, f);
        if (newDamage != -1 && newDamage != f) {
            return newDamage;
        }

        return f;
    }

    //生物攻击事件
    @Inject(method = "damage", at = @At(value = "HEAD"), cancellable = true)
    public void LivingEntity_hurt(DamageSource damageSource, float f, CallbackInfoReturnable<Boolean> ci) {
        LivingEntity livingEntity = (LivingEntity)(Object)this;
        World world = livingEntity.getWorld();

        if (!EntityEvents.ON_LIVING_ATTACK.invoker().onLivingAttack(world, livingEntity, damageSource, f)) {
            ci.setReturnValue(false);
        }
    }

    @Inject(method = "onDeath", at = @At(value = "HEAD"))
    public void LivingEntity_die(DamageSource damageSource, CallbackInfo ci) {
        Entity entity = (Entity)(Object)this;
        World world = entity.getWorld();

        EntityEvents.LIVING_ENTITY_DEATH.invoker().onDeath(world, entity, damageSource);
    }
}
