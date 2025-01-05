package aliveandwell.aliveandwell.mixins.aliveandwell.block;

import net.minecraft.block.*;
import net.minecraft.item.BoneMealItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BoneMealItem.class)
public class BoneMealItemMixin extends Item {
    public BoneMealItemMixin(Settings settings) {
        super(settings);
    }

    @Overwrite
    public static boolean useOnFertilizable(ItemStack stack, World world, BlockPos pos) {
        Fertilizable fertilizable;
        BlockState blockState = world.getBlockState(pos);
        if (blockState.getBlock() instanceof Fertilizable && (fertilizable = (Fertilizable)((Object)blockState.getBlock())).isFertilizable(world, pos, blockState, world.isClient)) {
            if (world instanceof ServerWorld) {
                if (fertilizable.canGrow(world, world.random, pos, blockState)) {
                    if(fertilizable instanceof CropBlock || fertilizable instanceof SaplingBlock || fertilizable instanceof PlantBlock || fertilizable instanceof CaveVinesHeadBlock || fertilizable instanceof MossBlock || fertilizable instanceof CocoaBlock){
                        return false;
                    }
                    fertilizable.grow((ServerWorld)world, world.random, pos, blockState);
                }
                stack.decrement(1);
            }
            return true;
        }
        return false;
    }
}
