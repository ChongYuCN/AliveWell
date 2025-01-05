package aliveandwell.aliveandwell.mixins.aliveandwell.client;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.encryption.PlayerPublicKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin extends PlayerEntity {

    @Final
    @Shadow
    protected MinecraftClient client;

    public ClientPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile gameProfile, @Nullable PlayerPublicKey publicKey) {
        super(world, pos, yaw, gameProfile, publicKey);
    }

    private int tick_hunger = 0; // controls the hunger bar in the GUI
    @Inject(at = @At("HEAD"), method = "tickMovement",cancellable = true)
    public void tickMovement(CallbackInfo info) {
        tick_hunger = this.hungerManager.getFoodLevel();
        if (tick_hunger > 0) {
            this.hungerManager.setFoodLevel(tick_hunger + 7);
        }
    }

    @Inject(at = @At("RETURN"), method = "tickMovement",cancellable = true)
    public void tickMovementReturn(CallbackInfo info) {
        this.hungerManager.setFoodLevel(tick_hunger);
    }

}

