package com.chongyu.aliveandwell.mixin.aliveandwell.block;

import com.chongyu.aliveandwell.AliveAndWellMain;
import net.minecraft.block.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CropBlock.class)
public abstract class CropBlockMixin extends PlantBlock implements Fertilizable {
    public CropBlockMixin(Settings settings) {
        super(settings);
    }

    @Inject(at=@At("HEAD"),method="getAvailableMoisture",cancellable = true)
    private static void getAvailableMoisture(Block block, BlockView world, BlockPos pos, CallbackInfoReturnable<Float> ca) {
       float f = 1.0F;
       BlockPos blockPos = pos.down();

       for(int i = -1; i <= 1; ++i) {
           for(int j = -1; j <= 1; ++j) {
               float g = 0.0F;
               BlockState blockState = world.getBlockState(blockPos.add(i, 0, j));
               if (blockState.isOf(Blocks.FARMLAND)) {
                   g = 1.0F;
                   if ((Integer)blockState.get(FarmlandBlock.MOISTURE) > 0) {
                       g = 3.0F;
                   }
               }

               if (i != 0 || j != 0) {
                   g /= 4.0F;
               }

               f += g;
           }
       }

       BlockPos blockPos2 = pos.north();
       BlockPos blockPos3 = pos.south();
       BlockPos blockPos4 = pos.west();
       BlockPos blockPos5 = pos.east();
       boolean bl = world.getBlockState(blockPos4).isOf(block) || world.getBlockState(blockPos5).isOf(block);
       boolean bl2 = world.getBlockState(blockPos2).isOf(block) || world.getBlockState(blockPos3).isOf(block);
       if (bl && bl2) {
           f /= 2.0F;
       } else {
           boolean bl3 = world.getBlockState(blockPos4.north()).isOf(block) || world.getBlockState(blockPos5.north()).isOf(block) || world.getBlockState(blockPos5.south()).isOf(block) || world.getBlockState(blockPos4.south()).isOf(block);
           if (bl3) {
               f /= 2.0F;
           }
       }

       if(AliveAndWellMain.day % 6 == 0){
           ca.setReturnValue(f*0.5f);
       }else {
           ca.setReturnValue(f*0.2f);
       }
    }

}
