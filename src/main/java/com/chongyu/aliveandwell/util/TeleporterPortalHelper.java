package com.chongyu.aliveandwell.util;

import com.chongyu.aliveandwell.dimensions.DimsRegistry;
import net.kyrptonaught.customportalapi.CustomPortalApiRegistry;
import net.kyrptonaught.customportalapi.CustomPortalsMod;
import net.kyrptonaught.customportalapi.interfaces.CustomTeleportingEntity;
import net.kyrptonaught.customportalapi.portal.PortalPlacer;
import net.kyrptonaught.customportalapi.portal.frame.PortalFrameTester;
import net.kyrptonaught.customportalapi.portal.linking.DimensionalBlockPos;
import net.kyrptonaught.customportalapi.util.CustomPortalHelper;
import net.kyrptonaught.customportalapi.util.CustomTeleporter;
import net.kyrptonaught.customportalapi.util.PortalLink;
import net.kyrptonaught.customportalapi.util.SHOULDTP;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ChunkTicketType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.*;
import net.minecraft.world.BlockLocating;
import net.minecraft.world.Heightmap;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.dimension.DimensionType;

import java.util.Optional;

public class TeleporterPortalHelper {
    public static void TPToDim(World world, Entity entity, Block portalBase, BlockPos portalPos) {
        PortalLink link = CustomPortalApiRegistry.getPortalLinkFromBase(portalBase);
        if (link == null) return;
        if (link.getBeforeTPEvent().execute(entity) == SHOULDTP.CANCEL_TP)
            return;

        RegistryKey<World> destKey = world.getRegistryKey();
        if(portalBase == Blocks.AMETHYST_BLOCK){//传送门框架是紫水晶块
            if(entity.getWorld().getRegistryKey() == DimsRegistry.UNDER_WORLD_KEY){
                destKey = World.OVERWORLD;
            }

            if(entity.getWorld().getRegistryKey() == World.OVERWORLD){
                destKey = DimsRegistry.UNDER_WORLD_KEY;
            }
        }else if(portalBase == Blocks.BUDDING_AMETHYST){//传送门框架是紫水晶母岩
            if(entity.getWorld().getRegistryKey() == DimsRegistry.UNDER_WORLD_KEY){
                destKey = World.NETHER;
            }
            if(entity.getWorld().getRegistryKey() == World.NETHER){
                destKey = DimsRegistry.UNDER_WORLD_KEY;
            }
        }else {
            RegistryKey<World> worldRegistryKey = destKey == CustomTeleporter.wrapRegistryKey(link.dimID) ? CustomTeleporter.wrapRegistryKey(link.returnDimID) : CustomTeleporter.wrapRegistryKey(link.dimID);
        }

        ServerWorld destination = ((ServerWorld) world).getServer().getWorld(destKey);
        if (destination == null) return;
        if (!entity.canUsePortals()) return;

        destination.getChunkManager().addTicket(ChunkTicketType.PORTAL, new ChunkPos(BlockPos.ofFloored(portalPos.getX() / destination.getDimension().coordinateScale(), portalPos.getY() / destination.getDimension().coordinateScale(), portalPos.getZ() / destination.getDimension().coordinateScale())), 3, BlockPos.ofFloored(portalPos.getX() / destination.getDimension().coordinateScale(), portalPos.getY() / destination.getDimension().coordinateScale(), portalPos.getZ() / destination.getDimension().coordinateScale()));
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
        double scaleFactor = DimensionType.getCoordinateScaleFactor(entity.getWorld().getDimension(), destination.getDimension());
        BlockPos blockPos3 = BlockPos.ofFloored(MathHelper.clamp(entity.getX() * scaleFactor, xMin, xMax), entity.getY(), MathHelper.clamp(entity.getZ() * scaleFactor, zMin, zMax));
        Optional<BlockLocating.Rectangle> portal = PortalPlacer.createDestinationPortal(destination, blockPos3, frameBlock, axis);
        if (portal.isPresent()) {
            PortalFrameTester portalFrameTester = CustomPortalApiRegistry.getPortalLinkFromBase(frameBlock.getBlock()).getFrameTester().createInstanceOfPortalFrameTester();

            CustomPortalsMod.portalLinkingStorage.createLink(portalFramePos.lowerLeft, entity.getWorld().getRegistryKey(), portal.get().lowerLeft, destination.getRegistryKey());
            return portalFrameTester.getTPTargetInPortal(portal.get(), axis, portalFrameTester.getEntityOffsetInPortal(portalFramePos, entity, axis), entity);
        }
        return idkWhereToPutYou(destination, entity, blockPos3);
    }


    protected static TeleportTarget idkWhereToPutYou(ServerWorld world, Entity entity, BlockPos pos) {
        CustomPortalsMod.logError("Unable to find tp location, forced to place on top of world");
        BlockPos destinationPos = world.getTopPosition(Heightmap.Type.WORLD_SURFACE, pos);
        return new TeleportTarget(new Vec3d(destinationPos.getX() + .5, destinationPos.getY(), destinationPos.getZ() + .5), entity.getVelocity(), entity.getYaw(), entity.getPitch());
    }

}
