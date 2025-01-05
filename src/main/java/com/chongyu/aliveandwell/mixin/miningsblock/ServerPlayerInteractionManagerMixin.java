package com.chongyu.aliveandwell.mixin.miningsblock;

import com.chongyu.aliveandwell.miningsblock.MiningMixinHooks;
import net.minecraft.block.BlockState;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerInteractionManager.class)
public class ServerPlayerInteractionManagerMixin {

    @Final
    @Shadow
    protected ServerPlayerEntity player;

    @Unique
    private BlockState source;


    @Inject(
            at = @At(value = "HEAD"),
            method = "tryBreakBlock(Lnet/minecraft/util/math/BlockPos;)Z")
    private void veinmining$preHarvest(BlockPos pos, CallbackInfoReturnable<Boolean> ci) {
        source = player.getServerWorld().getBlockState(pos);
    }

    //Ö´ÐÐÆÆ»µ·½¿é
    @Inject(
            at = @At(
                    value = "INVOKE",
                    target = "net/minecraft/item/ItemStack.postMine(Lnet/minecraft/world/World;Lnet/minecraft/block/BlockState;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/entity/player/PlayerEntity;)V"),
            method = "tryBreakBlock(Lnet/minecraft/util/math/BlockPos;)Z")
    private void veinmining$tryHarvest(BlockPos pos, CallbackInfoReturnable<Boolean> ci) {
        MiningMixinHooks.tryHarvest(player, pos, source);
    }
}
