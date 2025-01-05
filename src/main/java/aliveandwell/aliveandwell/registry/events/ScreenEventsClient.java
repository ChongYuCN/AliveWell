package aliveandwell.aliveandwell.registry.events;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.minecraft.client.gui.screen.OpenToLanScreen;
import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
public class ScreenEventsClient {
    public static void init(){
        //=================================Client=====================================================================
        ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
            if(screen instanceof OpenToLanScreen){
                Screens.getButtons(screen).get(0).active = false;
            }
        });
        //作弊按钮
        ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
            if(screen instanceof OpenToLanScreen){
                Screens.getButtons(screen).get(1).active = false;
            }
        });

        //生存类型
        ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
            if(screen instanceof CreateWorldScreen){
                Screens.getButtons(screen).get(0).active = false;
            }
        });

        ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
            if(screen instanceof CreateWorldScreen){
                Screens.getButtons(screen).get(1).setMessage(Text.translatable("aliveandwell.screen.info1"));
                Screens.getButtons(screen).get(1).active = false;
            }
        });
        ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
            if(screen instanceof CreateWorldScreen){
                Screens.getButtons(screen).get(2).active = false;
            }
        });
        //数据包
        ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
            if(screen instanceof CreateWorldScreen){
                Screens.getButtons(screen).get(3).active = false;
            }
        });

        //更多世界选项：生成建筑
        ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
            if(screen instanceof CreateWorldScreen){
                Screens.getButtons(screen).get(5).active = false;
            }
        });
        //更多世界选项：世界类型
        ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
            if(screen instanceof CreateWorldScreen){
                Screens.getButtons(screen).get(6).active = false;
            }
        });

//======================================================================================================================
//        //更多世界类型
//        ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
//            if(screen instanceof CreateWorldScreen){
//                Screens.getButtons(screen).get(11).active = false;
//            }
//        });

//游戏规则
//        ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
//            if(screen instanceof CreateWorldScreen){
//                Screens.getButtons(screen).get(4).active = false;
//            }
//        });

//        //创建世界
//        ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
//            if(screen instanceof CreateWorldScreen){
//                Screens.getButtons(screen).get(12).active = false;
//            }
//        });
        //取消
//        ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
//            if(screen instanceof CreateWorldScreen){
//                Screens.getButtons(screen).get(13).active = false;
//            }
//        });
    }
}
