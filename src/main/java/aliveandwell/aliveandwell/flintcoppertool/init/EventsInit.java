package aliveandwell.aliveandwell.flintcoppertool.init;

import aliveandwell.aliveandwell.flintcoppertool.utils.DisableWoodStoneTools;
import aliveandwell.aliveandwell.flintcoppertool.utils.FlintKnapEvent;

public class EventsInit {
    public static void init() {
        DisableWoodStoneTools.noStoneWoodTier();
        FlintKnapEvent.knapEvent();
    }
}
