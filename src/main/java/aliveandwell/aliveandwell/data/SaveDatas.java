package aliveandwell.aliveandwell.data;

import aliveandwell.aliveandwell.AliveAndWellMain;
import aliveandwell.aliveandwell.block.randompos.RandomPos;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class SaveDatas extends PersistentState {
    public ConcurrentHashMap<RandomPos, BlockPos> randomPosMap = new ConcurrentHashMap<>();
    public boolean canSpawnVillager;
    public boolean canSpawnStructure;
    public boolean firstServer;//第一次启动服务器
    public int gameImp;//gameday:服务器结束时赋值：反作弊
    public  ConcurrentHashMap<UUID, Integer> randomPlayerMap = new ConcurrentHashMap<>();//反作弊：玩家死亡次数

    public  ConcurrentHashMap<UUID, Long> randomPlayerTimeMap = new ConcurrentHashMap<>();//反作弊：玩家死亡次数

    public SaveDatas(){
        super();
    }
    public static SaveDatas fromNbt(NbtCompound nbt) {
        SaveDatas saveDatas = new SaveDatas();

        NbtList listRandompos = nbt.getList("aliveandewell_randompos", NbtCompound.COMPOUND_TYPE);
        NbtList listRandomPlayerMap = nbt.getList("aliveandwell_randomPlayerMap", NbtCompound.COMPOUND_TYPE);
        NbtList listRandomPlayerTimeMap = nbt.getList("aliveandwell_randomPlayerTimeMap", NbtCompound.COMPOUND_TYPE);

        for (NbtElement t : listRandompos) {
            NbtCompound randomNbt = (NbtCompound) t;
            RandomPos randomPos = new RandomPos(randomNbt.getString("world"),new ChunkPos(randomNbt.getInt("x"),randomNbt.getInt("z")),randomNbt.getString("topA"),randomNbt.getString("topB"),randomNbt.getString("bomA"),randomNbt.getString("bomB"));
            BlockPos pos = new BlockPos(randomNbt.getInt("posX"), randomNbt.getInt("posY") , randomNbt.getInt("posZ"));
            saveDatas.randomPosMap.put(randomPos, pos);
        }

        for (NbtElement t : listRandomPlayerMap) {
            NbtCompound nbtList = (NbtCompound) t;
            UUID uuid = nbtList.getUuid("playerUUID");
            int num= nbtList.getInt("playerNumber");
            saveDatas.randomPlayerMap.put(uuid, num);
        }

        for (NbtElement t : listRandomPlayerTimeMap) {
            NbtCompound nbtList = (NbtCompound) t;
            UUID uuid = nbtList.getUuid("playerUUID");
            long num= nbtList.getLong("playerNumberTime");
            saveDatas.randomPlayerTimeMap.put(uuid, num);
        }

        //单一变量只能用NbtCompound，不能用NbtList，否则服务器启动后数据会归零
        saveDatas.canSpawnVillager =nbt.getBoolean("canSpawnVillager");
        saveDatas.canSpawnStructure =nbt.getBoolean("canSpawnStructure");
        saveDatas.gameImp =nbt.getInt("gameImp");
        saveDatas.firstServer =nbt.getBoolean("firstServer");

        return saveDatas;
    }

    public static SaveDatas getServerState(MinecraftServer server) {
        if (Objects.requireNonNull(server.getOverworld()).isClient) {
            throw new RuntimeException("Don't access this client-side!");
        }

        PersistentStateManager persistentStateManager = Objects.requireNonNull(server.getOverworld()).getPersistentStateManager();
        SaveDatas state = persistentStateManager.getOrCreate(SaveDatas::fromNbt, SaveDatas::new, AliveAndWellMain.SAVE_FILE);

        state.markDirty();

        return state;
    }

    public  BlockPos getDestination(RandomPos randomPos,MinecraftServer server) {
        SaveDatas serverState = getServerState(server);

        if (serverState.randomPosMap.containsKey(randomPos))
            return serverState.randomPosMap.get(randomPos);
        return null;
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        NbtList list_randomPosMap = new NbtList();
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
                list_randomPosMap.add(randomNbt);
            }
        });

        NbtList list_randomPlayerMap = new NbtList();
        randomPlayerMap.forEach((uuid,num) -> {
            if(uuid != null && num != null){
                NbtCompound nbtCompound = new NbtCompound();
                nbtCompound.putUuid("playerUUID",uuid);
                nbtCompound.putInt("playerNumber",num);
                list_randomPlayerMap.add(nbtCompound);
            }
        });
        NbtList list_randomPlayerTimeMap = new NbtList();
        randomPlayerTimeMap.forEach((uuid,numTime) -> {
            if(uuid != null && numTime != null){
                NbtCompound nbtCompound = new NbtCompound();
                nbtCompound.putUuid("playerUUID",uuid);
                nbtCompound.putLong("playerNumberTime",numTime);
                list_randomPlayerTimeMap.add(nbtCompound);
            }
        });

        nbt.putBoolean("canSpawnVillager",canSpawnVillager);
        nbt.putBoolean("canSpawnStructure",canSpawnStructure);
        nbt.putInt("gameImp",gameImp);
        nbt.putBoolean("firstServer",firstServer);

        nbt.put("aliveandewell_randompos",list_randomPosMap);
        nbt.put("aliveandwell_randomPlayerMap",list_randomPlayerMap);
        nbt.put("aliveandwell_randomPlayerTimeMap",list_randomPlayerTimeMap);

        return nbt;
    }

    @Override
    public boolean isDirty() {
        return true;
    }
}
