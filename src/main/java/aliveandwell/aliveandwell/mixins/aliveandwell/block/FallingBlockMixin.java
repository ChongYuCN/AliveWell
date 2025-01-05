package aliveandwell.aliveandwell.mixins.aliveandwell.block;

import net.minecraft.block.*;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FallingBlock.class)
public abstract class FallingBlockMixin extends Block implements LandingBlock {

    public FallingBlockMixin(Settings settings) {
        super(settings);
    }

    @Shadow
    public void configureFallingBlockEntity(FallingBlockEntity entity) {
//        MagmaBlock block;
    }

    @Shadow
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        //world.createAndScheduleBlockTick(pos, this, this.getFallDelay());
    }

//    @Shadow
//    public int getFallDelay() {
//        return 2;
//    }
    public boolean canFall(World world, BlockPos pos) {
        return FallingBlock.canFallThrough(world.getBlockState(pos)) && pos.getY() >= world.getBottomY();
    }

    public boolean isReadyToFall(World world, BlockPos pos) {
        boolean fall = false;
        if (canFall(world, pos.down())) {
            fall = true;
        }
        else if (!(world.getBlockState(pos).getBlock() instanceof AnvilBlock)){
            if (world.getRandom().nextInt(3) == 0 && !world.isClient()) {

                if (canFall(world, pos.add(0, 1, 0))) {
                    if (canFall(world, pos.add(-1, 0, 0)) && canFall(world, pos.add(-1, -1, 0))) {
                        fall = true;
                    } else if (canFall(world, pos.add(0, 0, -1)) && canFall(world, pos.add(0, -1, -1))) {
                        fall = true;
                    } else if (canFall(world, pos.add(1, 0, 0)) && canFall(world, pos.add(1, -1, 0))) {
                        fall = true;
                    } else if (canFall(world, pos.add(0, 0, 1)) && canFall(world, pos.add(0, -1, 1))) {
                        fall = true;
                    }
                }
            }

        }
        return fall;
    }

    public void tryToFall(World world, BlockPos pos) {
        if (isReadyToFall(world, pos)) {
            FallingBlockEntity fallingBlockEntity = new FallingBlockEntity(world, (double) pos.getX() + 0.5D,
                    (double) pos.getY(), (double) pos.getZ() + 0.5D, world.getBlockState(pos));
            this.configureFallingBlockEntity(fallingBlockEntity);
//            world.removeBlock(pos, false);
            world.removeBlock(pos, true);
            world.spawnEntity(fallingBlockEntity);
        }
    }

//    @Override
//    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
//        if (placer != null) {
//            tryToFall(world, pos);
//        }
//    }

    @Inject(at = @At("HEAD"), method = "scheduledTick", cancellable = true)
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, net.minecraft.util.math.random.Random random, CallbackInfo ci) {
        if ((Block)(Object)this == Blocks.DIRT || (Block)(Object)this == Blocks.COARSE_DIRT || (Block)(Object)this == Blocks.ROOTED_DIRT || (Block)(Object)this == Blocks.SOUL_SAND || (Block)(Object)this == Blocks.SOUL_SOIL || (Block)(Object)this == Blocks.HAY_BLOCK) {
            if (!world.getBlockState(pos.down()).isSolidBlock(world, pos.down())) {
                tryToFall(world, pos);
            }
        }

    }
//    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
//        if (entity.getVelocity().length() > 0.0f) {
//            tryToFall(world, pos);
//        }
//        super.onSteppedOn(world, pos, state, entity);
//    }

}
