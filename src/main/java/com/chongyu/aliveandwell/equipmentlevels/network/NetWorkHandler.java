package com.chongyu.aliveandwell.equipmentlevels.network;

import com.chongyu.aliveandwell.equipmentlevels.core.Ability;
import com.chongyu.aliveandwell.equipmentlevels.core.Experience;
import com.chongyu.aliveandwell.equipmentlevels.util.EAUtil;
import com.chongyu.aliveandwell.equipmentlevels.util.NBTUtil;
import com.chongyu.aliveandwell.equipmentlevels.util.Static;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class NetWorkHandler {

    public static void onRun(){
        ServerPlayNetworking.registerGlobalReceiver(new Identifier(Static.MY_ID, "main"),
                (server, player, handler, buf, responseSender) -> {
                    var index = buf.readInt();
                    server.execute(() -> {
                        if (player != null) {
                            var stack = player.getInventory().getStack(player.getInventory().selectedSlot);

                            if (stack != ItemStack.EMPTY) {
                                var nbt = NBTUtil.loadStackNBT(stack);

                                if (EAUtil.canEnhanceWeapon(stack.getItem())) {
                                    if (Ability.WEAPON_ABILITIES.get(index).hasAbility(nbt)) {
                                        Ability.WEAPON_ABILITIES.get(index).setLevel(nbt, Ability.WEAPON_ABILITIES.get(index).getLevel(nbt) + 1);
                                        Experience.setAbilityTokens(nbt, Experience.getAbilityTokens(nbt) - Ability.WEAPON_ABILITIES.get(index).getTier());
                                    } else {
                                        Ability.WEAPON_ABILITIES.get(index).addAbility(nbt);
                                        if (!player.isCreative())
                                            player.addExperienceLevels(-Ability.WEAPON_ABILITIES.get(index).getExpLevel(nbt) + 1);
                                    }
                                } else if (EAUtil.canEnhanceArmor(stack.getItem())) {
                                    if (Ability.ARMOR_ABILITIES.get(index).hasAbility(nbt)) {
                                        Ability.ARMOR_ABILITIES.get(index).setLevel(nbt, Ability.ARMOR_ABILITIES.get(index).getLevel(nbt) + 1);
                                        Experience.setAbilityTokens(nbt, Experience.getAbilityTokens(nbt) - Ability.ARMOR_ABILITIES.get(index).getTier());
                                    } else {
                                        Ability.ARMOR_ABILITIES.get(index).addAbility(nbt);
                                        if (!player.isCreative())
                                            player.addExperienceLevels(-Ability.ARMOR_ABILITIES.get(index).getExpLevel(nbt) + 1);
                                    }
                                }
                            }
                        }
                    });

                });
    }
}
