package com.chongyu.aliveandwell.miningsblock.logic;

import com.chongyu.aliveandwell.AliveAndWellMain;
import com.chongyu.aliveandwell.miningsblock.MiningPlayers;
import com.google.common.collect.Sets;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MiningToolItem;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.GameMode;

import java.util.LinkedList;
import java.util.Set;

public class MiningLogic {
    //连锁方向
    private static final Direction[] CARDINAL_DIRECTIONS =
            new Direction[] {Direction.DOWN, Direction.UP, Direction.EAST, Direction.WEST,
                    Direction.NORTH, Direction.SOUTH};

    //开始连锁
    public static void startMining(ServerPlayerEntity playerEntity, BlockPos pos, BlockState source) {
        ServerWorld world = (ServerWorld) playerEntity.getWorld();
        ItemStack stack = playerEntity.getMainHandStack();

        if (!MiningPlayers.canStartMining(playerEntity)) {
            return;
        }

        //无效
        boolean ineffective = !playerEntity.canHarvest(source);

        if (ineffective) {
            return;
        }
        int miningLevels = EnchantmentHelper.getLevel(AliveAndWellMain.MINING_BLOCK, stack);
        int maxBlocks = 1 + miningLevels;//最大连锁方块数量
        int maxDistance = 14 + miningLevels;//最大连锁距离

        if (maxBlocks <= 0 || maxDistance <= 0) {
            return;
        }
        int blocks = 1;
        Set<BlockPos> visited = Sets.newHashSet(pos);
        LinkedList<Pair<BlockPos, Integer>> candidates = new LinkedList<>();//连锁方块-数量
        addValidNeighbors(candidates, pos, 1);//添加相邻有效的方块
        Block sourceBlock = source.getBlock();

        while (!candidates.isEmpty() && blocks < maxBlocks) {
            Pair<BlockPos, Integer> candidate = candidates.poll();
            BlockPos blockPos = candidate.getLeft();
            int blockDistance = candidate.getRight();

            if (stopVeining(stack)) {
                return;
            }
            BlockState state = world.getBlockState(blockPos);

            if (visited.add(blockPos) &&
                    BlockProcessor.isValidTarget(state, world, blockPos, sourceBlock) &&
                    harvest(playerEntity, blockPos, pos)) {

                if (blockDistance < maxDistance) {
                    addValidNeighbors(candidates, blockPos, blockDistance + 1);
                }
                blocks++;
            }
        }
    }

    private static boolean stopVeining(ItemStack stack) {
        int miningLevels = EnchantmentHelper.getLevel(AliveAndWellMain.MINING_BLOCK, stack);
        if(stack.getItem() instanceof MiningToolItem miningToolItem){
            int i = miningToolItem.getMaterial().getMiningLevel();
            if(i>= 3){
                i = i -(int)(i / 2);
            }
            return stack.isDamageable() && stack.getMaxDamage() - stack.getDamage()<= (miningLevels+1)*(2*i+1);
        }else {
            return stack.isDamageable() && stack.getMaxDamage() - stack.getDamage()<= 10;
        }
    }

    //相邻有效
    private static void addValidNeighbors(LinkedList<Pair<BlockPos, Integer>> candidates, BlockPos source, int distance) {
        BlockPos up = source.up();
        BlockPos down = source.down();
        candidates.add(new Pair<>(up, distance));
        candidates.add(new Pair<>(down, distance));
        BlockPos[] blockPositions = new BlockPos[] {up, down, source};

        for (BlockPos blockPos : blockPositions) {
            candidates.add(new Pair<>(blockPos.west(), distance));
            candidates.add(new Pair<>(blockPos.east(), distance));
            candidates.add(new Pair<>(blockPos.north(), distance));
            candidates.add(new Pair<>(blockPos.south(), distance));
            candidates.add(new Pair<>(blockPos.north().west(), distance));
            candidates.add(new Pair<>(blockPos.north().east(), distance));
            candidates.add(new Pair<>(blockPos.south().west(), distance));
            candidates.add(new Pair<>(blockPos.south().east(), distance));
        }

//        for (Direction direction : CARDINAL_DIRECTIONS) {
//            candidates.add(new Pair<>(source.offset(direction), distance));
//        }
    }

    //是否掉落
    public static boolean harvest(ServerPlayerEntity player, BlockPos pos, BlockPos originPos) {
        ServerWorld world = (ServerWorld) player.getWorld();
        BlockState blockState = world.getBlockState(pos);
        GameMode gameMode = player.interactionManager.getGameMode();

        if (!player.getMainHandStack().getItem().canMine(blockState, world, pos, player)) {
            return false;
        } else {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            Block block = blockState.getBlock();

            if ((block instanceof CommandBlock || block instanceof StructureBlock ||
                    block instanceof JigsawBlock) && !player.isCreativeLevelTwoOp()) {
                world.updateListeners(pos, blockState, blockState, 3);
                return false;
            } else if (player.isBlockBreakingRestricted(world, pos, gameMode)) {
                return false;
            } else {
                block.onBreak(world, pos, blockState, player);
                boolean bl = world.removeBlock(pos, false);

                if (bl) {
                    block.onBroken(world, pos, blockState);
                }

                if (gameMode != GameMode.CREATIVE) {
                    ItemStack itemStack = player.getMainHandStack();
                    ItemStack itemStack2 = itemStack.copy();
                    boolean bl2 = player.canHarvest(blockState);
                    itemStack.postMine(world, blockState, pos, player);

                    if (bl && bl2) {
                        BlockPos spawnPos = originPos;
                        HungerManager hungerManager = player.getHungerManager();
                        float currentExhaustion = hungerManager.getExhaustion();
                        MiningPlayers.addMiningBlock(world, pos, spawnPos);
                        block.afterBreak(world, player, pos, blockState, blockEntity, itemStack2);
                        MiningPlayers.removeMiningBlock(world, pos);

                        float diff = hungerManager.getExhaustion() - currentExhaustion;
                        hungerManager.setExhaustion(currentExhaustion);
                        hungerManager.addExhaustion((float) (diff * MiningPlayers.playerExhaustionMultiplier));
                    }
                }
                return true;
            }
        }
    }
}
