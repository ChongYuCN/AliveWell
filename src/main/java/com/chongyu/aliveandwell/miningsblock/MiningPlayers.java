package com.chongyu.aliveandwell.miningsblock;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

public class MiningPlayers {
    public static int timeDead = 2400;
    private static final long DIFF = 20;
    public static final float toolDamageMultiplier = 1.1f;
    public static final float playerExhaustionMultiplier = 1.1f;
    private static final Map<UUID, Long> ACTIVATED_MINERS = new ConcurrentHashMap<>();//玩家-冷却
    private static final Set<UUID> CURRENT_MINERS = new ConcurrentSkipListSet<>();//当前玩家
    private static final Map<World, Map<BlockPos, BlockPos>> MINING_BLOCKS = new ConcurrentHashMap<>();//世界-方块

    //验证：每tick可挖掘一次
    public static void validate(long worldTime) {
        Iterator<Map.Entry<UUID, Long>> entries = ACTIVATED_MINERS.entrySet().iterator();

        while (entries.hasNext()) {
            Map.Entry<UUID, Long> entry = entries.next();
            long lastTime = entry.getValue();

            if (worldTime - lastTime > DIFF || lastTime > worldTime) {
                entries.remove();
            }
        }
    }

    //是否可以开始连锁
    public static boolean canStartMining(PlayerEntity player) {
        return ACTIVATED_MINERS.containsKey(player.getUuid());
    }

    //激活连锁
    public static void activateMining(PlayerEntity player, long time) {
        ACTIVATED_MINERS.put(player.getUuid(), time);
    }

    //停止
    public static void deactivateMining(PlayerEntity player) {
        ACTIVATED_MINERS.remove(player.getUuid());
    }

    public static boolean isVeinMining(PlayerEntity player) {
        return CURRENT_MINERS.contains(player.getUuid());
    }

    public static void startMining(PlayerEntity player) {
        CURRENT_MINERS.add(player.getUuid());
    }

    public static void stopMining(PlayerEntity player) {
        CURRENT_MINERS.remove(player.getUuid());
    }

    public static void addMiningBlock(World level, BlockPos pos, BlockPos spawnPos) {
        MINING_BLOCKS.computeIfAbsent(level, (k) -> new ConcurrentHashMap<>()).put(pos, spawnPos);
    }

    public static void removeMiningBlock(World level, BlockPos pos) {
        Map<BlockPos, BlockPos> map = MINING_BLOCKS.get(level);

        if (map != null) {
            map.remove(pos);
        }
    }

    public static Optional<BlockPos> getNewSpawnPosForDrop(World level, BlockPos pos) {
        Map<BlockPos, BlockPos> map = MINING_BLOCKS.get(level);

        if (map != null) {
            return Optional.ofNullable(map.get(pos));
        }
        return Optional.empty();
    }
}
