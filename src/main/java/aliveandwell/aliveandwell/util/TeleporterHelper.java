package aliveandwell.aliveandwell.util;

import aliveandwell.aliveandwell.dimensions.DimsRegistry;
import net.kyrptonaught.customportalapi.CustomPortalApiRegistry;
import net.kyrptonaught.customportalapi.CustomPortalsMod;
import net.kyrptonaught.customportalapi.interfaces.CustomTeleportingEntity;
import net.kyrptonaught.customportalapi.portal.PortalPlacer;
import net.kyrptonaught.customportalapi.portal.frame.PortalFrameTester;
import net.kyrptonaught.customportalapi.portal.linking.DimensionalBlockPos;
import net.kyrptonaught.customportalapi.util.CustomPortalHelper;
import net.kyrptonaught.customportalapi.util.PortalLink;
import net.kyrptonaught.customportalapi.util.SHOULDTP;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.entity.Entity;
import net.minecraft.server.command.SpreadPlayersCommand;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ChunkTicketType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.*;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.BlockLocating;
import net.minecraft.world.Heightmap;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.dimension.DimensionType;

import java.util.Locale;
import java.util.Optional;
import java.util.Random;


public class TeleporterHelper {

    static int min = 1000;
    static int min2 = 5000;
    static int max = 3000;
    static int max2 = 20000;

    public static void TPToDim(World world, Entity entity, Block portalBase, BlockPos portalPos) {

        PortalLink link = CustomPortalApiRegistry.getPortalLinkFromBase(portalBase);
        if (link == null) return;
        if (link.getBeforeTPEvent().execute(entity) == SHOULDTP.CANCEL_TP)
            return;
//        RegistryKey<World> destKey = world.getRegistryKey() == CustomPortalsMod.dims.get(link.dimID) ? CustomPortalsMod.dims.get(link.returnDimID) : CustomPortalsMod.dims.get(link.dimID);

        RegistryKey<World> destKey = world.getRegistryKey();
        if(portalBase == Blocks.AMETHYST_BLOCK){
            if(entity.world.getRegistryKey() == DimsRegistry.UNDER_WORLD_KEY){
                destKey = World.OVERWORLD;
            }
            if(entity.world.getRegistryKey() == World.OVERWORLD){
                destKey = DimsRegistry.UNDER_WORLD_KEY;
            }
        }else if(portalBase == Blocks.BUDDING_AMETHYST){
            if(entity.world.getRegistryKey() == DimsRegistry.UNDER_WORLD_KEY){
                destKey = World.NETHER;
            }
            if(entity.world.getRegistryKey() == World.NETHER){
                destKey = DimsRegistry.UNDER_WORLD_KEY;
            }
        }else {
            RegistryKey<World> worldRegistryKey = destKey == CustomPortalsMod.dims.get(link.dimID) ? CustomPortalsMod.dims.get(link.returnDimID) : CustomPortalsMod.dims.get(link.dimID);
        }

        ServerWorld destination = ((ServerWorld) world).getServer().getWorld(destKey);
        if (destination == null) return;
        if (!entity.canUsePortals()) return;

        destination.getChunkManager().addTicket(ChunkTicketType.PORTAL, new ChunkPos(new BlockPos(portalPos.getX() / destination.getDimension().coordinateScale(), portalPos.getY() / destination.getDimension().coordinateScale(), portalPos.getZ() / destination.getDimension().coordinateScale())), 3, new BlockPos(portalPos.getX() / destination.getDimension().coordinateScale(), portalPos.getY() / destination.getDimension().coordinateScale(), portalPos.getZ() / destination.getDimension().coordinateScale()));
        TeleportTarget target = customTPTarget(destination, entity, portalPos, portalBase, link.getFrameTester());
        ((CustomTeleportingEntity) entity).setCustomTeleportTarget(target);
        entity = entity.moveToWorld(destination);
        if (entity != null) {
            entity.setYaw(target.yaw);
            entity.setPitch(target.pitch);
            if (entity instanceof ServerPlayerEntity)
                entity.refreshPositionAfterTeleport(target.position);
            link.executePostTPEvent(entity);
        }
    }


