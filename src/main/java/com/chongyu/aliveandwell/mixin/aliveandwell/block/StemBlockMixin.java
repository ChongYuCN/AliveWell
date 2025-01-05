package com.chongyu.aliveandwell.mixin.aliveandwell.block;

import net.minecraft.block.Fertilizable;
import net.minecraft.block.PlantBlock;
import net.minecraft.block.StemBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(StemBlock.class)
public abstract class StemBlockMixin extends PlantBlock implements Fertilizable {
    public StemBlockMixin(Settings settings) {
        super(settings);
    }

    @ModifyConstant(method = "randomTick", constant = @Constant(floatValue = 25.0f))
    public float randomTick(float constant) {
        return 55;
    }
}
