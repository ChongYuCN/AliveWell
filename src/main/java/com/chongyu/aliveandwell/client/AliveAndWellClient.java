package com.chongyu.aliveandwell.client;

import com.chongyu.aliveandwell.AliveAndWellMain;
import com.chongyu.aliveandwell.crafttime.Constants;
import com.chongyu.aliveandwell.item.CompassAncientCity;
import com.chongyu.aliveandwell.miningsblock.network.MiningNetwork;
import com.chongyu.aliveandwell.registry.ItemInit;
import com.chongyu.aliveandwell.registry.events.ScreenEventsClient;
import com.chongyu.aliveandwell.util.ModsChect;
import com.chongyu.aliveandwell.util.config.CommonConfig;
import com.chongyu.aliveandwell.xpgui.network.PlayerStatsClientPacket;
import com.chongyu.aliveandwell.xpgui.network.PlayerStatsServerPacket;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientLoginConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.item.CompassAnglePredicateProvider;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.network.packet.s2c.login.LoginDisconnectS2CPacket;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.Objects;

@Environment(EnvType.CLIENT)
public class AliveAndWellClient implements ClientModInitializer {
    public boolean a = CommonConfig.b;
    @Override
    public void onInitializeClient() {
        ScreenEventsClient.init();

//        ClientLoginConnectionEvents.INIT.register((handler, client) -> {
//            boolean b = new ModsChect().chectMods();
//            if(a){
//                if(!b ){
//                    handler.onDisconnect(new LoginDisconnectS2CPacket(Text.translatable("aliveandwell.modschect")));
//                }
//            }
//        });

        ClientLifecycleEvents.CLIENT_STARTED.register(client -> {
            boolean b = new ModsChect().chectMods();
            if(a){
                if(!b ){
                    client.close();
                }
            }
        });

        //xpgui
        PlayerStatsClientPacket.init();

        //crafttime
        ClientPlayNetworking.registerGlobalReceiver(Constants.DIFFICULTY_TABLE_PACKET_ID, (client, handler, packetBuf, responseSender) -> {
            int item = packetBuf.readVarInt();
            float value = packetBuf.readFloat();
            AliveAndWellMain.map.setDifficulty(item, value);
        });

        //连锁挖矿
        ClientTickEvents.END_CLIENT_TICK.register((client) -> {
            ClientWorld world = client.world;
            ClientPlayerEntity player = client.player;

            if (world != null && player != null && world.getTime() % 5 == 0) {
                boolean enabled = !player.isSneaking();
                MiningNetwork.sendState(enabled);
            }
        });

        //古城指南针
        ModelPredicateProviderRegistry.register(ItemInit.COMPASS_ANCIENT_CITY, new Identifier("angle"), new CompassAnglePredicateProvider((world, stack, entity) -> {
            if (stack.isOf(ItemInit.COMPASS_ANCIENT_CITY)) {
                CompassAncientCity item = (CompassAncientCity) stack.getItem();
                return item.getStructurePos(world, stack);
            }
            return null;
        }));
    }
}
