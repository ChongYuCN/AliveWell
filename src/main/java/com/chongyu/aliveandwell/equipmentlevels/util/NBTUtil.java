package com.chongyu.aliveandwell.equipmentlevels.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

import java.util.List;

public class NBTUtil {
    public static NbtCompound loadStackNBT(ItemStack stack) {
        return stack.getOrCreateNbt();
    }

    public static void saveStackNBT(ItemStack stack, NbtCompound nbt) {
        if (nbt != null) {
            stack.setNbt(nbt);
        }
    }

}