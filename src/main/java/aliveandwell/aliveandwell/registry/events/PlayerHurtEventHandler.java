package aliveandwell.aliveandwell.registry.events;

import aliveandwell.aliveandwell.accessor.IEntityNbt;
import aliveandwell.aliveandwell.equipmentlevels.handle.callback.EntityEvents;
import aliveandwell.aliveandwell.equipmentlevels.util.EAUtil;
import aliveandwell.aliveandwell.equipmentlevels.util.NBTUtil;
import aliveandwell.aliveandwell.registry.ItemInit;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.SilverfishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.rmi.registry.Registry;
import java.util.Random;

public class PlayerHurtEventHandler {

    public static void onHurt() {
        EntityEvents.ON_LIVING_DAMAGE_CALC.register((world, entity, damageSource, damageAmount) -> {
            Entity target = damageSource.getAttacker();
           if (entity instanceof PlayerEntity player) {//PLAYER IS GETTING HURT
               if(target != null) {
                   String mob = target.getName().toString();

                   //armor
                   ItemStack stack = player.getInventory().armor.get(world.random.nextInt(4));
                   ItemStack stack1 = player.getInventory().getMainHandStack();

                   //效果：腐蚀装备武器，腐蚀一般物品，腐蚀食物，缓慢，饥饿，中毒，致盲，冰冻，漂浮，虚弱，着火
//                   player.sendMessage(Text.translatable(mob));//test=================================================
//                   if(mob.contains("minecraft.")){//test=================================================
//                       if(mob.contains("slime")){
//
//                           hunger(player);
//                           blind(player);
//                           slow(player);
//                           ice(player);
//                           durability(player,stack);
//                   player.world.playSound(player.getX(),player.getY(),player.getZ(), SoundEvents.ITEM_FIRECHARGE_USE, SoundCategory.PLAYERS,1.0f,1.0f,true);
//                       }
//                   }//test=================================================
                    if(target instanceof SilverfishEntity){
                        if(player.world.getRegistryKey().getValue().toString().contains("twilightforest")){
                            if(new Random().nextInt(20) == 2){
                                hunger(player);
                                blind(player);
                            }
                        }else if(player.world.getRegistryKey().getValue().toString().contains("minecells")){
                            if(new Random().nextInt(20) == 2){
                                hunger(player);
                            }
                        }else {
//                            player.sendMessage(Text.translatable("生物群落名字一："+world.getBiome(entity.getBlockPos()).getKey().get().getValue().toString()));
                            if(new Random().nextInt(100) == 2){
                                hunger(player);
                            }
                        }
                    }


                   if (mob.contains("terrarianslimes.")) {
                       int random1 = player.world.random.nextInt(11);
                       int random2 = player.world.random.nextInt(5);
                       if (mob.contains("green_slime")) {//绿色史莱姆：饥饿
                           if(random2 == 0 ){
                               hunger(player);
                           }
                       }
                       if (mob.contains("blue_slime")) {//蓝色史莱姆：中毒
                           if(random2 == 0 ){
                               potion(player);
                           }
                       }
                       if (mob.contains("red_slime")) {//红史莱姆：缓慢，中毒
                           if(random2 == 0 ){
                               slow(player);
                           }else if(random2 ==2){
                               potion(player);
                           }else {
                               slow(player);
                               potion(player);
                           }
                       }
                       if (mob.contains("purple_slime")) {//紫史莱姆：中毒，
                           if(random2 == 0 ){
                               hunger(player);
                           }
                       }
                       if (mob.contains("yellow_slime")) {//黄史莱姆:yellow_slime饥饿，漂浮
                           if(random2 == 0 ){
                               hunger(player);
                           }else if(random2 ==2){
                               fly(player);
                           }else {
                               hunger(player);
                               fly(player);
                           }
                       }
                       if (mob.contains("black_slime")) { //黑史莱姆:black_slime腐蚀物品装备
                           if(random1 == 0 ){
                               durability(player,stack);//武器装备
                           }else if(random1 == 5){
                               //快捷栏和物品栏
                               damageItem(player,player.getInventory().main.get(player.world.random.nextInt(36)));//一般物品
                           }else if(random1 == 10){
                               damageFood(player,player.getInventory().main.get(player.world.random.nextInt(36)));//食物
                           }else if(random1 == 6){
                               durability(player,stack1);
                           }
                       }
                       if (mob.contains("ice_slime")) {//冰雪史莱姆:ice_slime
                           if(random2 == 0 ) ice(player);
                       }
                       if (mob.contains("sand_slime")) {//沙史莱姆:sand_slime
                           if(random2 == 0 )  hunger(player);
                           if(random2 == 5 )  slow(player);
                       }
                       if (mob.contains("green_slime")) {//丛林史莱姆:jungle_slime
                           if(random2 == 0 )  fly(player);
                       }
                       if (mob.contains("spiked_ice_slime")) {//尖刺冰雪史莱姆:spiked_ice_slime
                           if(random2 == 0 )  fly(player);
                       }
                       if (mob.contains("spiked_jungle_slime")) {//尖刺丛林史莱姆:spiked_jungle_slime
                           if(random2 == 0 )  fly(player);
                       }
                       if (mob.contains("mother_slime")) { //史莱姆之母:mother_slime
                           hunger(player);
                           if(random1 == 0 ){
                               durability(player,stack);//武器装备
                           }else if(random1 == 5){
                               damageItem(player,player.getInventory().main.get(player.world.random.nextInt(36)));//一般物品
                           }else if(random1 == 10){
                               damageFood(player,player.getInventory().main.get(player.world.random.nextInt(36)));//食物
                           }else if(random1 == 6){
                               durability(player,stack1);
                           }
                       }
                       if (mob.contains("lava_slime")) { //熔岩史莱姆:lava_slime
                           if(random2 == 0 ) fire(player);
                       }
                       if (mob.contains("pinky")) { //粉史莱姆:pinky
                           hunger(player);
                       }
                       if (mob.contains("king_slime")) {//史莱姆王:king_slime
                           hunger(player);
                           blind(player);
                           if(random1 == 0 ){
                               durability(player,stack);//武器装备
                           }else if(random1 == 5){
                               damageItem(player,player.getInventory().main.get(player.world.random.nextInt(36)));//一般物品
                           }else if(random1 == 10){
                               damageFood(player,player.getInventory().main.get(player.world.random.nextInt(36)));//食物
                           }else if(random1 == 6){
                               durability(player,stack1);
                           }
                       }
                       if (mob.contains("spiked_slime")) {//尖刺史莱姆:spiked_slime
                           if(random2 == 0 )  fly(player);
                       }
                       if (mob.contains("umbrella_slime")) {//雨伞史莱姆:umbrella_slime
                           if(random2 == 0 )   hunger(player);
                       }
                       if (mob.contains("corrupt_slime")) {//腐化史莱姆:corrupt_slime
                           if(random1 == 0 ){
                               durability(player,stack);//武器装备
                           }else if(random1 == 5){
                               damageItem(player,player.getInventory().main.get(player.world.random.nextInt(36)));//一般物品
                           }else if(random1 == 10){
                               damageFood(player,player.getInventory().main.get(player.world.random.nextInt(36)));//食物
                           }else if(random1 == 6){
                               durability(player,stack1);
                           }
                       }
                       if (mob.contains("crimslime")) {//猩红史莱姆:crimslime
                           if(random1 == 0 ){
                               slow(player);
                           }else if(random1 == 5){
                               potion(player);
                           }else if(random1 == 10){
                               slow(player);
                           }
                       }
                       if (mob.contains("illuminant_slime")) {//夜明史莱姆:illuminant_slime
                           if(random2 == 0 )  blind(player);
                       }
                       if (mob.contains("rainbow_slime")) { //彩虹史莱姆:rainbow_slime
                           weakness(player);
                       }
                   }
               }
           }else{
               if(target instanceof ServerPlayerEntity player){
                   if(player.getStackInHand(player.getActiveHand()).getItem() == ItemInit.ANCIENT_SWORD){
                       LightningEntity lightningEntity;
                       BlockPos blockPos = entity.getBlockPos();
                       if ((lightningEntity = EntityType.LIGHTNING_BOLT.create(entity.getWorld())) != null) {
                           lightningEntity.refreshPositionAfterTeleport(Vec3d.ofBottomCenter(blockPos));
                           lightningEntity.setChanneler(player);
                           ((IEntityNbt)lightningEntity).entityNbt$putBlooean("player_lighting_aliveandwell",true);
                           ((IEntityNbt)lightningEntity).entityNbt$setPlayerLighting(player);
                           entity.getWorld().spawnEntity(lightningEntity);
                       }
                       entity.getWorld().playSound(player,entity.getBlockPos(),SoundEvents.ITEM_TRIDENT_THUNDER, SoundCategory.PLAYERS, 0.1f, 0.1f);
                   }
               }
           }
            return damageAmount;
        });
    }

