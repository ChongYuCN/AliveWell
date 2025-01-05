package com.chongyu.aliveandwell.xpgui.network;

import com.chongyu.aliveandwell.xpgui.common.PlayerStatsManagerAccess;
import com.chongyu.aliveandwell.xpgui.common.XPStates;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.c2s.play.CustomPayloadC2SPacket;

import java.util.Objects;

public class PlayerStatsClientPacket {
    public static void init() {
        ClientPlayNetworking.registerGlobalReceiver(PlayerStatsServerPacket.XP_PACKET, (client, handler, buf, sender) -> {
            if (client.player != null) {
                executeXPPacket(client.player, buf);
            } else {
                PacketByteBuf newBuffer = new PacketByteBuf(Unpooled.buffer());
                newBuffer.writeInt(buf.readInt());
                client.execute(() -> {
                    executeXPPacket(client.player, newBuffer);
                });
            }
        });

//        ClientPlayNetworking.registerGlobalReceiver(PlayerStatsServerPacket.MAX_HEALTH_PACKET, (client, handler, buf, sender) -> {
//            if (client.player != null) {
//                executeMaxHealthPacket(client.player, buf);
//            }
//            else {
//                PacketByteBuf newBuffer = new PacketByteBuf(Unpooled.buffer());
//                newBuffer.writeFloat(buf.readFloat());
//                client.execute(() -> {
//                    executeMaxHealthPacket(client.player, newBuffer);
//                });
//            }
//        });
    }

    private static void executeXPPacket(PlayerEntity player, PacketByteBuf buf) {
        XPStates playerStatsManager = ((PlayerStatsManagerAccess) player).getPlayerStatsManager();
        playerStatsManager.setXpBox(buf.readInt());
    }

    public static void writeC2SIncreaseLevelPacket(XPStates playerStatsManager, int experience) {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        buf.writeInt(experience);
        buf.writeInt(playerStatsManager.getXp());

        CustomPayloadC2SPacket packet = new CustomPayloadC2SPacket(PlayerStatsServerPacket.XP_PACKET, buf);
        MinecraftClient.getInstance().getNetworkHandler().sendPacket(packet);
    }

//    private static void executeMaxHealthPacket(PlayerEntity player, PacketByteBuf buf) {
//        Objects.requireNonNull(player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH))
//                .setBaseValue(buf.readFloat());
//    }

}
