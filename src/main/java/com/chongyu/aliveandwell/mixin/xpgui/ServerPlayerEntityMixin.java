package com.chongyu.aliveandwell.mixin.xpgui;

import com.chongyu.aliveandwell.miningsblock.MiningPlayers;
import com.chongyu.aliveandwell.util.config.CommonConfig;
import com.chongyu.aliveandwell.xpgui.common.PlayerStatsManagerAccess;
import com.chongyu.aliveandwell.xpgui.common.PlayerSyncAccess;
import com.chongyu.aliveandwell.xpgui.common.XPStates;
import com.chongyu.aliveandwell.xpgui.network.PlayerStatsServerPacket;
import com.mojang.authlib.GameProfile;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.encryption.PlayerPublicKey;
import net.minecraft.network.packet.s2c.play.PlayerListS2CPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Objects;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends PlayerEntity implements PlayerSyncAccess {
    @Unique
    private final XPStates playerStatsManager = ((PlayerStatsManagerAccess) this).getPlayerStatsManager();

    @Unique
    private int syncedLevelExperience = -99999999;
    @Unique
    private boolean syncTeleportStats = false;
    @Unique
    private int tinySyncTicker = 0;

    @Shadow
    @Final
    public MinecraftServer server;

    @Shadow public abstract void sendMessage(Text message);

    public ServerPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile gameProfile) {
        super(world, pos, yaw, gameProfile);
    }


    @Inject(method = "playerTick", at = @At(value = "FIELD", target = "Lnet/minecraft/server/network/ServerPlayerEntity;totalExperience:I", ordinal = 0, shift = At.Shift.BEFORE))
    private void playerTickMixin(CallbackInfo info) {
        if (playerStatsManager.getXp() != this.syncedLevelExperience) {
            this.syncedLevelExperience = playerStatsManager.getXp();
            PlayerStatsServerPacket.writeS2CXPPacket(playerStatsManager, ((ServerPlayerEntity) (Object) this));
            if (this.syncTeleportStats) {
                PlayerStatsServerPacket.writeS2CXPPacket(playerStatsManager, (ServerPlayerEntity) (Object) this);
                this.syncTeleportStats = false;
            }
        }

        if (this.tinySyncTicker > 0) {
            this.tinySyncTicker--;
            if (this.tinySyncTicker % 20 == 0) {
                syncStats(false);
            }
        }
    }

    @Inject(method = "onSpawn", at = @At(value = "TAIL"))
    private void onSpawnMixin(CallbackInfo info) {
        PlayerStatsServerPacket.writeS2CXPPacket(playerStatsManager, (ServerPlayerEntity) (Object) this);
    }

    @Inject(method = "copyFrom", at = @At(value = "FIELD", target = "Lnet/minecraft/server/network/ServerPlayerEntity;syncedExperience:I", ordinal = 0))
    private void copyFromMixin(ServerPlayerEntity oldPlayer, boolean alive, CallbackInfo info) {
        syncStats(false);
    }

    @Override
    public void syncStats(boolean syncDelay) {
        this.syncTeleportStats = true;
        this.syncedLevelExperience = -1;
        if (syncDelay)
            this.tinySyncTicker = 40;
    }

    @Override
    public void addXPBox(int add) {
        ServerPlayerEntity playerEntity = (ServerPlayerEntity) (Object) this;
        playerEntity.addExperience(-add);
        playerStatsManager.xp += add;
        PlayerStatsServerPacket.writeS2CXPPacket(playerStatsManager, playerEntity);
        playerEntity.server.getPlayerManager().sendToAll(new PlayerListS2CPacket(PlayerListS2CPacket.Action.UPDATE_GAME_MODE, playerEntity));
    }

    @Override
    public void setXpBox(int add) {
        playerStatsManager.xp = add;
    }

    @Override
    public boolean canPlus(int plus) {
        if(plus > 0 ){
            return this.totalExperience >= plus && playerStatsManager.xp<= playerStatsManager.GetMaxXp()-plus;
        }else if(plus<0){
            return  playerStatsManager.xp >= -plus;
        }
        return false;
    }
}
