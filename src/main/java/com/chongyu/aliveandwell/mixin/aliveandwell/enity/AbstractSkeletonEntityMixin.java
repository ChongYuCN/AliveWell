package com.chongyu.aliveandwell.mixin.aliveandwell.enity;

import com.chongyu.aliveandwell.AliveAndWellMain;
import com.chongyu.aliveandwell.util.SkeletonEquipDiamondUtil;
import com.chongyu.aliveandwell.util.SkeletonEquipIronUtil;
import com.chongyu.aliveandwell.util.SkeletonEquipNetherUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.BowAttackGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Difficulty;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractSkeletonEntity.class)
public abstract class AbstractSkeletonEntityMixin extends HostileEntity implements RangedAttackMob {
    @Final
    @Mutable
    @Shadow
    private BowAttackGoal<AbstractSkeletonEntity> bowAttackGoal;
    @Final
    @Shadow
    private final MeleeAttackGoal meleeAttackGoal = new MeleeAttackGoal(this, 1.2, false){
        @Override
        public void stop() {
            super.stop();
            AbstractSkeletonEntityMixin.this.setAttacking(false);
        }

        @Override
        public void start() {
            super.start();
            AbstractSkeletonEntityMixin.this.setAttacking(true);
        }

        protected void attack(LivingEntity target, double squaredDistance) {
            double d = this.getSquaredMaxAttackDistance(target);
            if (squaredDistance <= d && this.cooldown <= 0 && (this.mob.canSee(target) || canSeeLeggings(target))) {
                this.resetCooldown();
                this.mob.swingHand(Hand.MAIN_HAND);
                this.mob.tryAttack(target);
            }
        }

        public boolean canSeeLeggings(Entity target) {
            if (target.getWorld() != this.mob.getWorld()) {
                return false;
            }
            Vec3d vec3d = new Vec3d(this.mob.getX(), this.mob.getEyeY()-1.14d, this.mob.getZ());
            Vec3d vec3d2 = new Vec3d(target.getX(), target.getEyeY(), target.getZ());
            if (vec3d2.distanceTo(vec3d) > 128.0) {
                return false;
            }
            return this.mob.getWorld().raycast(new RaycastContext(vec3d, vec3d2, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, this.mob)).getType() == HitResult.Type.MISS;
        }
        protected double getSquaredMaxAttackDistance(LivingEntity entity) {
            return 4.1f + entity.getWidth();
        }
    };

    protected AbstractSkeletonEntityMixin(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(at=@At("HEAD"), method="initGoals")
    public void initGoals(CallbackInfo info) {
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 48.0F));
    }

    @Inject(at=@At("HEAD"), method="createAbstractSkeletonAttributes",cancellable = true)
    private static void createAbstractSkeletonAttributes(
            CallbackInfoReturnable<DefaultAttributeContainer.Builder> info
    ) {
        info.setReturnValue(HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.26D)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 6).add(EntityAttributes.ZOMBIE_SPAWN_REINFORCEMENTS,4));
    }

    @Overwrite
    public void updateAttackType() {
        if (this.getWorld() != null && !this.getWorld().isClient) {
            ItemStack itemStack = this.getStackInHand(ProjectileUtil.getHandPossiblyHolding(this, Items.BOW));
            if (itemStack.isOf(Items.BOW)) {
                this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).setBaseValue(0.25D);
            } else {
                this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).setBaseValue(0.25D);
            }
        }

        if (this.getWorld() == null || this.getWorld().isClient) {
            return;
        }
        this.goalSelector.remove(this.meleeAttackGoal);
        this.goalSelector.remove(this.bowAttackGoal);
        ItemStack itemStack = this.getStackInHand(ProjectileUtil.getHandPossiblyHolding(this, Items.BOW));
        if (itemStack.isOf(Items.BOW)) {
            int i = 3*20;
            if (this.getWorld().getDifficulty() != Difficulty.HARD) {
                this.getEntityWorld().getServer().sendMessage(Text.translatable("11111111111111"));
                i = 4*20;
            }
            this.bowAttackGoal.setAttackInterval(i);
            this.goalSelector.add(4, this.bowAttackGoal);
        } else {
            this.goalSelector.add(4, this.meleeAttackGoal);
        }
    }

