package aliveandwell.aliveandwell.equipmentlevels.handle.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface ItemTooltipCallback {

    public static final Event<ItemTooltipCallback> LIVING_TICK = EventFactory.createArrayBacked(ItemTooltipCallback.class, callbacks -> (itemStack, entityPlayer, list, flags) -> {
        for (ItemTooltipCallback callback : callbacks) {
            callback.onItemTooltip(itemStack, entityPlayer, list, flags);
        }
    });


    void onItemTooltip(ItemStack itemStack, @Nullable PlayerEntity entityPlayer, List<Text> list, TooltipContext flags);


}

