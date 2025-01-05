package com.chongyu.aliveandwell.mixin.aliveandwell.armor;

import com.chongyu.aliveandwell.util.PlayerEquipUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(Entity.class)
public abstract class EntityMixinFireFreeze {
    @Shadow public abstract DamageSources getDamageSources();

    @Inject(method = "isFireImmune", at = @At("HEAD"), cancellable = true)
    public void armorIsFireImmune(CallbackInfoReturnable<Boolean> cir) {
        Entity entity = (Entity) (Object) this;
        if(entity instanceof PlayerEntity player){
            if(PlayerEquipUtil.getWearingDoomArmorCount(player) == 4) {
                cir.setReturnValue(true);
            }
        }
    }
}
