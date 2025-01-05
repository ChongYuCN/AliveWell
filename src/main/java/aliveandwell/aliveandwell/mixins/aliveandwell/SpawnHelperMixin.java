package aliveandwell.aliveandwell.mixins.aliveandwell;

import aliveandwell.aliveandwell.dimensions.DimsRegistry;
import aliveandwell.aliveandwell.util.Moons;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Heightmap;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.WorldChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;
import java.util.stream.Stream;

@Mixin(SpawnHelper.class)
public class SpawnHelperMixin {
    @Shadow
    private static final SpawnGroup[] SPAWNABLE_GROUPS = (SpawnGroup[]) Stream.of(SpawnGroup.values()).filter(spawnGroup -> spawnGroup != SpawnGroup.MISC).toArray(SpawnGroup[]::new);

//    @Overwrite
//    private static boolean isAcceptableSpawnPosition(ServerWorld world, Chunk chunk, BlockPos.Mutable pos, double squaredDistance) {
//        if (squaredDistance <= 225.0) {
//            return false;
//        }
//        if (world.getSpawnPos().isWithinDistance(new Vec3d((double)pos.getX() + 0.5, pos.getY(), (double)pos.getZ() + 0.5), 15.0)) {
//            return false;
//        }
//
//        int posY = pos.getY();
//        int high;
//        if(world.getRegistryKey() == DimsRegistry.UNDER_WORLD_KEY){
//            high=32;
//        }else {
//            high=20;
//        }
////        int players = world.getServer().getCurrentPlayerCount();
//        PlayerEntity playerEntity = world.getClosestPlayer(pos.getX()+0.5, (double)pos.getY(), pos.getZ()+0.5, -1.0, false);
//        if(playerEntity == null || Math.abs(posY-playerEntity.getBlockPos().getY()) > high) {
//            return false;
//        }
//
//        return Objects.equals(new ChunkPos(pos), chunk.getPos()) || world.shouldTick(pos);
//    }

    @ModifyConstant(method = "isAcceptableSpawnPosition", constant = @Constant(doubleValue = 24.0))
    private static double isAcceptableSpawnPosition(double constant) {
        return 10.0d;
    }

