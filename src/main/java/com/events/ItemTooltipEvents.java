package com.chongyu.aliveandwell.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface ItemTooltipEvents {

    public static final Event<ItemTooltipEvents> LIVING_TICK = EventFactory.createArrayBacked(ItemTooltipEvents.class, callbacks -> (itemStack, entityPlayer, list, flags) -> {
        for (ItemTooltipEvents callback : callbacks) {
            callback.onItemTooltip(itemStack, entityPlayer, list, flags);
        }
    });


    void onItemTooltip(ItemStack itemStack, @Nullable PlayerEntity entityPlayer, List<Text> list, TooltipContext flags);


}

