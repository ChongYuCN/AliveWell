package com.chongyu.aliveandwell.xpgui.network;

import com.chongyu.aliveandwell.xpgui.common.PlayerSyncAccess;
import com.chongyu.aliveandwell.xpgui.common.XPStates;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.s2c.play.CustomPayloadS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.util.Objects;

public class PlayerStatsServerPacket {
    public static final Identifier XP_PACKET = new Identifier("aliveandwell", "player_xp_box");
//    public static final Identifier MAX_HEALTH_PACKET = new Identifier("aliveandwell", "player_max_health");

    public static void init() {
        ServerPlayNetworking.registerGlobalReceiver(XP_PACKET, (server, player, handler, buffer, sender) -> {
            if (player == null)
                return;
            ((PlayerSyncAccess) player).addXPBox(buffer.readInt());
            ((PlayerSyncAccess) player).setXpBox(buffer.readInt());
        });

//        ServerPlayNetworking.registerGlobalReceiver(MAX_HEALTH_PACKET, (server, player, handler, buffer, sender) -> {
//            if (player == null)
//                return;
//
//            Objects.requireNonNull(player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH))
//                    .setBaseValue(player.getMaxHealth());
//        });

    }

    //同步box
    public static void writeS2CXPPacket(XPStates playerStatsManager, ServerPlayerEntity serverPlayerEntity) {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        buf.writeInt(playerStatsManager.getXp());
        CustomPayloadS2CPacket packet = new CustomPayloadS2CPacket(XP_PACKET, buf);
        serverPlayerEntity.networkHandler.sendPacket(packet);
    }

    public static void writeS2CXPPacket2(XPStates playerStatsManager, int experience) {
        playerStatsManager.addXPBox(experience);
        playerStatsManager.setXpBox(playerStatsManager.getXp());
    }

//    public static void writeS2CMaxHealthPacket(ServerPlayerEntity serverPlayerEntity) {
//        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
//        buf.writeFloat(serverPlayerEntity.getMaxHealth());
//        CustomPayloadS2CPacket packet = new CustomPayloadS2CPacket(MAX_HEALTH_PACKET, buf);
//        serverPlayerEntity.networkHandler.sendPacket(packet);
//    }
}