    //效果
    //装备武器腐蚀耐久
    public static void durability(PlayerEntity player, ItemStack stack){
        if(stack.getItem() instanceof ToolItem || stack.getItem() instanceof ArmorItem){
            stack.damage(2,player.world.random,(ServerPlayerEntity) player);
        }
    }
    //腐蚀物品数量
    public static void damageItem(PlayerEntity player,ItemStack stack){
        if(!stack.getItem().isFood() && !(stack.getItem() instanceof ToolItem) || !(stack.getItem() instanceof ArmorItem)){
            stack.increment(-(player.world.random.nextInt(2)));
            player.world.playSound(player.getX(),player.getY(),player.getZ(), SoundEvents.BLOCK_LAVA_EXTINGUISH, SoundCategory.PLAYERS,1.0f,1.0f,true);
        }
    }
    //腐蚀食物数量
    public static void damageFood(PlayerEntity player,ItemStack stack){
        if(stack.getItem().isFood()){//腐蚀食物
            stack.increment(-(player.world.random.nextInt(3)));
            player.world.playSound(player.getX(),player.getY(),player.getZ(), SoundEvents.BLOCK_LAVA_EXTINGUISH, SoundCategory.PLAYERS,1.0f,1.0f,true);
        }
    }
    //行动缓慢效果
    public static void slow(PlayerEntity player){
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 20*2, 1));
    }
    //饥饿
    public static void hunger(PlayerEntity player){
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 20*3));
    }
    //中毒
    public static void potion(PlayerEntity player){
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 20*3));
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 20*3));
    }
    //致盲
    public static void blind(PlayerEntity player){
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 20*3));
    }
    //冰冻
    public static void ice(PlayerEntity player){
        player.setFrozenTicks(Math.min(player.getMinFreezeDamageTicks(), 20*20 + 1));
    }
    //漂浮
    public static void fly(PlayerEntity player){
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 20*5));
    }
    //虚弱
    public static void weakness(PlayerEntity player){
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 20*5));
    }
    //着火
    public static void fire(PlayerEntity player){
        player.setOnFireFor(4);
    }
}
