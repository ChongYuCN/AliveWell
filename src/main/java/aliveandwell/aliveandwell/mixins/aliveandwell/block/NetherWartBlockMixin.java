package aliveandwell.aliveandwell.mixins.aliveandwell.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.NetherWartBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetherWartBlock.class)
public class NetherWartBlockMixin {
    @Inject(at=@At("HEAD"), method="randomTick", cancellable = true)
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, net.minecraft.util.math.random.Random random, CallbackInfo ci) {
        if (random.nextInt((int)(5 * (2.0 - world.getBiome(pos).value().getTemperature())) + 5) != 0) {
            ci.cancel();
        }
    }
}
