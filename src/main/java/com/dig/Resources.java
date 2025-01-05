package com.chongyu.aliveandwell.dig;

import net.minecraft.entity.Entity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

public class Resources {


    public static float getDistanceFromDeltas(double dx, double dy, double dz)
    {
        return MathHelper.sqrt((float)(dx * dx + dy * dy + dz * dz));
    }

    public static float lerp(float a, float b, float lerp)
    {
        return a + lerp * (b - a);
    }


    public static RaycastCollision getBlockCollisionForPhysicalReach(Vec3d start, Vec3d end, World world, Entity entity) {


        RaycastCollision rc = new RaycastCollision();
        BlockHitResult result = world.raycast(new RaycastContext(start, end, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, entity));

        if (result != null) {

            rc.block_hit_x = result.getBlockPos().getX();
            rc.block_hit_y = result.getBlockPos().getY();
            rc.block_hit_z = result.getBlockPos().getZ();
            rc.blockHit = world.getBlockState(result.getBlockPos()).getBlock();
        }

        return rc;
    }
}
