package com.chongyu.aliveandwell.util;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;

public class PlayerEquipUtil {

    //是否穿戴艾德曼盔甲
    public static int getWearingDoomArmorCount(PlayerEntity player) {
        ItemStack head = player.getEquippedStack(EquipmentSlot.HEAD);
        ItemStack chest = player.getEquippedStack(EquipmentSlot.CHEST);
        ItemStack legs = player.getEquippedStack(EquipmentSlot.LEGS);
        ItemStack feet = player.getEquippedStack(EquipmentSlot.FEET);
        String headName = Registries.ITEM.getId(head.getItem()).toString();
        String chestName = Registries.ITEM.getId(chest.getItem()).toString();
        String legsName = Registries.ITEM.getId(legs.getItem()).toString();
        String feetName = Registries.ITEM.getId(feet.getItem()).toString();

        int count1 = 0;
        int count2 = 0;
        int count3 = 0;
        int count4 = 0;
        if(	headName.contains("doom") ){
            count1=1;
        }
        if(	chestName.contains("doom") ){
            count2=1;
        }
        if(	legsName.contains("doom") ){
            count3=1;
        }
        if(	feetName.contains("doom") ){
            count4=1;
        }

        return count1 + count2 + count3 + count4;
    }

    public static int getWearingAamanArmorCount(PlayerEntity player) {
        ItemStack head = player.getEquippedStack(EquipmentSlot.HEAD);
        ItemStack chest = player.getEquippedStack(EquipmentSlot.CHEST);
        ItemStack legs = player.getEquippedStack(EquipmentSlot.LEGS);
        ItemStack feet = player.getEquippedStack(EquipmentSlot.FEET);
        String headName = Registries.ITEM.getId(head.getItem()).toString();
        String chestName = Registries.ITEM.getId(chest.getItem()).toString();
        String legsName = Registries.ITEM.getId(legs.getItem()).toString();
        String feetName = Registries.ITEM.getId(feet.getItem()).toString();

        int count1 = 0;
        int count2 = 0;
        int count3 = 0;
        int count4 = 0;
        if(	headName.contains("adamantium_helmet") ){
            count1=1;
        }
        if(	chestName.contains("adamantium_chestplate") ){
            count2=1;
        }
        if(	legsName.contains("adamantium_leggings") ){
            count3=1;
        }
        if(	feetName.contains("adamantium_boots") ){
            count4=1;
        }

        return count1 + count2 + count3 + count4;
    }

}
