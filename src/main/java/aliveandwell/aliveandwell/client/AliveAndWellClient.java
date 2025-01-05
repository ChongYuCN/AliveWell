package aliveandwell.aliveandwell.client;

import aliveandwell.aliveandwell.flintcoppertool.init.BlocksInit;
import aliveandwell.aliveandwell.miningsblock.network.MiningNetwork;
import aliveandwell.aliveandwell.registry.events.ScreenEventsClient;
import aliveandwell.aliveandwell.util.ModsChect;
import aliveandwell.aliveandwell.config.CommonConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.world.ClientWorld;

@Environment(EnvType.CLIENT)
public class AliveAndWellClient implements ClientModInitializer {
    public boolean a = CommonConfig.b;
    @Override
    public void onInitializeClient() {
        //flintcoppertool
        BlockRenderLayerMap.INSTANCE.putBlock(BlocksInit.STICK_TWIG_BLOCK, RenderLayer.getCutout());
        ScreenEventsClient.init();
//        ClientLoginConnectionEvents.INIT.register((handler, client) -> {
//            boolean b = new ModsChect().chectMods();
//            if(a){
//                if(!b ){
//                    handler.getConnection().disconnect(Text.translatable("aliveandwell.modschect"));
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

        //连锁挖矿
        ClientTickEvents.END_CLIENT_TICK.register((client) -> {
            ClientWorld world = client.world;
            ClientPlayerEntity player = client.player;
            if (world != null && player != null && world.getTime() % 5 == 0) {
                boolean enabled = !player.isSneaking();
                MiningNetwork.sendState(enabled);
            }
        });
    }
}
