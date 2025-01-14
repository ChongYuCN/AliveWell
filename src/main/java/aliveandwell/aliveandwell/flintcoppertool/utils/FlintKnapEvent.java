package aliveandwell.aliveandwell.flintcoppertool.utils;

import java.util.Random;

import aliveandwell.aliveandwell.registry.ItemInit;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class FlintKnapEvent {

    public static final Random RANDOM = new Random();

    public static void knapEvent() {
        
        UseBlockCallback.EVENT.register((player, world, hand, block) -> {
            ItemStack stack = player.getStackInHand(hand);
            BlockPos pos = block.getBlockPos();
            BlockState state = world.getBlockState(pos);

            double r1 = RANDOM.nextDouble();
            double r2 = RANDOM.nextDouble();
            int r3 = RANDOM.nextInt(2) + 1;

            if (state == null || player == null)
                return ActionResult.PASS;

            if (player.getInventory().getMainHandStack().getItem().equals(Items.FLINT) &&
                    (state.getMaterial() == Material.STONE ) &&
                    block.getSide() == Direction.UP) {
                if (!world.isClient) {
                    if (r1 <= 0.1) {
                        if (r2 <= 0.8) {
                            ItemEntity itemEntity = new ItemEntity(
                                player.world,
                                block.getPos().x,
                                block.getPos().y,
                                block.getPos().z,
                                new ItemStack(
                                    ItemInit.FLINT_SHARD,
                                    r3));
                            player.world.spawnEntity(itemEntity);
                        }
                        stack.decrement(1);
                        player.setStackInHand(hand, stack);
                        world.playSound(null, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE,
                            SoundCategory.PLAYERS, 1.0F, 0.5F);
                    }
                    else
                        world.playSound(null, pos, SoundEvents.BLOCK_STONE_HIT,
                            SoundCategory.PLAYERS, 1.0F, 1.0F);
                }
                return ActionResult.SUCCESS;
            }

            return ActionResult.PASS;
        });
    }
}
