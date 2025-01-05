package com.chongyu.aliveandwell.mixin.crafttime;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.screen.CraftingScreenHandler;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.CraftingResultSlot;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(ScreenHandler.class)
public class ScreenHandlerMixin {
    @Final
    @Shadow public final DefaultedList<Slot> slots = DefaultedList.of();

    @Inject(method = "internalOnSlotClick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/screen/slot/Slot;takeStackRange(IILnet/minecraft/entity/player/PlayerEntity;)Lnet/minecraft/item/ItemStack;"
                    ),
            slice = @Slice(
                    from = @At(value = "FIELD", target = "Lnet/minecraft/screen/slot/SlotActionType;THROW:Lnet/minecraft/screen/slot/SlotActionType;"),
                    to = @At(value = "FIELD", target = "Lnet/minecraft/screen/slot/SlotActionType;PICKUP_ALL:Lnet/minecraft/screen/slot/SlotActionType;")
            ),
            cancellable = true)
    private void internalOnSlotClick(int slotIndex, int button, SlotActionType actionType, PlayerEntity player, CallbackInfo ci) {
        if(!player.getWorld().isClient){
            Slot slot3 = this.slots.get(slotIndex);
            if(slot3 instanceof CraftingResultSlot && ((ScreenHandler)(Object)this instanceof CraftingScreenHandler || (ScreenHandler)(Object)this instanceof PlayerScreenHandler)){
                int j = button == 0 ? 1 : slot3.getStack().getCount();
                ItemStack itemStack = slot3.takeStackRange(j, Integer.MAX_VALUE, player);

                String nameItem = Registries.ITEM.getId(itemStack.getItem()).toString();
                //添加nbt标签后掉落不消失
                if (nameItem.contains("netherite_scrap") ||nameItem.contains("blaze_rod") ||nameItem.contains("elytra")) {
                    if (nameItem.contains("minecraft:")) {
                        if (itemStack.hasNbt()) {
                            if (!Objects.requireNonNull(itemStack.getNbt()).contains("aliveandwell")) {//不包含标签消失：玩家扔出不会消失
//                                setNbt(itemStack);
                                itemStack.getNbt().putString("aliveandwell","aliveandwell");
                            }
                        }else {
                            setNbt(itemStack);
                        }
                    }
                }

                if(nameItem.contains("argent_energy") ||nameItem.contains("argent_block")){
                    if(nameItem.contains("doom:")) {
                        if(itemStack.hasNbt()){
                            if(!Objects.requireNonNull(itemStack.getNbt()).contains("aliveandwell")) {//不包含标签消失：玩家扔出不会消失
                                setNbt(itemStack);
                            }
                        }else {
                            setNbt(itemStack);
                        }
                    }
                }

                if (player.getInventory().insertStack(itemStack)){
                    player.getInventory().insertStack(itemStack);
                }else {
                    player.dropStack(itemStack);
                }
                ci.cancel();
            }
        }
    }

    @Unique
    public void setNbt(ItemStack itemStack) {
        NbtCompound nbt;
        if(!itemStack.hasNbt()){
            nbt = new NbtCompound();
        }else {
            nbt = itemStack.getNbt();
        }

        itemStack.setSubNbt("aliveandwell",nbt);
    }
}
