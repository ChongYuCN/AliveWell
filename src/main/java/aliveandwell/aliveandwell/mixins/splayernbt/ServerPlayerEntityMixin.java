package aliveandwell.aliveandwell.mixins.splayernbt;

import aliveandwell.aliveandwell.accessor.SPlayerAccessorNbt;
import com.mojang.authlib.GameProfile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.encryption.PlayerPublicKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends PlayerEntity implements SPlayerAccessorNbt {
    @Unique
    private int invulnerableTime;
    @Unique
    private boolean invulnerableFlag;

    @Unique
    @Final
    private static final String NBT_BASE_KEY = "serverplayerentity_nbt_aliveandwell";//总的nbt

    @Mutable
    @Unique
    private NbtCompound customNbt = new NbtCompound();//分支nbt


    public ServerPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile gameProfile, @Nullable PlayerPublicKey publicKey) {
        super(world, pos, yaw, gameProfile, publicKey);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(CallbackInfo ci) {
        if(invulnerableFlag){
            ++invulnerableTime;
            if(this.invulnerableTime <= 6*20 ){
                this.setInvulnerable(true);
            }else {
                this.setInvulnerable(false);
                this.invulnerableTime = 0;
                this.invulnerableFlag =false;
            }
        }

    }

    //写入nbt
    @Inject(method = "writeCustomDataToNbt", at = @At("RETURN"))
    private void writeToNbt(NbtCompound nbt, CallbackInfo ci) {
        nbt.put(NBT_BASE_KEY, customNbt);
    }

    //读取nbt
    @Inject(method = "readCustomDataFromNbt", at = @At("RETURN"))
    private void readFromNbt(NbtCompound tag, CallbackInfo ci) {
        customNbt = tag.getCompound(NBT_BASE_KEY);
    }

    @Override
    public boolean splayerNbt$contains(String key) {
        return customNbt.contains(key);
    }

    @Override
    public long splayerNbt$getLong(String key) {
        return  customNbt.getLong(key);
    }

    @Override
    public void splayerNbt$putLong(String key, long value) {
        customNbt.putLong(key,value);
    }

    @Override
    public void splayerNbt$removeString(String key) {
        customNbt.remove(key);
    }

    @Override
    public void splayerNbt$invulnerableFlag(boolean fllag) {
        this.invulnerableFlag = fllag;
    }
}
