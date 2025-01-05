package aliveandwell.aliveandwell.util;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.scoreboard.AbstractTeam;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.*;

public class RandomTpUtil {

    static int minA = 6000;
    static int maxA = 9000;

    static int minB = 10000;
    static int maxB = 20000;

    private static final Dynamic2CommandExceptionType INVALID_HEIGHT_EXCEPTION = new Dynamic2CommandExceptionType((maxY, worldBottomY) -> {
        return Text.translatable("commands.spreadplayers.failed.invalid.height", new Object[]{maxY, worldBottomY});
    });

    public RandomTpUtil() {
    }
    public static void backSpawnpoint(World world, Entity entity) {
        if(entity instanceof ServerPlayerEntity serverPlayer){
            BlockPos startingPos;
            startingPos = serverPlayer.getWorld().getSpawnPos();
            int x;
            int y;
            int z;
            if(world.getRegistryKey() == World.OVERWORLD){
                y = 150;
            }else {
                y = 100;
            }
            x = startingPos.getX();
            z = startingPos.getZ();
            Vec2f center = new Vec2f(x,z);
            Collection<ServerPlayerEntity> collection = new ArrayList<>();
            collection.add(serverPlayer);
            //参数：目标玩家，目的地x和z，扩散距离，扩散范围，世界的最高y，是否团队，玩家集合
            execute(serverPlayer, center, 1,1,y,false, collection);
        }
    }

    public static void RandomTpA(World world, Entity entity) {
        if(entity instanceof ServerPlayerEntity serverPlayer){
            BlockPos startingPos;
            startingPos = serverPlayer.getBlockPos();
            java.util.Random rand = new java.util.Random();
            int x;
            int y = 100;
            int z;

            if(world.getDimension().hasCeiling()){
                if(world.getRegistryKey() == World.NETHER){
                    y = 80;
                }
            }else {
                y = world.getTopY();
            }

            x = Math.round(startingPos.getX()) + rand.nextInt(maxA + minA) - minA;
            z = Math.round(startingPos.getZ()) + rand.nextInt(maxA + minA) - minA;
            Vec2f center = new Vec2f(x,z);
            Collection<ServerPlayerEntity> collection = new ArrayList<>();
            collection.add(serverPlayer);
                //参数：目标玩家，目的地x和z，扩散距离，扩散范围，世界的最高y，是否团队，玩家集合
            execute(serverPlayer, center, 1,1,y,false, collection);
        }
    }

    public static void RandomTpB(World world, Entity entity) {
        if(entity instanceof ServerPlayerEntity serverPlayer){
            BlockPos startingPos;
            startingPos = serverPlayer.getBlockPos();
            java.util.Random rand = new java.util.Random();
            int x;
            int y = 100;
            int z;
            if(world.getDimension().hasCeiling()){
                if(world.getRegistryKey() == World.NETHER){
                    y = 80;
                }
            }else {
                y = world.getTopY();
            }
            x = Math.round(startingPos.getX()) + rand.nextInt(maxB + minB) - minB;
            z = Math.round(startingPos.getZ()) + rand.nextInt(maxB + minB) - minB;
            Vec2f center = new Vec2f(x,z);
            Collection<ServerPlayerEntity> collection = new ArrayList<>();
            collection.add(serverPlayer);
                //参数：目标玩家，目的地x和z，扩散距离，扩散范围，世界的最高y，是否团队，玩家集合
            execute(serverPlayer, center, 1,1,y,false, collection);
        }
    }

    private static void execute(ServerPlayerEntity player, Vec2f center, float spreadDistance, float maxRange, int maxY, boolean respectTeams, Collection<? extends Entity> players)  {
        ServerWorld serverWorld = player.getWorld();
        int i = serverWorld.getBottomY();
        if (maxY >= i) {
            Random random = Random.create();
            double d = (double)(center.x - maxRange);
            double e = (double)(center.y - maxRange);
            double f = (double)(center.x + maxRange);
            double g = (double)(center.y + maxRange);
            Pile[] piles = makePiles(random, respectTeams ? getPileCountRespectingTeams(players) : players.size(), d, e, f, g);
            spread(center, (double)spreadDistance, serverWorld, random, d, e, f, g, maxY, piles, respectTeams);
            double h = getMinDistance(players, serverWorld, piles, maxY, respectTeams);
        }
    }

    private static int getPileCountRespectingTeams(Collection<? extends Entity> entities) {
        HashSet<AbstractTeam> set = Sets.newHashSet();
        for (Entity entity : entities) {
            if (entity instanceof PlayerEntity) {
                set.add(entity.getScoreboardTeam());
                continue;
            }
            set.add(null);
        }
        return set.size();
    }

