package aliveandwell.aliveandwell;

import aliveandwell.aliveandwell.data.SaveDatas;
import aliveandwell.aliveandwell.dimensions.DimsRegistry;
import aliveandwell.aliveandwell.dimensions.PortalInit;
import aliveandwell.aliveandwell.equipmentlevels.handle.ItemTooltipEventHandler;
import aliveandwell.aliveandwell.equipmentlevels.handle.LivingDeathEventHandler;
import aliveandwell.aliveandwell.equipmentlevels.handle.LivingHurtEventHandler;
import aliveandwell.aliveandwell.equipmentlevels.handle.LivingUpdateEventHandler;
import aliveandwell.aliveandwell.equipmentlevels.network.NetWorkHandler;
import aliveandwell.aliveandwell.flintcoppertool.init.BlocksInit;
import aliveandwell.aliveandwell.flintcoppertool.init.EventsInit;
import aliveandwell.aliveandwell.flintcoppertool.utils.ModPlacedFeatures;
import aliveandwell.aliveandwell.hometpaback.Back;
import aliveandwell.aliveandwell.hometpaback.Homes;
import aliveandwell.aliveandwell.hometpaback.Tpa;
import aliveandwell.aliveandwell.miningsblock.BlockGroups;
import aliveandwell.aliveandwell.miningsblock.MiningEnchantment;
import aliveandwell.aliveandwell.miningsblock.MiningPlayers;
import aliveandwell.aliveandwell.miningsblock.logic.BlockProcessor;
import aliveandwell.aliveandwell.miningsblock.network.MiningNetwork;
import aliveandwell.aliveandwell.registry.*;
import aliveandwell.aliveandwell.registry.events.*;
import aliveandwell.aliveandwell.util.ConfigLock;
import aliveandwell.aliveandwell.util.ModsChect;
import aliveandwell.aliveandwell.config.CommonConfig;
import aliveandwell.aliveandwell.config.Config;
import aliveandwell.aliveandwell.world.OrePlacedFeature;
import com.mojang.logging.LogUtils;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.GameRules;
import net.minecraft.world.gen.GenerationStep;

import java.io.IOException;
import java.util.Objects;

public class AliveAndWellMain implements ModInitializer {
//    public static final String VERSION = "v2.5.18";//
    public static final String VERSION = "v2.5.18-modrinth";//

    //===================================================================================================
//    public static boolean canEnd = true;//仅测试使用
    public static boolean canEnd = false;//仅测试使用====打包改false
    //===================================================================================================


//    //*****************************************************************************************************************
    public static boolean canCreative = true;//仅测试使用====打包改false
//    public static boolean canCreative = false;//仅测试使用====打包改false
    // *****************************************************************************************************************


    public static final String MOD_ID = "aliveandwell";
    public static final String SAVE_FILE = "fabricellive";

    public static final Identifier MOD_DIMENSION_ID = new Identifier(AliveAndWellMain.MOD_ID, "underworld_dim");  // 384 -64

    public static final org.slf4j.Logger LOGGER = LogUtils.getLogger();
    public static Config config;

    public static int ca;
    public static int structureUnderDay=32;

    public static double time_increment = 20.0D / 40.0D;//白天20分钟

    public static int day ;
    public static long day_time ;

    //连锁附魔
    public static final Enchantment MINING_BLOCK = new MiningEnchantment();

