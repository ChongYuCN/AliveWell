package com.chongyu.aliveandwell.registry.events;

import com.chongyu.aliveandwell.AliveAndWellMain;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.*;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
public class UseItem {
    public static void init(){

        UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            Item item = player.getStackInHand(hand).getItem();
            if(item == Items.NAME_TAG){
                if(entity instanceof ZombieEntity || entity instanceof AbstractSkeletonEntity || entity instanceof SpiderEntity || entity instanceof CreeperEntity || entity instanceof SilverfishEntity || entity instanceof EndermanEntity || entity instanceof WitchEntity) {
                    if (AliveAndWellMain.day <= 32) {
                        player.sendMessage(Text.translatable("aliveandwell.soul_star"));
                        return TypedActionResult.fail(ItemStack.EMPTY).getResult();
                    }
                }
            }
            return ActionResult.PASS;
        });

        UseItemCallback.EVENT.register((player, world, hand) -> {
            ItemStack itemStack = player.getMainHandStack();
            Item item = itemStack.getItem();
            if(!world.isClient) {
                //test名字
//                if(player.isSneaking()){
//                    player.sendMessage(Text.translatable("物品名字："+Registry.ITEM.getId(item).toString()));
//                    player.sendMessage(Text.translatable("物品堆得物品名字："+Registry.ITEM.getId(itemStack.getItem()).toString()));
//                    player.sendMessage(Text.translatable("物品堆名字："+itemStack.getName().toString()));
//                }

                if (Registries.ITEM.getId(item).toString().substring(Registries.ITEM.getId(item).toString().indexOf(":")).equals(":torchbow")) {
                    if (player.getWorld().getRegistryKey() == World.OVERWORLD) {
                        if (AliveAndWellMain.day <= 128) {
                            player.sendMessage(Text.translatable("aliveandwell.useitem.info1").formatted(Formatting.YELLOW));
                            return TypedActionResult.fail(ItemStack.EMPTY);
                        }
                    } else {
                        if(!player.hasStatusEffect(StatusEffects.NIGHT_VISION)){
                            player.sendMessage(Text.translatable("aliveandwell.useitem.info2").formatted(Formatting.YELLOW));
                            return TypedActionResult.fail(ItemStack.EMPTY);
                        }
                    }
                }

                if(Registries.ITEM.getId(item).toString().contains("bosses_of_mass_destruction:") && Registries.ITEM.getId(item).toString().contains("soul_star")
                        || Registries.ITEM.getId(item).toString().contains("endrem:") && Registries.ITEM.getId(item).toString().contains("_eye")){
                    if(AliveAndWellMain.day <= 32) {
                        player.sendMessage(Text.translatable("aliveandwell.soul_star"));
                        return TypedActionResult.fail(ItemStack.EMPTY);
                    }
                }

//                if(item == Items.FIREWORK_ROCKET){
//                    if(FabricLoader.getInstance().isModLoaded("levelz")){
//                        ArrayList<Object> levelList = LevelLists.elytraList;
//                        if (!PlayerStatsManager.playerLevelisHighEnough(player, levelList, (String)null, true)) {
//                            player.sendMessage(Text.translatable("你的【敏捷】技能等级未达到【鞘翅】使用等级,不能使用烟花！").formatted(Formatting.LIGHT_PURPLE));
//                            return TypedActionResult.fail(itemStack.copy());
//                        }
//                    }
//                }
//                if(item instanceof AxeItem){
//                    if(FabricLoader.getInstance().isModLoaded("levelz")){
//                        ArrayList<Object> levelList = LevelLists.axeList;
//                        String material = ((MiningToolItem)item).getMaterial().toString().toLowerCase();
//                        if (!PlayerStatsManager.playerLevelisHighEnough(player, levelList, material, true)) {
//                            if(player.isSneaking()){
//                                return TypedActionResult.fail(itemStack.copy());
//                            }else {
//                                return TypedActionResult.fail(itemStack.copy());
//                            }
//                        }
//                    }
//                }

//                if(item == Items.FIREWORK_ROCKET){
//                    ArrayList<Object> levelList = LevelLists.elytraList;
//                    PlayerStatsManager playerStatsManager = ((PlayerStatsManagerAccess)player).getPlayerStatsManager();
//                    int playerLevel = playerStatsManager.getLevel(levelList.get(levelList.indexOf("AgilityLevel") + 1).toString());
//                    if (playerLevel < 16) {
//                        player.sendMessage(Text.translatable("你的【敏捷】技能等级未达到使用等级"));
//                        return TypedActionResult.fail(itemStack);
//                    }
//                }
            }

            return TypedActionResult.pass(ItemStack.EMPTY);
        });
    }
}
