package aliveandwell.aliveandwell.mixins.equipmentlevels;

import aliveandwell.aliveandwell.equipmentlevels.handle.callback.EntityEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

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

}
