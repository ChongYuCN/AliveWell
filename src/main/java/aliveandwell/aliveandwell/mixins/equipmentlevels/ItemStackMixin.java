package aliveandwell.aliveandwell.mixins.equipmentlevels;

import aliveandwell.aliveandwell.equipmentlevels.handle.callback.ItemTooltipCallback;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(ItemStack.class)
public class ItemStackMixin {

    @Inject(method = "getTooltip", at = @At(value = "RETURN"))
    public void tooltipLines(PlayerEntity player, TooltipContext context, CallbackInfoReturnable<List<Text>> cir) {
        ItemTooltipCallback.LIVING_TICK.invoker().onItemTooltip((ItemStack) (Object)this, player, cir.getReturnValue(), context);
    }

}