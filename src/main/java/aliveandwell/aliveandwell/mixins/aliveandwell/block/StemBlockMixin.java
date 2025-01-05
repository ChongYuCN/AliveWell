package aliveandwell.aliveandwell.mixins.aliveandwell.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.Fertilizable;
import net.minecraft.block.PlantBlock;
import net.minecraft.block.StemBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(StemBlock.class)
public abstract class StemBlockMixin extends PlantBlock implements Fertilizable {
    public StemBlockMixin(Settings settings) {
        super(settings);
    }

//    @ModifyConstant(method = "randomTick", constant = @Constant(floatValue = 25.0f))
//    public float randomTick(float constant) {
//        return 55;
//    }

    @Inject(at=@At("HEAD"), method="randomTick", cancellable = true)
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, net.minecraft.util.math.random.Random random, CallbackInfo ci) {
        if (random.nextInt((int)(10 * (2.0 - world.getBiome(pos).value().getTemperature())) + 10) != 0) {
            ci.cancel();
        }
    }
}
