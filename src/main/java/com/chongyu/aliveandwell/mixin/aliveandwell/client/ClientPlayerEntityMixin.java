package com.chongyu.aliveandwell.mixin.aliveandwell.client;

import com.chongyu.aliveandwell.miningsblock.MiningPlayers;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Timer;
import java.util.TimerTask;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin extends PlayerEntity {
    @Final
    @Shadow
    protected MinecraftClient client;

    @Unique
    private int tick_hunger = 0;

    public ClientPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile gameProfile) {
        super(world, pos, yaw, gameProfile);
    }

    @Inject(at = @At("HEAD"), method = "tick")
    public void tick(CallbackInfo ci) {
        if(this.isDead()){
            MiningPlayers.timeDead--;
        }
    }

    @Inject(at = @At("HEAD"), method = "tickMovement")
    public void tickMovement(CallbackInfo info) {
        tick_hunger = this.hungerManager.getFoodLevel();
        if (tick_hunger > 0) {
            this.hungerManager.setFoodLevel(tick_hunger + 7);
        }

    }

    @Inject(at = @At("TAIL"), method = "tickMovement")
    public void tickMovementReturn(CallbackInfo info) {
        this.hungerManager.setFoodLevel(tick_hunger);
    }
}

