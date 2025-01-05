package com.chongyu.aliveandwell.mixin.miningsblock;

import com.chongyu.aliveandwell.miningsblock.MiningMixinHooks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(ItemStack.class)
public class ItemStackMixin {

//    @Shadow private @Nullable Entity holder;

//    @Inject(
//            at = @At(
//            value = "INVOKE",
//            target = "Lnet/minecraft/item/ItemStack;isDamageable()Z"),
//    method = "damage(ILnet/minecraft/entity/LivingEntity;Ljava/util/function/Consumer;)V",
//    cancellable = true)
//    private <T extends LivingEntity> void veinmining$hurtAndBreak(int amount, T entity, Consumer<T> consumer, CallbackInfo ci) {
//        if (MiningMixinHooks.shouldCancelItemDamage(entity)) {
//            ci.cancel();
//        }
//    }

//    @SuppressWarnings("ConstantConditions")
//    @ModifyArg(
//            at = @At(
//                    value = "INVOKE",
//                    target = "Lnet/minecraft/item/ItemStack;damage(ILnet/minecraft/util/math/random/Random;Lnet/minecraft/server/network/ServerPlayerEntity;)Z"),
//            method = "damage(ILnet/minecraft/entity/LivingEntity;Ljava/util/function/Consumer;)V")
//    private <T extends LivingEntity> int veinmining$changeBreak(int amount) {
//        if(this.holder instanceof ServerPlayerEntity player){
//            return MiningMixinHooks.modifyItemDamage(amount, player);
//        }
//        return amount;
//    }
}
