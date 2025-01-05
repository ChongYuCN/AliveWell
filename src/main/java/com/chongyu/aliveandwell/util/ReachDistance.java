package com.chongyu.aliveandwell.util;

import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;

import java.util.Objects;

public class ReachDistance {
    public static void setReachDistance(PlayerEntity player ){
        float reach = -0.5f;
        ItemStack selectedStack  = player.getMainHandStack();
        if(selectedStack != null) {
            Item item = selectedStack.getItem();
            if (item == Items.STICK) reach += 0.25F;
            else if (item == Items.BONE) reach += 0.25f;
            else if (item instanceof TridentItem) reach += 1.25f;
            else if (item instanceof ShearsItem) reach += 0.5f;
            else if (item instanceof ToolItem ) {
                reach += 0.75f;
            }
        }
        if(!player.isOnGround()){
            reach += 0.5f;
        }
        Objects.requireNonNull(player.getAttributeInstance(ReachEntityAttributes.REACH)).setBaseValue(reach);
        Objects.requireNonNull(player.getAttributeInstance(ReachEntityAttributes.ATTACK_RANGE)).setBaseValue(reach);
    }
}
