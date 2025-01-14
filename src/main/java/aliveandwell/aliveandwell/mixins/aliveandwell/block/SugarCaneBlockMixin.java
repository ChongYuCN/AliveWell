package aliveandwell.aliveandwell.mixins.aliveandwell.block;

import aliveandwell.aliveandwell.AliveAndWellMain;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SugarCaneBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SugarCaneBlock.class)
public abstract class SugarCaneBlockMixin extends Block {
    @Final
    @Shadow public static final IntProperty AGE = Properties.AGE_15;
    public SugarCaneBlockMixin(Settings settings) {
        super(settings);
    }

    @Inject(at=@At("HEAD"), method="randomTick", cancellable = true)
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci) {
        if (random.nextInt((int)(13 * (2.0 - world.getBiome(pos).value().getTemperature())) + 13) != 0) {
            ci.cancel();
        }
    }

//    @Overwrite
//    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
//        if (world.isAir(pos.up())) {
//            int i = 1;
//            while (world.getBlockState(pos.down(i)).isOf(this)) {
//                ++i;
//            }
//            if (i < 3) {
//                int j = state.get(AGE);
//                if (j == 15) {
//                    world.setBlockState(pos.up(), this.getDefaultState());
//                    world.setBlockState(pos, (BlockState)state.with(AGE, 0), Block.NO_REDRAW);
//                } else {
//                    if(AliveAndWellMain.day % 3 == 0 && new java.util.Random().nextInt(4) == 0){
//                        world.setBlockState(pos, (BlockState)state.with(AGE, j + 1), Block.NO_REDRAW);
//                    }
//                }
//            }
//        }
//    }
}
