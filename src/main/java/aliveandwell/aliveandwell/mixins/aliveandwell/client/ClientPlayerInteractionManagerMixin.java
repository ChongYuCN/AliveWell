package aliveandwell.aliveandwell.mixins.aliveandwell.client;

import aliveandwell.aliveandwell.util.ReachDistance;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.world.GameMode;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerInteractionManager.class)
public class ClientPlayerInteractionManagerMixin {
    @Shadow
    private GameMode gameMode = GameMode.DEFAULT;

    @Mutable
    @Final
    @Shadow
    private final MinecraftClient client;

    public ClientPlayerInteractionManagerMixin(MinecraftClient client) {
        this.client = client;
    }

    @Inject(at = @At("HEAD"), method = "getReachDistance", cancellable = true)
    public void getReachDistance(CallbackInfoReturnable<Float> cir) {
        cir.setReturnValue(ReachDistance.getReachDistance(client,gameMode));
    }
}