    @ModifyConstant(method = "isAcceptableSpawnPosition", constant = @Constant(doubleValue = 576.0))
    private static double isAcceptableSpawnPositionDistance(double constant) {
        return 100.0;
    }
//    @Inject(at = @At("RETURN"), method = "isAcceptableSpawnPosition", cancellable = true)
//    private static void isAcceptableSpawnPosition(ServerWorld world, Chunk chunk, BlockPos.Mutable pos, double squaredDistance, CallbackInfoReturnable<Boolean> cir) {
//        int posY = pos.getY();
//        int high;
//        int players = world.getServer().getCurrentPlayerCount();
//        PlayerEntity playerEntity = world.getClosestPlayer(pos.getX()+0.5, (double)pos.getY(), pos.getZ()+0.5, -1.0, false);
//        if(world.getRegistryKey() == DimsRegistry.UNDER_WORLD_KEY || world.getRegistryKey() == World.NETHER){
//            if(AliveAndWellMain.day <= 128){
//                if(players == 1){
//                    if(world.random.nextInt(35)  < 5){
//                        high = 15;
//                    }else {
//                        high = 20;
//                    }
//                }else if(players <= 4){
//                    if(world.random.nextInt(30)  < 5){
//                        high = 15;
//                    }else {
//                        high = 20;
//                    }
//                }else if(players <= 8){
//                    if(world.random.nextInt(25)  < 5){
//                        high = 15;
//                    }else {
//                        high = 20;
//                    }
//                }else {
//                    if(world.random.nextInt(20)  < 5){
//                        high = 15;
//                    }else {
//                        high = 20;
//                    }
//                }
//            }else {
//                if(world.random.nextInt(20)  < 5){
//                    high = 15;
//                }else {
//                    high = 20;
//                }
//            }
//        }else if(world.getRegistryKey() == World.OVERWORLD){
//            if(AliveAndWellMain.day >= 10 && AliveAndWellMain.day <= 64){
//                if(players == 1){
//                    if(world.random.nextInt(35)  < 5){
//                        high = 15;
//                    }else {
//                        high = 20;
//                    }
//                }else if(players <= 4){
//                    if(world.random.nextInt(30)  < 5){
//                        high = 15;
//                    }else {
//                        high = 20;
//                    }
//
//                }else if(players <= 8){
//                    if(world.random.nextInt(25)  < 5){
//                        high = 15;
//                    }else {
//                        high = 20;
//                    }
//                }else {
//                    if(world.random.nextInt(20)  < 5){
//                        high = 15;
//                    }else {
//                        high = 20;
//                    }
//                }
//            }else {
//                if(world.random.nextInt(20)  < 5){
//                    high = 15;
//                }else {
//                    high = 20;
//                }
//            }
//        }else {
//            high = 20;
//        }
//
//        if(playerEntity == null || Math.abs(posY-playerEntity.getBlockPos().getY()) > high) {
//            cir.setReturnValue(false);
//        }
//    }

//    @Inject(method = "spawnEntitiesInChunk(Lnet/minecraft/entity/SpawnGroup;Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/world/chunk/Chunk;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/SpawnHelper$Checker;Lnet/minecraft/world/SpawnHelper$Runner;)V",
//            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/SpawnHelper;isAcceptableSpawnPosition(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/world/chunk/Chunk;Lnet/minecraft/util/math/BlockPos$Mutable;D)Z"), cancellable = true)
//    private static void spawnEntitiesInChunk(SpawnGroup group, ServerWorld world, Chunk chunk, BlockPos pos, SpawnHelper.Checker checker, SpawnHelper.Runner runner, CallbackInfo ci) {
//        int posY = pos.getY();
//        PlayerEntity playerEntity = world.getClosestPlayer(pos.getX()+0.5, (double)pos.getY(), pos.getZ()+0.5, -1.0, false);
//        if(playerEntity == null || Math.abs(posY-playerEntity.getBlockPos().getY()) > 15) ci.cancel();
//    }
//    @Inject(method = "spawnEntitiesInChunk(Lnet/minecraft/entity/SpawnGroup;Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/world/chunk/Chunk;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/SpawnHelper$Checker;Lnet/minecraft/world/SpawnHelper$Runner;)V",
//            at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/BlockPos$Mutable;<init>()V"), cancellable = true)
//    private static void spawnEntitiesInChunk(SpawnGroup group, ServerWorld world, Chunk chunk, BlockPos pos, SpawnHelper.Checker checker, SpawnHelper.Runner runner, CallbackInfo ci) {
//        int posY = pos.getY();
//        PlayerEntity playerEntity = world.getClosestPlayer(pos.getX()+0.5, (double)posY, pos.getZ()+0.5, 64.0, false);
//        if(playerEntity == null || Math.abs(posY-playerEntity.getBlockPos().getY()) > 15) ci.cancel();
//    }
//    @Inject(method = "spawnEntitiesInChunk(Lnet/minecraft/entity/SpawnGroup;Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/world/chunk/Chunk;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/SpawnHelper$Checker;Lnet/minecraft/world/SpawnHelper$Runner;)V",
//            at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/ServerWorld;spawnEntityAndPassengers(Lnet/minecraft/entity/Entity;)V"), cancellable = true)
//    private static void spawnEntitiesInChunk2(SpawnGroup group, ServerWorld world, Chunk chunk, BlockPos pos, SpawnHelper.Checker checker, SpawnHelper.Runner runner, CallbackInfo ci)  {
//        List<LivingEntity> mobs = world.<LivingEntity>getEntitiesByClass(
//                LivingEntity.class,
//                new Box(chunk.getPos().x+8, pos.getY() + 8, chunk.getPos().z+8, chunk.getPos().x -8, pos.getY() - 8, chunk.getPos().z-8),
//                (e) -> e instanceof MobEntity
//        );
//        List<LivingEntity> players = world.<LivingEntity>getEntitiesByClass(
//                LivingEntity.class,
//                new Box(chunk.getPos().x+8, pos.getY() + 8, chunk.getPos().z+8, chunk.getPos().x -8, pos.getY() - 8, chunk.getPos().z-8),
//                (e) -> e instanceof PlayerEntity
//        );
//        if(players.isEmpty() && mobs.size() >= 1) return;
//        if(players.size() <= 2 && mobs.size() >= 5) return;
//        if(players.size() >= 3 && mobs.size()>=10)  ci.cancel();
//    }

