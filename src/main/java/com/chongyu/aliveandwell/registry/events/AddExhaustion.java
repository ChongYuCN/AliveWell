package com.chongyu.aliveandwell.registry.events;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.util.ActionResult;

public class AddExhaustion {

    public static void init(){

        //破坏方块
        PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, entity) -> {
            if(!player.getWorld().isClient){
                player.addExhaustion(0.002f);
            }
        });


        //放置方块
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            player.addExhaustion(0.002f);
            return ActionResult.PASS;
        });
    }
}
