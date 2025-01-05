package aliveandwell.aliveandwell.goal;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.SquidEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class SwimGoal extends Goal {
    private final SquidEntity squid;

    public SwimGoal(SquidEntity squid) {
        this.squid = squid;
    }

    public boolean canStart() {

        return true;
    }

    public boolean shouldContinue() {
        return true;
    }

    public void tick() {
        int i = this.squid.getDespawnCounter();
        Entity target = squid.getTarget();

        if (target != null) {
            if (target.getVehicle() instanceof BoatEntity) {
                target = target.getVehicle();
            }
            else if (target.isInsideWaterOrBubbleColumn() == false) {
                target = null;
            }
        }

        if (target != null) {

            if (target.distanceTo(squid) <= 2.0f) {
                target.setVelocity(target.getVelocity().x, target.getVelocity().y - 0.05f, target.getVelocity().z);
                if (target instanceof LivingEntity) {
                    LivingEntity living = (LivingEntity)target;
                    if (!living.hasStatusEffect(StatusEffects.SLOWNESS)) {
                        living.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 6 * 20, 2));
                        living.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON,8*20,0));
                        if(living.hasStatusEffect(StatusEffects.POISON) && living.getHealth()<=1.0f){
                            living.damage(DamageSource.GENERIC,1.0f);
                        }
                    }
                }
                if (target instanceof BoatEntity) {
                    BoatEntity boat = (BoatEntity)target;
                    boat.onBubbleColumnCollision(true);
                    boat.damage(DamageSource.FALL, 999);
                }
            }
        }
        if (i > 100) {
            this.squid.setSwimmingVector(0.0F, 0.0F, 0.0F);
        } else if (this.squid.getRandom().nextInt(50) == 0
                || !this.squid.hasSwimmingVector()) {

            //target entities in water


            if (target != null) {
                Vec3d vec3d = new Vec3d(this.squid.getX() - target.getX(),
                        this.squid.getY() - target.getY(), this.squid.getZ() - target.getZ());
                BlockState blockState = this.squid.world.getBlockState(new BlockPos(this.squid.getX() + vec3d.x,
                        this.squid.getY() + vec3d.y, this.squid.getZ() + vec3d.z));
                FluidState fluidState = this.squid.world.getFluidState(new BlockPos(this.squid.getX() + vec3d.x,
                        this.squid.getY() + vec3d.y, this.squid.getZ() + vec3d.z));
                if (fluidState.isIn(FluidTags.WATER) || blockState.isAir()) {
                    double d = vec3d.length();
                    if (d > 0.0D) {
                        vec3d.normalize();
                        float f = 3.0F;
                        if (d > 5.0D)
                            f = (float) (f - (d - 5.0D) / 5.0D);
                        if (f > 0.0F)
                            vec3d = vec3d.multiply(f);
                    }
                    if (blockState.isAir())
                        vec3d = vec3d.subtract(0.0D, vec3d.y, 0.0D);
                    this.squid.setSwimmingVector((float) -vec3d.x / 40.0F, (float) -vec3d.y / 40.0F,
                            (float) -vec3d.z / 40.0F);
                }
            } else {
                float f = this.squid.getRandom().nextFloat() * 6.2831855F;
                float g = MathHelper.cos(f) * 0.2F;
                float h = -0.1F + this.squid.getRandom().nextFloat() * 0.2F;
                float j = MathHelper.sin(f) * 0.2F;
                this.squid.setSwimmingVector(g, h, j);
            }
        }
    }
}