package com.chongyu.aliveandwell.mixin.aliveandwell.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.Fertilizable;
import net.minecraft.block.PlantBlock;
import net.minecraft.block.SaplingBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(SaplingBlock.class)
public abstract class SaplingBlockMixin extends PlantBlock implements Fertilizable {
    @Unique
    private int time;

    public SaplingBlockMixin(Settings settings) {
        super(settings);
    }

    @Overwrite
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        time++;
        if(time >= 5){
            if (world.getLightLevel(pos.up()) >= 9 && random.nextInt(4) == 0) {
                this.generate(world, pos, state, random);
            }
            time=0;
        }

    }

    @Shadow
    public void generate(ServerWorld world, BlockPos pos, BlockState state, Random random) {
    }
}
