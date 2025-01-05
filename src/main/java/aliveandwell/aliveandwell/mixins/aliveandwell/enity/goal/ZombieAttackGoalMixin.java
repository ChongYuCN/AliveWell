package aliveandwell.aliveandwell.mixins.aliveandwell.enity.goal;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.ZombieAttackGoal;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ZombieAttackGoal.class)
public abstract class ZombieAttackGoalMixin extends MeleeAttackGoal {
    private long lastUpdateTime;

    public ZombieAttackGoalMixin(PathAwareEntity mob, double speed, boolean pauseWhenMobIdle) {
        super(mob, speed, pauseWhenMobIdle);
    }

//    @Override
//    public boolean canStart() {
//        long l = this.mob.world.getTime();
//        if (l - this.lastUpdateTime < 20L) {
//            return false;
//        }
//        this.lastUpdateTime = l;
//        LivingEntity target = this.mob.getTarget();
////        if(target != null){
////            if(target.distanceTo(this.mob) <= this.getSquaredMaxAttackDistance(target)){
////                if(!target.canSee(this.mob)){
////                    return false;
////                }
////            }
////        }
//
//        if(target != null){
//            if (!target.isAlive()) {
//                return false;
//            }
//            this.path = this.mob.getNavigation().findPathTo(target, 0);
//            if (this.path != null) {
//                return true;
//            }
//            Vec3d vec3d = new Vec3d(this.mob.getX(), this.mob.getEyeY()-1.14d, this.mob.getZ());
//            Vec3d vec3d2 = new Vec3d(target.getX(), target.getEyeY(), target.getZ());
//            if(this.getSquaredMaxAttackDistance(target) >= this.mob.squaredDistanceTo(target.getX(), target.getY(), target.getZ())){
//                if(this.mob.canSee(target) ){
//                    return true;
//                }
////                else return this.mob.world.raycast(new RaycastContext(vec3d, vec3d2, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, this.mob)).getType() == HitResult.Type.MISS;
//            }
//        }
//        return false;
//    }

    protected void attack(LivingEntity target, double squaredDistance) {
        double d = this.getSquaredMaxAttackDistance(target);
        Vec3d vec3d = new Vec3d(this.mob.getX(), this.mob.getEyeY()-1.14d, this.mob.getZ());
        Vec3d vec3d2 = new Vec3d(target.getX(), target.getEyeY(), target.getZ());
        boolean b =this.mob.world.raycast(new RaycastContext(vec3d, vec3d2, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, this.mob)).getType() == HitResult.Type.MISS;
        if (squaredDistance <= d && this.cooldown <= 0 && (this.mob.canSee(target) || b)) {
            this.resetCooldown();
            this.mob.swingHand(Hand.MAIN_HAND);
            this.mob.tryAttack(target);
        }
    }

    //4.1f
    protected double getSquaredMaxAttackDistance(LivingEntity entity) {
        return 4.065f + entity.getWidth();
    }
}
