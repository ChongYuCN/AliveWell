package aliveandwell.aliveandwell.mixins.aliveandwell.enity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.boss.dragon.EnderDragonFight;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.MiningToolItem;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EndCrystalEntity.class)
public abstract class EndCrystalEntityMixin extends Entity {

    public EndCrystalEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

//    @Overwrite
//    public boolean damage(DamageSource source, float amount) {
//        if(source.getAttacker() instanceof PlayerEntity){
//            PlayerEntity player = (PlayerEntity) source.getAttacker();
//            if(player.getMainHandStack().getItem() == Items.DIAMOND_PICKAXE || player.getMainHandStack().getItem() == Items.NETHERITE_PICKAXE){
//                if (!this.isRemoved() && !this.world.isClient) {
//                    this.remove(RemovalReason.KILLED);
//                    if (!source.isExplosive()) {
//                        this.world.createExplosion((Entity)null, this.getX(), this.getY(), this.getZ(), 6.0F, Explosion.DestructionType.DESTROY);
//                    }
//
//                    this.crystalDestroyed(source);
//                }
//                return true;
//            }
//        }
//        return false;
//    }

    @Inject(at = @At("HEAD"), method = "damage",cancellable = true)
    public void damage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> ca) {
        if(source.getAttacker() instanceof PlayerEntity){
            PlayerEntity player = (PlayerEntity) source.getAttacker();
            Item item = player.getMainHandStack().getItem();
            if(item instanceof MiningToolItem){
                MiningToolItem miningToolItem = (MiningToolItem) item;
                if(miningToolItem.getMaterial().getMiningLevel() >= 3){
                    if (!this.isRemoved() && !this.world.isClient) {
                        this.remove(RemovalReason.KILLED);
                        if (!source.isExplosive()) {
                            this.world.createExplosion((Entity)null, this.getX(), this.getY(), this.getZ(), 6.0F, Explosion.DestructionType.DESTROY);
                        }
                        this.crystalDestroyed(source);
                    }
                    ca.setReturnValue(true);
                }
            }
        }
        ca.setReturnValue(false);
    }

    @Shadow
    private void crystalDestroyed(DamageSource source) {
        if (this.world instanceof ServerWorld) {
            EnderDragonFight enderDragonFight = ((ServerWorld)this.world).getEnderDragonFight();
            if (enderDragonFight != null) {
                enderDragonFight.crystalDestroyed((EndCrystalEntity) (Object)this, source);
            }
        }

    }


}
