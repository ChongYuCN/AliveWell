package aliveandwell.aliveandwell.mixins.aliveandwell.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.Fertilizable;
import net.minecraft.block.PlantBlock;
import net.minecraft.block.SweetBerryBushBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SweetBerryBushBlock.class)
public abstract class SweetBerryBushBlockMixin extends PlantBlock implements Fertilizable {
    @Unique
    public int time;
    @Final
    @Shadow
    public static IntProperty AGE;

    public SweetBerryBushBlockMixin(Settings settings) {
        super(settings);
    }

    @Inject(at=@At("HEAD"), method="randomTick", cancellable = true)
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci) {
        if (random.nextInt((int)(5 * (2.0 - world.getBiome(pos).value().getTemperature())) + 5) != 0) {
            ci.cancel();
        }
    }

//    @Overwrite
//    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
//        time++;
//        int i = (Integer)state.get(AGE);
//        if(time >= 3*20){
//            if (i < 3 && random.nextInt(5) == 0 && world.getBaseLightLevel(pos.up(), 0) >= 9) {
//                BlockState blockState = (BlockState)state.with(AGE, i + 1);
//                world.setBlockState(pos, blockState, 2);
//                world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(blockState));
//            }
//            time = 0 ;
//        }
//
//    }

//    @Overwrite
//    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
//        int i = (Integer)state.get(AGE);
//        boolean bl = i == 3;
//        if (!bl && player.getStackInHand(hand).isOf(Items.BONE_MEAL)) {
//            return ActionResult.PASS;
//        } else if (i > 1) {
//            int j = 1 ;
//            dropStack(world, pos, new ItemStack(Items.SWEET_BERRIES, j + (bl ? world.random.nextInt(2) : 0)));
//            world.playSound((PlayerEntity)null, pos, SoundEvents.BLOCK_SWEET_BERRY_BUSH_PICK_BERRIES, SoundCategory.BLOCKS, 1.0F, 0.8F + world.random.nextFloat() * 0.4F);
//            BlockState blockState = (BlockState)state.with(AGE, 1);
//            world.setBlockState(pos, blockState, 2);
//            world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(player, blockState));
//            return ActionResult.success(world.isClient);
//        } else {
//            return super.onUse(state, world, pos, player, hand, hit);
//        }
//    }
}
