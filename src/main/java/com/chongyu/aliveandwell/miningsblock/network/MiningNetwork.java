package com.chongyu.aliveandwell.miningsblock.network;

import com.chongyu.aliveandwell.AliveAndWellMain;
import com.chongyu.aliveandwell.miningsblock.MiningPlayers;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

//更新客户端是否按键激活连锁
public class MiningNetwork {
    public static final Identifier SEND_STATE = new Identifier(AliveAndWellMain.MOD_ID, "state");

    public static void sendState(boolean state) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeBoolean(state);
        ClientPlayNetworking.send(SEND_STATE, buf);
    }

    public static void handleState(MinecraftServer minecraftServer,
                                   ServerPlayerEntity serverPlayerEntity,
                                   ServerPlayNetworkHandler serverPlayNetworkHandler,
                                   PacketByteBuf packetByteBuf, PacketSender packetSender) {
        boolean flag = packetByteBuf.readBoolean();
        minecraftServer.execute(() -> {
            if (flag) {
                MiningPlayers.activateMining(serverPlayerEntity, serverPlayerEntity.getServerWorld().getTime());
            } else {
                MiningPlayers.deactivateMining(serverPlayerEntity);
            }
        });
    }
}
