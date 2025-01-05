package com.chongyu.aliveandwell.block;

import net.minecraft.block.*;
import net.minecraft.block.enums.Instrument;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

import java.util.function.ToIntFunction;

public class HardGlass extends AbstractGlassBlock {
    public HardGlass(MapColor mapColor) {
        super(AbstractBlock.Settings.create().mapColor(mapColor).instrument(Instrument.HAT).requiresTool().strength(50.0f, 1200.0f).sounds(BlockSoundGroup.GLASS).nonOpaque().allowsSpawning(Blocks::never).solidBlock(Blocks::never).suffocates(Blocks::never).blockVision(Blocks::never).luminance(createLightLevelFromBlockState2()));
    }

    private static ToIntFunction<BlockState> createLightLevelFromBlockState2() {
        return (blockState) -> 10;
    }
}
