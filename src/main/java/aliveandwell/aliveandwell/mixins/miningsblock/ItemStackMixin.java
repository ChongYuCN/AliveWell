package aliveandwell.aliveandwell.mixins.miningsblock;

import aliveandwell.aliveandwell.miningsblock.MiningMixinHooks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(ItemStack.class)
public class ItemStackMixin {

//    @Inject(
//            at = @At(
//                    value = "INVOKE",
//                    target = "net/minecraft/item/ItemStack.isDamageable()Z"),
//            method = "damage(ILnet/minecraft/entity/LivingEntity;Ljava/util/function/Consumer;)V",
//            cancellable = true)
//    private <T extends LivingEntity> void veinmining$damage(int amount, T entity,
//                                                            Consumer<T> consumer,
//                                                            CallbackInfo ci) {
//
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
//    private <T extends LivingEntity> int veinmining$changeDamage(int amount) {
//        return MiningMixinHooks.modifyItemDamage(amount);
//    }
}