    public static TeleportTarget customTPTarget(ServerWorld destinationWorld, Entity entity, BlockPos enteredPortalPos, Block frameBlock, PortalFrameTester.PortalFrameTesterFactory portalFrameTesterFactory) {
        Direction.Axis portalAxis = CustomPortalHelper.getAxisFrom(entity.getEntityWorld().getBlockState(enteredPortalPos));
        BlockLocating.Rectangle fromPortalRectangle = portalFrameTesterFactory.createInstanceOfPortalFrameTester().init(entity.getEntityWorld(), enteredPortalPos, portalAxis, frameBlock).getRectangle();
        DimensionalBlockPos destinationPos = CustomPortalsMod.portalLinkingStorage.getDestination(fromPortalRectangle.lowerLeft, entity.getEntityWorld().getRegistryKey());

        if (destinationPos != null && destinationPos.dimensionType.equals(destinationWorld.getRegistryKey().getValue())) {
            PortalFrameTester portalFrameTester = portalFrameTesterFactory.createInstanceOfPortalFrameTester().init(destinationWorld, destinationPos.pos, portalAxis, frameBlock);
            if (portalFrameTester.isValidFrame()) {
                if (!portalFrameTester.isAlreadyLitPortalFrame()) {
                    portalFrameTester.lightPortal(frameBlock);
                }
                return portalFrameTester.getTPTargetInPortal(portalFrameTester.getRectangle(), portalAxis, portalFrameTester.getEntityOffsetInPortal(fromPortalRectangle, entity, portalAxis), entity);
            }
        }
        return createDestinationPortal(destinationWorld, entity, portalAxis, fromPortalRectangle, frameBlock.getDefaultState());
    }

    public static TeleportTarget createDestinationPortal(ServerWorld destination, Entity entity, Direction.Axis axis, BlockLocating.Rectangle portalFramePos, BlockState frameBlock) {
        WorldBorder worldBorder = destination.getWorldBorder();
        double xMin = Math.max(-2.9999872E7D, worldBorder.getBoundWest() + 16.0D);
        double zMin = Math.max(-2.9999872E7D, worldBorder.getBoundNorth() + 16.0D);
        double xMax = Math.min(2.9999872E7D, worldBorder.getBoundEast() - 16.0D);
        double zMax = Math.min(2.9999872E7D, worldBorder.getBoundSouth() - 16.0D);
        double scaleFactor = DimensionType.getCoordinateScaleFactor(entity.world.getDimension(), destination.getDimension());
        BlockPos blockPos3 = new BlockPos(MathHelper.clamp(entity.getX() * scaleFactor, xMin, xMax), entity.getY(), MathHelper.clamp(entity.getZ() * scaleFactor, zMin, zMax));
        Optional<BlockLocating.Rectangle> portal = PortalPlacer.createDestinationPortal(destination, blockPos3, frameBlock, axis);
        if (portal.isPresent()) {
            PortalFrameTester portalFrameTester = CustomPortalApiRegistry.getPortalLinkFromBase(frameBlock.getBlock()).getFrameTester().createInstanceOfPortalFrameTester();

            CustomPortalsMod.portalLinkingStorage.createLink(portalFramePos.lowerLeft, entity.world.getRegistryKey(), portal.get().lowerLeft, destination.getRegistryKey());
            return portalFrameTester.getTPTargetInPortal(portal.get(), axis, portalFrameTester.getEntityOffsetInPortal(portalFramePos, entity, axis), entity);
        }
        return idkWhereToPutYou(destination, entity, blockPos3);
    }


    protected static TeleportTarget idkWhereToPutYou(ServerWorld world, Entity entity, BlockPos pos) {
        CustomPortalsMod.logError("Unable to find tp location, forced to place on top of world");
        BlockPos destinationPos = world.getTopPosition(Heightmap.Type.WORLD_SURFACE, pos);
        return new TeleportTarget(new Vec3d(destinationPos.getX() + .5, destinationPos.getY(), destinationPos.getZ() + .5), entity.getVelocity(), entity.getYaw(), entity.getPitch());
    }


