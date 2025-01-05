package aliveandwell.aliveandwell.mixins.aliveandwell.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockItem.class)
public class BlockItemMixin {

    @Inject(method = "place(Lnet/minecraft/item/ItemPlacementContext;)Lnet/minecraft/util/ActionResult;", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;onPlaced(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;)V", shift = At.Shift.AFTER))
    public void place(ItemPlacementContext context, CallbackInfoReturnable<ActionResult> cir) {
        ItemPlacementContext itemPlacementContext = this.getPlacementContext(context);
        PlayerEntity playerEntity = null;
        if (itemPlacementContext != null) {
            playerEntity = itemPlacementContext.getPlayer();
        }
        if (playerEntity != null && !playerEntity.getWorld().isClient) {
            playerEntity.addExhaustion(0.6f);
        }
    }

    @Shadow
    @Nullable
    public ItemPlacementContext getPlacementContext(ItemPlacementContext context) {
        return context;
    }
}
