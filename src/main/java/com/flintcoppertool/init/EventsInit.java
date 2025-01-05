package com.chongyu.aliveandwell.flintcoppertool.init;


import com.chongyu.aliveandwell.AliveAndWellMain;
import com.chongyu.aliveandwell.block.randompos.RandomManager;
import com.chongyu.aliveandwell.flintcoppertool.utils.DisableWoodStoneTools;
import com.chongyu.aliveandwell.flintcoppertool.utils.FlintKnapEvent;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.world.World;

import java.util.Objects;

public class EventsInit {
    public static void init() {
        DisableWoodStoneTools.noStoneWoodTier();
        FlintKnapEvent.knapEvent();
    }
}
