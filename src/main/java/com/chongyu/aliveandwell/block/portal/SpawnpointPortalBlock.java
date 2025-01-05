package com.chongyu.aliveandwell.block.portal;

import com.chongyu.aliveandwell.util.TpUtil;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.kyrptonaught.customportalapi.CustomPortalBlock;
import net.kyrptonaught.customportalapi.interfaces.EntityInCustomPortal;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SpawnpointPortalBlock extends CustomPortalBlock {

    public SpawnpointPortalBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        EntityInCustomPortal entityInPortal = (EntityInCustomPortal) entity;
        entityInPortal.tickInPortal(pos.toImmutable());
        if (!entityInPortal.didTeleport()) {
            if (entityInPortal.getTimeInPortal() >= entity.getMaxNetherPortalTime()) {
                entityInPortal.setDidTP(true);
                if (!world.isClient){
                    TpUtil.backSpawnpoint(world,entity);
                }
            }
        }
    }
}
