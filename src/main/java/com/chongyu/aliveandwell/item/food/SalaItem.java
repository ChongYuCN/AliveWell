package com.chongyu.aliveandwell.item.food;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;

public class SalaItem extends Item {
    public SalaItem(Settings settings) {
        super(settings);
    }

    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        ItemStack itemStack = super.finishUsing(stack, world, user);
        if(user instanceof PlayerEntity && ((PlayerEntity)user).getAbilities().creativeMode){
            return itemStack;
        }else {
            if(user instanceof PlayerEntity){
                PlayerEntity player = (PlayerEntity)user;
                PlayerInventory inventory = player.getInventory();
                ItemStack itemStack1 = new ItemStack(Items.BOWL,1);

                //物品是碗，且可以插入合并
                if (inventory.insertStack(itemStack1)){
                    inventory.insertStack(itemStack1);
                    return itemStack;
                }else {
                    player.dropStack(itemStack1);
                    return itemStack;
                }
            }
        }
        return itemStack;
    }
}
