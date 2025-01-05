package aliveandwell.aliveandwell.mixins.aliveandwell.enity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.thrown.ThrownEntity;
import net.minecraft.text.Text;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ThrownEntity.class)
public abstract class ThrownEntityMixin extends ProjectileEntity {

    public ThrownEntityMixin(EntityType<? extends ProjectileEntity> entityType, World world) {
        super(entityType, world);
    }
    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity();

        String name = Registry.ENTITY_TYPE.getId(this.getType()).toString();
        if(name.contains("pswg:") && name.contains("_bolt")){
            entity.damage(DamageSource.thrownProjectile(this, this.getOwner()), 26);
        }

        if(name.contains("doom:") && (name.contains("bullets") || name.contains("shotgun_shells")
                || name.contains("rocket") || name.contains("energy_cells") || name.contains("bfg_cell"))){
            entity.damage(DamageSource.thrownProjectile(this, this.getOwner()), 8);
        }
    }
}
