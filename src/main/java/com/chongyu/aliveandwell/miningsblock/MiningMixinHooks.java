package com.chongyu.aliveandwell.miningsblock;

import com.chongyu.aliveandwell.miningsblock.logic.MiningLogic;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MiningMixinHooks {
    //ִ������
    public static void tryHarvest(ServerPlayerEntity player, BlockPos pos, BlockState source) {
        if (MiningPlayers.canStartMining(player) && !MiningPlayers.isVeinMining(player)) {
            MiningPlayers.startMining(player);
            MiningLogic.startMining(player, pos, source);
            MiningPlayers.stopMining(player);
        }
    }

    //��ȡʵ������λ��
    public static BlockPos getActualSpawnPos(World level, BlockPos pos) {
        return MiningPlayers.getNewSpawnPosForDrop(level, pos).orElse(pos);
    }

    //�Ƿ�ȡ����Ʒ�;�
    public static <T extends LivingEntity> boolean shouldCancelItemDamage(T entity) {
        return entity instanceof PlayerEntity player && !MiningPlayers.isVeinMining(player);
    }

    public static int modifyItemDamage(int damage, ServerPlayerEntity player) {
        int newDamage = damage;

        if (player != null && MiningPlayers.isVeinMining(player)) {
            float multiplier = MiningPlayers.toolDamageMultiplier;

            if (multiplier != 1.0f) {
                newDamage = Math.max(0, (int) ((float) newDamage * multiplier));
            }
        }
        return newDamage;
    }
}
