package com.chongyu.aliveandwell.registry.events;

import com.chongyu.aliveandwell.registry.ItemInit;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.TypedActionResult;

public class EatOreAddExperience {
    public static int DIAMOND_XP= 500;
    public static int ITEM_EN_GENSTONE_XP= 200;
//    public static int EMERALD_XP= 100;
    public static int LAPIS_LAZULI_XP= 60;
    public static int QUARTZ_XP= 60;
    public static int REDSTONE_XP= 60;

    public static void init(){
        UseItemCallback.EVENT.register((player, world, hand) -> {
            ItemStack itemStack = player.getMainHandStack();
            Item item = itemStack.getItem();
                //钻石
                if(item == Items.DIAMOND){
                    if(!player.getWorld().isClient){
                        if(player.isSneaking()){
                            player.addExperience(DIAMOND_XP*itemStack.getCount());
                            itemStack.split(itemStack.getCount());
                        }else {
                            player.addExperience(DIAMOND_XP);
                            itemStack.split(1);
                        }
                    }
                    world.playSound(player,player.getX(),player.getY(),player.getZ(), SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.PLAYERS,1.0F,1.0F);
                    return TypedActionResult.success(itemStack);
                }

                //绿宝石
//                if(item == Items.EMERALD){
//                    if(!player.getWorld().isClient){
//                        if(player.isSneaking()){
//                            player.addExperience(EMERALD_XP*itemStack.getCount());
//                            itemStack.split(itemStack.getCount());
//                        }else {
//                            player.addExperience(EMERALD_XP);
//                            itemStack.split(1);
//                        }
//                    }
//                    world.playSound(player,player.getX(),player.getY(),player.getZ(), SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.PLAYERS,1.0F,1.0F);
//                    return TypedActionResult.success(itemStack);
//                }

                //宝石
                if(item == ItemInit.ITEM_EN_GENSTONE){
                    if(!player.getWorld().isClient){
                        if(player.isSneaking()){
                            player.addExperience(ITEM_EN_GENSTONE_XP*itemStack.getCount());
//                            if(FabricLoader.getInstance().isModLoaded("levelz")){
//                                ((PlayerSyncAccess)player).addLevelExperience(ITEM_EN_GENSTONE_LEVEL_XP*itemStack.getCount());
//                            }
                            itemStack.split(itemStack.getCount());
                        }else {
//                            if(FabricLoader.getInstance().isModLoaded("levelz")){
//                                ((PlayerSyncAccess)player).addLevelExperience(ITEM_EN_GENSTONE_LEVEL_XP);
//                            }
                            player.addExperience(ITEM_EN_GENSTONE_XP);
                            itemStack.split(1);
                        }
                    }
                    world.playSound(player,player.getX(),player.getY(),player.getZ(), SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.PLAYERS,1.0F,1.0F);
                    return TypedActionResult.success(itemStack);
                }
                //青金石
                if(item == Items.LAPIS_LAZULI){
                    if(!player.getWorld().isClient){
                        if(player.isSneaking()){
                            player.addExperience(LAPIS_LAZULI_XP*itemStack.getCount());
                            itemStack.split(itemStack.getCount());
                        }else {
                            player.addExperience(LAPIS_LAZULI_XP);
                            itemStack.split(1);
                        }
                    }
                    world.playSound(player,player.getX(),player.getY(),player.getZ(), SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.PLAYERS,1.0F,1.0F);
                    return TypedActionResult.success(itemStack);
                }
                //红石
                if(item == Items.REDSTONE){
                    if(!player.getWorld().isClient){
                        if(player.isSneaking()){
                            player.addExperience(REDSTONE_XP*itemStack.getCount());
                            itemStack.split(itemStack.getCount());
                        }else {
                            player.addExperience(REDSTONE_XP);
                            itemStack.split(1);
                        }
                    }
                    world.playSound(player,player.getX(),player.getY(),player.getZ(), SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.PLAYERS,1.0F,1.0F);
                    return TypedActionResult.success(itemStack);
                }
                //石英
                if(item == Items.QUARTZ){
                    if(!player.getWorld().isClient){
                        if(player.isSneaking()){
                            player.addExperience(QUARTZ_XP*itemStack.getCount());
                            itemStack.split(itemStack.getCount());
                        }else {
                            player.addExperience(QUARTZ_XP);
                            itemStack.split(1);
                        }
                    }
                    world.playSound(player,player.getX(),player.getY(),player.getZ(), SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.PLAYERS,1.0F,1.0F);
                    return TypedActionResult.success(itemStack);
                }

//                if(itemStack.hasNbt() && item == ItemInit.ITEM_EN_GENSTONE
//                        && !itemStack.getNbt().toString().contains(player.getUuid().toString())){
//                    return TypedActionResult.fail(ItemStack.EMPTY);
//                }

            return TypedActionResult.pass(ItemStack.EMPTY);
        });
    }
}
