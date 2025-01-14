package aliveandwell.aliveandwell.mixins.miningsblock;

import aliveandwell.aliveandwell.miningsblock.MiningMixinHooks;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Block.class)
public class BlockMixin {
    //��������λ��
    @ModifyVariable(at = @At("HEAD"), method = "dropStack(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/item/ItemStack;)V", argsOnly = true)
    private static BlockPos veinmining$dropStack(BlockPos pos, World level, BlockPos unused, ItemStack stack) {
        return MiningMixinHooks.getActualSpawnPos(level, pos);
    }

    //��������λ��
    @ModifyVariable(at = @At("HEAD"), method = "dropStack(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/Direction;Lnet/minecraft/item/ItemStack;)V", argsOnly = true)
    private static BlockPos veinmining$dropStackFromFace(BlockPos pos, World level, BlockPos unused, Direction direction, ItemStack stack) {
        return MiningMixinHooks.getActualSpawnPos(level, pos);
    }
}