    public static void randomTP1(World world, Entity entity) {
        if (!world.isClient && !entity.isSneaking()) {
            if (world instanceof ServerWorld) {
                // 检测安全点的次数
                for(int i = 1; i < 6; i++) {
                    if(entity instanceof ServerPlayerEntity){
                        ServerPlayerEntity serverPlayer = (ServerPlayerEntity) entity;
                        if(i == 1) {
                            serverPlayer.sendMessage((Text.translatable("Checking for a safe spot, please wait a moment")), true);
                        }
                        if(i > 1 && i < 6) {
                            serverPlayer.sendMessage((Text.translatable("Still checking...")), true);
                        }
                    }
                    BlockPos startingPos;
                    BlockPos playerPos = entity.getBlockPos();
                    Random rand = new Random();

                    startingPos = playerPos;

                    int x;
                    int y = 150;
                    int z;
                    x = Math.round(startingPos.getX()) + rand.nextInt(max + min) - min;
                    z = Math.round(startingPos.getZ()) + rand.nextInt(max + min) - min;

                    Chunk chunk = world.getChunk(x >> 4, z >> 4);
                    RegistryEntry<Biome> registryEntry = world.getBiome(startingPos);
                    //河流、海洋、海滩会继续
                    if (registryEntry.matchesKey(BiomeKeys.OCEAN) ||
                            registryEntry.matchesKey(BiomeKeys.RIVER) ||
                            registryEntry.matchesKey(BiomeKeys.BEACH)) {
                        continue;
                    }

                    //让我们避免把它们放在地下=最小为60高度
                    while (y > 60) {
                        y--;
                        BlockPos groundPos = new BlockPos(x, y-2, z);//着陆点
                        //着陆点方块不是空气、基岩、水
                        if(!chunk.getBlockState(groundPos).getMaterial().equals(Material.AIR) &&
                                (!chunk.getBlockState(groundPos).getBlock().equals(Blocks.BEDROCK) &&
                                        (!chunk.getBlockState(groundPos).getBlock().equals(Blocks.WATER) &&
                                                (y-2) > 60))) {
                            //检测腿部坐标方块是否为空气
                            BlockPos legPos = new BlockPos(x, y-1, z);
                            if (chunk.getBlockState(legPos).getMaterial().equals(Material.AIR)) {
                                //检测头部坐标方块是否为空气
                                BlockPos headPos = new BlockPos(x, y, z);
                                if (chunk.getBlockState(headPos).getMaterial().equals(Material.AIR)) {
                                    entity.stopRiding();//不能骑乘
                                    entity.requestTeleport(x, y, z);
                                    entity.fallDistance = 0.0F;
                                    if(entity instanceof ServerPlayerEntity){
                                        ServerPlayerEntity serverPlayer = (ServerPlayerEntity) entity;
                                        serverPlayer.sendMessage((Text.translatable("Enjoy your exploring.")), true);
                                    }
//                                    world.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.BLOCK_PORTAL_TRAVEL, SoundCategory.NEUTRAL, 0.5F, 0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F));
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static void randomTP2(World world, Entity entity) {
        if (!world.isClient && !entity.isSneaking()) {
            if (world instanceof ServerWorld) {
                // 检测安全点的次数
                for(int i = 1; i < 6; i++) {
                    if(entity instanceof ServerPlayerEntity){
                        ServerPlayerEntity serverPlayer = (ServerPlayerEntity) entity;
                        if(i == 1) {
                            serverPlayer.sendMessage((Text.translatable("Checking for a safe spot, please wait a moment")), true);
                        }
                        if(i > 1 && i < 6) {
                            serverPlayer.sendMessage((Text.translatable("Still checking...")), true);
                        }
                    }
                    BlockPos startingPos;
                    BlockPos playerPos = entity.getBlockPos();
                    Random rand = new Random();

                    startingPos = playerPos;

                    int x;
                    int y = 150;
                    int z;
                    x = Math.round(startingPos.getX()) + rand.nextInt(max + min) - min;
                    z = Math.round(startingPos.getZ()) + rand.nextInt(max + min) - min;

                    Chunk chunk = world.getChunk(x >> 4, z >> 4);
                    RegistryEntry<Biome> registryEntry = world.getBiome(startingPos);
                    //河流、海洋、海滩会继续
                    if (registryEntry.matchesKey(BiomeKeys.OCEAN) ||
                            registryEntry.matchesKey(BiomeKeys.RIVER) ||
                            registryEntry.matchesKey(BiomeKeys.BEACH)) {
                        continue;
                    }

                    //让我们避免把它们放在地下=最小为60高度
                    while (y > 60) {
                        y--;
                        BlockPos groundPos = new BlockPos(x, y-2, z);//着陆点
                        //着陆点方块不是空气、基岩、水
                        if(!chunk.getBlockState(groundPos).getMaterial().equals(Material.AIR) &&
                                (!chunk.getBlockState(groundPos).getBlock().equals(Blocks.BEDROCK) &&
                                        (!chunk.getBlockState(groundPos).getBlock().equals(Blocks.WATER) &&
                                                (y-2) > 60))) {
                            //检测腿部坐标方块是否为空气
                            BlockPos legPos = new BlockPos(x, y-1, z);
                            if (chunk.getBlockState(legPos).getMaterial().equals(Material.AIR)) {
                                //检测头部坐标方块是否为空气
                                BlockPos headPos = new BlockPos(x, y, z);
                                if (chunk.getBlockState(headPos).getMaterial().equals(Material.AIR)) {
                                    entity.stopRiding();//不能骑乘
                                    entity.requestTeleport(x, y, z);
                                    entity.fallDistance = 0.0F;
                                    if(entity instanceof ServerPlayerEntity){
                                        ServerPlayerEntity serverPlayer = (ServerPlayerEntity) entity;
                                        serverPlayer.sendMessage((Text.translatable("Enjoy your exploring.")), true);
                                    }
//                                    world.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.BLOCK_PORTAL_TRAVEL, SoundCategory.NEUTRAL, 0.5F, 0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F));
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static void backSpawnpoint(World world, Entity entity) {

        if (!world.isClient ) {
            if (world instanceof ServerWorld) {
                // 检测安全点的次数
                for(int i = 1; i < 6; i++) {
                    RegistryKey<World> registryKey = world.getRegistryKey();

                    //只能在主世界随机传送
                    if(!(registryKey == World.OVERWORLD)) {
                       return;
                    }

                    BlockPos startingPos;
                    ServerWorld serverWorld = (ServerWorld)world;
                    BlockPos worldSpawn = serverWorld.getSpawnPos();//出生点
//                    BlockPos playerPos = player.getBlockPos();
//                    Random rand = new Random();

                    startingPos = worldSpawn;

                    // 使用当前世界出生点x和z作为起点
                    int x = startingPos.getX();
                    int y = 150;
                    int z = startingPos.getZ();

                    Chunk chunk = world.getChunk(x >> 4, z >> 4);
//                    RegistryEntry<Biome> registryEntry = world.getBiome(startingPos);
//                    //河流、海洋、海滩会继续
//                    if (registryEntry.matchesKey(BiomeKeys.OCEAN) ||
//                            registryEntry.matchesKey(BiomeKeys.RIVER) ||
//                            registryEntry.matchesKey(BiomeKeys.BEACH)) {
//                        continue;
//                    }
                    int y1=0;
                    //让我们避免把它们放在地下=最小为60高度
                    while (y > 60) {
                        y--;
                        BlockPos groundPos = new BlockPos(x, y-2, z);//着陆点
                        //着陆点方块不是空气、基岩、水
                        if(!chunk.getBlockState(groundPos).getMaterial().equals(Material.AIR) &&
                                (!chunk.getBlockState(groundPos).getBlock().equals(Blocks.BEDROCK) &&
                                        (!chunk.getBlockState(groundPos).getBlock().equals(Blocks.WATER) &&
                                                (y-2) > 60))) {
                            int m=0;
                            for (int k = 0 ;k<=100;k++){
                                y1=y+k;
                                BlockPos kPos = (new BlockPos(x, y1, z)).mutableCopy();
                                if(!chunk.getBlockState(kPos).getMaterial().equals(Material.AIR)){
                                    m++;
                                }
                            }
                            //检测腿部坐标方块是否为空气
                            BlockPos legPos = new BlockPos(x, y-1, z);
                            if (chunk.getBlockState(legPos).getMaterial().equals(Material.AIR)) {
                                //检测头部坐标方块是否为空气
                                BlockPos headPos = new BlockPos(x, y, z);
                                if (chunk.getBlockState(headPos).getMaterial().equals(Material.AIR)) {
                                    if(m==0) {//传送点头顶没有方块。
                                        entity.stopRiding();//不能骑乘
                                        entity.requestTeleport(x, y, z);
                                        entity.fallDistance = 0.0F;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    //spread

}
