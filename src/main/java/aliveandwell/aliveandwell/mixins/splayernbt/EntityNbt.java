package aliveandwell.aliveandwell.mixins.splayernbt;

import aliveandwell.aliveandwell.accessor.IEntityNbt;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class EntityNbt implements IEntityNbt {
    private PlayerEntity ownerLighting;
    @Unique
    @Final
    private static final String NBT_BASE_KEY = "entity_nbt_aliveandwell";//总的nbt

    @Mutable
    @Unique
    private NbtCompound customNbt = new NbtCompound();//分支nbt

    //写入nbt
    @Inject(method = "writeNbt", at = @At("RETURN"))
    private void writeNbt(NbtCompound nbt, CallbackInfoReturnable<NbtCompound> cir) {
        nbt.put(NBT_BASE_KEY, customNbt);
    }

    //读取nbt
    @Inject(method = "readNbt", at = @At("RETURN"))
    private void readNbt(NbtCompound tag, CallbackInfo ci) {
        customNbt = tag.getCompound(NBT_BASE_KEY);
    }


    @Override
    public boolean entityNbt$contains(String key) {
        return customNbt.contains(key);
    }

    @Override
    public boolean entityNbt$getBlooean(String key) {
        return  customNbt.getBoolean(key);
    }

    @Override
    public void entityNbt$putBlooean(String key, boolean value) {
        customNbt.putBoolean(key,value);
    }

    @Override
    public void entityNbt$putString(String key, String value) {
        customNbt.putString(key,value);
    }

    @Override
    public String entityNbt$getString(String key) {
        return  customNbt.getString(key);
    }

    @Override
    public PlayerEntity entityNbt$getPlayerLighting(){
        return this.ownerLighting;
    }

    @Override
    public void entityNbt$setPlayerLighting(PlayerEntity player){
        this.ownerLighting = player;
    }
}