    @Inject(at=@At("HEAD"), method = "getRandomPosInChunkSection", cancellable = true)
    private static void getRandomPosInChunkSection(World world, WorldChunk chunk, CallbackInfoReturnable<BlockPos> cir) {
        if(Moons.isBlueMoon() && Moons.isNight()){
            ChunkPos chunkPos = chunk.getPos();
            int i = chunkPos.getStartX() + world.random.nextInt(16);
            int j = chunkPos.getStartZ() + world.random.nextInt(16);
            int k = chunk.sampleHeightmap(Heightmap.Type.WORLD_SURFACE, i, j) -5;
            int l = MathHelper.nextBetween(world.random, world.getBottomY(), k);
            if(l >= 60){
                l = 60;
            }
            cir.setReturnValue(new BlockPos(i, l, j));
        }else {
            ChunkPos chunkPos = chunk.getPos();
            int i = chunkPos.getStartX() + world.random.nextInt(16);
            int j = chunkPos.getStartZ() + world.random.nextInt(16);
            int k = chunk.sampleHeightmap(Heightmap.Type.WORLD_SURFACE, i, j) + 1;

            int l = MathHelper.nextBetween(world.random, world.getBottomY(), k);

            PlayerEntity playerEntity = world.getClosestPlayer(i+0.5, l, j+0.5, -1.0, false);
            int high;
            int palyerPosY = 0;
            if (playerEntity != null) {
                palyerPosY = playerEntity.getBlockPos().getY();
            }

            if(world.getRegistryKey() == DimsRegistry.UNDER_WORLD_KEY){
                high=24;
            }else {
                high=15;
            }
            if(playerEntity == null || Math.abs(l-palyerPosY) > high) {
                if(l<palyerPosY){
                    l=palyerPosY-new Random().nextInt(15);
//                if(world.isNight() && world.getLightLevel(new BlockPos(i, l, j)) >= 7){
//                    l=palyerPosY-(new Random().nextInt(high-5)+10);
//                }else {
//                    l=palyerPosY-(new Random().nextInt(high-5)+10);
//                }
                }else {
                    l=palyerPosY+new Random().nextInt(15);
                }
            }

//        AliveAndWellMain.LOGGER.info("&&&&&&&&&&&&"+"i="+i+";l="+l+";j="+j+"player="+String.valueOf(playerEntity==null));
            cir.setReturnValue(new BlockPos(i, l, j));
        }
    }

//    @Overwrite
//    private static BlockPos getRandomPosInChunkSection(World world, WorldChunk chunk) {
//        ChunkPos chunkPos = chunk.getPos();
//        int i = chunkPos.getStartX() + world.random.nextInt(16);
//        int j = chunkPos.getStartZ() + world.random.nextInt(16);
//        int k = chunk.sampleHeightmap(Heightmap.Type.WORLD_SURFACE, i, j) + 1;
//
//        int l = MathHelper.nextBetween(world.random, world.getBottomY(), k);
//
//        PlayerEntity playerEntity = world.getClosestPlayer(i+0.5, l, j+0.5, -1.0, false);
//        int high;
//        int palyerPosY = playerEntity.getBlockPos().getY();
//
//        if(world.getRegistryKey() == DimsRegistry.UNDER_WORLD_KEY){
//            high=24;
//        }else {
//            high=15;
//        }
//        if(playerEntity == null || Math.abs(l-palyerPosY) > high) {
//            if(l<palyerPosY){
//                l=palyerPosY-new Random().nextInt(15);
////                if(world.isNight() && world.getLightLevel(new BlockPos(i, l, j)) >= 7){
////                    l=palyerPosY-(new Random().nextInt(high-5)+10);
////                }else {
////                    l=palyerPosY-(new Random().nextInt(high-5)+10);
////                }
//            }else {
//                l=palyerPosY+new Random().nextInt(15);
//            }
//        }
//
////        AliveAndWellMain.LOGGER.info("&&&&&&&&&&&&"+"i="+i+";l="+l+";j="+j+"player="+String.valueOf(playerEntity==null));
//        return new BlockPos(i, l, j);
//    }
}
