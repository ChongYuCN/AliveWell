package com.chongyu.aliveandwell.mixin.aliveandwell.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.NetherPortalBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(NetherPortalBlock.class)
public class NetherPortalBlockMixin {

    @Overwrite
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if(entity instanceof PlayerEntity){
            PlayerEntity player = (PlayerEntity)(Object)entity;
            player.sendMessage(Text.translatable("aliveandwell.netherportal.collision"));
        }else if(!entity.hasVehicle() && !entity.hasPassengers() && entity.canUsePortals()) {
            entity.setInNetherPortal(pos);
        }
    }
}
