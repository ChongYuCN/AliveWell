package aliveandwell.aliveandwell.mixins.aliveandwell;

import aliveandwell.aliveandwell.AliveAndWellMain;
import aliveandwell.aliveandwell.data.SaveDatas;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerTask;
import net.minecraft.server.command.CommandOutput;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.thread.ReentrantThreadExecutor;
import net.minecraft.world.GameMode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(MinecraftServer.class)
public abstract class MinecraftServerMixin extends ReentrantThreadExecutor<ServerTask> implements CommandOutput, AutoCloseable {

    @Shadow public abstract ServerWorld getOverworld();

    public MinecraftServerMixin(String string) {
        super(string);
    }

    @Overwrite
    public GameMode getDefaultGameMode() {
        return GameMode.SURVIVAL;
    }


    @Inject(at = @At("HEAD"), method = "stop")
    public void stop(boolean bl, CallbackInfo ci) {
        SaveDatas serverState = SaveDatas.getServerState(Objects.requireNonNull((MinecraftServer) (Object)this));
        serverState.gameImp=AliveAndWellMain.day * 100 + 1800;
    }

}
