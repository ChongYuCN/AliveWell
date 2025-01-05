package aliveandwell.aliveandwell.block.randompos;

import net.minecraft.util.math.ChunkPos;

import java.util.Objects;

public class RandomPos {
    public String world;
    public ChunkPos chunkPos;
    public String topA;
    public String topB;
    public String bomA;
    public String bomB;
    public RandomPos(String world, ChunkPos chunkPos, String topA, String topB, String bomA, String bomB){
        this.world = world;
        this.chunkPos = chunkPos;
        this.topA = topA;
        this.topB = topB;
        this.bomA = bomA;
        this.bomB = bomB;
    }

    public String getWorld(){
        return this.world;
    }
    public ChunkPos getChunkPos(){
        return this.chunkPos;
    }
    public String getTopA(){
        return this.topA;
    }
    public String getTopB(){
        return this.topB;
    }
    public String getBomA(){
        return this.bomA;
    }
    public String getBomB(){
        return this.bomB;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RandomPos randomPos = (RandomPos) o;
        return world.equals(randomPos.world) && chunkPos.equals(randomPos.chunkPos) && topA.equals(randomPos.topA) && topB.equals(randomPos.topB) && bomA.equals(randomPos.bomA) && bomB.equals(randomPos.bomB);
    }

    @Override
    public int hashCode() {
        return Objects.hash(world, chunkPos, topA, topB, bomA, bomB);
    }
}
