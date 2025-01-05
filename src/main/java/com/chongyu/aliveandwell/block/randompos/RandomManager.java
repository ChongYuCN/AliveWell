package com.chongyu.aliveandwell.block.randompos;

import com.chongyu.aliveandwell.AliveAndWellMain;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.World;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class RandomManager extends PersistentState {
    public ConcurrentHashMap<RandomPos, BlockPos> randomPosMap = new ConcurrentHashMap<>();
    public  static boolean canSpawnVillager;
    public  static boolean canSpawnStructure;

    public RandomManager(){
        super();
    }
    public RandomManager(NbtCompound nbt) {
//        randomPosMap.clear();
        NbtList list = nbt.getList("aliveandewell_randompos", NbtCompound.COMPOUND_TYPE);
        NbtList list2 = nbt.getList("aliveandewell_canSpawnVillager", NbtCompound.COMPOUND_TYPE);
        NbtList list3 = nbt.getList("aliveandewell_canSpawnStructure", NbtCompound.COMPOUND_TYPE);
        for (NbtElement t : list) {
            NbtCompound randomNbt = (NbtCompound) t;
            RandomPos randomPos = new RandomPos(randomNbt.getString("world"),new ChunkPos(randomNbt.getInt("x"),randomNbt.getInt("z")),randomNbt.getString("topA"),randomNbt.getString("topB"),randomNbt.getString("bomA"),randomNbt.getString("bomB"));
            BlockPos pos = new BlockPos(randomNbt.getInt("posX"), randomNbt.getInt("posY") , randomNbt.getInt("posZ"));
            randomPosMap.put(randomPos, pos);
        }

        for (NbtElement t : list2) {
            NbtCompound randomNbt = (NbtCompound) t;
            canSpawnVillager =randomNbt.getBoolean("canSpawnVillager");
        }

        for (NbtElement t : list3) {
            NbtCompound randomNbt = (NbtCompound) t;
            canSpawnStructure =randomNbt.getBoolean("canSpawnStructure");
        }
    }

    public static RandomManager getServerState(MinecraftServer server) {
        if (Objects.requireNonNull(server.getWorld(World.OVERWORLD)).isClient) {
            throw new RuntimeException("Don't access this client-side!");
        }

        // (注：如需在任意维度生效，请使用 'World.OVERWORLD' ，不要使用 'World.END' 或 'World.NETHER')
        PersistentStateManager persistentStateManager = Objects.requireNonNull(server.getWorld(World.OVERWORLD)).getPersistentStateManager();

        // 当第一次调用了方法 'getOrCreate' 后，它会创建新的 'RandomManager' 并将其存储于  'PersistentStateManager' 中。
        //  'getOrCreate' 的后续调用将本地的 'RandomManager' NBT 传递给 'StateSaverAndLoader::createFromNbt'。

        RandomManager  state = persistentStateManager.getOrCreate(RandomManager::new, RandomManager::new, AliveAndWellMain.MOD_ID);

        // 若状态未标记为脏(dirty)，当 Minecraft 关闭时， 'writeNbt' 不会被调用，相应地，没有数据会被保存。
        // 从技术上讲，只有在事实上发生数据变更时才应当将状态标记为脏(dirty)。
        // 但大多数开发者和模组作者会对他们的数据未能保存而感到困惑，所以不妨直接使用 'markDirty' 。
        // 另外，这只将对应的布尔值设定为 TRUE，代价是文件写入磁盘时模组的状态不会有任何改变。(这种情况非常少见)
        state.markDirty();

        return state;
    }

    public  BlockPos getDestination(RandomPos randomPos,MinecraftServer server) {
        RandomManager serverState = getServerState(server);

        if (serverState.randomPosMap.containsKey(randomPos))
            return serverState.randomPosMap.get(randomPos);
        return null;
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        NbtList list = new NbtList();
        randomPosMap.forEach((randomPos,pos) -> {
            if(randomPos != null && pos != null){
                NbtCompound randomNbt = new NbtCompound();
                randomNbt.putString("world",randomPos.getWorld());
                randomNbt.putInt("x", randomPos.getChunkPos().x);
                randomNbt.putInt("z", randomPos.getChunkPos().z);
                randomNbt.putString("topA",randomPos.getTopA());
                randomNbt.putString("topB",randomPos.getTopB());
                randomNbt.putString("bomA",randomPos.getBomA());
                randomNbt.putString("bomB",randomPos.getBomB());
                randomNbt.putInt("posX", pos.getX());
                randomNbt.putInt("posY", pos.getY());
                randomNbt.putInt("posZ", pos.getZ());
                list.add(randomNbt);
            }
        });

        NbtCompound listCanSpawnVillager = new NbtCompound();
        listCanSpawnVillager.putBoolean("canSpawnVillager",canSpawnVillager);

        NbtCompound listcanSpawnStructure = new NbtCompound();
        listcanSpawnStructure.putBoolean("canSpawnStructure",canSpawnStructure);

        nbt.put("aliveandewell_randompos",list);
        nbt.put("aliveandewell_canSpawnVillager",listCanSpawnVillager);
        nbt.put("aliveandewell_canSpawnStructure",listcanSpawnStructure);

        return nbt;
    }

    @Override
    public boolean isDirty() {
        return true;
    }
}
