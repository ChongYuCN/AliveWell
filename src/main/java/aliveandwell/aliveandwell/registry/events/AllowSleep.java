package aliveandwell.aliveandwell.registry.events;

import aliveandwell.aliveandwell.AliveAndWellMain;
import aliveandwell.aliveandwell.mixins.aliveandwell.enity.PlayerEntityAccessor;
import aliveandwell.aliveandwell.mixins.aliveandwell.world.WorldAccessor;
import aliveandwell.aliveandwell.accessor.WorldTimeHelper;
import net.fabricmc.fabric.api.entity.event.v1.EntitySleepEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.level.ServerWorldProperties;
import aliveandwell.aliveandwell.mixins.aliveandwell.world.ServerWorldAccessors;

import java.util.List;

public class AllowSleep {
    public static void init(){

        EntitySleepEvents.ALLOW_RESETTING_TIME.register(player -> {
            return false;
        });

        EntitySleepEvents.ALLOW_NEARBY_MONSTERS.register((player, sleepingPos, vanillaResult) -> {
            return ActionResult.SUCCESS;
        });

        //中午6000=12点，日落晚上13000=7点，子夜18000=24点,23000=凌晨3点
        EntitySleepEvents.ALLOW_SLEEP_TIME.register( (player, sleepingPos, vanillaResult) -> {
            if(player.world instanceof ServerWorld){
                ServerWorld serverWorld = (ServerWorld)(player.world);
                Long l = player.world.getTimeOfDay()/24000L;
                int i = l.intValue();

                //床平行位置东面，高度3格水平20格有空气
                int x = sleepingPos.getX()+1;//-32
                int y = sleepingPos.getY();//76
                int z = sleepingPos.getZ()+1;//-6
                int ix;
                int iy;
                int iz;
                Vec3d vec3d01 ;
                Vec3d vec3d02 ;
                Vec3d vec3d03 ;
                Vec3d vec3d04 ;
                Vec3d vec3d05 ;
                Vec3d vec3d21 ;
                Vec3d vec3d22 ;
                Vec3d vec3d23 ;
                Vec3d vec3d24 ;
                Vec3d vec3d25 ;
                boolean safe;
                int count1=0;
                int count2=0;
                int count3=0;
                int count4=0;
                int count5=0;
                //ix =x-20。西面20格外看不见
                for(int k =-20;k<=20;k++){
                    iz =z+k;
                    for (int j =0;j<=3;j++){
                        iy=y+j;
                        vec3d21 = new Vec3d(x-20,iy,iz);
                        //床位置2格高
                        for (int q=0;q<=2;q++){
                            vec3d01 = new Vec3d(x,y+q,z);
                            if(player.world.raycast(new RaycastContext(vec3d01, vec3d21, RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.NONE, player)).getType() == net.minecraft.util.hit.HitResult.Type.MISS){
                                count1 ++;
                            }
                        }
                    }
                }

                //ix =x+20。东面20格外看不见
                for(int k =-20;k<=20;k++){
                    iz =z+k;
                    for (int j =0;j<=3;j++){
                        iy=y+j;
                        vec3d22 = new Vec3d(x+20,iy,iz);
                        //床位置2格高
                        for (int q=0;q<=2;q++){
                            vec3d02 = new Vec3d(x,y+q,z);
                            if(player.world.raycast(new RaycastContext(vec3d02, vec3d22, RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.NONE, player)).getType() == net.minecraft.util.hit.HitResult.Type.MISS){
                                count2 ++;
                            }
                        }
                    }
                }

                //iz =z-20。南面20格外看不见
                for(int k =-20;k<=20;k++){
                    ix =x+k;
                    for (int j =0;j<=3;j++){
                        iy=y+j;
                        vec3d23 = new Vec3d(ix,iy,z-20);
                        //床位置2格高
                        for (int q=0;q<=2;q++){
                            vec3d03 = new Vec3d(x,y+q,z);
                            if(player.world.raycast(new RaycastContext(vec3d03, vec3d23, RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.NONE, player)).getType() == net.minecraft.util.hit.HitResult.Type.MISS){
                                count3 ++;
                            }
                        }
                    }
                }

                //iz =z+20。北面20格外看不见
                for(int k =-20;k<=20;k++){
                    ix =x+k;
                    for (int j =0;j<=3;j++){
                        iy=y+j;
                        vec3d24 = new Vec3d(ix,iy,z+20);
                        //床位置2格高
                        for (int q=0;q<=2;q++){
                            vec3d04 = new Vec3d(x,y+q,z);
                            if(player.world.raycast(new RaycastContext(vec3d04, vec3d24, RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.NONE, player)).getType() == net.minecraft.util.hit.HitResult.Type.MISS){
                                count4 ++;
                            }
                        }
                    }
                }

                //iy =y+20。顶面20格外看不见
                for(int k =-3;k<=3;k++){
                    ix =x+k;
                    for (int j =-3;j<=3;j++){
                        iz=z+j;
                        vec3d25 = new Vec3d(ix,y+20,iz);
                        //床位置2格高
                        for (int q=0;q<=2;q++){
                            vec3d05 = new Vec3d(x,y+q,z);
                            if(player.world.raycast(new RaycastContext(vec3d05, vec3d25, RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.NONE, player)).getType() == net.minecraft.util.hit.HitResult.Type.MISS){
                                count5 ++;
                            }
                        }
                    }
                }

//                player.sendMessage(Text.translatable("一西"+count1));
//                player.sendMessage(Text.translatable("二东"+count2));
//                player.sendMessage(Text.translatable("三南"+count3));
//                player.sendMessage(Text.translatable("四北"+count4));
//                player.sendMessage(Text.translatable("五上"+count5));
//                player.sendMessage(Text.translatable("x"+sleepingPos.getX()));
//                player.sendMessage(Text.translatable("y"+sleepingPos.getY()));
//                player.sendMessage(Text.translatable("z"+sleepingPos.getZ()));
                if(count1>42 || count2>53 || count3>26 || count4>94 || count5>72){
                    player.sendMessage(Text.translatable("aliveandwell.sleepsafe.info1"));
                    return ActionResult.FAIL;
                }

                if(player.getHungerManager().getFoodLevel()==0){
                    return ActionResult.FAIL;
                }

                //6:0,7:1,8:2,9:3,10:4,11:5,12:6,13:7,14:8,
                // 15:9000L, 16:10000L, 17:11000L, 18:12000L, 19:13000L, 20:14000L, 21:15000L
                if((player.world.getLunarTime()-i*24000L < 15000L || (player.world.getLunarTime()-i*24000L > 21000L))) {
                    int time = ((PlayerEntityAccessor)(Object)player).getSleepTimer();
                    if(((PlayerEntityAccessor)(Object)player).getSleepTimer() > 0){
                        ((PlayerEntityAccessor)(Object)player).setSleepTimer(0);
                        if(player.getHungerManager().getFoodLevel()<=0){
                            player.wakeUp();
                            player.sendMessage(Text.translatable("aliveandwell.sleepsafe.info2").formatted(Formatting.RED));
                            return ActionResult.FAIL;
                        }else {
                            return ActionResult.SUCCESS;
                        }
                    }
                }else {
                    if(((ServerWorldAccessors)(Object)(serverWorld)).getSleepManager().canSkipNight(100)   && player.getWorld().getRegistryKey() == World.OVERWORLD){
                        if(((PlayerEntityAccessor)(Object)player).getSleepTimer() >= 100){

                            //time--------------
//                            Long lTime = ((ServerWorldProperties)(((WorldAccessor)(Object)(player.world)).getProperties())).getTimeOfDay();

                            //time+++++++++++++++++
                            WorldTimeHelper timeHelper = (WorldTimeHelper)(((WorldAccessor) (player.getWorld())).getProperties());

                            Long lTime = (long)timeHelper.GetDoubleTime();

                            Long lTime1 = (24000l-(lTime-(AliveAndWellMain.day-1)*24000))/100;
                            for(int itime=0; itime<=5*20;itime++){
                                player.addExhaustion(0.0125f);
                                if(player.getHungerManager().getFoodLevel()<=0){
                                    player.wakeUp();
                                    player.sendMessage(Text.translatable("aliveandwell.sleepsafe.info2").formatted(Formatting.RED));
                                    itime=0;
                                    //time--------------
//                                    ((ServerWorldProperties)(((WorldAccessor)(Object)(player.world)).getProperties())).setTimeOfDay(lTime+lTime1*itime);

                                    //time+++++++++++++++++
                                    timeHelper.SetDoubleTime(lTime+lTime1*itime);

                                    return ActionResult.FAIL;
                                }else{
                                    if(itime>=5*20){
                                        //time++++++++++++++++++
                                        timeHelper.SetDoubleTime(AliveAndWellMain.day* 24000L);
                                        long l1 = (long) timeHelper.GetDoubleTime();
                                        ((ServerWorldProperties) serverWorld.getLevelProperties()).setTime(l1);
                                        ((ServerWorldProperties) serverWorld.getLevelProperties()).getScheduledEvents().processEvents(serverWorld.getServer(), l1);
                                        serverWorld.setTimeOfDay((long) timeHelper.GetDoubleTime());
                                        player.heal(player.getMaxHealth());
                                        List<? extends PlayerEntity> players = player.getWorld().getPlayers();
                                        for (PlayerEntity player1 : players){
                                            //主世界播报：天亮了。
                                            player1.sendMessage(Text.translatable("aliveandwell.sleepsafe.info3").formatted(Formatting.YELLOW));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            return ActionResult.PASS;
        });

    }
}
