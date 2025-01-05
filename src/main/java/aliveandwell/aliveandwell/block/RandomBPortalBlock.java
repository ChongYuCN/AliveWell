package aliveandwell.aliveandwell.block;

import aliveandwell.aliveandwell.block.randompos.BaseAdamantiumRandomBlock;
import aliveandwell.aliveandwell.data.SaveDatas;
import aliveandwell.aliveandwell.block.randompos.RandomPos;
import aliveandwell.aliveandwell.registry.BlockInit;
import aliveandwell.aliveandwell.util.RandomTpUtil;
import net.kyrptonaught.customportalapi.CustomPortalBlock;
import net.kyrptonaught.customportalapi.interfaces.EntityInCustomPortal;
import net.kyrptonaught.customportalapi.util.CustomPortalHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.Objects;

public class RandomBPortalBlock extends CustomPortalBlock {
    public RandomBPortalBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        EntityInCustomPortal entityInPortal = (EntityInCustomPortal) entity;
        entityInPortal.tickInPortal(pos.toImmutable());

        if (!entityInPortal.didTeleport()) {
            if (entityInPortal.getTimeInPortal() >= entity.getMaxNetherPortalTime()) {
                entityInPortal.setDidTP(true);
                if (!world.isClient) {
                    if (entity instanceof ServerPlayerEntity player1) {
                        RandomPos randomPos;
                        BlockPos blockPos ;
                        SaveDatas serverState = SaveDatas.getServerState(Objects.requireNonNull(world.getServer()));

                        Direction.Axis axis = CustomPortalHelper.getAxisFrom(state);
                        //东西朝向：东
                        if (axis == Direction.Axis.X) {
                            //在最左边
                            if (world.getBlockState(pos.west()).getBlock() == BlockInit.FRAME_ADAMANTIUM
                                    && world.getBlockState(pos.up(3)).getBlock() == BlockInit.FRAME_ADAMANTIUM
                                    && world.getBlockState(pos.up(2)).getBlock() == this
                                    && world.getBlockState(pos.up(1)).getBlock() == this) {

                                Block block1 = world.getBlockState(pos.up(3).west()).getBlock();
                                Block block2 = world.getBlockState(pos.down().west()).getBlock();
                                Block block3 = world.getBlockState(pos.up(3).east(2)).getBlock();
                                Block block4 = world.getBlockState(pos.down().east(2)).getBlock();
//                                String name1 = block1.getTranslationKey();//block.aliveandwell.frame_mithril=============================
//                                String name1 = block1.getName().getString();//传送门框架（秘银）====================================
//                                player1.sendMessage(Text.of(name1));//======================================================
                                if(block1 instanceof BaseAdamantiumRandomBlock && block2 instanceof BaseAdamantiumRandomBlock && block3 instanceof BaseAdamantiumRandomBlock && block4 instanceof BaseAdamantiumRandomBlock){
                                    String name1 = block1.getName().getString();
                                    String name2 = block2.getName().getString();
                                    String name3 = block3.getName().getString();
                                    String name4 = block4.getName().getString();

                                    if(valid(name1,name2,name3,name4)){
                                        randomPos = new RandomPos(player1.world.getRegistryKey().getValue().toString(),world.getChunk(pos).getPos(), name1,name2,name3,name4);
                                        blockPos = serverState.getDestination(randomPos,world.getServer());
//                                player1.sendMessage(Text.of("========="+serverState.randomPosMap.toString()));//===================================================
//                                player1.sendMessage(Text.of("66"+serverState.randomPosMap.containsKey(randomPos)));//==============================================
//                                player1.sendMessage(Text.of("11"+player1.world.getRegistryKey().getValue().toString()));//aliveandwell:underworld_dim=================
                                        if(blockPos != null){
                                            player1.teleport((ServerWorld) world, blockPos.getX(),blockPos.getY(),blockPos.getZ(),0.0f,0.0f);
                                        }else {
                                            for (PlayerEntity player : world.getPlayers()){
                                                player.sendMessage(((MutableText)player1.getName()).append(Text.translatable("aliveandwell.randomportalB.through")).formatted(Formatting.RED));
                                            }
                                                RandomTpUtil.RandomTpB(world, entity);

                                            BlockPos pos1 = entity.getBlockPos();
                                            serverState.randomPosMap.put(randomPos,pos1);
//                                    player1.sendMessage(Text.of("****"+serverState.randomPosMap.toString()));//======================================================

                                            for (PlayerEntity player : world.getPlayers()){
                                                player.sendMessage(((MutableText)player1.getName()).append(Text.translatable("aliveandwell.randomportalB.pass")).formatted(Formatting.YELLOW));
                                            }
                                        }
                                    }else {
                                        player1.sendMessage(Text.translatable("aliveandewell.randomBPotal.wrongInfo2").formatted(Formatting.YELLOW));
                                    }
                                }else {
                                    player1.sendMessage(Text.translatable("aliveandewell.randomBPotal.wrongInfo1").formatted(Formatting.YELLOW));
                                }
                            }
                            //在最右下边
                            if (world.getBlockState(pos.east()).getBlock() == BlockInit.FRAME_ADAMANTIUM
                                    && world.getBlockState(pos.up(3)).getBlock() == BlockInit.FRAME_ADAMANTIUM
                                    && world.getBlockState(pos.up(2)).getBlock() == this
                                    && world.getBlockState(pos.up(1)).getBlock() == this) {
                                Block block1 = world.getBlockState(pos.up(3).west(2)).getBlock();
                                Block block2 = world.getBlockState(pos.down().west(2)).getBlock();
                                Block block3 = world.getBlockState(pos.up(3).east()).getBlock();
                                Block block4 = world.getBlockState(pos.down().east()).getBlock();

                                if(block1 instanceof BaseAdamantiumRandomBlock && block2 instanceof BaseAdamantiumRandomBlock && block3 instanceof BaseAdamantiumRandomBlock && block4 instanceof BaseAdamantiumRandomBlock){
                                    String name1 = block1.getName().getString();
                                    String name2 = block2.getName().getString();
                                    String name3 = block3.getName().getString();
                                    String name4 = block4.getName().getString();

                                    if(valid(name1,name2,name3,name4)){
                                        randomPos = new RandomPos(player1.world.getRegistryKey().getValue().toString(),world.getChunk(pos).getPos(), name1,name2,name3,name4);
                                        blockPos = serverState.getDestination(randomPos,world.getServer());
                                        if(blockPos != null){
                                            player1.teleport((ServerWorld) world, blockPos.getX(),blockPos.getY(),blockPos.getZ(),0.0f,0.0f);
                                        }else {
                                            for (PlayerEntity player : world.getPlayers()){
                                                player.sendMessage(((MutableText)player1.getName()).append(Text.translatable("aliveandwell.randomportalB.through")).formatted(Formatting.RED));
                                            }
                                                RandomTpUtil.RandomTpB(world, entity);
                                            BlockPos pos1 = entity.getBlockPos();
                                            serverState.randomPosMap.put(randomPos,pos1);
                                            for (PlayerEntity player : world.getPlayers()){
                                                player.sendMessage(((MutableText)player1.getName()).append(Text.translatable("aliveandwell.randomportalB.pass")).formatted(Formatting.YELLOW));
                                            }
                                        }
                                    }else {
                                        player1.sendMessage(Text.translatable("aliveandewell.randomBPotal.wrongInfo2").formatted(Formatting.YELLOW));
                                    }
                                }else {
                                    player1.sendMessage(Text.translatable("aliveandewell.randomBPotal.wrongInfo1").formatted(Formatting.YELLOW));
                                }
                            }
                            //在最左中间边
                            if (world.getBlockState(pos.west()).getBlock() == BlockInit.FRAME_ADAMANTIUM
                                    && world.getBlockState(pos.down(2)).getBlock() == BlockInit.FRAME_ADAMANTIUM
                                    && world.getBlockState(pos.down(1)).getBlock() == this
                                    && world.getBlockState(pos.up(1)).getBlock() == this) {

                                Block block1 = world.getBlockState(pos.up(2).west()).getBlock();
                                Block block2 = world.getBlockState(pos.down(2).west()).getBlock();
                                Block block3 = world.getBlockState(pos.up(2).east(2)).getBlock();
                                Block block4 = world.getBlockState(pos.down(2).east(2)).getBlock();
                                if(block1 instanceof BaseAdamantiumRandomBlock && block2 instanceof BaseAdamantiumRandomBlock && block3 instanceof BaseAdamantiumRandomBlock && block4 instanceof BaseAdamantiumRandomBlock){
                                    String name1 = block1.getName().getString();
                                    String name2 = block2.getName().getString();
                                    String name3 = block3.getName().getString();
                                    String name4 = block4.getName().getString();

                                    if(valid(name1,name2,name3,name4)){
                                        randomPos = new RandomPos(player1.world.getRegistryKey().getValue().toString(),world.getChunk(pos).getPos(), name1,name2,name3,name4);
                                        blockPos = serverState.getDestination(randomPos,world.getServer());
                                        if(blockPos != null){
                                            player1.teleport((ServerWorld) world, blockPos.getX(),blockPos.getY(),blockPos.getZ(),0.0f,0.0f);
                                        }else {
                                            for (PlayerEntity player : world.getPlayers()){
                                                player.sendMessage(((MutableText)player1.getName()).append(Text.translatable("aliveandwell.randomportalB.through")).formatted(Formatting.RED));
                                            }
                                                RandomTpUtil.RandomTpB(world, entity);
                                            BlockPos pos1 = entity.getBlockPos();
                                            serverState.randomPosMap.put(randomPos,pos1);
                                            for (PlayerEntity player : world.getPlayers()){
                                                player.sendMessage(((MutableText)player1.getName()).append(Text.translatable("aliveandwell.randomportalB.pass")).formatted(Formatting.YELLOW));
                                            }
                                        }
                                    }else {
                                        player1.sendMessage(Text.translatable("aliveandewell.randomBPotal.wrongInfo2").formatted(Formatting.YELLOW));
                                    }
                                }else {
                                    player1.sendMessage(Text.translatable("aliveandewell.randomBPotal.wrongInfo1").formatted(Formatting.YELLOW));
                                }
                            }

                            //在最右中间边
                            if (world.getBlockState(pos.east()).getBlock() == BlockInit.FRAME_ADAMANTIUM
                                    && world.getBlockState(pos.down(2)).getBlock() == BlockInit.FRAME_ADAMANTIUM
                                    && world.getBlockState(pos.down(1)).getBlock() == this
                                    && world.getBlockState(pos.up(1)).getBlock() == this
                                    && world.getBlockState(pos.up(2)).getBlock() == BlockInit.FRAME_ADAMANTIUM) {
                                Block block1 = world.getBlockState(pos.up(2).west(2)).getBlock();
                                Block block2 = world.getBlockState(pos.down(2).west(2)).getBlock();
                                Block block3 = world.getBlockState(pos.up(2).east()).getBlock();
                                Block block4 = world.getBlockState(pos.down(2).east()).getBlock();
                                if(block1 instanceof BaseAdamantiumRandomBlock && block2 instanceof BaseAdamantiumRandomBlock && block3 instanceof BaseAdamantiumRandomBlock && block4 instanceof BaseAdamantiumRandomBlock){
                                    String name1 = block1.getName().getString();
                                    String name2 = block2.getName().getString();
                                    String name3 = block3.getName().getString();
                                    String name4 = block4.getName().getString();

                                    if(valid(name1,name2,name3,name4)){
                                        randomPos = new RandomPos(player1.world.getRegistryKey().getValue().toString(),world.getChunk(pos).getPos(), name1,name2,name3,name4);
                                        blockPos = serverState.getDestination(randomPos,world.getServer());
                                        if(blockPos != null){
                                            player1.teleport((ServerWorld) world, blockPos.getX(),blockPos.getY(),blockPos.getZ(),0.0f,0.0f);
                                        }else {
                                            for (PlayerEntity player : world.getPlayers()){
                                                player.sendMessage(((MutableText)player1.getName()).append(Text.translatable("aliveandwell.randomportalB.through")).formatted(Formatting.RED));
                                            }
                                                RandomTpUtil.RandomTpB(world, entity);
                                            BlockPos pos1 = entity.getBlockPos();
                                            serverState.randomPosMap.put(randomPos,pos1);
                                            for (PlayerEntity player : world.getPlayers()){
                                                player.sendMessage(((MutableText)player1.getName()).append(Text.translatable("aliveandwell.randomportalB.pass")).formatted(Formatting.YELLOW));
                                            }
                                        }
                                    }else {
                                        player1.sendMessage(Text.translatable("aliveandewell.randomBPotal.wrongInfo2").formatted(Formatting.YELLOW));
                                    }
                                }else {
                                    player1.sendMessage(Text.translatable("aliveandewell.randomBPotal.wrongInfo1").formatted(Formatting.YELLOW));
                                }
                            }
                        }

                        if (axis == Direction.Axis.Z) {
                            //南北朝向：北
                            //在最左边
                            if (world.getBlockState(pos.south()).getBlock() == BlockInit.FRAME_ADAMANTIUM
                                    && world.getBlockState(pos.up(3)).getBlock() == BlockInit.FRAME_ADAMANTIUM
                                    && world.getBlockState(pos.up(2)).getBlock() == this
                                    && world.getBlockState(pos.up(1)).getBlock() == this
                            ) {
                                Block block1 = world.getBlockState(pos.up(3).south()).getBlock();
                                Block block2 = world.getBlockState(pos.down().south()).getBlock();
                                Block block3 = world.getBlockState(pos.up(3).north(2)).getBlock();
                                Block block4 = world.getBlockState(pos.down().north(2)).getBlock();
                                if(block1 instanceof BaseAdamantiumRandomBlock && block2 instanceof BaseAdamantiumRandomBlock && block3 instanceof BaseAdamantiumRandomBlock && block4 instanceof BaseAdamantiumRandomBlock){
                                    String name1 = block1.getName().getString();
                                    String name2 = block2.getName().getString();
                                    String name3 = block3.getName().getString();
                                    String name4 = block4.getName().getString();

                                    if(valid(name1,name2,name3,name4)){
                                        randomPos = new RandomPos(player1.world.getRegistryKey().getValue().toString(),world.getChunk(pos).getPos(), name1,name2,name3,name4);
                                        blockPos = serverState.getDestination(randomPos,world.getServer());
                                        if(blockPos != null){
                                            player1.teleport((ServerWorld) world, blockPos.getX(),blockPos.getY(),blockPos.getZ(),0.0f,0.0f);
                                        }else {
                                            for (PlayerEntity player : world.getPlayers()){
                                                player.sendMessage(((MutableText)player1.getName()).append(Text.translatable("aliveandwell.randomportalB.through")).formatted(Formatting.RED));
                                            }
                                                RandomTpUtil.RandomTpB(world, entity);
                                            BlockPos pos1 = entity.getBlockPos();
                                            serverState.randomPosMap.put(randomPos,pos1);
                                            for (PlayerEntity player : world.getPlayers()){
                                                player.sendMessage(((MutableText)player1.getName()).append(Text.translatable("aliveandwell.randomportalB.pass")).formatted(Formatting.YELLOW));
                                            }
                                        }
                                    }else {
                                        player1.sendMessage(Text.translatable("aliveandewell.randomBPotal.wrongInfo2").formatted(Formatting.YELLOW));
                                    }
                                }else {
                                    player1.sendMessage(Text.translatable("aliveandewell.randomBPotal.wrongInfo1").formatted(Formatting.YELLOW));
                                }
                            }

                            //在最右下边
                            if (world.getBlockState(pos.north()).getBlock() == BlockInit.FRAME_ADAMANTIUM
                                    && world.getBlockState(pos.up(3)).getBlock() == BlockInit.FRAME_ADAMANTIUM
                                    && world.getBlockState(pos.up(2)).getBlock() == this
                                    && world.getBlockState(pos.up(1)).getBlock() == this
                            ) {
                                Block block1 = world.getBlockState(pos.up(3).south(2)).getBlock();
                                Block block2 = world.getBlockState(pos.down().south(2)).getBlock();
                                Block block3 = world.getBlockState(pos.up(3).north()).getBlock();
                                Block block4 = world.getBlockState(pos.down().north()).getBlock();
                                if(block1 instanceof BaseAdamantiumRandomBlock && block2 instanceof BaseAdamantiumRandomBlock && block3 instanceof BaseAdamantiumRandomBlock && block4 instanceof BaseAdamantiumRandomBlock){
                                    String name1 = block1.getName().getString();
                                    String name2 = block2.getName().getString();
                                    String name3 = block3.getName().getString();
                                    String name4 = block4.getName().getString();

                                    if(valid(name1,name2,name3,name4)){
                                        randomPos = new RandomPos(player1.world.getRegistryKey().getValue().toString(),world.getChunk(pos).getPos(), name1,name2,name3,name4);
                                        blockPos = serverState.getDestination(randomPos,world.getServer());
                                        if(blockPos != null){
                                            player1.teleport((ServerWorld) world, blockPos.getX(),blockPos.getY(),blockPos.getZ(),0.0f,0.0f);
                                        }else {
                                            for (PlayerEntity player : world.getPlayers()){
                                                player.sendMessage(((MutableText)player1.getName()).append(Text.translatable("aliveandwell.randomportalB.through")).formatted(Formatting.RED));
                                            }
                                                RandomTpUtil.RandomTpB(world, entity);
                                            BlockPos pos1 = entity.getBlockPos();
                                            serverState.randomPosMap.put(randomPos,pos1);
                                            for (PlayerEntity player : world.getPlayers()){
                                                player.sendMessage(((MutableText)player1.getName()).append(Text.translatable("aliveandwell.randomportalB.pass")).formatted(Formatting.YELLOW));
                                            }
                                        }
                                    }else {
                                        player1.sendMessage(Text.translatable("aliveandewell.randomBPotal.wrongInfo2").formatted(Formatting.YELLOW));
                                    }
                                }else {
                                    player1.sendMessage(Text.translatable("aliveandewell.randomBPotal.wrongInfo1").formatted(Formatting.YELLOW));
                                }
                            }

                            //在最左中间边
                            if (world.getBlockState(pos.south()).getBlock() == BlockInit.FRAME_ADAMANTIUM
                                    && world.getBlockState(pos.down(2)).getBlock() == BlockInit.FRAME_ADAMANTIUM
                                    && world.getBlockState(pos.down(1)).getBlock() == this
                                    && world.getBlockState(pos.up(2)).getBlock() == BlockInit.FRAME_ADAMANTIUM
                                    && world.getBlockState(pos.up(1)).getBlock() == this
                            ) {
                                Block block1 = world.getBlockState(pos.up(2).south()).getBlock();
                                Block block2 = world.getBlockState(pos.down(2).south()).getBlock();
                                Block block3 = world.getBlockState(pos.up(2).north(2)).getBlock();
                                Block block4 = world.getBlockState(pos.down(2).north(2)).getBlock();
                                if(block1 instanceof BaseAdamantiumRandomBlock && block2 instanceof BaseAdamantiumRandomBlock && block3 instanceof BaseAdamantiumRandomBlock && block4 instanceof BaseAdamantiumRandomBlock){
                                    String name1 = block1.getName().getString();
                                    String name2 = block2.getName().getString();
                                    String name3 = block3.getName().getString();
                                    String name4 = block4.getName().getString();

                                    if(valid(name1,name2,name3,name4)){
                                        randomPos = new RandomPos(player1.world.getRegistryKey().getValue().toString(),world.getChunk(pos).getPos(), name1,name2,name3,name4);
                                        blockPos = serverState.getDestination(randomPos,world.getServer());
                                        if(blockPos != null){
                                            player1.teleport((ServerWorld) world, blockPos.getX(),blockPos.getY(),blockPos.getZ(),0.0f,0.0f);
                                        }else {
                                            for (PlayerEntity player : world.getPlayers()){
                                                player.sendMessage(((MutableText)player1.getName()).append(Text.translatable("aliveandwell.randomportalB.through")).formatted(Formatting.RED));
                                            }
                                                RandomTpUtil.RandomTpB(world, entity);
                                            BlockPos pos1 = entity.getBlockPos();
                                            serverState.randomPosMap.put(randomPos,pos1);
                                            for (PlayerEntity player : world.getPlayers()){
                                                player.sendMessage(((MutableText)player1.getName()).append(Text.translatable("aliveandwell.randomportalB.pass")).formatted(Formatting.YELLOW));
                                            }
                                        }
                                    }else {
                                        player1.sendMessage(Text.translatable("aliveandewell.randomBPotal.wrongInfo2").formatted(Formatting.YELLOW));
                                    }
                                }else {
                                    player1.sendMessage(Text.translatable("aliveandewell.randomBPotal.wrongInfo1").formatted(Formatting.YELLOW));
                                }
                            }

                            //在最右中间边
                            if (world.getBlockState(pos.north()).getBlock() == BlockInit.FRAME_ADAMANTIUM
                                    && world.getBlockState(pos.down(2)).getBlock() == BlockInit.FRAME_ADAMANTIUM
                                    && world.getBlockState(pos.down(1)).getBlock() == this
                                    && world.getBlockState(pos.up(2)).getBlock() == BlockInit.FRAME_ADAMANTIUM
                                    && world.getBlockState(pos.up(1)).getBlock() == this
                            ) {
                                Block block1 = world.getBlockState(pos.up(2).south(2)).getBlock();
                                Block block2 = world.getBlockState(pos.down(2).south(2)).getBlock();
                                Block block3 = world.getBlockState(pos.up(2).north()).getBlock();
                                Block block4 = world.getBlockState(pos.down(2).north()).getBlock();
                                if(block1 instanceof BaseAdamantiumRandomBlock && block2 instanceof BaseAdamantiumRandomBlock && block3 instanceof BaseAdamantiumRandomBlock && block4 instanceof BaseAdamantiumRandomBlock){
                                    String name1 = block1.getName().getString();
                                    String name2 = block2.getName().getString();
                                    String name3 = block3.getName().getString();
                                    String name4 = block4.getName().getString();

                                    if(valid(name1,name2,name3,name4)){
                                        randomPos = new RandomPos(player1.world.getRegistryKey().getValue().toString(),world.getChunk(pos).getPos(), name1,name2,name3,name4);
                                        blockPos = serverState.getDestination(randomPos,world.getServer());
                                        if(blockPos != null){
                                            player1.teleport((ServerWorld) world, blockPos.getX(),blockPos.getY(),blockPos.getZ(),0.0f,0.0f);
                                        }else {
                                            for (PlayerEntity player : world.getPlayers()){
                                                player.sendMessage(((MutableText)player1.getName()).append(Text.translatable("aliveandwell.randomportalB.through")).formatted(Formatting.RED));
                                            }
                                                RandomTpUtil.RandomTpB(world, entity);
                                            BlockPos pos1 = entity.getBlockPos();
                                            serverState.randomPosMap.put(randomPos,pos1);
                                            for (PlayerEntity player : world.getPlayers()){
                                                player.sendMessage(((MutableText)player1.getName()).append(Text.translatable("aliveandwell.randomportalB.pass")).formatted(Formatting.YELLOW));
                                            }
                                        }
                                    }else {
                                        player1.sendMessage(Text.translatable("aliveandewell.randomBPotal.wrongInfo2").formatted(Formatting.YELLOW));
                                    }
                                }else {
                                    player1.sendMessage(Text.translatable("aliveandewell.randomBPotal.wrongInfo1").formatted(Formatting.YELLOW));
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public boolean valid(String name1,String name2,String name3,String name4){
        if(name1.equals(name2) || name1.equals(name3) || name1.equals(name4)){
            return false;
        }else if(name2.equals(name3) || name2.equals(name4)){
            return false;
        }else return !name3.equals(name4);
    }
}