//    @Inject(at=@At("HEAD"),method="updateAttackType")
//    public void updateAttackType(CallbackInfo info) {
//        if (this.getWorld() != null && !this.getWorld().isClient) {
//            ItemStack itemStack = this.getStackInHand(ProjectileUtil.getHandPossiblyHolding(this, Items.BOW));
//            if (itemStack.isOf(Items.BOW)) {
//                this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).setBaseValue(0.25D);
//            } else {
//                this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).setBaseValue(0.25D);
//            }
//        }
//    }
//
//    @Inject(at=@At("TAIL"),method="updateAttackType")
//    public void updateAttackType1(CallbackInfo info) {
//        if (this.getWorld() != null && !this.getWorld().isClient) {
//            this.goalSelector.remove(this.meleeAttackGoal);
//            this.goalSelector.remove(this.bowAttackGoal);
//            ItemStack itemStack = this.getStackInHand(ProjectileUtil.getHandPossiblyHolding(this, Items.BOW));
//
//            if (itemStack.isOf(Items.BOW)) {
//                int i = 40;
//                if (this.getWorld().getDifficulty() != Difficulty.HARD) {
//                    i = 10*20;
//                }
//                this.bowAttackGoal.setAttackInterval(i);
//                this.goalSelector.add(4, this.bowAttackGoal);
//            } else {
//                this.goalSelector.add(4, this.meleeAttackGoal);
//            }
//        }
//    }

    /**
     * @author
     * @reason
     */
    @Overwrite
    public void attack(LivingEntity target, float pullProgress) {
        ItemStack itemStack = this.getProjectileType(this.getStackInHand(ProjectileUtil.getHandPossiblyHolding(this, Items.BOW)));
        float m = (float) AliveAndWellMain.day /100;
        if(m>=1.5f){
            m = 1.5f;
        }

        PersistentProjectileEntity persistentProjectileEntity = this.createArrowProjectile(itemStack, m);
        double d = target.getX() - this.getX();
        double e = target.getBodyY(0.3333333333333333) - persistentProjectileEntity.getY();
        double f = target.getZ() - this.getZ();
        double g = Math.sqrt(d * d + f * f);
        persistentProjectileEntity.setVelocity(d, e + g * 0.20000000298023224, f, 1.6F, (float)(14 - this.getWorld().getDifficulty().getId() * 4));
        this.playSound(SoundEvents.ENTITY_SKELETON_SHOOT, 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
        int i = AliveAndWellMain.day<=48 ? 0 : 1+(int)(AliveAndWellMain.day/40) ;
        if(i >= 5){
            i=5;
        }
        persistentProjectileEntity.setPunch(i);//++++++++++
        this.getWorld().spawnEntity(persistentProjectileEntity);
    }
    @Shadow
    protected PersistentProjectileEntity createArrowProjectile(ItemStack arrow, float damageModifier) {
        return ProjectileUtil.createArrowProjectile(this, arrow, damageModifier);
    }
    /**
     * @author
     * @reason
     */
    @Overwrite
    public void initEquipment(Random random, LocalDifficulty localDifficulty) {
        if( AliveAndWellMain.day <= 16) {
            super.initEquipment(random, localDifficulty);
            this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
        }else if(AliveAndWellMain.day <= 32) {
            if (this.getWorld().getRegistryKey() != World.OVERWORLD) {
                SkeletonEquipIronUtil.equip((AbstractSkeletonEntity) (Object) this);
            }else {
                if(new java.util.Random().nextInt(10)+1 == 5){
                    SkeletonEquipIronUtil.equip3((AbstractSkeletonEntity) (Object) this);
                }else {
                    super.initEquipment(random, localDifficulty);
                    this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
                }

            }
        }else if(AliveAndWellMain.day < 72) {
            SkeletonEquipIronUtil.equip((AbstractSkeletonEntity)(Object)this);
        }else if( AliveAndWellMain.day <= 128) {
            if (this.getWorld().getRegistryKey() == World.OVERWORLD) {
                SkeletonEquipIronUtil.equip((AbstractSkeletonEntity) (Object) this);
            } else {
                SkeletonEquipDiamondUtil.equip((AbstractSkeletonEntity) (Object) this);
            }
        } else if (AliveAndWellMain.day <= 160) {
            if (this.getWorld().getRegistryKey() == World.OVERWORLD) {
                SkeletonEquipIronUtil.equip2((AbstractSkeletonEntity) (Object) this);
            } else {
                SkeletonEquipDiamondUtil.equip((AbstractSkeletonEntity) (Object) this);
            }
        }else if (AliveAndWellMain.day <= 196){
            SkeletonEquipNetherUtil.equip1((AbstractSkeletonEntity) (Object) this);
        }else {
            SkeletonEquipNetherUtil.equip2((AbstractSkeletonEntity) (Object) this);
        }
    }
}
