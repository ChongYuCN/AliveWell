package com.chongyu.aliveandwell.mixin.aliveandwell.block;

import com.chongyu.aliveandwell.block.FallingBlockHelper;
import net.minecraft.block.*;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.SilverfishEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collections;
import java.util.List;
import java.util.Random;

@Mixin(AbstractBlock.class)
public abstract class AbstractBlockMixin {

    @Shadow @Deprecated public abstract BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos);

    @Inject(at = @At("HEAD"), method = "scheduledTick")
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, net.minecraft.util.math.random.Random random, CallbackInfo ci) {
        if (!world.isClient()) {
            if ((Block)(Object)this == Blocks.DIRT || (Block)(Object)this == Blocks.COARSE_DIRT || (Block)(Object)this == Blocks.ROOTED_DIRT || (Block)(Object)this == Blocks.SOUL_SAND || (Block)(Object)this == Blocks.SOUL_SOIL || (Block)(Object)this == Blocks.HAY_BLOCK) {
                if (!(world.getBlockState(pos.down()).isSolidBlock(world, pos.down()))) {
                    FallingBlockHelper.tryToFall(world, pos);
                }
            }
        }
    }

//    @Inject(at = @At("HEAD"), method = "getStateForNeighborUpdate", cancellable = true)
//    public void getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos, CallbackInfoReturnable<BlockState> cir) {
//        if (!world.isClient()) {
//            if((Block)(Object)this == Blocks.STONE){
//                SilverfishEntity silverfishEntity = EntityType.SILVERFISH.create(world.getServer().getWorld(World.OVERWORLD));
//                silverfishEntity.refreshPositionAndAngles((double)pos.getX() + 0.5, pos.getY(), (double)pos.getZ() + 0.5, 0.0f, 0.0f);
//                world.spawnEntity(silverfishEntity);
//                silverfishEntity.playSpawnEffects();
//            }
//        }
//
//    }

    @Inject(at = @At("HEAD"), method = "getDroppedStacks", cancellable = true)
    public void getDroppedStacks(BlockState state, LootContextParameterSet.Builder builder, CallbackInfoReturnable<List<ItemStack>> cir) {
        String name = Registries.BLOCK.getId((Block)(Object)this).toString();
//        if(name.contains("modern_industrialization:") && name.contains("lignite_coal")){
//            cir.setReturnValue(Collections.emptyList());
//        }

        if(state.getBlock() instanceof CropBlock cropBlock){
            if(cropBlock.getAge(state) < cropBlock.getMaxAge()){
                cir.setReturnValue(Collections.emptyList());
            }
        }
    }

    @Inject(at = @At("HEAD"), method = "onStacksDropped")
    public void onStacksDropped(BlockState state, ServerWorld world, BlockPos pos, ItemStack stack, boolean dropExperience, CallbackInfo ci) {
        if (!world.isClient()) {
            if((Block)(Object)this == Blocks.STONE || (Block)(Object)this == Blocks.DEEPSLATE
                    || (Block)(Object)this == Blocks.GRANITE || (Block)(Object)this == Blocks.DIORITE
                    || (Block)(Object)this == Blocks.ANDESITE || (Block)(Object)this == Blocks.CALCITE
                    || (Block)(Object)this == Blocks.TUFF || (Block)(Object)this instanceof ExperienceDroppingBlock){
                SilverfishEntity silverfishEntity = EntityType.SILVERFISH.create(world);
                Block blockDown = world.getBlockState(pos.down()).getBlock();
                Block blockUP = world.getBlockState(pos.up()).getBlock();
                Block blockEast = world.getBlockState(pos.east()).getBlock();
                Block blockWest = world.getBlockState(pos.west()).getBlock();
                Block blockSouth = world.getBlockState(pos.south()).getBlock();
                Block blockNorth = world.getBlockState(pos.north()).getBlock();
                if(blockDown == Blocks.STONE || blockDown == Blocks.DEEPSLATE
                        || blockDown == Blocks.GRANITE || blockDown == Blocks.DIORITE
                        || blockDown == Blocks.ANDESITE || blockDown == Blocks.CALCITE
                        || blockDown == Blocks.TUFF || blockDown instanceof ExperienceDroppingBlock
                ){//下方刷出蠹虫
                    int random ;

                    if(world.getRegistryKey().getValue().toString().contains("twilightforest")){
                        random = new Random().nextInt(30);
                    }else {
                        random = new Random().nextInt(1000);
                    }

                    if(random <= 5){
                        spawnSilverFish(world,pos.down(),random);
                    }
                }

                if(blockUP == Blocks.STONE || blockUP == Blocks.DEEPSLATE
                        || blockUP == Blocks.GRANITE || blockUP == Blocks.DIORITE
                        || blockUP == Blocks.ANDESITE || blockUP == Blocks.CALCITE
                        || blockUP == Blocks.TUFF || blockUP instanceof ExperienceDroppingBlock
                ){//下方刷出蠹虫
                    int random ;

                    if(world.getRegistryKey().getValue().toString().contains("twilightforest")){
                        random = new Random().nextInt(30);
                    }else {
                        random = new Random().nextInt(1000);
                    }

                    if(random <= 5){
                        spawnSilverFish(world,pos.up(),random);
                    }
                }
                if(blockEast == Blocks.STONE || blockEast == Blocks.DEEPSLATE
                        || blockEast == Blocks.GRANITE || blockEast == Blocks.DIORITE
                        || blockEast == Blocks.ANDESITE || blockEast == Blocks.CALCITE
                        || blockEast == Blocks.TUFF || blockEast instanceof ExperienceDroppingBlock
                ){//下方刷出蠹虫
                    int random ;

                    if(world.getRegistryKey().getValue().toString().contains("twilightforest")){
                        random = new Random().nextInt(30);
                    }else {
                        random = new Random().nextInt(1000);
                    }

                    if(random <= 5){
                        spawnSilverFish(world,pos.east(),random);
                    }
                }
                if(blockWest == Blocks.STONE || blockWest == Blocks.DEEPSLATE
                        || blockWest == Blocks.GRANITE || blockWest == Blocks.DIORITE
                        || blockWest == Blocks.ANDESITE || blockWest == Blocks.CALCITE
                        || blockWest == Blocks.TUFF || blockWest instanceof ExperienceDroppingBlock
                ){//下方刷出蠹虫
                    int random ;

                    if(world.getRegistryKey().getValue().toString().contains("twilightforest")){
                        random = new Random().nextInt(30);
                    }else {
                        random = new Random().nextInt(1000);
                    }

                    if(random <= 5){
                        spawnSilverFish(world,pos.west(),random);
                    }
                }
                if(blockSouth == Blocks.STONE || blockSouth == Blocks.DEEPSLATE
                        || blockSouth == Blocks.GRANITE || blockSouth == Blocks.DIORITE
                        || blockSouth == Blocks.ANDESITE || blockSouth == Blocks.CALCITE
                        || blockSouth == Blocks.TUFF || blockSouth instanceof ExperienceDroppingBlock
                ){//下方刷出蠹虫
                    int random ;

                    if(world.getRegistryKey().getValue().toString().contains("twilightforest")){
                        random = new Random().nextInt(30);
                    }else {
//                        random = new Random().nextInt(1000);
                        random = new Random().nextInt(1000);
                    }

                    if(random <= 5){
                        spawnSilverFish(world,pos.south(),random);
                    }
                }
                if(blockNorth == Blocks.STONE || blockNorth == Blocks.DEEPSLATE
                        || blockNorth == Blocks.GRANITE || blockNorth == Blocks.DIORITE
                        || blockNorth == Blocks.ANDESITE || blockNorth == Blocks.CALCITE
                        || blockNorth == Blocks.TUFF || blockNorth instanceof ExperienceDroppingBlock
                ){//下方刷出蠹虫
                    int random ;

                    if(world.getRegistryKey().getValue().toString().contains("twilightforest")){
                        random = new Random().nextInt(30);
                    }else {
                        random = new Random().nextInt(1000);
                    }

                    if(random <= 5){
                        spawnSilverFish(world,pos.north(),random);
                    }
                }
            }
        }
    }

    @Unique
    private void spawnSilverFish(ServerWorld world, BlockPos pos, int count){
        SilverfishEntity silverfishEntity = EntityType.SILVERFISH.create(world);
        world.breakBlock(pos, true);
        assert silverfishEntity != null;
        silverfishEntity.refreshPositionAndAngles((double)pos.getX() + 0.5, pos.getY(), (double)pos.getZ() + 0.5, 0.0f, 0.0f);
        if(count >= 3){
            count = 3;
        }
        if(count==1){
            world.spawnEntity(silverfishEntity);
        }else if(count == 2){
            world.spawnEntity(silverfishEntity);
            world.spawnEntity(silverfishEntity);
        }else if(count == 3){
            world.spawnEntity(silverfishEntity);
            world.spawnEntity(silverfishEntity);
            world.spawnEntity(silverfishEntity);
        }
        silverfishEntity.playSpawnEffects();
    }
}
