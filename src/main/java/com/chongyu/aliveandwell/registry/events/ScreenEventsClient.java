package com.chongyu.aliveandwell.registry.events;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.client.gui.screen.OpenToLanScreen;
import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import net.minecraft.client.gui.screen.world.ExperimentsScreen;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
public class ScreenEventsClient {
    public static void init() {
        //=================================Client=====================================================================
        //局域网作弊：生存模式
        ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
            if(screen instanceof OpenToLanScreen){
                Screens.getButtons(screen).get(0).active = false;
            }
        });
        //局域网作弊：作弊按钮
        ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
            if(screen instanceof OpenToLanScreen){
                Screens.getButtons(screen).get(1).active = false;
            }
        });
    }
}
