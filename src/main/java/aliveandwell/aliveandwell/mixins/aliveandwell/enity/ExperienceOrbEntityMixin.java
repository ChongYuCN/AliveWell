package aliveandwell.aliveandwell.mixins.aliveandwell.enity;

import aliveandwell.aliveandwell.util.ExperienceOrbUtil;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.TypeFilter;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.*;

@Mixin(ExperienceOrbEntity.class)
public abstract class ExperienceOrbEntityMixin extends Entity implements ExperienceOrbUtil {

    @Unique
    private static final TrackedData<Optional<UUID>> OWNER_UUID;

    @Shadow
    private PlayerEntity target;

    @Shadow
    private int pickingCount = 1;

    @Shadow
    private int orbAge;

    @Shadow
    private int amount;

    public ExperienceOrbEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ExperienceOrbEntity;move(Lnet/minecraft/entity/MovementType;Lnet/minecraft/util/math/Vec3d;)V"))
    public void tick(CallbackInfo ci) {
        if(this.target != null && this.getOwnerUuid() != null && !(this.getOwnerUuid().equals(this.target.getUuid()))){
//            System.out.println("111111111="+this.getOwnerUuid());
//            System.out.println("222222222="+this.target.getUuid());
            this.target = null;
        }

    }

    @Overwrite
    private void expensiveUpdate() {
        if (this.target == null || this.target.squaredDistanceTo(this) > 64.0) {
            this.target = this.world.getClosestPlayer(this, 8.0);
        }
        if (this.world instanceof ServerWorld) {
            List<ExperienceOrbEntity> list = this.world.getEntitiesByType(TypeFilter.instanceOf(ExperienceOrbEntity.class), this.getBoundingBox().expand(0.5), this::isMergeable);
            for (ExperienceOrbEntity experienceOrbEntity : list) {
                this.merge(experienceOrbEntity);
            }
        }
    }

//    @Unique
//    private static void aliveAndWell$spawn(ServerWorld world, Vec3d pos, int amount, @Nullable PlayerEntity ownerPlayer) {
//        while (amount > 0) {
//            int i = ExperienceOrbEntity.roundToOrbSize(amount);
//            amount -= i;
//
//            if (!ExperienceOrbEntity.wasMergedIntoExistingOrb(world, pos, i)) {
//                ExperienceOrbEntity experienceOrbEntity = new ExperienceOrbEntity(world, pos.getX(), pos.getY(), pos.getZ(), i);
//                if (ownerPlayer != null) {
//                    ((ExperienceOrbEntityMixin)(Object)experienceOrbEntity).setOwnerUuid(ownerPlayer.getUuid());
//                }
//
//                world.spawnEntity(experienceOrbEntity);
//                System.out.println("4444444444444444444444");
//            }
//        }
//    }

    @Overwrite
    private boolean isMergeable(ExperienceOrbEntity other) {
        return other != ((ExperienceOrbEntity)(Object)this) && ExperienceOrbEntity.isMergeable(other, this.getId(), this.amount) && Objects.equals(((ExperienceOrbEntityMixin)(Object)other).getOwnerUuid(), this.getOwnerUuid());
    }

    @Shadow
    private void merge(ExperienceOrbEntity other) {
        this.pickingCount += ((ExperienceOrbEntityAssessor)other).getPickingCount();
        this.orbAge = Math.min(this.orbAge, ((ExperienceOrbEntityAssessor)other).getOrbAge());
        other.discard();
    }


    @Override
    public void initDataTracker() {
        this.dataTracker.startTracking(OWNER_UUID, Optional.empty());
    }

    @Unique
    @Nullable
    public UUID getOwnerUuid() {
        return (UUID)((Optional)this.dataTracker.get(OWNER_UUID)).orElse((Object)null);
    }

    @Override
    public void setOwnerUuid(@Nullable UUID pUuid) {
        this.dataTracker.set(OWNER_UUID, Optional.ofNullable(pUuid));
    }

    @Unique
    public void setOwner(PlayerEntity pPlayer) {
        this.setOwnerUuid(pPlayer.getUuid());
    }

    @Inject(at = @At("TAIL"), method = "writeCustomDataToNbt")
    public void writeCustomDataToNbt(NbtCompound nbt, CallbackInfo ci) {
        if (this.getOwnerUuid() != null) {
            nbt.putUuid("Owner", this.getOwnerUuid());
        }
    }

    @Inject(at = @At("TAIL"), method = "readCustomDataFromNbt")
    public void readCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci) {
        UUID uuid = null;
        if (nbt.contains("Owner")) {
            uuid = nbt.getUuid("Owner");
        }

        if (uuid != null) {
            try {
                this.setOwnerUuid(uuid);
            } catch (Throwable var4) {
            }
        }
    }

    @Inject(at = @At("HEAD"), method = "onPlayerCollision", cancellable = true)
    public void onPlayerCollision(PlayerEntity player, CallbackInfo ci) {
        if (!this.world.isClient) {
            if (player.experiencePickUpDelay == 0) {
                if (this.getOwnerUuid() == null || this.getOwnerUuid().equals(player.getUuid())) {
                    player.experiencePickUpDelay = 2;
                    player.sendPickup(this, 1);
                    int i = this.repairPlayerGears(player, this.amount);
                    if (i > 0) {
                        player.addExperience(i);
                    }
                    --this.pickingCount;
                    if (this.pickingCount == 0) {
                        this.discard();
                    }
                }
            }
        }
        ci.cancel();
    }


    @Shadow
    private int repairPlayerGears(PlayerEntity player, int amount) {
        Map.Entry<EquipmentSlot, ItemStack> entry = EnchantmentHelper.chooseEquipmentWith(Enchantments.MENDING, player, ItemStack::isDamaged);
        if (entry != null) {
            ItemStack itemStack = entry.getValue();
            int i = Math.min(this.getMendingRepairAmount(this.amount), itemStack.getDamage());
            itemStack.setDamage(itemStack.getDamage() - i);
            int j = amount - this.getMendingRepairCost(i);
            if (j > 0) {
                return this.repairPlayerGears(player, j);
            }
            return 0;
        }
        return amount;
    }

    @Shadow
    private int getMendingRepairCost(int repairAmount) {
        return repairAmount / 2;
    }

    @Shadow
    private int getMendingRepairAmount(int experienceAmount) {
        return experienceAmount * 2;
    }

    static {
        OWNER_UUID = DataTracker.registerData(ExperienceOrbEntity.class, TrackedDataHandlerRegistry.OPTIONAL_UUID);
    }
}
