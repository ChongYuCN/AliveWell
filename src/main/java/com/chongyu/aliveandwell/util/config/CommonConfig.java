package com.chongyu.aliveandwell.util.config;

import com.chongyu.aliveandwell.AliveAndWellMain;
import com.google.gson.JsonObject;

public class CommonConfig {
    public static boolean areCheatAllowed = false;
    public static boolean b = true;
    public static int deathCount = 50;
    public static boolean c = true;

    public static int tptime = 1800;
    public static int xptime = 10;
    public static int netherDay = 80;

    public CommonConfig(){
    }

    //序列化配置文件
    public JsonObject serialize(){
        JsonObject root = new JsonObject();//父类
        JsonObject entry = new JsonObject();//子条目
        //参数一：条目名称     参数二：值
        entry.addProperty("desc:", "是否开启op作弊模式，默认false，不可配置");
        entry.addProperty("areCheatAllowed", areCheatAllowed);
        entry.addProperty("desc1:", "是否开启模组完整性检查，默认true，不可配置");
        entry.addProperty("areChectMods", b);
        entry.addProperty("desc2:", "是否开启村民交易，默认true,不可配置");
        entry.addProperty("areAllowedTrade", c);
        entry.addProperty("desc3:", "最大死亡次数0-60");
        entry.addProperty("deathCount", deathCount);

        entry.addProperty("desc4:", "传送冷却时间,最小900");
        entry.addProperty("tptime", tptime);
        entry.addProperty("desc5:", "传送每天消耗经验倍数,最小5");
        entry.addProperty("xptime", xptime);
        entry.addProperty("desc6:", "地狱开启天数,默认80,最小64");
        entry.addProperty("netherDay", netherDay);

        root.add("aliveandwell", entry);//创建父类条目名称，并把子条目添加进去

        return root;
    }

    //反序列化
    public void deserialize(JsonObject data) {
        if (data == null) {
            AliveAndWellMain.LOGGER.error("Config file was empty!");
        } else {
            try {
                //dev============================================================================
                areCheatAllowed = data.get("aliveandwell").getAsJsonObject().get("areCheatAllowed").getAsBoolean();
                b  = data.get("aliveandwell").getAsJsonObject().get("areChectMods").getAsBoolean();
                c  = data.get("aliveandwell").getAsJsonObject().get("areAllowedTrade").getAsBoolean();
                //dev============================================================================

                deathCount  = data.get("aliveandwell").getAsJsonObject().get("deathCount").getAsInt();
                tptime  = data.get("aliveandwell").getAsJsonObject().get("tptime").getAsInt();
                xptime  = data.get("aliveandwell").getAsJsonObject().get("xptime").getAsInt();
                netherDay  = data.get("aliveandwell").getAsJsonObject().get("netherDay").getAsInt();

                if(deathCount>=60){
                    deathCount=60;
                }
                if(tptime<= 900){
                    tptime=900;
                }
                if(xptime<= 5){
                    xptime=5;
                }
                if(netherDay<=64){
                    netherDay=64;
                }

            } catch (Exception var3) {
                AliveAndWellMain.LOGGER.error("Could not parse config file", var3);
            }

        }
    }
}
