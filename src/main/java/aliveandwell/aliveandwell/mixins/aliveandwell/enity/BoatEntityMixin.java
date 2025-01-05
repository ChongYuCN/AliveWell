package aliveandwell.aliveandwell.mixins.aliveandwell.enity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.Items;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BoatEntity.class)
public abstract class BoatEntityMixin extends Entity {
    @Shadow
    private double fallVelocity;

    @Shadow
    private BoatEntity.Location location;

    @Shadow
    private static final TrackedData<Integer> BOAT_TYPE = DataTracker.registerData(BoatEntity.class, TrackedDataHandlerRegistry.INTEGER);

    public BoatEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(at = @At("HEAD"), method = "fall", cancellable = true)
    public void fall(double heightDifference, boolean onGround, BlockState state, BlockPos landedPosition, CallbackInfo ci) {
        this.fallVelocity = this.getVelocity().y;
        if (this.hasVehicle()) {
            return;
        }
        if (onGround) {
            if (this.fallDistance > 3.0f) {
//                if (this.location != BoatEntity.Location.ON_LAND) {
//                    this.onLanding();
//                    return;
//                }
                this.handleFallDamage(this.fallDistance, 1.0f, DamageSource.FALL);
                if (!this.world.isClient && !this.isRemoved()) {
                    this.kill();
                    if (this.world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
                        int i;
                        for (i = 0; i < 3; ++i) {
                            this.dropItem(this.getBoatType().getBaseBlock());
                        }
                        for (i = 0; i < 2; ++i) {
                            this.dropItem(Items.STICK);
                        }
                    }
                }
            }
            this.onLanding();
        } else if (!this.world.getFluidState(this.getBlockPos().down()).isIn(FluidTags.WATER) && heightDifference < 0.0) {
            this.fallDistance -= (float)heightDifference;
        }
        ci.cancel();
    }

    @Shadow
    public BoatEntity.Type getBoatType() {
        return BoatEntity.Type.getType(this.dataTracker.get(BOAT_TYPE));
    }

    @Inject(at=@At("HEAD"), method="canAddPassenger", cancellable = true)
    public void canAddPassenger(Entity passenger, CallbackInfoReturnable<Boolean> cir) {
        if(passenger instanceof HostileEntity){
            cir.setReturnValue(false);
        }
    }
}
