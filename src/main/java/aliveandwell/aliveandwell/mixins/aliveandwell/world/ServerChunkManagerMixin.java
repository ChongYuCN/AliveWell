package aliveandwell.aliveandwell.mixins.aliveandwell.world;

import net.minecraft.server.world.ServerChunkManager;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.WorldProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

@Mixin(ServerChunkManager.class)
public class ServerChunkManagerMixin {

    //暂时取消
//    @Inject(method = "tickChunks", at = @At(value = "TAIL"), locals = LocalCapture.CAPTURE_FAILHARD)
//    private void tickChunks(CallbackInfo ci, long l, long m, boolean bl, WorldProperties worldProperties, Profiler profiler, int i, boolean bl2, int j, SpawnHelper.Info info, List list) {
//        j = j * 2;//增加刷怪上限
//    }
}
