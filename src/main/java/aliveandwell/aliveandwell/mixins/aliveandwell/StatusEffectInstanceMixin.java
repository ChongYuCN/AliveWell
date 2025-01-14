package aliveandwell.aliveandwell.mixins.aliveandwell;

import aliveandwell.aliveandwell.mixins.aliveandwell.enity.LivingEntityAccessor;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(StatusEffectInstance.class)
public abstract class StatusEffectInstanceMixin {

    @Shadow
    int duration;

    @Shadow public abstract int getDuration();


    @Inject(at = @At("TAIL"), method = "applyUpdateEffect", cancellable = true)
    public void applyUpdateEffect(LivingEntity entity, CallbackInfo ca) {
        if(this.equals((StatusEffectInstance)((LivingEntityAccessor)(Object)entity).getActiveStatusEffects().get(StatusEffects.POISON))){
            if (this.getDuration() > 400){
                this.duration = 200;
            }
        }

    }
}