    @Override
    public void onInitialize() {

        config = new Config();
        config.load();

        DimsRegistry.setupDimension();
        OrePlacedFeature.register();
        ItemInit.init();
        BlockInit.registerBlocks();
        BlockInit.registerBlockItems();
        PortalInit.registerPortal();
        PlayerHurtEventHandler.onHurt();

        //flintcoppertool
        BlocksInit.init();
        EventsInit.init();
        BiomeModifications.addFeature(
                BiomeSelectors.foundInOverworld(),
                GenerationStep.Feature.VEGETAL_DECORATION,
                ModPlacedFeatures.STICK_TWIG.getKey().get());

        FuelRegistry.INSTANCE.add(ItemInit.BONE_STICK,150);

        //events
        PlayerAttackDistance.init();
        EatOreAddExperience.init();
//        EatFood.init();
        UseBlock.init();
        UseItem.init();
        AddExhaustion.init();
        AllowSleep.init();
        PlayerInventoryTick.onPlayerInventoryItemStackTick();

        //homes ,tpa
        new Homes().register();
        new Back().register();
        new Tpa().register();

        //附魔：连锁
        Registry.register(Registry.ENCHANTMENT, new Identifier(MOD_ID, "mining_block"), MINING_BLOCK);

        //equipmentlevels
        ItemTooltipEventHandler.addInformation();
        LivingDeathEventHandler.init();
        LivingHurtEventHandler.onHurt();
        LivingHurtEventHandler.onArrowShoot();
        LivingHurtEventHandler.onArrowHit();
        LivingUpdateEventHandler.onUpdate();
        NetWorkHandler.onRun();

        VanillaTweaks.ApplyChanges();

        //连锁======================================================================
        ServerLifecycleEvents.END_DATA_PACK_RELOAD
                .register((minecraftServer, serverResourceManager, b) -> BlockProcessor.rebuild());
        ServerTickEvents.END_WORLD_TICK
                .register((world) -> MiningPlayers.validate(world.getTime()));
        ServerPlayNetworking
                .registerGlobalReceiver(MiningNetwork.SEND_STATE, MiningNetwork::handleState);
        //连锁======================================================================

        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            //开始时更新天数
            AliveAndWellMain.day = (int)((Objects.requireNonNull(server.getOverworld()).getLevelProperties().getTimeOfDay()) / 24000L)+1;

            BlockGroups.init();//连锁==============================================================

            //服务端模组检测*************************************************
            boolean b = new ModsChect().chectModsServer();
            if(!canCreative){
                if(server.isUsingNativeTransport()){//服务器，不包括本地开局域网
                    if(CommonConfig.b){
                        if(!b ){
                            System.out.println("Server chect mods DONE + ModsChect="+b+",=CommonConfig.canAddFTBMod="+CommonConfig.canAddFTBMod);
                            server.close();
                        }
                    }
                }

                //反作弊：天数
                SaveDatas serverState = SaveDatas.getServerState(Objects.requireNonNull(server));
                if(!serverState.firstServer){
                    serverState.firstServer=true;
                    serverState.gameImp=AliveAndWellMain.day * 100 + 1800;
                }
                if(serverState.firstServer && AliveAndWellMain.day != (serverState.gameImp-1800)/100){
                    server.close();
                }
            }else {
                if(server.isUsingNativeTransport()){//服务器，不包括本地开局域网
                    System.out.println("Server chect mods DONE!"+ b);
                }
            }

            //配置文件保护
            if(!AliveAndWellMain.canCreative){
                try {
                    if(!(new ConfigLock().isDefaultConfig())){
                        System.out.println("Server config chect mods DONE + ConfigLock!");
                        server.close();
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            //关闭游戏规则作弊选项
            ServerCommandSource serverCommandSource = server.getCommandSource();
            if(server.getGameRules().getBoolean(GameRules.KEEP_INVENTORY)//死亡不掉落true1===================================
                    || !server.getGameRules().getBoolean(GameRules.DROWNING_DAMAGE)//溺水伤害false2
                    || !server.getGameRules().getBoolean(GameRules.FALL_DAMAGE)//掉落伤害false3
                    || !server.getGameRules().getBoolean(GameRules.FREEZE_DAMAGE)//冰冻伤害false4
                    || !server.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)//禁用怪物破坏方块false5==================
                    || !server.getGameRules().getBoolean(GameRules.DO_FIRE_TICK)//火焰蔓延false7================================
                    || !server.getGameRules().getBoolean(GameRules.DO_DAYLIGHT_CYCLE)//时间循环false8
                    || !server.getGameRules().getBoolean(GameRules.DO_WEATHER_CYCLE)//天气更替false9
                    || !server.getGameRules().getBoolean(GameRules.DO_INSOMNIA)//生成幻翼false10================================
                    || !server.getGameRules().getBoolean(GameRules.DO_PATROL_SPAWNING)//生成灾厄巡逻队false11
                    || !server.getGameRules().getBoolean(GameRules.DO_WARDEN_SPAWNING)//生成监守者false12
            ){
                GameRules.BooleanRule rule1 = serverCommandSource.getServer().getGameRules().get(GameRules.KEEP_INVENTORY);
                GameRules.BooleanRule rule2 = serverCommandSource.getServer().getGameRules().get(GameRules.DROWNING_DAMAGE);
                GameRules.BooleanRule rule3 = serverCommandSource.getServer().getGameRules().get(GameRules.FALL_DAMAGE);
                GameRules.BooleanRule rule4 = serverCommandSource.getServer().getGameRules().get(GameRules.FREEZE_DAMAGE);
                GameRules.BooleanRule rule5 = serverCommandSource.getServer().getGameRules().get(GameRules.DO_MOB_GRIEFING);
                GameRules.BooleanRule rule7 = serverCommandSource.getServer().getGameRules().get(GameRules.DO_FIRE_TICK);
                GameRules.BooleanRule rule8 = serverCommandSource.getServer().getGameRules().get(GameRules.DO_DAYLIGHT_CYCLE);
                GameRules.BooleanRule rule9 = serverCommandSource.getServer().getGameRules().get(GameRules.DO_WEATHER_CYCLE);
                GameRules.BooleanRule rule10 = serverCommandSource.getServer().getGameRules().get(GameRules.DO_INSOMNIA);
                GameRules.BooleanRule rule11 = serverCommandSource.getServer().getGameRules().get(GameRules.DO_PATROL_SPAWNING);
                GameRules.BooleanRule rule12 = serverCommandSource.getServer().getGameRules().get(GameRules.DO_WARDEN_SPAWNING);
                rule1.set(false,server);
                rule2.set(true,server);
                rule3.set(true,server);
                rule4.set(true,server);
                rule5.set(true,server);
                rule7.set(true,server);
                rule8.set(true,server);
                rule9.set(true,server);
                rule10.set(true,server);
                rule11.set(true,server);
                rule12.set(true,server);
            }
        });
    }

}