    private static void spread(Vec2f center, double spreadDistance, ServerWorld world, Random random, double minX, double minZ, double maxX, double maxZ, int maxY, RandomTpUtil.Pile[] piles, boolean respectTeams) {
        int i;
        boolean bl = true;
        double d = 3.4028234663852886E38;
        for (i = 0; i < 10000 && bl; ++i) {
            bl = false;
            d = 3.4028234663852886E38;
            for (int j = 0; j < piles.length; ++j) {
                RandomTpUtil.Pile pile = piles[j];
                int k = 0;
                RandomTpUtil.Pile pile2 = new RandomTpUtil.Pile();
                for (int l = 0; l < piles.length; ++l) {
                    if (j == l) continue;
                    RandomTpUtil.Pile pile3 = piles[l];
                    double e = pile.getDistance(pile3);
                    d = Math.min(e, d);
                    if (!(e < spreadDistance)) continue;
                    ++k;
                    pile2.x += pile3.x - pile.x;
                    pile2.z += pile3.z - pile.z;
                }
                if (k > 0) {
                    pile2.x /= (double)k;
                    pile2.z /= (double)k;
                    double f = pile2.absolute();
                    if (f > 0.0) {
                        pile2.normalize();
                        pile.subtract(pile2);
                    } else {
                        pile.setPileLocation(random, minX, minZ, maxX, maxZ);
                    }
                    bl = true;
                }
                if (!pile.clamp(minX, minZ, maxX, maxZ)) continue;
                bl = true;
            }
            if (bl) continue;
            for (RandomTpUtil.Pile pile2 : piles) {
                if (pile2.isSafe(world, maxY)) continue;
                pile2.setPileLocation(random, minX, minZ, maxX, maxZ);
                bl = true;
            }
        }
        if (d == 3.4028234663852886E38) {
            d = 0.0;
        }
        if (i >= 10000) {
//            if (respectTeams) {
//                throw FAILED_TEAMS_EXCEPTION.create(piles.length, Float.valueOf(center.x), Float.valueOf(center.y), String.format(Locale.ROOT, "%.2f", d));
//            }
//            throw FAILED_ENTITIES_EXCEPTION.create(piles.length, Float.valueOf(center.x), Float.valueOf(center.y), String.format(Locale.ROOT, "%.2f", d));
            world.getServer().sendMessage(Text.translatable("random8888888888888888888888"));
        }

    }

    private static double getMinDistance(Collection<? extends Entity> entities, ServerWorld world, RandomTpUtil.Pile[] piles, int maxY, boolean respectTeams) {
        double d = 0.0;
        int i = 0;
        HashMap<AbstractTeam, RandomTpUtil.Pile> map = Maps.newHashMap();
        for (Entity entity : entities) {
            RandomTpUtil.Pile pile;
            if (respectTeams) {
                AbstractTeam abstractTeam;
                AbstractTeam abstractTeam2 = abstractTeam = entity instanceof PlayerEntity ? entity.getScoreboardTeam() : null;
                if (!map.containsKey(abstractTeam)) {
                    map.put(abstractTeam, piles[i++]);
                }
                pile = (RandomTpUtil.Pile)map.get(abstractTeam);
            } else {
                pile = piles[i++];
            }

            entity.teleport((double)MathHelper.floor(pile.x) + 0.5, pile.getY(world, maxY), (double)MathHelper.floor(pile.z) + 0.5);
            double e = Double.MAX_VALUE;
            for (RandomTpUtil.Pile pile2 : piles) {
                if (pile == pile2) continue;
                double f = pile.getDistance(pile2);
                e = Math.min(f, e);
            }
            d += e;
        }
        if (entities.size() < 2) {
            return 0.0;
        }
        return d /= (double)entities.size();
    }

    private static RandomTpUtil.Pile[] makePiles(Random random, int count, double minX, double minZ, double maxX, double maxZ) {
        RandomTpUtil.Pile[] piles = new RandomTpUtil.Pile[count];
        for (int i = 0; i < piles.length; ++i) {
            RandomTpUtil.Pile pile = new RandomTpUtil.Pile();
            pile.setPileLocation(random, minX, minZ, maxX, maxZ);
            piles[i] = pile;
        }
        return piles;
    }

    static class Pile {
        double x;
        double z;

        Pile() {
        }

        double getDistance(RandomTpUtil.Pile other) {
            double d = this.x - other.x;
            double e = this.z - other.z;
            return Math.sqrt(d * d + e * e);
        }

        void normalize() {
            double d = this.absolute();
            this.x /= d;
            this.z /= d;
        }

        double absolute() {
            return Math.sqrt(this.x * this.x + this.z * this.z);
        }

        public void subtract(RandomTpUtil.Pile other) {
            this.x -= other.x;
            this.z -= other.z;
        }

        public boolean clamp(double minX, double minZ, double maxX, double maxZ) {
            boolean bl = false;
            if (this.x < minX) {
                this.x = minX;
                bl = true;
            } else if (this.x > maxX) {
                this.x = maxX;
                bl = true;
            }
            if (this.z < minZ) {
                this.z = minZ;
                bl = true;
            } else if (this.z > maxZ) {
                this.z = maxZ;
                bl = true;
            }
            return bl;
        }

        public int getY(BlockView blockView, int maxY) {
            BlockPos.Mutable mutable = new BlockPos.Mutable(this.x, (double)(maxY + 1), this.z);
            boolean bl = blockView.getBlockState(mutable).isAir();
            mutable.move(Direction.DOWN);
            boolean bl2 = blockView.getBlockState(mutable).isAir();
            while (mutable.getY() > blockView.getBottomY()) {
                mutable.move(Direction.DOWN);
                boolean bl3 = blockView.getBlockState(mutable).isAir() || (blockView.getBlockState(mutable).getBlock()==Blocks.BEDROCK) || (blockView.getBlockState(mutable).getBlock().equals(Blocks.WATER));
                if (!bl3 && bl2 && bl) {
                    return mutable.getY() + 1;
                }
                bl = bl2;
                bl2 = bl3;
            }
            return maxY + 1;
        }

        public boolean isSafe(BlockView world, int maxY) {
            BlockPos blockPos = new BlockPos(this.x, (double)(this.getY(world, maxY) - 1), this.z);
            BlockState blockState = world.getBlockState(blockPos);
            Material material = blockState.getMaterial();
            return blockPos.getY() < maxY && !material.isLiquid() && material != Material.FIRE;
        }

        public void setPileLocation(Random random, double minX, double minZ, double maxX, double maxZ) {
            this.x = MathHelper.nextDouble(random, minX, maxX);
            this.z = MathHelper.nextDouble(random, minZ, maxZ);
        }
    }
}

