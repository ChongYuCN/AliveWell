package aliveandwell.aliveandwell.util;

import aliveandwell.aliveandwell.AliveAndWellMain;

public class Moons {
    //int moon_index = (int)(Moons.day_time / 24000L % 8L + 8L) % 8;
    //原版8天一循环
    //原版0：满月，7：右半月，6：中月，5：左虚月，4：暗月，3：右虚月，2：中月，1：左半月
    //原版第25天是满月
    //血月（红色）：
    //白天6点-18点雷暴天气，晚上血月，怪物仇恨距离增加，力量buff，数量增加
    //24，48，72，96（蓝月），120，144，168，192（蓝月），226
    public static boolean isBloodMoon() {
        return !isBlueMoon() && !isHarvestMoon()  &&(AliveAndWellMain.day -1) % 24 == 0 && AliveAndWellMain.day > 24;
    }

    //48(幻）,72，96(幻），120，144(幻），168，192(幻），226
    //幻月：卫道士，幻魔者，掠夺者生成，主动攻击玩家
//    public static boolean isMagicMoon() {
//        return  !isBlueMoon() && !isHarvestMoon() && (AliveAndWellMain.day-1) % 8 == 0 && (AliveAndWellMain.day-1-48) % 48 == 0 && AliveAndWellMain.day > 48;
//    }

    //丰收之月（黄色）：64（丰），72，80，88，96，104，112，120，128（丰），144，168，192（丰），226
    //晚上18点-24点-6点：收获庄稼时有时运二效果
    public static boolean isHarvestMoon() {
        return !isBlueMoon() && (AliveAndWellMain.day-1) % 8 == 0 && (AliveAndWellMain.day-1-64) % 64 == 0 && AliveAndWellMain.day > 64;
    }

    //96（蓝月），120，144，168，192（蓝月），226
    //蓝月（蓝色）：晚上地面不刷怪，第二天白天刷新动物
    public static boolean isBlueMoon() {
        return (AliveAndWellMain.day-1) % 8 == 0 && (AliveAndWellMain.day-1-96) % 96 == 0 && AliveAndWellMain.day > 96;
    }

    public static boolean isNight(){
        long timeDayMoon = AliveAndWellMain.day_time - (AliveAndWellMain.day -1)*24000L;
        //6:0,7:1,8:2,9:3,10:4,11:5,12:6,13:7,14:8,
        // 15:9000L, 16:10000L, 17:11000L, 18:12000L, 19:13000L, 20:14000L, 21:15000L
        //22:16,  23:17, 24:18, 1:19, 2:20, 3:21, 4:22, 5:23, 6: 0
        return timeDayMoon > 12000L && timeDayMoon < 23000L;
    }

}
