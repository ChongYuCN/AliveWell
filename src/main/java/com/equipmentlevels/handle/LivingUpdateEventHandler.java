package com.chongyu.aliveandwell.equipmentlevels.handle;

import com.chongyu.aliveandwell.equipmentlevels.core.Experience;
import com.chongyu.aliveandwell.equipmentlevels.util.EAUtil;
import com.chongyu.aliveandwell.equipmentlevels.util.NBTUtil;
import com.chongyu.aliveandwell.events.EntityEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.collection.DefaultedList;

public class LivingUpdateEventHandler {
    private static int count = 0;


    public static void onUpdate() {
        EntityEvents.LIVING_TICK.register((world, entity) ->{
            if (entity instanceof PlayerEntity player) {
                DefaultedList<ItemStack> main = player.getInventory().main;

                if (!player.getWorld().isClient) {
//                    for (ItemStack stack : player.getInventory().armor) {
//                        if (stack != null && EAUtil.canEnhanceArmor(stack.getItem())) {
//                            NbtCompound nbt = NBTUtil.loadStackNBT(stack);
//                            float heal = Ability.REMEDIAL.getLevel(nbt);
//                            if (Ability.REMEDIAL.hasAbility(nbt))
//                                if (count < 120) {
//                                    count++;
//                                } else {
//                                    count = 0;
////                                    player.heal(heal);
//                                }
//                        }
//                    }
                    for (ItemStack stack : main) {
                        if (stack != ItemStack.EMPTY) {
                            Item item = stack.getItem();

                            if (EAUtil.canEnhance(item)) {

                                NbtCompound nbt = NBTUtil.loadStackNBT(stack);
                                if (!Experience.isEnabled(nbt)) {
                                    boolean okay = true;

                                    if (okay) {
                                        Experience.enable(nbt, true);
//                                        Rarity rarity = Rarity.getRarity(nbt);
//                                        Random rand = player.world.random;

//                                        if (rarity == Rarity.DEFAULT) {
//                                            rarity = Rarity.getRandomRarity(rand);
//                                            rarity.setRarity(nbt);
//                                            NBTUtil.saveStackNBT(stack, nbt);
//                                        }
                                    }
                                }

                            }
                        }
                    }
                }
            }
        });

    }
}